package stubs

import com.fieldfreshmarket.api.core.cognito.CognitoAccessTokenClaims
import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.model.User
import java.time.Instant
import java.util.*

const val TEST_USER_ID_1: String = "u_9TQbKiuoUB3ArZ6IinEDab"
const val TEST_USER_ID_2: String = "u_9TQbKiuoUB3ArZ6IinEDBA"
const val FIRST_NAME_1: String = "Ashkan"
const val LAST_NAME_1: String = "Abedian"
const val FIRST_NAME_2: String = "John"
const val LAST_NAME_2: String = "Doe"
const val EMAIL_1: String = "neyonlime@gmil.com"
const val EMAIL_2: String = "testemail@gmil.com"
const val PHONE: String = "5432643421"
const val SUB_1: String = "4ae0268f-d135-49ce-99fd-e4ed0b1c479d"
const val SUB_2: String = "4ae0268f-d135-49ce-99fd-e4ed0b1c479l"

fun user(
    firstName: String = FIRST_NAME_1,
    lastName: String = LAST_NAME_1,
    email: String = EMAIL_1,
    sub: String = SUB_1
) = User(
    firstName = firstName,
    lastName = lastName,
    email = email,
    cognitoSub = sub,
    phone = PHONE
).apply { id = TEST_USER_ID_1 }

fun user2(
    firstName: String = FIRST_NAME_2,
    lastName: String = LAST_NAME_2,
    email: String = EMAIL_2,
    sub: String = SUB_2
) = User(
    firstName = firstName,
    lastName = lastName,
    email = email,
    cognitoSub = sub,
    phone = PHONE
).apply { id = TEST_USER_ID_2 }

fun grant(user: User = user()) = AccessGrant(
    user = user,
    accessTokenClaims = tokenClaims(user)
)

private fun tokenClaims(user: User) = CognitoAccessTokenClaims(
    tokenUse = "",
    issued = Date(),
    expire = Date(),
    issuer = "",
    email = user.email,
    sub = user.cognitoSub!!,
    token = ""
)