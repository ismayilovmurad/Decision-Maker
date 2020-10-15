package com.martiandeveloper.decisionmaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private var _eventContinueMBTNClick = MutableLiveData<Boolean>()
    val eventContinueMBTNClick: LiveData<Boolean>
        get() = _eventContinueMBTNClick

}
