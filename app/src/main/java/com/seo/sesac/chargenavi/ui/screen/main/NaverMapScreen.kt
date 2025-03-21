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
import androidx.compose.runtime.collectAsState
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
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.RouteInfo
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

    // 마커 리스트 상태 관리
    var latLngStates by rememberSaveable {
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
                showToast("현재 위치를 파악할 수 없습니다")
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
                    showToast("검색 결과가 없습니다.")
                }
            }
        }
    }

    // 충전소 마커 상태 저장
    LaunchedEffect(key1 = mainViewModel) {
        mainViewModel.evCsList.collectLatest { result ->
            if (result is RestResult.Success) {
                latLngStates = addMarkers(result.data.toList())
            }
        }
    }

    /** 경로 표시 정보 */
    val routeState = mainViewModel.routeInfo.collectAsState()

    /** 마커 클릭 시 선택 좌표 */
    var selectedLatLng by remember {
        mutableStateOf<LatLng?>(null)
    }

/*    *//** 출발지 좌표 *//*
    var start by remember {
        mutableStateOf("")
    }

    *//** 목적지 좌표 *//*
    var goal by remember {
        mutableStateOf("")
    }*/

    /*if (showButtons) {
    // 마커 클릭 시 띄우는 버튼
    selectedLatLng?.let { latLng ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    val csId = mainViewModel.findByCoords(latLng)
                    navController.navigate(
                        "${NavigationRoute.Detail.routeName}/${csId}"
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = ""
                )
                Text("충전소 상세")
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextButton(
                onClick = {
                    start = "${currentLatLng.longitude},${currentLatLng.latitude}"
                    goal = "${latLng.longitude},${latLng.latitude}"
                    mainViewModel.getRoute(start, goal)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = ""
                )
                Text("경로 안내")
            }
        }
    }
}*/

    val sheetScope = rememberCoroutineScope()

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
        latLngStates.forEach { latLngState ->

            MarkerComposable(
                state = MarkerState(latLngState),
                onClick = {

                    val csId = mainViewModel.findByCoords(latLngState)
                    val csInfo = mainViewModel.findByCsId(csId.toString()).values
                        .flatten()
                        .sortedBy { it.cpId }

                    /*start = "${currentLatLng.longitude},${currentLatLng.latitude}"
                    goal = "${latLngState.longitude},${latLngState.latitude}"
                    mainViewModel.getRoute(start, goal)*/

                    /*val csId = mainViewModel.findByCoords(latLngState)
                    navController.navigate(
                        "${NavigationRoute.Detail.routeName}/${csId}"
                    )*/

                    selectedLatLng = latLngState

                    if (csInfo.isNotEmpty()) {
                        mainViewModel.setCsInfo(csInfo)

                        sheetScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.show()
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
                        contentDescription = "충전소 마커"
                    )
                }

            }

        }

        if (routeState.value is RestResult.Success) {
            val pathList = (routeState.value as RestResult.Success<List<RouteInfo>>).data.first().path
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