package quentin.jeu.cat.utils;

import java.util.Calendar;

import quentin.jeu.cat.CatGame;

public class GameData {
	
	//////////TIME/////////////////////
	public int year, month, day, hour, minute, second, timelaps;
	
	//////////MAP//////////////////////
	public static int starnumber=500;
	public float[]   starsize, starX,starY;
	public int[]     starType;
	public boolean[] visited, explored;
	public int playerPos, playerGo, explornbr, visitnbr, playerPosEvent;
	
	/////////////STORY//////////////////
	public boolean eventkuro, eventkuro1, eventdest, eventdest1, eventlose,
					eventbosskuro, eventdefeatkuro, eventdefeatkuro1,
					eventbosscatious, eventdefeatcatious, eventdefeatcatious1, eventendcat;
	public boolean eventdolphinbegin, eventspeak1, eventRemorse,
					eventshield1, eventshield11, eventshield2, eventshield22, eventspeak;
	public boolean eventbossn, eventdefeatn, eventdefeatn1,norisdefeat, anomaly, anomaly1,eventlose2;
	
	///////RESSOURCES//////////////////
	public int metal, maki, probes;
	
	////////TECHNO/////////////////////
	public boolean drillDisp, hmakiDisp ,smDisp, lzDisp, shieldDisp;
	public boolean drillOwn, hmakiOwn ,smOwn, lzOwn, shieldOwn;
	
	///////////SHIP////////////////////
	public float hp, speed, acceleration;
	public int weapon1, weapon2, weapon3;
	//////////SHIELD//////////////////
	public float shieldhp, shieldreload;
	/////////WEAPONS///////////////////
	public float riceGspeed, riceGprec, riceGpower;
	public float sushiGnbr, sushiGfr, sushiGspeed;
	public float lasermax, lasermin, laserreload;
	
	/////////MISSION/////////////
	public int metalspent, score, norisdefeated, lose;
	////////GOOGLE SERVICES
	public int metalspentgs, scoregs, visitnbrgs;
	
	/////////CONF/////////////////////
	public boolean musicEnabled, soundEnabled, accelerometer;
	public int lang;
	
	public GameData() {
	}
	
	// sets up an empty high scores table
	public void init() {
		//time
		year   = Calendar.getInstance().get(Calendar.YEAR);
		month  = Calendar.getInstance().get(Calendar.MONTH);
		day    = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		hour   = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		minute = Calendar.getInstance().get(Calendar.MINUTE);
		second = Calendar.getInstance().get(Calendar.SECOND);
		//resource
		maki   = 60;
		metal  = 20;
		probes = 0;
		//ship
		hp           = 4;
		speed        = 4;
		acceleration = 9;
		//weapons
		weapon1=CatGame.RG;
		weapon2=-1;
		weapon3=-1;
				
		riceGspeed = 2f;
		riceGpower = 10f;
		riceGprec  = 10f;
		
		sushiGnbr  = 10f;
		sushiGfr   = 2f;
		sushiGspeed= 7f;
		
		lasermax=0.2f;
		lasermin=0;
		laserreload=0.5f;
		
		shieldhp = 2;
		shieldreload = 0;
		
		//map
		playerPos = -1;
		starsize= new float  [starnumber];
		starX   = new float  [starnumber];
		starY   = new float  [starnumber];
		starType= new int    [starnumber];
		visited = new boolean[starnumber];
		explored= new boolean[starnumber];
		for(int i=0;i<starsize.length; i++){
			starsize[i]=starX[i]=starY[i]=starType[i]=-1;
			visited[i]=false;
			explored[i]=false;
		}
		//conf
		musicEnabled =true;
		soundEnabled =true;
		accelerometer=false;
		lang=-1;
		
		playerPosEvent=10000;
		
	}
	
	public void razHS() {
		playerPos = 0;
		musicEnabled=true;
		soundEnabled=true;
	
	}
	
}
