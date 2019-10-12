package com.bappedamalang.sipelitmotion.model.response

import com.bappedamalang.sipelitmotion.model.MBase
import com.bappedamalang.sipelitmotion.model.MKajian
import com.bappedamalang.sipelitmotion.model.MUser
import java.io.Serializable

class ResponseLoginRegister : MBase(), Serializable {
    var data: MUser? = null
}