package com.martiandeveloper.decisionmaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _eventContinueMBTNClick = MutableLiveData<Boolean>()
    val eventContinueMBTNClick: LiveData<Boolean>
        get() = _eventContinueMBTNClick

}
