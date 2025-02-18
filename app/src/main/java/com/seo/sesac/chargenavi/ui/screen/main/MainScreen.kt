package com.seo.sesac.chargenavi.ui.screen.main

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.LocationUtils
import com.seo.sesac.chargenavi.common.NaverOAuth
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 시작 화면, 메인 화면
 * */
@Composable
fun MainScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (NaverOAuth.isLoggedIn()) {
            Log.e("NaverOAuth", "로그인 된 상태")
            NaverOAuth.getProfile { result ->
                // 네이버 로그인 되어있으면
                userViewModel.findById(result.profile?.id.toString())
            }
        } else {
            Log.e("NaverOAuth", "로그인 되지 않은 상태")
            NaverOAuth.logout()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // NaverMap
        NaverMapScreen(navController, context)

        Column(
            modifier = Modifier
                .padding(
                    start = 10.dp, end = 10.dp, top = 10.dp
                )
        ) {

            // 검색 창
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        vertical = 8.dp, horizontal = 8.dp
                    )
                    .clickable { // 클릭 시 검색 창으로 이동
                        navController.navigate(NavigationRoute.Search.routeName)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) { // 검색 아이콘
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
                Text(
                    text = stringResource(R.string.search_placeholder),
                    fontSize = 15.sp,
                    color = Color.LightGray
                )
            }


        }

    }

}



