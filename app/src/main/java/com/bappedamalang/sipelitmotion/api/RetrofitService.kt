package com.ezyindustries.pos.api

import com.bappedamalang.sipelitmotion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {

    companion object {

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }
}