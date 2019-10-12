package com.bappedamalang.sipelitmotion.model.response

import com.bappedamalang.sipelitmotion.model.MBase
import com.bappedamalang.sipelitmotion.model.MKajian
import java.io.Serializable

class ResponseKajian: MBase(), Serializable {
    var data: List<MKajian> = ArrayList()
}