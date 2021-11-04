package com.example.wanandroid.feature_search.domain.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle


/**
 *@Author xiejunyan
 *@Date 2021/10/31
 *@Description 用于search screen上的工具
 */

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun textToAnnText(str: String):AnnotatedString {
    val list: MutableList<Int> = mutableListOf()
    val pre = "<em class='highlight'>"
    val pos = "</em>"
    var startIndex = 0
    var preIndex = str.indexOf(pre, startIndex)
    var posIndex = str.indexOf(pos, startIndex)
    while (preIndex != -1) {
        list.add(preIndex)
        list.add(posIndex)
        startIndex = posIndex + pos.length
        preIndex = str.indexOf(pre, startIndex)
        posIndex = str.indexOf(pos, startIndex)
    }
    if (list.size == 0){
        return buildAnnotatedString {
                append(str)
        }
    }
    startIndex = 0
    val builder = AnnotatedString.Builder()
    list.forEachIndexed { index, num ->
        if (index and 1 == 0) { //偶数
            builder.append(str.substring(startIndex, num))
            startIndex = num + pre.length
        }
        else { //奇数
            builder.withStyle(style = SpanStyle(color = Color.Red, fontStyle = FontStyle.Italic)){
                append(str.substring(startIndex, num))
                startIndex = num + pos.length
            }
        }
    }
    builder.append(str.substring(startIndex))
    return builder.toAnnotatedString()
}