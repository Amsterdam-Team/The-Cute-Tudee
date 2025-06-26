package com.amsterdam.cutetudee.utils

import com.amsterdam.cutetudee.presentation.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher


class TestDispatcherProvider : DispatcherProvider {
    val testDispatcher = StandardTestDispatcher()
    override val Main: CoroutineDispatcher
        get() = testDispatcher
    override val Default: CoroutineDispatcher
        get() = testDispatcher
    override val IO: CoroutineDispatcher
        get() = testDispatcher

}