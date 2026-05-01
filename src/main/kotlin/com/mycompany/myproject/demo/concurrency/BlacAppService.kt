package com.mycompany.myproject.demo.concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking { // This blocks the main thread until all coroutines inside it complete
        var job : Job? = null
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                increaseCount()
                if (count == 10) {
                    printCount()
                    job?.cancel()
                }
            }
        }
        job.join() // Wait for the launched coroutine to complete
    }
}

var count = 0

suspend fun increaseCount(): Int {
    count ++
    return count
}

suspend fun printCount() {
    delay(4000L) // pretend to do something useful here
    println(count)
}