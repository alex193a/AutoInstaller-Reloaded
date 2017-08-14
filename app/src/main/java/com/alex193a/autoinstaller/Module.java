package com.alex193a.autoinstaller;


import android.os.Build;
import android.widget.Button;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Module implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals("com.android.packageinstaller"))
            return;
        //hook the method
        XposedHelpers.findAndHookMethod("com.android.packageinstaller.PackageInstallerActivity",
                lpparam.classLoader, "startInstallConfirm", new XC_MethodHook(){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            //init sharedpref
                            XSharedPreferences pref = new XSharedPreferences("com.hamzah.autoinstall", "pref");
                            //check if its enabled
                            if (pref.getBoolean("enabled", false)) {
                                XposedBridge.log("Going to autoinstall");
                                //get the confirm button
                                Button mOk = (Button) XposedHelpers.getObjectField(param.thisObject, "mOk");
                                //make it think user has read permissions
                                XposedHelpers.setObjectField(param.thisObject, "mScrollView", null);
                                XposedHelpers.setBooleanField(param.thisObject, "mOkCanInstall", true);
                                //click install button
                                mOk.performClick();
                            }
                        } else {
                            XposedBridge.log("Going to autoinstall");
                            //get the confirm button
                            Button mOk = (Button) XposedHelpers.getObjectField(param.thisObject, "mOk");
                            //make it think user has read permissions
                            XposedHelpers.setObjectField(param.thisObject, "mScrollView", null);
                            XposedHelpers.setBooleanField(param.thisObject, "mOkCanInstall", true);
                            //click install button
                            mOk.performClick();
                        }
                    }
                });
    }

}
