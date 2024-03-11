package com.daftmobile.listtap.data

import com.daftmobile.listtap.data.model.Element
import com.daftmobile.listtap.data.model.ElementColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val MAX_VALUE = 4 // max value for random item. Set it to 4 for easier testing
class ElementRepositoryImpl : ElementRepository {
    override suspend fun getRandomElement(): Element = withContext(Dispatchers.Default) {
        val chance = (1..100).random()
        // Question: There is no rules how to create random value for item so in real life I would ask for more details
        if (chance < 51) {
            Element((0..MAX_VALUE).random(), ElementColor.RED)
        } else {
            Element((0..MAX_VALUE).random(), ElementColor.BLUE)
        }
    }
}
