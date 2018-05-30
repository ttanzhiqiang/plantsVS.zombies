package com.example.administrator.pvzhm48.layer;

import android.os.AsyncTask;
import android.view.MotionEvent;

import com.example.administrator.pvzhm48.R;
import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * Created by Administrator on 2018/3/15.
 */

public class WelcomeLayer extends BaseLayer {
    private CCSprite start;
    private CCSprite logo;
    public WelcomeLayer(){
        logo = CCSprite.sprite("image/popcap_logo.png");
        logo.setPosition(winSize.width/2,winSize.height/2);//屏幕居中
        this.addChild(logo);

        CCHide hide = CCHide.action();//表示隐藏
        CCDelayTime delay = CCDelayTime.action(1);//延迟一秒
        CCShow show = CCShow.action();//显示

        CCSequence s = CCSequence.actions(hide,delay,show,delay,hide,
                  delay, CCCallFunc.action(this,"showWelcome"));
        logo.runAction(s);

        // 异步在后台初始化数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                start.setVisible(true);// 显示点击开始的按钮
                setIsTouchEnabled(true);// 打开点击事件
            }
        }.execute();
        SoundEngine engine = SoundEngine.sharedEngine();
        engine.playSound(CCDirector.theApp, R.raw.start,true);//播放音乐
    }
    /*
    * 显示欢迎界面
    * */
    public void showWelcome(){
        logo.removeSelf();//删除logo
        //初始化欢迎界面
        CCSprite welcome = CCSprite.sprite("image/welcome.jpg");
        welcome.setAnchorPoint(0,0);
        this.addChild(welcome);

        //初始化加载中的图片
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        loading.setPosition(winSize.width/2,30);
        this.addChild(loading);

        //初始化开始图片
        start = CCSprite.sprite("image/loading/loading_start.png");
        start.setPosition(winSize.width/2,30);
        start.setVisible(false);//隐藏按钮
        this.addChild(start);

        CCAction animate = CommonUtils.animate(
                "image/loading/loading_%02d.png",9,false);
        loading.runAction(animate);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint convertTouchToNodeSapce = convertTouchToNodeSpace(event);
        if(CGRect.containsPoint(start.getBoundingBox(),
                convertTouchToNodeSapce)){
            System.out.println("点击开始！！！");
            CommonUtils.changeLayer(new MenuLayer());
        }
        return super.ccTouchesBegan(event);
    }

}

