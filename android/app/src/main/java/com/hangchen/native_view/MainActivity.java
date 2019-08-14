package com.hangchen.native_view;

import android.net.Uri;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.view.FlutterView;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import android.view.*;
import android.content.*;
import android.graphics.Color;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.common.BinaryMessenger;
import android.content.Intent;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    Registrar registrar = registrarFor("com.hangchen/NativeViews");
    SampleViewFactory playerViewFactory = new SampleViewFactory(registrar.messenger());
    registrar.platformViewRegistry().registerViewFactory("SampleView", playerViewFactory);
  }
}



class SampleViewFactory extends PlatformViewFactory {
  private final BinaryMessenger messenger;
  public SampleViewFactory(BinaryMessenger msger) {
    super(StandardMessageCodec.INSTANCE);
    messenger = msger;
  }
  @Override
  public PlatformView create(Context context, int id, Object obj) {
    return new SimpleViewControl(context, id, messenger);
  }
}

class SimpleViewControl implements PlatformView, MethodChannel.MethodCallHandler {
  private final MethodChannel methodChannel;
  private final View view;
  public SimpleViewControl(Context context, int id, BinaryMessenger messenger) {
    view = new View(context);
    view.setBackgroundColor(Color.rgb(255, 0, 0));

    methodChannel = new MethodChannel(messenger, "samples.chenhang/native_views_" + id);
    methodChannel.setMethodCallHandler(this);

  }

  @Override
  public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
    if (methodCall.method.equals("changeBackgroundColor")) {
      view.setBackgroundColor(Color.rgb(0, 0, 255));
      result.success(0);
    }else {
      result.notImplemented();
    }

  }


  @Override
  public View getView() {
    return view;
  }
  @Override
  public void dispose() {

  }
}