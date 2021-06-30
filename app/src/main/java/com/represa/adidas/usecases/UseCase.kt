package com.represa.adidas.usecases

interface UseCase<I : Any?, O : Any?> {
    suspend operator fun invoke(param: I): O?
}

interface SynchronousUseCase<I : Any?, O : Any?> {
    operator fun invoke(param: I): O
}