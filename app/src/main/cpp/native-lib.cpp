#include <jni.h>
#include <string>
#include <android/log.h>
#include "quickjs/quickjs.h"

// Log tag so you can debug
#define TAG "NodeMotionEngine"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nodemotion_MainActivity_runScript(
        JNIEnv* env,
        jobject /* this */,
        jstring scriptCode) {

    const char *code = env->GetStringUTFChars(scriptCode, 0);

    // 1. Initialize QuickJS
    JSRuntime *rt = JS_NewRuntime();
    JSContext *ctx = JS_NewContext(rt);

    // 2. Evaluate Script
    JSValue result = JS_Eval(ctx, code, strlen(code), "<input>", JS_EVAL_TYPE_GLOBAL);

    // 3. Process Result
    const char *resultStr;
    if (JS_IsException(result)) {
        JSValue exception = JS_GetException(ctx);
        resultStr = JS_ToCString(ctx, exception);
        JS_FreeValue(ctx, exception);
    } else {
        resultStr = JS_ToCString(ctx, result);
    }

    // 4. Clean Memory
    std::string finalOutput = resultStr ? resultStr : "null";
    JS_FreeCString(ctx, resultStr);
    JS_FreeValue(ctx, result);
    JS_FreeContext(ctx);
    JS_FreeRuntime(rt);
    env->ReleaseStringUTFChars(scriptCode, code);

    return env->NewStringUTF(finalOutput.c_str());
}
