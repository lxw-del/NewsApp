package com.permissionx.goodnews.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.permissionx.goodnews.R
import com.permissionx.goodnews.db.bean.NewslistItem
import com.permissionx.goodnews.viewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeItem(mNavHostController: NavHostController,viewModel: HomeViewModel){

    /*val dataState = viewModel.result.observeAsState()

    dataState.value?.let {
        it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavHostController, it1) }
    }*/

    TabViewPager(mNavHostController,viewModel)

}

@Composable
fun ShowNewsList(mNavController: NavHostController, newslist: List<NewslistItem>){
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .padding(8.dp)
            //.navigationBarsPadding()//添加填充以适应导航栏的插入，适配导航栏的高度，让他不会遮挡lazycolumn的内容
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
                AsyncImage(//加载失败则加载error的图片
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(new.picUrl)
                        .error(R.drawable.lucy)
                        .crossfade(true)//淡入淡出动画，切换动画比较轻
                        .build()
                    ,
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    contentScale = ContentScale.FillBounds,//非均匀缩放填满目标边界
                    placeholder = painterResource(id = R.drawable.lucy)//预览图
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

/**
 * 这是顶层的不同小窗口tab
 * 标签窗口
 */
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabViewPager(mNavController: NavHostController,viewModel: HomeViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 0.dp, 0.dp, 50.dp)) {
        val pages by mutableStateOf(
            listOf("社会","军事","科技","财经","娱乐")
        )
        val pagerState = rememberPagerState(
            initialPage = 0,//初始化页面，0就表示第一个页面
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            //使用提供的pagerTabIndicatorOffset 修饰符 自定义指示器
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState,tabPositions)
                )
            },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
            ) {
                //给全部页面添加标签，为每个页面设置Tab属性
                pages.forEachIndexed { index, title ->
                    Tab(
                        
                        selected = pagerState.currentPage == index,
                        onClick = {
                            //Dispatchers.Main 把协程运行在平台相关的只能操作UI对象的Main线程
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)//让HorizontalPager滚动到index所属的页面
                        }
                        },
                        modifier = Modifier.alpha(0.9f),
                        enabled = true,
                        selectedContentColor = colorResource(id = R.color.black),
                        unselectedContentColor = colorResource(id = R.color.gray),
                        text = { Text(text = title, fontWeight = FontWeight.Bold)}
                        )
                }
        }
        //用于创建一个可以横向滑动的分页布局
        HorizontalPager(
            count = pages.size,
            state = pagerState,//用于控制或观察viewPage状态的状态对象
            modifier = Modifier.padding(top = 4.dp),//分页tab与内容的间隔
            itemSpacing = 2.dp //内容中每个一级组件之间的水平间隔
        ) { page ->//以下的内容是页面内容
            when(page){
                0 -> viewModel.result.observeAsState().value?.let {
                    it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavController = mNavController, newslist = it1) }
            }
                1 -> viewModel.resultMilitary.observeAsState().value?.let {
                    it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavController = mNavController, newslist = it1) }
                }
                2 -> viewModel.resultTechnology.observeAsState().value?.let {
                    it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavController = mNavController, newslist = it1) }
                }
                3 -> viewModel.resultFinance.observeAsState().value?.let {
                    it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavController = mNavController, newslist = it1) }
                }
                4 -> viewModel.resultAmusement.observeAsState().value?.let {
                    it.getOrNull()!!.result.newslist?.let { it1 -> ShowNewsList(mNavController = mNavController, newslist = it1) }
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "page :$page",
                            modifier = Modifier.fillMaxWidth()
                            )
                    }
                }
            }
        }
    }
}