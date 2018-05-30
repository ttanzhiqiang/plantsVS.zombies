package com.example.administrator.pvzhm48.domain;

import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by Administrator on 2018/3/16.
 * 展现僵尸
 */

public class ShowZombie extends CCSprite {
    public ShowZombie(){
        sprite("image/zombies/zombies_1/shake/z_1_01.png");
        setAnchorPoint(0.5f,0);//锚点是两脚之间
        setScale(0.5);//设置僵尸大小
       CCAction animate=  CommonUtils.animate(
               "image/zombies/zombies_1/shake/z_1_%02d.png",2,true);
        runAction(animate);//僵尸颤抖
    }
}
