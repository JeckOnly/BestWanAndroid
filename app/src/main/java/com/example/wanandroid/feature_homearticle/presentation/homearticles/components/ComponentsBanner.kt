package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.util.lerp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.wanandroid.feature_homearticle.domain.model.banner.Banner
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue
import android.R.attr.data
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.transform.RoundedCornersTransformation
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.presentation.util.LottieLoadingView
import com.example.wanandroid.feature_homearticle.presentation.util.openLinkBrowser
import com.example.wanandroid.ui.theme.roundedCornerDp


/**
 *@Author xiejunyan
 *@Date 2021/10/6
 *@Description Banner用到的各种组件
 */

private const val TAG = "Banner"

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun HorizontalPagerWithOffsetTransition(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    //用于在浏览器打开banner的链接,以及创造lottie的drawable
    val context = LocalContext.current
    val banners: List<Banner> = viewModel.bannerState

    HorizontalPager(
        count = banners.size,
        // Add 32.dp horizontal padding to 'center' the pages
        contentPadding = PaddingValues(horizontal = 50.dp),
        modifier = modifier
    ) { page ->
        //给图片加上一个圆角
        //val radius:Float = with(LocalDensity.current) { roundedCornerDp.toPx() }
        Card(
            Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .clip(RoundedCornerShape(roundedCornerDp))
        ) {
            Box {
                val painter = rememberImagePainter(
                    data = banners[page].imagePath,
                    builder = {
                        crossfade(true)
                    })
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        openLinkBrowser(context, banners[page].url)
                    }
                )
                //当图片没有加载出来之前，使用这个动画覆盖在image上面
                if (painter.state !is ImagePainter.State.Success){
                    LottieLoadingView(id = R.raw.loading_banner, speed = 2.0f)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSome(){
//    Text(text = "jiudsdflskdj", modifier = Modifier.paddingFrom())
}

fun Modifier.myWidthColor() =  composed(
    inspectorInfo = debugInspectorInfo{
        name = "myWidthColor"
    }
){
    var flag by remember{ mutableStateOf(false)}
    if (flag) Modifier
        .width(200.dp)
        .background(color = Color.Red)
        .clickable {
            flag = !flag
        }
    else Modifier
        .width(100.dp)
        .background(color = Color.Blue)
        .clickable {
            flag = !flag
        }
}
