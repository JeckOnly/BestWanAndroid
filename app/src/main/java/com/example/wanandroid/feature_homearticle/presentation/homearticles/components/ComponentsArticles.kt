package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import com.example.wanandroid.feature_homearticle.domain.model.banner.Banner
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesEvent
import com.example.wanandroid.feature_homearticle.presentation.util.openLinkBrowser
import com.example.wanandroid.ui.theme.BOTTOM_ICON_SIZE
import com.example.wanandroid.ui.theme.articleItemRoundedDp
import com.example.wanandroid.ui.theme.roundedCornerDp


/**
 *@Author xiejunyan
 *@Date 2021/9/29
 *@Description 主页面需要用到的各种组件
 */

@ExperimentalFoundationApi
@Composable
fun ArticlesColumn(modifier: Modifier = Modifier, articlesLazyColumnState: LazyListState) {
    val viewModel: MainViewModel = hiltViewModel()//只会有一个实例

    val context = LocalContext.current

    //Card(modifier = modifier) {
        LazyColumn(modifier = modifier, state = articlesLazyColumnState) {
            items(items = viewModel.state.value.articles) {
                ArticleItem(
                    article = it,
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    onClick = {
                        openLinkBrowser(context, it.link)//调用浏览器打开页面
                    })
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Spacer(modifier = Modifier.height(BOTTOM_ICON_SIZE * 1.2f))
            }
        }
    //}
}


@ExperimentalFoundationApi
@Composable
fun ArticleItem(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
    val showMenu = remember { mutableStateOf(false) }
    val viewModel: MainViewModel = hiltViewModel()//只会有一个实例

    Box(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { showMenu.value = true })//弹出可用操作按钮（eg: 收藏）
    ) {
        Column() {
            val scroll = rememberScrollState(0)
            Text(
                text = article.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Visible,
                modifier = Modifier.horizontalScroll(scroll)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                val content =
                    if (article.author != "") "作者：${article.author}" else "分享：${article.shareUser}"
                Text(
                    text = content,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = "${article.chapterName}/${article.superChapterName}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = article.niceDate,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        PopUpMenu(
            showMenu = showMenu,
            onDismiss = {
                showMenu.value = false
            }
        ) {
            CollectIcon(showMenu, article, viewModel)
        }
    }
}

@Composable
private fun CollectIcon(
    showMenu: MutableState<Boolean>,
    article: Article,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    Icon(
        painter = painterResource(R.drawable.ic_heartlove), contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                showMenu.value = false
                val articleCollect = article.toArticleCollect()
                viewModel.onEvent(
                    ArticlesEvent.InsertArticleCollect(articleCollect)//发送插入文章这个事件
                )
                Toast
                    .makeText(context, "已帮你加入收藏", Toast.LENGTH_LONG)
                    .show()
            },
        tint = Color.Red
    )
}


@Composable
fun ArticlesHeader(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel() //只会有一个实例
    Box(modifier) {
        Row {
            Text(
                text = "Today Article ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = "P${viewModel.state.value.page + 1}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

/**
 * 自定义DropdownMenu
 */
@Composable
fun PopUpMenu(
    showMenu: State<Boolean>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { onDismiss() },
        modifier = modifier,
        content = content
    )
}