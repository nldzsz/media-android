//
// Created by 飞拍科技 on 2019/5/22.
//

// for jni
#include <jni.h>
// for c++ 要引入，否则NULL关键字找不到
#include <stdio.h>

// for log
#include "common/CPPLog.h"
#include "common/Common.h"

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

// 定义引擎对象接口，该接口是使用OpenSL ES的唯一入口
SLObjectItf enginObject = NULL;

// 创建一个线程安全的对象引擎接口
SLresult initSLEngine()
{
    SLresult result;
    // OpenSL ES for Android is designed to be thread-safe,
    // so this option request will be ignored, but it will
    // make the source code portable to other platforms.
    SLEngineOption engineOptions[] = {{SL_ENGINEOPTION_THREADSAFE,SL_BOOLEAN_TRUE}};
    // Create the OpenSL ES engine object
    return slCreateEngine(&enginObject, ARRAY_LEN(engineOptions), engineOptions, 0, // no interfaces
                          0, // no interfaces
                          0); // no required
}

extern "C" void
Java_com_media_Audio_ADOpenSLES_playAudio(JNIEnv *env, jobject instance, jstring path,
                                          jint sr, jint ch_layout, int format)
{

}