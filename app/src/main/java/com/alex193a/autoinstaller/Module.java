package com.alex193a.autoinstaller;


import android.widget.Button;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Module implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.packageinstaller"))
            return;

        XposedHelpers.findAndHookMethod("com.android.packageinstaller.PackageInstallerActivity",
                lpparam.classLoader, "startInstallConfirm", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        //get the confirm button
                        Button mOk = (Button) XposedHelpers.getObjectField(param.thisObject, "mOk");
                        //make it think user has read permissions
                        XposedHelpers.setObjectField(param.thisObject, "mScrollView", null);
                        XposedHelpers.setBooleanField(param.thisObject, "mOkCanInstall", true);
                        //click install button
                        mOk.performClick();



                    }
                });
    }

}
