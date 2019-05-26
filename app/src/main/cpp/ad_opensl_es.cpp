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

#include "SLAudioPlayer.h"
#include "opensles/SLAudioPlayer.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_media_Audio_ADOpenSLES_playAudio(JNIEnv *env, jobject instance, jstring path_,
                                          jint sample_rate, jint ch_layout, jint format) {
    const char *path = env->GetStringUTFChars(path_, 0);
    FILE *pcmFile = fopen(path,"r");

    if (pcmFile == NULL) {
        LOGD("file is null");
        env->ReleaseStringUTFChars(path_, path);
        return;
    }

    SLAudioPlayer *player = new SLAudioPlayer(sample_rate,(Sample_format)format,(Channel_Layout)ch_layout);
    int perid = 20; // 20ms
    int bufSize = sample_rate * perid / 1000;
    int exit = 0;
    if (format == Sample_format_SignedInteger_8) {
        char buffer[bufSize];
        while (!exit && !feof(pcmFile)) {
            if (fread((char *)buffer, bufSize, 1, pcmFile) != 1) {
                LOGD("failed to read data \n ");
                break;
            }
            player->putAudioData(buffer,bufSize);
        }
    } else if(format == Sample_format_SignedInteger_16) {
        int16_t buffer[bufSize];
        while (!exit && !feof(pcmFile)) {
            if (fread((char *)buffer, bufSize* sizeof(int16_t), 1, pcmFile) != 1) {
                LOGD("failed to read data \n ");
                break;
            }
            player->putAudioData((char *)buffer,bufSize);
        }
    } else if (format == Sample_format_SignedInteger_32) {
        int32_t buffer[bufSize];
        while (!exit && !feof(pcmFile)) {
            if (fread((char *)buffer, bufSize* sizeof(int32_t), 1, pcmFile) != 1) {
                LOGD("failed to read data \n ");
                break;
            }
            player->putAudioData((char *)buffer,bufSize);
        }
    }

    player->closeAudioPlayer();
    fclose(pcmFile);
    env->ReleaseStringUTFChars(path_, path);
}