package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import com.example.wanandroid.feature_homearticle.presentation.util.BottomBarScreen
import com.google.accompanist.insets.navigationBarsPadding


/**
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description 底部导航栏的组件
 */

@Composable
fun BottomNav(
    screenNow: BottomBarScreen,
    screens: List<BottomBarScreen>,
    onClick: (BottomBarScreen) -> Unit,
    color: Color,
    contentColor: Color = screenNow.color,
) {
    val springSpec = SpringSpec<Float>(
        // Determined experimentally
        stiffness = 800f,
        dampingRatio = 0.8f
    )
    val selectedIndex  = remember(key1 = screenNow) {
        screens.indexOfFirst { screen -> //选出当前screen的index
            screen == screenNow
        }
    }
    Surface(color = color, contentColor = contentColor) {
        BottomNavLayout(
            selectedIndex = selectedIndex,
            itemCount = screens.size,
            animSpec = springSpec,
            indicator = { BottomNavIndicator(strokeWidth = 1.dp, color = screenNow.color) },
            modifier = Modifier.navigationBarsPadding(start = false, end = false)
        ) {

            screens.forEach { screen ->
                val selected = screen == screenNow //当前项是否被选中
                val tint = screenNow.color //设置icon和text的tint

                BottomNavItem(
                    icon = {
                        Icon(
                            painterResource(id = screen.id),
                            tint = tint,
                            contentDescription = screen.name
                        )
                    },
                    text = {
                        Text(
                            text = screen.name,
                            color = tint,
                            style = MaterialTheme.typography.button,
                            maxLines = 1
                        )
                    },
                    selected = selected,
                    onSelected = { onClick(screen) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding
                        .clip(RoundedCornerShape(50))
                )
            }

        }


    }
}

/**
 * @param animSpec 控制indicator位置的动画规则
 */
@Composable
private fun BottomNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    //
    val selectionFractions =
        List(itemCount) { i ->
            animateFloatAsState(targetValue = if (i == selectedIndex) 1f else 0f)
        }

    //
    val indicatorIndex by animateFloatAsState(targetValue = selectedIndex.toFloat(), animSpec)


    Layout(
        modifier = modifier.height(BottomNavHeight), //底部栏的高度
        content = {
            content() //一堆 BottomNavItem
            Box(Modifier.layoutId("indicator"), content = indicator) //当前项指示器
        }
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1)) // 检查按钮项数是否正确

        val unselectedWidth = constraints.maxWidth / (itemCount + 1) //把底部栏分成itemCount+1份
        val selectedWidth = 2 * unselectedWidth  //当前项占两份

        val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }
        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        ) //利用约束好的大小测量每一个BottomNavItem 和 indicator

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }//放置每一个BottomNavItem 和 indicator
    }
}

/**
 * @param animSpec 控制一个item的text的长度变换的动画规则
 */
@Composable
fun BottomNavItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center
    ) {
        // Animate the icon/text positions within the item based on selection
        val animationProgress by animateFloatAsState(if (selected) 1f else 0f, animSpec)
        BottomNavItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}

@Composable
fun BottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(content = {
        Box(
            modifier = Modifier
                .layoutId("icon")//添加id便于布局的时候设置位置
                .padding(horizontal = TextIconSpacing),
            content = icon
        )
        val scale = lerp(0.3f, 1f, animationProgress)
        Box(
            modifier = Modifier
                .layoutId("text")
                .padding(TextIconSpacing)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = animationProgress
                }, content = text
        )
    }, modifier = Modifier.width(50.dp)) { measurables, constraints ->
        val iconPlaceable = measurables.first() { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first() { it.layoutId == "text" }.measure(constraints)

        placeTextAndIcon(
            iconPlaceable = iconPlaceable,
            textPlaceable = textPlaceable,
            width = constraints.maxWidth,
            height = constraints.maxHeight,
            animateProgress = animationProgress
        )
    }
}

fun MeasureScope.placeTextAndIcon(
    iconPlaceable: Placeable,
    textPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animateProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2
    val textWidth = textPlaceable.width * animateProgress//text长度由0到1变换

    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = (iconX + iconPlaceable.width)
    return layout(width, height) {
        iconPlaceable.placeRelative(iconX.toInt(), iconY)
        if (animateProgress != 0f)
            textPlaceable.placeRelative(textX.toInt(), textY)
    }
}

/**
 * 底部栏的当前项指示器
 */
@Composable
private fun BottomNavIndicator(
    strokeWidth: Dp,
    color: Color,
    shape: Shape = RoundedCornerShape(50)
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationIndicatorPadding)
            .border(strokeWidth, color, shape)
    )
}

@Composable
fun AnimatableIcon(
    resId: Int,
    iconSize: Dp,
    scale: Float,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Animation params
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        // Here the animation spec serves no purpose but to demonstrate in slow speed.
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(iconSize)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = "icon",
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}

private val TextIconSpacing = 2.dp
private val BottomNavHeight = 36.dp
private val BottomNavigationItemPadding = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
private val BottomNavigationIndicatorPadding = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)