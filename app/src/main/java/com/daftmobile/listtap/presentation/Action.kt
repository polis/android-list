package com.daftmobile.listtap.presentation

import com.daftmobile.listtap.data.model.Element

sealed class Action {

    object Start : Action()
    data class IncrementItemOnClick(val item: Element, val index: Int) : Action()
    object AppendElement : Action()
    object IncrementRandomElement : Action()
    object ResetRandomElementCounter : Action()
    object DeleteRandomElement : Action()
    object AddValueOfAnElementBefore : Action()
}



