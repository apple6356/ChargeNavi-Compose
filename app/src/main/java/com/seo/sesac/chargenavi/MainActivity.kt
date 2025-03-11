package com.seo.sesac.chargenavi

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.ChargeNaviTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationItem
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.navigation.favoriteNavGraph
import com.seo.sesac.chargenavi.ui.navigation.mainNavGraph
import com.seo.sesac.chargenavi.ui.navigation.myPageNavGraph
import com.seo.sesac.chargenavi.ui.screen.main.BottomSheetDetailScreen
import com.seo.sesac.chargenavi.ui.screen.main.MainScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkMyAppPermissionList()

        enableEdgeToEdge()
        setContent {
            StartApp()
        }
    }

    // permission 얻은 이후 진행 할 코드듣 작성
    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Log.e("MainActivity", "모든 권한이 허용되었습니다.")
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
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage("본 앱을 사용하기 위한 권한을 허락해 주세요!")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
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
            initialValue = SheetValue.Hidden,
            skipHiddenState = false // 완전 숨김 상태 허용
        )
       /* bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )*/
    )

    ChargeNaviTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                // bottom sheet 내용
                BottomSheetDetailScreen(
                    navController,
                    bottomSheetScaffoldState,
                    mainViewModel
                )
            },
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.White,
                        contentColor = Color.Blue
                    ) {
                        NavigationItem().settingBottomNavigationItems()
                            .forEachIndexed { _, navigationItem ->
                                NavigationBarItem(
                                    selected = navigationItem.route == currentDestination?.route,
                                    label = {
                                        Text(
                                            text = navigationItem.tabName,
                                            color = if (navigationItem.route == currentDestination?.route) {
                                                Color.Blue
                                            } else {
                                                Color.Black
                                            }
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = navigationItem.icon,
                                            contentDescription = navigationItem.tabName,
                                            tint = if (navigationItem.route == currentDestination?.route) {
                                                Color.Blue
                                            } else {
                                                Color.Black
                                            }
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(navigationItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                    }
                }
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = NavigationRoute.Main.routeName, // 메인을 시작 탭으로 설정
                    modifier = Modifier.padding(paddingValues = innerPadding)
                ) {
                    composable(NavigationRoute.Main.routeName) {
                        MainScreen(
                            navController,
                            bottomSheetScaffoldState = bottomSheetScaffoldState,
                            mainViewModel = mainViewModel
                        )
                    }
                    /**
                     * Navigation Graph 를 Custom 분할 함수
                     */
                    mainNavGraph(navController, bottomSheetScaffoldState, mainViewModel)
                    favoriteNavGraph(navController)
                    myPageNavGraph(navController)
                }

            }
        }
    }


}

