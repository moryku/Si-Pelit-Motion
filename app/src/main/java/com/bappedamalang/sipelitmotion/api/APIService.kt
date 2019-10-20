package com.ezyindustries.pos.api

import com.bappedamalang.sipelitmotion.model.response.ResponseCategory
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.bappedamalang.sipelitmotion.model.response.ResponseLoginRegister
import com.bappedamalang.sipelitmotion.model.response.ResponseSubmitKajian
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.http.GET



interface APIService {

    @GET("kajian/")
    fun getAllKajian(): Call<ResponseKajian>

    @GET("kajian/user/{userId}")
    fun getAllKajianUser(@Path("userId") userId: String): Call<ResponseKajian>

    @GET("category")
    fun getAllKategory(): Call<ResponseCategory>

    @Multipart
    @POST("kajian/add")
    fun submitKajian(@PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
                     @Part document: MultipartBody.Part,
                     @Part cover: MultipartBody.Part ): Call<ResponseSubmitKajian>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email")  email: String, @Field("password") password: String): Call<ResponseLoginRegister>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("email")  email: String,
                 @Field("password") password: String,
                 @Field("institusi")  institusi: String,
                 @Field("name")  nama: String,
                 @Field("address")  address: String,
                 @Field("phone")  noHp: String
                 ): Call<ResponseLoginRegister>

    @GET
    fun downloadPDF(@Url fileUrl: String): Call<ResponseBody>
}