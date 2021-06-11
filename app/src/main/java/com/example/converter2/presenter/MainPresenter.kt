package com.example.converter2.presenter

import com.example.converter2.model.IConverter
import com.example.converter2.view.MainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class MainPresenter(
    private val converter: IConverter,
    private val thread: Scheduler
) : MvpPresenter<MainView>() {

    val compositeDisposable: CompositeDisposable? = null

    fun starMain() {
        viewState.startMain()
    }

    fun convertionImage(str: String) {
        val disposable = converter.convertionImage(str)
            .observeOn(thread)
            .doOnSubscribe { viewState.showDialog() }
            .subscribe({
                viewState.hideDialog()
                viewState.showResult()
            }, {
                viewState.hideDialog()
                viewState.showError()
            })
        compositeDisposable?.add(disposable)
    }

    fun dismiss() {
        compositeDisposable?.dispose()
        viewState.hideDialog()
        viewState.showInterrupted()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }
}