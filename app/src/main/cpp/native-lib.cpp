#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_my_diary_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "https://raw.githubusercontent.com/Aspsine/SwipeToLoadLayout/master/app/src/main/assets/all_characters.json";
    return env->NewStringUTF(hello.c_str());
}