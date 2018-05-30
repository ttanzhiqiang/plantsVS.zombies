package com.example.administrator.pvzhm48.engine;

import com.example.administrator.pvzhm48.domain.base.AttackPlant;
import com.example.administrator.pvzhm48.domain.base.Bullet;
import com.example.administrator.pvzhm48.domain.base.Plant;
import com.example.administrator.pvzhm48.domain.base.Zombie;

import com.example.administrator.pvzhm48.domain.base.BaseElement.DieListener;

import org.cocos2d.actions.CCScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战线
 *
 * @author Kevin
 *
 */
public class FightLine {

    private HashMap<Integer, Plant> mPlants = new HashMap<Integer, Plant>();// key表示植物在第几列

    private CopyOnWriteArrayList<AttackPlant> mAttackPlants = new CopyOnWriteArrayList<AttackPlant>();// 可攻击性植物的集合

    private CopyOnWriteArrayList<Zombie> mZombies = new CopyOnWriteArrayList<Zombie>();// 僵尸集合

    public FightLine(int i) {

        CCScheduler scheduler = CCScheduler.sharedScheduler();
        scheduler.schedule("attackPlant", this, 0.2f, false);// 每隔0.2秒检测僵尸是否可以攻击植物
        scheduler.schedule("createBullet", this, 0.2f, false);// 每隔0.2秒检测是否要产生子弹
        scheduler.schedule("attackZombie", this, 0.2f, false);// 每隔0.2秒检测子弹是否可以攻击僵尸
    }

    /**
     * 产生子弹
     *
     * @param f
     */
    public void createBullet(float f) {
        if (!mZombies.isEmpty() && !mAttackPlants.isEmpty()) {// 有僵尸和攻击性植物
            for (AttackPlant plant : mAttackPlants) {
                plant.createBullet();// 产生子弹
            }
        }
    }

    /**
     * 僵尸攻击植物
     *
     * @param f
     */
    public void attackPlant(float f) {
        if (!mPlants.isEmpty() && !mZombies.isEmpty()) {
            for (Zombie zombie : mZombies) {
                int column = (int) (zombie.getPosition().x / 46 - 1);
                if (mPlants.keySet().contains(column)) {// 僵尸当前所在的列上,有植物存在
                    if (!zombie.isAttacking()) {
                        zombie.attack(mPlants.get(column));// 僵尸开始攻击该列的植物
                        zombie.setAttacking(true);// 标记正在攻击
                    }
                }
            }
        }
    }

    /**
     * 子弹攻击僵尸
     *
     * @param f
     */
    public void attackZombie(float f) {
        if (!mZombies.isEmpty() && !mAttackPlants.isEmpty()) {// 有僵尸和攻击性植物
            for (Zombie zombie : mZombies) {
                int x = (int) zombie.getPosition().x;
                int left = x - 10;
                int right = x + 10;

                for (AttackPlant plant : mAttackPlants) {
                    List<Bullet> bullets = plant.getBullets();// 获取植物的子弹

                    for (Bullet bullet : bullets) {
                        int bx = (int) bullet.getPosition().x;

                        if (bx >= left && bx <= right) {// 子弹处于可攻击的范围内
                            zombie.attacked(bullet.getAttack());// 僵尸掉血了
                            bullet.setVisible(false);// 隐藏子弹
                            bullet.setAttack(0);// 让子弹攻击力为0
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加植物
     *
     * @param plant
     */
    public void addPlant(final Plant plant) {
        plant.setDieListener(new DieListener() {
            @Override
            public void die() {
                mPlants.remove(plant.getColumn());// 移除植物
                mAttackPlants.remove(plant);
            }
        });
        mPlants.put(plant.getColumn(), plant);

        if (plant instanceof AttackPlant) {// 判断是否是可攻击的植物
            mAttackPlants.add((AttackPlant) plant);
        }
    }

    /**
     * 添加僵尸
     *
     * @param zombie
     */
    public void addZombie(final Zombie zombie) {
        // 僵尸的死亡回调
        zombie.setDieListener(new DieListener() {

            @Override
            public void die() {
                mZombies.remove(zombie);// 僵尸死亡后,从集合中移除
            }
        });
        mZombies.add(zombie);
    }

    /**
     * 判断战线上是否已经有植物,有的话就不能再安放
     *
     * @return
     */
    public boolean contaionsPlant(Plant plant) {
        // 1, 5, 8 , 5
        return mPlants.keySet().contains(plant.getColumn());
    }
}
