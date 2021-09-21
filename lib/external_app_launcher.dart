import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class LaunchApp {
  static const MethodChannel _channel = const MethodChannel('launch_vpn');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static isAppInstalled(
      {required String iosUrlScheme, required String androidPackageName}) async {
    String packageName = Platform.isIOS ? iosUrlScheme : androidPackageName;
    if (packageName.isEmpty) {
      throw Exception('The package name can not be empty');
    }
    dynamic isAppInstalled = await _channel
        .invokeMethod('isAppInstalled', {'package_name': packageName});
    return isAppInstalled;
  }

  static Future<int> openApp(
      {required String iosUrlScheme,
      required String androidPackageName,
      String? appStoreLink,
      bool? openStore}) async {
    String packageName = Platform.isIOS ? iosUrlScheme : androidPackageName;
    String packageVariableName =
        Platform.isIOS ? 'iosUrlScheme' : 'androidPackageName';
    if (packageName == "") {
      throw Exception('The $packageVariableName can not be empty');
    }
    if (Platform.isIOS && appStoreLink == null && openStore != false) {
      openStore = false;
    }

    return await _channel.invokeMethod('openApp', {
      'package_name': packageName,
      'open_store': openStore == false ? "false" : "open it",
      'app_store_link': appStoreLink
    });
  }
  // }
}
