package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination

interface ManageFeedResults {

    fun invoke() : ResultRecipient<ManageFeedScreenDestination, Boolean>
}