package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.ParamManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.services.ssm.SsmClient

@Configuration
class SSMConfig {

  @Bean
  fun ssmClient(): SsmClient? =
      // Create a Secrets Manager client
      SsmClient.create()

  @Bean
  fun paramManager(ssmClient: SsmClient, environment: Environment): ParamManager =
      ParamManager(
         "fieldfresh-api",
         ssmClient,
         environment.activeProfiles.first()
      )
}
