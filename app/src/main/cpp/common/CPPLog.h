//
//  CPPLog.h
//  study
//
//  Created by 飞拍科技 on 2018/12/29.
//  Copyright © 2018 飞拍科技. All rights reserved.
//

#ifndef CPPLog_h
#define CPPLog_h

#ifdef __cplusplus
extern "C" {
#endif
char* getTimeFormatForDebug();
#ifdef __cplusplus
} // extern "C"
#endif
#include <assert.h>

// 用于定义同时用于安卓和IOS的日志输出函数 改为0 则关闭
#define ENABLE_CPP_LOG 1
#define LOG_TAG "CPP_LOG"

// ===============日志定义 开始==================//
/**
 *  ## 表示字符串连接符号，##__VA_ARGS__ 表示将__VA_ARGS__与前面的字符串连接成一个字符串
 *  #  表示为后面的字符串添加双引号 比如 #A 则代表 "A"
 *  下面的fmt，是格式化的输出字符串比如"%s %d"，...表示可变参数，后面的__VA_ARGS__与前面可变参数对应
 */
#ifdef ENABLE_CPP_LOG
    #ifdef ANDROID
        #include <android/log.h>
        #define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
        #define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
        #define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__))
        #define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))
    #else
        #include <stdio.h>
        #include <sys/time.h>
        #define LOGD(fmt, ...) printf(" %s " fmt "\n",getTimeFormatForDebug(), ##__VA_ARGS__)
        #define LOGI(fmt, ...) printf(" %s " fmt "\n",getTimeFormatForDebug(), ##__VA_ARGS__)
        #define LOGW(fmt, ...) printf(" %s " fmt "\n",getTimeFormatForDebug(), ##__VA_ARGS__)
        #define LOGE(fmt, ...) printf(" %s " fmt "\n",getTimeFormatForDebug(), ##__VA_ARGS__)
    #endif
#else
    #define LOGD(...)
    #define LOGI(...)
    #define LOGW(...)
    #define LOGE(...)
#endif
// ===============日志定义 结束==================//

#endif /* CPPLog_h */
