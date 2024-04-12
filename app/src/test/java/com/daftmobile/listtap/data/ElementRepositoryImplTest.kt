package com.daftmobile.listtap.data

import com.daftmobile.listtap.MainDispatcherRule
import com.daftmobile.listtap.data.model.Element
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ElementRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun testGetRandomElement() = runTest {

        val elementRepository = ElementRepositoryImpl(defaultDispatcher = mainDispatcherRule.testDispatcher)

        val numberOfTestElements = 10
        val elements = mutableListOf<Element>()
        for (i in 1..numberOfTestElements) {
            elements.add(elementRepository.getRandomElement())
        }
        assertEquals(numberOfTestElements, elements.size)
        for (i in 0 until numberOfTestElements) {
            assert(elements[i].value in 0..MAX_VALUE)
        }
    }
}
