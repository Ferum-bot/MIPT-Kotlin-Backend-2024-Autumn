package com.mipt.kotlin.coroutines.practice.services.impl

import com.mipt.kotlin.coroutines.practice.di.DependencyProvider
import com.mipt.kotlin.coroutines.practice.services.PracticeService
import kotlinx.coroutines.*

class PracticeServiceDefault: PracticeService {

    private val remoteExceptionHandler = CoroutineExceptionHandler { context, exception ->
        val name = context[CoroutineName.Key]?.name
        println("Exception received ${exception.message} in coroutine $name")
    }

    private val remoteClient = DependencyProvider.provideRemoteClient()

    private val remoteScope = CoroutineScope(Dispatchers.IO + remoteExceptionHandler + SupervisorJob())

    override suspend fun startWork() {
        val identifiers = DependencyProvider.provideRemoteDataIdentifiers()

        val jobs = mutableListOf<Job>()
        for (identifier in identifiers) {
            val coroutineName = CoroutineName("Coroutine $identifier")
            val job = remoteScope.launch(coroutineName) {
                val remoteData = remoteClient.loadRemotePayload(identifier)
                println("Received remote data with id: ${remoteData.identifier}")
            }

            jobs.add(job)
        }

        jobs.joinAll()
    }
}