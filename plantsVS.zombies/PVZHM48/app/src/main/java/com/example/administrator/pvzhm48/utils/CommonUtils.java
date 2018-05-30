package com.example.administrator.pvzhm48.utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/15.
 */

public class CommonUtils {

    /*
*  动画工具类
* format 路径format
* num 帧数
* repeat 动画是否循环
* */
    public static CCAction animate(String format,int num,boolean repeat,float t){
        //初始化图像
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        for(int i=1;i<= num;i++){
            frames.add(CCSprite.sprite(String.format(format,i))
                    .displayedFrame());
        }
        CCAnimation anim = CCAnimation.animation("loading",t,frames);//参数2表示每一帧显示时间
        if(!repeat){
            CCAnimate animate = CCAnimate.action(anim,false);//参数2表示只执行一次，默认是true
            return animate;
        }else{
            CCAnimate animate = CCAnimate.action(anim);
            CCRepeatForever r = CCRepeatForever.action(animate);
            return r;
        }
    }
    public static CCAction animate(String format,int num,boolean repeat){
        return animate(format,num,repeat,0.2f);
    }
    /*
* 切换图层
* */
    public  static void changeLayer(CCLayer layer){
        CCScene scene = CCScene.node();
        scene.addChild(layer);
//        CCJumpZoomTransition transition = CCJumpZoomTransition.transition(2,
//                scene);//切换效果
        CCFadeTransition transition = CCFadeTransition.transition(1,scene);
        CCDirector.sharedDirector().replaceScene(transition);//切换场景
    }
/*
* 加载坐标点
* */
    public  static ArrayList<CGPoint> loadPoint(CCTMXTiledMap map,
                                         String groupName){
        ArrayList<CGPoint> points = new ArrayList<CGPoint>();
        CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed(groupName);
        ArrayList<HashMap<String,String>> objects = objectGroupNamed.objects;
        for( HashMap<String,String> hashMaps :objects){
            Integer x = Integer.parseInt(hashMaps.get("x"));
            Integer y = Integer.parseInt(hashMaps.get("y"));
            points.add(CCNode.ccp(x,y));
        }
        return points;
    }
}
