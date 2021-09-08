package utils

import kotlinx.coroutines.*
import kotlin.coroutines.cancellation.CancellationException

open class ViewModel() {
    val viewModelScope = MainScope() + CoroutineName("$javaClass") + Dispatchers.IO
    fun close() {
        viewModelScope.cancel(EndViewModel())
    }

    inner class EndViewModel() : CancellationException()
}