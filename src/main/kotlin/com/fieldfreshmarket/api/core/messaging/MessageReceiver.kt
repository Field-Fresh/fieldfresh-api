package com.fieldfreshmarket.api.core.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sqs.model.Message


abstract class MessageReceiver (
    private val router: MessageRouter,
    private val objectMapper: ObjectMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(MessageReceiver::class.java)

    private val gson: Gson by lazy {
        createGson()
    }

    open fun createGson(): Gson = GsonBuilder().create()

    open fun receive(message: Message) {
        val body = gson.fromJson(message.body(), MutableMap::class.java)
                .let {
                    gson.fromJson(it["Message"].toString(), MutableMap::class.java)
                }
        val type = body["type"]
        router::class.java.declaredMethods.forEach { method ->
            val annotation = method.annotations.filterIsInstance<Consumer>().firstOrNull {
                it.name == type
            }
            val obj = annotation?.let { objectMapper.convertValue(body["message"], annotation.type.java) }
            if (obj != null) {
                method?.invoke(router, obj)
            }
        }
    }
}