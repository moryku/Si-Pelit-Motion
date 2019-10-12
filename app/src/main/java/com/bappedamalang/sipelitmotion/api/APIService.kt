package com.ezyindustries.pos.api

import com.bappedamalang.sipelitmotion.model.response.ResponseCategory
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.bappedamalang.sipelitmotion.model.response.ResponseLoginRegister
import com.bappedamalang.sipelitmotion.model.response.ResponseSubmitKajian
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("kajian/")
    fun getAllKajian(): Call<ResponseKajian>

    @GET("category")
    fun getAllKategory(): Call<ResponseCategory>

    @Multipart
    @POST("category")
    fun submitKajian(): Call<ResponseSubmitKajian>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email")  email: String, @Field("password") password: String): Call<ResponseLoginRegister>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("email")  email: String,
                 @Field("password") password: String,
                 @Field("institusi")  institusi: String,
                 @Field("nama")  nama: String,
                 @Field("address")  address: String
                 ): Call<ResponseLoginRegister>
}