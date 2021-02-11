package stubs

import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product

const val TEST_PRODUCT_ID: String = "p_ATnbo5DttmX6RkybualxsN"
const val TEST_PRODUCT_ID_2: String = "p_ATnbo5DttmX6RkybualxaN"
const val TEST_PRODUCT_ID_3: String = "p_ATnbo5DttmX6Rkybualsad"
const val TYPE: String = "Granny Smith"
const val CATEGORY: String = "Fruit"
const val FAMILY: String = "Apple"
const val UNIT: String = "lbs"

fun product(
    id: String = TEST_PRODUCT_ID,
) = Product(
    type = TYPE,
    category = CATEGORY,
    family = FAMILY,
    unit = UNIT,
    classType = ClassType.A
).apply { this.id = id }