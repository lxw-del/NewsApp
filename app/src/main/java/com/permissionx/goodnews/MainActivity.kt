package com.permissionx.goodnews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.permissionx.goodnews.db.bean.ListItem
import com.permissionx.goodnews.repository.EpidemicNewsRepository
import com.permissionx.goodnews.ui.theme.GoodNewsTheme
import com.permissionx.goodnews.utils.ToastUtils.showToast
import com.permissionx.goodnews.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
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

   /* val dataState = viewModel.result.observeAsState()

    dataState.value?.let {
        val orNull = it.getOrNull()
        if (orNull != null){
            orNull.result.list?.let { it1 -> MainScreen(it1) }
        }
    }*/
    
    MainScreen(list = App.db.listItemDao().getAll())

}

@Composable
private fun MainScreen(list:List<ListItem>){
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
        BodyContent(list,Modifier.padding(it))
    }
}

@Composable
fun BodyContent(lists: List<ListItem>, modifier: Modifier = Modifier){
    //LazyColumn 等同于 RecyclerView 只会渲染界面上的可见项，且不需要使用scroll修饰符
    LazyColumn(
        //state用于记录当前项的状态，用户处理滚动事件。
        state = rememberLazyListState(),
        modifier = Modifier.padding(8.dp)
    ){
        items(lists){list ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = list.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(0.dp,10.dp)
                )
                Text(text = list.digest, fontSize = 12.sp)
                Row(modifier = Modifier.padding(0.dp,10.dp)) {
                    Text(text = list.mtime, fontSize = 12.sp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        //Greeting("Android")
    }
}