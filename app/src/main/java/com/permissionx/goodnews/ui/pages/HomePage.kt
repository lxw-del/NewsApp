package com.permissionx.goodnews.ui.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.permissionx.goodnews.R
import com.permissionx.goodnews.ui.BottomBarView
import com.permissionx.goodnews.ui.pages.PageConstant.COLLECTION_ITEM
import com.permissionx.goodnews.ui.pages.PageConstant.HOME_ITEM
import com.permissionx.goodnews.utils.ToastUtils.showToast
import com.permissionx.goodnews.viewModel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePage(mNavController: NavHostController,homeViewModel: HomeViewModel){
    val navController = rememberAnimatedNavController()

    //Scaffold中要打开抽屉布局，需要使用Scaffold中的drawerState，需要通过协程或挂起函数更改
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val drawerState = scaffoldState.drawerState
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {mNavController.navigate(PageConstant.EPIDEMIC_NEWS_LIST_PAGE)}) {
                        Icon(imageVector = Icons.Default.Sick, contentDescription = "疫情")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerContent = { DrawerView()},//抽屉的内容，需要将抽屉页面在这里调用
        bottomBar = {
            BottomBarView(navController)
        }
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = HOME_ITEM,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = {fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            }
        ){
            composable(HOME_ITEM){
                HomeItem(mNavController,homeViewModel)
            }
            composable(COLLECTION_ITEM){
                CollectionItem()
            }
        }
    }
}