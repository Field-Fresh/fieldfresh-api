package stubs

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User

const val TEST_PROXY_ID: String = "pr_9TQbKiuoUB3ArZ6IinEDab"
const val DISPLAY_NAME: String = "My Store"
const val STREET_ADDRESS: String = "22 Olive Ave "
const val POSTAL_CODE: String = "M2N 7G6"
const val CITY: String = "Toronto"
const val PROVINCE: String = "ON"
const val COUNTRY: String = "CA"
const val LONGITUDE: Double = -80.5868704
const val LATITUDE: Double = 43.4658121

fun proxy(
    user: User = user(),
    displayName: String = DISPLAY_NAME,
    description: String? = null,
    streetAddress: String = STREET_ADDRESS,
    postalCode: String = POSTAL_CODE,
    city: String = CITY,
    province: String = PROVINCE,
    country: String = COUNTRY,
    longitude: Double = LONGITUDE,
    latitude: Double = LATITUDE,
) = Proxy(
    user = user,
    displayName = displayName,
    description = description,
    streetAddress = streetAddress,
    postalCode = postalCode,
    city = city,
    province = province,
    country = country,
    longitude = longitude,
    latitude = latitude
).apply { id = TEST_PROXY_ID }