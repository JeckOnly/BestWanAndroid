package com.example.wanandroid.feature_search.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wanandroid.R


/**
 *@Author xiejunyan
 *@Date 2021/10/28
 *@Description 在search screen上展示东西的top bar的组件
 */

@ExperimentalComposeUiApi
@Composable
fun SearchScreenTopBar(
    modifier: Modifier = Modifier,
    onClickSearchText: (text: String) -> Unit = {},
    onClickBackIcon: () -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()//是否搜索框被点击
    val focusRequester = FocusRequester()//用于搜索框的点击请求
    val localFocusManager = LocalFocusManager.current//用于清除搜索框焦点

    Log.d("SearchScreenTopBar", "$isFocused")
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "go back to home screen",
            modifier = Modifier
                .padding(
                    10.dp
                )
                .clickable {
                    onClickBackIcon()
                }
        )
        Box(
            Modifier
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                .weight(0.8f)
        ) {

            Box(
                Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search"
                    )
                    InputEditText(
                        value = text,
                        modifier = Modifier.focusRequester(focusRequester),
                        onValueChange = { text = it },
                        contentTextStyle = TextStyle.Default,
                        placeHolderString = "  type something",
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onClickSearchText(text)
                                localFocusManager.clearFocus()//清除搜索框焦点
                            }
                        ),
                        interactionSource = interactionSource
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(0.2f)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "搜索",
                modifier = Modifier
                    .clickable {
                        onClickSearchText(text)
                        localFocusManager.clearFocus()//清除搜索框焦点
                    },
                fontSize = 17.sp
            )
        }
    }
    DisposableEffect(Unit) { //进入SearchScreen时执行一次
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
fun InputEditText(
    value: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    contentTextStyle: TextStyle = TextStyle.Default,
    hintTextStyle: TextStyle = TextStyle.Default,
    placeHolderString: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = Color.Black,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = contentTextStyle,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        if (contentTextStyle.textAlign == TextAlign.Start)
                            IntOffset(x = 10, y = 0)
                        else
                            IntOffset(x = 0, y = 0)
                    },
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolderString,
                        color = hintTextStyle.color,
                        fontSize = hintTextStyle.fontSize
                    )
                }

                innerTextField()

            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        interactionSource = interactionSource
    )
}

@Preview
@Composable
fun PreviewSomething() {
    Column {
        Box(
            Modifier
                .padding(20.dp)
                .background(Color.Blue)
                .size(100.dp))
        Box(
            Modifier
                .background(Color.Blue)
                .padding(20.dp)
                .size(100.dp))
    }
}