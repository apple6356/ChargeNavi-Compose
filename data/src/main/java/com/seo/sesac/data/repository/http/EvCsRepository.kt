package com.seo.sesac.data.repository.http

import com.seo.sesac.data.datasource.http.EvCsDataSource

class EvCsRepository(private val evCsDataSource: EvCsDataSource) {

    suspend fun getEvCsList(page: Int, perPage: Int, address: String, apiKey: String) =
        evCsDataSource.getEvCsList(page, perPage, address, apiKey)

}