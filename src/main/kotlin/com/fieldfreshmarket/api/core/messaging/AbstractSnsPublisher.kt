package com.fieldfreshmarket.api.core.messaging

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.model.PublishRequest
import software.amazon.awssdk.services.sns.model.PublishResponse
import java.util.*
import kotlin.coroutines.CoroutineContext

abstract class AbstractSnsPublisher(
    private val sns: SnsAsyncClient
) : CoroutineScope {

    private val supervisorJob = SupervisorJob()

    private val logger: Logger = LoggerFactory.getLogger(AbstractSnsPublisher::class.java)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + supervisorJob

    private val gson: Gson by lazy {
        createGson()
    }

    open fun createGson(): Gson = GsonBuilder().create()

    // TODO Consider using channels to limit concurrency
    private fun sendMessageAsync(topicArn: String, type: String, message: SNSMessage): Deferred<PublishResponse> {
        val _message = mapOf(
            "type" to type,
            "message" to message
        )
        return async {
            sns.publish(PublishRequest.builder()
                .targetArn(topicArn)
                .message(gson.toJson(_message))
                .build()
            ).await()
        }
    }

    internal fun sendBatchMessage(
        topicArn: String,
        type: String,
        messages: List<SNSMessage>,
        totalCount: Long,
        batchSize: Int,
        batchId: String?
    ) = runBlocking {
        messages.chunked(batchSize).forEach { chunk ->
            chunk.map { message ->
                sendMessageAsync(
                    topicArn,
                    type,
                    BatchMessage(
                        batchId ?: UUID.randomUUID().toString(),
                        totalCount,
                        message
                    ))
            }.forEach { it.await() }
            logger.info("Published ${chunk.size} message/s to $topicArn")
        }
    }

    fun stop() {
        supervisorJob.cancel()
    }
}
