package com.example.administrator.pvzhm48.domain;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by Administrator on 2018/3/16.
 * 选择植物框的植物
 */

public class ShowPlant {
    CCSprite bgPlant;
    CCSprite showPlant;
    private int id;//表示植物
    String format = "image/fight/chose/choose_default%02d.png";
    public ShowPlant(int i){
        this.id = i;

        //初始化背景图片
        bgPlant = CCSprite.sprite(String.format(format,i));
        bgPlant.setAnchorPoint(0,0);
        float x = (i-1)%4 *54 +16;//计算x坐标
        float y = 175 - (i-1)/4 *59;//计算y坐标
        bgPlant.setPosition(x,y);
        bgPlant.setOpacity(100);//设置为半透明

        //初始化展现的图片
        showPlant = CCSprite.sprite(String.format(format,i));
        showPlant.setAnchorPoint(0,0);
        showPlant.setPosition(bgPlant.getPosition());
    }
    public  CCSprite getBgPlant(){
        return bgPlant;
    }
    public CCSprite getShowPlant(){
        return showPlant;
    }
    public int getId(){
        return id;
    }
}
