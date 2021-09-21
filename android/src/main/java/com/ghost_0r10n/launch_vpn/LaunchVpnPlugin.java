package com.ghost_0r10n.launch_vpn;
//import Constants;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.net.Uri;
import android.app.Activity;
import io.flutter.app.FlutterActivity;
import io.flutter.embedding.android.FlutterFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.content.pm.PackageManager;

/** LaunchVpnPlugin */
public class LaunchVpnPlugin extends FlutterFragmentActivity implements MethodCallHandler {

  private final Context context;
  private Result result;

  private LaunchVpnPlugin(Context context) {
    this.context = context;
  }
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "launch_vpn");
    channel.setMethodCallHandler(new LaunchVpnPlugin(registrar.activeContext()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("isAppInstalled")) {
      if (!call.hasArgument("package_name") || TextUtils.isEmpty(call.argument("package_name").toString())) {
        result.error("ERROR", "Empty or null package name", null);
      } else {
        String packageName = call.argument("package_name").toString();
        result.success(isAppInstalled(packageName));
      }
    } else if (call.method.equals("openApp")) {
      if (!call.hasArgument("package_name") || TextUtils.isEmpty(call.argument("package_name").toString())) {
        result.error("ERROR", "Empty or null package name", null);
      } else {
        String packageName = call.argument("package_name").toString();
        
        String activityClass = call.argument("activity_class").toString();
        openApp(packageName,activityClass);
        
      }
    } else {
      result.notImplemented();
    }
  }

  private int isAppInstalled(String packageName) {
    try {
      context.getPackageManager().getPackageInfo(packageName, 0);
      return 1;
    } catch (PackageManager.NameNotFoundException ignored) {
      return 0;
    }
  }

  private void openApp(String packageName, String activityClass) {
    if(!activityClass.equals("none")){
      
      Intent launchIntent = new Intent(packageName+'.'+activityClass);
    }else{
      Intent launchIntent = new Intent(packageName);

    }
      //if(launchIntent!=null){
//
       ((Activity)context).startActivityForResult(launchIntent,777);
//
   //   }
   //   // null pointer check in case package name was not found
    
   
    
  }


}

