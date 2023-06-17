package dev.weazyexe.fonto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.features.app.AppPresentation

class AppViewModel(
    private val presentation: AppPresentation
) : ViewModel() {

    val state = presentation.domainState
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }
}