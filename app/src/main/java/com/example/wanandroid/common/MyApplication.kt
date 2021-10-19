package com.example.wanandroid.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 *@className MyApplication
 *@Author xiejunyan
 *@Date 2021/10/11
 *@Description hilt注入的基类
 */
@HiltAndroidApp
class MyApplication : Application()