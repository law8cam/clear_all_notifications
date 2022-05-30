import 'dart:async';

import 'package:flutter/services.dart';

class ClearAllNotifications {
  static const MethodChannel _channel = const MethodChannel('clear_all_notifications');

  static Future<void> clear() async {
    await _channel.invokeMethod('clear');
  }

  static Future<void> clearTag(String tag) async {
    await _channel.invokeMethod('clearTag', {"tag": tag});
  }

  static Future<dynamic> getNotificationData(String tag) async {
    var result = await _channel.invokeMethod('getNotificationData');
    return result;
  }
}
