package com.example.movie.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object NetworkUtils {
    fun <T> fetchData(
        scope: CoroutineScope,
        onLoading: () -> Unit,
        onComplete: (List<T>) -> Unit,
        onError: () -> Unit,
        fetch: suspend () -> List<T>
    ) {
        scope.launch {
            try {
                onLoading()
                val data = fetch()
                onComplete(data)
            } catch (e: Exception) {
                onError()
            }
        }
    }
}