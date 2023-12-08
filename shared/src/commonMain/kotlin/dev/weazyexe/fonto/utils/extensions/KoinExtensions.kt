package dev.weazyexe.fonto.utils.extensions

import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope

inline fun <reified T> Scope.get(params: ParametersHolder): T = get(parameters = { params })
