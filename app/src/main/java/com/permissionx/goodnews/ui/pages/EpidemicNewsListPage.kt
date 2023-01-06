package com.permissionx.goodnews.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.permissionx.goodnews.R
import com.permissionx.goodnews.db.bean.Desc
import com.permissionx.goodnews.db.bean.NewsItem
import com.permissionx.goodnews.db.bean.Riskarea
import com.permissionx.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.permissionx.goodnews.ui.pages.PageConstant.WEB_VIEW_PAGE
import com.permissionx.goodnews.utils.ToastUtils.showToast
import com.permissionx.goodnews.utils.addSymbols
import com.permissionx.goodnews.viewModel.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.math.ceil


@SuppressLint("StaticFieldLeak")
lateinit var mNavController: NavHostController
lateinit var mViewModel: MainViewModel

/**
 * 疫情新闻列表页面
 */
@Composable
fun EpidemicNewsListPage(navController: NavHostController,viewModel: MainViewModel){
  /*  Column {
        Text(text = "疫情新闻列表")
        //navController.navigate(PageConstant.RISK_ZONE_DETAILS_PAGE)可以进行导航跳转页面
        Button(onClick = { navController.navigate(PageConstant.RISK_ZONE_DETAILS_PAGE)}) {
            Text(text = "查看详情")
        }
    }*/

    mNavController = navController
    mViewModel = viewModel

    mViewModel.getNews()

    mViewModel.result.observeAsState().value?.let { result ->
        result.getOrNull()?.result?.let { MainScreen(it) } }


}

@Composable
private fun MainScreen(result: com.permissionx.goodnews.db.bean.Result){
    //Scaffold是最高的可组合项，可为最常见的Material组件，提供槽位。可以确保这些组件能够正常放置并协同工作。
    Scaffold(
        //这里可以设置一些属性，如顶层应用栏，可以插入插槽Text等，形成标题。还有各种图标控件
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "疫情新闻",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {"Person".showToast()}) {
                        //Icons Material提供的图标依赖库
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Person")
                    }
                },
                actions = {
                    IconButton(onClick = {"Settings".showToast()}) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                elevation = 4.dp
                )
        },
        //compose 悬浮按钮 floatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                onClick = { "主页面".showToast() },
                contentColor = Color.White,
                content = {
                  Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                }
                )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        //这是主题内容部分，更改的是主体。
        result.news?.let { it1 -> BodyContent(it1,result.riskarea,result.desc, Modifier.padding(it)) }
    }
}

@Composable
fun BodyContent(lists: List<NewsItem>, riskarea: Riskarea, desc: Desc, modifier: Modifier = Modifier, viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    //下拉刷新
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { viewModel.getNews(true) },
        indicator = { state,trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,//刷新触发距离
                scale = true,//刷新View的大小动画
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small,
            )

        }
    ) {
        //LazyColumn 等同于 RecyclerView 只会渲染界面上的可见项，且不需要使用scroll修饰符
        LazyColumn(
            //state用于记录当前项的状态，用户处理滚动事件。
            state = rememberLazyListState(),
            modifier = Modifier.padding(8.dp)
        ) {
            // Log.d("MainActivity", "desc: ${Gson().toJson(desc)}")


            descItem(desc)
            //descItemPlus(desc)
            RiskareaItem(riskarea = riskarea)


            items(lists) { list ->
                Column(modifier = Modifier
                    .clickable {
                        //Url作为参数传递，需要转码成UTF-8
                        val encodedUrl =
                            URLEncoder.encode(list.sourceUrl, StandardCharsets.UTF_8.toString())
                        mNavController.navigate("${WEB_VIEW_PAGE}/${list.title}/${encodedUrl}")
                    }
                    .padding(8.dp)
                ) {
                    Text(
                        text = list.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Text(text = list.summary, fontSize = 12.sp)
                    Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                        Text(text = list.infoSource, fontSize = 12.sp)
                        Text(
                            text = list.pubDateStr,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(8.dp, 0.dp)
                        )
                    }
                }
                //分界线，设置颜色和透明度，属于子项的属性。
                Divider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = colorResource(id = R.color.black).copy(alpha = 0.08f)
                )
            }
        }
    }
}

//将人数信息整合成一个数据类，因为样式有规律性。
data class DescDataItem(var title:String,var current:Int,var yesterday:Int)

data class GroupItem(val descDataItem:DescDataItem?,val isEmpty:Boolean)


/**
 * descItemPlus函数是网格布局，用于简化添加数据布局的代码
 * 通过观察发现数据有统一性，所以可以设置数据类去代表这些数据
 * 通过一个列表去存储这些数据对象，并计算他们的行列。
 * 通过Items将每一行的数据显示出来
 *
 * 这样做就可以在需要添加删除数据的时候只需要更改descList这个列表的数据
 *
 * @param desc
 */



//这就是一个数据源，可以进行添加数据和删减数据
private fun LazyListScope.descItemPlus(desc: Desc){
    //构建一个desc 列表
    val descList = mutableListOf<DescDataItem>().apply {
        add(DescDataItem("现存确诊人数", desc.currentConfirmedCount, desc.currentConfirmedIncr))
        add(DescDataItem("累计确诊人数", desc.confirmedCount, desc.confirmedIncr))
        add(DescDataItem("累计治愈人数", desc.curedCount, desc.curedIncr))
        add(DescDataItem("累计死亡人数", desc.deadCount, desc.deadIncr))
        add(DescDataItem("现存无症状人数", desc.seriousCount, desc.seriousIncr))
    }

    //计算网格行数，并填满网格
    val gridItems = mutableListOf<List<GroupItem>>()
    var index = 0

    //设置列为2列
    val columnNum = 2
    //计算设置几行，ceil取整，比如2.3 就是 3
    val rowNum = ceil(descList.size.toFloat() / columnNum).toInt()

    //遍历行列，填充数据
    for(i in 0 until rowNum){
        val rowList = mutableListOf<GroupItem>()

        //这里根据列添加数据
        for (j in 0 until columnNum){
            if (index.inc() <= descList.size){
                rowList.add(GroupItem(descList[index++],false))
            }
        }

        //如果数据不够，没有填满，则填空占位
        val itemNum = columnNum - rowList.size
        for (x in 0 until itemNum){
            rowList.add(GroupItem(null,true))
        }
        //最后将每一行的数据加入到网格列表
        gridItems.add(rowList)
    }

    //显示每一个Item
    items(gridItems){gridItem ->
        Row {
            for(grid in gridItem){
                if (grid.isEmpty){
                    Box(modifier = Modifier.weight(1f))
                }else{
                    Box(modifier = Modifier.weight(1f)){
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                            elevation = 2.dp,//阴影
                            backgroundColor = Color.White) {

                            val descItemInfo = grid.descDataItem!!

                            Column(modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = descItemInfo.title)
                                Text(text = descItemInfo.current.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                                Text(text = "较昨日 ${descItemInfo.yesterday}")
                            }
                        }
                    }
                }
            }
        }

    }

}

//4个基本数据布局
private fun LazyListScope.descItem(desc: Desc){
    item {
        Card(//这里不写height或者width就是表示自适应，根据内容决定。
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 2.dp,//阴影
            backgroundColor = Color.White
        ) {
            Column {
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,//设置垂直居中对齐
                        horizontalAlignment = Alignment.CenterHorizontally//设置水平居中对齐
                    ) {
                        Text(text = "现存确诊人数")
                        Text(text = desc.currentConfirmedCount.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp,4.dp),
                            color = colorResource(id = R.color.red)
                        )
                        //buildAnnotatedString，它可以对一个Text中的不同内容做不同的样式设置
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))){
                                append("较昨日")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)){
                                append(desc.currentConfirmedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "累计确诊人数")
                        Text(text = desc.confirmedCount.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.dark_red),
                            modifier = Modifier.padding(0.dp,4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))){
                                append("较昨日")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)){
                                append(desc.confirmedIncr.addSymbols())
                            }
                        })
                    }
                }

                Row(modifier = Modifier.padding(12.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "累计治愈人数")
                        Text(text = desc.curedCount.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.green),
                            modifier = Modifier.padding(0.dp,4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))){
                                append("较昨日")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)){
                                append(desc.curedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "累计死亡人数")
                        Text(text = desc.deadCount.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.gray_black),
                            modifier = Modifier.padding(0.dp,4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))){
                                append("较昨日")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)){
                                append(desc.deadIncr.addSymbols())
                            }
                        })
                    }
                }
            }

        }
    }
}

//显示高低风险地区
private fun LazyListScope.RiskareaItem(riskarea: Riskarea){

    item {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = Color.White
        ) {
            //  记录变量的值，使得下次使用改变量时不进行初始化。
            //	使用 remember 存储对象的可组合项会创建内部状态，使该可组合项有状态。
            //	remember 会为函数提供存储空间，将 remember 计算的值储存，当 remember 的键改变的时候会进行重新计算值并储存。
            /*val openDialog = remember {
                mutableStateOf(false)//表明某个变量是有状态的，对变量进行监听，当状态改变时，触发重绘
            }
            val dialogTitle = remember {
                mutableStateOf(String())
            }
            val dialogList = remember {
                mutableStateOf(listOf(String()))
            }
            ShowDialog(openDialog,dialogTitle,dialogList)*/

            Row {
                Column(//clickable 如果设置在padding之后就会不包含内填充，反之会包含。准则，最后再去设置padding
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            /*openDialog.value = true
                            dialogTitle.value = "高风险地区"
                            dialogList.value = riskarea.high!!*/
                            mNavController.navigate(
                                "$RISK_ZONE_DETAILS_PAGE/高风险区/${
                                    Gson().toJson(
                                        riskarea.high
                                    )
                                }"
                            )
                        }
                        .padding(0.dp, 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "高风险地区", fontSize = 12.sp)
                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.red
                        )
                        )
                        ){
                            append("${riskarea.high?.size}")
                        }
                        withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)){
                            append("个")
                        }
                    })

                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            /*openDialog.value = true
                            dialogTitle.value = "中风险地区"
                            dialogList.value = riskarea.mid!!*/
                            mNavController.navigate(
                                "$RISK_ZONE_DETAILS_PAGE/中风险区/${
                                    Gson().toJson(
                                        riskarea.mid
                                    )
                                }"
                            )
                        }
                        .padding(0.dp, 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "中风险地区", fontSize = 12.sp)
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorResource(
                                id = R.color.dark_red
                            )
                            )
                        ){
                            append("${riskarea.mid?.size}")
                        }
                        withStyle(
                            style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        ){
                            append("个")
                        }
                    })
                }
            }

        }
    }
}

@Composable
private fun ShowDialog(
    openDialog: MutableState<Boolean>,
    dialogTitle: MutableState<String>,
    dialogList: MutableState<List<String>>
){
    if (openDialog.value){
        AlertDialog(
            onDismissRequest = {openDialog.value = false},
            title = { Text(text = dialogTitle.value)},
            text = { Text(text = "${dialogList.value.size}个")},
            confirmButton = {
                TextButton(onClick = {openDialog.value = false}) {
                    Text(text = "确定")
                }
            }
        )
    }
}