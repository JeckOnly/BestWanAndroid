package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wanandroid.feature_homearticle.presentation.util.openLinkBrowser


/**
 *@Author xiejunyan
 *@Date 2021/10/12
 *@Description 放置用于置顶文章的composable
 */

@ExperimentalFoundationApi
@Composable
fun TopArticlesColumn(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()//只会有一个实例

    val context = LocalContext.current

    Card(modifier = modifier) {
        LazyColumn{
            items(items = viewModel.topArticle){
                ArticleItem(
                    article = it,
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    onClick = {
                        openLinkBrowser(context, it.link)//调用浏览器打开页面
                    })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TopArticleHeader(modifier: Modifier = Modifier) {
    Box(modifier) {
        Row {
            Text(
                text = "Top Article",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }
    }
}
