package com.seo.sesac.chargenavi.ui.screen.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
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

/**
 * 시작 화면, 메인 화면
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
)
 {

    val context = LocalContext.current

     // Bottom Sheet Scaffold 상태
     val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

     // coroutineScope
     val scope = rememberCoroutineScope()

     BottomSheetScaffold(
         scaffoldState = bottomSheetScaffoldState,
         sheetContent = {
             // Todo: Bottom Sheet 내용
             Text(text = "bottom sheet!")
         }
     ) { innerpadding ->

         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(innerpadding)
         ) {

             // NaverMap
             NaverMapScreen(navController, viewModel, context)

             Column(
                 modifier = Modifier.padding(
                         start = 10.dp, end = 10.dp, top = 10.dp
                     )
             ) {

                 // 검색 창
                 Row(
                     modifier = Modifier.fillMaxWidth().background(
                             color = Color.White, shape = RoundedCornerShape(8.dp)
                         ).padding(
                             vertical = 8.dp, horizontal = 8.dp
                         ).clickable { // 클릭 시 검색 창으로 이동
                             navController.navigate(NavigationRoute.Search.routeName)
                         }, verticalAlignment = Alignment.CenterVertically
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
}

/*
* NaverMap은 효율적인 상태 관리를 위해 분리
* */
@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NaverMapScreen(navController: NavController, viewModel: MainViewModel, context: Context) {

    // 마커 리스트 상태 관리
    var markerStates by remember {
        mutableStateOf<List<MarkerState>>(emptyList())
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
                viewModel.convertCoordsToAddress(currentLatLng.latitude, currentLatLng.longitude)
            } else {
                showToast("현재 위치를 파악할 수 없습니다")
            }
        }
    }

    // 충전소 목록 갱신
    LaunchedEffect(key1 = viewModel) {
        viewModel.address.collectLatest { result ->
            if (result is RestResult.Success) {
                val location = result.data.firstOrNull()

                if (location != null) {
                    viewModel.getEvCsList(addr = location.region.area2.name.trim())
                } else {
                    showToast("검색 결과가 없습니다.")
                }
            }
        }
    }

    // 충전소 마커 상태 저장
    LaunchedEffect(key1 = viewModel) {
        viewModel.evCsList.collectLatest { result ->
            if (result is RestResult.Success) {
                markerStates = addMarkers(result.data.toList())
            }
        }
    }

    // Todo: SearchScreen 에서 검색 시 검색 키워드를 기반으로 카메라 업데이트
    // Todo: Marker 클릭시 DetailBottomSheetScreen 띄우기
    // Naver Map Compose 사용하여 Naver Map 사용
    NaverMap(
        modifier = Modifier
            .fillMaxSize(),
        locationSource = rememberFusedLocationSource(isCompassEnabled = true),
        uiSettings = MapUiSettings(
            // location button 활성
            isLocationButtonEnabled = true,
            isCompassEnabled = true
        ),
        cameraPositionState = cameraPositionState,
        contentPadding = PaddingValues(
            top = 50.dp
        )
    ) {

        // 화면에 마커 표시
        markerStates.forEach { markerState ->
            Marker(
                state = markerState,
                onClick = {
                    val csId = viewModel.findCsIdByCoords(markerState.position)
                    navController.navigate(
                        "${NavigationRoute.Detail.routeName}/${csId}"
                    )
                    true
                }
            )
        }

    }

}

/** 마커 리스트를 반환 */
fun addMarkers(markerList: List<EvCsInfo>): List<MarkerState> = markerList.map { evCsInfo ->
    MarkerState(
        position = LatLng(evCsInfo.latitude.toDouble(), evCsInfo.longitude.toDouble())
    )
}

