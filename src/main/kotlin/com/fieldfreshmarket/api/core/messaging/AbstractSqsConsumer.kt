package com.fieldfreshmarket.api.core.messaging

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import java.lang.Thread.currentThread
import kotlin.coroutines.CoroutineContext

abstract class AbstractSqsConsumer(
    private val sqs: SqsAsyncClient,
    private val queueUrl: String?,
    private val workerCount: Int?,
    private val receiver: MessageReceiver
): CoroutineScope {

    private val supervisorJob = SupervisorJob()

    private val logger: Logger = LoggerFactory.getLogger(AbstractSqsConsumer::class.java)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + supervisorJob

    fun start() = launch {
        val messageChannel = Channel<Message>()
        if (queueUrl == null) {
            logger.warn("SQS listener's not started due to null queueUrl")
            stop()
            return@launch
        }
        repeat(workerCount ?: 0) { launchWorker(messageChannel) }
        launchMsgReceiver(messageChannel)
        logger.info("SQS listener started to $queueUrl")
    }

    fun stop() {
        logger.info("SQS listener's stopping...")
        return supervisorJob.cancel()
    }

    private fun CoroutineScope.launchMsgReceiver(channel: SendChannel<Message>) = launch {
        repeatUntilCancelled {
            val receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .waitTimeSeconds(20)
                    .maxNumberOfMessages(10)
                    .build()

            val messages = sqs.receiveMessage(receiveRequest)?.await()?.messages()
            messages?.run{
                if(this.size > 0)
                    println("${currentThread().name} Retrieved ${this.size} messages")
            }
            messages?.forEach {
                channel.send(it)
            }
        }
    }

    suspend fun CoroutineScope.repeatUntilCancelled(block: suspend () -> Unit) {
        while (isActive) {
            try {
                block()
                yield()
            } catch (ex: CancellationException) {
                println("coroutine on ${currentThread().name} cancelled")
            } catch (ex: Exception) {
                println("${currentThread().name} failed with {$ex}. Retrying...")
                ex.printStackTrace()
            }
        }

        println("coroutine on ${currentThread().name} exiting")
    }

    private fun CoroutineScope.launchWorker(channel: ReceiveChannel<Message>) = launch {
        repeatUntilCancelled {
            for (msg in channel) {
                try {
                    processMsg(msg)
                    deleteMessage(msg)
                } catch (ex: Exception) {
                    println("${currentThread().name} exception trying to process message ${msg.body()}")
                    ex.printStackTrace()
                    changeVisibility(msg)
                }
            }
        }
    }

    private suspend fun processMsg(message: Message) =
        receiver.receive(message)

    private suspend fun deleteMessage(message: Message) {
        sqs.deleteMessage { req ->
            req.queueUrl(queueUrl)
            req.receiptHandle(message.receiptHandle())
        }.await()
    }

    private suspend fun changeVisibility(message: Message) {
        sqs.changeMessageVisibility { req ->
            req.queueUrl(queueUrl)
            req.receiptHandle(message.receiptHandle())
            req.visibilityTimeout(60)
        }.await()
    }

}