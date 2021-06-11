package com.example.converter2.model

import io.reactivex.rxjava3.core.Completable

interface IConverter {
    fun convertionImage(str: String): Completable
}