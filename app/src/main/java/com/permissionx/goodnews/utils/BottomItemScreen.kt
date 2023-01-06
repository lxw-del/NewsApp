package com.permissionx.goodnews.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.permissionx.goodnews.ui.pages.PageConstant.COLLECTION_ITEM
import com.permissionx.goodnews.ui.pages.PageConstant.HOME_ITEM

/**
 * 定义路线名称，底部标题和图标
 * @param route
 * @param title
 * @param icon
 */

sealed class BottomItemScreen(val route:String,val title:String,val icon:ImageVector){
    //object xxx:xxx()这是kotlin一种实现匿名内部类的方法
    object HOME: BottomItemScreen(HOME_ITEM,"首页", Icons.Default.Home)
    object STAR: BottomItemScreen(COLLECTION_ITEM,"收藏",Icons.Default.Favorite)
}