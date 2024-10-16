package com.mipt.kotlin.coroutines.practice

import com.mipt.kotlin.coroutines.practice.services.PracticeService
import com.mipt.kotlin.coroutines.practice.services.impl.PracticeServiceDefault
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val service: PracticeService = PracticeServiceDefault()

    runBlocking {
        service.startWork()
    }
}