//
//  CPPLog.cpp
//  study
//
//  Created by 飞拍科技 on 2019/1/2.
//  Copyright © 2019 飞拍科技. All rights reserved.
//

#include "CPPLog.h"
#include <time.h>
#include <stdio.h>
char* getTimeFormatForDebug()
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    struct tm * tm_local = localtime(&tv.tv_sec);
    char str_f_t [30];
    strftime(str_f_t, sizeof(str_f_t), "%G-%m-%d %H:%M:%S", tm_local);
    char *returnStr = new char[40]();
    sprintf(returnStr, "%s:%.0f",str_f_t,tv.tv_usec/1000.0);
    
    return returnStr;
}
