package com.example.administrator.pvzhm48.domain;

import com.example.administrator.pvzhm48.domain.base.AttackPlant;
import com.example.administrator.pvzhm48.domain.base.Bullet;
import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;

/**
 *
 * 豌豆射手
 */

public class PeaPlant extends AttackPlant {
    public PeaPlant(){
        super("image/plant/peas/p_2_01.png");
        baseAction();
    }
    @Override
    public Bullet createBullet() {
        if (bullets.size() < 1) {// 每次只能生产一个子弹
            final Pea pea = new Pea();
            pea.setPosition(ccp(getPosition().x + 30, getPosition().y + 35));// 设置子弹的位置
            pea.move();// 子弹移动

            pea.setDieListener(new DieListener() {

                @Override
                public void die() {
                    bullets.remove(pea);// 从集合移除子弹
                }
            });

            bullets.add(pea);
            this.getParent().addChild(pea);// 子弹显示在地图上

            return pea;
        }

        return null;
    }
    public void baseAction(){
        CCAction animate = CommonUtils.animate("image/plant/peas/p_2_%02d.png",8,true);
        this.runAction(animate);
    }
}
