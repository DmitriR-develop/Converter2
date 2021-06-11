package com.example.converter2.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun startMain()

    fun showDialog()

    fun hideDialog()

    fun showResult()

    fun showError()

    fun showInterrupted()

}