package com.fieldfreshmarket.api.core.messaging

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Consumer (val name: String, val type: KClass<*>)