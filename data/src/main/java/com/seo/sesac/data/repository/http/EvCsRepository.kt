package com.seo.sesac.data.repository.http

import com.seo.sesac.data.datasource.http.EvCsDataSourceImpl

class EvCsRepository(private val evCsDataSource: EvCsDataSourceImpl) {

    suspend fun getEvCsList(page: Int, perPage: Int, address: String, apiKey: String) =
        evCsDataSource.getEvCsList(page, perPage, address, apiKey)

}