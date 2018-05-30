package com.example.administrator.pvzhm48.domain;

import com.example.administrator.pvzhm48.domain.base.DefancePlant;
import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;


/**
 * Created by Administrator on 2018/3/17.
 * 土豆
 */

public class Nut extends DefancePlant {
    public Nut(){
        super("image/plant/nut/p_3_01.png");
        baseAction();
    }
    public void baseAction(){
        CCAction animate = CommonUtils.animate("image/plant/nut/p_3_%02d.png",11,true);
        this.runAction(animate);
    }
}
