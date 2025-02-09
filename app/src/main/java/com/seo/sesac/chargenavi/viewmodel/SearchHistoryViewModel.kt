package com.seo.sesac.chargenavi.viewmodel

import androidx.lifecycle.ViewModel
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.SearchHistory
import com.seo.sesac.firestore.datasource.SearchHistoryDataSourceImpl
import com.seo.sesac.firestore.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow

class SearchHistoryViewModel(
    private val searchHistoryRepository: SearchHistoryRepository = SearchHistoryRepository(SearchHistoryDataSourceImpl())
): ViewModel() {

    val _searchHistory =
        MutableStateFlow<FireResult<MutableList<SearchHistory>>>(FireResult.DummyConstructor)
    val searchHistory get() = _searchHistory

}