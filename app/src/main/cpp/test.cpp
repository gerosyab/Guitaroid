//
// Created by donghe on 2017-08-04.
//

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <jni.h>
#include <string>
#include <math.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL
Java_net_gerosyab_guitaroid_activity_RhythmGuideActivity_calculateArea(JNIEnv *jenv, jobject self, jdouble radius) {
    jdouble area = M_PI * radius * radius;
    char output[40];
    sprintf(output, "The area is %f sqm", area);
    return jenv->NewStringUTF(output);
}

#ifdef __cplusplus
}
#endif