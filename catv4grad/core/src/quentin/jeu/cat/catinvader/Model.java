package quentin.jeu.cat.catinvader;


import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.catinvader.Enemy.Type;
import quentin.jeu.cat.screens.FightScreen;
import quentin.jeu.cat.catinvader.Pnj.pnjType;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/** The core of the game logic. The model manages all game information but knows nothing about the view, ie it knows nothing about
 * how this information might be drawn to the screen. This model-view separation is a clean way to organize the code. */
public class Model {
	public  static float scale = 1 / 64f;
	private static float gameOverSlowdown = 3f;
	public  static float worldheight=8;

	public  FightScreen controller;
	public  Player player;
	public  float timeScale = 1;
	
	public  FloatArray playerRGbullets = new FloatArray();
	public  FloatArray playerSM = new FloatArray();
	public  FloatArray playerSMbullets = new FloatArray();
	public boolean shootlz;
	public  FloatArray enemybullets  = new FloatArray();
	public  FloatArray enemySM       = new FloatArray();
	public boolean shootenemylz;
	private Vector2 temp1 = new Vector2();
	float explodSMtimer=1;
	private Array<Trigger> triggers  = new Array<Trigger>();
	public  Array<Enemy>   enemies   = new Array<Enemy>();
	
	
	private float gameOverTimer; //slow down timer
	public  boolean win=false;
	public  int probeCollect=0,metalCollect=0, score, lose;
	private int probechance, lvlchance, bosschance;
	public  Pnj kuro;

	
	public Model (FightScreen controller) {
		this.controller = controller;
		if(		   Save.gd.visitnbr==4 || Save.gd.visitnbr==3 ||      //no probe when dialog (redo)
				  (Save.gd.playerGo>CatGame.EVENTKURO && !Save.gd.eventkuro)||
				  (Save.gd.playerGo!=Save.gd.playerPosEvent && !Save.gd.eventkuro1 && Save.gd.eventkuro) ||
				  (Save.gd.playerGo==CatGame.EVENTDEST) ||
				  (Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro) ||
				  (Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatcatious)||
				  (Save.gd.playerGo>=CatGame.EVENTENDCAT && !Save.gd.eventendcat) ||
				  (!Save.gd.eventspeak1 && Save.gd.eventdolphinbegin )||
				  (Save.gd.playerGo>=CatGame.EVENTDSHIELD1 && !Save.gd.eventshield1)){
					probechance=0;
				}
		else if(Save.gd.playerGo==CatGame.NORIS1 || Save.gd.playerGo==CatGame.NORIS2 || Save.gd.playerGo==CatGame.NORIS3 ){
			probechance=0;
		}
		else{
			probechance= MathUtils.random(1, 10);
		}
		lvlchance  = MathUtils.random(0, 5);
		restart();
	}

	public Model() {
		restart();
	}

	public void restart () {
		player = new Player(this);
		player.position.set(0, 0);
		player.velocity.set(0, 0);
		playerRGbullets.clear();
		enemies.clear();
		gameOverTimer = 0;
		score=0;
		probeCollect=0;
		metalCollect=0;
		//find KURO again
		if(Save.gd.playerGo>CatGame.EVENTKURO && !Save.gd.eventkuro){
			kuro = new Pnj(this, pnjType.kuro);
			kuro.position.set(200, 3);
			kuro.velocity.set(0, 0);
		}
		else if (Save.gd.eventkuro && !Save.gd.eventdest){
			kuro = new Pnj(this, pnjType.kuro);
			kuro.position.set(3, 3);
			kuro.velocity.set(0, 0);
		}
		bosschance=MathUtils.random(0, 2);

		// Setup triggers to spawn enemies based on the x coordinate of the player.
		triggers.clear();
		createlvl();
		
	}
	
	void createlvl(){
		
		
		//probes
		
		addprobe();
		////////////////////ENEMIES///////////////////
		//asteroids (Save.gd.visitnbr>=3 && !Save.gd.eventdest)
		addasteroids0();
		
		if(Save.gd.visitnbr>=3 && Save.gd.playerGo<=CatGame.EVENTDEST)
			addasteroids1();
		//first cats (Save.gd.eventdest && Save.gd.playerPos<CatGame.EVENTBOSSKURO)
		if(Save.gd.eventdest && Save.gd.playerPos<CatGame.EVENTBOSSKURO && Save.gd.playerGo>=CatGame.EVENTDEST)
			addcats1();
		
		//2nd cats (Save.gd.eventdefeatkuro && Save.gd.playerPos<CatGame.EVENTBOSSCATIOUS)
		if(Save.gd.eventdefeatkuro && Save.gd.playerPos<CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>=CatGame.EVENTBOSSKURO)
			addcats2();
		
		//3nd cats (Save.gd.eventdefeatcatious && Save.gd.playerPos<CatGame.EVENTENDCAT)
		if(Save.gd.eventdefeatcatious && Save.gd.playerPos<CatGame.EVENTENDCAT )
			addcats3();
		
		// quiet before dolphin
		if(!Save.gd.eventdolphinbegin && Save.gd.eventendcat)
			addasteroids2();
		
		//dolphin 1 //shield less
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2)
			adddolphin0();
		
		//dolphin 2 //shield 1
		if(Save.gd.eventdolphinbegin && Save.gd.eventshield1 && !Save.gd.eventshield2)
			adddolphin1();
		
		//dolphin 3 //shield 2
		if(Save.gd.playerPos<CatGame.EVENTBOSSN && Save.gd.eventshield2)
			adddolphin2();
		
		//dolphin 3 post boss bomber
		if(Save.gd.eventdefeatn && !Save.gd.eventspeak)
			adddolphin3();
		//NORIS & farm
		if(Save.gd.eventdefeatn && Save.gd.eventspeak && Save.gd.playerGo>CatGame.EVENTDSPEAK && !Save.gd.visited[Save.gd.playerGo]
				&& Save.gd.playerGo!=CatGame.NORIS1
				&& Save.gd.playerGo!=CatGame.NORIS2 
				&& Save.gd.playerGo!=CatGame.NORIS3
				&& Save.gd.playerGo!=CatGame.ANOMALY)
			addnoris1();
		else if(Save.gd.playerGo==CatGame.NORIS1 || Save.gd.playerGo==CatGame.NORIS2 || Save.gd.playerGo==CatGame.NORIS3 || Save.gd.playerGo==CatGame.ANOMALY){
			
		}
		else if(Save.gd.eventdefeatn && Save.gd.eventspeak){
			if(bosschance==0){
				addasteroids1();
				addasteroids1();
			}
			else{
				addasteroids3();
			}
		}
		//boss 
		addbosses();
		
		//Anomaly
		addcosmicrays();
		
	//	System.out.println(lvlchance);
	//	System.out.println(bosschance);
	//	System.out.println(Save.gd.playerGo);
	}
	
	
	void addprobe(){
		if(probechance==1){
			addTrigger(80 , Type.probes,  1);
		}
		if(probechance==2){
			addTrigger(140, Type.probes,  1);
			addTrigger(320, Type.probes,  1);
		}
		if(probechance==3){
			addTrigger(230, Type.probes,  1);
			addTrigger(260, Type.probes,  1);
			addTrigger(290, Type.probes,  1);
		}
		if(probechance==4){
			addTrigger(20 , Type.probes,  1);
			addTrigger(290, Type.probes,  1);
		}
		if(probechance==5){
			addTrigger(200, Type.probes,  1);
		}
		if(probechance==6){
			addTrigger(110, Type.probes,  1);
			addTrigger(290, Type.probes,  1);
		}
		if(probechance==7){
			addTrigger(170, Type.probes,  1);
			addTrigger(260, Type.probes,  1);
			addTrigger(290, Type.probes,  1);
		}
		if(probechance==8){
			addTrigger(50 , Type.probes,  1);
			addTrigger(80 , Type.probes,  1);
		}
		if(probechance==9){
			addTrigger(140, Type.probes,  1);
		}
		if(probechance==10){
			addTrigger(200, Type.probes,  1);
		}
	}
	
	
	void addasteroids0(){
		//first levels
		if(Save.gd.visitnbr<3){
			addTrigger(20  , Type.asteroidsmall  ,  5);
			addTrigger(50  , Type.asteroidnormal ,  3);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(100 , Type.asteroidsmall  ,  5);
			addTrigger(120 , Type.asteroidsmall  ,  8);
			addTrigger(140 , Type.asteroidsmall  ,  5);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(220 , Type.asteroidsmall  ,  3);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.asteroidsmall  ,  6);
			addTrigger(290 , Type.asteroidnormal ,  3);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidbig    ,  2);
		}
	}
	
	void addasteroids1(){
		//asteroids levels
		if((lvlchance==0 || lvlchance==1) ){
			
			addTrigger(20  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.asteroidnormal ,  5);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  7);
			addTrigger(100 , Type.asteroidsmall  ,  8);
			addTrigger(110 , Type.asteroidsmall  ,  15);
			addTrigger(120 , Type.asteroidsmall  ,  8);
			addTrigger(140 , Type.asteroidnormal ,  7);
			addTrigger(140 , Type.asteroidsmall  ,  8);
			addTrigger(160 , Type.asteroidsmall  ,  8);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  2);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidbig    ,  2);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.asteroidsmall  ,  16);
			addTrigger(260 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(290 , Type.asteroidnormal ,  3);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidbig    ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidsmall  ,  10);
			addTrigger(360 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidbig    ,  2);
		}
		if((lvlchance==2 || lvlchance==3) ){
			addTrigger(20  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.asteroidnormal ,  7);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  7);
			addTrigger(100 , Type.asteroidsmall  ,  8);
			addTrigger(110 , Type.asteroidsmall  ,  15);
			addTrigger(110 , Type.asteroidnormal ,  5);
			addTrigger(140 , Type.asteroidnormal ,  5);
			addTrigger(140 , Type.asteroidsmall  ,  8);
			addTrigger(160 , Type.asteroidsmall  ,  8);
			addTrigger(160 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  2);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidbig    ,  2);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.asteroidsmall  ,  16);
			addTrigger(260 , Type.asteroidbig    ,  1);
			addTrigger(260 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(290 , Type.asteroidnormal ,  6);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidbig    ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
		}
		if((lvlchance==4 || lvlchance==5) ){
			addTrigger(20  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.asteroidbig    ,  1);
			addTrigger(50  , Type.asteroidnormal ,  6);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidbig    ,  1);
			addTrigger(80  , Type.asteroidnormal ,  7);
			addTrigger(100 , Type.asteroidsmall  ,  8);
			addTrigger(110 , Type.asteroidsmall  ,  15);
			addTrigger(110 , Type.asteroidbig    ,  1);
			addTrigger(140 , Type.asteroidnormal ,  7);
			addTrigger(140 , Type.asteroidsmall  ,  8);
			addTrigger(160 , Type.asteroidsmall  ,  8);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  2);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidbig    ,  2);
			addTrigger(240 , Type.asteroidnormal ,  5);
			addTrigger(260 , Type.asteroidnormal ,  8);
			addTrigger(260 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(290 , Type.asteroidbig    ,  1);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidbig    ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(360 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidbig    ,  1);
			addTrigger(400 , Type.asteroidbig    ,  2);
			addTrigger(400 , Type.asteroidsmall  ,  15);
		}
	}
	
	void addasteroids3(){
		//asteroids levels
			addTrigger(20  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  2);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidbig    ,  2);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.asteroidbig    ,  2);
			addTrigger(20  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.asteroidnormal ,  7);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  7);
			addTrigger(140 , Type.asteroidnormal ,  5);
			addTrigger(160 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidbig    ,  2);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.asteroidsmall  ,  16);
			addTrigger(260 , Type.asteroidbig    ,  1);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.asteroidnormal ,  6);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidbig    ,  1);
			addTrigger(80  , Type.asteroidnormal ,  7);
			addTrigger(100 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(340 , Type.asteroidsmall  ,  8);
		
	}
	
	
	void addcats1(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.weak  ,  2);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  2);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==1){
			addTrigger(20  , Type.normal  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(60  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  1);
			addTrigger(140 , Type.normal,  2);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.weak  ,  3);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
		}
		if(lvlchance==2){
			addTrigger(20  , Type.weak  ,  4);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.normal,  1);
			addTrigger(140 , Type.normal,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.normal,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  1);
			addTrigger(300 , Type.normal,  3);
		}
		if(lvlchance==3){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  3);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidnormal ,  4);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  ,  4);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  2);
			addTrigger(80 , Type.weak  ,  1);
			addTrigger(200 , Type.normal,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  2);
			addTrigger(320 , Type.normal,  2);
		}
	}
	
	void addcats2(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.normal,  2);
			addTrigger(80  , Type.normal,  1);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.weak,    5);
			addTrigger(110 , Type.weak  ,  2);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(170 , Type.asteroidsmall ,  4);
			addTrigger(180 , Type.asteroidnormal ,  5);
			addTrigger(190 , Type.weak  ,  6);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.asteroidsmall  ,  10);
			addTrigger(230 , Type.normal,  3);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==1){
			addTrigger(20  , Type.normal ,  1);
			addTrigger(40  , Type.weak   ,  3);
			addTrigger(60  , Type.normal ,  2);
			addTrigger(80  , Type.strong,  1);
			addTrigger(140 , Type.weak ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.weak  ,  6);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
		}
		if(lvlchance==2){
			addTrigger(40  , Type.asteroidnormal  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.strong,  1);
			addTrigger(140 , Type.normal ,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.weak    ,  5);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.strong ,  1);
		}
		if(lvlchance==3){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  3);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.strong  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(160 , Type.normal,  2);
			addTrigger(160 , Type.weak  ,  4);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidbig    ,  3);
			addTrigger(220 , Type.weak  ,  4);
			addTrigger(280 , Type.asteroidnormal ,  4);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.strong,  1);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  ,  4);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  2);
			addTrigger(80  , Type.weak  ,  1);
			addTrigger(200 , Type.normal,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  1);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.strong,  2);
		}
	}

	void addcats3(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  ,  3);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(50  , Type.normal,  2);
			addTrigger(80  , Type.normal,  1);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.weak,    5);
			addTrigger(110 , Type.weak   , 2);
			addTrigger(140 , Type.strong,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(170 , Type.asteroidnormal ,  4);
			addTrigger(180 , Type.asteroidnormal ,  5);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.asteroidsmall  ,  10);
			addTrigger(230 , Type.normal,  3);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(lvlchance==1){
			addTrigger(20  , Type.normal ,  1);
			addTrigger(40  , Type.weak   ,  3);
			addTrigger(60  , Type.normal ,  2);
			addTrigger(80  , Type.normal ,  1);
			addTrigger(80  , Type.strong ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(180 , Type.normal ,  1);
			addTrigger(200 , Type.asteroidnormal ,  5);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			if(bosschance==0)
				addTrigger(290 , Type.big,  1);
			else{
				addTrigger(260 , Type.normal,  3);
				addTrigger(320 , Type.strong,  2);
				
			}
		}
		if(lvlchance==2){
			addTrigger(40  , Type.asteroidnormal  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.strong,  1);
			addTrigger(140 , Type.normal ,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.weak    ,  5);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			if(bosschance==0)
				addTrigger(300 , Type.big ,  1);
			else{
				addTrigger(230  ,Type.weak,  5);
				addTrigger(240 , Type.weak,  5);
				addTrigger(260 , Type.weak,  5);
				addTrigger(280 , Type.weak,  5);
				addTrigger(300 , Type.weak,  5);
				addTrigger(320 , Type.weak,  5);
			}
		}
		if(lvlchance==3){
			addTrigger(20  , Type.strong  , 2);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(140 , Type.normal,  3);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(260 , Type.weak  ,  6);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.strong,  2);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.weak  ,  5);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			if(bosschance==0)
				addTrigger(40 , Type.strong,  1);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(160 , Type.weak  ,  4);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(280 , Type.asteroidnormal ,  4);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(350 , Type.strong,  1);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  ,  4);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.big,  1);
			addTrigger(80  , Type.weak  ,  5);
			addTrigger(200 , Type.normal,  2);
			addTrigger(200 , Type.asteroidnormal ,  3);
			addTrigger(220 , Type.asteroidsmall  ,  8);
		}
	}
	
	void addbosses(){
		if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro){
			addTrigger(20  , Type.strong  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
		}
		else if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo<Save.gd.playerPos && !Save.gd.eventdefeatkuro){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.weak  ,  2);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  2);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatcatious){
			addTrigger(20  , Type.big  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
		}
		else if(Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo<Save.gd.playerPos && !Save.gd.eventdefeatcatious){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.weak  ,  2);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  2);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn){
			addTrigger(20  , Type.bomber, 0 ,  1);
		}
		else if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo<Save.gd.playerPos && !Save.gd.eventdefeatn){
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(50  , Type.normal, 1,  2);
			addTrigger(80  , Type.weak  , 0,  1);
			addTrigger(110 , Type.weak  , 1,  1);
			addTrigger(170 , Type.normal, 1,  2);
			addTrigger(200 , Type.asteroidbig,  1);
			addTrigger(230 , Type.weak  , 1,  1);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(250 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 0,  3);
			addTrigger(330 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 0,  1);
			addTrigger(350 , Type.asteroidsmall  ,  8);
			addTrigger(360 , Type.asteroidnormal ,  4);
			addTrigger(370 , Type.asteroidsmall  ,  8);
			addTrigger(420 , Type.normal, 1,  1);
			addTrigger(450 , Type.strong, 0,  1);
		}
		if(Save.gd.playerGo==CatGame.NORIS1){
			addTrigger(20  , Type.bomber , 1 ,  1);
			addTrigger(400  , Type.bomber, 2 ,  1);
		}
		if(Save.gd.playerGo==CatGame.NORIS2){
			addTrigger(20  , Type.ultima , 3 ,  1);
			
		}
		if(Save.gd.playerGo==CatGame.NORIS3){
			addTrigger(20  , Type.weak , 3 ,  1);
		}
	}
	
	void addasteroids2(){
		addTrigger(20  , Type.asteroidsmall  ,  8);
		addTrigger(40  , Type.asteroidsmall  ,  8);
		addTrigger(50  , Type.asteroidnormal ,  10);
		addTrigger(60  , Type.asteroidsmall  ,  8);
		addTrigger(80  , Type.asteroidsmall  ,  8);
		addTrigger(80  , Type.asteroidnormal ,  7);
		
	}
	
	void adddolphin0(){
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==0){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(40  , Type.weak  ,  2);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  2);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  3);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==1){
			addTrigger(20  , Type.normal  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(60  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  1);
			addTrigger(140 , Type.normal,  2);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.weak  ,  3);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
		}
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==2){
			addTrigger(20  , Type.weak  ,  4);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.normal,  1);
			addTrigger(140 , Type.normal,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.normal,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  1);
			addTrigger(300 , Type.normal,  3);
		}
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==3){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  1);
			addTrigger(110 , Type.weak  ,  3);
			addTrigger(140 , Type.normal,  2);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  4);
			addTrigger(290 , Type.normal,  1);
			addTrigger(320 , Type.normal,  2);
		}
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==4){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.weak  ,  3);
			addTrigger(100 , Type.normal,  2);
			addTrigger(120 , Type.weak  ,  2);
			addTrigger(140 , Type.strong  ,  1);
			addTrigger(160 , Type.normal,  2);
			addTrigger(160 , Type.weak  ,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(220 , Type.weak  ,  2);
			addTrigger(280 , Type.asteroidnormal ,  4);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.strong,  1);
		}
		if(Save.gd.eventdolphinbegin && !Save.gd.eventshield1 && !Save.gd.eventshield2 && lvlchance==5){
			addTrigger(20  , Type.weak  ,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal,  2);
			addTrigger(80  , Type.weak  ,  2);
			addTrigger(120 , Type.asteroidsmall,  8);
			addTrigger(140 , Type.asteroidbig  ,  2);
			addTrigger(200 , Type.normal,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  ,  2);
			addTrigger(290 , Type.normal,  2);
			addTrigger(320 , Type.strong,  1);
		}
	}
	
	void adddolphin1(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  , 0,  1);
			addTrigger(50  , Type.normal, 1,  2);
			addTrigger(80  , Type.weak  , 0,  2);
			addTrigger(110 , Type.weak  , 1,  2);
			addTrigger(140 , Type.normal, 1,  2);
			addTrigger(170 , Type.asteroidbig,  1);
			addTrigger(190 , Type.weak  , 1,  1);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(230 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  , 0,  3);
			addTrigger(290 , Type.normal, 1,  1);
			addTrigger(320 , Type.normal, 0,  1);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(340 , Type.asteroidnormal ,  4);
			addTrigger(360 , Type.asteroidsmall  ,  8);
			addTrigger(380 , Type.normal, 0,  1);
		}
		if(lvlchance==1){
			addTrigger(20  , Type.normal , 0,  1);
			addTrigger(40  , Type.weak   , 1,  1);
			addTrigger(80  , Type.weak   , 0,  1);
			addTrigger(80  , Type.strong , 0,  1);
			addTrigger(140 , Type.weak   , 1,  2);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.weak  , 0,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  , 0,  1);
			addTrigger(290 , Type.normal, 1,  1);
			addTrigger(320 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidnormal ,  8);
			addTrigger(330 , Type.asteroidbig ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(390 , Type.strong , 0,  1);
		}
		if(lvlchance==2){
			addTrigger(20 , Type.normal , 1,  1);
			addTrigger(20 , Type.normal , 0,  1);
			addTrigger(40  , Type.asteroidnormal  ,  8);
			addTrigger(60  , Type.weak  , 0,  1);
			addTrigger(80  , Type.strong, 0,  1);
			addTrigger(130 , Type.normal, 0,  1);
			addTrigger(180 , Type.weak  , 1,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.normal, 1,  1);
			addTrigger(200 , Type.normal, 0,  1);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.normal, 1,  1);
			addTrigger(300 , Type.normal, 0,  1);
			addTrigger(320 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidnormal ,  8);
			addTrigger(330 , Type.asteroidbig ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.normal, 1,  1);
		}
		if(lvlchance==3){ 
			addTrigger(20  , Type.weak  , 1,  1);
			addTrigger(40  , Type.weak  , 0,  1);
			addTrigger(60  , Type.weak  , 0,  1);
			addTrigger(80  , Type.asteroidsmall  ,  4);
			addTrigger(80  , Type.normal, 0,  1);
			addTrigger(140 , Type.normal, 0,  2);
			addTrigger(200 , Type.normal , 1,  1);
			addTrigger(200 , Type.normal , 0,  1);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  , 0,  4);
			addTrigger(290 , Type.normal, 1,  1);
			addTrigger(400 , Type.strong, 0,  2);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.normal, 1,  1);
			addTrigger(20  , Type.normal, 0,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.weak  , 1,  1);
			addTrigger(80  , Type.weak  , 0,  1);
			addTrigger(100 , Type.strong, 1,  1);
			addTrigger(100 , Type.strong, 0,  1);
			addTrigger(170 , Type.asteroidbig    ,  1);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(220 , Type.weak  , 0,  2);
			addTrigger(280 , Type.asteroidnormal ,  4);
			addTrigger(280 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.strong, 0,  1);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  , 1,  1);
			addTrigger(20  , Type.weak  , 0,  2);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal, 0,  2);
			addTrigger(80  , Type.weak  , 1,  1);
			addTrigger(240 , Type.normal, 1,  1);
			addTrigger(240 , Type.normal, 0,  1);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(240 , Type.asteroidnormal ,  4);
			addTrigger(260 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  , 0,  2);
			addTrigger(260 , Type.weak  , 1,  1);
			addTrigger(290 , Type.normal, 1,  2);
			addTrigger(300 , Type.asteroidsmall  ,  8);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(360 , Type.strong, 0,  1);
		}
	}
	
	void adddolphin2(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(50  , Type.normal, 1,  2);
			addTrigger(80  , Type.weak  , 0,  1);
			addTrigger(110 , Type.weak  , 1,  1);
			addTrigger(170 , Type.normal, 1,  2);
			addTrigger(200 , Type.asteroidbig,  1);
			addTrigger(230 , Type.weak  , 1,  1);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(250 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 0,  3);
			addTrigger(330 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 0,  1);
			addTrigger(350 , Type.asteroidsmall  ,  8);
			addTrigger(360 , Type.asteroidnormal ,  4);
			addTrigger(370 , Type.asteroidsmall  ,  8);
			addTrigger(420 , Type.normal, 1,  1);
			addTrigger(450 , Type.strong, 0,  1);
		}
		if(lvlchance==1){
			addTrigger(20  , Type.normal , 2,  1);
			addTrigger(40  , Type.weak   , 1,  1);
			addTrigger(80  , Type.weak   , 0,  1);
			addTrigger(80  , Type.strong , 0,  1);
			addTrigger(140 , Type.weak   , 1,  2);
			addTrigger(180 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.weak  , 0,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(260 , Type.weak  , 0,  1);
			addTrigger(290 , Type.normal, 1,  1);
			addTrigger(320 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidnormal ,  8);
			addTrigger(330 , Type.asteroidbig ,  2);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(390 , Type.strong , 1,  1);
		}
		if(lvlchance==2){
			addTrigger(20 , Type.normal , 2,  1);
			addTrigger(20 , Type.normal , 0,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(60  , Type.weak  , 0,  1);
			addTrigger(80  , Type.strong, 1,  1);
			addTrigger(180 , Type.normal, 2,  1);
			addTrigger(180 , Type.weak  , 1,  1);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(200 , Type.normal, 1,  1);
			addTrigger(200 , Type.normal, 2,  1);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.normal, 1,  1);
			addTrigger(300 , Type.normal, 0,  1);
			addTrigger(320 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.asteroidnormal ,  3);
			addTrigger(330 , Type.asteroidbig ,  1);
			addTrigger(340 , Type.asteroidsmall  ,  8);
			addTrigger(320 , Type.normal, 2,  1);
		}
		if(lvlchance==3){ 
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(40  , Type.weak  , 0,  1);
			addTrigger(60  , Type.weak  , 1,  1);
			addTrigger(80  , Type.asteroidsmall  ,  4);
			addTrigger(80  , Type.normal, 2,  1);
			addTrigger(140 , Type.normal, 0,  2);
			addTrigger(210 , Type.normal , 1,  1);
			addTrigger(220 , Type.normal , 2,  1);
			addTrigger(230 , Type.asteroidsmall  ,  8);
			addTrigger(270 , Type.weak  , 0,  4);
			addTrigger(350 , Type.normal, 1,  1);
			addTrigger(450 , Type.strong, 2,  2);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.normal, 2,  1);
			addTrigger(40  , Type.normal, 0,  1);
			addTrigger(60  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(100 , Type.weak  , 1,  1);
			addTrigger(110 , Type.weak  , 0,  1);
			addTrigger(130 , Type.strong, 1,  1);
			addTrigger(140 , Type.strong, 2,  1);
			addTrigger(200 , Type.asteroidbig    ,  1);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(210 , Type.asteroidsmall  ,  8);
			addTrigger(290 , Type.weak  , 0,  2);
			addTrigger(300 , Type.asteroidnormal ,  4);
			addTrigger(310 , Type.asteroidsmall  ,  8);
			addTrigger(360 , Type.strong, 0,  1);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  , 2,  2);
			addTrigger(20  , Type.weak  , 0,  2);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal, 0,  2);
			addTrigger(80  , Type.weak  , 1,  2);
			addTrigger(200 , Type.normal, 0,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 2,  2);
			addTrigger(350 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 2,  1);
			addTrigger(370 , Type.strong, 0,  1);
		}
	}
	
	void adddolphin3(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(50  , Type.normal, 1,  1);
			addTrigger(50  , Type.normal, 2,  2);
			addTrigger(80  , Type.weak  , 2,  1);
			addTrigger(110 , Type.weak  , 1,  1);
			addTrigger(170 , Type.normal, 2,  1);
			addTrigger(170 , Type.normal, 1,  1);
			addTrigger(200 , Type.asteroidbig,  1);
			addTrigger(230 , Type.weak  , 1,  1);
			addTrigger(240 , Type.asteroidsmall  ,  8);
			addTrigger(250 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 2,  3);
			addTrigger(330 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 2,  1);
			if(bosschance==0)
				addTrigger(400 , Type.bomber,0 ,  1);
			else{
				addTrigger(350 , Type.normal, 0,  3);
				addTrigger(400 , Type.strong, 1,  2);
				
			}
		}
		if(lvlchance==1){
			if(bosschance==0){
				addTrigger(20  , Type.asteroidsmall  ,  8);
				addTrigger(40  , Type.asteroidsmall  ,  8);
				addTrigger(50  , Type.asteroidnormal ,  5);
				addTrigger(60  , Type.asteroidsmall  ,  8);
				addTrigger(80  , Type.asteroidsmall  ,  8);
				addTrigger(80  , Type.asteroidnormal ,  7);
				addTrigger(100 , Type.asteroidsmall  ,  8);
				addTrigger(110 , Type.asteroidsmall  ,  15);
				addTrigger(120 , Type.asteroidsmall  ,  8);
				addTrigger(140 , Type.asteroidnormal ,  7);
				addTrigger(140 , Type.asteroidsmall  ,  8);
				addTrigger(160 , Type.asteroidsmall  ,  8);
				addTrigger(170 , Type.asteroidbig    ,  1);
				addTrigger(180 , Type.asteroidsmall  ,  8);
				addTrigger(200 , Type.asteroidsmall  ,  8);
				addTrigger(200 , Type.asteroidnormal ,  2);
				addTrigger(220 , Type.asteroidsmall  ,  8);
				addTrigger(230 , Type.asteroidbig    ,  2);
				addTrigger(240 , Type.asteroidsmall  ,  8);
				addTrigger(260 , Type.asteroidsmall  ,  16);
				addTrigger(260 , Type.asteroidsmall  ,  8);
				addTrigger(280 , Type.asteroidsmall  ,  8);
				addTrigger(290 , Type.asteroidnormal ,  3);
				addTrigger(300 , Type.asteroidsmall  ,  8);
				addTrigger(320 , Type.asteroidbig    ,  2);
				addTrigger(340 , Type.asteroidsmall  ,  8);
				addTrigger(320 , Type.asteroidsmall  ,  10);
				addTrigger(360 , Type.asteroidsmall  ,  8);
				addTrigger(380 , Type.asteroidsmall  ,  8);
				addTrigger(380 , Type.asteroidbig    ,  2);
			}
			else{
				addTrigger(20  , Type.normal , 2,  1);
				addTrigger(40  , Type.weak   , 1,  1);
				addTrigger(80  , Type.weak   , 0,  1);
				addTrigger(80  , Type.strong , 0,  1);
				addTrigger(140 , Type.weak   , 1,  2);
				addTrigger(180 , Type.asteroidsmall  ,  8);
				addTrigger(200 , Type.asteroidsmall  ,  8);
				addTrigger(200 , Type.weak  , 0,  1);
				addTrigger(260 , Type.weak  , 0,  1);
				addTrigger(290 , Type.normal, 1,  1);
				addTrigger(320 , Type.asteroidsmall  ,  8);
				addTrigger(390 , Type.strong , 1,  1);
				addTrigger(390 , Type.strong , 2,  1);
			}
		}
		if(lvlchance==2){
			addTrigger(20 , Type.normal , 1,  1);
			addTrigger(20 , Type.normal , 2,  1);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(60  , Type.normal  , 0,  1);
			addTrigger(80  , Type.strong, 1,  1);
			addTrigger(180 , Type.normal, 2,  1);
			addTrigger(180 , Type.weak  , 1,  1);
			addTrigger(200 , Type.normal, 1,  1);
			addTrigger(200 , Type.normal, 2,  1);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.normal, 1,  1);
			addTrigger(300 , Type.normal, 0,  1);
			if(bosschance==0)
				addTrigger(360 , Type.bomber,1 ,  1);
			else{
				addTrigger(350 , Type.normal, 0,  3);
				addTrigger(400 , Type.strong, 0,  2);
			}
		}
		if(lvlchance==3){ 
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(40  , Type.weak  , 0,  1);
			addTrigger(60  , Type.weak  , 1,  1);
			addTrigger(80  , Type.asteroidsmall  ,  4);
			addTrigger(80  , Type.normal, 2,  1);
			addTrigger(140 , Type.normal, 0,  2);
			addTrigger(210 , Type.normal , 1,  1);
			addTrigger(220 , Type.normal , 2,  1);
			addTrigger(230 , Type.asteroidsmall  ,  8);
			addTrigger(270 , Type.weak  , 0,  4);
			if(bosschance==0){
				addTrigger(350 , Type.bomber, 1,  1);
			}
			else if(bosschance==1){
				addTrigger(350 , Type.bomber, 1,  1);
			}
			else{
				addTrigger(350 , Type.normal, 1,  1);
				addTrigger(450 , Type.strong, 2,  1);
				addTrigger(450 , Type.strong, 1,  1);
			}
		}
		if(lvlchance==4){
			if(bosschance==0 || bosschance==1){
				addTrigger(20  , Type.normal, 2,  1);
				addTrigger(40  , Type.normal, 0,  1);
				addTrigger(60  , Type.asteroidsmall  ,  8);
				addTrigger(80  , Type.asteroidsmall  ,  8);
				addTrigger(100 , Type.weak  , 1,  1);
				addTrigger(110 , Type.weak  , 0,  1);
				addTrigger(130 , Type.strong, 1,  1);
				addTrigger(140 , Type.strong, 2,  1);
				addTrigger(200 , Type.asteroidbig    ,  1);
				addTrigger(200 , Type.asteroidsmall  ,  8);
				addTrigger(210 , Type.asteroidsmall  ,  8);
				addTrigger(290 , Type.weak  , 0,  2);
				addTrigger(300 , Type.asteroidnormal ,  4);
				addTrigger(310 , Type.asteroidsmall  ,  8);
				addTrigger(360 , Type.strong, 0,  1);
			}
			else{
				addTrigger(20  , Type.weak  , 0,  2);
				addTrigger(40  , Type.weak  , 1,  2);
				addTrigger(60  , Type.weak  , 2,  1);
				addTrigger(80  , Type.weak  , 0,  2);
				addTrigger(100 , Type.weak  , 1,  2);
				addTrigger(120 , Type.weak  , 2,  1);
				addTrigger(140 , Type.weak  , 0,  1);
				addTrigger(160 , Type.weak  , 1,  2);
				addTrigger(180 , Type.weak  , 2,  2);
				addTrigger(200 , Type.weak  , 0,  1);
				addTrigger(220 , Type.weak  , 1,  1);
				addTrigger(240 , Type.weak  , 2,  2);
				addTrigger(260 , Type.weak  , 0,  2);
				addTrigger(280 , Type.weak  , 1,  1);
				addTrigger(300 , Type.weak  , 2,  1);
				addTrigger(320 , Type.weak  , 0,  1);
				addTrigger(240 , Type.weak  , 1,  2);
				addTrigger(260 , Type.weak  , 2,  2);
			}
		}
		if(lvlchance==5){
			addTrigger(20  , Type.weak  , 2,  2);
			addTrigger(20  , Type.weak  , 0,  2);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal, 0,  2);
			addTrigger(80  , Type.weak  , 1,  2);
			addTrigger(200 , Type.normal, 0,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 2,  2);
			addTrigger(350 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 2,  1);
			addTrigger(410 , Type.bomber, 0,  1);
		}
	}
	

	void addnoris1(){
		if(lvlchance==0){
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(50  , Type.normal, 1,  1);
			addTrigger(50  , Type.normal, 2,  1);
			addTrigger(80  , Type.weak  , 2,  1);
			addTrigger(110 , Type.weak  , 1,  1);
			addTrigger(170 , Type.normal, 2,  1);
			addTrigger(170 , Type.normal, 1,  1);
			addTrigger(230 , Type.weak  , 1,  1);
			addTrigger(300 , Type.weak  , 2,  3);
			addTrigger(330 , Type.normal, 1,  1);
			addTrigger(350 , Type.normal, 2,  1);
			addTrigger(400 , Type.bomber, 2,  1);
			
		}
		if(lvlchance==1){
			addTrigger(20  , Type.bomber , 2,  1);
			addTrigger(110 , Type.strong , 1,  1);
			addTrigger(200 , Type.weak  , 1,   1);
			addTrigger(200 , Type.weak  , 2,  1);
			addTrigger(260 , Type.weak  , 2,  1);
			addTrigger(290 , Type.normal, 1,  1);
			addTrigger(330 , Type.strong , 1,  1);
			addTrigger(340 , Type.normal , 2,  1);
			addTrigger(400  , Type.bomber , 2,  1);
			
		}
		if(lvlchance==2){
			addTrigger(20 , Type.normal , 1,  1);
			addTrigger(20 , Type.normal , 2,  2);
			addTrigger(60  , Type.normal, 2,  1);
			addTrigger(80  , Type.strong, 1,  2);
			addTrigger(180 , Type.normal, 2,  1);
			addTrigger(180 , Type.weak  , 1,  1);
			addTrigger(200 , Type.normal, 1,  1);
			addTrigger(200 , Type.normal, 2,  1);
			addTrigger(300 , Type.normal, 1,  1);
			addTrigger(300 , Type.normal, 2,  1);
			addTrigger(350 , Type.strong, 2,  2);
			addTrigger(380 , Type.strong, 1,  1);
		}
		if(lvlchance==3){ 
			addTrigger(20  , Type.weak  , 2,  1);
			addTrigger(40  , Type.weak  , 2,  1);
			addTrigger(60  , Type.weak  , 1,  1);
			addTrigger(80  , Type.asteroidsmall  ,  4);
			addTrigger(80  , Type.normal, 2,  1);
			addTrigger(140 , Type.normal, 2,  2);
			addTrigger(210 , Type.normal , 1,  1);
			addTrigger(220 , Type.normal , 2,  1);
			addTrigger(230 , Type.asteroidsmall  ,  8);
			addTrigger(270 , Type.weak  , 2,  4);
			addTrigger(410 , Type.bomber, 0,  1);
		}
		if(lvlchance==4){
			addTrigger(20  , Type.normal, 2,  1);
			addTrigger(40  , Type.normal, 1,  1);
			addTrigger(100 , Type.weak  , 1,  1);
			addTrigger(110 , Type.weak  , 2,  1);
			addTrigger(130 , Type.normal, 1,  1);
			addTrigger(140 , Type.normal, 2,  1);
			addTrigger(360 , Type.normal, 2,  1);
			addTrigger(60  , Type.weak  , 2,  1);
			addTrigger(120 , Type.weak  , 2,  1);
			addTrigger(140 , Type.weak  , 1,  1);
			addTrigger(200 , Type.weak  , 2,  1);
			addTrigger(220 , Type.weak  , 1,  1);
			addTrigger(280 , Type.weak  , 1,  1);
			addTrigger(300 , Type.weak  , 2,  1);
			addTrigger(320 , Type.weak  , 2,  1);
			addTrigger(320 , Type.strong, 0,  2);
		}
		if(lvlchance==5){
			addTrigger(20  , Type.bomber, 2,  1);
			addTrigger(20  , Type.weak  , 0,  2);
			addTrigger(40  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidsmall  ,  8);
			addTrigger(80  , Type.asteroidnormal ,  4);
			addTrigger(80  , Type.normal, 0,  2);
			addTrigger(80  , Type.weak  , 1,  2);
			addTrigger(200 , Type.normal, 0,  2);
			addTrigger(200 , Type.asteroidsmall  ,  8);
			addTrigger(200 , Type.asteroidnormal ,  4);
			addTrigger(220 , Type.asteroidsmall  ,  8);
			addTrigger(300 , Type.weak  , 2,  2);
			addTrigger(410 , Type.bomber, 2,  1);
		}
	}
	
	void addcosmicrays(){
		if(Save.gd.playerGo==CatGame.ANOMALY){
			///anomaly1 (300) (don't know yet)
			addTrigger(20  , Type.normal, 2,  1);
			addTrigger(30  , Type.strong, 2,  1);
			addTrigger(60  , Type.normal, 2,  1);
			addTrigger(100 , Type.strong, 1,  1);
			addTrigger(140 , Type.strong, 1,  1);
			addTrigger(180 , Type.normal, 1,  1);
			addTrigger(200 , Type.normal, 2,  1);
			addTrigger(240 , Type.bomber, 2,  1);
			///anomaly2 (250) (transition)
			addTrigger(300  , Type.asteroidsmall  ,  8);
			addTrigger(320  , Type.asteroidnormal ,  5);
			addTrigger(320  , Type.asteroidsmall  ,  8);
			addTrigger(340  , Type.asteroidsmall  ,  8);
			addTrigger(360  , Type.asteroidnormal ,  5);
			addTrigger(360  , Type.asteroidsmall  ,  8);
			addTrigger(380  , Type.asteroidbig    ,  3);
			addTrigger(380  , Type.asteroidsmall  ,  8);
			addTrigger(400  , Type.asteroidnormal ,  5);
			addTrigger(400  , Type.asteroidbig    ,  4);
			addTrigger(400  , Type.asteroidsmall  ,  8);
			///anomaly3 (600) (start become white)
			addTrigger(420  , Type.normal ,  1);
			addTrigger(440  , Type.weak   ,  3);
			addTrigger(440  , Type.normal ,  2);
			addTrigger(480  , Type.strong,  1);
			addTrigger(500 , Type.strong ,  1);
			addTrigger(520 , Type.asteroidsmall  ,  8);
			addTrigger(520 , Type.weak  ,  3);
			addTrigger(540 , Type.asteroidsmall  ,  8);
			addTrigger(540 , Type.asteroidnormal ,  4);
			addTrigger(560 , Type.big,  1);
			///anomaly4 (800) (become white)
			addTrigger(600  , Type.cosmicray  ,  2);
			addTrigger(630  , Type.cosmicray  ,  3);
			addTrigger(660  , Type.cosmicray  ,  4);
			addTrigger(680  , Type.cosmicray  ,  5);
			addTrigger(700  , Type.cosmicray  ,  6);
			addTrigger(710  , Type.cosmicray  ,  7);
			addTrigger(720  , Type.cosmicray  ,  7);
			addTrigger(730  , Type.cosmicray  ,  7);
		}
	}
	
	
	
	
	
	void addTrigger (float triggerX, Type type, int count) {
		Trigger trigger = new Trigger();
		trigger.x = triggerX;
		float spawnX=triggerX+35;
		triggers.add(trigger);
		for (int i = 0; i < count; i++) {
			Enemy enemy = new Enemy(this, type,0);
			if(type==Type.probes)
				enemy.position.set(spawnX, 0+MathUtils.random(-7, 6));
			else if(type==Type.cosmicray){
				int r=MathUtils.random(1);
				if(r==0)enemy.position.set(spawnX+MathUtils.random(-3,3), 10);
				if(r==1)enemy.position.set(spawnX+MathUtils.random(-3,3), -10);
			}
			else if(type==Type.ultima) 
				enemy.position.set(spawnX, 0);
			else 
				enemy.position.set(spawnX+MathUtils.random(-5,3), 0+MathUtils.random(-9, 9));          
			
			trigger.enemies.add(enemy);
		}
	}
	
	
	void addTrigger (float triggerX, Type type, int shield,  int count) {
		Trigger trigger = new Trigger();
		trigger.x = triggerX;
		float spawnX=triggerX+35;
		triggers.add(trigger);
		for (int i = 0; i < count; i++) {
			Enemy enemy = new Enemy(this, type, shield);
			if(type==Type.probes)
				enemy.position.set(spawnX, 0+MathUtils.random(-7, 6));
			else if(type==Type.ultima) 
				enemy.position.set(spawnX, -17/9f);
			else                 
				enemy.position.set(spawnX+MathUtils.random(-7,7), 0+MathUtils.random(-9, 9));
			trigger.enemies.add(enemy);
		}
	}

	
	public void update (float delta) {
		if (player.hp <= 0 || (kuro!=null && kuro.hp==0)) {
			gameOverTimer += delta / getTimeScale() * timeScale; // Isn't affected by player death time scaling.
			controller.eventGameOver(false);
		}
		updateEnemies(delta);
		updateEnemyBullets(delta);
		updateEnemySM(delta);
		for (Enemy enemy : enemies) {
			updateEnemyLaser( enemy, delta);
			if(enemy.type==Type.ultima){
				updateEnemyLaser2( enemy, delta);
				updateEnemyLaser3( enemy, delta);
			}
		}
		
		updatePlayerBullets(delta);
		updatePlayerSM(delta);
		updatePlayerSMBullets(delta);
		updatePlayerLaser(delta);
		
		player.update(delta);
		if(kuro!=null) kuro.update(delta);
		updateTriggers();
	}
	

	void updateTriggers () {
		for (int i = 0, n = triggers.size; i < n; i++) {
			Trigger trigger = triggers.get(i);
			if (player.position.x > trigger.x) {
				enemies.addAll(trigger.enemies);
				triggers.removeIndex(i);
				break;
			}
		}
	}

	
	void updateEnemies (float delta) {
		for (int i = enemies.size - 1; i >= 0; i--) {
			Enemy enemy = enemies.get(i);
			enemy.update(delta);
			if (enemy.type==Type.ultima && enemy.gen1hp<=0 && enemy.shieldhp>1){
				enemy.repairtimer1+=delta;
				if(enemy.repairtimer1>=6){
					enemy.gen1hp=50;
					enemy.repairtimer1=0;
				}
			}
			if (enemy.type==Type.ultima && enemy.gen2hp<=0 && enemy.shieldhp>1){
				enemy.repairtimer2+=delta;
				if(enemy.repairtimer2>=6){
					enemy.gen2hp=50;
					enemy.repairtimer2=0;
				}
			}
			if (enemy.deathTimer < 0) {
				enemies.removeIndex(i);
				continue;
			}
			
			if (enemy.hp > 0 && player.hp > 0) {
				if (enemy.type==Type.bombes && enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
					if (player.collisionTimer < 0) {
						// Player gets hit.
						player.dir = enemy.position.x + enemy.rect.width / 2 < player.position.x + player.rect.width / 2 ? -1 : 1;
						float amount = Player.knockbackX * player.dir;
						player.velocity.x = -amount;
						player.velocity.y += Player.knockbackY;
						player.hp-=enemy.bombepower;
						enemy.hp=0;
						if (player.hp > 0) {
							//player.setState(State.fall);
							player.collisionTimer = Player.collisionDelay;
						} else {
							player.state=State.death;
							player.velocity.y *= 0.5f;
						}
						enemy.collisionTimer = Enemy.collisionDelay;
					}
				}
				if ((enemy.type==Type.asteroidbig || enemy.type==Type.asteroidnormal || enemy.type==Type.asteroidsmall) ){
					if(enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) { 
						if (player.collisionTimer < 0) {
							// Player gets hit.
							player.dir = enemy.position.x + enemy.rect.width / 2 < player.position.x + player.rect.width / 2 ? -1 : 1;
							float amount = Player.knockbackX * player.dir;
							player.velocity.x = -amount;
							player.velocity.y += Player.knockbackY;
							//player.setGrounded(false);
							player.hp-=enemy.hp/6+1;
							enemy.hp-=Save.gd.hp*6+1;
							if (player.hp > 0) {
								player.collisionTimer = Player.collisionDelay;
							} else {
								player.state=State.death;
								player.velocity.y *= 0.5f;
							}
							enemy.collisionTimer = Enemy.collisionDelay;
						}
					}
					//asteroid cycling
					if(enemy.position.x>player.position.x+16*16/9 && enemy.velocity.x>Player.ScrollVelocityX-5) enemies.removeIndex(i);
					if(enemy.position.y<-12) {
						if (enemy.type==Type.asteroidbig) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpBig)  enemies.removeIndex(i);
						if (enemy.type==Type.asteroidnormal) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpNormA)enemies.removeIndex(i);
						if (enemy.type==Type.asteroidsmall) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpSmall)enemies.removeIndex(i);
						enemy.position.y=10;
						enemy.position.x+=10;
					}
					if(enemy.position.y>12) {
						if (enemy.type==Type.asteroidbig) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpBig)  enemies.removeIndex(i);
						if (enemy.type==Type.asteroidnormal) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpNormA)enemies.removeIndex(i);
						if (enemy.type==Type.asteroidsmall) 
							if(enemy.velocity.x>Player.ScrollVelocityX-2 || enemy.hp!=Enemy.hpSmall)enemies.removeIndex(i);
						enemy.position.y=-11;
						enemy.position.x+=10;
					}
				}
				
				//collision probes
				if ((enemy.type==Type.probes) 
						&& enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
						enemy.hp=0;
						probeCollect+=1;
						enemy.velocity.x=Player.ScrollVelocityX;
						enemy.collisionTimer = Enemy.collisionDelay;
				}
				//collision metal
				if (enemy.type==Type.metal) {
					if( enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
						enemy.hp=0;
						metalCollect+=1;
						enemy.velocity.x=Player.ScrollVelocityX;
						enemy.collisionTimer = Enemy.collisionDelay;
					}
					if(enemy.position.y>12 || enemy.position.y<-12 
							|| enemy.position.x>player.position.x+16*16/9)
						enemies.removeIndex(i);
					
				}
				
				if (kuro!=null) {
					//asteroids hit PNJ
					// hit pnj
					if ((enemy.type == Type.asteroidbig
							|| enemy.type == Type.asteroidnormal || enemy.type == Type.asteroidsmall)
							&& enemy.collisionTimer < 0
							&& enemy.rect.overlaps(kuro.rect)) {
						if (kuro.collisionTimer < 0) {
							// kuro gets hit.
							kuro.dir = enemy.position.x + enemy.rect.width / 2 < kuro.position.x
									+ kuro.rect.width / 2 ? -1 : 1;
							float amount = Player.knockbackX * kuro.dir;
							kuro.velocity.x = -amount;
							kuro.velocity.y += Player.knockbackY;
							kuro.hp--;
							enemy.hp = 0;
							if (kuro.hp > 0) {
								kuro.collisionTimer = Player.collisionDelay;
							} else {
								kuro.state = State.death;
								kuro.velocity.y *= 0.5f;
							}
							enemy.collisionTimer = Enemy.collisionDelay;
						}
					}
				}
				if(enemy.position.x<player.position.x-10) enemies.removeIndex(i);
				
			}
		}
		// End the game when all enemies are dead and all triggers have occurred.
		if (enemies.size==0 && triggers.size == 0){
			win=true;
			controller.eventGameOver(true);
		}
	}

	
	void updatePlayerBullets (float delta) {
		outer:
		for(int i = playerRGbullets.size - 5; i >= 0; i -= 5) {
			float vx = playerRGbullets.get(i);
			float vy = playerRGbullets.get(i + 1);
			float x  = playerRGbullets.get(i + 2);
			float y  = playerRGbullets.get(i + 3);
			float angle = playerRGbullets.get(i + 4);
			if (Math.abs(x - player.position.x) > 32) {
				// Bullet traveled too far.
				playerRGbullets.removeRange(i, i + 4);
				continue;
			}
			for (Enemy enemy : enemies) {
				if(enemy.shieldtype==1 && enemy.shieldhp>0 && enemy.shield.contains(x, y)) {
					playerRGbullets.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					enemy.shieldhp-=player.RGweaponpower;
					continue outer;
				}
				else if(enemy.shieldtype==3 && enemy.shield.contains(x, y) && enemy.shieldhp>0) {
					if(enemy.gen1hp<=0 && enemy.gen2hp<=0 && enemy.bhtimer<=0){
						enemy.shieldhp-=player.RGweaponpower;
					}
					else if(enemy.gen1hp<=0){
						enemy.shieldhp-=player.RGweaponpower/3;
					}
					playerRGbullets.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					continue outer;
				}
				else if(enemy.type==Type.ultima && enemy.shieldhp>0){
					if (enemy.prot1.contains(x, y) && enemy.prot1hp>0) {
						// Bullet hit enemy.
						playerRGbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot1hp-=player.RGweaponpower;
						if(enemy.prot1hp<=0) enemy.destructiontimer2=10;
						continue outer;
					}
					if (enemy.prot2.contains(x, y) && enemy.prot2hp>0) {
						// Bullet hit enemy.
						playerRGbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot2hp-=player.RGweaponpower;
						if(enemy.prot2hp<=0) enemy.destructiontimer3=10;
						continue outer;
					}
					if (enemy.gen1.contains(x, y) &&  enemy.gen1hp>0) {
						// Bullet hit enemy.
						playerRGbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen1hp-=player.RGweaponpower;
						continue outer;
					}
					if (enemy.gen2.contains(x, y) && enemy.gen2hp>0) {
						// Bullet hit enemy.
						playerRGbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen2hp-=player.RGweaponpower;
						continue outer;
					}
				}
				else if (enemy.rect.contains(x, y) && enemy.state!=State.death) {
					// Bullet hit enemy.
					playerRGbullets.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					//enemy.collisionTimer = Enemy.collisionDelay;
					enemy.hp-=player.RGweaponpower;
					if (enemy.hp <= 0) {
						if(enemy.type==Type.probes || enemy.type==Type.metal) {
							enemy.deathTimer=0;
							enemy.exploded=false;
							enemy.colected=true;
						}
						if(enemy.type==Type.asteroidsmall)  score+=10;
						if(enemy.type==Type.asteroidnormal) score+=100;
						if(enemy.type==Type.asteroidbig)    score+=500;
						if(enemy.type==Type.weak)           score+=20;
						if(enemy.type==Type.normal)         score+=50;
						if(enemy.type==Type.strong)        score+=100;
						if(enemy.type==Type.bomber)         score+=500;
						enemy.state = State.death;
						enemy.velocity.y *= 0.5f;
					} 
					enemy.velocity.x += MathUtils.random((enemy.knockbackX*player.RGweaponpower) / 11f, (enemy.knockbackX*player.RGweaponpower)/4)
						* (player.position.x < enemy.position.x + enemy.rect.width / 2 ? 1 : -1);
					enemy.velocity.y += MathUtils.random((enemy.knockbackX*player.RGweaponpower) / 11f, (enemy.knockbackX*player.RGweaponpower)/4)
							* (Math.sin(angle*2*Math.PI/360));
					
					continue outer;
				}
			}
			x += vx * delta;
			y += vy * delta;
			playerRGbullets.set(i + 2, x);
			playerRGbullets.set(i + 3, y);
		}
	}

	
	void addPlayerRGBullet (float startX, float startY, float vx, float vy, float angle) {
		playerRGbullets.add(vx);
		playerRGbullets.add(vy);
		playerRGbullets.add(startX);
		playerRGbullets.add(startY);
		playerRGbullets.add(angle);
	}
	
	
	void updatePlayerSM (float delta) {
		outer:
		for(int i = playerSM.size - 5; i >= 0; i -= 5) {
			float vx = playerSM.get(i);
			float vy = playerSM.get(i + 1);
			float x  = playerSM.get(i + 2);
			float y  = playerSM.get(i + 3);
			float angle = playerSM.get(i + 4);
			if (Math.abs(x - player.position.x) > 32) {
				// Bullet traveled too far.
				playerSM.removeRange(i, i + 4);
				continue;
			}
			for (Enemy enemy : enemies) {
				if(enemy.shieldtype==1 && enemy.shieldhp>0 && enemy.shield.contains(x, y)) {
					player.view.explodeSM(i);
					playerSM.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					enemy.shieldhp-=player.RGweaponpower;
					continue outer;
				}
				else if(enemy.shieldtype==3 && enemy.shield.contains(x, y) && enemy.shieldhp>0) {
					player.view.explodeSM(i);
					playerSM.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					if(enemy.gen1hp<=0 && enemy.gen2hp<=0){
						enemy.shieldhp-=player.RGweaponpower;
					}
					else if(enemy.gen1hp<=0){
						enemy.shieldhp-=player.RGweaponpower/2;
					}
					continue outer;
				}
				else if(enemy.type==Type.ultima && enemy.shieldhp>0){
					if (enemy.prot1.contains(x, y) && enemy.prot1hp>0) {
						// Bullet hit enemy.
						player.view.explodeSM(i);
						playerSM.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot1hp-=player.RGweaponpower;
						if(enemy.prot1hp<=0) enemy.destructiontimer2=10;
						continue outer;
					}
					if (enemy.prot2.contains(x, y) && enemy.prot2hp>0) {
						// Bullet hit enemy.
						player.view.explodeSM(i);
						playerSM.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot2hp-=player.RGweaponpower;
						if(enemy.prot2hp<=0) enemy.destructiontimer3=10;
						continue outer;
					}
					if (enemy.gen1.contains(x, y) && enemy.gen1hp>0) {
						// Bullet hit enemy.
						player.view.explodeSM(i);
						playerSM.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen1hp-=player.RGweaponpower;
						continue outer;
					}
					if (enemy.gen2.contains(x, y) && enemy.gen2hp>0) {
						// Bullet hit enemy.
						player.view.explodeSM(i);
						playerSM.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen2hp-=player.RGweaponpower;
						continue outer;
					}
				}
				else if (enemy.rect.contains(x, y) && enemy.state!=State.death) {
					// Bullet hit enemy.
					player.view.explodeSM(i);
					playerSM.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					//enemy.collisionTimer = Enemy.collisionDelay;
					enemy.hp-=player.RGweaponpower;
					if (enemy.hp <= 0) {
						if(enemy.type==Type.probes || enemy.type==Type.metal) {
							enemy.deathTimer=0;
							enemy.exploded=false;
							enemy.colected=true;
						}
						if(enemy.type==Type.asteroidsmall)  score+=10;
						if(enemy.type==Type.asteroidnormal) score+=100;
						if(enemy.type==Type.asteroidbig)    score+=500;
						if(enemy.type==Type.weak)           score+=20;
						if(enemy.type==Type.normal)         score+=50;
						if(enemy.type==Type.strong)        score+=100;
						if(enemy.type==Type.bomber)         score+=500;
						enemy.state = State.death;
						enemy.velocity.y *= 0.5f;
					} 
					enemy.velocity.x += MathUtils.random((enemy.knockbackX*player.RGweaponpower) / 8, (enemy.knockbackX*player.RGweaponpower)/4)
						* (player.position.x < enemy.position.x + enemy.rect.width / 2 ? 1 : -1);
					enemy.velocity.y += MathUtils.random((enemy.knockbackX*player.RGweaponpower) / 8, (enemy.knockbackX*player.RGweaponpower)/4)
							* (Math.sin(angle*2*Math.PI/360));
					
					continue outer;
				}
			}
			x += vx * delta;
			y += vy * delta;
			
			playerSM.set(i + 2, x);
			playerSM.set(i + 3, y);
		}
	}
	
	
	void addPlayerSM (float startX, float startY, float vx, float vy, float angle) {
		playerSM.add(vx);
		playerSM.add(vy);
		playerSM.add(startX);
		playerSM.add(startY);
		playerSM.add(angle);
	}
	
	
	void updatePlayerSMBullets (float delta) {
		outer:
		for(int i = playerSMbullets.size - 5; i >= 0; i -= 5) {
			float vx = playerSMbullets.get(i);
			float vy = playerSMbullets.get(i + 1);
			float x  = playerSMbullets.get(i + 2);
			float y  = playerSMbullets.get(i + 3);
			float angle = playerSMbullets.get(i + 4);
			if (Math.abs(x - player.position.x) > 32) {
				// Bullet traveled too far.
				playerSMbullets.removeRange(i, i + 4);
				continue;
			}
			for (Enemy enemy : enemies) {
				if(enemy.shieldtype==1 && enemy.shieldhp>0 && enemy.shield.contains(x, y)) {
					playerSMbullets.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					enemy.shieldhp-=Player.SMweaponpower/2;
					continue outer;
				}
				else if(enemy.shieldtype==3 && enemy.shield.contains(x, y) && enemy.shieldhp>0) {
					playerSMbullets.removeRange(i, i + 4);
					enemy.shieldviewtimer=40;
					if(enemy.gen1hp<=0 && enemy.gen2hp<=0 && enemy.bhtimer<=0){
						enemy.shieldhp-=Player.SMweaponpower/2;
					}
					else if( enemy.bhtimer>0){
						enemy.shieldhp-=Player.SMweaponpower/20;
					}
					else if(enemy.gen1hp<=0){
						enemy.shieldhp-=Player.SMweaponpower/5;
					}
					continue outer;
				}
				else if(enemy.type==Type.ultima && enemy.shieldhp>0){
					if (enemy.prot1.contains(x, y) && enemy.prot1hp>0) {
						// Bullet hit enemy.
						playerSMbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot1hp-=Player.SMweaponpower;
						if(enemy.prot1hp<=0) enemy.destructiontimer2=10;
						continue outer;
					}
					if (enemy.prot2.contains(x, y) && enemy.prot2hp>0) {
						// Bullet hit enemy.
						playerSMbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.prot2hp-=Player.SMweaponpower;
						if(enemy.prot2hp<=0) enemy.destructiontimer3=10;
						continue outer;
					}
					if (enemy.gen1.contains(x, y) &&  enemy.gen1hp>0) {
						// Bullet hit enemy.
						playerSMbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen1hp-=Player.SMweaponpower;
						continue outer;
					}
					if (enemy.gen2.contains(x, y) && enemy.gen2hp>0) {
						// Bullet hit enemy.
						playerSMbullets.removeRange(i, i + 4);
						controller.eventHitBullet(x, y, vx, vy);
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.gen2hp-=Player.SMweaponpower;
						continue outer;
					}
				}
				else if (enemy.rect.contains(x, y) && enemy.state!=State.death) {
					// Bullet hit enemy.
					playerSMbullets.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					//enemy.collisionTimer = Enemy.collisionDelay;
					enemy.hp-=Player.SMweaponpower;
					if (enemy.hp <= 0) {
						if(enemy.type==Type.probes || enemy.type==Type.metal) {
							enemy.deathTimer=0;
							enemy.exploded=false;
							enemy.colected=true;
						}
						if(enemy.type==Type.asteroidsmall)  score+=10;
						if(enemy.type==Type.asteroidnormal) score+=100;
						if(enemy.type==Type.asteroidbig)    score+=500;
						if(enemy.type==Type.weak)           score+=20;
						if(enemy.type==Type.normal)         score+=50;
						if(enemy.type==Type.strong)        score+=100;
						if(enemy.type==Type.bomber)         score+=500;
						enemy.state = State.death;
						enemy.velocity.y *= 0.5f;
					} 
					enemy.velocity.x += MathUtils.random((enemy.knockbackX*Player.SMweaponpower) / (11f*player.SMnumber), (enemy.knockbackX*Player.SMweaponpower)/4)
						* (player.position.x < enemy.position.x + enemy.rect.width / 2 ? 1 : -1);
					enemy.velocity.y += MathUtils.random((enemy.knockbackX*Player.SMweaponpower) / (11f*player.SMnumber), (enemy.knockbackX*Player.SMweaponpower)/4)
							* (Math.sin(angle*2*Math.PI/360));
					
					continue outer;
				}
			}
			x += vx * delta;
			y += vy * delta;
			playerSMbullets.set(i + 2, x);
			playerSMbullets.set(i + 3, y);
		}
	}

	
	void addPlayerSMBullet (float startX, float startY, float vx, float vy, float angle) {
		playerSMbullets.add(vx);
		playerSMbullets.add(vy);
		playerSMbullets.add(startX);
		playerSMbullets.add(startY);
		playerSMbullets.add(angle);
	}
	
	void updatePlayerLaser (float delta) {
		if(shootlz && player.shootlz){
			for(int j = 0; j <= 80; j ++) {
				float i=0.5f*j;
				float x  = player.rect.x+player.rect.width+0.3f+i*MathUtils.cosDeg(player.view.weapangle);
				float y  = player.position.y+i*MathUtils.sinDeg(player.view.weapangle);
				
				for (Enemy enemy : enemies) {
					if(enemy.shieldtype==2 && enemy.shieldhp>0 && enemy.shield.contains(x, y)) {
						enemy.shieldviewtimer=40;
						enemy.shieldhp-=player.laserpower/5f;
						player.laserendx=x;
						player.laserendy=y;
						return;
					}
					else if(enemy.shieldtype==3 && enemy.shield.contains(x, y) && enemy.shieldhp>0) {
						if(enemy.gen1hp<=0 && enemy.gen2hp<=0 && enemy.bhtimer<=0){
							enemy.shieldhp-=player.laserpower/2.5f;
						}
						else if(enemy.gen2hp<=0){
							enemy.shieldhp-=player.laserpower/6f;
						}
						enemy.shieldviewtimer=40;
						player.laserendx=x;
						player.laserendy=y;
						return;
					}
					else if(enemy.type==Type.ultima && enemy.shieldhp>0){
						if (enemy.prot1.contains(x, y)&&enemy.prot1hp>0) {
							// Bullet hit enemy.
							controller.eventHitBulletlz(x, y, MathUtils.cosDeg(player.view.weapangle), MathUtils.sinDeg(player.view.weapangle));
							//enemy.collisionTimer = Enemy.collisionDelay;
							enemy.prot1hp-=player.laserpower/1.5f;
							if(enemy.prot1hp<=0) enemy.destructiontimer2=10;
							player.laserendx=x;
							player.laserendy=y;
							return;
						}
						if (enemy.prot2.contains(x, y) && enemy.prot2hp>0) {
							// Bullet hit enemy.
							controller.eventHitBulletlz(x, y, MathUtils.cosDeg(player.view.weapangle), MathUtils.sinDeg(player.view.weapangle));
							//enemy.collisionTimer = Enemy.collisionDelay;
							enemy.prot2hp-=player.laserpower/1.5f;
							if(enemy.prot2hp<=0) enemy.destructiontimer3=10;
							player.laserendx=x;
							player.laserendy=y;
							return;
						}
						if (enemy.gen1.contains(x, y) && enemy.gen1hp>0) {
							// Bullet hit enemy.
							controller.eventHitBulletlz(x, y, MathUtils.cosDeg(player.view.weapangle), MathUtils.sinDeg(player.view.weapangle));
							//enemy.collisionTimer = Enemy.collisionDelay;
							enemy.gen1hp-=player.laserpower/1.5f;
							player.laserendx=x;
							player.laserendy=y;
							return;
						}
						if (enemy.gen2.contains(x, y)&& enemy.gen2hp>0) {
							// Bullet hit enemy.
							controller.eventHitBulletlz(x, y, MathUtils.cosDeg(player.view.weapangle), MathUtils.sinDeg(player.view.weapangle));
							//enemy.collisionTimer = Enemy.collisionDelay;
							enemy.gen2hp-=player.laserpower/1.5f;
							player.laserendx=x;
							player.laserendy=y;
							return;
						}
					}
					else if (enemy.rect.contains(x, y) && enemy.state!=State.death) {
						// Bullet hit enemy.
						controller.eventHitBulletlz(x, y, MathUtils.cosDeg(player.view.weapangle), MathUtils.sinDeg(player.view.weapangle));
						//enemy.collisionTimer = Enemy.collisionDelay;
						enemy.hp-=player.laserpower/1.5f;
						player.laserendx=x;
						player.laserendy=y;
						if (enemy.hp <= 0) {
							if(enemy.type==Type.probes || enemy.type==Type.metal) {
								enemy.deathTimer=0;
								enemy.exploded=false;
								enemy.colected=true;
							}
							if(enemy.type==Type.asteroidsmall)  score+=10;
							if(enemy.type==Type.asteroidnormal) score+=100;
							if(enemy.type==Type.asteroidbig)    score+=500;
							if(enemy.type==Type.weak)        score+=20;
							if(enemy.type==Type.normal)      score+=50;
							if(enemy.type==Type.strong)      score+=100;
							if(enemy.type==Type.big)         score+=250;
							if(enemy.type==Type.bomber)      score+=500;
							enemy.state = State.death;
							enemy.velocity.y *= 0.5f;
						} 
						return;
					}
					else{
						player.laserendx=x;
						player.laserendy=y;
					}
				}
				if(enemies.size==0){
					player.laserendx=x;
					player.laserendy=y;
				}
			}
		}
	}
	
	////enemies
	
	void updateEnemyBullets (float delta) {
		outer:
		for(int i = enemybullets.size - 6; i >= 0; i -= 6) {
			float vx = enemybullets.get(i);
			float vy = enemybullets.get(i + 1);
			float x  = enemybullets.get(i + 2);
			float y  = enemybullets.get(i + 3);
			float angle = enemybullets.get(i + 4);
			float power = enemybullets.get(i + 5);
			if (Math.abs(x - player.position.x) > 25) {
				// Bullet traveled too far.
				enemybullets.removeRange(i, i + 5);
				continue;
			}
			
			if (player.state == State.death) continue;
			if(player.shield.contains(x, y) && player.shieldhp>0) {
				enemybullets.removeRange(i, i + 5);
				player.shieldviewTimer=40;
				player.shieldhp-=power;
				if(player.shieldhp<=0)player.shieldRLTimer = 20-Save.gd.shieldreload;
				continue outer;
				
			}
			else if (player.rect.contains(x, y)) {
				if (player.collisionTimer < 0) {
					// Bullet hit player.
					enemybullets.removeRange(i, i + 5);
					controller.eventHitBullet(x, y, vx, vy);
					//controller.eventHitPlayer(player);
					player.collisionTimer = Player.collisionDelay;
					player.velocity.x += (float) (Player.knockbackX/2*Math.cos(angle*2*Math.PI/360));
					player.hp-=power;
					if (player.hp <= 0) {
						player.state = State.death;
						player.velocity.y *= 0.5f;
					} 
					continue outer;
				}
			}
			
			x += vx * delta;
			y += vy * delta;
			enemybullets.set(i + 2, x);
			enemybullets.set(i + 3, y);
		}
	}
	
	
	void addEnemyBullet (float startX, float startY, float vx, float vy, float angle, float power) {
		enemybullets.add(vx);
		enemybullets.add(vy);
		enemybullets.add(startX);
		enemybullets.add(startY);
		enemybullets.add(angle);
		enemybullets.add(power);
	}
	
	
	void updateEnemySM (float delta) {
		outer:
		for(int i = enemySM.size - 6; i >= 0; i -= 6) {
			float vx = enemySM.get(i);
			float vy = enemySM.get(i + 1);
			float x  = enemySM.get(i + 2);
			float y  = enemySM.get(i + 3);
			if (Math.abs(x - player.position.x) > 32) {
				// Bullet traveled too far.
				enemySM.removeRange(i, i + 5);
				continue;
			}
			explodSMtimer-=delta;
			if(explodSMtimer<0){
				explodeEnemySM(0);
				enemySM.removeRange(i, i + 5);
				explodSMtimer=MathUtils.random(0.45f, 0.8f);
				continue outer;
				
			}
			
			if (player.state == State.death) continue;
			if(player.shield.contains(x, y) && player.shieldhp>0) {
				explodeEnemySM(i);
				enemySM.removeRange(i, i + 4);
				player.shieldviewTimer=40;
				if(player.shieldhp<=0)player.shieldRLTimer = 20-Save.gd.shieldreload;
				continue outer;
				
			}
			else if (player.rect.contains(x, y)) {
				if (player.collisionTimer < 0) {
					// Bullet hit player.
					explodeEnemySM(i);
					enemySM.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					//controller.eventHitPlayer(player);
					player.collisionTimer = Player.collisionDelay;
					player.velocity.x = -Player.knockbackX/2;
					player.hp-=1;
					if (player.hp <= 0) {
						player.state = State.death;
						player.velocity.y *= 0.5f;
					} 
					continue outer;
				}
			}
			
			x += vx * delta;
			y += vy * delta;
			enemySM.set(i + 2, x);
			enemySM.set(i + 3, y);
		}
	}
	
	
	void explodeEnemySM (int smNbr) {
		// Compute the position and velocity to spawn a new bullet.
		float sushix = enemySM.get(smNbr+2);
		float sushiy = enemySM.get(smNbr+3);
		for(int i =0;i<Enemy.smNbr; i++){
			
			float angle = 360/Enemy.smNbr*i;
			float cos = MathUtils.cosDeg(angle), sin = MathUtils.sinDeg(angle);
			float vx = cos * Enemy.bulletSpeed;
			float vy = sin * Enemy.bulletSpeed;
			float power=enemySM.get(smNbr+5);
			sushix += cos * 2 * scale;
			sushiy += sin * 2 * scale;
			addEnemyBullet(sushix, sushiy, vx, vy, temp1.set(vx, vy).angle(), power);
		}
		//burstShots = Math.min(player.RGmaxShots2heat, burstShots + 1);
	}
	
	
	void addEnemySM (float startX, float startY, float vx, float vy, float angle, float power) {
		enemySM.add(vx);
		enemySM.add(vy);
		enemySM.add(startX);
		enemySM.add(startY);
		enemySM.add(angle);
		enemySM.add(power);
	}
	
	
	void updateEnemyLaser (Enemy enemy, float delta) {
		if(enemy.shootinglz){
			for(int j = 0; j <= 60; j ++) {
				float i=0.5f*j;
				float x  = enemy.laserstartx+i*MathUtils.cosDeg(enemy.shootlzangle);
				float y  = enemy.laserstarty+i*MathUtils.sinDeg(enemy.shootlzangle);
				if(player.shield.contains(x, y) && player.shieldhp>0) {
					enemy.laserendx=x;
					enemy.laserendy=y;
					player.shieldviewTimer=40;
					player.shieldhp-=enemy.laserpower;
					if(player.shieldhp<=0)player.shieldRLTimer = 20-Save.gd.shieldreload;
					return;
					
				}
				else if (player.rect.contains(x, y)) {
					if (player.collisionTimer < 0) {
						// Bullet hit player.
						enemy.laserendx=x;
						enemy.laserendy=y;
						controller.eventHitBullet(x, y, MathUtils.cosDeg(enemy.shootlzangle), MathUtils.sinDeg(enemy.shootlzangle));
						//controller.eventHitPlayer(player);
						player.hp-=enemy.laserpower/1.5f;
						if (player.hp <= 0) {
							player.state = State.death;
							player.velocity.y *= 0.5f;
						} 
						return;
					}
				}
				else{
					enemy.laserendx=x;
					enemy.laserendy=y;
				}
			}
		}
	}
	
	void updateEnemyLaser2 (Enemy enemy, float delta) {
		if(enemy.shootinglz2){
			for(int j = 0; j <= 60; j ++) {
				float i=0.5f*j;
				float x  = enemy.position.x+enemy.rect.width/2-enemy.ultiw*0.41f+enemy.ultiw*0.0844f-i;
				float y  = enemy.lzstarty2;
					
				if(player.shield.contains(x, y) && player.shieldhp>0) {
					enemy.laserendx2=x;
					player.shieldviewTimer=40;
					player.shieldhp-=enemy.laserpower;
					if(player.shieldhp<=0)player.shieldRLTimer = 20-Save.gd.shieldreload;
					return;
					
				}
				else if (player.rect.contains(x, y)) {
					if (player.collisionTimer < 0) {
						// Bullet hit player.
						enemy.laserendx2=x;
						controller.eventHitBullet(x, y, -1, 0);
						//controller.eventHitPlayer(player);
						player.hp-=enemy.laserpower/1.5f;
						if (player.hp <= 0) {
							player.state = State.death;
							player.velocity.y *= 0.5f;
						} 
						return;
					}
				}
				else{
					enemy.laserendx2=x;
				}
			}
		}
	}
	
	void updateEnemyLaser3 (Enemy enemy, float delta) {
		if(enemy.shootinglz3){
			for(int j = 0; j <= 60; j ++) {
				float i=0.5f*j;
				float x  = enemy.position.x+enemy.rect.width/2-enemy.ultiw*0.41f+enemy.ultiw*0.0844f-i;
				float y  = enemy.lzstarty3;
					
				if(player.shield.contains(x, y) && player.shieldhp>0) {
					enemy.laserendx3=x;
					player.shieldhp-=enemy.laserpower;
					if(player.shieldhp<=0)player.shieldRLTimer = 20-Save.gd.shieldreload;
					return;
					
				}
				else if (player.rect.contains(x, y)) {
					if (player.collisionTimer < 0) {
						// Bullet hit player.
						enemy.laserendx3=x;
						controller.eventHitBullet(x, y, -1, 0);
						//controller.eventHitPlayer(player);
						player.hp-=enemy.laserpower/1.5f;
						if (player.hp <= 0) {
							player.state = State.death;
							player.velocity.y *= 0.5f;
						} 
						return;
					}
				}
				else{
					enemy.laserendx3=x;
				}
			}
		}
	}
	
	public float getTimeScale () {
		if (player.hp <= 0|| (kuro!=null && kuro.hp==0))
			return timeScale * Interpolation.pow2In.apply(0.1f, 0, MathUtils.clamp(gameOverTimer / gameOverSlowdown, 0.01f, 1));
		return timeScale;
	}

	
	enum State {
		death
	}

	
	public static class Trigger {
		float x;
		Array<Enemy> enemies = new Array<Enemy>();
	}

}
