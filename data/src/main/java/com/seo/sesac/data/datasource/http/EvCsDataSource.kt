package com.seo.sesac.data.datasource.http

import com.seo.sesac.data.apimodule.EvCsService

class EvCsDataSource(private val restService: EvCsService) {
    suspend fun getEvCsList(page: Int, perPage: Int, address: String, apiKey: String) =
        restService.getEvCsList(
            page = page, perPage = perPage, address = address, serviceKey = apiKey
        )

}