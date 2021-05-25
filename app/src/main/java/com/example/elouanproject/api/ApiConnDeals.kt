package com.example.elouanproject.api

import com.example.elouanproject.model.DealsItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiConnDeals {
    @GET("api/1.0/deals?storeID=1&upperPrice=15")
    fun getDealsItem(): Call<List<DealsItem>>
}