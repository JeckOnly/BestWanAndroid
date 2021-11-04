package com.example.wanandroid.feature_search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import com.example.wanandroid.feature_search.domain.util.textToAnnText


/**
 *@Author xiejunyan
 *@Date 2021/10/29
 *@Description
 */

@Composable
fun SearchScreenArticle(
    article: Article,
    onClickItem: ()->Unit,
    onclickCollect: (ArticleCollect) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column() {
            Text(
                text = textToAnnText(article.title),
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                modifier = Modifier.clickable {
                    onClickItem()
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                val content =
                    if (article.author != "") "作者：${article.author}" else "分享：${article.shareUser}"
                Text(
                    text = content,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = "${article.chapterName}/${article.superChapterName}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = article.niceDate,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
//        Icon(
//            painter = painterResource(id = R.drawable.ic_heartlove),
//            contentDescription = "collect",
//            modifier = Modifier
//                .size(24.dp)
//                .align(
//                    Alignment.BottomEnd
//                ).clickable { /*收藏逻辑*/ }
//        )
    }
}

@Preview
@Composable
fun PreviewText() {
    Text(
        text = "1111111111111111111111111111111111111111111111111111",
        fontSize = 16.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2
    )
}