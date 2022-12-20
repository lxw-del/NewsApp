package com.permissionx.goodnews.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * 风险地区详情页面
 */
@Composable
fun RiskZoneDetailsPage(navController: NavHostController){
    Column {
        Text(text = "风险地区详情")
        //navController.popBackStack()回退栈，也就是让Risk这个页面出栈，进而显示栈顶内容
        Button(onClick = {navController.popBackStack()}) {
            Text(text = "back")
        }
    }
}