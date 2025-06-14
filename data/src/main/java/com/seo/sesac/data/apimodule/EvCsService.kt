package com.seo.sesac.data.apimodule

import com.seo.sesac.data.entity.EvCsResponse
import retrofit2.http.GET
import retrofit2.http.Query


/** Rest Service */
interface EvCsService {

    @GET("EvInfoServiceV2/v1/getEvSearchList")
    suspend fun getEvCsList(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("returnType") returnType: String = "JSON",
        @Query("cond[addr::LIKE]") address: String,
        @Query("serviceKey") serviceKey: String
    ): EvCsResponse


}