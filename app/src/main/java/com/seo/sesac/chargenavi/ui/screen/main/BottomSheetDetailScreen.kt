package com.seo.sesac.chargenavi.ui.screen.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.LocationUtils
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDetailScreen(
    navController: NavController,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    mainViewModel: MainViewModel,
) {

    // 현재 충전소 정보
    val csInfo by mainViewModel.csInfo.collectAsStateWithLifecycle()

    // 현재 위치 (기본 값 서울 시청)
    var currentLatLng by rememberSaveable {
        mutableStateOf(LatLng(37.5666, 126.979))
    }

    val locationUtils = LocationUtils(LocalContext.current)

    // 현재 위치 좌표 가져오기
    if (currentLatLng == LatLng(37.5666, 126.979)) {
        locationUtils.getCurrentLocation {
            if (it != null) {
                currentLatLng = LatLng(it.latitude, it.longitude)

                // 현재 좌표를 주소로 변환
                mainViewModel.convertCoordsToAddress(currentLatLng.latitude, currentLatLng.longitude)
            } else {
                showToast(R.string.current_location_not_found.toString())
            }
        }
    }

    // 바텀 시트 코루틴 스코프
    val bottomSheetScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // 충전소 이름
                csInfo.first().run {
                    Text(
                        text = csNm, fontSize = 30.sp, fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val goalLatLng = csInfo.first().let { LatLng(it.latitude.toDouble(), it.longitude.toDouble()) }

                TextButton(
                    onClick = {
                        val csIdByCoords = goalLatLng.let { mainViewModel.findCsIdByCoords(it) }

                        navController.navigate(
                            "${NavigationRoute.Detail.routeName}/${csIdByCoords}"
                        )

                        bottomSheetScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null
                    )
                    Text(stringResource(R.string.charge_station_detail))
                }

                TextButton(
                    onClick = {
                        val start = "${currentLatLng.longitude},${currentLatLng.latitude}"
                        val goal = "${goalLatLng.longitude},${goalLatLng.latitude}"
                        mainViewModel.getRoute(start, goal)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null
                    )
                    Text(stringResource(R.string.route_guide))
                }
            }
        }
    }
}