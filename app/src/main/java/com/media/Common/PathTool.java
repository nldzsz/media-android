package com.media.Common;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;

import java.io.IOException;

public class PathTool {

    /** AssetFileDescriptor详解
     * 1、该对象提供了用于访问位于assets目录下的资源文件句柄FileDescriptor，通过FileDescriptor就可以通过NSInputStream
     * 2、assets目录下的资源文件不会被编译成id，但是会直接编译到应用程序中
     * 3、assets目录支持多级子目录
     * 4、必须要有应用的Context上下文和资源文件的文件名,如果在二级目录下则还要包含路径
     * */
    static public AssetFileDescriptor getAssetFileDescriptor(Context context,String fileName) {
        try {

            return context.getAssets().openFd(fileName);
        } catch (IOException io) {
            io.printStackTrace();
        }

        return null;
    }


    /** Uri位于android.net框架下
     * 1、它是统一资源描述符 由[scheme:]scheme-specific-part[#fragment]组成，既可以描述网络又可以描述本地
     * 2、提供了将字符串形式的地址转换成Uri方法
     * */
    static public Uri getUriByString(String uriStr) {
        return Uri.parse(uriStr);
    }

}
