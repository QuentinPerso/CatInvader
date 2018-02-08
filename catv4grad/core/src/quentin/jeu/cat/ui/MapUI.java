package quentin.jeu.cat.ui;

import static com.badlogic.gdx.math.Interpolation.fade;
import static com.badlogic.gdx.math.Interpolation.pow2Out;
import static com.badlogic.gdx.math.Interpolation.pow5In;
import static com.badlogic.gdx.math.Interpolation.pow5Out;
import static com.badlogic.gdx.math.Interpolation.sine;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.screens.FightScreen;
import quentin.jeu.cat.screens.MainMenu;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.GameData;
import quentin.jeu.cat.utils.Save;
import quentin.jeu.cat.utils.TimeCalc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class MapUI extends InputAdapter {
	public static float sizeratiow=Gdx.graphics.getWidth()/960f;
	public static float sizeratioh=Gdx.graphics.getHeight()/540f;
	public static float aspectratio=(float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
	private static float universSize = 20*sizeratiow*GameData.starnumber+40*sizeratiow;
	private static float pad=5;
	private float scale;
	public  Stage stage,starstage;
	private static int sstageinitw=Gdx.graphics.getWidth(), sstageinith=Gdx.graphics.getHeight();
	private float sstagew=Gdx.graphics.getWidth(), sstageh=Gdx.graphics.getHeight();
	private int  zoomnbr=0;
	private Skin skin;
	public  Music music;
	
	private Table mainMenu, gotoMenu, exploreMenu;
	private Label systemexplored;
	private Label tuto1,tuto2,tuto3 ,tuto4, tuto5,tuto6, tuto7,tuto8, tuto9;
	private Garage1 gar;
	
	public  TextButton menuButton, resumButton, titleButton, journalButton, shopButton, musicButton, soundButton;
	public  TextButton debugButton;
	public  TextButton buy1, buy2, buy3, backbt;
	public  TextButton okjournal;
	public static  String price1,price2,price3;
	private Button goButton, exButton;
	private Table exploretable;
	private int makiwon,metalwon,makiwoni,metalwoni;
	private float rewardTimer;
	private Label makiwont,metalwont,explored, score;
	private TextButton notgoButton, notexButton;
	private Group lab;
	private Image echoImage, rangeImage, targetImage, playerIm, destIm1, destIm2, destIm3, black;
	private Image planet1, planet2, planet3;
	private Array<Image> pathImages;
	
	private ScrollPane map;
	private Group starsTable, buymenu, tutotable, newtec, journal;
	private ScrollPane record;
	private boolean hasScrolled, created=false, zoomed=false;
	private int playerPos=Save.gd.playerPos, playerGo;
	private float playerX, playerY,playerstarsize, range=256;
	private float prevplayerX, prevplayerY, prevplayerstarsize;
	private Vector2 temp1 = new Vector2(), temp2 = new Vector2();
	private Label nrjcostlabel,excost;
	private int nrjcost, event=-1;
	private Label maki, time2maki;
	public  Label metal;
	public TimeCalc timecalc;
	private Image shipbutton;
	
	public  static Sound click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
	public static boolean maki1buy, maki2buy, metalbuy;
	private Sound targ, zoom,no;

	
	public MapUI () {
	/*	Save.gd.visited[CatGame.NORIS2]=false;
		Save.gd.norisdefeated=1;
		Save.gd.playerPos=306;
		Save.gd.playerGo=306;
		Save.gd.eventspeak=true;
		Save.gd.eventdefeatkuro=true;
		Save.gd.eventdefeatcatious=true;
		Save.gd.eventdefeatcatious1=true;
		Save.gd.eventdolphinbegin=true;
		Save.gd.eventshield1=Save.gd.eventshield2=true;
		Save.gd.eventkuro= Save.gd.eventkuro1= Save.gd.eventdest= Save.gd.eventdest1= Save.gd.eventlose=
		Save.gd.eventbosskuro= Save.gd.eventdefeatkuro= Save.gd.eventdefeatkuro1=
		Save.gd.eventbosscatious= Save.gd.eventdefeatcatious= Save.gd.eventdefeatcatious1= Save.gd.eventendcat=
		Save.gd.eventdolphinbegin= Save.gd.eventspeak1= Save.gd.eventRemorse=
		Save.gd.eventshield1= Save.gd.eventshield11= Save.gd.eventshield2= Save.gd.eventshield22= Save.gd.eventspeak=
		Save.gd.eventbossn= Save.gd.eventdefeatn= Save.gd.eventdefeatn1=true;
		Save.gd.eventdest=true;
		Save.gd.eventdest1=true;
		Save.gd.eventendcat=true;
		Save.gd.lzOwn=true;
		Save.gd.lzDisp=true;
		Save.gd.shieldOwn=true;
		Save.gd.smDisp=true;
		Save.gd.smOwn=true;*/
		/*Save.gd.visited[CatGame.NORIS2]=true;
		Save.gd.visited[CatGame.NORIS3]=false;
		Save.gd.norisdefeated=2;
		Save.gd.playerPos=321;
		Save.gd.playerGo=321;*/
		/*Save.gd.playerPos=351;
		Save.gd.playerGo=351;
		Save.gd.visited[CatGame.ANOMALY]=false;
		Save.gd.anomaly=false;
		Save.gd.anomaly1=false;
		Save.gd.norisdefeated=3;
		Save.gd.norisdefeat=true;*/
		
		//shop & services
		CatGame.shop.checkprice();
		if(CatGame.gservices.isSignedIn())
			CatGame.gservices.submitScore(Save.gd.score);
		//sound
		click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		targ  = Gdx.audio.newSound(Gdx.files.internal("sounds/zoom.wav"));
		zoom  = Gdx.audio.newSound(Gdx.files.internal("sounds/zoom2.wav"));
		no    = Gdx.audio.newSound(Gdx.files.internal("sounds/no.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicspace.mp3"));
		music.setLooping(true);
		music.setVolume(0.17f);
		if(Save.gd.musicEnabled) music.play();
		
		//UI
		stage = new Stage();
		starstage = new Stage();
		
		//player position
		prevplayerX = Save.gd.starX[Save.gd.playerGo];
		prevplayerY = Save.gd.starY[Save.gd.playerGo];
		prevplayerstarsize = Save.gd.starsize[Save.gd.playerGo];
		
		loadSkin();
		Image explode= null;
		//event NORIS1
		if(((playerPos==CatGame.NORIS1 && !Save.gd.visited[CatGame.NORIS1]) ||
		   (playerPos==CatGame.NORIS2 && !Save.gd.visited[CatGame.NORIS2]) ||
		   (playerPos==CatGame.NORIS3 && !Save.gd.visited[CatGame.NORIS3]))
		   && Save.gd.norisdefeated==0){
			explode= new Image(Assets.manager.get(Assets.power, Texture.class));
			explode.setTouchable(Touchable.disabled);
			explode.setSize(128, 128);
			explode.setColor(1, 1, 1, 0);
			explode.setVisible(false);
			Save.gd.norisdefeated+=1;
			event=24;
		}
		//event NORIS2
		else if(((playerPos==CatGame.NORIS1 && !Save.gd.visited[CatGame.NORIS1]) ||
		   (playerPos==CatGame.NORIS2 && !Save.gd.visited[CatGame.NORIS2]) ||
		   (playerPos==CatGame.NORIS3 && !Save.gd.visited[CatGame.NORIS3]))
		   && Save.gd.norisdefeated==1){
			explode= new Image(Assets.manager.get(Assets.power, Texture.class));
			explode.setTouchable(Touchable.disabled);
			explode.setSize(128, 128);
			explode.setColor(1, 1, 1, 0);
			explode.setVisible(false);
			Save.gd.norisdefeated+=1;
			event=25;
		}
		//event NORIS3
		else if(((playerPos==CatGame.NORIS1 && !Save.gd.visited[CatGame.NORIS1]) ||
		    (playerPos==CatGame.NORIS2 && !Save.gd.visited[CatGame.NORIS2]) ||
		    (playerPos==CatGame.NORIS3 && !Save.gd.visited[CatGame.NORIS3]))
		   && Save.gd.norisdefeated==2){
			explode= new Image(Assets.manager.get(Assets.power, Texture.class));
			explode.setTouchable(Touchable.disabled);
			explode.setSize(128, 128);
			explode.setColor(1, 1, 1, 0);
			explode.setVisible(false);
			Save.gd.norisdefeated+=1;
			event=26;
		}
		//event endless mode
		if(Save.gd.anomaly && !Save.gd.anomaly1){
			event=28;
		}
		
		create();
		layout();
		events();
		scan();
		
		//player travel
		drawPath(prevplayerX+prevplayerstarsize/2,playerX+playerstarsize/2,prevplayerY+prevplayerstarsize/2,playerY+playerstarsize/2 , Color.GREEN);
		starsTable.addActor(playerIm);
		float angle=temp1.set(playerX+playerstarsize/2,playerY+playerstarsize/2).sub(temp2.set(prevplayerX+prevplayerstarsize/2,prevplayerY+prevplayerstarsize/2)).angle();
		playerIm.addAction(sequence(
				rotateTo(angle-90),
				alpha(1,1),
				moveTo(playerX-16+playerstarsize/2, playerY-16+playerstarsize/2,2)));
		Save.gd.playerGo=Save.gd.playerPos;
		Save.save();
		if(event!=-1)stage.addActor(Scenario.createdialog(event, skin));
		//default explore
		if(Save.gd.visitnbr==1 || Save.gd.explored[playerPos] || event!=-1) exploreMenu.setVisible(false);
		black=new Image(skin.newDrawable("white", Color.BLACK));
		black.setSize(stage.getWidth(), stage.getHeight());
		black.setPosition(0, 0);
		stage.addActor(black);
		black.addAction(sequence(fadeOut(1), hide()));
		
		////animation destruction star
		if(explode!=null){
			
			explode.setPosition(Save.gd.starX[playerPos]-destIm1.getWidth()/2+Save.gd.starsize[playerPos]/2,
					   Save.gd.starY[playerPos]-destIm1.getHeight()/2+Save.gd.starsize[playerPos]/2);
			explode.setOrigin(64, 64);
			starsTable.addActor(explode);
			explode.addAction(sequence(delay(4),show(),
					parallel(alpha(1,0.25f),rotateTo(-10,0.25f)),parallel(alpha(0,0.25f),rotateTo(10,0.25f)),
					parallel(alpha(1,0.25f),rotateTo(-10,0.25f)),parallel(alpha(0,0.25f),rotateTo(10,0.25f)),
					parallel(alpha(1,0.25f),rotateTo(-10,0.25f)),parallel(alpha(0,0.25f),rotateTo(10,0.25f))
					));
			if(planet1!=null) planet1.addAction(sequence(delay(5),alpha(0,1), hide()));
			if(planet2!=null) planet2.addAction(sequence(delay(5),alpha(0,1), hide()));
			if(planet3!=null) planet3.addAction(sequence(delay(5),alpha(0,1), hide()));
			
		}
	}
	
	private void create () {
		
		//================================================STAR MAP STAGE========================================================//
		playerIm    = new Image(Assets.manager.get(Assets.ship, Texture.class));
		playerIm.setSize(32,32);
		playerIm.setOrigin(16, 16);
		playerIm.setPosition(prevplayerX-16+prevplayerstarsize/2, prevplayerY-16+prevplayerstarsize/2);
		playerIm.setTouchable(Touchable.disabled);
		rangeImage  = new Image(Assets.manager.get(Assets.range, Texture.class));
		rangeImage.setSize(range*2, range*2);
		echoImage = new Image(Assets.manager.get(Assets.range, Texture.class));
		echoImage.setColor(1, 1, 1, 0.5f);
		echoImage.setOrigin(echoImage.getHeight()/2,echoImage.getHeight()/2);
		echoImage.addAction(forever(parallel(sequence(scaleTo(0,0),scaleTo(1f,1,1.5f, pow2Out)),
				sequence(alpha(1f,0),alpha(0.1f,1.5f, pow5Out)))));
		
		pathImages = new Array<Image>();
		
		targetImage= new Image(Assets.manager.get(Assets.target, Texture.class));
		targetImage.setTouchable(Touchable.disabled);
		targetImage.setSize(128, 128);
		targetImage.setOrigin(64, 64);
		targetImage.addAction(forever(sequence(fadeOut(0.75f,sine), fadeIn(0.75f,sine))));
		targetImage.setColor(0, 1, 0, 0);
		
		destIm1= new Image(Assets.manager.get(Assets.target, Texture.class));
		destIm1.setTouchable(Touchable.disabled);
		destIm1.setSize(128, 128);
		destIm1.setOrigin(64, 64);
		destIm1.addAction(forever(sequence(fadeOut(0.5f,sine), fadeIn(0.5f,sine))));
		destIm1.setColor(1, 0, 0, 0);
		destIm1.setVisible(false);
		
		destIm2= new Image(Assets.manager.get(Assets.target, Texture.class));
		destIm2.setTouchable(Touchable.disabled);
		destIm2.setSize(128, 128);
		destIm2.setOrigin(64, 64);
		destIm2.addAction(forever(sequence(fadeOut(0.5f,sine), fadeIn(0.5f,sine))));
		destIm2.setColor(1, 0, 0, 0);
		destIm2.setVisible(false);
		
		destIm3= new Image(Assets.manager.get(Assets.target, Texture.class));
		destIm3.setTouchable(Touchable.disabled);
		destIm3.setSize(128, 128);
		destIm3.setOrigin(64, 64);
		destIm3.addAction(forever(sequence(fadeOut(0.5f,sine), fadeIn(0.5f,sine))));
		destIm3.setColor(1, 0, 0, 0);
		destIm3.setVisible(false);
		
		map=new ScrollPane(createStars(GameData.starnumber));
		
		map.setFillParent(true);
		map.getColor().a=0;
		map.addAction(fadeIn(1,pow5In));
		
		//=========================================MAIN STAGE============================================//
		timecalc=new TimeCalc();
		
		score=new Label("Cat Points : "+Integer.toString(Save.gd.score), skin, "small");
		score.setAlignment(Align.center);
		score.getColor().a=0.8f;
		
		//Ressources
		metal = new Label(Integer.toString(Save.gd.metal), skin);
		metal.addAction(forever(sequence(alpha(1f,1,sine),alpha(0.5f,1, sine))));
		time2maki = new Label("88:88" , skin);
		maki  = new Label("000" , skin);
		maki.setText(Integer.toString(Save.gd.maki));
		maki.addAction(forever(sequence(alpha(1f,1,sine),alpha(0.5f,1, sine))));
		
		//Garage
		disctec();
		gar =new Garage1(skin, stage.getWidth(), stage.getHeight(), scale);
		lab=(gar.lab);
		
		
		shipbutton=new Image(Assets.manager.get(Assets.shipb,Texture.class));
		
		//Menu
		menuButton = tbutton("Menu", true);
		menuButton.getColor().a = 0.9f;
		
		resumButton = tbutton (CatGame.myBundle.get("resume")  , true);
		
		titleButton = tbutton (CatGame.myBundle.get("mainmenu"), true);
		journalButton  = tbutton (CatGame.myBundle.get("journal"), false);
		shopButton  = tbutton (CatGame.myBundle.get("shopmenu"), false);
		musicButton = tbutton (CatGame.myBundle.get("music")   , true);
		soundButton = tbutton (CatGame.myBundle.get("sound")   , true);
		musicButton.setChecked(Save.gd.musicEnabled);
		soundButton.setChecked(Save.gd.soundEnabled);
		
		//Sushi Shop
		buy1 = tbutton (price1, false);
		buy2 = tbutton (price2, false);
		buy3 = tbutton (price2, false);
		backbt = tbutton (CatGame.myBundle.get("quit"), false);
		
		//journal
		okjournal = tbutton (CatGame.myBundle.get("ok"), false);
		
		//Go to star
		nrjcostlabel= new Label("(-5", skin);
		nrjcostlabel.setAlignment(Align.left);
		goButton    = new Button (skin);
		goButton.add(" ");
		goButton.add(CatGame.myBundle.get("yes")).center();
		goButton.add("  (");
		goButton.add(nrjcostlabel).pad(pad);
		Image makiIm=new Image(Assets.manager.get(Assets.maki,Texture.class));
		goButton.add(makiIm).size(30*scale).pad(pad);
		goButton.add(") ").pad(pad);
		notgoButton = tbutton (CatGame.myBundle.get("no"), true);
		
		//explore menu
		excost= new Label("(-5", skin);
		excost.setAlignment(Align.left);
		exButton    = new Button (skin);
		exButton.add(" ");
		exButton.add(CatGame.myBundle.get("yes")).center();
		exButton.add("  ");
		exButton.add(excost).pad(pad);
		makiIm=new Image(Assets.manager.get(Assets.maki,Texture.class));
		exButton.add(makiIm).size(30*scale).pad(pad);
		exButton.add(") ").pad(pad);
		notexButton = tbutton (CatGame.myBundle.get("no"), true);
		explored = new Label(CatGame.myBundle.get("explored"),skin);
		explored.setTouchable(Touchable.disabled);
		explored.setAlignment(Align.center);
		explored.getColor().a=0;
		
		exploretable=new Table(skin);
		exploretable.setFillParent(true);
		
		Image metalim=  new Image(Assets.manager.get(Assets.metal,Texture.class));
		metalim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		Image makiim=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiwont =new Label(" : +0",skin);
		metalwont=new Label(" : +0",skin);
		
		TextButton okbutton = tbutton("  ok  ",true);
		okbutton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				exploretable.addAction(sequence(alpha(0, 0.2f, fade), hide()));
			}
		});
		
		exploretable.center().add(CatGame.myBundle.get("sysexplored")).colspan(2).spaceBottom(30).row();
		exploretable.add(metalim).size(40, 40).right().pad(10);
		exploretable.add(metalwont).left().pad(10).row();
		exploretable.add(makiim).size(40, 40).right().pad(10);
		exploretable.add(makiwont).left().pad(10).row();
		//exploretable.center().add(okbutton).spaceTop(20).colspan(2);
		exploretable.getColor().a = 0;
		exploretable.setVisible(false);
		
	}
	
	private void layout () {
		
		//Create menu
		Table menubuttons = new Table();
		menubuttons.defaults().uniformX().fillX();
		menubuttons.defaults().padTop(5);
		menubuttons.add(resumButton).row();
		/*TextButton debugButton = tbutton ("Cine"  , false);
		final Table table = new Table();
		table.setFillParent(true);
		for(int i=0;i<30;i++){
			final int event2=i;
			TextButton button = tbutton (Integer.toString(i)  , false);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					stage.addActor(Scenario.createdialog(event2, skin));
					table.setVisible(false);
					menuButton.setChecked(true);
				}
			});
			if(i==9 || i==19)
				table.add(button).row();
			else
				table.add(button);
		}
		table.setVisible(false);
		
		stage.addActor(table);
		debugButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.setVisible(true);
				menuButton.setChecked(false);
			}
		});
		menubuttons.add(debugButton).row();*/
		menubuttons.add(journalButton).row();
		menubuttons.add(shopButton).row();
		menubuttons.add(musicButton).row();
		menubuttons.add(soundButton).row();
		menubuttons.add(titleButton);
		
		//Menu layout
		mainMenu = new Table(skin);
		mainMenu.setFillParent(true);
		
		mainMenu.center().add(menubuttons).center().padTop(10).colspan(1);
		mainMenu.setVisible(false);
		
		gotoMenu = new Table(skin);
		systemexplored = new Label(CatGame.myBundle.get("notexplored"), skin, "small");
		gotoMenu.setFillParent(true);
		gotoMenu.add(CatGame.myBundle.get("go")).colspan(2).row();
		gotoMenu.add(systemexplored).colspan(2).row();
		gotoMenu.defaults().uniform().fill();
		gotoMenu.defaults().center().padTop(5);
		gotoMenu.add(goButton).pad(5);
		gotoMenu.add(notgoButton).pad(5).row();
		gotoMenu.setVisible(false);
		gotoMenu.getColor().a=0;
		
		exploreMenu = new Table(skin);
		exploreMenu.setFillParent(true);
		exploreMenu.add(CatGame.myBundle.get("explore")).colspan(2).row();
		exploreMenu.defaults().uniform().fill();
		exploreMenu.defaults().center().padTop(5);
		exploreMenu.add(exButton).pad(5);
		exploreMenu.add(notexButton).pad(5).row();
		exploreMenu.center().add(explored).center().colspan(2).row();
		
		Image metalim= new Image(Assets.manager.get(Assets.metal,Texture.class));
		metalim.addAction(forever(sequence(rotateTo(-4,1,sine),rotateTo(4,1,sine))));
		metalim.setSize(60*sizeratiow, 60*sizeratiow);
		
		metal.setPosition  (pad*sizeratiow +metal.getWidth()/2, (60/2+pad)*sizeratiow-maki.getHeight()/2);
		metalim.setPosition(pad*sizeratiow                    , pad*sizeratiow);
		
		Image makiim=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiim.setSize(60*sizeratiow, 60*sizeratiow);
		
		time2maki.setPosition(stage.getWidth()-(pad+70)*sizeratiow-time2maki.getWidth(), (60/2+pad)*sizeratiow-maki.getHeight()/2);
		maki.setPosition     (stage.getWidth()-(pad+65)*sizeratiow+maki.getWidth()/2   , (60/2+pad)*sizeratiow-maki.getHeight()/2);
		makiim.setPosition   (stage.getWidth()-(pad+60)*sizeratiow                     , pad*sizeratiow);
		
		starstage.addActor(map);
		
		menuButton.setSize(100*scale, 40*scale);
		menuButton.setPosition(pad, stage.getHeight()-menuButton.getHeight()-pad);
		stage.addActor(menuButton);
		shipbutton.setSize(100*scale, 40*scale);
		shipbutton.setPosition(stage.getWidth()-shipbutton.getWidth()-pad, stage.getHeight()-shipbutton.getHeight()-pad);
		shipbutton.setOrigin(100*scale, 40*scale);
		shipbutton.addAction(forever(sequence(sizeTo(110*scale, 44*scale,1f,sine),sizeTo(100*scale, 40*scale,1f,sine))));
		
		score.setPosition(stage.getWidth()/2-score.getWidth()/2, stage.getHeight()-menuButton.getHeight()/2-score.getHeight()/2-pad);
		stage.addActor(score);
		
		stage.addActor(lab);
		stage.addActor(shipbutton);
		stage.addActor(mainMenu);
		stage.addActor(gotoMenu);
		stage.addActor(exploreMenu);
		stage.addActor(exploretable);
		stage.addActor(time2maki);
		stage.addActor(makiim);
		stage.addActor(maki);
		stage.addActor(metalim);
		stage.addActor(metal);
		
		checksenario();
		createshop();
		createjournal();
		
		stage.addActor(buymenu);
		stage.addActor(journal);
	}
	
	private void createshop(){
		buymenu = new Group();
		Image bgbuy1 = new Image(skin.newDrawable("white", Color.BLACK));
		bgbuy1.setSize(stage.getWidth()/2, stage.getHeight()*3/4);
		bgbuy1.setPosition(stage.getWidth()/4, stage.getHeight()/8);
		bgbuy1.getColor().a=0.9f;
		Image bgbuy2 = new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		bgbuy2.setSize(stage.getWidth()/2-stage.getWidth()/60, stage.getHeight()*3/4-stage.getWidth()/60);
		bgbuy2.setPosition(stage.getWidth()/4+stage.getWidth()/120, stage.getHeight()/8+stage.getWidth()/120);
		bgbuy2.getColor().a=0.2f;
		buymenu.addActor(bgbuy1);
		buymenu.addActor(bgbuy2);
		
		Label buytitle=new Label(CatGame.myBundle.get("sushishop"), skin);
		buytitle.setPosition(stage.getWidth()/2-buytitle.getWidth()/2, stage.getHeight()*3/4-pad/2);
		buymenu.addActor(buytitle);
		
		Image makiimb1=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiimb1.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiimb1.setSize(60, 60);
		makiimb1.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*3/5);
		buymenu.addActor(makiimb1);
		Label buynbr1 = new Label("x15", skin);
		buynbr1.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), makiimb1.getY()+makiimb1.getHeight()/2-buynbr1.getHeight()/2);
		buymenu.addActor(buynbr1);
		buy1.setSize(100*scale, 40*scale);
		buy1.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy1.getWidth(), makiimb1.getY()+makiimb1.getHeight()/2-buy1.getHeight()/2);
		buymenu.addActor(buy1);
		Label buytxt1 = new Label(CatGame.myBundle.get("maki1"), skin, "small");
		buytxt1.setPosition((buy1.getX()+(buynbr1.getX()+buynbr1.getWidth()))/2-buytxt1.getWidth()/2, makiimb1.getY()+makiimb1.getHeight()/2-buytxt1.getHeight()/2);
		buymenu.addActor(buytxt1);
		
		Image makiimb2=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiimb2.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiimb2.setSize(60, 60);
		makiimb2.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*2/5);
		buymenu.addActor(makiimb2);
		Label buynbr2 = new Label("x40", skin);
		buynbr2.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), makiimb2.getY()+makiimb2.getHeight()/2-buynbr2.getHeight()/2);
		buymenu.addActor(buynbr2);
		buy2.setSize(100*scale, 40*scale);
		buy2.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy1.getWidth(), makiimb2.getY()+makiimb2.getHeight()/2-buy2.getHeight()/2);
		buymenu.addActor(buy2);
		Label buytxt2 = new Label(CatGame.myBundle.get("maki2"), skin, "small");
		buytxt2.setPosition((buy2.getX()+(buynbr2.getX()+buynbr2.getWidth()))/2-buytxt2.getWidth()/2, makiimb2.getY()+makiimb2.getHeight()/2-buytxt2.getHeight()/2);
		buymenu.addActor(buytxt2);
		
		Image metalimb=  new Image(Assets.manager.get(Assets.metal,Texture.class));
		metalimb.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		metalimb.setSize(60, 60);
		metalimb.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*1/5);
		buymenu.addActor(metalimb);
		Label buynbr3 = new Label("x20", skin);
		buynbr3.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), metalimb.getY()+metalimb.getHeight()/2-buynbr3.getHeight()/2);
		buymenu.addActor(buynbr3);
		buy3.setSize(100*scale, 40*scale);
		buy3.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy3.getWidth(), metalimb.getY()+metalimb.getHeight()/2-buy3.getHeight()/2);
		buymenu.addActor(buy3);
		Label buytxt3 = new Label(CatGame.myBundle.get("metal"), skin, "small");
		buytxt3.setPosition((buy3.getX()+(buynbr3.getX()+buynbr3.getWidth()))/2-buytxt3.getWidth()/2, metalimb.getY()+metalimb.getHeight()/2-buytxt3.getHeight()/2);
		buymenu.addActor(buytxt3);

		backbt.setSize(100*scale, 40*scale);
		backbt.setPosition(stage.getWidth()/2-backbt.getWidth()/2, bgbuy2.getY()+pad);
		buymenu.addActor(backbt);
		
		buymenu.getColor().a=0;
		buymenu.setVisible(false);
		
	}
	
	private void createjournal(){
		journal = new Group();
		Image bgjrnl1 = new Image(skin.newDrawable("white", Color.BLACK));
		bgjrnl1.setSize(stage.getWidth()*3/4, stage.getHeight()*3/4);
		bgjrnl1.setPosition(stage.getWidth()/8, stage.getHeight()/8);
		bgjrnl1.getColor().a=0.9f;
		Image bgjrnl2 = new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		bgjrnl2.setSize(stage.getWidth()*3/4-stage.getWidth()/60, stage.getHeight()*3/4-stage.getWidth()/60);
		bgjrnl2.setPosition(stage.getWidth()/8+stage.getWidth()/120, stage.getHeight()/8+stage.getWidth()/120);
		float jrnltop=stage.getHeight()/8+stage.getWidth()/120+stage.getHeight()*3/4-stage.getWidth()/60;
		bgjrnl2.getColor().a=0.2f;
		journal.addActor(bgjrnl1);
		journal.addActor(bgjrnl2);
		
		Label journaltitle=new Label(CatGame.myBundle.get("journal"), skin);
		journaltitle.setPosition(stage.getWidth()/2-journaltitle.getWidth()/2, jrnltop-journaltitle.getHeight()-pad);
		journal.addActor(journaltitle);
		TextButton recordbt= tbutton("  Record  ", true);
		recordbt.setPosition(stage.getWidth()/8+stage.getWidth()/120+pad, journaltitle.getY()-recordbt.getHeight());
		journal.addActor(recordbt);
		TextButton missionbt= tbutton(" Mission ", true);
		missionbt.setPosition(stage.getWidth()/8+stage.getWidth()/120+2*pad+recordbt.getWidth(), journaltitle.getY()-missionbt.getHeight());
		journal.addActor(missionbt);
		new ButtonGroup<Button>(recordbt, missionbt);
		recordbt.setChecked(true);
		
		okjournal.setSize(100*scale, 40*scale);
		okjournal.setPosition(stage.getWidth()/2-okjournal.getWidth()/2, bgjrnl2.getY()+pad);
		journal.addActor(okjournal);
			
		final Label journaltxt= new Label(setjrnltxt(), skin,"small");
		
		record=new ScrollPane(journaltxt);
		record.setBounds(stage.getWidth()/8+stage.getWidth()/120+pad, okjournal.getY()+okjournal.getHeight()+2*pad,
				stage.getWidth()*3/4-stage.getWidth()/60-2*pad,
				recordbt.getY()-pad-(okjournal.getY()+okjournal.getHeight()+2*pad));
		journal.addActor(record);
		
		final ScrollPane missionspane=new ScrollPane(setmissiontxt());
		missionspane.setBounds(stage.getWidth()/8+stage.getWidth()/120+pad, okjournal.getY()+okjournal.getHeight()+2*pad,
				stage.getWidth()*3/4-stage.getWidth()/60-2*pad,
				recordbt.getY()-pad-(okjournal.getY()+okjournal.getHeight()+2*pad));
		journal.addActor(missionspane);
		missionspane.getColor().a=0;
		missionspane.setVisible(false);
		
		missionbt.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				//journaltxt.clearActions();
				//missionspane.clearActions();
				if (missionspane.isVisible()){
					missionspane.addAction(sequence(alpha(0, 0.1f, fade), hide()));
				}
				else{
					missionspane.addAction(sequence(show(), alpha(1, 0.1f, fade)));
				}
			}
		});
		Save.save();
		recordbt.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				//missionspane.clearActions();
				//journaltxt.clearActions();
				if (journaltxt.isVisible()){
					journaltxt.addAction(sequence(alpha(0, 0.1f, fade), hide()));
				}
				else{
					journaltxt.addAction(sequence(show(), alpha(1, 0.1f, fade)));
				}
			}
		});
		
		//pane.setFlickScroll(flickScroll);
		journal.getColor().a=0;
		journal.setVisible(false);
	}
	
	private String setjrnltxt(){
		String journaltxt1=null;
		if(Save.gd.visitnbr<4)
			journaltxt1= 
			//tuto
			 "- Commandant Kuroneko : \n"
			+"      " + CatGame.myBundle.get("kuro1") +"\n"
			+"      " + CatGame.myBundle.get("kuro2") +"\n"
			+"      " + CatGame.myBundle.get("kuro3") +"\n"
			+"- Nanako : \n"
			+"      " + CatGame.myBundle.get("nanak1") +"\n"
			+"      " + CatGame.myBundle.get("nanak2") +"\n"
			+"- Commandant Kuroneko : \n"
			+"      " + CatGame.myBundle.get("kuro4") +"\n"
			+"      " + CatGame.myBundle.get("kuro5") +"\n"
			+"      " + CatGame.myBundle.get("kuro6") +"\n"
			+"      " + CatGame.myBundle.get("kuro7") +"\n"
			+"      " + CatGame.myBundle.get("kuro8") +"\n"
			+"      " + CatGame.myBundle.get("kuro9") +"\n"
			+"- Nanako : \n"
			+"      " + CatGame.myBundle.get("nanak3") +"\n\n"
			//blabla1
			+"- Nanako : \n"
			+"      " + CatGame.myBundle.get("nanak5") +"\n"
			+"      " + CatGame.myBundle.get("nanak6") +"\n"
			+"      " + CatGame.myBundle.get("nanak7") +"\n"
			+"      " + CatGame.myBundle.get("nanak8") +"\n"
			+"      " + CatGame.myBundle.get("nanak9") +"\n\n"
		;
	else if(Save.gd.visitnbr<5)
		journaltxt1= journaltxt1
				+"      " + CatGame.myBundle.get("kuro1") +"\n"
				+"      " + CatGame.myBundle.get("kuro2") +"\n"
				+"      " + CatGame.myBundle.get("kuro3") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak1") +"\n"
				+"      " + CatGame.myBundle.get("nanak2") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro4") +"\n"
				+"      " + CatGame.myBundle.get("kuro5") +"\n"
				+"      " + CatGame.myBundle.get("kuro6") +"\n"
				+"      " + CatGame.myBundle.get("kuro7") +"\n"
				+"      " + CatGame.myBundle.get("kuro8") +"\n"
				+"      " + CatGame.myBundle.get("kuro9") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak3") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak5") +"\n"
				+"      " + CatGame.myBundle.get("nanak6") +"\n"
				+"      " + CatGame.myBundle.get("nanak7") +"\n"
				+"      " + CatGame.myBundle.get("nanak8") +"\n"
				+"      " + CatGame.myBundle.get("nanak9") +"\n\n"
				//blabla2
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak13") +"\n"
				+"      " + CatGame.myBundle.get("nanak14") +"\n"
				+"      " + CatGame.myBundle.get("nanak15") +"\n"
				+"      " + CatGame.myBundle.get("nanak16") +"\n"
				+"      " + CatGame.myBundle.get("nanak17") +"\n"
				+"      " + CatGame.myBundle.get("nanak18") +"\n\n"
			;
	
	else if(Save.gd.visitnbr<8 )
		journaltxt1= "- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro1") +"\n"
				+"      " + CatGame.myBundle.get("kuro2") +"\n"
				+"      " + CatGame.myBundle.get("kuro3") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak1") +"\n"
				+"      " + CatGame.myBundle.get("nanak2") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro4") +"\n"
				+"      " + CatGame.myBundle.get("kuro5") +"\n"
				+"      " + CatGame.myBundle.get("kuro6") +"\n"
				+"      " + CatGame.myBundle.get("kuro7") +"\n"
				+"      " + CatGame.myBundle.get("kuro8") +"\n"
				+"      " + CatGame.myBundle.get("kuro9") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak3") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak5") +"\n"
				+"      " + CatGame.myBundle.get("nanak6") +"\n"
				+"      " + CatGame.myBundle.get("nanak7") +"\n"
				+"      " + CatGame.myBundle.get("nanak8") +"\n"
				+"      " + CatGame.myBundle.get("nanak9") +"\n\n"
				//blabla3
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak13") +"\n"
				+"      " + CatGame.myBundle.get("nanak14") +"\n"
				+"      " + CatGame.myBundle.get("nanak15") +"\n"
				+"      " + CatGame.myBundle.get("nanak16") +"\n"
				+"      " + CatGame.myBundle.get("nanak17") +"\n"
				+"      " + CatGame.myBundle.get("nanak18") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak10") +"\n"
				+"      " + CatGame.myBundle.get("nanak11") +"\n"
				+"      " + CatGame.myBundle.get("nanak12") +"\n\n"
			;
	else if(Save.gd.visitnbr>=8)
		journaltxt1= "- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro1") +"\n"
				+"      " + CatGame.myBundle.get("kuro2") +"\n"
				+"      " + CatGame.myBundle.get("kuro3") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak1") +"\n"
				+"      " + CatGame.myBundle.get("nanak2") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro4") +"\n"
				+"      " + CatGame.myBundle.get("kuro5") +"\n"
				+"      " + CatGame.myBundle.get("kuro6") +"\n"
				+"      " + CatGame.myBundle.get("kuro7") +"\n"
				+"      " + CatGame.myBundle.get("kuro8") +"\n"
				+"      " + CatGame.myBundle.get("kuro9") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak3") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak5") +"\n"
				+"      " + CatGame.myBundle.get("nanak6") +"\n"
				+"      " + CatGame.myBundle.get("nanak7") +"\n"
				+"      " + CatGame.myBundle.get("nanak8") +"\n"
				+"      " + CatGame.myBundle.get("nanak9") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak13") +"\n"
				+"      " + CatGame.myBundle.get("nanak14") +"\n"
				+"      " + CatGame.myBundle.get("nanak15") +"\n"
				+"      " + CatGame.myBundle.get("nanak16") +"\n"
				+"      " + CatGame.myBundle.get("nanak17") +"\n"
				+"      " + CatGame.myBundle.get("nanak18") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak10") +"\n"
				+"      " + CatGame.myBundle.get("nanak11") +"\n"
				+"      " + CatGame.myBundle.get("nanak12") +"\n\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak19") +"\n"
				+"      " + CatGame.myBundle.get("nanak20") +"\n"
				+"      " + CatGame.myBundle.get("nanak21") +"\n"
				+"      " + CatGame.myBundle.get("nanak22") +"\n\n"
			;
	
	if(Save.gd.eventkuro)
		journaltxt1= journaltxt1
				//eventkuro1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak23") +"\n"
				+"      " + CatGame.myBundle.get("nanak24") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro11") +"\n"
				+"      " + CatGame.myBundle.get("kuro12") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak25") +"\n"
				+"      " + CatGame.myBundle.get("nanak26") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro13") +"\n\n"
			;
	
	if(Save.gd.eventdest)
		journaltxt1= journaltxt1
				//eventdest1
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious1") +"\n"
				+"      " + CatGame.myBundle.get("catious2") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak27") +"\n"
				+"      " + CatGame.myBundle.get("nanak28") +"\n"
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious3") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro14") +"\n"
				+"      " + CatGame.myBundle.get("kuro15") +"\n"
				+"      " + CatGame.myBundle.get("kuro16") +"\n"
				+"      " + CatGame.myBundle.get("kuro17") +"\n"
				+"      " + CatGame.myBundle.get("kuro18") +"\n"
				+"      " + CatGame.myBundle.get("kuro19") +"\n"
				+"      " + CatGame.myBundle.get("kuro20") +"\n"
				+"      " + CatGame.myBundle.get("kuro21") +"\n"
				+"      " + CatGame.myBundle.get("kuro22") +"\n"
				+"      " + CatGame.myBundle.get("kuro23") +"\n"
				+"      " + CatGame.myBundle.get("kuro24") +"\n"
				+"      " + CatGame.myBundle.get("kuro25") +"\n"
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious4") +"\n"
				+"      " + CatGame.myBundle.get("catious5") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro26") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak29") +"\n"
				+"      " + CatGame.myBundle.get("nanak30") +"\n"
				+"      " + CatGame.myBundle.get("nanak31") +"\n"
				+"      " + CatGame.myBundle.get("nanak32") +"\n"
				+"      " + CatGame.myBundle.get("nanak33") +"\n"
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious6") +"\n\n"
			;
	
	if(Save.gd.playerPosEvent!=CatGame.EVENTDEST && Save.gd.eventdest && !Save.gd.eventdest1)
		journaltxt1= journaltxt1
				//bilabial2
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak34") +"\n"
				+"      " + CatGame.myBundle.get("nanak35") +"\n"
				+"      " + CatGame.myBundle.get("nanak36") +"\n\n"
			;
	
	if(Save.gd.eventbosskuro)
		journaltxt1= journaltxt1
				//boss1
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro27") +"\n"
				+"      " + CatGame.myBundle.get("kuro28") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak37") +"\n"
				+"      " + CatGame.myBundle.get("nanak38") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro29") +"\n"
				+"      " + CatGame.myBundle.get("kuro30") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak39") +"\n"
				+"- Commandant Kuroneko : \n"
				+"      " + CatGame.myBundle.get("kuro31") +"\n"
				+"      " + CatGame.myBundle.get("kuro32") +"\n\n"
				
			;
	if(Save.gd.eventdefeatkuro)
		journaltxt1=journaltxt1
				//end boss1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak40") +"\n"
				+"      " + CatGame.myBundle.get("nanak41") +"\n"
				+"      " + CatGame.myBundle.get("nanak42") +"\n"
				+"      " + CatGame.myBundle.get("nanak43") +"\n\n";
	if(Save.gd.eventbosscatious)
		journaltxt1= journaltxt1
				//boss CATIOUS
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious7") +"\n"
				+"      " + CatGame.myBundle.get("catious8") +"\n"
				+"      " + CatGame.myBundle.get("catious9") +"\n"
				+"      " + CatGame.myBundle.get("catious10") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak44") +"\n"
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious11") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak45") +"\n"
				+"- General Catious : \n"
				+"      " + CatGame.myBundle.get("catious12") +"\n\n"
			;
	if(Save.gd.eventdefeatcatious)
		journaltxt1= journaltxt1
				//end boss CATIOUS
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak46") +"\n"
				+"      " + CatGame.myBundle.get("nanak47") +"\n"
				+"      " + CatGame.myBundle.get("nanak48") +"\n"
				+"      " + CatGame.myBundle.get("nanak49") +"\n\n"
			;
	if(Save.gd.eventendcat)
		journaltxt1= journaltxt1
				//end cats
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak50") +"\n"
				+"      " + CatGame.myBundle.get("nanak51") +"\n"
				+"      " + CatGame.myBundle.get("nanak52") +"\n"
				+"      " + CatGame.myBundle.get("nanak53") +"\n\n"
			;
	if(Save.gd.explornbr>=1)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak54") +"\n"
				+"      " + CatGame.myBundle.get("nanak55") +"\n"
				+"      " + CatGame.myBundle.get("nanak56") +"\n"
				+"      " + CatGame.myBundle.get("nanak57") +"\n"
				+"      " + CatGame.myBundle.get("nanak58") +"\n"
				+"      " + CatGame.myBundle.get("nanak59") +"\n\n"
			;
	if(Save.gd.explornbr>=2)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak60") +"\n"
				+"      " + CatGame.myBundle.get("nanak61") +"\n"
				+"      " + CatGame.myBundle.get("nanak62") +"\n"
				+"      " + CatGame.myBundle.get("nanak63") +"\n"
				+"      " + CatGame.myBundle.get("nanak64") +"\n"
				+"      " + CatGame.myBundle.get("nanak65") +"\n\n"
			;
	if(Save.gd.eventspeak1)
		journaltxt1= journaltxt1
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin1") +"\n"
				+"      " + CatGame.myBundle.get("dolphin2") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak66") +"\n"
				+"      " + CatGame.myBundle.get("nanak67") +"\n"
				+"      " + CatGame.myBundle.get("nanak68") +"\n"
				+"      " + CatGame.myBundle.get("nanak69") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin3") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak70") +"\n\n"
			;
	if(Save.gd.explornbr>=2)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak71") +"\n"
				+"      " + CatGame.myBundle.get("nanak72") +"\n"
				+"      " + CatGame.myBundle.get("nanak73") +"\n"
				+"      " + CatGame.myBundle.get("nanak74") +"\n"
				+"      " + CatGame.myBundle.get("nanak75") +"\n\n"
			;
	if(Save.gd.eventshield11)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak76") +"\n"
				+"      " + CatGame.myBundle.get("nanak77") +"\n"
				+"      " + CatGame.myBundle.get("nanak78") +"\n\n"
			;
	if(Save.gd.eventshield22)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak79") +"\n"
				+"      " + CatGame.myBundle.get("nanak80") +"\n\n"
			;
	if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak82") +"\n"
				+"      " + CatGame.myBundle.get("nanak83") +"\n"
				+"      " + CatGame.myBundle.get("nanak84") +"\n"
				+"      " + CatGame.myBundle.get("nanak85") +"\n\n"
			;
	if(Save.gd.eventspeak)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak86") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin4") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak87") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin5") +"\n"
				+"      " + CatGame.myBundle.get("dolphin6") +"\n"
				+"      " + CatGame.myBundle.get("dolphin7") +"\n"
				+"      " + CatGame.myBundle.get("dolphin8") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak88") +"\n"
				+"      " + CatGame.myBundle.get("nanak89") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin9") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak90") +"\n"
				+"      " + CatGame.myBundle.get("nanak91") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin10") +"\n"
				+"      " + CatGame.myBundle.get("dolphin11") +"\n"
				+"      " + CatGame.myBundle.get("dolphin12") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak91") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin13") +"\n"
				+"      " + CatGame.myBundle.get("dolphin14") +"\n"
				+"      " + CatGame.myBundle.get("dolphin15") +"\n"
				+"      " + CatGame.myBundle.get("dolphin16") +"\n"
				+"      " + CatGame.myBundle.get("dolphin17") +"\n"
				+"      " + CatGame.myBundle.get("dolphin18") +"\n"
				+"      " + CatGame.myBundle.get("dolphin19") +"\n"
				+"      " + CatGame.myBundle.get("dolphin20") +"\n"
				+"      " + CatGame.myBundle.get("dolphin21") +"\n"
				+"      " + CatGame.myBundle.get("dolphin22") +"\n"
				+"      " + CatGame.myBundle.get("dolphin23") +"\n"
				+"      " + CatGame.myBundle.get("dolphin24") +"\n"
				+"      " + CatGame.myBundle.get("dolphin25") +"\n"
				+"      " + CatGame.myBundle.get("dolphin26") +"\n"
				+"      " + CatGame.myBundle.get("dolphin27") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak93") +"\n"
				+"      " + CatGame.myBundle.get("nanak94") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin28") +"\n"
				+"      " + CatGame.myBundle.get("dolphin29") +"\n"
			;
	if(Save.gd.norisdefeated>=1)
		journaltxt1= journaltxt1
		+"- Nanako : \n"
		+"      " + CatGame.myBundle.get("nanak95") +"\n"
		+"      " + CatGame.myBundle.get("nanak96") +"\n\n"
		;
	if(Save.gd.norisdefeated>=2)
		journaltxt1= journaltxt1
		+"- Nanako : \n"
		+"      " + CatGame.myBundle.get("nanak97") +"\n"
		+"      " + CatGame.myBundle.get("nanak98") +"\n\n"
		;
	if(Save.gd.norisdefeated>=3)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak99") +"\n"
				+"      " + CatGame.myBundle.get("nanak100") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin30") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak101") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin31") +"\n"
				+"      " + CatGame.myBundle.get("dolphin32") +"\n"
				+"      " + CatGame.myBundle.get("dolphin33") +"\n"
				+"      " + CatGame.myBundle.get("dolphin34") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak102") +"\n"
				+"      " + CatGame.myBundle.get("nanak103") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin35") +"\n"
				+"      " + CatGame.myBundle.get("dolphin36") +"\n"
				+"      " + CatGame.myBundle.get("dolphin37") +"\n"
				+"      " + CatGame.myBundle.get("dolphin38") +"\n"
				+"      " + CatGame.myBundle.get("dolphin39") +"\n"
				+"      " + CatGame.myBundle.get("dolphin40") +"\n"
				+"      " + CatGame.myBundle.get("dolphin41") +"\n"
				+"      " + CatGame.myBundle.get("dolphin42") +"\n"
				+"      " + CatGame.myBundle.get("dolphin43") +"\n"
				+"      " + CatGame.myBundle.get("dolphin44") +"\n\n"
			;
	if(Save.gd.anomaly)
		journaltxt1= journaltxt1
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak104") +"\n"
				+"      " + CatGame.myBundle.get("nanak105") +"\n"
				+"      " + CatGame.myBundle.get("nanak106") +"\n"
				+"      " + CatGame.myBundle.get("nanak107") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin45") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak108") +"\n"
				+"      " + CatGame.myBundle.get("nanak109") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin46") +"\n"
				+"      " + CatGame.myBundle.get("dolphin47") +"\n"
				+"      " + CatGame.myBundle.get("dolphin48") +"\n"
				+"      " + CatGame.myBundle.get("dolphin49") +"\n"
				+"      " + CatGame.myBundle.get("dolphin50") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak110") +"\n"
				+"      " + CatGame.myBundle.get("nanak111") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin51") +"\n"
				+"      " + CatGame.myBundle.get("dolphin52") +"\n"
				+"      " + CatGame.myBundle.get("dolphin53") +"\n"
				+"      " + CatGame.myBundle.get("dolphin54") +"\n"
				+"      " + CatGame.myBundle.get("dolphin55") +"\n"
				+"      " + CatGame.myBundle.get("dolphin56") +"\n"
				+"- Nanako : \n"
				+"      " + CatGame.myBundle.get("nanak112") +"\n"
				+"      " + CatGame.myBundle.get("nanak113") +"\n"
				+"      " + CatGame.myBundle.get("nanak114") +"\n"
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin57") +"\n\n"
			;
	if(Save.gd.anomaly1)
		journaltxt1= journaltxt1
				+"- Dolphin : \n"
				+"      " + CatGame.myBundle.get("dolphin58") +"\n"
				+"      " + CatGame.myBundle.get("dolphin59") +"\n"
				+"      " + CatGame.myBundle.get("dolphin60") +"\n"
				+"      " + CatGame.myBundle.get("dolphin61") +"\n"
				+"      " + CatGame.myBundle.get("dolphin62") +"\n"
				+"      " + CatGame.myBundle.get("dolphin63") +"\n"
				+"      " + CatGame.myBundle.get("dolphin64") +"\n\n"
			;
	
	return journaltxt1;
	}
	
	private Group setmissiontxt(){
		
		Group missiontxt = new Group();
		Label tuto=new Label("- Tutorial : \n"
				+"      " + CatGame.myBundle.get("tuto1") +"\n"
				+"      " + CatGame.myBundle.get("tuto2") +"\n"
				+"      " + CatGame.myBundle.get("tuto3") +"\n"
				+"      " + CatGame.myBundle.get("tuto4") +"\n"
				+"      " + CatGame.myBundle.get("tuto5") +"\n"
				+"      " + CatGame.myBundle.get("tuto6") +"\n"
				+"      " + CatGame.myBundle.get("tuto7") +"\n", skin,"small");
		Label mission1=new Label("- Mission  1 : \n"
				+"      " + CatGame.myBundle.get("mission1") +"\n", skin,"small");//find button
		Label mission2=new Label("- Mission  2 : \n"
				+"      " + CatGame.myBundle.get("mission2") +"\n", skin,"small");//find destination
		Label mission3=new Label("- Mission  3 : \n"
				+"      " + CatGame.myBundle.get("mission3") +" ("+ Integer.toString(Save.gd.metalspent)+"/120) \n", skin,"small");//upgrade 120
		Label missionb=new Label("- Mission  3.1 : \n"
				+"      " + CatGame.myBundle.get("missionb") +" ("+ Integer.toString(Save.gd.probes)+"/30) \n", skin,"small");//run from cats
		Label mission4=new Label("- Mission  4 : \n"
				+"      " + CatGame.myBundle.get("mission4") +"\n", skin,"small");//run from cats
		Label mission5=new Label("- Mission  5 : \n"
				+"      " + CatGame.myBundle.get("mission5") +"\n", skin,"small");//defeat KURO
		Label mission6=new Label("- Mission  6 : \n"
				+"      " + CatGame.myBundle.get("mission6") +"\n", skin,"small");//defeat CATIOUS
		Label mission7=new Label("- Mission  7 : \n"
				+"      " + CatGame.myBundle.get("mission7") +"\n", skin,"small");//find dolphin
		Label mission8=new Label("- Mission  8 : \n"
				+"      " + CatGame.myBundle.get("mission8") +" ("+ Integer.toString(Save.gd.explornbr)+"/"+Integer.toString(CatGame.RUINNBRSPEAK)+") \n", skin,"small");//learn dolphin
		Label mission9=new Label("- Mission  9 : \n"
				+"      " + CatGame.myBundle.get("mission9") +"\n", skin,"small");//find speaking dolphin
		Label mission10=new Label("- Mission  10 : \n"
				+"      " + CatGame.myBundle.get("mission10") +" ("+ Integer.toString(Save.gd.norisdefeated)+"/3) \n", skin,"small");//defeat NORIS
		Label mission11=new Label("- Mission  11 : \n"
				+"      " + CatGame.myBundle.get("mission11") +"\n", skin,"small");//find anomaly
		
		if(Save.gd.visitnbr<4){
			missiontxt.setSize(1, tuto.getHeight()+mission1.getHeight()+mission2.getHeight());
			
			mission2.setPosition(0, missiontxt.getHeight()-
					mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(tuto);
		}
		if(Save.gd.visitnbr>=4 && !Save.gd.eventdest){
			missiontxt.setSize(1, tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight());
			
			mission3.setPosition(0, missiontxt.getHeight()-
					mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					tuto.getHeight()-mission1.getHeight()-mission2.getHeight()-mission3.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(tuto);
		}
		if(Save.gd.probes>=5){
			missiontxt.setSize(1, tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+missionb.getHeight());
			
			missionb.setPosition(0, missiontxt.getHeight()-
					missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					missionb.getHeight()-tuto.getHeight()-mission1.getHeight()-mission2.getHeight()-mission3.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(tuto);
		}
		if(Save.gd.eventdest){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+mission4.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission4.getHeight()-missionb.getHeight()-tuto.getHeight()-mission1.getHeight()-mission2.getHeight()-mission3.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.eventbosskuro){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+missionb.getHeight()+mission4.getHeight()+mission5.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro1)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.eventbosscatious){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.explornbr>=1){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight()+mission7.getHeight());
			mission7.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(mission7);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.explornbr>=5){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight()+mission7.getHeight()+mission8.getHeight());
			mission8.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight());
			mission7.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK)mission8.setColor(Color.GREEN);
			else                  mission8.setColor(Color.RED);
			mission8.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(mission7);
			missiontxt.addActor(mission8);
			missiontxt.addActor(tuto);
		}
		if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight()+
					mission7.getHeight()+mission8.getHeight()+mission9.getHeight());
			mission9.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight());
			mission8.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight());
			mission7.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK)mission8.setColor(Color.GREEN);
			else                  mission8.setColor(Color.RED);
			mission8.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak)mission9.setColor(Color.GREEN);
			else                  mission9.setColor(Color.RED);
			mission9.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(mission7);
			missiontxt.addActor(mission8);
			missiontxt.addActor(mission9);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.playerPos>=CatGame.EVENTDSPEAK){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight()+
					mission7.getHeight()+mission8.getHeight()+mission9.getHeight()+mission10.getHeight());
			mission10.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight());
			mission9.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight());
			mission8.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight());
			mission7.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK)mission8.setColor(Color.GREEN);
			else                  mission8.setColor(Color.RED);
			mission8.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak)mission9.setColor(Color.GREEN);
			else                  mission9.setColor(Color.RED);
			mission9.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.norisdefeated==3)mission10.setColor(Color.GREEN);
			else                  mission10.setColor(Color.RED);
			mission10.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(mission7);
			missiontxt.addActor(mission8);
			missiontxt.addActor(mission9);
			missiontxt.addActor(mission10);
			missiontxt.addActor(tuto);
		}
		
		if(Save.gd.norisdefeated==3){
			missiontxt.setSize(1, 
					tuto.getHeight()+mission1.getHeight()+mission2.getHeight()+mission3.getHeight()+
					missionb.getHeight()+mission4.getHeight()+mission5.getHeight()+mission6.getHeight()+
					mission7.getHeight()+mission8.getHeight()+mission9.getHeight()+mission10.getHeight()+mission11.getHeight());
			mission11.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight());
			mission10.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight());
			mission9.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight());
			mission8.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight());
			mission7.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight());
			mission6.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight());
			mission5.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight());
			mission4.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight());
			missionb.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight());
			mission3.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight());
			mission2.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight());
			mission1.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight());
			tuto.setPosition(0, missiontxt.getHeight()-
					mission11.getHeight()-mission10.getHeight()-mission9.getHeight()-mission8.getHeight()-mission7.getHeight()-mission6.getHeight()-mission5.getHeight()-mission4.getHeight()-missionb.getHeight()-mission3.getHeight()-mission2.getHeight()-mission1.getHeight()-tuto.getHeight());
			
			mission1.setColor(Color.GREEN);
			mission1.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdest)mission2.setColor(Color.GREEN);
			else                 mission2.setColor(Color.RED);
			mission2.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.metalspent>=120)mission3.setColor(Color.GREEN);
			else                       mission3.setColor(Color.RED);
			mission3.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.probes>=30)missionb.setColor(Color.GREEN);
			else                  missionb.setColor(Color.RED);
			missionb.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventendcat)mission4.setColor(Color.GREEN);
			else                   mission4.setColor(Color.RED);
			mission4.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatkuro)mission5.setColor(Color.GREEN);
			else                  mission5.setColor(Color.RED);
			mission5.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventdefeatcatious)mission6.setColor(Color.GREEN);
			else                  mission6.setColor(Color.RED);
			mission6.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak1)mission7.setColor(Color.GREEN);
			else                  mission7.setColor(Color.RED);
			mission7.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.explornbr>=CatGame.RUINNBRSPEAK)mission8.setColor(Color.GREEN);
			else                  mission8.setColor(Color.RED);
			mission8.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.eventspeak)mission9.setColor(Color.GREEN);
			else                  mission9.setColor(Color.RED);
			mission9.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.norisdefeated==3)mission10.setColor(Color.GREEN);
			else                  mission10.setColor(Color.RED);
			mission10.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			if(Save.gd.anomaly)   mission11.setColor(Color.GREEN);
			else                  mission11.setColor(Color.RED);
			mission11.addAction(forever(sequence(alpha(0.9f,1f,sine),alpha(0.7f,1f, sine))));
			
			missiontxt.addActor(mission1);
			missiontxt.addActor(mission2);
			missiontxt.addActor(mission3);
			missiontxt.addActor(missionb);
			missiontxt.addActor(mission4);
			missiontxt.addActor(mission5);
			missiontxt.addActor(mission6);
			missiontxt.addActor(mission7);
			missiontxt.addActor(mission8);
			missiontxt.addActor(mission9);
			missiontxt.addActor(mission10);
			missiontxt.addActor(mission11);
			missiontxt.addActor(tuto);
		}
		
		return missiontxt;
	}
	
	private void checksenario(){
		//Bilabial1
		if(Save.gd.visitnbr==1){
			event=1;
			createtuto();
		}
		
		if(Save.gd.visitnbr==5 && Save.gd.eventkuro==false){
			event=2;
		}
		if(Save.gd.visitnbr==4 && Save.gd.eventkuro==false){
			event=3;
		}
		if(Save.gd.visitnbr==8 && Save.gd.eventkuro==false){
			event=4;
		}
		
		//event KURO
		if(playerPos>CatGame.EVENTKURO && !Save.gd.eventkuro){
			Save.gd.eventkuro=true;
			Save.gd.playerPosEvent=playerPos;
			event=5;
		}
		if(playerPos!=Save.gd.playerPosEvent && !Save.gd.eventkuro1 && Save.gd.eventkuro){
			Save.gd.eventkuro1=true;
			event=29;
		}
				
		//event destination
		if(playerPos==CatGame.EVENTDEST && Save.gd.eventdest==false){
			Save.gd.playerPosEvent=CatGame.EVENTDEST;
			Save.gd.eventdest=true;
			event=6;
		}
		if(Save.gd.lose>=2 && !Save.gd.eventlose && Save.gd.eventdest){
			event=30;
			Save.gd.eventlose=true;
		}
		//Bilabial2
		if(playerPos!=CatGame.EVENTDEST && Save.gd.eventdest && !Save.gd.eventdest1){
			event=7;
			Save.gd.eventdest1=true;
		}
		
		//event boss KURO 
		if(playerPos>=CatGame.EVENTBOSSKURO && !Save.gd.eventdefeatkuro && Save.gd.eventdest){
			if(!Save.gd.eventbosskuro){
				Save.gd.eventbosskuro=true;
				event=8;
			}
			else{
				event=9;
			}
			Save.gd.playerPosEvent=Save.gd.playerPos;
		}
		//event end boss KURO 
		if(Save.gd.playerPosEvent!=Save.gd.playerPos && Save.gd.eventdefeatkuro && !Save.gd.eventdefeatkuro1){
			event=10;
			Save.gd.eventdefeatkuro1=true;
		}
		
		//event boss CATIOUS 
		if(playerPos>=CatGame.EVENTBOSSCATIOUS && !Save.gd.eventdefeatcatious && Save.gd.eventdest){
			if(!Save.gd.eventbosscatious){
				Save.gd.eventbosscatious=true;
				event=11;
			}
			else{
				event=12;
			}
			Save.gd.playerPosEvent=Save.gd.playerPos;
			stage.addActor(Scenario.createdialog(event, skin));
		}
		//event end boss CATIOUS 
		if(Save.gd.playerPosEvent!=Save.gd.playerPos && Save.gd.eventdefeatcatious && !Save.gd.eventdefeatcatious1){
			event=13;
			Save.gd.eventdefeatcatious1=true;
		}
		//event end cat
		if(playerPos>=CatGame.EVENTENDCAT && !Save.gd.eventendcat){
			event=14;
			Save.gd.eventendcat=true;
		}
		if(Save.gd.eventendcat && Save.gd.visitnbr<2){
			Save.gd.playerPosEvent=Save.gd.playerPos;
		}
		//event 15 : found ruins (explored=1)
		//event 16 : found unknown ship (explored=2) (dolphin begin=true)
		//event dolphin misunderstood 
		if(Save.gd.eventendcat && Save.gd.explornbr<2){
			Save.gd.playerPosEvent=Save.gd.playerPos;
		}
		if(Save.gd.playerPosEvent!=Save.gd.playerPos && !Save.gd.eventspeak1 &&  Save.gd.eventendcat){
			event=17;
			Save.gd.playerPosEvent=Save.gd.playerPos;
			Save.gd.eventspeak1=true;
		}
		
		//event 18 more ruins hurry communicate
		
		//event shield1
		if(playerPos>=CatGame.EVENTDSHIELD1 && !Save.gd.eventshield1){
			Save.gd.playerPosEvent=Save.gd.playerPos;
			Save.gd.eventshield1=true;
		}
		if(Save.gd.playerPosEvent!=Save.gd.playerPos &&  Save.gd.eventshield1 &&  !Save.gd.eventshield11){
			Save.gd.eventshield11=true;
			event=19;
		}
		
		//event boss CATIOUS 
		if(playerPos>=CatGame.EVENTBOSSN && !Save.gd.eventdefeatn && Save.gd.eventshield11){
			if(!Save.gd.eventbossn){
				Save.gd.eventbossn=true;
				event=31;
			}
			stage.addActor(Scenario.createdialog(event, skin));
		}
		
		//event shield2
		if(playerPos>=CatGame.EVENTDSHIELD2 && !Save.gd.eventshield2){
			Save.gd.playerPosEvent=Save.gd.playerPos;
			Save.gd.eventshield2=true;
		}
		if(Save.gd.playerPosEvent!=Save.gd.playerPos &&  Save.gd.eventshield2 &&  !Save.gd.eventshield22 ){
			Save.gd.eventshield22=true;
			event=20;
		}
		
		//event 21 : find laser
		//event 22 : dolphin language learned 
		
		//event speak dolphin
		if(playerPos>=CatGame.EVENTDSPEAK && Save.gd.explornbr>=CatGame.RUINNBRSPEAK && !Save.gd.eventspeak){
			Save.gd.eventspeak=true;
			event=23;
		}
		
		if(Save.gd.eventspeak && Save.gd.lose>=1 && !Save.gd.eventlose2){
			Save.gd.eventlose2=true;
			event=32;
		}
			
	}
	
	private void checkevents() {
		if(Save.gd.explornbr==CatGame.RUINNBRSHIELD){
			event=15;
			//discTEC
			newtec= new Group();
			Image drill= new Image(Assets.manager.get(Assets.shield,Texture.class));
			drill.setSize(256, 256);
			drill.setColor(Color.BLUE);
			drill.setPosition(stage.getWidth()/2-drill.getWidth()/2, stage.getHeight()/2-drill.getHeight()/3);
			drill.setOrigin(Align.center);
			drill.setTouchable(Touchable.disabled);
			drill.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
			Label cool= new Label("New Technology!!", skin);
			cool.setPosition(stage.getWidth()/2-cool.getWidth()/2, drill.getY()-cool.getHeight()-pad);
			cool.setTouchable(Touchable.disabled);
			Image bg=new Image(skin.newDrawable("white", Color.BLACK));
			bg.getColor().a=0.9f;
			bg.setSize(cool.getWidth()+14*pad, cool.getHeight()+drill.getHeight()+10*pad);
			bg.setPosition(cool.getX()-7*pad, cool.getY()-5*pad);
			bg.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Save.gd.soundEnabled)MapUI.click.play();
					newtec.addAction(sequence(alpha(0,0.2f),hide()));
				}
			});
			Image bg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
			bg2.getColor().a=0.2f;
			bg2.setSize(cool.getWidth()+8*pad, cool.getHeight()+drill.getHeight()+4*pad);
			bg2.setPosition(cool.getX()-4*pad, cool.getY()-2*pad);
			bg2.setTouchable(Touchable.disabled);
			newtec.addActor(bg);
			newtec.addActor(bg2);
			newtec.addActor(drill);
			newtec.addActor(cool);
			newtec.setVisible(false);
			newtec.getColor().a=0;
			stage.addActor(newtec);
			newtec.addAction(sequence(delay(18),show(),alpha(1,1f)));
			Save.gd.shieldDisp=true;
			Save.save();
			gar =new Garage1(skin, stage.getWidth(), stage.getHeight(), scale);
			lab=(gar.lab);
			stage.addActor(lab);
			createjournal();
			stage.addActor(journal);
			stage.addActor(Scenario.createdialog(event, skin));
		}
		if(Save.gd.explornbr==2){
			event=16;
			Save.gd.eventdolphinbegin=true;
			Save.gd.playerPosEvent=Save.gd.playerPos;
			stage.addActor(Scenario.createdialog(event, skin)); 
		}
		if(Save.gd.explornbr==5){
			event=18;
			stage.addActor(Scenario.createdialog(event, skin));
		}
		if(Save.gd.explornbr==CatGame.RUINNBRLZ){
			event=21;
			stage.addActor(Scenario.createdialog(event, skin));
			newtec= new Group();
			Image sM= new Image(Assets.manager.get(Assets.laserGun,Texture.class));
			float ratio = sM.getHeight()/sM.getWidth();
			sM.setSize(256, ratio*256);
			sM.setPosition(stage.getWidth()/2-sM.getWidth()/2, stage.getHeight()/2-sM.getHeight()/3);
			sM.setOrigin(Align.center);
			sM.setTouchable(Touchable.disabled);
			sM.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
			Label cool= new Label("New Technology!!", skin);
			cool.setPosition(stage.getWidth()/2-cool.getWidth()/2, sM.getY()-cool.getHeight()-pad);
			cool.setTouchable(Touchable.disabled);
			Image bg=new Image(skin.newDrawable("white", Color.BLACK));
			bg.getColor().a=0.9f;
			bg.setSize(cool.getWidth()+14*pad, cool.getHeight()+sM.getHeight()+10*pad);
			bg.setPosition(cool.getX()-7*pad, cool.getY()-5*pad);
			bg.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Save.gd.soundEnabled)MapUI.click.play();
					newtec.addAction(sequence(alpha(0,0.2f),hide()));
				}
			});
			Image bg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
			bg2.getColor().a=0.2f;
			bg2.setSize(cool.getWidth()+8*pad, cool.getHeight()+sM.getHeight()+4*pad);
			bg2.setPosition(cool.getX()-4*pad, cool.getY()-2*pad);
			bg2.setTouchable(Touchable.disabled);
			newtec.addActor(bg);
			newtec.addActor(bg2);
			newtec.addActor(sM);
			newtec.addActor(cool);
			newtec.setVisible(false);
			newtec.getColor().a=0;
			stage.addActor(newtec);
			newtec.addAction(sequence(delay(3),show(),alpha(1,1f)));
			Save.gd.lzDisp=true;
			Save.save();
			gar =new Garage1(skin, stage.getWidth(), stage.getHeight(), scale);
			lab=(gar.lab);
			stage.addActor(lab);
		}
		
		if(Save.gd.explornbr==CatGame.RUINNBRSPEAK){
			event=22;
			stage.addActor(Scenario.createdialog(event, skin));
		}
	}
	
	private void createtuto(){
		//tuto
		if(event==1 && !Save.gd.explored[0]){
			tuto1 =new Label(CatGame.myBundle.get("tuto1"), skin);
			tuto2 =new Label(CatGame.myBundle.get("tuto2"), skin);
			tuto3 =new Label(CatGame.myBundle.get("tuto3"), skin);
			tuto4 =new Label(CatGame.myBundle.get("tuto4"), skin);
			tuto5 =new Label(CatGame.myBundle.get("tuto5"), skin);
			tuto6 =new Label(CatGame.myBundle.get("tuto6"), skin);
			tuto7 =new Label(CatGame.myBundle.get("tuto7"), skin);
			tuto8 =new Label(CatGame.myBundle.get("tuto8"), skin);
			tuto9 =new Label(CatGame.myBundle.get("tuto9"), skin);
			
			tuto1.setAlignment(Align.center);
			tuto2.setAlignment(Align.center);
			tuto3.setAlignment(Align.center);
			tuto4.setAlignment(Align.center);
			tuto5.setAlignment(Align.center);
			tuto6.setAlignment(Align.center);
			tuto7.setAlignment(Align.center);
			tuto8.setAlignment(Align.center);
			tuto9.setAlignment(Align.center);
			
			tuto1.setTouchable(Touchable.disabled);
			tuto2.setTouchable(Touchable.disabled);
			tuto3.setTouchable(Touchable.disabled);
			tuto4.setTouchable(Touchable.disabled);
			tuto5.setTouchable(Touchable.disabled);
			tuto6.setTouchable(Touchable.disabled);
			tuto7.setTouchable(Touchable.disabled);
			tuto8.setTouchable(Touchable.disabled);
			tuto9.setTouchable(Touchable.disabled);
			
			tuto1.setPosition(Gdx.graphics.getWidth()/2- tuto1.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto2.setPosition(Gdx.graphics.getWidth()/2- tuto2.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto3.setPosition(Gdx.graphics.getWidth()/2- tuto3.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto4.setPosition(Gdx.graphics.getWidth()/2- tuto4.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto5.setPosition(Gdx.graphics.getWidth()/2- tuto5.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto6.setPosition(Gdx.graphics.getWidth()/2- tuto6.getWidth()/2, 0+2*pad);
			tuto7.setPosition(Gdx.graphics.getWidth()/2- tuto7.getWidth()/2, Gdx.graphics.getHeight()/4);
			tuto8.setPosition(Gdx.graphics.getWidth()/2- tuto8.getWidth()/2, 0+2*pad);
			tuto9.setPosition(Gdx.graphics.getWidth()/2- tuto9.getWidth()/2, Gdx.graphics.getHeight()/4);
			
			tutotable=new Group();
			tutotable.addActor(tuto1); 
			tutotable.addActor(tuto2);
			tutotable.addActor(tuto3);
			tutotable.addActor(tuto4);
			tutotable.addActor(tuto5);
			tutotable.addActor(tuto6);
			tutotable.addActor(tuto7);
			tutotable.addActor(tuto8);
			tutotable.addActor(tuto9);
			
			tuto1.getColor().a=0;
			tuto2.getColor().a=0;
			tuto3.getColor().a=0;
			tuto4.getColor().a=0;
			tuto5.getColor().a=0;
			tuto6.getColor().a=0;
			tuto7.getColor().a=0;
			tuto8.getColor().a=0;
			tuto9.getColor().a=0;
			
			tuto1.addAction  (sequence(delay(24), alpha(1, 0.5f, fade)));
			stage.addActor(tutotable);
		}
	}
	
	
	private void events () {
	//////////////////////////////////////	//debug cine
		
		///////////////////////end debug////////////////////////////
		//main menu
		menuButton .addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(tuto7 ==null || ((tuto7 !=null && tuto7.getColor().a==1) || mainMenu.isVisible())){
					if(Save.gd.soundEnabled)click.play();
					gotoMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					exploreMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					mainMenu.clearActions();
					mainMenu.getColor().a = mainMenu.isVisible() ? 1 : 0;
					if (mainMenu.isVisible()){
						mainMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						if(tutotable!=null){
							tuto7.addAction(sequence(alpha(0, 0.5f, fade)));
							tuto8.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade)));
						}
						mainMenu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
					menuButton.getColor().a = mainMenu.isVisible() ? 0.7f : 1;
				}
				else {
					menuButton.setChecked(false);
				}
			}
			
		});
		
		shipbutton .addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setChecked(false);
				gotoMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				exploreMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				lab.clearActions();
				lab.getColor().a = lab.isVisible() ? 1 : 0;
				if (lab.isVisible()){
					lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				}
				else{
					if(tutotable!=null){
						tuto5.addAction(sequence(alpha(0, 0.5f, fade)));
						tuto6.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade)));
					}
					lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
				}
				shipbutton.getColor().a = lab.isVisible() ? 0.6f : 1;
			}
		});
		
		resumButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setChecked(false);
				resumButton.setChecked(false);
			}
		});
		
		titleButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				if(tuto1==null)
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		journalButton .addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setTouchable(Touchable.disabled);
				shipbutton.setTouchable(Touchable.disabled);
				mainMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				journal.addAction(sequence(show(), alpha(1, 0.5f, fade)));
			}
		});
		
		shopButton .addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setTouchable(Touchable.disabled);
				shipbutton.setTouchable(Touchable.disabled);
				mainMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				buymenu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
			}
		});
		
		musicButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				Save.gd.musicEnabled=musicButton.isChecked();
				if(musicButton.isChecked()) music.play();
				else music.stop();
				Save.save();
			}
		});
		
		soundButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(!Save.gd.soundEnabled)click.play();
				Save.gd.soundEnabled=soundButton.isChecked();
				Save.save();
			}
		});
		
		//travel
		goButton   .addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(exploreMenu.getColor().a==0){
				if(Save.gd.maki>=nrjcost){
					if(Save.gd.soundEnabled)zoom.play(1,0.3f,0);
					nrjcostlabel.addAction(moveTo(stage.getWidth()/2+nrjcostlabel.getWidth()*4-pad,
							-stage.getHeight()/2+nrjcostlabel.getHeight()+4*pad,2,sine));
					black.addAction(sequence(show(),
							fadeIn(2),run(new Runnable() {
								@Override
								public void run() {
									((Game) Gdx.app.getApplicationListener()).setScreen(new FightScreen(nrjcost));
								}
							})));
					if(Save.gd.playerGo!=playerGo) Save.gd.maki-=nrjcost;
					if(nrjcostlabel.getY()<90*scale) maki.setText(Integer.toString(Save.gd.maki));
					Save.gd.playerGo=playerGo;
					Save.save();
				}
				else{
					if(Save.gd.soundEnabled)no.play(1,0.6f,0);
					maki.addAction(sequence(color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f)));
					buymenu.addAction(sequence(delay(1.2f),show(), alpha(1, 0.5f, fade)));
				}
				
				menuButton.setChecked(false);
				resumButton.setChecked(false);
			}
			}
		});
		
		notgoButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(exploreMenu.getColor().a==0){
				if(Save.gd.soundEnabled)click.play();
				gotoMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				notgoButton.setChecked(false);
				targetImage.addAction(sequence(alpha(0, 0.5f, fade),hide()));
				for(int i=0;i<pathImages.size;i++){
					starsTable.removeActor(pathImages.get(i));
				}
				pathImages.clear();
			}
			}
		});
		
		//explore
		exButton   .addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(tutotable!=null && tuto2.getColor().a==1){
					tuto2.addAction(alpha(0, 0.5f, fade));
					tuto3.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade), delay(3),alpha(0, 0.5f, fade)));
					tuto4.addAction(sequence(delay(4.5f), alpha(1, 0.5f, fade), delay(3),alpha(0, 0.5f, fade)));
					tuto5.addAction(sequence(delay(8.5f), alpha(1, 0.5f, fade)));
				}
				if(gotoMenu.getColor().a==0){
				if(Save.gd.soundEnabled)click.play();
				if(Save.gd.maki>=5){
					if(!Save.gd.explored[playerPos]){
						collect();
					}
					else{
						explored.addAction(sequence(alpha(1,0.5f),delay(1.5f),alpha(0,0.5f)));
					}
					menuButton.setChecked(false);
					resumButton.setChecked(false);
				}
				else{
					if(Save.gd.soundEnabled)no.play(1,0.6f,0);
					maki.addAction(sequence(color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f)));
					buymenu.addAction(sequence(delay(1.2f),show(), alpha(1, 0.5f, fade)));
				}
			}
			}
		});
		
		notexButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(gotoMenu.getColor().a==0){
				if(Save.gd.soundEnabled && zoomed)zoom.play(1,0.3f,0);
				if(Save.gd.soundEnabled)click.play();
				zoomed=false;
				playerIm.addAction(alpha(1,6));
				if(planet1!=null){
					planet1.addAction(alpha(0,1));
				}
				if(planet2!=null){
					planet2.addAction(alpha(0,1));
				}
				if(planet3!=null){
					planet3.addAction(alpha(0,1));
				}
				exploreMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				notexButton.setChecked(false);
				targetImage.addAction(sequence(alpha(0, 0.5f, fade),hide()));
			}
			}
		});
		
		//journal
		okjournal.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setTouchable(Touchable.enabled);
				shipbutton.setTouchable(Touchable.enabled);
				journal.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				mainMenu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
			}
		});
		
		//shop
		backbt.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				menuButton.setTouchable(Touchable.enabled);
				shipbutton.setTouchable(Touchable.enabled);
				buymenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				if(menuButton.isChecked()) mainMenu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
			}
		});
	
		buy1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buyMaki1();
			}
		});
		
		buy2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buyMaki2();
			}
		});
		buy3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buymetal();;
			}
		});
	}
	
	private Group createStars(int starNbr ) {
		if(Save.gd.starX[0]==-1){
			starsTable=new Group();
			starsTable.setHeight(universSize);
			starsTable.setWidth(0);
			for(int i=0; i< starNbr; i++){
				starsTable.addActor(createStar(i));
			}
			created=true;
			Save.save();
		}
		else {
			starsTable=new Group();
			starsTable.setHeight(universSize);
			starsTable.setWidth(0);
			for(int i=0; i< starNbr; i++){
				starsTable.addActor(loadStar(i));
			}
		}
		return starsTable;
		
	}
	
	private Image createStar(int position) {
			
		//create map
		Image lvlimage = new Image(Assets.manager.get(Assets.star0, Texture.class));
		float starsize= 26*sizeratiow + MathUtils.random(-11*sizeratiow, 11*sizeratiow);
		lvlimage.setSize(starsize, starsize);
		lvlimage.setAlign(Align.center);
		lvlimage.setUserObject(position);
		float x=MathUtils.random(0, 960*sizeratiow-starsize/2);
		int type= MathUtils.random(1,8);
		float y=40*sizeratiow+20*position*sizeratiow;
		lvlimage.setPosition(x, y);
		Save.gd.starType[position]=type;
		Save.gd.starsize[position]=starsize;
		Save.gd.starX[position]=x;
		Save.gd.starY[position]=y;
			
		//star player is currently at
		if(position==CatGame.ANOMALY || position==CatGame.ANOMALY2 ){
			Save.gd.starType[position]=9;
		}
		if(position==playerPos){
			Save.gd.visitnbr++;
			if(CatGame.gservices.isSignedIn()){
				CatGame.gservices.incrementexplore(1);
			}
			else 
				Save.gd.visitnbrgs++;
			
			Save.gd.visited[position]=true;
			Save.gd.starX[position]=stage.getWidth()/2;
			//planet1 ou planet21 ou planet31 same orbit
			Save.gd.starType[position]=1;
			lvlimage = new Image(Assets.manager.get(Assets.star1, Texture.class));
			planet1  = new Image(Assets.manager.get(Assets.planet1, Texture.class));
			planet2  = new Image(Assets.manager.get(Assets.planet2, Texture.class));
			planet3  = new Image(Assets.manager.get(Assets.planet3, Texture.class));
			
			lvlimage.setSize(Save.gd.starsize[position], Save.gd.starsize[position]);
			lvlimage.setAlign(Align.center);
			lvlimage.setUserObject(position);
			lvlimage.setPosition(Save.gd.starX[position],Save.gd.starY[position]);
			if(planet1!=null){
				planet1.setSize(Save.gd.starsize[position]*2, Save.gd.starsize[position]*2);
				planet1.setAlign(Align.center);
				planet1.setUserObject(position);
				planet1.setPosition(Save.gd.starX[position]-Save.gd.starsize[position]/2,Save.gd.starY[position]-Save.gd.starsize[position]/2);
				planet1.setOrigin(Align.center);
				planet1.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
				planet1.getColor().a=0;
				starsTable.addActor(planet1);
			}
			if(planet2!=null){
				planet2.setSize(Save.gd.starsize[position]*2, Save.gd.starsize[position]*2);
				planet2.setAlign(Align.center);
				planet2.setUserObject(position);
				planet2.setPosition(Save.gd.starX[position]-Save.gd.starsize[position]/2,Save.gd.starY[position]-Save.gd.starsize[position]/2);
				planet2.setOrigin(Align.center);
				planet2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(5, 8)))));
				planet2.getColor().a=0;
				starsTable.addActor(planet2);
			}
			if(planet3!=null){
				planet3.setSize(Save.gd.starsize[position]*2, Save.gd.starsize[position]*2);
				planet3.setAlign(Align.center);
				planet3.setUserObject(position);
				planet3.setPosition(Save.gd.starX[position]-Save.gd.starsize[position]/2,Save.gd.starY[position]-Save.gd.starsize[position]/2);
				planet3.setOrigin(Align.center);
				planet3.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(7, 10)))));
				planet3.getColor().a=0;
				starsTable.addActor(planet3);
			}
			playerX=Save.gd.starX[position];
			playerY=Save.gd.starY[position];
			playerstarsize=Save.gd.starsize[position];
			echoImage.setSize(range*sizeratiow*2, range*sizeratiow*2);
			echoImage.setPosition(playerX-echoImage.getWidth()/2+playerstarsize/2,playerY-echoImage.getHeight()/2+playerstarsize/2);
			echoImage.setOrigin(Align.center);
			starsTable.addActor(echoImage);
			rangeImage.setSize(range*sizeratiow*2, range*sizeratiow*2);
			rangeImage.setPosition(playerX-rangeImage.getWidth()/2+playerstarsize/2,playerY-rangeImage.getHeight()/2+playerstarsize/2);
			starsTable.addActor(rangeImage);
			lvlimage.addListener(explorClickListener);
		}
		else if(position==CatGame.EVENTDEST && !Save.gd.visited[CatGame.EVENTDEST] && Save.gd.visitnbr==1){
			Save.gd.starType[position]=1;
			destIm1.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[position]/2,
						   playerY+Save.gd.starsize[position]/2,0),
					delay(18),show(),
					moveTo(Save.gd.starX[position]-destIm1.getWidth()/2+Save.gd.starsize[position]/2,
						   Save.gd.starY[position]-destIm1.getHeight()/2+Save.gd.starsize[position]/2,3)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		else if(position==CatGame.EVENTDEST && !Save.gd.visited[CatGame.EVENTDEST]){
			destIm1.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[position]-destIm1.getWidth()/2+Save.gd.starsize[position]/2,
						   Save.gd.starY[position]-destIm1.getHeight()/2+Save.gd.starsize[position]/2,3)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		else lvlimage.addListener(gotostarClickListener);		
	
		return lvlimage;
		
	}
	
	private Image loadStar(int starNbr) {
		//create map
		Image lvlimage = new Image(Assets.manager.get(Assets.star0, Texture.class));
		if(Save.gd.visited[starNbr]) {
			if(Save.gd.starType[starNbr]==1){
				lvlimage = new Image(Assets.manager.get(Assets.star1, Texture.class));
			}
			if(Save.gd.starType[starNbr]==2){
				lvlimage = new Image(Assets.manager.get(Assets.star1, Texture.class));
			}
			if(Save.gd.starType[starNbr]==3){
				lvlimage = new Image(Assets.manager.get(Assets.star2, Texture.class));
			}
			if(Save.gd.starType[starNbr]==4){
				lvlimage = new Image(Assets.manager.get(Assets.star2, Texture.class));
			}
			if(Save.gd.starType[starNbr]==5){
				lvlimage = new Image(Assets.manager.get(Assets.star3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==6){
				lvlimage = new Image(Assets.manager.get(Assets.star3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==7){
				lvlimage = new Image(Assets.manager.get(Assets.star4, Texture.class));
			}
			if(Save.gd.starType[starNbr]==8){
				lvlimage = new Image(Assets.manager.get(Assets.star4, Texture.class));
			}
			if(Save.gd.starType[starNbr]==9){
				lvlimage = new Image(Assets.manager.get(Assets.star5, Texture.class));
			}
		}
		else{
			lvlimage = new Image(Assets.manager.get(Assets.star0, Texture.class));
		}
		lvlimage.setSize(Save.gd.starsize[starNbr], Save.gd.starsize[starNbr]);
		lvlimage.setAlign(Align.center);
		lvlimage.setUserObject(starNbr);
		lvlimage.setPosition(Save.gd.starX[starNbr],Save.gd.starY[starNbr]);
		//star player is currently at
		if(starNbr==playerPos){
			if(!Save.gd.visited[starNbr]){
				Save.gd.visitnbr++;
				if(CatGame.gservices.isSignedIn()){
					CatGame.gservices.incrementexplore(1);
				}
				else 
					Save.gd.visitnbrgs++;
			}
			Save.gd.visited[starNbr]=true;
			//planet1 ou planet21 ou planet31 same orbit
			if(Save.gd.starType[starNbr]==1){
				lvlimage = new Image(Assets.manager.get(Assets.star1, Texture.class));
				planet1  = new Image(Assets.manager.get(Assets.planet1, Texture.class));
				planet2  = new Image(Assets.manager.get(Assets.planet2, Texture.class));
				planet3  = new Image(Assets.manager.get(Assets.planet3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==2){
				lvlimage = new Image(Assets.manager.get(Assets.star1, Texture.class));
				planet2  = new Image(Assets.manager.get(Assets.planet1, Texture.class));
				planet3  = new Image(Assets.manager.get(Assets.planet11, Texture.class));
			}
			if(Save.gd.starType[starNbr]==3){
				lvlimage = new Image(Assets.manager.get(Assets.star2, Texture.class));
				planet2  = new Image(Assets.manager.get(Assets.planet31, Texture.class));
				planet3  = new Image(Assets.manager.get(Assets.planet3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==4){
				lvlimage = new Image(Assets.manager.get(Assets.star2, Texture.class));
				planet2  = new Image(Assets.manager.get(Assets.planet11, Texture.class));
				planet3  = new Image(Assets.manager.get(Assets.planet3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==5){
				lvlimage = new Image(Assets.manager.get(Assets.star3, Texture.class));
				planet1  = new Image(Assets.manager.get(Assets.planet1, Texture.class));
				
			}
			if(Save.gd.starType[starNbr]==6){
				lvlimage = new Image(Assets.manager.get(Assets.star3, Texture.class));
			}
			if(Save.gd.starType[starNbr]==7){
				lvlimage = new Image(Assets.manager.get(Assets.star4, Texture.class));
				planet3  = new Image(Assets.manager.get(Assets.planet3, Texture.class));
				
			}
			if(Save.gd.starType[starNbr]==8){
				lvlimage = new Image(Assets.manager.get(Assets.star4, Texture.class));
			}
			if(Save.gd.starType[starNbr]==9){
				lvlimage = new Image(Assets.manager.get(Assets.star5, Texture.class));
			}
			lvlimage.setSize(Save.gd.starsize[starNbr], Save.gd.starsize[starNbr]);
			lvlimage.setPosition(Save.gd.starX[starNbr],Save.gd.starY[starNbr]);
			lvlimage.setAlign(Align.center);
			lvlimage.setOrigin(Align.center);
			lvlimage.setUserObject(starNbr);
			lvlimage.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(11, 20)))));
			if(planet1!=null){
				planet1.setSize(Save.gd.starsize[starNbr]*2, Save.gd.starsize[starNbr]*2);
				planet1.setAlign(Align.center);
				planet1.setUserObject(starNbr);
				planet1.setPosition(Save.gd.starX[starNbr]-Save.gd.starsize[starNbr]/2,Save.gd.starY[starNbr]-Save.gd.starsize[starNbr]/2);
				planet1.setOrigin(Align.center);
				planet1.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
				planet1.getColor().a=0;
				starsTable.addActor(planet1);
			}
			if(planet2!=null){
				planet2.setSize(Save.gd.starsize[starNbr]*2, Save.gd.starsize[starNbr]*2);
				planet2.setAlign(Align.center);
				planet2.setUserObject(starNbr);
				planet2.setPosition(Save.gd.starX[starNbr]-Save.gd.starsize[starNbr]/2,Save.gd.starY[starNbr]-Save.gd.starsize[starNbr]/2);
				planet2.setOrigin(Align.center);
				planet2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(5, 8)))));
				planet2.getColor().a=0;
				starsTable.addActor(planet2);
			}
			if(planet3!=null){
				planet3.setSize(Save.gd.starsize[starNbr]*2, Save.gd.starsize[starNbr]*2);
				planet3.setAlign(Align.center);
				planet3.setUserObject(starNbr);
				planet3.setPosition(Save.gd.starX[starNbr]-Save.gd.starsize[starNbr]/2,Save.gd.starY[starNbr]-Save.gd.starsize[starNbr]/2);
				planet3.setOrigin(Align.center);
				planet3.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(7, 10)))));
				planet3.getColor().a=0;
				starsTable.addActor(planet3);
			}
			playerX=Save.gd.starX[starNbr];
			playerY=Save.gd.starY[starNbr];
			playerstarsize=Save.gd.starsize[starNbr];
			echoImage.setSize(range*sizeratiow*2, range*sizeratiow*2);
			echoImage.setPosition(playerX-echoImage.getWidth()/2+playerstarsize/2,playerY-echoImage.getHeight()/2+playerstarsize/2);
			echoImage.setOrigin(Align.center);
			starsTable.addActor(echoImage);
			rangeImage.setSize(range*sizeratiow*2, range*sizeratiow*2);
			rangeImage.setPosition(playerX-rangeImage.getWidth()/2+playerstarsize/2,playerY-rangeImage.getHeight()/2+playerstarsize/2);
			starsTable.addActor(rangeImage);
			if(starNbr!=CatGame.NORIS1 && starNbr!=CatGame.NORIS2 && starNbr!=CatGame.NORIS3)
				lvlimage.addListener(explorClickListener);
			else{
				lvlimage.addAction(sequence(delay(4),alpha(0,1), hide()));
			}
			
		}
		//image destination "video"
		else if(starNbr==CatGame.EVENTDEST && !Save.gd.visited[CatGame.EVENTDEST] && Save.gd.visitnbr==1){
			destIm1.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(18),show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination else
		else if(starNbr==CatGame.EVENTDEST && !Save.gd.visited[CatGame.EVENTDEST]){
			destIm1.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination NORIS1 1RST time
		else if(starNbr==CatGame.NORIS1 && !Save.gd.visited[CatGame.NORIS1] && Save.gd.playerPos>=CatGame.EVENTDSPEAK && !Save.gd.eventspeak){
			destIm1.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(106),show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination NORIS1 else
		else if(starNbr==CatGame.NORIS1 && !Save.gd.visited[CatGame.NORIS1] && Save.gd.eventspeak){
			destIm1.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		else if(starNbr==CatGame.NORIS1 && Save.gd.visited[CatGame.NORIS1]){
			lvlimage.setVisible(false);
		}
		
		//image destination NORIS2 1RST time
		else if(starNbr==CatGame.NORIS2 && !Save.gd.visited[CatGame.NORIS2] && Save.gd.playerPos>=CatGame.EVENTDSPEAK && !Save.gd.eventspeak){
			destIm2.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(106),show(),
					moveTo(Save.gd.starX[starNbr]-destIm2.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm2.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm2);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination NORIS2 else
		else if(starNbr==CatGame.NORIS2 && !Save.gd.visited[CatGame.NORIS2] && Save.gd.eventspeak){
			destIm2.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm2.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm2.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm2);
			lvlimage.addListener(gotostarClickListener);
		}
		//destruction NORIS2
		else if(starNbr==CatGame.NORIS2 && Save.gd.visited[CatGame.NORIS2]){
			lvlimage.setVisible(false);
		}
		//image destination NORIS3 1RST time
		else if(starNbr==CatGame.NORIS3 && !Save.gd.visited[CatGame.NORIS3] && Save.gd.playerPos>=CatGame.EVENTDSPEAK && !Save.gd.eventspeak){
			destIm3.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(106),show(),
					moveTo(Save.gd.starX[starNbr]-destIm3.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm3.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm3);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination NORIS3 else
		else if(starNbr==CatGame.NORIS3 && !Save.gd.visited[CatGame.NORIS3] && Save.gd.eventspeak){
			destIm3.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm3.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm3.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm3);
			lvlimage.addListener(gotostarClickListener);
		}
		//destruction NORIS1
		else if(starNbr==CatGame.NORIS1 && Save.gd.visited[CatGame.NORIS1]){
			lvlimage.setVisible(false);
		}
		//image destination Anomaly 1RST time
		else if(starNbr==CatGame.ANOMALY && !Save.gd.visited[CatGame.ANOMALY] && Save.gd.norisdefeated==3 && !Save.gd.norisdefeat){
			destIm1.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(61),show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm1);
			Save.gd.norisdefeat=true;
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination Anomaly else
		else if(starNbr==CatGame.ANOMALY && !Save.gd.visited[CatGame.ANOMALY] && Save.gd.norisdefeated==3 && Save.gd.norisdefeat){
			destIm1.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination Anomaly2 1RST time
		else if(starNbr==CatGame.ANOMALY2 && Save.gd.anomaly && !Save.gd.anomaly1){
			destIm1.addAction(sequence(
					moveTo(playerX+Save.gd.starsize[starNbr]/2,
						   playerY+Save.gd.starsize[starNbr]/2,0),
					delay(20),show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,5)
					));
			starsTable.addActor(destIm1);
			Save.gd.anomaly1=true;
			lvlimage.addListener(gotostarClickListener);
		}
		//image destination Anomaly2 else
		else if(starNbr==CatGame.ANOMALY2 && Save.gd.anomaly1){
			destIm1.addAction(sequence(
					show(),
					moveTo(Save.gd.starX[starNbr]-destIm1.getWidth()/2+Save.gd.starsize[starNbr]/2,
						   Save.gd.starY[starNbr]-destIm1.getHeight()/2+Save.gd.starsize[starNbr]/2,0)
					));
			starsTable.addActor(destIm1);
			lvlimage.addListener(gotostarClickListener);
		}
		else lvlimage.addListener(gotostarClickListener);		
		
		return lvlimage;
	}
	
	
	
	public ClickListener gotostarClickListener = new ClickListener() {
		public void clicked ( InputEvent event, float xbut, float ybut) {
			if(tuto9 ==null || (tuto9 !=null && tuto9.getColor().a==1)){
				if(Save.gd.soundEnabled)targ.play();
				menuButton.setChecked(false);
				playerGo=(Integer) event.getListenerActor().getUserObject();
				exploreMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				if(Save.gd.explored[playerPos]) systemexplored.setText("");
				else systemexplored.setText(CatGame.myBundle.get("notexplored"));
				gotoMenu.clearActions();
				gotoMenu.getColor().a =  0;
				gotoMenu.addAction(sequence(show(), alpha(0.7f, 0.5f, fade)));
				float x=event.getListenerActor().getX()+event.getListenerActor().getWidth()/2;
				float y=event.getListenerActor().getY()+event.getListenerActor().getHeight()/2;
				
				drawPath((playerX+playerstarsize/2),x,(playerY+playerstarsize/2), y, Color.GREEN);
				
				targetImage.addAction(parallel(moveTo(x-targetImage.getWidth()/2, y-targetImage.getHeight()/2,0.4f),show()));
				starsTable.addActor(targetImage);
			}
		}
	};
	
	
	public ClickListener explorClickListener = new ClickListener() {
		public void clicked ( InputEvent event, float xbut, float ybut) {
			if(Save.gd.soundEnabled)zoom.play(1,0.3f,0);
			if(tutotable!=null && tuto1.getColor().a==1){
				tuto1.addAction(alpha(0, 0.5f, fade));
				tuto2.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade)));
			}
			menuButton.setChecked(false);
			for(int i=0;i<pathImages.size;i++){
				starsTable.removeActor(pathImages.get(i));
			} 
			pathImages.clear();
			playerGo=(Integer) event.getListenerActor().getUserObject();
			gotoMenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
			exploreMenu.clearActions();
			exploreMenu.getColor().a = 0;
			exploreMenu.addAction(sequence(show(), alpha(0.7f, 0.5f, fade)));
			float x=event.getListenerActor().getX()+event.getListenerActor().getWidth()/2;
			float y=event.getListenerActor().getY()+event.getListenerActor().getHeight()/2;
			targetImage.addAction(parallel(moveTo(x-targetImage.getWidth()/2, y-targetImage.getHeight()/2,0.4f),show()));
			zoomed=true;
			playerIm.addAction(alpha(0,3));
			if(planet1!=null){ 
				planet1.addAction(alpha(1,1));
				}
			if(planet2!=null){
				planet2.addAction(alpha(1,1));
			}
			if(planet3!=null){
				planet3.addAction(alpha(1,1));
			}
			targetImage.addAction(sequence(alpha(0,4),hide()));
			starsTable.addActor(targetImage);
			
		}
	};
	
	public void drawPath(float xstart,float xend,float ystart, float yend, Color color){
		for(int i=0;i<pathImages.size;i++){
			starsTable.removeActor(pathImages.get(i));
		} 
		pathImages.clear();
		float dir=(yend-ystart)/(xend-xstart);
		float dist=(float) Math.sqrt((xstart-xend)*(xstart-xend)+(ystart-yend)*(ystart-yend));
		nrjcost=3+(int) (dist/(40*sizeratiow));
		nrjcostlabel.setText("-"+Integer.toString(nrjcost));
		if((yend-ystart)>0 && (xend-xstart)>0){
			if(dir>1 ){
				for(int i=0; i<dist;i+=10){
					if(ystart+i+10<yend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i/dir-pathImage.getWidth()/2,ystart+i-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
			else if(dir<1) {
				for(int i=0; i<dist;i+=10){
					if(xstart+i+10<xend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i-pathImage.getWidth()/2,ystart+i*dir-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
		}
		if((yend-ystart)<0 && (xend-xstart)>0){
			if(dir<-1 ){
				for(int i=0; i<dist;i+=10){
					if(ystart-i-10>yend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i/-dir-pathImage.getWidth()/2,ystart-i-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
			else {
				for(int i=0; i<dist;i+=10){
					if(xstart+i+10<xend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i-pathImage.getWidth()/2,ystart+i*dir-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
		}
		if((yend-ystart)>0 && (xend-xstart)<0){
			if(dir<-1 ){
				for(int i=0; i<dist;i+=10){
					if(ystart+i+10<yend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i/dir-pathImage.getWidth()/2,ystart+i-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
			else {
				for(int i=0; i<dist;i+=10){
					if(xstart-i-10>xend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart-i-pathImage.getWidth()/2,ystart+i*-dir-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
		}
		if((yend-ystart)<0 && (xend-xstart)<0){
			if(dir>1 ){
				for(int i=0; i<dist;i+=10){
					if(ystart-i-10>yend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart+i/-dir-pathImage.getWidth()/2,ystart-i-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
			else if(dir<1) {
				for(int i=0; i<dist;i+=10){
					if(xstart-i-10>xend){
						Image pathImage = new Image(Assets.manager.get(Assets.path , Texture.class));
						pathImage.setSize(10,10);
						pathImage.setPosition(xstart-i-pathImage.getWidth()/2,ystart-i*dir-pathImage.getHeight()/2);
						pathImage.setColor(color);pathImage.getColor().a=0;
						pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
						pathImages.add(pathImage);
					}
				}
			}
		}
		for(int i=0;i<pathImages.size;i++){
			starsTable.addActor(pathImages.get(i));
		}
		
	}
	
	
	private void loadSkin () {
		skin = new Skin();
		if(Gdx.graphics.getHeight()<1000){
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			skin.add("small"  , new BitmapFont(Gdx.files.internal("font/arialverysmall.fnt")));
			scale=1;
		}
		else{
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			skin.add("small"  , new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			scale=2;
		}
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		ButtonStyle buttonstyle = new ButtonStyle();
		buttonstyle.up   = skin.newDrawable("white", Color.BLACK);
		buttonstyle.down = skin.newDrawable("white", new Color(0x416ba1ff));
		buttonstyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		skin.add("default", buttonstyle);
		
		buttonstyle = new ButtonStyle(buttonstyle);
		buttonstyle.checked = skin.newDrawable("white", new Color(0x5287ccff));
		skin.add("toggle", buttonstyle);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up   = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.down = skin.newDrawable("white", new Color(0x416ba1ff));
		textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		textButtonStyle = new TextButtonStyle(textButtonStyle);
		textButtonStyle.checked = skin.newDrawable("white", new Color(0x5287ccff));
		skin.add("toggle", textButtonStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("small");
		skin.add("small", labelStyle);
		labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);
		
		ProgressBarStyle progressBarStyle = new ProgressBarStyle();
		progressBarStyle.background = skin.newDrawable("white", new Color(1f, 0.25f, 0.25f, 0.66f));
		progressBarStyle.background.setMinHeight(30*scale);
		progressBarStyle.knobBefore = skin.newDrawable("white", Color.CLEAR);
		progressBarStyle.knobBefore.setMinHeight(30*scale);
		progressBarStyle.knobAfter = skin.newDrawable("white", new Color(0.25f, 0.25f, 0.25f, 0.88f));
		progressBarStyle.knobAfter .setMinHeight(30*scale);
		skin.add("red", progressBarStyle);
	}
	
	
	private void scan(){
		float dist=0;
		for(int i = 0; i<starsTable.getChildren().size;i++){
			float x = starsTable.getChildren().get(i).getX();//+starsTable.getChildren().get(i).getWidth()/2;
			float y = starsTable.getChildren().get(i).getY();//+starsTable.getChildren().get(i).getHeight()/2;
			if(!created)
				dist=(float) Math.sqrt((playerX-x)*(playerX-x)+(playerY-y)*(playerY-y));
			else {
				float size = starsTable.getChildren().get(i).getHeight();
				dist=(float) Math.sqrt((playerX-size/2-x)*(playerX-size/2-x)+(playerY-size/2-y)*(playerY-size/2-y));
			}
			if(dist>range*sizeratiow) starsTable.getChildren().get(i).setTouchable(Touchable.disabled);
			
		}
	}
	
	
	private TextButton tbutton (String text, boolean toggle) {
		TextButton button = new TextButton(text, skin, toggle ? "toggle" : "default");
		button.pad(2, 12, 2, 12);
		return button;
	}
	
	
	private void disctec(){
		if(!Save.gd.smDisp && Save.gd.probes>=5){
			Save.gd.smDisp=true;
			event=0;
			stage.addActor(Scenario.createdialog(event, skin));
			newtec= new Group();
			Image sM= new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
			float ratio = sM.getHeight()/sM.getWidth();
			sM.setSize(256, ratio*256);
			sM.setPosition(stage.getWidth()/2-sM.getWidth()/2, stage.getHeight()/2-sM.getHeight()/3);
			sM.setOrigin(Align.center);
			sM.setTouchable(Touchable.disabled);
			sM.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
			Label cool= new Label("New Technology!!", skin);
			cool.setPosition(stage.getWidth()/2-cool.getWidth()/2, sM.getY()-cool.getHeight()-pad);
			cool.setTouchable(Touchable.disabled);
			Image bg=new Image(skin.newDrawable("white", Color.BLACK));
			bg.getColor().a=0.9f;
			bg.setSize(cool.getWidth()+14*pad, cool.getHeight()+sM.getHeight()+10*pad);
			bg.setPosition(cool.getX()-7*pad, cool.getY()-5*pad);
			bg.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Save.gd.soundEnabled)MapUI.click.play();
					newtec.addAction(sequence(alpha(0,0.2f),hide()));
				}
			});
			Image bg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
			bg2.getColor().a=0.2f;
			bg2.setSize(cool.getWidth()+8*pad, cool.getHeight()+sM.getHeight()+4*pad);
			bg2.setPosition(cool.getX()-4*pad, cool.getY()-2*pad);
			bg2.setTouchable(Touchable.disabled);
			newtec.addActor(bg);
			newtec.addActor(bg2);
			newtec.addActor(sM);
			newtec.addActor(cool);
			newtec.setVisible(false);
			newtec.getColor().a=0;
			stage.addActor(newtec);
			newtec.addAction(sequence(delay(3),show(),alpha(1,1f)));
		}
		if(!Save.gd.drillDisp && Save.gd.probes>=15){
				Save.gd.drillDisp=true;
				event=0;
				stage.addActor(Scenario.createdialog(event, skin));
				newtec= new Group();
				Image drill= new Image(Assets.manager.get(Assets.drill,Texture.class));
				drill.setPosition(stage.getWidth()/2-drill.getWidth()/2, stage.getHeight()/2-drill.getHeight()/3);
				drill.setOrigin(Align.center);
				drill.setTouchable(Touchable.disabled);
				drill.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
				Label cool= new Label("New Technology!!", skin);
				cool.setPosition(stage.getWidth()/2-cool.getWidth()/2, drill.getY()-cool.getHeight()-pad);
				cool.setTouchable(Touchable.disabled);
				Image bg=new Image(skin.newDrawable("white", Color.BLACK));
				bg.getColor().a=0.9f;
				bg.setSize(cool.getWidth()+14*pad, cool.getHeight()+drill.getHeight()+10*pad);
				bg.setPosition(cool.getX()-7*pad, cool.getY()-5*pad);
				bg.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(Save.gd.soundEnabled)MapUI.click.play();
						newtec.addAction(sequence(alpha(0,0.2f),hide()));
					}
				});
				Image bg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
				bg2.getColor().a=0.2f;
				bg2.setSize(cool.getWidth()+8*pad, cool.getHeight()+drill.getHeight()+4*pad);
				bg2.setPosition(cool.getX()-4*pad, cool.getY()-2*pad);
				bg2.setTouchable(Touchable.disabled);
				newtec.addActor(bg);
				newtec.addActor(bg2);
				newtec.addActor(drill);
				newtec.addActor(cool);
				newtec.setVisible(false);
				newtec.getColor().a=0;
				stage.addActor(newtec);
				newtec.addAction(sequence(delay(3),show(),alpha(1,1f)));
		}
		if(!Save.gd.hmakiDisp && Save.gd.probes>30){
			Save.gd.hmakiDisp=true;
			int event=0;
			stage.addActor(Scenario.createdialog(event, skin));
			newtec= new Group();
			Image hmaki= new Image(Assets.manager.get(Assets.hmaki,Texture.class));
			hmaki.setPosition(stage.getWidth()/2-hmaki.getWidth()/2, stage.getHeight()/2-hmaki.getHeight()/3);
			hmaki.setOrigin(Align.center);
			hmaki.setTouchable(Touchable.disabled);
			hmaki.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
			Label cool= new Label("New Technology!!", skin);
			cool.setPosition(stage.getWidth()/2-cool.getWidth()/2, hmaki.getY()-cool.getHeight()-pad);
			cool.setTouchable(Touchable.disabled);
			Image bg=new Image(skin.newDrawable("white", Color.BLACK));
			bg.getColor().a=0.9f;
			bg.setSize(cool.getWidth()+14*pad, cool.getHeight()+hmaki.getHeight()+10*pad);
			bg.setPosition(cool.getX()-7*pad, cool.getY()-5*pad);
			bg.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Save.gd.soundEnabled)MapUI.click.play();
					newtec.addAction(sequence(alpha(0,0.2f),hide()));
				}
			});
			Image bg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
			bg2.getColor().a=0.2f;
			bg2.setSize(cool.getWidth()+8*pad, cool.getHeight()+hmaki.getHeight()+4*pad);
			bg2.setPosition(cool.getX()-4*pad, cool.getY()-2*pad);
			bg2.setTouchable(Touchable.disabled);
			newtec.addActor(bg);
			newtec.addActor(bg2);
			newtec.addActor(hmaki);
			newtec.addActor(cool);
			newtec.setVisible(false);
			newtec.getColor().a=0;
			stage.addActor(newtec);
			newtec.addAction(sequence(delay(3),show(),alpha(1,1f)));
		}
		
	}

	
	private void collect(){
		Label excostmove= new Label("-5",skin);
		excostmove.setPosition(stage.getWidth()/2+excostmove.getWidth(), stage.getHeight()/2-excostmove.getHeight());
		stage.addActor(excostmove);
		excostmove.getColor().a=0;
		excostmove.addAction(sequence(alpha(1),parallel(moveTo(stage.getWidth()-pad-excostmove.getWidth(),0+nrjcostlabel.getHeight(),1,sine),alpha(0,1)),hide()));
		Save.gd.maki-=5;
		maki.setText(Integer.toString(Save.gd.maki));
		metalwon=0;  ///average 2.625 METAL/explore   /////DRILL average 5.5 METAL/explore
		makiwon=0;   ///average 2.625 MAKI/explore    /////HMAKI average 6   MAKI/explore
		/// total 24 METAL, 21 MAKI
		/// total 44 METAL, 48 MAKI
		if(Save.gd.starType[playerPos]==1){
		//	planet1  = metal
		//	planet2  = terre
		//	planet3  = gaz
			///average 6 METAL, 7 MAKI (upgrade less)
			///average 10 METAL, 9 MAKI (upgrade)
			if(Save.gd.drillOwn)metalwon+=MathUtils.random(7, 13);
			else metalwon+=MathUtils.random(5, 7);
			
			makiwon +=MathUtils.random(2, 4);
			
			if(Save.gd.hmakiOwn)makiwon+=MathUtils.random(4, 10);
			else makiwon+=MathUtils.random(2, 6);
		}
		if(Save.gd.starType[playerPos]==2){
		//	planet2  = metal
		//	planet3  = metal
			///average 7 METAL, 0 MAKI (upgrade less)
			///average 12 METAL,0 MAKI (upgrade)
			if(Save.gd.drillOwn)metalwon+=MathUtils.random(5, 13);
			else metalwon+=MathUtils.random(4, 8);
			if(Save.gd.drillOwn)metalwon+=MathUtils.random(4, 8);
			else metalwon+=MathUtils.random(2, 6);
		}
		if(Save.gd.starType[playerPos]==3){
		//	planet2  = gaz
		//	planet3  = gaz
			///average 0 METAL, 6 MAKI (upgrade less)
			///average 0 METAL, 14 MAKI (upgrade)
			if(Save.gd.hmakiOwn)makiwon+=MathUtils.random(4, 10);
			else makiwon+=MathUtils.random(2, 4);
			if(Save.gd.hmakiOwn)makiwon+=MathUtils.random(4, 10);
			else makiwon+=MathUtils.random(2, 6);
		}
		if(Save.gd.starType[playerPos]==4){
		//	planet2  = metal
		//	planet3  = gaz
			///average 8 METAL, 4 MAKI (upgrade less)
			///average 6 METAL, 10 MAKI (upgrade less)
			if(Save.gd.drillOwn)metalwon+=MathUtils.random(2, 10);
			else metalwon+=MathUtils.random(6, 10);
			if(Save.gd.hmakiOwn)makiwon+=MathUtils.random(8, 12);
			else makiwon+=MathUtils.random(1, 7);
		}
		if(Save.gd.starType[playerPos]==5){
		//	planet1  = metal
			///average 16 METAL, 0 MAKI (upgrade less)
			if(Save.gd.drillOwn)metalwon+=MathUtils.random(6, 26);
			else metalwon+=MathUtils.random(0, 6);
			
		}
		if(Save.gd.starType[playerPos]==6){
		}
		if(Save.gd.starType[playerPos]==7){
		//	planet3  = gas
			///average 0 METAL, 15 MAKI (upgrade less)
			if(Save.gd.hmakiOwn)makiwon+=MathUtils.random(10, 20);
			else makiwon+=MathUtils.random(3, 7);
			
		}
		if(Save.gd.starType[playerPos]==8){
		}
		
		/////////////////////////////////dolphin ruins
		if(Save.gd.playerPos>CatGame.EVENTENDCAT && (
				Save.gd.starType[playerPos]==5 || Save.gd.starType[playerPos]==4 || Save.gd.starType[playerPos]==2 || Save.gd.starType[playerPos]==1 )){
			Save.gd.explornbr++;
			////////////////////first ruin
			checkevents();
		}
		
		
		Save.gd.explored[playerPos]=true;
		exploreMenu.addAction(sequence(alpha(0, 1f, fade), hide()));
		exploretable.addAction(sequence(delay(1f),show(),fadeIn(0.6f), delay(1.8f), fadeOut(0.6f),hide()));
		makiwont.addAction(sequence(delay(2.5f),
				parallel(moveTo(stage.getWidth()-(pad+60)*sizeratiow+makiwont.getWidth()/2,
				(60/2+pad)*sizeratiow-maki.getHeight()/2,2,sine),
				alpha(0,1.5f))));
		metalwont.addAction(sequence(delay(2.5f),
				parallel(moveTo((pad+60)*sizeratiow+makiwont.getWidth()/2,
				(60/2+pad)*sizeratiow-maki.getHeight()/2,2,sine),
				alpha(0,1.5f))));
		
		zoomed=false;
		playerIm.addAction(alpha(1,6));
		if(planet1!=null){
			planet1.addAction(alpha(0,1));
		}
		if(planet2!=null){
			planet2.addAction(alpha(0,1));
		}
		if(planet3!=null){
			planet3.addAction(alpha(0,1));
		}
		Save.gd.maki+=makiwon;
		Save.gd.metal+=metalwon;
		
		Save.save();
	}
	
	
	public void render (float delta) {
		
		//initial scroll
		if(!hasScrolled){
			if(playerY>starstage.getHeight()){
				map.setScrollY(universSize -playerY-starstage.getHeight()/2);
				if(map.getScrollY()==universSize-playerY-starstage.getHeight()/2) hasScrolled=true;
			}
			else{
				map.setScrollPercentY(1);
				if(map.getScrollPercentY()==1) hasScrolled=true;
			}
		}
		if(journal.getColor().a==0){
			record.setScrollPercentY(1);
		}
		
		float zoomstepx=16, zoomstepy=9;
		//zoom to see planets
		if(zoomed==true){
			if(zoomnbr<230){
				sstagew+=zoomstepx*sizeratiow;///Gdx.graphics.getWidth()/60 =16????
				sstageh+=zoomstepy*sizeratioh;///Gdx.graphics.getHeight()/60 =9
				starstage.getViewport().getCamera().position.x+=sizeratiow*
						2*((float)sstagew)/(((float)sstagew-zoomstepx))*(2*playerX)/sstageinitw;
				starstage.getViewport().getCamera().position.y+=sizeratioh*
						2*((float)sstageh)/(((float)sstageh-zoomstepy))
						*(playerY-(universSize-map.getScrollY()-sstageinith))/sstageinith;
				zoomnbr++;
			}
			starstage.getViewport().update((int)sstagew,(int)sstageh,false);
		}
		else if(zoomed==false){
			if(zoomnbr>=1){
				sstagew-=zoomstepx*sizeratiow;
				sstageh-=zoomstepy*sizeratioh;
				starstage.getViewport().getCamera().position.x-=sizeratiow*
						2*((float)sstagew)/(((float)sstagew-zoomstepx))*(2*playerX)/sstageinitw;
				starstage.getViewport().getCamera().position.y-=sizeratioh*
						2*((float)sstageh)/(((float)sstageh-zoomstepy))
						*(playerY-(universSize-map.getScrollY()-sstageinith))/sstageinith;
				zoomnbr--;
				starstage.getViewport().update((int)sstagew,(int)sstageh,false);
			}
			else starstage.getViewport().update((int)sstagew,(int)sstageh,true);
			
		}
		//garage & stages act
		gar.render();
		starstage.act();
		starstage.draw();
		
		stage.act();
		stage.getViewport().apply(true);
		stage.draw();
		
		//upgrade metal update
		if(gar.upgraded){
			metal.setText(Integer.toString(Save.gd.metal));
			gar.upgraded=false;
		}
		
		//time calculator
		timecalc.update();
		time2maki.setText(timecalc.time2);
		
		//tutorial action
		if(tutotable!=null && tuto6.getColor().a==1 && !lab.isVisible()){
			tuto6.addAction(sequence(alpha(0, 0.5f, fade)));
			tuto7.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade)));
		}
		if(tutotable!=null && tuto8.getColor().a==1 && !mainMenu.isVisible() && !buymenu.isVisible() && !journal.isVisible()){
			tuto8.addAction(sequence(alpha(0, 0.5f, fade)));
			tuto9.addAction(sequence(delay(0.5f), alpha(1, 0.5f, fade)));
		}
		
		//buy maki
		if (maki1buy || maki2buy || timecalc.makiupdate){
			maki.setText(Integer.toString(Save.gd.maki));
		/*	if(Save.gd.maki<20)
				maki.setPosition(stage.getWidth()-maki.getWidth()-Save.gd.maki/10*15-4*pad, 60/2-maki.getHeight()/2+pad);
			else if(Save.gd.maki>99 && Save.gd.maki<200)
				maki.setPosition(stage.getWidth()-maki.getWidth()-2*(Save.gd.maki/100)*15-4*pad, 60/2-maki.getHeight()/2+pad);*/
			maki1buy=false;maki2buy=false;
		}
		if (metalbuy){
			metal.setText(Integer.toString(Save.gd.metal));
			metalbuy=false;
		}
		
		//exploration result animation
		if(makiwont.getX()>stage.getWidth()*5/6){
			maki.setText(Integer.toString(Save.gd.maki));
			metal.setText(Integer.toString(Save.gd.metal));
		}
		if(metalwoni<metalwon || makiwoni<makiwon){
			rewardTimer++;
			if(rewardTimer%5==0){
				if(makiwoni<makiwon){
					makiwoni++;
					makiwont.setText(" : +"+Integer.toString(makiwoni));
				}
				if(metalwoni<metalwon){
					metalwoni++;
					metalwont.setText(" : +"+Integer.toString(metalwoni));
				}
			}
		}
		/*if(black!=null){
			music.setVolume(0.17f-black.getColor().a);
		}*/
	}
	
	
	public void resize (int width, int height) {
		starstage.getViewport().update(width, height, true);
	}
	
	
	public void dispose(){
		starstage.dispose();
		timecalc.dispose();
		click.dispose();
		targ.dispose();
		zoom.dispose();
		no.dispose();
		//music.stop();
		music.dispose();
		//stage.dispose(); //problem glitch black disposed....
		skin.dispose();
	}

}
