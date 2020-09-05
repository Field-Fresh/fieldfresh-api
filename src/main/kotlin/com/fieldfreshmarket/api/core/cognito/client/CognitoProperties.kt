package com.fieldfreshmarket.api.core.cognito.client

interface CognitoProperties {
   val cognitoUserDomain: String
   val cognitoUserPoolId: String
   val cognitoUserClientId: String
   val cognitoUserKeysUrl: String
   val cognitoCallbackUrl: String
   val awsRegion: String
}