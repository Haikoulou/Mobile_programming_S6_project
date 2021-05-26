package com.example.elouanproject.api

import com.example.elouanproject.model.DealsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConnDeals {
    @GET("api/1.0/deals?storeID=1&upperPrice=5")
    fun getDealsItem(@Query("title") title:String,
        @Query("lowerPrice") lowerPrice:Int,
        @Query("upperPrice") upperPrice:Int
    ): Call<List<DealsItem>>
}