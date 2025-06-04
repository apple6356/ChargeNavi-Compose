package com.seo.sesac.chargenavi.ui.screen.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.LocationUtils
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
* NaverMap은 효율적인 상태 관리를 위해 분리
* */
@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NaverMapScreen(
    navController: NavController,
    context: Context,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    mainViewModel: MainViewModel
) {

    // 마커 리스트 상태
    var latLngState by rememberSaveable {
        mutableStateOf<List<LatLng>>(emptyList())
    }

    // 카메라 포지션 상태
    val cameraPositionState = rememberCameraPositionState()

    // 현재 위치 (기본 값 서울 시청)
    var currentLatLng by rememberSaveable {
        mutableStateOf(LatLng(37.5666, 126.979))
    }

    val locationUtils = LocationUtils(context)

    // 현재 위치 좌표 가져오기
    if (currentLatLng == LatLng(37.5666, 126.979)) {
        locationUtils.getCurrentLocation {
            if (it != null) {
                currentLatLng = LatLng(it.latitude, it.longitude)

                // 카메라 포지션 상태 변경
                cameraPositionState.position = CameraPosition(currentLatLng, 13.0)

                // 현재 좌표를 주소로 변환
                mainViewModel.convertCoordsToAddress(currentLatLng.latitude, currentLatLng.longitude)
            } else {
                showToast(R.string.current_location_not_found.toString())
            }
        }
    }

    // 충전소 목록 갱신
    LaunchedEffect(key1 = mainViewModel) {
        mainViewModel.address.collectLatest { result ->
            if (result is RestResult.Success) {
                val location = result.data.firstOrNull()

                if (location != null) {
                    mainViewModel.getEvCsList(addr = location.region.area2.name.trim())
                } else {
                    showToast(R.string.search_result_not_found.toString())
                }
            }
        }
    }

    // 충전소 정보 상태
    val evCsListState = mainViewModel.evCsList.collectAsStateWithLifecycle()

    if (evCsListState.value is RestResult.Success) {
        latLngState = addMarkers((evCsListState.value as RestResult.Success).data.toList())
    }

    // 경로 표시 정보
    val routeState = mainViewModel.routeInfo.collectAsStateWithLifecycle()

    // 마커 클릭 시 선택 좌표
    var selectedLatLng by remember {
        mutableStateOf<LatLng?>(null)
    }

    // 바텀 시트 코루틴 스코프
    val bottomSheetScope = rememberCoroutineScope()

    // Naver Map Compose 사용하여 Naver Map 사용
    NaverMap(
        modifier = Modifier.fillMaxSize(),
        locationSource = rememberFusedLocationSource(isCompassEnabled = true),
        uiSettings = MapUiSettings( // location button 활성
            isLocationButtonEnabled = true, isCompassEnabled = true
        ),
        cameraPositionState = cameraPositionState,
        contentPadding = PaddingValues(
            top = 50.dp
        )
    ) {

        // 화면에 마커 표시
        latLngState.forEach { latLngState ->

            MarkerComposable(
                state = MarkerState(latLngState),
                onClick = {

                    val csId = mainViewModel.findCsIdByCoords(latLngState)
                    val csInfo = mainViewModel.findByCsId(csId.toString()).values
                        .flatten()
                        .sortedBy { it.cpId }

                    selectedLatLng = latLngState

                    if (csInfo.isNotEmpty()) {
                        mainViewModel.setCsInfo(csInfo)

                        bottomSheetScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isVisible) {
                                bottomSheetScaffoldState.bottomSheetState.hide()
                            } else {
                                // 마커 클릭 시 DetailScreen bottom sheet 생성
                                navController.navigate("${NavigationRoute.Detail.routeName}/${csId}") {
                                    popUpTo(navController.graph.id)
                                }

                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }

                true
            }) {

                // 마커 이미지 추가
                Row(
                    modifier = Modifier.fillMaxSize().background(Color.Cyan, CircleShape),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon( // 충전소 마커 아이콘
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.marker_cs_image),
                        contentDescription = null
                    )
                }

            }

        }

        if (routeState.value is RestResult.Success) {
            val pathList = (routeState.value as RestResult.Success).data.first().path
                .map {
                    LatLng(it[1], it[0])
                }

            PathOverlay(
                coords = pathList,
                color = Color.Blue,
                width = 8.dp
            )
        }

    }

}

/** 마커 리스트를 반환 */
fun addMarkers(markerList: List<EvCsInfo>) = markerList.map { evCsInfo ->
    LatLng(evCsInfo.latitude.toDouble(), evCsInfo.longitude.toDouble())
}