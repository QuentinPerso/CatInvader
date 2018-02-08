package quentin.jeu.cat;

////////// average probes/trip = 1.8 POS<150 (special level not counted)
////////// average METAL from probes/trip = 6.3 POS<200
////////// average MAKI  from probes/trip = 6.3 POS<200

///average 2.625 METAL/explore   /////DRILL average 5.5 METAL/explore
///average 2.625 MAKI/explore    /////HMAKI average 6   MAKI/explore

////////// average 2.000 METAL/trip from explore
////////// average 2.625 MAKI/trip from explore

////////// total gain : 8.3 METAL/level
////////// total gain : 8.9 MAKI/level

////////// average trip cost          6 (-1 clever)
////////// potential exploration cost 3.75 (chance to have to explore/trip*explore cost)
////////// average LEVEL COST (trip and explore clever) : 8 
////////// total METAL to spend 5925

import quentin.jeu.cat.screens.Splash;
import quentin.jeu.cat.utils.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class CatGame extends Game {

	public static I18NBundle myBundle;
	public static Shop shop;
	public static IGoogleServices gservices;
	public static final String TITLE = "Cat Invaders";
	
	public static final int RG = 0, SM=1, RRB=2, LZ=3;
	public static final int EVENTKURO=40, EVENTDEST=60, EVENTBOSSKURO=90, EVENTBOSSCATIOUS=120, EVENTENDCAT=155 ; //33 50 85 120 150
	public static final int EVENTDSHIELD1=185, EVENTDSHIELD2=205, EVENTBOSSN=220, EVENTDSPEAK=270, NORIS1=300, NORIS2=310, NORIS3=320, ANOMALY=350, ANOMALY2=370;
	// 200 240 290 320 340 360 400 420
	public static final int RUINNBRSHIELD=1, RUINNBRLZ=7, RUINNBRSPEAK=23; //1 7 30
	///////////////Max upgrade
	//ship
	public static float maxhp=20, maxSpeed=10, maxAcc=40;
	//RG
	public static float maxRGprec=200, maxRGFR=20, maxRGpower=200;
	//SM
	public static float maxSMnbr=80, maxSMFR=8f, maxSMspeed=20;
	//LZ
	public static float maxLZpower=1, minLZpower=0.21f, reloadLZ=1.8f;
	//Shield
	public static float maxshieldhp=20, maxshieldRL=30;//, zzreloadLZ=3;
	
	
	
	 public CatGame(Shop shop, IGoogleServices gservices) {
	      CatGame.shop = shop;
	      CatGame.gservices = gservices;
	   }
	
	@Override
	public void create() {
		Assets.manager = new AssetManager();
		setScreen(new Splash());
		FileHandle baseFileHandle = Gdx.files.internal("internation/MyBundle");
		//Locale locale = new Locale("en");
		myBundle = I18NBundle.createBundle(baseFileHandle);
		
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}