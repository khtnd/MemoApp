package com.example.memoapp.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.memoapp.BR

data class NewFileActivityBindingVo(
    private var _okEnabled: Boolean = false,
    private var _cancelEnabled: Boolean = false,
    private var _inputText: String = "",
    private var _showError: Boolean = false
) : BaseObservable() {

    @get:Bindable
    var okEnabled: Boolean
        get() = _okEnabled
        set(value) {
            _okEnabled = value
            notifyPropertyChanged(BR.okEnabled)
        }

    @get:Bindable
    var cancelEnabled: Boolean
        get() = _cancelEnabled
        set(value) {
            _cancelEnabled = value
            notifyPropertyChanged(BR.cancelEnabled)
        }

    @get:Bindable
    var inputText: String
        get() = _inputText
        set(value) {
            _inputText = value
            notifyPropertyChanged(BR.inputText)
        }

    @get:Bindable
    var showError: Boolean
        get() = _showError
        set(value) {
            _showError = value
            notifyPropertyChanged(BR.showError)
        }
}
