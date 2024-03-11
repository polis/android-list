package com.daftmobile.listtap.presentation.maincontent

import com.daftmobile.listtap.MainDispatcherRule
import com.daftmobile.listtap.data.ElementRepository
import com.daftmobile.listtap.data.model.Element
import com.daftmobile.listtap.data.model.ElementColor
import com.daftmobile.listtap.presentation.Action
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainContentViewModelTest {

    private val elementRepository = mock<ElementRepository>()
    private val viewModel = MainContentViewModel(elementRepository)

    private val element1 = Element(1, ElementColor.BLUE)
    private val element2 = Element(2, ElementColor.RED)
    private val element3 = Element(3, ElementColor.BLUE)
    private val element4 = Element(4, ElementColor.RED)



    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun testActionAppendElement() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1
        )

        viewModel.submitAction(Action.AppendElement)
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(1, state.elements.size)
    }

    @Test
    fun testActionStarStop() = runTest {

        var state = viewModel.uiState.value
        println(state.isRunning)
        assertEquals(false, state.isRunning)

        viewModel.submitAction(Action.Start)
        advanceTimeBy(100)
        state = viewModel.uiState.value
        println(state.isRunning)
        assertEquals(true, state.isRunning)

        viewModel.submitAction(Action.Start)
        advanceUntilIdle()
        state = viewModel.uiState.first()
        println(state.isRunning)
        assertEquals(false, state.isRunning)

        verify(elementRepository).getRandomElement()
    }

    @Test
    // This is the only one test that is using fake repository.
    fun testActionIncrementItemOnClickFor_Last_ElementOfTheList() = runTest {
        val fakeRepository = FakeElementRepositoryImpl()
        val viewModel = MainContentViewModel(fakeRepository)

        // add 4 elements from the fake repository
        for (i in 1..4) {
            viewModel.submitAction(Action.AppendElement)
            advanceUntilIdle()
        }

        viewModel.submitAction(Action.IncrementItemOnClick(fakeRepository.element4, 3))
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(7, state.elements[3].value)
    }

    @Test
    fun testActionIncrementItemOnClickFor_First_ElementOfTheList() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1,
            element2,
            element3,
            element4
        )

        for (i in 1..4) {
            viewModel.submitAction(Action.AppendElement)
            advanceUntilIdle()
        }

        viewModel.submitAction(Action.IncrementItemOnClick(element1, 0))
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(1, state.elements[0].value)
    }

    @Test
    fun testActionIncrementRandomElement() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1
        )

        viewModel.submitAction(Action.AppendElement)
        advanceUntilIdle()

        viewModel.submitAction(Action.IncrementRandomElement)
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(2, state.elements[0].value)
    }

    @Test
    fun testActionResetRandomElementCounter() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1
        )

        viewModel.submitAction(Action.AppendElement)
        advanceUntilIdle()

        viewModel.submitAction(Action.ResetRandomElementCounter)
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(0, state.elements[0].value)
    }

    @Test
    fun testActionDeleteRandomElement() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1
        )

        viewModel.submitAction(Action.AppendElement)
        advanceUntilIdle()

        viewModel.submitAction(Action.DeleteRandomElement)
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(0, state.elements.size)
    }

    @Test
    fun testActionAddValueOfAnElementBefore() = runTest {
        whenever(elementRepository.getRandomElement()).thenReturn(
            element1,
            element2
        )

        for (i in 1..2) {
            viewModel.submitAction(Action.AppendElement)
            advanceUntilIdle()
        }

        viewModel.submitAction(Action.AddValueOfAnElementBefore)
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        println(state)
        assertEquals(3, state.elements[1].value)
    }
}