package com.fieldfreshmarket.api.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DataSourceConfig{

  @Bean
  fun getDataSource(properties: FieldFreshProperties): DataSource =
     DataSourceBuilder.create()
        .url(properties.dbUrl)
        .username(properties.dbUser)
        .password(properties.dbPass)
        .driverClassName("org.postgresql.Driver")
        .build()
}