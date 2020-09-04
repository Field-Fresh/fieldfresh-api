package com.fieldfreshmarket.api.core

import software.amazon.awssdk.services.ssm.SsmClient
import software.amazon.awssdk.services.ssm.model.GetParameterRequest
import software.amazon.awssdk.services.ssm.model.GetParameterResponse


class ParamManager(
   private val basePath: String,
   private val ssmClient: SsmClient? = null,
   private val environment: String
) {

   private fun getParameter(key: String?, encryption: Boolean = true): String? {
      val getParameterRequest: GetParameterRequest = GetParameterRequest.builder()
         .name("/${environment}/${basePath}/$key")
         .withDecryption(encryption)
         .build()
      val result: GetParameterResponse?
      result = try {
         ssmClient?.getParameter(getParameterRequest)
      } catch (e: Exception) {
         null
      }
      return result?.parameter()?.value()
   }

   fun getStringList(parameterName: String): List<String> =
      System.getProperty(parameterName)?.split(",")
         ?: System.getenv(parameterName)?.split(",")
         ?: getParameter(parameterName)?.split(",")
         ?: error("Parameter $parameterName not found")

   fun getStringOrNull(parameterName: String): String? =
      System.getProperty(parameterName)
         ?: System.getenv(parameterName)
         ?: getParameter(parameterName)

   fun getString(parameterName: String): String =
      System.getProperty(parameterName)
         ?: System.getenv(parameterName)
         ?: getParameter(parameterName)
         ?: error("Parameter $parameterName not found")

   fun getInt(parameterName: String): Int =
      System.getProperty(parameterName)?.toInt()
         ?: System.getenv(parameterName)?.toInt()
         ?: getParameter(parameterName)?.toInt()
         ?: getParameter(parameterName)?.toInt()
         ?: error("Parameter $parameterName not found")

   fun getIntOrNull(parameterName: String): Int? =
      System.getProperty(parameterName)?.toInt()
         ?: System.getenv(parameterName)?.toInt()
         ?: getParameter(parameterName)?.toInt()
         ?: getParameter(parameterName)?.toInt()

}