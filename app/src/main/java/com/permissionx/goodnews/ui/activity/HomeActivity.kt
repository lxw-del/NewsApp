package com.permissionx.goodnews.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.permissionx.goodnews.ui.activity.ui.theme.GoodNewsTheme
import com.permissionx.goodnews.ui.pages.EpidemicNewsListPage
import com.permissionx.goodnews.ui.pages.HomePage
import com.permissionx.goodnews.ui.pages.PageConstant.EPIDEMIC_NEWS_LIST_PAGE
import com.permissionx.goodnews.ui.pages.PageConstant.HOME_PAGE
import com.permissionx.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.permissionx.goodnews.ui.pages.PageConstant.WEB_VIEW_PAGE
import com.permissionx.goodnews.ui.pages.RiskZoneDetailsPage
import com.permissionx.goodnews.ui.pages.WebViewPage
import com.permissionx.goodnews.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    //使用导航动画需要加这个注解
    /**
     * @ExperimentalAnimationApi❌
        这个注释的意思是——“这是一个实验性的 API，所有用户都必须明确选择使用”。应用程序开发人员很少遇到这种情况。

        @OptIn(ExperimetalAnimationApi::class)✅
        这个注释的意思是——“我选择使用一个实验性的 api”。它不会强制此方法/类的用户在他们的代码中添加注释。
     */
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mviewModel:MainViewModel = viewModel()
                    //用于控制导航
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = HOME_PAGE ,
                        //进入动画
                        enterTransition = {
                            //垂直滑动动画
                            //slideInVertically(
                                //lambda 中为正数，就是向上滑动，偏移fullHeight
                                //initialOffsetY = {fullHeight -> fullHeight },
                                //动画的属性等级
                                //animationSpec = spring(
                                    //阻尼比
                                    //dampingRatio = Spring.DampingRatioLowBouncy,
                                    //刚度
                                   // stiffness = Spring.StiffnessMedium
                                //)
                            //)
                               slideInHorizontally(
                                   initialOffsetX = {fullWidth -> fullWidth },
                                   animationSpec = tween(500)
                               )
                        },
                        exitTransition = {
                            //shrinkOut(animationSpec = tween(500))
                            slideOutHorizontally(
                                targetOffsetX = {fullWidth -> fullWidth },
                                animationSpec = tween(500)
                            )
                        },
                        popEnterTransition = {
                            //expandIn(animationSpec = tween(500))
                             slideInHorizontally(
                                 initialOffsetX = {fullWidth -> fullWidth },
                                 animationSpec = tween(500)
                             )
                        },
                        popExitTransition = {
                            //shrinkOut(animationSpec = tween(500))
                             slideOutHorizontally(
                                 targetOffsetX = {fullWidth -> fullWidth },
                                 animationSpec = tween(500)
                             )
                        }
                        ){
                        //主页面
                        composable(HOME_PAGE){
                            HomePage()
                        }
                        
                        //疫情新闻列表页面
                        composable(EPIDEMIC_NEWS_LIST_PAGE){
                            EpidemicNewsListPage(navController,mviewModel)
                        }

                        //风险区详情页面
                        composable("$RISK_ZONE_DETAILS_PAGE/{title}/{stringList}",
                            arguments = listOf(
                                navArgument("title"){
                                    type = NavType.StringType
                                    defaultValue = "风险区详情"
                                    nullable = true //不允许有空值
                                },
                                navArgument("stringList"){
                                    type = NavType.StringType
                                }
                            )
                            ){
                            val title = it.arguments?.getString("title")?:"风险区详情"
                            val stringList = it.arguments?.getString("stringList")
                            RiskZoneDetailsPage(navController,title,stringList)
                        }

                        //webView页面
                        composable(
                            "$WEB_VIEW_PAGE/{title}/{url}",
                            arguments = listOf(
                                navArgument("title"){
                                    type = NavType.StringType
                                },
                                navArgument("url"){
                                    type = NavType.StringType
                                },
                            )
                        ){
                            val title = it.arguments?.getString("title")?:"WebView页面"
                            val url = it.arguments?.getString("url")?:"WebViewUrl"
                            WebViewPage(navController = navController, title = title, url =url )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        Greeting("Android")
    }
}