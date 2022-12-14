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
            //.navigationBarsPadding()//???????????????????????????????????????????????????????????????????????????????????????lazycolumn?????????
    ){
        items(newslist){ new ->
            Log.d("HomeItem", "ShowNewsList: ${Gson().toJson(new)}")
            //??????items?????????????????????Column??????
            Row(modifier = Modifier
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(new.url, StandardCharsets.UTF_8.toString())
                    mNavController.navigate("${PageConstant.WEB_VIEW_PAGE}/${new.title}/$encodedUrl")
                }
                .padding(8.dp)
            ) {
                AsyncImage(//?????????????????????error?????????
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(new.picUrl)
                        .error(R.drawable.lucy)
                        .crossfade(true)//??????????????????????????????????????????
                        .build()
                    ,
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    contentScale = ContentScale.FillBounds,//?????????????????????????????????
                    placeholder = painterResource(id = R.drawable.lucy)//?????????
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
 * ??????????????????????????????tab
 * ????????????
 */
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabViewPager(mNavController: NavHostController,viewModel: HomeViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 0.dp, 0.dp, 50.dp)) {
        val pages by mutableStateOf(
            listOf("??????","??????","??????","??????","??????")
        )
        val pagerState = rememberPagerState(
            initialPage = 0,//??????????????????0????????????????????????
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            //???????????????pagerTabIndicatorOffset ????????? ??????????????????
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState,tabPositions)
                )
            },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
            ) {
                //???????????????????????????????????????????????????Tab??????
                pages.forEachIndexed { index, title ->
                    Tab(
                        
                        selected = pagerState.currentPage == index,
                        onClick = {
                            //Dispatchers.Main ?????????????????????????????????????????????UI?????????Main??????
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)//???HorizontalPager?????????index???????????????
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
        //???????????????????????????????????????????????????
        HorizontalPager(
            count = pages.size,
            state = pagerState,//?????????????????????viewPage?????????????????????
            modifier = Modifier.padding(top = 4.dp),//??????tab??????????????????
            itemSpacing = 2.dp //????????????????????????????????????????????????
        ) { page ->//??????????????????????????????
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