#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_task_newsfeedapp_utils_Keys_BASEURL(JNIEnv *env, jobject thiz) {
std::string baseurl ;
#ifdef PRODCTION
baseurl = "https://api.nytimes.com/";
#endif
#ifdef UAT
baseurl = "https://api.nytimes.com/";
#endif
#ifdef QA
baseurl = "https://api.nytimes.com/";
#endif
#ifdef DEVELOPMENT
baseurl = "https://api.nytimes.com/";
#endif
return env->NewStringUTF(baseurl.c_str());
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_task_newsfeedapp_utils_Keys_APPSIGNATURE(JNIEnv *env, jobject thiz) {
std::string key;
#ifdef RELEASE
key="AA469D5D7C915EADFF052E154B0A0FCA6E8C1494"; // Google Play Store Key

#endif
#ifdef QA
key = "DE60E0F1119D3249C553C66B3584E330BB43CA66";
#endif
#ifdef DEV
key = "0252d7582af33c37d50155a0ec3420d911fc085e";

#endif
#ifdef DEVELOPMENT
key = "0252d7582af33c37d50155a0ec3420d911fc085e";

#endif
return env->NewStringUTF(key.c_str());
}

/*

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_task_newsfeedapp_utils_BASEURL(JNIEnv *env, jobject thiz) {
    std::string baseurl;
#ifdef PRODCTION
    baseurl = "https://api.nytimes.com/";
#endif
#ifdef UAT
    baseurl = "https://custappdev.mahindradigisense.com/";
#endif
#ifdef DEVELOPMENT
    baseurl = "https://custappdev.mahindradigisense.com/";
#endif
    return env->NewStringUTF(baseurl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_mahindra_myo_utils_Keys_APPSIGNATURE(JNIEnv *env, jobject thiz) {
    std::string key;
#ifdef RELEASE
    key="AA469D5D7C915EADFF052E154B0A0FCA6E8C1494"; // Google Play Store Key

#endif
#ifdef QA
    key = "DE60E0F1119D3249C553C66B3584E330BB43CA66";
#endif
#ifdef DEV
    key = "0252d7582af33c37d50155a0ec3420d911fc085e";

#endif
#ifdef DEVELOPMENT
    key = "0252d7582af33c37d50155a0ec3420d911fc085e";

#endif
    return env->NewStringUTF(key.c_str());
}*/


