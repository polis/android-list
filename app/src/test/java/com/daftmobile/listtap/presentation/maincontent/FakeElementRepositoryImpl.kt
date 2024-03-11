package com.daftmobile.listtap.presentation.maincontent

import com.daftmobile.listtap.data.ElementRepository
import com.daftmobile.listtap.data.model.Element
import com.daftmobile.listtap.data.model.ElementColor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// It's just a show case, how to implement a fake repository for testing.
class FakeElementRepositoryImpl : ElementRepository {

    val element1 = Element(1, ElementColor.BLUE)
    val element2 = Element(2, ElementColor.RED)
    val element3 = Element(3, ElementColor.BLUE)
    val element4 = Element(4, ElementColor.RED)

    val elements = listOf(element1, element2, element3, element4)

    private var i = 0
    val mutex = Mutex()
    override suspend fun getRandomElement(): Element {
        mutex.withLock {
            val element = elements[i]
            println("getRandomElement: $element")
            i++
            return element
        }
    }

}