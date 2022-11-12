package com.permissionx.goodnews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.permissionx.goodnews.repository.EpidemicNewsRepository
import com.permissionx.goodnews.ui.theme.GoodNewsTheme
import kotlin.math.log

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
                    EpidemicNewsRepository.getEpidemicNews().observe(this@MainActivity){

                        //从result种获取内容使用getOrNull
                        val epidemicNews = it.getOrNull()

                        if (epidemicNews != null){
                            Log.d(TAG, "code is ${epidemicNews.code}")
                            Log.d(TAG, "msg is ${epidemicNews.msg}")
                            Log.d(TAG, "title is ${epidemicNews.result.list?.get(0)?.title}")
                            Log.d(TAG, "mtime is ${epidemicNews.result.list?.get(0)?.mtime}")
                            Log.d(TAG, "digest is ${epidemicNews.result.list?.get(0)?.digest}")
                        }else{
                            Log.e(TAG, "request info error!")
                        }
                    }
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        Greeting("Android")
    }
}