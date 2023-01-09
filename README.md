# NewsApp
Study Kotlin Compose and create a News APP

知识点小结
Url作为参数传递，需要转码成UTF-8
```kotlin
val encodedUrl = URLEncoder.encode(list.sourceUrl,StandardCharsets.UTF_8.toString())
```

这是不同小窗口tab
标签窗口配置
```kotlin
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabViewPager(){
    Column(modifier = Modifier.fillMaxSize()) {
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
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Page : $page", modifier = Modifier.fillMaxWidth())
            }
        }
    }
```