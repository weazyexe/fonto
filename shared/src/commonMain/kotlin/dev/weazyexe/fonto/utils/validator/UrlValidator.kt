package dev.weazyexe.fonto.utils.validator

import dev.weazyexe.fonto.common.data.Validator

class UrlValidator : Validator<String> {

    override fun validate(value: String): Boolean {
        return value.isNotBlank()
    }
}