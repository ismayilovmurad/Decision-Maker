package com.martiandeveloper.decisionmaker.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var questionET = MutableLiveData<String>()

    private var _eventOptionMBTNClick = MutableLiveData<Boolean>()
    val eventOptionMBTNClick: LiveData<Boolean>
        get() = _eventOptionMBTNClick

    private var _eventDecideMBTNClick = MutableLiveData<Boolean>()
    val eventDecideMBTNClick: LiveData<Boolean>
        get() = _eventDecideMBTNClick

    var optionET = MutableLiveData<String>()

    private var _eventDoneMBTNClick = MutableLiveData<Boolean>()
    val eventDoneMBTNClick: LiveData<Boolean>
        get() = _eventDoneMBTNClick

    private var _eventCancelMBTNClick = MutableLiveData<Boolean>()
    val eventCancelMBTNClick: LiveData<Boolean>
        get() = _eventCancelMBTNClick

    private var _isOptionDialogShowing = MutableLiveData<Boolean>()
    val isOptionDialogShowing: LiveData<Boolean>
        get() = _isOptionDialogShowing

    private var _optionsMTV = MutableLiveData<String>()
    val optionsMTV: LiveData<String>
        get() = _optionsMTV

    private var _options = MutableLiveData<ArrayList<String>>()
    val options: LiveData<ArrayList<String>>
        get() = _options

    private var _isLoadingDialogShowing = MutableLiveData<Boolean>()
    val isLoadingDialogShowing: LiveData<Boolean>
        get() = _isLoadingDialogShowing

    private var _view = MutableLiveData<View>()
    val view: LiveData<View>
        get() = _view


    init {
        _isOptionDialogShowing.value = false

        _options.value = ArrayList()
    }


    fun onOptionMBTNClick() {
        _eventOptionMBTNClick.value = true
    }

    fun onOptionMBTNClickComplete() {
        _eventOptionMBTNClick.value = false
    }

    fun onDecideMBTNClick() {
        _eventDecideMBTNClick.value = true
    }

    fun onDecideMBTNClickComplete() {
        _eventDecideMBTNClick.value = false
    }

    fun onDoneMBTNClick() {
        _eventDoneMBTNClick.value = true
    }

    fun onDoneMBTNClickComplete() {
        _eventDoneMBTNClick.value = false
    }

    fun onCancelMBTNClick() {
        _eventCancelMBTNClick.value = true
    }

    fun onCancelMBTNClickComplete() {
        _eventCancelMBTNClick.value = false
    }

    fun setIsOptionDialogShowing(show: Boolean) {
        _isOptionDialogShowing.value = show
    }

    fun setOptionsMTVText(text: String) {
        _optionsMTV.value = text
    }

    fun addOption(text: String) {
        _options.value?.add(text)
    }

    fun resetOptionET() {
        optionET.value = ""
    }

    fun setIsLoadingDialogShowing(show: Boolean) {
        _isLoadingDialogShowing.value = show
    }

    fun setView(view: View) {
        _view.value = view
    }

}
