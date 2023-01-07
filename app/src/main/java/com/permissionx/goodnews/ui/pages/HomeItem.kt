package com.permissionx.goodnews.ui.pages

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.permissionx.goodnews.R
import com.permissionx.goodnews.db.bean.NewslistItem
import com.permissionx.goodnews.viewModel.HomeViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeItem(mNavHostController: NavHostController,viewModel: HomeViewModel){

    val dataState = viewModel.result.observeAsState()

    dataState.value?.let {
        it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavHostController, it1) }
    }



}

@Composable
fun ShowNewsList(mNavController: NavHostController, newslist: List<NewslistItem>){
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(8.dp)
            .navigationBarsPadding()//添加填充以适应导航栏的插入，适配导航栏的高度，让他不会遮挡lazycolumn的内容
    ){
        items(newslist){ new ->
            Log.d("HomeItem", "ShowNewsList: ${Gson().toJson(new)}")
            //整个items布局默认是一个Column布局
            Row(modifier = Modifier
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(new.url, StandardCharsets.UTF_8.toString())
                    mNavController.navigate("${PageConstant.WEB_VIEW_PAGE}/${new.title}/$encodedUrl")
                }
                .padding(8.dp)
            ) {
                AsyncImage(
                    model = new.picUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    contentScale = ContentScale.FillBounds//非均匀缩放填满目标边界
                    )
                Column(
                    modifier = Modifier
                        .clickable {
                            val encodedUrl =
                                URLEncoder.encode(new.url, StandardCharsets.UTF_8.toString())
                            mNavController.navigate("${PageConstant.WEB_VIEW_PAGE}/${new.title}/$encodedUrl")
                        }
                        .padding(8.dp, 0.dp, 0.dp, 0.dp)) {
                    Text(
                        text = new.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                    )
                    Row (modifier = Modifier.padding(0.dp,20.dp)){
                        Text(text = new.source, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 2.dp))
                        Text(text = new.ctime, fontSize = 12.sp)
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = colorResource(id = R.color.black).copy(alpha = 0.08f)
            )


        }

    }
}