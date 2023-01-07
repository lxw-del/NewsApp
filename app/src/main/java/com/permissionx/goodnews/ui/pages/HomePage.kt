package com.permissionx.goodnews.ui.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
import com.permissionx.goodnews.viewModel.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePage(mNavController: NavHostController,homeViewModel: HomeViewModel){
    val navController = rememberAnimatedNavController()
    Scaffold(
        topBar = {
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
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
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