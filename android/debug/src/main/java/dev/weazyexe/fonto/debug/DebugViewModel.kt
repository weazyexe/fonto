package dev.weazyexe.fonto.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.features.debug.DebugPresentation

class DebugViewModel(
    private val presentation: DebugPresentation
) : ViewModel() {

    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun addMockFeeds() {
        presentation.addMockFeeds()
    }

    fun addPartialInvalidMockFeeds() {
        presentation.addPartialInvalidMockFeeds()
    }

    fun addInvalidMockFeeds() {
        presentation.addInvalidMockFeeds()
    }

    fun deletePosts() {
        presentation.deleteAllPosts()
    }
}