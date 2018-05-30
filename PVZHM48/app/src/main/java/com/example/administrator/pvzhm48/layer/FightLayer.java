package com.example.administrator.pvzhm48.layer;

import android.view.MotionEvent;

import com.example.administrator.pvzhm48.R;
import com.example.administrator.pvzhm48.domain.ShowPlant;
import com.example.administrator.pvzhm48.domain.ShowZombie;
import com.example.administrator.pvzhm48.domain.Sun;
import com.example.administrator.pvzhm48.engine.GameEngine;
import com.example.administrator.pvzhm48.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2018/3/16.
 * 战斗图层
 */
public class FightLayer extends BaseLayer {

    private CCTMXTiledMap map;
    private ArrayList<CGPoint> mZombiePoint;// 僵尸坐标点集合
    private CCSprite mSelectedBox;// 已选植物框
    private CCSprite mChooseBox;// 选择植物框
    private boolean isMoving = false;//标记选择的植物是否正在移动
    private CCSprite btnStart;
    private ArrayList<ShowZombie> mShowZombies;// 展示的僵尸集合
    private CCSprite startLabel;
    public  static final  int TAG_SELECTED_BOX = 1;
    public static final int TAG_TOTAL_MONEY = 2;
    private CCLabel label;// 显示阳光数的文字

    private CopyOnWriteArrayList<ShowPlant> mShowPlants;//展示植物的集合
    private CopyOnWriteArrayList<ShowPlant> mSelectedPlants = new CopyOnWriteArrayList<ShowPlant>();//已选植物的集合
    public FightLayer() {
        loadMap();
        loadZombie();
        SoundEngine.sharedEngine().pauseSound();// 暂停音乐
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        this.addChild(map);
        mZombiePoint = CommonUtils.loadPoint(map, "zombies");
        moveMap();
    }

    /**
     * 加载僵尸
     */
    private void loadZombie() {
        mShowZombies = new ArrayList<ShowZombie>();
        for (CGPoint point : mZombiePoint) {
            ShowZombie zombie = new ShowZombie();
            zombie.setPosition(point);
            map.addChild(zombie);
            mShowZombies.add(zombie);
        }
    }

    /**
     * 移动地图
     */
    private void moveMap() {
        float offset = winSize.width - map.getContentSizeRef().width;// 地图移动的偏移量 用屏幕的宽减去图片的宽 得到一个负的偏移量
        System.out.print(winSize.width - map.getContentSizeRef().width);
        CCDelayTime delay = CCDelayTime.action(1);// 延时1秒
        CCMoveBy move = CCMoveBy.action(2, ccp(offset, 0));
        CCSequence s = CCSequence.actions(delay, move, delay,
                CCCallFunc.action(this, "showPlantBox"));
        map.runAction(s);
    }

    /**
     * 展示植物框
     */
    public void showPlantBox() {
        setIsTouchEnabled(true);
        showSelectedBox();
        showChooseBox();
    }

    /**
     * 展示选择植物框
     */
    private void showChooseBox() {
        mChooseBox = CCSprite.sprite("image/fight/chose/fight_choose.png");
        mChooseBox.setAnchorPoint(0, 0);// 锚点为左下角
        this.addChild(mChooseBox);

//      String format = "image/fight/chose/choose_default%02d.png";
        mShowPlants =  new CopyOnWriteArrayList<ShowPlant>();
        for (int i = 1; i <= 9; i++) {
            //添加背景植物和展示植物，位置一样
            ShowPlant showPlant = new ShowPlant(i);
            mChooseBox.addChild(showPlant.getBgPlant());
            mChooseBox.addChild(showPlant.getShowPlant());
            mShowPlants.add(showPlant);
        }
        //开始战斗
        btnStart = CCSprite.sprite("image/fight/chose/fight_start.png");
        btnStart.setPosition(mChooseBox.getContentSize().width/2,30);
        mChooseBox.addChild(btnStart);
    }

    /**
     * 展示已选植物框
     */
    private void showSelectedBox() {
        mSelectedBox = CCSprite.sprite("image/fight/chose/fight_chose.png");
        mSelectedBox.setAnchorPoint(0, 1);// 锚点为左上角
        mSelectedBox.setPosition(0, winSize.height);
        this.addChild(mSelectedBox,0,TAG_SELECTED_BOX);
        //显示阳光数的文字
        label = CCLabel.labelWithString(String.valueOf(Sun.totalMoney),
                "hkbd.ttf", 15);
        label.setColor(ccc3(0, 0, 0));
        label.setPosition(33, CCDirector.sharedDirector().winSize().height - 62);
        this.addChild(label, 1, TAG_TOTAL_MONEY);
    }


    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        if(GameEngine.isStart){//判断游戏是否开始
            GameEngine.getInstance().handleTouch(event);
            return true;
        }

        CGPoint convertTouchToNodeSpace = convertTouchToNodeSpace(event);

        if(CGRect.containsPoint(mChooseBox.getBoundingBox(),
                convertTouchToNodeSpace)){//落在植物选择框
            if(CGRect.containsPoint(btnStart.getBoundingBox(),
                    convertTouchToNodeSpace)){//开始战斗被点击
            gamePrepare();
                return true;
            }
            for(ShowPlant showPlant : mShowPlants){
                if(CGRect.containsPoint(showPlant.getShowPlant().getBoundingBox(),
                        convertTouchToNodeSpace)){//植物选择了
                    if(mSelectedPlants.size()<5 && !isMoving){
                        isMoving = true;
                        mSelectedPlants.add(showPlant);
                        //植物移动到已选框
                        CCMoveTo move = CCMoveTo.action(0.5f,
                                ccp(75+(mSelectedPlants.size()-1)*53,
                                        winSize.height-65));
                        CCSequence s = CCSequence.actions(move,
                                CCCallFunc.action(this, "unlock"));
                        showPlant.getShowPlant().runAction(s);
                    }
                    break;
                }
            }
        }else if(CGRect.containsPoint(mSelectedBox.getBoundingBox(),
                convertTouchToNodeSpace)){//已选框被点击
//            boolean isSelect = false;
            for(ShowPlant showPlant : mShowPlants){
                if(CGRect.containsPoint(showPlant.getShowPlant().getBoundingBox(),
                        convertTouchToNodeSpace)) {//植物选择了
                    CCMoveTo move = CCMoveTo.action(0.5f,showPlant
                            .getBgPlant().getPosition());//移动到背景植物的位置
                    showPlant.getShowPlant().runAction(move);
                    mSelectedPlants.remove(showPlant);//移除取消的植物
//                    isSelect = true;
//                    continue;
                }
//                if (isSelect) {// 有植物被点击
//                    CCMoveBy move = CCMoveBy.action(0.5f, ccp(-53, 0));// 向左偏移
//                    showPlant.getShowPlant().runAction(move);
//                }
            }
        }
        return super.ccTouchesBegan(event);
    }
    //植物选择框移动结束后调用此方法
    public void unlock() {
        isMoving = false;
    }
    /*
    *准备战斗
     */
    private void gamePrepare(){
        setIsTouchEnabled(false);
        System.out.println("准备战斗");
        //隐藏植物框
        mChooseBox.removeSelf();
        //地图移回去
        moveMapBack();
        //缩放已选框
        mSelectedBox.setScale(0.65);
        //重新添加已选的植物
        for (ShowPlant plant : mSelectedPlants) {
            plant.getShowPlant().setScale(0.65f);// 因为父容器缩小了 孩子一起缩小
            plant.getShowPlant().setPosition(
                    plant.getShowPlant().getPosition().x * 0.65f,
                    plant.getShowPlant().getPosition().y
                            + (winSize.height - plant.getShowPlant()
                            .getPosition().y) * 0.35f);// 设置坐标

            this.addChild(plant.getShowPlant());
        }
        //缩放阳光数的文字
        label.setPosition(22, CCDirector.sharedDirector().winSize().height - 42);
        label.setScale(0.65f);
    }
    /*
    * 地图移动回去
    * */
    private void moveMapBack(){
        float offset = map.getContentSizeRef().width - winSize.width;// 地图移动的偏移量

        CCDelayTime delay = CCDelayTime.action(1);// 延时1秒
        CCMoveBy move = CCMoveBy.action(2, ccp(offset, 0));
        CCSequence s = CCSequence.actions(delay, move,delay,CCCallFunc.action(this,"showLabel"));

        map.runAction(s);
    }
    /*
    展示文字
    * */
    public void showLabel(){
        //回收僵尸
        for (ShowZombie zombie : mShowZombies){
            zombie.removeSelf();
        }
        mShowZombies.clear();
        //显示准备开始战斗的文字
        startLabel = CCSprite.sprite("image/fight/startready_01.png");
        startLabel.setPosition(winSize.width/2,winSize.height/2);
        this.addChild(startLabel);
        CCAnimate animate = (CCAnimate) CommonUtils.animate(
                "image/fight/startready_%02d.png",3,false,0.5f);
        CCSequence s = CCSequence.actions(animate,
                CCCallFunc.action(this,"gameBegin"));
        startLabel.runAction(s);
    }
    /*
    * 游戏开始
    * */
    public void gameBegin(){
        startLabel.removeSelf();
        System.out.print("游戏正式开始！");
        setIsTouchEnabled(true);
        GameEngine.getInstance().gameStart(map,mSelectedPlants);
        SoundEngine.sharedEngine().playSound(CCDirector.theApp, R.raw.day,true);//播放音乐
    }

}
