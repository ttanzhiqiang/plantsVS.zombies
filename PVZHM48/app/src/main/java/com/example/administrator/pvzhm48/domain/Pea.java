package com.example.administrator.pvzhm48.domain;

import com.example.administrator.pvzhm48.domain.base.Bullet;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;

/**
 * Created by Administrator on 2018/3/19.
 * 豌豆射手子弹的逻辑
 */

public class Pea extends Bullet {
    public Pea(){
        super("image/fight/bullet.png");
        setScale(0.65f);
    }
    public void move(){
        float t = (CCDirector.sharedDirector().winSize().width - getPosition().x)
                / speed;// 计算子弹移动事件
        CCMoveTo move = CCMoveTo.action(
                t,
                ccp(CCDirector.sharedDirector().winSize().width,
                        getPosition().y));// 子弹移动到屏幕右侧边缘
        CCSequence sequence = CCSequence.actions(move,
                CCCallFunc.action(this, "destroy"));// 移动结束后,子弹销毁
        runAction(sequence);
    }
}
