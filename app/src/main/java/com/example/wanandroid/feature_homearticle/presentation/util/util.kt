package com.example.wanandroid.feature_homearticle.presentation.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.presentation.homearticles.components.MainViewModel
import kotlinx.coroutines.launch


/**
 *@Author xiejunyan
 *@Date 2021/10/2
 *@Description
 */

private const val TAG = "util"
fun whenDragColumn(
    change: PointerInputChange,
    dragAmount: Offset,
    viewModel: MainViewModel
) {
    change.consumeAllChanges()
    val (x, y) = dragAmount
    when {
        x > 0 -> {
            if (x > 40 && viewModel.state.value.page > 0) viewModel.onEvent(
                ArticlesEvent.UpOrDownPage(-1)
            )
        }
        x < 0 -> {
            if (x < -40) viewModel.onEvent(ArticlesEvent.UpOrDownPage(1))
        }
    }
}


@Composable
fun LottieLoadingView(
    id: Int,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
    speed: Float = 1f
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(id))
    LottieAnimation(
        composition,
        modifier = modifier.defaultMinSize(300.dp),
        iterations = iterations,
        speed = speed
    )
}

enum class Direction {
    Left, Right
}

@ExperimentalAnimationApi
fun columnSlideAnim(direction: Direction): AnimatedContentScope<List<Article>>.() -> ContentTransform =
    {
        val spring = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = 800f,
            visibilityThreshold = IntOffset.VisibilityThreshold
        )
        val tween = tween<IntOffset>(
            durationMillis = 500
        )
        if (direction == Direction.Right)
            slideIn(
                initialOffset = {
                    IntOffset(it.width, 0)
                },
                animationSpec = tween
            ) with slideOut(
                targetOffset = {
                    IntOffset(-it.width, 0)
                },
                animationSpec = tween
            ) else slideIn(
            initialOffset = {
                IntOffset(-it.width, 0)
            },
            animationSpec = tween
        ) with slideOut(
            targetOffset = {
                IntOffset(it.width, 0)
            },
            animationSpec = tween
        )
    }

fun openLinkBrowser(context: Context, link: String){
    val url = Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}



//enum class WebState {
//    LOADING, SUCCESS, NOAPP, FAILED
//}
//
//@Composable
//fun CustomWebView(
//    modifier: Modifier = Modifier,
//    onPageStarted: () -> Unit,
//    onPageFinished: () -> Unit,
//    onNoApp: () -> Unit,
//    onFailed: () -> Unit,
//    url: String,
//    onBack: (webView: WebView?) -> Unit,
//    initSettings: (webSettings: WebSettings?) -> Unit =
//        { settings ->
//            settings?.apply {
//                //支持js交互
//                javaScriptEnabled = true
//                //将图片调整到适合webView的大小
//                useWideViewPort = true
//                //缩放至屏幕的大小
//                loadWithOverviewMode = true
//                //缩放操作
//                setSupportZoom(true)
//                builtInZoomControls = true
//                displayZoomControls = true
//                //是否支持通过JS打开新窗口
//                javaScriptCanOpenWindowsAutomatically = true
//                //不加载缓存内容
//                cacheMode = WebSettings.LOAD_NO_CACHE
//            }
//        }
//) {
//    val webViewChromeClient = WebChromeClient()
//    val webViewClient = object : WebViewClient() {
//        override fun onPageStarted(
//            view: WebView?, url: String?,
//            favicon: Bitmap?
//        ) {
//            super.onPageStarted(view, url, favicon)
//            onPageStarted()
//        }
//
//        override fun onPageFinished(view: WebView?, url: String?) {
//            super.onPageFinished(view, url)
//            onPageFinished()
//        }
//
//        override fun shouldOverrideUrlLoading(
//            view: WebView?,
//            request: WebResourceRequest?
//        ): Boolean {
//            if (null == request?.url) return false
//            val showOverrideUrl = request.url.toString()
//            try {
//                if (!showOverrideUrl.startsWith("http://")
//                    && !showOverrideUrl.startsWith("https://")
//                ) {
//                    //处理非http和https开头的链接地址
//                    Intent(Intent.ACTION_VIEW, Uri.parse(showOverrideUrl)).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        view?.context?.applicationContext?.startActivity(this)
//                    }
//                    return true//中止当前webView的请求
//                }
//            } catch (e: Exception) {
//                //没有安装和找到能打开(「xxxx://openlink.cc....」、「weixin://xxxxx」等)协议的应用
//                onNoApp()
//                return true
//            }
//            return super.shouldOverrideUrlLoading(view, request)
//        }
//
//        override fun onReceivedError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            error: WebResourceError?
//        ) {
//            super.onReceivedError(view, request, error)
//            onFailed()
//        }
//    }
//    var webView: WebView? = null
//    val coroutineScope = rememberCoroutineScope()
//    AndroidView(modifier = modifier, factory = { context ->
//        WebView(context).apply {
//            this.webViewClient = webViewClient
//            this.webChromeClient = webViewChromeClient
//            //回调webSettings供调用方设置webSettings的相关配置
//            initSettings(this.settings)
//            webView = this
//            loadUrl(url)
//        }
//    })
//    BackHandler {
//        coroutineScope.launch {
//            //自行控制点击了返回按键之后，关闭页面还是返回上一级网页
//            onBack(webView)
//        }
//    }
//}

