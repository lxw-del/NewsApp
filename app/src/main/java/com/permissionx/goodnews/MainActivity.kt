package com.permissionx.goodnews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.permissionx.goodnews.db.bean.Desc
import com.permissionx.goodnews.db.bean.NewsItem
import com.permissionx.goodnews.repository.EpidemicNewsRepository
import com.permissionx.goodnews.ui.theme.GoodNewsTheme
import com.permissionx.goodnews.utils.ToastUtils.showToast
import com.permissionx.goodnews.utils.addSymbols
import com.permissionx.goodnews.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InitData()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//创建ViewModel，并且添加注册器，将返回值表示为State，State.value就是返回的真正的数据
@Composable
fun InitData(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    viewModel.getNews(false)

    viewModel.result.observeAsState().value?.let { result ->
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
                    Text(text = stringResource(id = R.string.app_name),
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

            )
        }
    ) {
        //这是主题内容部分，更改的是主体。
        result.news?.let { it1 -> BodyContent(it1,result.desc,Modifier.padding(it)) }
    }
}

@Composable
fun BodyContent(lists: List<NewsItem>,desc:Desc, modifier: Modifier = Modifier,viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
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
            Log.d("MainActivity", "desc: ${Gson().toJson(desc)}")

            descItem(desc)
            descItemPlus(desc)

            items(lists) { list ->
                Column(modifier = Modifier.padding(8.dp)) {
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
fun LazyListScope.descItem(desc: Desc){
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        //Greeting("Android")
    }
}