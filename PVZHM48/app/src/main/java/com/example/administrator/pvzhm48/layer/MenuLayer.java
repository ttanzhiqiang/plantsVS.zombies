package com.example.administrator.pvzhm48.layer;

import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MenuLayer extends BaseLayer {
    public MenuLayer(){
        //初始化背景
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0,0);
        this.addChild(sprite);

        CCMenu menu =CCMenu.menu();//初始化CCMenu
        CCSprite normalSprite = CCSprite
                .sprite("image/menu/start_adventure_default.png");//默认图片
        CCSprite selectedSprite = CCSprite
                .sprite("image/menu/start_adventure_press.png");//选中图片
        CCMenuItemSprite item = CCMenuItemSprite
                .item(normalSprite,selectedSprite,this,"onClick");
        menu.addChild(item);
        menu.setScale(0.5f);
        menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
        menu.setRotation(4.5f);
        this.addChild(menu);
    }
    /**
     * 菜单按钮被点击
     * 必须带有object参数，这样才能反射到该方法
     */
    public void onClick(Object obj){
        System.out.println("菜单点击");
        CommonUtils.changeLayer(new FightLayer());
        SoundEngine.sharedEngine().realesAllSounds();//停止音乐
    }
}
