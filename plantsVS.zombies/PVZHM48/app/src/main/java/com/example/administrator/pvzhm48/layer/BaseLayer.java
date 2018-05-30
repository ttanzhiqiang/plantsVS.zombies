package com.example.administrator.pvzhm48.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * Created by Administrator on 2018/3/15.
 */

public class BaseLayer extends CCLayer {
    public CGSize winSize = CCDirector.sharedDirector().winSize();//屏幕宽高
    public BaseLayer(){

    }
}
