package com.bappedamalang.sipelitmotion.model

import java.io.Serializable

open class MBase: Serializable {
    var status: Boolean = false
    var message: String = ""
    var code: Int = 0
}