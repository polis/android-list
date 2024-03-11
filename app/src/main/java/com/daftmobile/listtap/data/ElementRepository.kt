package com.daftmobile.listtap.data

import com.daftmobile.listtap.data.model.Element

// In real life we usually use get initial data from the data sources. And then use repository to combine data sources and provide them to usecases or viewmodels.
// Here is just showcase how to get random item from the repository. But for this particular app we can do it in the viewmodel.
// So Data layer is not necessary in this application but I created it to show how repository can be used in general.
// Additionally repository should avoid to implement business logic and should be used only to get data from data sources.
interface ElementRepository {
    suspend fun getRandomElement(): Element
}