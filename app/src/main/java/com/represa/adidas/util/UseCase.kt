package com.represa.adidas.util

interface UseCase<I : Any?, O : Any?> {
    suspend operator fun invoke(param: I): O?
}

interface SynchronousUseCase<I : Any?, O : Any?> {
    operator fun invoke(param: I): O
    fun dispose() {}
}