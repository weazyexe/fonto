package dev.weazyexe.fonto.utils.validator

import dev.weazyexe.fonto.common.data.Validator

internal class TitleValidator : Validator<String> {

    override fun validate(value: String): Boolean {
        return value.isNotBlank()
    }
}