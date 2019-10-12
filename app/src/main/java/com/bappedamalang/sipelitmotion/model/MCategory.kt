package com.bappedamalang.sipelitmotion.model

class MCategory: MBase() {

    var id: Int = 0
    var nama: String = ""

    override fun toString(): String {
        return this.nama
    }
}