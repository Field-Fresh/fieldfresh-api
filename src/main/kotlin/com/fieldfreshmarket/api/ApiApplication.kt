package com.fieldfreshmarket.api

import com.fieldfreshmarket.api.messaging.SQSConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.AbstractEnvironment

@SpringBootApplication
class ApiApplication: CommandLineRunner {

	private val logger: Logger = LoggerFactory.getLogger(ApiApplication::class.java)

	@Autowired
	private lateinit var sqsConsumer: SQSConsumer

	override fun run(vararg args: String?) {
		val currentEnv = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME)
		logger.info("Starting SQS Listener...")
		sqsConsumer.start()
		logger.info("FieldFresh api is ready to receive requests in: $currentEnv environment")
	}

}

fun main(args: Array<String>) {
	var currentEnv = System.getProperty("ENVIRONMENT")
	if (currentEnv == null) currentEnv = System.getenv("ENVIRONMENT")
	if (currentEnv == null) currentEnv = "dev"
	System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, currentEnv)
	runApplication<ApiApplication>(*args)
}
