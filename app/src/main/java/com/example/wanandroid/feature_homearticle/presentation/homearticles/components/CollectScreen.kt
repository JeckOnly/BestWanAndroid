package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import android.content.Context
import android.graphics.PathDashPathEffect
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.StampedPathEffectStyle.Companion.Rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesEvent
import com.example.wanandroid.feature_homearticle.presentation.util.openLinkBrowser
import com.example.wanandroid.ui.theme.articleCollectItemBackColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt


/**
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description
 */
private const val TAG = "CollectScreen"

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun CollectScreen(lazyListState: LazyListState) {
    val viewModel: MainViewModel = hiltViewModel()
    val articleCollect by viewModel.articleCollect.collectAsState(initial = emptyList())
    val context = LocalContext.current
    var trashbinY by remember { mutableStateOf(0f) }//用于记录垃圾箱区域的绝对y值
    var inTrashArea: Boolean by remember { mutableStateOf(false) }//判断元素是否在垃圾箱区域内
    var drag by remember { mutableStateOf(false) }//用于判断有无在拖动
    var refresh by remember { mutableStateOf(false) }//用于刷新屏幕, 把位置刷新一下

    Box() {
        AnimatedContent(
            targetState = refresh,
            transitionSpec = { fadeIn() with fadeOut() }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                state = lazyListState,
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = articleCollect
                ) {
                    var offset by remember { mutableStateOf(Offset.Zero) }//用于拖动时的偏移数值状态
                    var itemOriginY = remember { 0f }//控件的y坐标
                    CollectArticleItem(
                        context = context,
                        it = it,
                        offset = offset,
                        onDragStart = {
                            //开始拖动
                            drag = true
                        },
                        onDragEnd = {
                            //结束拖动
                            Log.d(TAG, "trashbinY——$trashbinY")
                            Log.d(TAG, "itemY——${offset.y + itemOriginY}")
                            if (!inTrashArea)
                                offset = Offset.Zero  //归位
                            else {
                                viewModel.onEvent(ArticlesEvent.DeleteArticleCollect(it))//删除这篇文章
                                offset = Offset.Zero  //归位
                                refresh = !refresh //刷新
                            }
                            drag = false
                            inTrashArea = false
                        },
                        onDragCancel = {
                            //拖动取消
                            offset = Offset.Zero
                            drag = false
                            inTrashArea = false
                        },
                        onDrag = { _: PointerInputChange, dragAmount: Offset ->
                            // 拖动中
                            offset += dragAmount
                            inTrashArea = offset.y + itemOriginY >= trashbinY//立即检测是否在垃圾箱区域里面
                        },
                        modifier = Modifier.onGloballyPositioned { lc ->
                            itemOriginY = lc.positionInWindow().y//把控件初始的y坐标赋值给itemOriginY
                        }
                    )
                }
            }
        }
        AnimatedVisibility(visible = drag, modifier = Modifier.align(Alignment.BottomCenter)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(TrashbinAreaShape())
                    .background(color = Color.Black.copy(alpha = 0.3f))
                    .onGloballyPositioned {
                        trashbinY = it.positionInWindow().y//把垃圾箱区域的顶端y坐标赋值给trashbinY
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_trashbin),
                    contentDescription = "trashbin",
                    tint = if (!inTrashArea) Color.Unspecified else Color.Red,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


@Composable
private fun CollectArticleItem(
    context: Context,
    it: ArticleCollect,
    offset: Offset,
    onDragStart: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit,
    onDrag: (PointerInputChange, Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable {
                openLinkBrowser(context, it.link)//调用浏览器打开页面
            }
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart, onDragEnd, onDragCancel, onDrag
                )
            }
            .fillMaxWidth()
            .background(color = articleCollectItemBackColor, shape = RoundedCornerShape(10))
            .padding(5.dp)
    ) {
        Column() {
            val scroll = rememberScrollState(0)
            Text(
                text = it.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Visible,
                modifier = Modifier.horizontalScroll(scroll)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                val content =
                    if (it.author != "") "作者：${it.author}" else "分享：${it.shareUser}"
                Text(
                    text = content,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = "${it.chapterName}/${it.superChapterName}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

class TrashbinAreaShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            // Draw your custom path here
            path = Path().apply {
                val m = size.width / 400
                moveTo(0f, 116f)
                cubicTo(88 * m, 0f, 312 * m, 0f, 400 * m, 116f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
        )
    }

}

@Preview
@Composable
fun DragGestureDemo() {
    val boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .size(boxSize)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        Log.d(TAG, "on drag start")
                    },
                    onDragEnd = {
                        Log.d(TAG, "on drag end")
                    },
                    onDragCancel = {
                        Log.d(TAG, "on drag cancel")
                    },
                    onDrag = { change: PointerInputChange, dragAmount: Offset ->
                        // 拖动中
                        offset += dragAmount
                        Log.d(TAG, offset.x.toString() + " ----- " + offset.y.toString())
                    }
                )
            }
        )
    }
}


@Preview
@Composable
fun PreviewCanvas() {
    val OPEN_ANGLE = 120f
    Canvas(modifier = Modifier.size(500.dp)) {
        val px = 150.dp.toPx()
        val dashWidth = 2.dp.toPx()
        val dashHeight = 10.dp.toPx()
        val dash = Path()
        dash.addRect(Rect(Offset(0f, 0f), Size(dashWidth, dashHeight)))
        val arcPath = Path()
        arcPath.addArc(
            Rect(
                Offset(size.width / 2 - px, size.height / 2 - px),
                Size(2 * px, 2 * px)
            ), 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE
        )

        drawPath(arcPath, Color.Black, style = Stroke(3.dp.toPx()))
        drawPath(
            arcPath,
            Color.Black,
            style = Stroke(pathEffect = PathEffect.stampedPathEffect(dash, 50f, 0f, Rotate))
        )
    }
}

