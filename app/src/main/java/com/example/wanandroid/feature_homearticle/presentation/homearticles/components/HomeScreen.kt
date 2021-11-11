package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.presentation.util.columnSlideAnim
import com.example.wanandroid.feature_homearticle.presentation.util.whenDragColumn
import com.google.accompanist.pager.ExperimentalPagerApi


/**
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description
 */

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(
    fold: Boolean,
    viewModel: MainViewModel,
    articlesLazyColumnState: LazyListState,
    onClickArrow: () -> Unit
) {
    Column(Modifier.padding(start = 10.dp, end = 10.dp)) {

        HorizontalPagerWithOffsetTransition(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        )

        TopArticleHeader(Modifier.fillMaxWidth())
        Spacer(
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )
        AnimatedVisibility(visible = !fold) {
            TopArticlesColumn(Modifier.fillMaxWidth())
        }
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(if (fold) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up),
                contentDescription = null,
                modifier = Modifier.clickable {
                   onClickArrow()
                })
        }

        ArticlesHeader(Modifier.fillMaxWidth())
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        )


        AnimatedContent(
            targetState = viewModel.state.value.articles,
            transitionSpec = columnSlideAnim(viewModel.getDirection())
        ) {
            ArticlesColumn(
                modifier = Modifier
                    .pointerInput(Unit) {//控制左滑、右滑的事件监听
                        detectDragGestures { change, dragAmount ->
                            whenDragColumn(change, dragAmount, viewModel)
                        }
                    }
                    .fillMaxWidth(),
                articlesLazyColumnState = articlesLazyColumnState
            )
        }
    }
}
