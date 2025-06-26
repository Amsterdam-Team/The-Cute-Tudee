package com.amsterdam.cutetudee.presentation.utils.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val Main: CoroutineDispatcher
    val Default : CoroutineDispatcher
    val IO : CoroutineDispatcher
}

