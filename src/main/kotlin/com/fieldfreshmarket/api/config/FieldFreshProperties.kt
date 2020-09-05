package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.ParamManager
import com.fieldfreshmarket.api.core.cognito.client.CognitoProperties
import org.springframework.stereotype.Component

@Component
class FieldFreshProperties(
   paramManager: ParamManager
): CognitoProperties {

   override val cognitoUserDomain: String = paramManager.getString("COGNITO_USER_DOMAIN")
   override val cognitoUserPoolId: String = paramManager.getString("COGNITO_USER_POOL_ID")
   override val cognitoUserClientId: String = paramManager.getString("COGNITO_USER_CLIENT_ID")
   override val cognitoUserKeysUrl: String = paramManager.getString("COGNITO_USER_KEYS_URL")
   override val cognitoCallbackUrl: String = paramManager.getString("COGNITO_CALLBACK_URL")

   override val awsRegion: String = paramManager.getString("AWS_REGION")

   val unauthorizedEndpoints: List<String> = paramManager.getStringList("UNAUTHORIZED_ENDPOINTS")

   val dbUrl: String = paramManager.getString("DB_URL")
   val dbUser: String = paramManager.getString("DB_USER")
   val dbPass: String = paramManager.getString("DB_PASS")
}