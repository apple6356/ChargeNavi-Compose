package com.seo.sesac.chargenavi

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.ChargeNaviTheme
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationItem
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.navigation.favoriteNavGraph
import com.seo.sesac.chargenavi.ui.navigation.mainNavGraph
import com.seo.sesac.chargenavi.ui.navigation.myPageNavGraph
import com.seo.sesac.chargenavi.ui.screen.main.MainScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.MyPageScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkMyAppPermissionList()

        enableEdgeToEdge()
        setContent {
            StartApp()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setContent {
            StartApp()
        }
    }

    // permission 얻은 이후 진행 할 코드듣 작성
    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Log.e("MainActivity", "1모든 권한이 허용되었습니다.")
            showToast("위치 정보 권한이 허용되었습니다")
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            deniedPermissions?.let {
                Log.e("MainActivity", it.toString())
            }
            showToast("모든 권한을 허용해야 앱의 기능을 사용할 수 있습니다")
            finish()
        }
    }

    /** permission 체크 */
    private fun checkMyAppPermissionList() {
        TedPermission.create().setPermissionListener(permissionListener)
            .setRationaleMessage("본 앱을 사용하기 위한 권한을 허락해 주세요!").setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ).check()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StartApp() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)

    // bottomSheetScaffold 상태
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden, skipHiddenState = false // 완전 숨김 상태 허용
        )
    )

    val bottomSheetScope = rememberCoroutineScope()

    val naviItem by remember {
        derivedStateOf {
            if (!bottomSheetScaffoldState.bottomSheetState.isVisible) {
                NavigationRoute.Main.routeName
            } else {
                currentDestination?.route
            }
        }
    }

    ChargeNaviTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = { // bottom sheet 내용
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoute.MyPage.routeName,
                    modifier = Modifier.padding(0.dp)
                ) {
                    composable(route = NavigationRoute.MyPage.routeName) {
                        MyPageScreen(navController = navController, bottomSheetScaffoldState = bottomSheetScaffoldState)
                    }
                    mainNavGraph(navController, bottomSheetScaffoldState, mainViewModel)
                    favoriteNavGraph(navController, bottomSheetScaffoldState)
                    myPageNavGraph(navController, bottomSheetScaffoldState)
                }
            },
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(), bottomBar = {
                    NavigationBar(
                        containerColor = Color.White, contentColor = Color.Blue
                    ) {
                        NavigationItem().settingBottomNavigationItems()
                            .forEachIndexed { _, navigationItem ->
                                NavigationBarItem(
                                    selected = navigationItem.route == naviItem,
                                    label = {
                                        Text(
                                            text = navigationItem.tabName,
                                            color = if (navigationItem.route == naviItem) {
                                                Color.Blue
                                            } else {
                                                Color.Black
                                            }
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = navigationItem.icon,
                                            contentDescription = null,
                                            tint = if (navigationItem.route == naviItem) {
                                                Color.Blue
                                            } else {
                                                Color.Black
                                            }
                                        )
                                    },
                                    onClick = {
                                        Log.e("navigation check", "${navigationItem.route}, ${currentDestination?.route}, $naviItem")

                                        if (navigationItem.route == NavigationRoute.Main.routeName) {
                                            // 지도 탭 클릭 시 바텀시트 숨김
                                            bottomSheetScope.launch {
                                                if (bottomSheetScaffoldState.bottomSheetState.isVisible) {
                                                    bottomSheetScaffoldState.bottomSheetState.hide()
                                                }
                                            }
                                        } else {
                                            // 즐겨찾기, MY 탭 클릭 시 바텀시트 올림
                                            bottomSheetScope.launch {
                                                navController.navigate(navigationItem.route) {
                                                    popUpTo(navController.graph.id)
                                                }
                                                if (!bottomSheetScaffoldState.bottomSheetState.isVisible) {
                                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                                }
                                            }
                                        }
                                    })
                            }
                    }
                    
                }) { innerPadding ->

                MainScreen(
                    navController = navController,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    modifier = Modifier.padding(innerPadding),
                    mainViewModel = mainViewModel
                )
            }
        }
    }


}

