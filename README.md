# plantsVS.zombies

- logo 运营公司的logo, 开发公司logo, 游戏中的角色
- 加载中
    实现步骤，先将logo图标先隐藏，再延时一秒，再将它显示出来，再延时一秒，最后将它隐藏，实现了这个logo的显示，然后我们跳转图层，到下一个显示界面，也就是欢迎界面。
- 菜单页面
    首先我们初始化背景图片，也就是main_menu_bg.jpg，然后我们就在这张图片作为背景图片的基础上进行添加loading图片，也就是进度条。这个地方我们做了一个工具类，实现了一个方法animate，public static CCAction animate(String format,int num,boolean repeat)有三个参数，第一个参数表示图片的地址信息，例如"image/loading/loading_%02d.png"，而%02d表示的是d表示的整数，而02表示以后出来的数都是这个形式01,02...12这样的形式，第二个参数表示图片数目，而第三个参数表示是否重复循环使用（永远循环），而我们这只使用一遍，故用false,当我们加载完这个loading图片，然后我们就加载开始按钮，新添图片loading_start，当这张图片被点击后，我们就跳转到另一个图层上，实现了开始游戏的功能。所以我们在这里设计了一个监听事件，当图片被点击后，我们就能实现跳转功能。
-开始冒险界面
    当我们跳转到开始冒险的选择界面，首先我们初始化背景，将main_menu_bg.jpg作为背景，我们写一个方法onClick，当图片start_adventure
_default.png在当前节点被单击的时候，我们显示另一张图片被选中的图片start_adventure_press.png，，然后调用onClick方法，onClick方法中实现了跳转到FightLayer图层，就实现了，点击图片，显示被选中图片，最后实现跳转，实现了点击跳转的功能。
    -战斗图层
    首先，我们对图片进行处理，使用Tiled将bk1.jpg图片进行处理，得到一个map_day.tmx文件，我们将地图分为14*6的方块阵，然后对需要放植物和僵尸地方设置点的坐标。得到了map_day.tmx这一个文件。
- 地图移动
    植物大战僵尸的地图有一个偏移量，用屏幕的宽减去图片的宽，得到一个负的偏移量，参照物为屏幕，cocos2d的默认坐标(0,0),在屏幕的左下角，而不是左上角。所以当我们的参照物为屏幕的时候，我们要实现地图的移动，那么屏幕的坐标始终不变(0,0),只能是将地图向左边移动，所以，我们得到的偏移量是负的。
- 展现僵尸
    要展示僵尸，首先，我们要得到展示僵尸位置的坐标点，当我们通过工具类中的loadPoint方法获得了展示僵尸位置的坐标点的时候，我们创建一个ShowZombie类用来实现展示僵尸，但涉及到一个问题:锚点。锚点默认是图片中心位置(0.5,0.5),就会发现僵尸飘在上面。所以我们设置为(0.5,0)
，为两脚之间。紧接着，我们实现僵尸颤抖的动画效果，我们用帧动画，使用两张图片实现，"image/zombies/zombies_1/shake/z_1_%02d.png
"，只是第三个参数选择true，这样我们就实现了僵尸永远颤抖的动画效果。
- 选择植物框
    设置背景图片，计算图片所在的坐标，并获得当前植物的id,设置背景图片为半透明，然后设置展示植物，两个植物位置是一样的。锚点为左下角
- 已选植物框
    锚点为左上角
- 选择/取消选择植物
    选择/取消植物，我们做了一个监听，当我们在植物选择框中点击了(被选择)，我们通过CCMoveTo(绝对移动)类，直接将所选植物弄到植物选择框里。而当我们在已选框中点击所选植物，我们将已选植物移动到原先背景植物的位置，这时候，我们所选的后面的植物会自动向左偏移一格。
    - 开始战斗
- 地图移动回去
- 准备...好...开始
    当我们开始战斗的时候，首先，隐藏选择植物框(选择植物框自杀 removeSelf)，缩放所选框，地图移回去(和移动地图一样，只是偏移量变成正数)，然后我们回收展示的僵尸，显示战斗开始的文字(使用CommonUtils类的animate方法，设置3张图片，不循环播放，0.5s一张)，最后我们将展示字体移除。
- 加载僵尸
    我们设置一个定时器，每隔两秒，执行一次加载的方法，获得起点坐标和终点坐标，随机产生一个僵尸行走(基础动作，永久循环走，7帧)在战线中，我们创建了一个战线类(FightLine)，表示只处理一条战线的东西，其他的都类似。
    基本元素
    攻击型植物-豌豆射手 产物-子弹、太阳 防御型植物-土豆 生产型植物-太阳花 僵尸基类
- 安放植物
    我们获得当前植物的坐标点loadPlantPoints，当所选框被点击，被点击的植物设置半透明的，我们可以安放豌豆射手(PeaPlant)、向日葵(SunPlant)和土豆(Nut),当我们的鼠标落在草评上时，我们判断当前格子中是否有植物(我们通过当前鼠标所点击的格子的横纵坐标是否包含在安放植物的植物坐标点，如果)，如果有，则不能被放置，如果没有，可以安放植物。
- 僵尸攻击植物
    我们做一个定时器，每隔0.2秒检测僵尸是否可以攻击植物，判断僵尸当前所在的列上，是否有植物存在，如果有，僵尸开始攻击该列的植物(战线定义的类，所以我们所得到的战线只是一条)
那我们停止僵尸的所有动作(行走)，开始了咬植物的动画(10帧，永久循环)，每隔一秒,咬一口植物，直到僵尸死亡(执行死亡动画分为有攻击动画的(8帧和6帧，均不循环)和没有攻击动画的(6帧，均不循环，))或者植物死亡(执行僵尸继续行走动画)。
- 植物攻击僵尸
    植物分为攻击型和防御性和生产性植物。所以植物能攻击僵尸，只能是攻击性植物。当前我们的攻击性植物只有豌豆射手，豌豆射手要产生豌豆才能攻击僵尸，首先当前战线存在攻击性植物和僵尸，才会产生子弹。当这两个条件满足了，我们将产生一个弹夹，我们获得当前植物的坐标，从豌豆射手的前面发射出子弹(效果就像是豌豆射手喷射豌豆),然后通过子弹移动的方法进行移动，当子弹处于可以攻击的范围内[-10,10]，当子弹处于僵尸身边的这个范围内，僵尸掉血，然后我们立刻隐藏子弹，让子弹的攻击力为零，当子弹移动结束后，我们将它销毁。
- 进度展示
    我们初始化一个进度条，让它显示在当前屏幕下，从左向右的，水平进度条。每增加一个僵尸更新一下进度条，增加进度5%。
- 太阳花生产太阳 - 收集太阳
    太阳花属于生产性植物，当我们种下太阳花，有左右摆动的动画(8帧，永远循环)每隔10秒产生一个阳光(旋转的阳光),如果5秒后还不收集,就销毁掉
我们收集阳光，当阳光被点击后，我们使用CCMoveTo类方法，将阳光直达阳光收集框，最后将阳光删除，将显示在界面的阳光数的文字增加相应的数目，更新阳光数的文字，这样实现了阳光的收集。
- 声音播放
    当logo出来时，先播放start.mp3音乐，然后当点击开始冒险的时候，start.mp3音乐停止，当到游戏正式开始的时候，播放day.mp3.