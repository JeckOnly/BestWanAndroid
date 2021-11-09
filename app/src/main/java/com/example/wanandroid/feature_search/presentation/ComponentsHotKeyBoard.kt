package com.example.wanandroid.feature_search.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.wanandroid.feature_search.domain.model.HotKey
import java.lang.Integer.max


/**
 *@Author xiejunyan
 *@Date 2021/11/4
 *@Description 显示搜索热词
 */

@Composable
fun HotKeyBoard(
    hotKeys: List<HotKey>,
    modifier: Modifier = Modifier,
    onClick: ((String) -> Unit) = {}
) {
    MessyBoard(modifier = modifier) {
        hotKeys.forEach { hotKey ->
            AHotKey(hotKey = hotKey, onClick = onClick)
        }
    }
}


@Composable
fun AHotKey(hotKey: HotKey, onClick: ((String) -> Unit)) {
    Button(onClick = { onClick(hotKey.name) }) {
        Text(text = hotKey.name)
    }
}

@Composable
fun MessyBoard(modifier: Modifier, content: @Composable () -> Unit) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables: List<Measurable>, constraints: Constraints ->
        val maxWidth = constraints.maxWidth
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var y = 0//放置当前item的x坐标
            var x = 0//放置当前item的y坐标
            var height = 0//这一层最高高度
            placeables.forEach { placeable ->
                if (x + placeable.width > maxWidth) {
                    x = 0
                    y += height + 10.dp.roundToPx()
                    placeable.placeRelative(x, y)
                    x += placeable.width + (10..25).random().dp.roundToPx()
                    height = placeable.height
                } else {
                    placeable.placeRelative(x, y)
                    height = max(placeable.height, height)
                    x += placeable.width + (10..25).random().dp.roundToPx()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessyBoard() {
    MessyBoard(Modifier) {
        for (i in 1..20) {
            Text(text = "---------")
            Text(text = "111112223")
        }
    }
}
