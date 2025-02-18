package com.seo.sesac.chargenavi.ui.screen.main.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.seo.sesac.chargenavi.R

/*
* 검색 기록 목록 화면
* */
@Composable
fun SearchListScreen(searchHistory: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
    ) {
        items(searchHistory) { query ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White
                    )
                    .clickable {
                        // 클릭 시 query 값으로 검색 실행, 파라미터로 람다를 받아서?
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 이전 검색 기록
                Text(
                    text = query,
                    fontSize = 15.sp
                )

                // 검색 기록 삭제 버튼
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.search_delete_button)
                    )
                }
            }

            HorizontalDivider()
        }
    }
}