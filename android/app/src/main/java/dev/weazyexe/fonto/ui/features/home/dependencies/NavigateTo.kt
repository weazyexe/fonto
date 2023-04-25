package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.spec.Direction

interface NavigateTo {

    operator fun invoke(direction: Direction)
}