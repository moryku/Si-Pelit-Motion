package com.bappedamalang.sipelitmotion.model.response

import com.bappedamalang.sipelitmotion.model.MBase
import com.bappedamalang.sipelitmotion.model.MKajian
import java.io.Serializable

class ResponseAddKajian : MBase(), Serializable {
    var data: MKajian? = null
}