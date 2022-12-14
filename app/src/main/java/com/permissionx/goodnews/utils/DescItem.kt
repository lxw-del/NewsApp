package com.permissionx.goodnews.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.permissionx.goodnews.db.bean.Desc

object DescItem {

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
                            Text(text = desc.currentConfirmedCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = "较昨日 ${desc.currentConfirmedIncr}")
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "累计确诊人数")
                            Text(text = desc.confirmedCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = "较昨日 ${desc.currentConfirmedIncr}")
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
                            Text(text = desc.curedCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = "较昨日 ${desc.curedIncr}")
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "累计死亡人数")
                            Text(text = desc.deadCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = "较昨日${desc.deadIncr}")
                        }
                    }
                }

            }
        }
    }

}