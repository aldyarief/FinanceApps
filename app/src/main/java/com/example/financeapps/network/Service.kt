package com.example.financeapps.network

import retrofit2.Call
import com.example.financeapps.data.*
import retrofit2.http.*

interface Service {

    @FormUrlEncoded
    @POST("myfinance/showlogin.php?")
    fun getShowLogin(@Field("username") username : String,
                     @Field("password") password : String): Call<Login>

   @GET("myfinance/showkat.php?")
    fun getDataKategori(): Call<Kategori>

    @GET("myfinance/showkategori.php?")
    fun getDataKat(): Call<ShowKategori>

    @GET("myfinance/showkattrans.php?")
    fun getDataKattrans(): Call<Kattrans>

    @GET("myfinance/showearnings.php?")
    fun getTotalEarnings(): Call<ShowEarnings>

    @GET("myfinance/showtrans.php?")
    fun getDatatrans(): Call<ShowTrans>

    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getInsertKat(@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>


    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getEditKat  (@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>
    @FormUrlEncoded
    @POST("myfinance/kategori.php?")
    fun getDeleteKat(@Field("action") action : String,
                     @Field("nama") password : String,
                     @Field("kattrans") kattrans : String,
                     @Field("katid") katid : String): Call<CrudKategori>

    @FormUrlEncoded
    @POST("myfinance/transaksi.php?")
    fun getInsertTrans( @Field("action") action : String,
                        @Field("namkat") namkat : String,
                        @Field("jml") jml : String,
                        @Field("tgl") tgl: String,
                        @Field("idtrans") idtrans : String): Call<CrudTrans>


    @FormUrlEncoded
    @POST("myfinance/transaksi.php?")
    fun getDeleteTrans( @Field("action") action : String,
                        @Field("namkat") namkat : String,
                        @Field("jml") jml : String,
                        @Field("tgl") tgl: String,
                        @Field("idtrans") idtrans: String): Call<CrudTrans>

    @FormUrlEncoded
    @POST("myfinance/showdashboard.php?")
    fun getTrans( @Field("action") action : String): Call<ShowTrans>

}