package com.practicum.apimahasiswa.api

import com.practicum.apimahasiswa.model.AddMahasiswaResponse
import com.practicum.apimahasiswa.model.MahasiswaResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("mahasiswa")
    fun getMahasiswa(@Query("nrp") nrp: String?): Call<MahasiswaResponse?>?

    @POST("mahasiswa")
    @FormUrlEncoded
    fun addMahasiswa(
        @Field("nrp") nrp: String?,
        @Field("nama") nama: String?,
        @Field("email") email: String?,
        @Field("jurusan") jurusan: String?
    ): Call<AddMahasiswaResponse?>?

    @GET("mahasiswa")
    fun getAllMahasiswa(): Call<MahasiswaResponse>

}