package com.daftmobile.listtap.presentation.maincontent

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.daftmobile.listtap.R
import com.daftmobile.listtap.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class MainContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun startStopActionTest() {

        with(composeTestRule) {

            val startString = composeTestRule.activity.getString(R.string.start).uppercase()
            val stopString = composeTestRule.activity.getString(R.string.stop).uppercase()

            val buttonStart = composeTestRule.onNode(
                hasText(startString)
                        or
                        hasText(stopString)
            )

            setContent { MainContent() }

            buttonStart.performClick()
            onNodeWithText(stopString).assertExists()
            onNodeWithText(startString).assertDoesNotExist()

            // test that 5 elements of the list are displayed
            waitUntil(6000) {
                onAllNodes(
                    hasParent(hasScrollAction())
                            and
                            hasClickAction()
                ).fetchSemanticsNodes().size > 4
            }

            buttonStart.performClick()
            onNodeWithText(startString).assertExists()
            onNodeWithText(stopString).assertDoesNotExist()
        }

    }

}