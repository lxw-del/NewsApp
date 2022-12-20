package com.permissionx.goodnews.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.permissionx.goodnews.ui.activity.ui.theme.GoodNewsTheme
import com.permissionx.goodnews.ui.pages.EpidemicNewsListPage
import com.permissionx.goodnews.ui.pages.PageConstant.EPIDEMIC_NEWS_LIST_PAGE
import com.permissionx.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.permissionx.goodnews.ui.pages.RiskZoneDetailsPage

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //用于控制导航
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = EPIDEMIC_NEWS_LIST_PAGE ){
                        //疫情新闻列表页面
                        composable(EPIDEMIC_NEWS_LIST_PAGE){
                            EpidemicNewsListPage(navController)
                        }

                        //风险区详情页面
                        composable(RISK_ZONE_DETAILS_PAGE){
                            RiskZoneDetailsPage(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    GoodNewsTheme {
        Greeting2("Android")
    }
}