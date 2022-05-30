package com.example.clear_all_notifications;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.app.NotificationManager;
import android.content.Context;

/** ClearAllNotificationsPlugin */
public class ClearAllNotificationsPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "clear_all_notifications");
    channel.setMethodCallHandler(this);
    this.context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("clear")) {
      channelMethodClearAllNotifications(result);
    } else if (call.method.equals("clearTag")) {
      String tag = call.argument("tag");
      channelMethodClearTagNotifications(result, tag);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void channelMethodClearAllNotifications(@NonNull Result result) {
    try {
      NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancelAll();
      result.success(true);
    } catch (Exception e) {
      result.error("Can not clear all notifications", e.getMessage(), e);
    }
  }

  private void channelMethodClearTagNotifications(@NonNull Result result, String tag) {
    try {
      NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancel(tag, 0);
      result.success(true);
    } catch (Exception e) {
      result.error("Can not clear tag notifications", e.getMessage(), e);
    }
  }
}
