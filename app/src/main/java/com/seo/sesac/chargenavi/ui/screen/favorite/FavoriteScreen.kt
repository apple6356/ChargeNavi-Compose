package com.seo.sesac.chargenavi.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.ui.screen.favorite.list.FavoriteListScreen

/**
 * 즐겨찾기 화면,
 * 즐겨찾기 된 충전소 목록을 보여주고,
 * 찾는 충전소의 중요 정보(이름, 주소, 단자 타입, 충전 가능 상태 등)를 목록에서 보여 준다
 * */
@Composable
fun FavoriteScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )
        ) {
            // 뒤로가기 버튼
            // 즐겨찾기 탭에 뒤로가기 버튼이 필요 한지?
            /*IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "뒤로가기"
                )
            }*/

            Text(
                text = "즐겨찾기",
                fontSize = 30.sp
            )

            // 임시
            val favoriteList = mutableListOf("1", "2", "3", "4", "5")
            // 즐겨찾기 된 충전소 리스트
            FavoriteListScreen(favoriteList)

        }
    }
}