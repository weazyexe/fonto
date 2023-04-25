package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.spec.Direction

interface NavigateWithResult {

    operator fun invoke(direction: Direction)
}