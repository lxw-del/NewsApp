package com.permissionx.goodnews.ui

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.permissionx.goodnews.R
import com.permissionx.goodnews.utils.BottomItemScreen

@Composable
fun BottomBarView(navController: NavController){
    val navItem = listOf(
        BottomItemScreen.HOME,
        BottomItemScreen.STAR
    )
    //返回返回栈一个可变的状态，栈顶的状态
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    //当前路线
    //只有先设置栈顶的目标路径，才可以设置目标id
    val currentRoute = navBackStackEntry?.destination?.route
    BottomAppBar {
        navItem.forEach {
            BottomNavigationItem(
                label = { Text(text = it.title) },//设置item标签
                icon = { Icon(imageVector = it.icon, contentDescription = it.title)},//设置图标
                selectedContentColor = Color.White,//选中时候的颜色
                unselectedContentColor = colorResource(id = R.color.gray),//未选中的颜色
                selected = currentRoute == it.route,//选中时判断
                onClick = {
                    navController.navigate(it.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true //重新加载saveState的状态，还原以前saveState的状态
                    }
                }
            )
        }
    }

}