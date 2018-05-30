package com.example.administrator.pvzhm48;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.pvzhm48.engine.GameEngine;
import com.example.administrator.pvzhm48.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

public class MainActivity extends Activity {
    private  CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView view = new CCGLSurfaceView(this);//创建一个SurfaceView,类似导演眼前的小银幕
        setContentView(view);

        director = CCDirector.sharedDirector();//获取导演单例类
        director.attachInView(view);//开启绘制线程

        director.setDisplayFPS(true);//显示帧率，表示每秒刷新界面的次数，一般当帧率大于30，看起来比较流畅，帧率和手机性能有关，也和程序的性能有关
//        director.setAnimationInterval(1/60f);//设置最高帧率为60

        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//设置强制横屏显示
        director.setScreenSize(480,320);//用于屏幕适配，会基于不同屏幕进行等比例压缩，设置我们开发时候的分辨率

        CCScene scene = CCScene.node();//创建一个场景对象

        WelcomeLayer layer = new WelcomeLayer();

        scene.addChild(layer);//给场景添加图层
        director.runWithScene(scene);//导演运行场景

    }
    @Override
    protected void onResume(){
        super.onResume();
        director.resume();//游戏继续
        SoundEngine.sharedEngine().resumeSound();// 音乐继续
    }
    @Override
    protected void onPause(){
        super.onPause();
        director.pause();//游戏暂停
        SoundEngine.sharedEngine().resumeSound();//音乐暂停
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        director.end();//游戏结束
        GameEngine.isStart = false;
        System.exit(0);//杀死进程，清空所有静态变量
    }
}
