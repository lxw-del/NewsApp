package com.permissionx.goodnews.ui.pages

import android.graphics.Bitmap
import android.icu.text.CaseMap.Title
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun WebViewPage(navController: NavHostController,title:String,url:String){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary,
                        overflow = TextOverflow.Ellipsis,//超出省略
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                    }
                },
                elevation = 4.dp
            )
        }
    ) {
        val mWebViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("webView", "加载开始")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("webView", "加载完成")
            }
        }

        val mWebViewChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d("webView", "加载: $newProgress")
            }
        }

        //要在Compose中使用原生的Android控件，则就需要通过AndroidView来加载
        //经典webView配置
        AndroidView(factory = {context ->

            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    //false就是webView本身的宽度，true就是根据页面tag给的宽度
                    //如果没有给定宽度，就使用宽视口
                    useWideViewPort = true
                    javaScriptCanOpenWindowsAutomatically = true
                    domStorageEnabled = true
                    loadsImagesAutomatically = true
                    //与useWideViewPort 配套使用，true时，表示可以自适应屏幕的宽度来显示网页
                    loadWithOverviewMode = true
                }
                webViewClient = mWebViewClient
                webChromeClient = mWebViewChromeClient
                loadUrl(url)
            }



        })


    }
}