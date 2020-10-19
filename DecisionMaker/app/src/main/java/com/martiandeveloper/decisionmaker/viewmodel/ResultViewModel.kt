package com.martiandeveloper.decisionmaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private var _decide = MutableLiveData<String>()
    val decide: LiveData<String>
        get() = _decide

    private var _eventTryAgainMBTNClick = MutableLiveData<Boolean>()
    val eventTryAgainMBTNClick: LiveData<Boolean>
        get() = _eventTryAgainMBTNClick

    fun onTryAgainMBTNClick() {
        _eventTryAgainMBTNClick.value = true
    }

    fun onTryAgainMBTNClickComplete() {
        _eventTryAgainMBTNClick.value = false
    }

    fun setDecide(text: String) {
        _decide.value = text
    }

}
