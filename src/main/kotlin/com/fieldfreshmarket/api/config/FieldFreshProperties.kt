package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.ParamManager
import org.springframework.stereotype.Component

@Component
class FieldFreshProperties(
   paramManager: ParamManager
) {
   val awsRegion: String = paramManager.getString("AWS_REGION")

   val dbUrl: String = paramManager.getString("DB_URL")
   val dbUser: String = paramManager.getString("DB_USER")
   val dbPass: String = paramManager.getString("DB_PASS")
}