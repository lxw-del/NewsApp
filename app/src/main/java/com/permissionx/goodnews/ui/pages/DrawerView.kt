package com.permissionx.goodnews.ui.pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Stars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.permissionx.goodnews.R

/**
 * 简单的抽屉页面
 */
@Composable
fun DrawerView(){
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.white))
        .padding(0.dp, 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.lucy),
            contentDescription = "lucy",
            modifier = Modifier
                .size(100.dp)//大小
                .clip(CircleShape),//圆形
            contentScale = ContentScale.FillBounds
            )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Lucy",
            color = colorResource(id = R.color.black),
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Compose实践",
            color = colorResource(id = R.color.black),
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ItemView(name = "文章", num = 100, modifier = Modifier.weight(1f))
            ItemView(name = "点赞", num = 200, modifier = Modifier.weight(1f))
            ItemView(name = "评论", num = 300, modifier = Modifier.weight(1f))
            ItemView(name = "收藏", num = 400, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            color = colorResource(id = R.color.gray_black),
            thickness = 1.dp//分隔线的厚度
        )
        ItemViewOnClick(name = "CSDN主页", url = "https://blog.csdn.net/qq_50711629?type=sub&spm=1001.2101.3001.5348", context =context )
        ItemViewOnClick(name = "github主页", url = "https://github.com/lxw-del/NewsApp", context = context )
    }
}

@Composable
fun ItemView(name: String,num:Int,modifier: Modifier){
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = num.toString(), modifier = Modifier.padding(0.dp,6.dp))
        Text(text = name)
    }
}

@Composable
fun ItemViewOnClick(name: String,url:String,context: Context){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
        .height(50.dp)
        .padding(12.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Stars, contentDescription = name)
        Text(text = name, modifier = Modifier
            .weight(1f)
            .padding(12.dp, 0.dp))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription ="打开" )//ChevronRight就是个右箭头
    }
}