package com.permissionx.goodnews.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * 疫情新闻列表页面
 */
@Composable
fun EpidemicNewsListPage(navController: NavHostController){
    Column {
        Text(text = "疫情新闻列表")
        //navController.navigate(PageConstant.RISK_ZONE_DETAILS_PAGE)可以进行导航跳转页面
        Button(onClick = { navController.navigate(PageConstant.RISK_ZONE_DETAILS_PAGE)}) {
            Text(text = "查看详情")
        }
    }
}