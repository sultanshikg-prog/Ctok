package com.ctok.mod

import android.app.Activity
import android.widget.Toast
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.zhiliaoapp.musically" &&
            lpparam.packageName != "com.ss.android.ugc.trill") return

        XposedHelpers.findAndHookMethod(Activity::class.java, "onResume", object : XC_MethodHook() {
            override fun afterHookedMethod(p: MethodHookParam) {
                val a = p.thisObject as Activity
                Toast.makeText(a, "Ctok активен", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
