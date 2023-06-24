package dev.weazyexe.fonto.common.serialization

import nl.adaptivity.xmlutil.serialization.XML

fun createXml(): XML = XML {
    recommended {
        defaultPolicy {
            ignoreUnknownChildren()
        }
    }
}
