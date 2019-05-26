package com.media.Audio;

public class ADOpenSLES {
    static {
        System.loadLibrary("adMedia");
    }


    native void playAudio(String path);
}
