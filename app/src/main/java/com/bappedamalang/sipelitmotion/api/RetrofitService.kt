package com.ezyindustries.pos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {

    companion object {

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://remap.id/v1beta/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }
}