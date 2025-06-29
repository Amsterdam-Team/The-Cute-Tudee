package com.amsterdam.cutetudee.utils

import com.amsterdam.cutetudee.presentation.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher


class TestDispatcherProvider : DispatcherProvider {

    override val Main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val Default: CoroutineDispatcher
        get() = Dispatchers.Main
    override val IO: CoroutineDispatcher
        get() = Dispatchers.Main

}