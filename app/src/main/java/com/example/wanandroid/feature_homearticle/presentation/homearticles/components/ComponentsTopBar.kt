package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wanandroid.R


/**
 *@Author xiejunyan
 *@Date 2021/10/27
 *@Description 用于顶部栏的组件
 */

@Composable
fun MainScreenTopBar(modifier: Modifier = Modifier, onClickSearch: (() -> Unit) = {}) {
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_system),
            contentDescription = "go to system",
            modifier = Modifier.padding(
                10.dp
            )
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null//把波纹效果给取消
                ) {
                    onClickSearch()
                }
                .padding(10.dp)
                .weight(0.8f),
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "search something")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                )
            }
        )
        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.CenterEnd

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more), contentDescription = "more",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreenTopBar() {
    MainScreenTopBar(modifier = Modifier.height(45.dp))
}
