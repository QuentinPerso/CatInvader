package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Model.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/** The model class for an enemy. */
public class Enemy extends Character {
  /*
 	level dependent parameter :
  	- HP, fire power, fire speed, bullet speed
  */
	////////////////////GLOBAL (ALL TYPE)/////////////////////////
	static float width = 160/1.5f * scale, height = 160/1.5f * scale;
	static float corpseTime = .5f;
	static float collisionDelay = 0.3f;
	////////////////////SHIP////////////////////////////////////
	//sizes
	static float sizeWeak = 0.7f, sizeSmall = 0.6f, sizeBomber = 2.5f, sizeStrong = 1.3f, smallCount = 6, sizeBig = 7;
	//velocity y
	static float weakVelocityY = 12, bombeVelocityY = 8, normalVelocityY = 8, strongVelocityY = 10, becomesBigVelocityY = 8, bomberVelocityY = 6,
			bigcatVelocityY=1;
	//Acceleration
		static float hightaccel = 15, normalaccel = 8, lowaccel = 5;
	//HP
	 float 	hpBombe  ,hpWeak   ,hpNormal  ,hpStrong  ,hpBigCat  ,hpkuro   , hpcatious ,hpBomber  ; 
	 
	 //////////////WEAPONS//////////////////////////////
	//RG
	float shootDWeak = 5, shootDNormal = 2f, shootDStrong = 1.2f, shootDBig = 8, shootDBomber = 2, bombDBomber = 2;
	float rgpower, rgpower1, rgpower2, rgpower3;
	int rafalnbr=0, rafalnbr1, rafalnbr2;
	//SM
	static float smNbr=11;
	float shootSM=5;
	float smpower;
	//Bombs
	float bombepower;
	//LASER
	public float shootlzangle, laserendx, laserendy, laserstartx, laserstarty;
	float lasershoottime= 0.05f, laseraimtime= 0.05f, lasertimer, laserRLtime;
	float laserpower, laserpower1 ,laserpower2, laserpower3, laserprec;
	public boolean shootinglz, canShootlz;
	
	float laserspeed=0.25f;
	
	//////////////////////////shield/////////////////////////
	public Rectangle shield = new Rectangle();
	float shieldMaxhp;
	float shieldhp;
	float shieldviewtimer;
	
	//////////////////////////  ULTIMA  /////////////////////////
	public Rectangle prot1, prot2, gen1, gen2;
	float prot1hp, prot2hp, gen1hp, gen2hp;
	float destructiontimer2,destructiontimer3,repairtimer1, repairtimer2;
	public boolean shootinglz2, shootinglz3, canShootlz2;
	float laserendx2,laserendx3,lzstarty2,lzstarty3;
	float lzt2;
	int randomlz=-1;
	float ultih=17, ultiw=13.7f; //w#=0.82*h
	///////////NORIS 3//////////
	float bhtimer;
	static boolean bhed, bomblaser;
	
	//other non type specific (yet...)
	static float maxOffsetX    = -10f  , minOffsetX = 1f;
	static float shootOffsetX  = 50    , shootOffsetY = 20;
	static float smSpeed   = 5, bulletSpeed   = 10    , burstDuration = 0.18f;
	static float precision = 8;
	static float normalKnockbackX = 3, normalKnockbackY = 0, bigKnockbackX = 1, bigKnockbackY = 0;
	
	////////////////////ASTEROIDS////////////////////////////////////
	static float SACount = 8, NACount=4;
	static float hpSmall = 1, hpNormA = 30, hpBig = 60;
	float maxspeedSmall=5, maxspeedNorm=3, maxspeedBig=2;
	//asteroids spawn metal
	int metalchance;
	
	/////////////////Timers and proper stuff////////
	public Type  type;
	public int shieldtype;
	float size = 1;
	//enemy basic fire
	float shootDelay;
	float shootTimer;
	//enemy SM
	float shootDelaySM=-1;
	float shootTimerSM;
	//bomb
	float bombTimer;
	//metal timer
	float metalTimer,metaltimer1;
	//other
	float deathTimer = corpseTime;
	float offsetX;
	float collisionTimer;
	float spawnSmallsTimer;
	float spawnSATimer,spawnNATimer,spawnMetalTimer;
	boolean exploded, colected, moveup=true;
	float moverange;
	int collisions;
	float knockbackX , knockbackY;

	// This is here for convenience, the model should never touch the view.
	EnemyView view;

	Enemy (Model model, Type type, int shieldtype) {
		//true for all (and default)
		super(model);
		this.type    = type;
		this.shieldtype=shieldtype;
		size = 1;
		exploded     = false;
		colected     = true;
		acceleration = normalaccel;
		knockbackX = normalKnockbackX;
		knockbackY = normalKnockbackY;
		shootTimer   = MathUtils.random(0, 3);
		offsetX      = MathUtils.random(maxOffsetX, minOffsetX);
		maxVelocityX = MathUtils.random(-2, 4);

		/*
		  for each enemy define : (else default)
		  - size 
		  - velocity y
		  - HP                                                        
		  - fire speed
		  - knock back
		  - offset
		  - acceleration y
		  - specific offset 
		 */
		if((Save.gd.playerGo<CatGame.EVENTBOSSKURO && Save.gd.eventdefeatkuro1) || !Save.gd.eventdefeatkuro1){
			hpWeak    = 1;
			hpNormal  = 4;
			hpkuro    = 40; 
			shootDWeak   = 5;
			shootDNormal = 2f;
			shootDStrong = 1.2f;
			shootDBig    = 8;
			shootDBomber = 2;
			bombDBomber  = 2;
			rgpower1 =rgpower2= rgpower3=1;
			smpower = 1;
			metaltimer1=MathUtils.random(12, 16);
			
		}
		else if ((Save.gd.playerGo<CatGame.EVENTBOSSCATIOUS && Save.gd.eventdefeatcatious1) || !Save.gd.eventdefeatcatious1){
			hpBombe   = 1;
			hpWeak    = 1;
			hpNormal  = 6;
			hpStrong  = 12;
			hpkuro    = 40; 
			hpcatious = 500;
			rgpower1 =rgpower2= rgpower3=1;
			rafalnbr2 =2;
			shootDWeak   = 5;
			shootDNormal = 2f;
			shootDStrong = 1.2f;
			shootDBig    = 8;
			shootDBomber = 2;
			bombDBomber  = 2;
			bombepower=1;
			shootSM   = 6;
			smpower = 1;
			metaltimer1=MathUtils.random(12, 16);
			
		}
		else if (!Save.gd.eventendcat){
			hpBombe   = 1;
			hpWeak    = 2;
			hpNormal  = 8;
			hpStrong  = 30;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 200; 
			rgpower1 =rgpower2= rgpower3=1.25f;
			shootDWeak   = 5;
			shootDNormal = 2.5f;
			shootDStrong = 1.7f;
			shootDBig    = 8;
			shootDBomber = 2;
			bombDBomber  = 2;
			smpower   = 1.25f;
			rafalnbr2  =1;
			bombepower =2;
			shootSM   = 8;
			metaltimer1=MathUtils.random(12, 16);
		}
		else if (Save.gd.eventdolphinbegin && !Save.gd.eventshield1){
			hpBombe   = 1;
			hpWeak    = 2;
			hpNormal  = 10;
			hpStrong  = 30;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 200; 
			rgpower1 = 1;
			rgpower2 = smpower = 1.5f;
			rgpower3 = 2f;
			shootDWeak   = 6;
			shootDNormal = 2.5f;
			shootDStrong = 1.5f;
			shootDBig    = 8;
			shootDBomber = 2;
			bombDBomber  = 2;
			rafalnbr1  =1;
			rafalnbr2  =0;
			bombepower =2;
			shootSM   = 8;
			lasertimer    = MathUtils.random(lasershoottime);
			laserRLtime=1;
			lasershoottime= 0.05f;
			laseraimtime  = 0.05f*1/1f;
			laserpower2    = 0.055f;
			laserprec     = 3;
			metaltimer1=MathUtils.random(12, 16);
		}
		else if (Save.gd.eventshield1 && !Save.gd.eventshield2){
			hightaccel = 10;
			hpBombe   = 1;
			hpWeak    = 5;
			hpNormal  = 10;
			hpStrong  = 30;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 200; 
			rgpower1 = 1;
			rgpower2 = smpower = 1.5f;
			rgpower3 = 2f;
			shootDWeak   = 6;
			shootDNormal = 2.5f;
			shootDStrong = 3f;
			shootDBig    = 8;
			shootDBomber = 2;
			bombDBomber  = 2;
			rafalnbr1  =1;
			rafalnbr2  =0;
			bombepower =2;
			shootSM   = 8;
			lasertimer    = MathUtils.random(lasershoottime);
			lasershoottime= 0.05f;
			laserRLtime=1;
			laseraimtime  = 0.05f*1/1f;
			laserpower2    = 0.055f;
			laserprec     = 3;
			shieldMaxhp = 15;
			shieldhp    = shieldMaxhp;
			metaltimer1=MathUtils.random(18, 25);
		}
		else if (Save.gd.eventshield2  && !Save.gd.eventbossn){
			//weak not so weak so less fast
			hightaccel = 10;
			//HP
			hpBombe   = 1;
			hpWeak    = 5;
			hpNormal  = 10;
			hpStrong  = 30;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 200; 
			//RG power
			rgpower1 = 1;
			rgpower2 = smpower = 1.5f;
			rgpower3 = 2f;
			// burst number
			rafalnbr1  =1; //weak normal
			rafalnbr2  =0; //strong big bomber
			//fire rate gun
			shootDWeak   = 6;
			shootDNormal = 2.5f;
			shootDStrong = 3f;
			shootDBig    = 8;
			shootDBomber = 2;
			//fire rate missiles
			shootSM   = 8;
			//laser
			lasertimer    = MathUtils.random(lasershoottime);
			lasershoottime= 0.05f;
			laseraimtime  = 0.05f*1/1f;
			laserRLtime=1;
			laserpower2    = 0.055f;
			laserprec     = 3;
			shieldMaxhp = 15;
			shieldhp    = shieldMaxhp;
			//bombs
			bombDBomber  = 2;
			bombepower = 2;
			
			metaltimer1=MathUtils.random(18, 25);
		}
		else if (Save.gd.eventbossn && !Save.gd.eventspeak){  // dolphin3
			//weak not so weak so less fast
			hightaccel = 10;
			//HP
			hpBombe   = 1;
			hpWeak    = 5;
			hpNormal  = 10;
			hpStrong  = 30;
			hpBigCat  = 400;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 100; 
			//RG power
			rgpower1 = 1;
			rgpower2 = smpower = 1.5f;
			rgpower3 = 2f;
			// burst number
			rafalnbr1  =1; //weak normal
			rafalnbr2  =0; //strong big bomber
			//fire rate gun
			shootDWeak   = 6;
			shootDNormal = 2.5f;
			shootDStrong = 3f;
			shootDBig    = 8;
			shootDBomber = 2;
			//fire rate missiles
			shootSM   = 8;
			//laser
			lasershoottime= 0.05f;
			lasertimer    = MathUtils.random(lasershoottime);
			laserRLtime=1;
			laserRLtime=1;
			laseraimtime  = 0.05f*1/1f;
			laserpower1    = 0.055f;
			laserpower2    = 0.065f;
			laserpower3    = 0.075f;
			laserprec     = 3;
			shieldMaxhp = 30;
			shieldhp    = shieldMaxhp;
			//bombs
			bombDBomber  = 3.5f;
			bombepower = 3;
			metaltimer1=MathUtils.random(18, 25);
		}
		
		else if (Save.gd.eventspeak && 
				Save.gd.playerGo!=CatGame.NORIS1 && Save.gd.playerGo!=CatGame.NORIS2 && Save.gd.playerGo!=CatGame.NORIS3){  // noris
			//weak not so weak so less fast
			hightaccel = 10;
			//HP
			hpBombe   = 1;
			hpWeak    = 8;
			hpNormal  = 15;
			hpStrong  = 50;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 150; 
			//RG power
			rgpower1 = 1f;
			rgpower2 = smpower = 1.75f;
			rgpower3 = 2.5f;
			// burst number
			rafalnbr1  =1; //weak normal
			rafalnbr2  =0; //strong big bomber
			//fire rate gun
			shootDWeak   = 4;
			shootDNormal = 3f;
			shootDStrong = 4f;
			shootDBig    = 8;
			shootDBomber = 5;
			//fire rate missiles
			shootSM   = 8;
			//laser
			lasershoottime= 0.05f;
			laserRLtime=1;
			lasertimer    = MathUtils.random(lasershoottime);
			if(Save.gd.playerGo<CatGame.NORIS2)
				laseraimtime  = 0.05f*0.7f;
			else laseraimtime  = 0;
			laserspeed  =0.3f;
			laserpower1    = 0.055f;
			laserpower2    = 0.075f;
			laserpower3    = 0.085f;
			laserprec     = 10;
			//shield
			shieldMaxhp = 30;
			shieldhp    = shieldMaxhp;
			//bombs
			bombDBomber  = 3.9f;
			bombepower = 3;
			metaltimer1=MathUtils.random(18, 25);
		}
		else if (Save.gd.playerGo==CatGame.NORIS1){  // NORIS 1
			//weak not so weak so less fast
			hightaccel = 10;
			//HP
			hpBombe   = 1;
			hpWeak    = 8;
			hpNormal  = 15;
			hpStrong  = 50;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 150; 
			//RG power
			rgpower1 = 1.25f;
			rgpower2 = smpower = 2f;
			rgpower3 = 3f;
			// burst number
			rafalnbr1  =1; //weak normal
			rafalnbr2  =1; //strong big bomber
			//fire rate gun
			shootDWeak   = 4;
			shootDNormal = 2.2f;
			shootDStrong = 2f;
			shootDBig    = 8;
			shootDBomber = 2;
			//fire rate missiles
			shootSM   = 8;
			//laser
			lasershoottime= 0.05f;
			laserRLtime=1;
			lasertimer    = MathUtils.random(lasershoottime);
			laseraimtime  = 0.05f*0.7f/1f;
			laserpower1    = 0.055f;
			laserpower2    = 0.075f;
			laserpower3    = 0.085f;
			laserprec     = 10;
			//shield
			shieldMaxhp = 30;
			shieldhp    = shieldMaxhp;
			//bombs
			bombDBomber  = 3.5f;
			bombepower = 3;
			metaltimer1=MathUtils.random(18, 25);
		}
		else if (Save.gd.playerGo==CatGame.NORIS2){ /////////NORIS 2
			//weak not so weak so less fast
			hightaccel = 10;
			//HP
			hpBombe   = 1;
			hpWeak    = 8;
			hpNormal  = 15;
			hpStrong  = 50;
			hpBigCat  = 200;
			hpkuro    = 40; 
			hpcatious = 500;
			hpBomber  = 150; 
			//RG power
			rgpower1 = 1.25f;
			rgpower2 = smpower = 2f;
			rgpower3 = 2.5f;
			// burst number
			rafalnbr1  =1; //weak normal
			rafalnbr2  =1; //strong big bomber
			//fire rate gun
			shootDWeak   = 4;
			shootDNormal = 2.2f;
			shootDStrong = 2f;
			shootDBig    = 8;
			shootDBomber = 2;
			//fire rate missiles
			shootSM   = 8;
			//laser
			lasershoottime= 0.05f;
			laserRLtime=1;
			lasertimer    = MathUtils.random(lasershoottime);
			laseraimtime  = 0;
			laserspeed  =0.25f;
			laserpower1    = 0.055f;
			laserpower2    = 0.075f;
			laserpower3    = 0.065f;
			laserprec     = 10;
			//shield
			shieldMaxhp = 100;
			shieldhp    = shieldMaxhp;
			//bombs
			bombDBomber  = 3.5f;
			bombepower = 3;
			metaltimer1=MathUtils.random(18, 25);
		}
		else if (Save.gd.playerGo==CatGame.NORIS3){ /////////NORIS 3
			//weak not so weak so less fast
			hightaccel = 10;
			//special NORIS1
			hpBombe   = 1;
			hpWeak    = 8;
			rafalnbr1  =4; //weak normal
			lasershoottime= 0.065f;
			lasertimer=0;
			laserpower1    = 0.025f;
			laserpower3    = 0.085f;
			laserRLtime=1f;
			laseraimtime  = 0.065f;
			laserspeed=MathUtils.random(-0.7f, 0.7f);
			laserprec=10;
			rgpower1 = 1.25f;
			rgpower2 = 2f;
			rgpower3 = smpower = 2.5f;
			shieldMaxhp = 250;
			bombepower = 5;
			shieldhp    = shieldMaxhp;
			offsetX      = -2.5f;
			if(type==Type.weak) bombTimer=3000;
		}
		////////////////////////////  ASTEROIDS  /////////////////////
		if ( type == Type.asteroidsmall || type == Type.asteroidnormal || type == Type.asteroidbig) {
			maxspeedSmall = Math.min(maxspeedSmall *=(Save.gd.visitnbr/23+Save.gd.playerGo/18), 20);
			maxspeedNorm  = Math.min(maxspeedNorm  *=(Save.gd.visitnbr/23+Save.gd.playerGo/18), 11);
			maxspeedBig  = Math.min(maxspeedBig    *=(Save.gd.visitnbr/23+Save.gd.playerGo/18), 11);
		}
		
		//////////////////////////////  SHIP  //////////////////////////////////////////
		
		if (type == Type.bombes) {//=================BOMBE===================
			size         = sizeSmall*0.9f;           //size
			rect.width   = width * size;
			rect.height  = height * size;
			maxVelocityY = bombeVelocityY;           //velocityY
			hp           = hpBombe;                  //HP
			if(shieldtype!=0){						 //shield
				shield.width =width*size*1.2f;            
				shield.height=height*size*1.2f; 
			}
			if(Save.gd.playerGo==CatGame.NORIS3){
				offsetX      = MathUtils.random(-1, 1.5f);
				maxVelocityX=MathUtils.random(-5.7f, -4.3f);
				laserpower=laserpower3;
			}

		} 
		else if (type == Type.weak){//=====================WEAK===================
			size         = sizeWeak;                 //size
			rect.width   = width*size;
			rect.height  = height*size*0.8f;
			maxVelocityY = weakVelocityY;            //velocityY
			hp           = hpWeak;                   //HP
			shootDelay   = shootDWeak;				 //fire speed
			rgpower = rgpower1;
			rafalnbr     =rafalnbr1;
			acceleration = hightaccel;				 //specific acceleration
			if(shieldtype!=0){						 //shield
				shield.width =width*size*1.2f;            
				shield.height=height*size*1.2f; 
			}
			if(Save.gd.playerGo==CatGame.NORIS3){ ///////boss
				rgpower = rgpower1;
				smpower = rgpower2;
				shootDelaySM = 7;
			}
		}
		else if (type == Type.normal) {//=================NORMAL===================
			size         = 1;						 //size
			rect.width   = width * size;
			rect.height  = height * size * 0.5f;
			maxVelocityY = normalVelocityY;          //velocityY
			hp           = hpNormal;				 //HP
			shootDelay   = shootDNormal;			 //fire speed
			rgpower = rgpower2;
			rafalnbr     =rafalnbr1;
			laserpower=laserpower1;
			laserRLtime=0.5f;
			if(shieldtype!=0){						 //shield
				shield.width =width*size*1.2f;            
				shield.height=height*size*1.2f; 
			}
			metalTimer =metaltimer1;
		} 
		else if (type == Type.strong) {//==================STRONG==================
			size         = sizeStrong;				 //size
			rect.width   = width*size;				 
			rect.height  = height*size*0.5f;
			offsetX      = MathUtils.random(-4, 1);
			maxVelocityY = strongVelocityY;			 //velocityY
			hp           = hpStrong;				 //HP
			shootDelay   = shootDStrong;			 //fire speed
			rgpower      = rgpower3;
			rafalnbr     =rafalnbr2;
			//shootTimerSM = shootSM;			     //other weapon
			laserpower=laserpower2;
			if(shieldtype!=0){						 //shield
				shield.width =width*size*1.2f;            
				shield.height=height*size*1.2f; 
			}
			if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro){
				hp = hpkuro;   
				rgpower =1;
				rafalnbr =3; 
				shootDelay = 0.85f; 
				shieldhp=5;
				metalTimer =50;
			}                          //boss1
			metalTimer =metaltimer1*2;
		} 
		else if (type == Type.big) {//=================BIGCAT===================
			size         = sizeBig;				     //size
			rect.width   = width * size* 0.5f;
			rect.height  = height * size;
			maxVelocityY = bigcatVelocityY;          //velocityY
			knockbackX   = bigKnockbackX;            //knock back
			knockbackY   = bigKnockbackY;
			offsetX=0;                               //specific offset
			hp           = hpBigCat;				 //HP
			shootDelay   = shootDBig;			     //fire speed
			rgpower      = rgpower1;
			rafalnbr     =rafalnbr2;
			shootDelaySM = shootSM;			         //other weapon
			metalTimer =metaltimer1*3;
			if(Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatcatious){
				hp = hpcatious;   
				rgpower   =1.25f; 
				rafalnbr     =1; 
				shootDelay   =4f;
				bombepower   =2f; 
				shootDelaySM =7f;
				metalTimer =100;	
			}         //boss2
		} 
		else if (type == Type.bomber) {//=====================BOMBER===================
			size         = sizeBomber;                  //size
			rect.width   = width * size;
			rect.height  = height * size * 0.8f;
			maxVelocityY = bomberVelocityY;          //velocityY
			hp           = hpBomber;                 //HP
			shootDelay   = shootDBomber;             //fire speed
			rgpower = rgpower3;
			knockbackX   = bigKnockbackX;            //knock back
			knockbackY   = bigKnockbackY;
			offsetX=0;                               //specific offset
			if(shieldtype!=0){						 //shield
				shield.width =width*size*1.2f;            
				shield.height=height*size*1.2f; 
			}
			laserpower=laserpower3;
			rafalnbr     =rafalnbr2;
			metalTimer =metaltimer1*4f; ////?????????????
			if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn){
				hp = 200;   
				bombepower   =10f; 
				rgpower =rgpower3;
				rafalnbr =2; 
				smpower =rgpower3;
				shootDelay = 4f; 
				metalTimer =100;
			}   
			if(Save.gd.playerGo==CatGame.NORIS1){
				hp = 270;   
				bombepower   =7f; 
				rgpower =rgpower3;
				rafalnbr =2; 
				smpower =rgpower3;
				shootDelay = 4f; 
				metalTimer =150;
			}
		} 
		else if (type == Type.ultima) {//=====================ULTIMA===================
			rect.width   = ultih/4.5f;
			rect.height  = ultih/4.5f;
			maxVelocityY = bigcatVelocityY;          //velocityY
			shootDelay   = shootDBomber;             //fire speed
			rgpower = rgpower3;
			knockbackX   = bigKnockbackX;            //knock back
			knockbackY   = bigKnockbackY;
			offsetX=-3;                               //specific offset
			prot1= new Rectangle();
			prot1.width   = 0.35f*ultiw;
			prot1.height  = 0.12f*ultih;
			prot2= new Rectangle();
			prot2.width   = 0.35f*ultiw;
			prot2.height  = 0.12f*ultih;
			gen1= new Rectangle();
			gen1.width   = 0.1f*ultih;
			gen1.height  = 0.1f*ultih;
			gen2= new Rectangle();
			gen2.width   = 0.1f*ultih;
			gen2.height  = 0.1f*ultih;
			shieldtype=3;	
			bombDBomber=6;
			shield.width =rect.width*1.4f;            //shield
			shield.height=rect.width*1.4f; 
			hp           = 20;                 //HP
			prot1hp=prot2hp=100;
			gen1hp=gen2hp=50;
			laserpower=laserpower3;
			laserRLtime=0.35f;
			rafalnbr     =rafalnbr2;
			shootDelaySM = 7;
			smpower =rgpower3;
			metalTimer =metaltimer1*4f; ////?????????????
			metalTimer =300;
		} 
		////////////////////////////////////////COSMIC RAYS////////////////////////////////
		else if (type == Type.cosmicray) {//==================STRONG==================
			size         = 0.001f;				 //size
			rect.width   = width*size;				 
			rect.height  = height*size;
			hp           = 10000000;		     //HP
			laserpower=laserpower1;
			laserprec     = 35;
		} 
		/////////////////////////////////////////////ASTEROIDS/////////////////////////////////////////////////
		else if (type == Type.asteroidnormal) {
			size = 1;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpNormA;
			knockbackX = 3;
			knockbackY = 3;
		} else if (type == Type.asteroidbig) {
			size = sizeBomber;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpBig;
			knockbackX = 1;
			knockbackY = 1;
		}else if (type == Type.asteroidsmall) {
			size = sizeSmall;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpSmall;
			knockbackX = 0;
			knockbackY = 0;
		}
		/////////////////////  BONUSES //////////////////////////
		else if (type == Type.probes) {
			exploded=true;
			colected=false;
			maxVelocityX = Player.ScrollVelocityX;
			maxVelocityY =  0;
			size = sizeSmall;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = 10;
			knockbackX = 5;
			knockbackY = 5;
		}
		else if (type == Type.metal) {
			exploded=true;
			colected=false;
			maxVelocityX = Player.ScrollVelocityX;
			maxVelocityY =  0;
			size = sizeSmall;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = 2;
			knockbackX = 3;
			knockbackY = 3;
		}
	}

	void update (float delta) {
		if(metalTimer>0) metalTimer-=delta;
		if(type==Type.cosmicray){
			laserstartx = position.x;
			laserstarty = position.y;
			view.shootLaser();
			return;
		}
		stateChanged = false;
		shootTimer -= delta;
		shootTimerSM -= delta;
		
		////////boss ultima///////////////////////
		if(type==Type.ultima){
			updateult(delta);
		}
		////////UPGRADE BOSS NORIS3////////////////////////
		if(type==Type.weak && Save.gd.playerGo==CatGame.NORIS3){
			updateN3(delta);
		}
		
		if(shieldtype!=0){
			shield.x=position.x+rect.width/2f-shield.width/2;
			shield.y=position.y+rect.height/2-shield.height/2;
		}
		if (state == State.death) {
			if (type == Type.bomber) {
				spawnMetalTimer = 0.4f;
				type = Type.bombes;
				rect.height=1;
				rect.width =1;
			}
			if (type == Type.asteroidbig) {
				spawnNATimer = 0.4f;
				spawnSATimer = 0.4f;
				metalchance=MathUtils.random(15,30);
				type = Type.asteroidsmall;
			}
			if (type == Type.asteroidnormal) {
				spawnSATimer = 0.4f;
				metalchance=MathUtils.random(10,15);
				type = Type.asteroidsmall;
			}
			if (type == Type.normal) {
				spawnMetalTimer = 0.4f;
				type = Type.weak;
			}
			if (type == Type.strong) {
				spawnMetalTimer = 0.4f;
				type = Type.weak;
			}
			if (type == Type.big) {
				spawnMetalTimer = 0.4f;
				type = Type.bombes;
				rect.height=1;
				rect.width =1;
			}
			if (type == Type.ultima) {
				spawnMetalTimer = 0.4f;
				type = Type.bombes;
				rect.height=1;
				rect.width =1;
			}
		}
		
		
		//Bomber bomb
		if (type == Type.bomber) {
			bombTimer-=delta;
			if(bombTimer<0){
				bombTimer=MathUtils.random(bombDBomber, 2*bombDBomber);
				if((Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn)||
						Save.gd.playerGo==CatGame.NORIS1){
					int bombechance = MathUtils.random(2);
					if(bombechance==0){
						Enemy small = new Enemy(model, Type.bombes,1);
						small.position.set(position.x+width*sizeBomber/3, position.y+height*3*sizeBomber/4);
						small.velocity.x = MathUtils.random(-0, -0);
						small.velocity.y = MathUtils.random(-10, 10);
						Enemy small1 = new Enemy(model, Type.bombes,2);
						small1.position.set(position.x+width*sizeBomber/3, position.y+height*sizeBomber/4);
						small1.velocity.x = MathUtils.random(-0, -0);
						small1.velocity.y = MathUtils.random(-10, 10);
						model.enemies.add(small);
						model.enemies.add(small1);
					}
					if(bombechance==1){
						Enemy small = new Enemy(model, Type.bombes,1);
						small.position.set(position.x+width*sizeBomber/3, position.y+height*3*sizeBomber/4);
						small.velocity.x = MathUtils.random(-0, -0);
						small.velocity.y = MathUtils.random(-10, 10);
						Enemy small1 = new Enemy(model, Type.bombes,1);
						small1.position.set(position.x+width*sizeBomber/3, position.y+height*sizeBomber/4);
						small1.velocity.x = MathUtils.random(-0, -0);
						small1.velocity.y = MathUtils.random(-10, 10);
						model.enemies.add(small);
						model.enemies.add(small1);
					}
					if(bombechance==2){
						Enemy small = new Enemy(model, Type.bombes,2);
						small.position.set(position.x+width*sizeBomber/3, position.y+height*3*sizeBomber/4);
						small.velocity.x = MathUtils.random(-0, -0);
						small.velocity.y = MathUtils.random(-10, 10);
						Enemy small1 = new Enemy(model, Type.bombes,2);
						small1.position.set(position.x+width*sizeBomber/3, position.y+height*sizeBomber/4);
						small1.velocity.x = MathUtils.random(-0, -0);
						small1.velocity.y = MathUtils.random(-10, 10);
						model.enemies.add(small);
						model.enemies.add(small1);
					}
				}
				else {
					Enemy small = new Enemy(model, Type.bombes,0);
					small.position.set(position.x+width*sizeBomber/3, position.y+height*3*sizeBomber/4);
					small.velocity.x = MathUtils.random(-0, -0);
					small.velocity.y = MathUtils.random(-10, 10);
					Enemy small1 = new Enemy(model, Type.bombes,0);
					small1.position.set(position.x+width*sizeBomber/3, position.y+height*sizeBomber/4);
					small1.velocity.x = MathUtils.random(-0, -0);
					small1.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
					model.enemies.add(small1);
				}
			}
		}
		if (type == Type.big) {
			bombTimer-=delta;
			if(bombTimer<0){
				bombTimer=MathUtils.random(bombDBomber, 2*bombDBomber);
				int bombechance = MathUtils.random(2);
				if(bombechance==0){
					Enemy small = new Enemy(model, Type.bombes,0);
					small.position.set(position.x, position.y+height*sizeBig*0.1f);
					small.velocity.x = MathUtils.random(-0, -0);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
				}
				if(bombechance==1){
					Enemy small1 = new Enemy(model, Type.bombes,0);
					small1.position.set(position.x, position.y+height*sizeBig*0.9f);
					small1.velocity.x = MathUtils.random(-0, -0);
					small1.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small1);
				}
				if(bombechance==2){
					Enemy small = new Enemy(model, Type.bombes,0);
					small.position.set(position.x, position.y+height*sizeBig*0.15f);
					small.velocity.x = MathUtils.random(-0, -0);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
					Enemy small1 = new Enemy(model, Type.bombes,0);
					small1.position.set(position.x, position.y+height*sizeBig*0.85f);
					small1.velocity.x = MathUtils.random(-0, -0);
					small1.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small1);
				}
				
			}
		}
		
		// Big enemy explodes into small ones.
		if (spawnSmallsTimer > 0) {
			spawnSmallsTimer -= delta;
			if (spawnSmallsTimer < 0) {
				for (int i = 0; i < smallCount; i++) {
					Enemy small = new Enemy(model, Type.bombes,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
				}
			}
		}
		
		//big asteroids explode
		if (spawnNATimer > 0) {
			spawnNATimer -= delta;
			if (spawnNATimer < 0) {
				for (int i = 0; i < NACount; i++) {
					Enemy small = new Enemy(model, Type.asteroidnormal,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
				}
				for (int i = 0; i < metalchance; i++) {
					Enemy small = new Enemy(model, Type.metal,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-6, 6);
					small.velocity.y = MathUtils.random(-3, 3);
					model.enemies.add(small);
				}
			}
		}
		
		if (spawnSATimer > 0) {
			spawnSATimer -= delta;
			if (spawnSATimer < 0) {
				for (int i = 0; i < smallCount; i++) {
					Enemy small = new Enemy(model, Type.asteroidsmall,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 0);
					model.enemies.add(small);
				}
				for (int i = 0; i < metalchance; i++) {
					Enemy small = new Enemy(model, Type.metal,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-6, 6);
					small.velocity.y = MathUtils.random(-3, 3);
					model.enemies.add(small);
				}
			}
		}
		if (spawnMetalTimer > 0) {
			spawnMetalTimer -= delta;
			if (spawnMetalTimer < 0) {
				for (int i = 0; i < (int)(metalTimer/2); i++) {
					Enemy small = new Enemy(model, Type.metal,0);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-6, 6);
					small.velocity.y = MathUtils.random(-3, 3);
					model.enemies.add(small);
				}
			}
		}
		
		
		// Kill enemies stuck in the map or those that have somehow fallen out of the map.
		if (state != State.death && (hp <= 0 || position.y < -100 || collisions > 100)) {
			state = State.death;
			hp = 0;
		}
		
		
		// Simple enemy AI.
		if (type == Type.weak || type == Type.normal || type == Type.strong || type == Type.bomber || type == Type.big) {
			//shoot every one!!
			view.shoot();
			//shoot SM (mainly in view when it has a chance)
			if(type==Type.big) view.shootSM();
			//shoot LZ (it's complicated)
			if(maxVelocityX>-Player.ScrollVelocityX-1f && maxVelocityX<-Player.ScrollVelocityX+1f 
					&& position.y>-worldheight && position.y+rect.height<worldheight ){ //shoot laser at fire range
				if((type==Type.normal && Save.gd.eventdefeatn && Save.gd.playerGo!=CatGame.ANOMALY ) ||
				   (type==Type.normal && Save.gd.playerGo==CatGame.ANOMALY && model.player.position.x<View.t1)) //normal after bossN
						view.shootLaser();
				if((type==Type.strong && Save.gd.eventendcat && Save.gd.playerGo!=CatGame.ANOMALY ) ||
				   (type==Type.strong && Save.gd.playerGo==CatGame.ANOMALY && model.player.position.x<View.t1)) //strong D
						view.shootLaser();
				if(type==Type.bomber&& Save.gd.eventdefeatn)
						view.shootLaser();
			}
		}
		if (Save.gd.playerGo==CatGame.NORIS3 && type == Type.bombes && bomblaser){
			view.shootLaser();
		}
		collisionTimer -= delta;
		//enemy ship
		if (type == Type.weak || type == Type.normal || type == Type.strong || type == Type.bomber || type == Type.big ||type== Type.bombes){
			if (state == State.death){
				deathTimer -= delta;
				velocity.x=0;
				velocity.y=0;
			}
			else if (collisionTimer < 0) {
					// Move toward the player.
				if(type != Type.bombes && model.player.collisionTimer<0 ){ //stop when at fire distance  //exept for bombes 
					maxVelocityX=Player.ScrollVelocityX/(20+offsetX)*(position.x-model.player.position.x)-2*Player.ScrollVelocityX;
				}
				moveLeft(delta);
				if(shootinglz && Save.gd.playerGo<CatGame.NORIS2){
					velocity.y*=0.8f;
				}
				else{
					if(position.y> worldheight-rect.height-2)
						velocity.y = Math.max(velocity.y - 3 * acceleration * delta, -maxVelocityY);
					if(position.y<-worldheight+1)
						velocity.y = Math.min(velocity.y + 3 * acceleration * delta, maxVelocityY);
					else if (model.player.position.y+ model.player.rect.height/2> 
						position.y+rect.height/2+MathUtils.random(-30/(maxVelocityY+acceleration), 30/(maxVelocityY+acceleration)) ) 
						 moveup(delta);
					else movedown(delta);
				}
			}
			else{
				velocity.x*=0.98f;
				velocity.y*=0.98f;
			}
			super.update(delta, false);
		}
		
		// Compute the position and velocity to spawn a new bullet.

		if(type==Type.strong){
			laserstartx = position.x+rect.width*0.2f;
			laserstarty = position.y+rect.height*0.3f;
		}
		if(type==Type.bomber){
			laserstartx = position.x+rect.width*2/3f;
			laserstarty = position.y+rect.height*0.9f/2f;
		}
		else if(type==Type.normal){
			laserstartx = position.x+0.1f;//+width+0.14f;
			laserstarty = position.y+rect.height/3;
		}
		if(Save.gd.playerGo==CatGame.NORIS3 && type==Type.bombes){
			laserstartx = position.x+rect.width/2;
			laserstarty = position.y+rect.height/2;
		}
		if(type==Type.ultima){
			laserstartx = position.x+rect.width/2-ultiw*(0.1944f);
			laserstarty = position.y+rect.height/2;
		}
		//asteroids
		else if( type == Type.asteroidsmall || type == Type.asteroidnormal || type == Type.asteroidbig){
			//moveLeft(delta);
			if(velocity.y==0 && velocity.x==0 && state!=State.death){
				if(type==Type.asteroidsmall){
					velocity.x=MathUtils.random(-maxspeedSmall,maxspeedSmall);
					velocity.y=MathUtils.random(-maxspeedSmall/2,maxspeedSmall/2);
					
				}
				if(type==Type.asteroidnormal){
					velocity.x=MathUtils.random(-maxspeedNorm,maxspeedNorm);
					velocity.y=MathUtils.random(-maxspeedNorm/2,maxspeedNorm/2);
				}
				if(type==Type.asteroidbig){
					velocity.x=MathUtils.random(-maxspeedBig,maxspeedBig);
					velocity.y=MathUtils.random(-maxspeedBig/2,maxspeedBig/2);
				}
			}
			if (state == State.death){
				deathTimer -= delta;
				velocity.x=0;
				velocity.y=0;
			}
			super.update(delta, false);
		}
		//probes
		else if( type == Type.probes){
			if (state == State.death)
				deathTimer -= delta;
			super.update(delta, true);
		}
		else if( type == Type.metal){
			if(velocity.y==0 && velocity.x==0 && state!=State.death){
				velocity.x=MathUtils.random(-4,3);
				velocity.y=MathUtils.random(-0.7f,0.7f);
			}
			if (state == State.death)
				deathTimer -= delta;
			super.update(delta, false);
		}
		

		int previousCollision = collisions;
		if (collisions == previousCollision) collisions = 0;
		
		
	}

	private void updateN3(float delta) {
		bombTimer-=delta;
		if(shieldhp<=shieldMaxhp*5/6 && shieldhp>shieldMaxhp*4/6){
			rgpower=rgpower2;
			smpower = rgpower2;
			shootDelaySM = 7;
			view.shootSM();
		}
		if(shieldhp<=shieldMaxhp*4/6){
			rgpower=rgpower3;
			smpower = rgpower3;
			shootDelaySM = 3;
			if(shieldhp>shieldMaxhp*3/6)view.shootSM();
			
		}
		if(shieldhp<=shieldMaxhp*3/6 && !bhed){
			bhtimer=10;
			bhed=true;
		}
		if(shieldhp<=shieldMaxhp*1/6){
			bomblaser=true;
		}
		if(bhtimer>=0)bhtimer-=delta;
		if(bombTimer<0){
			if (shieldhp<=shieldMaxhp*1/6) {
				bombTimer=MathUtils.random(6, 7);
				for (int i = 0; i < 4; i++) {
					Enemy small = new Enemy(model, Type.bombes, MathUtils.random(1,2));
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-4, 4);
					small.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
				}
				for (int i = 0; i < 7; i++) {
					Enemy small = new Enemy(model, Type.asteroidsmall,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 0);
					model.enemies.add(small);
				}
				
			}
			else if (shieldhp<=shieldMaxhp*2/6) {
				bombTimer=MathUtils.random(4, 6);
				for (int i = 0; i < 6; i++) {
					Enemy small = new Enemy(model, Type.bombes,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
				}
				for (int i = 0; i < 3; i++) {
					Enemy small = new Enemy(model, Type.asteroidnormal,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
				}
				for (int i = 0; i < 6; i++) {
					Enemy small = new Enemy(model, Type.asteroidsmall,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 0);
					model.enemies.add(small);
				}
			}
			else if (shieldhp<=shieldMaxhp*3/6) {
				bombTimer=MathUtils.random(5, 6);
				for (int i = 0; i < 2; i++) {
					Enemy small = new Enemy(model, Type.asteroidnormal,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-5, 5);
					model.enemies.add(small);
				}
				for (int i = 0; i < 5; i++) {
					Enemy small = new Enemy(model, Type.asteroidsmall,0);
					small.position.set(position.x, 0);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 0);
					model.enemies.add(small);
				}
			}
		}
		
	}

	private void updateult(float delta) {
		prot1.x=position.x+rect.width/2-ultiw*0.41f+ultiw*0.816f-prot1.getWidth()/2;
		prot1.y=position.y+rect.height/2+ultih*0.148f-prot1.getHeight()/2;
		prot2.x=position.x+rect.width/2-ultiw*0.41f+ultiw*0.816f-prot2.getWidth()/2;
		prot2.y=position.y+rect.height/2-ultih*0.148f-prot2.getHeight()/2;
		gen1.x=position.x+rect.width/2-ultiw*0.41f+ultiw*0.773f-gen1.getWidth()/2;
		gen1.y=position.y+rect.height/2+ultih*0.148f-gen1.getHeight()/2;
		gen2.x=position.x+rect.width/2-ultiw*0.41f+ultiw*0.773f-gen2.getWidth()/2;
		gen2.y=position.y+rect.height/2-ultih*0.148f-gen2.getHeight()/2;
		lzstarty2= position.y+rect.height/2+0.253f*ultih;
		lzstarty3= position.y+rect.height/2-0.257f*ultih;
		//move
		if (state == State.death){
			deathTimer -= delta;
			velocity.x=0;
			velocity.y=0;
		}
		else if (collisionTimer < 0) {
				// Move toward the player.
			if(model.player.collisionTimer<0)
				maxVelocityX=Player.ScrollVelocityX/(20+offsetX)*(position.x-model.player.position.x)-2*Player.ScrollVelocityX;
			moveLeft(delta);
			if(position.y+rect.height/2f<-moverange) {
				moveup=true;
				moverange=MathUtils.random(-1, 3);
			}
			if(position.y+rect.height/2f> moverange) {
				moveup=false;
				moverange=MathUtils.random(-1, 3);
			}
			if(moveup)moveup(delta);
			else      movedown(delta);
		}
		else{
			velocity.x*=0.98f;
			velocity.y*=0.98f;
		}
		super.update(delta, false);
		
		//upgrade
		if(prot1hp>0 && prot2hp>0){
			rgpower=rgpower1;
			smpower=rgpower1;
			laserpower=laserpower1;
			smNbr=9;
			smSpeed=1.5f;
			
		}
		else if (shieldhp>shieldMaxhp/2){
			if(destructiontimer2==10 ||destructiontimer3==10) spawnSmallsTimer=0.2f;
			rgpower=rgpower2;
			smpower=rgpower2;
			laserpower=laserpower2;
			smNbr=11;
		}
		else{
			maxVelocityY=2*bigcatVelocityY;
			rgpower=rgpower3;
			smpower=rgpower3;
			laserpower=laserpower3;
			smNbr=13;
		}
		//shoot
		view.shootSM();
		if(maxVelocityX>-Player.ScrollVelocityX-1f && maxVelocityX<-Player.ScrollVelocityX+1f ){
			view.shootLaser();
			////laser 2
			if(lzt2>0 && canShootlz2){
				lzt2-=0.055f*2/300;
				if(randomlz==1 ||randomlz==3)
					shootinglz3=true;
				else if(randomlz==2 ||randomlz==4)
					shootinglz3=true;
				else {
					shootinglz2=true;
					shootinglz3=true;
				}
			}
			else if(shootinglz2 || shootinglz3){
				lzt2=0;
				shootinglz2=false;
				shootinglz3=false;
				randomlz= MathUtils.random(4);
				canShootlz2=false;
			}
			if (!shootinglz2 && !shootinglz3 && lzt2<0.055f) {
				if(prot1hp>0 && prot2hp>0) lzt2+=0.055f*2/300*0.25f;
				else if (prot1hp<=0 && prot2hp<=0 && shieldhp>shieldMaxhp/2) lzt2+=0.055f*2/300*0.5f;
				else  lzt2+=0.055f*2/300*0.75f;
			} 
			else if(lzt2>=0.055f){
				canShootlz2=true;
			}
		}
		
		
		bombTimer-=delta;
		if(bombTimer<0){
			bombTimer=MathUtils.random(bombDBomber*3/4f,bombDBomber*6/4f);
			int bombechance = MathUtils.random(2);
			if(bombechance==0){
				Enemy small = new Enemy(model, Type.bombes,1);
				small.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2+ultih*0.332f);
				small.velocity.x = MathUtils.random(-0, -0);
				small.velocity.y = MathUtils.random(-10, 10);
				Enemy small1 = new Enemy(model, Type.bombes,2);
				small1.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2-ultih*0.332f);
				small1.velocity.x = MathUtils.random(-0, -0);
				small1.velocity.y = MathUtils.random(-10, 10);
				model.enemies.add(small);
				model.enemies.add(small1);
			}
			if(bombechance==1){
				Enemy small = new Enemy(model, Type.bombes,1);
				small.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2+ultih*0.332f);
				small.velocity.x = MathUtils.random(-0, -0);
				small.velocity.y = MathUtils.random(-10, 10);
				Enemy small1 = new Enemy(model, Type.bombes,1);
				small1.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2-ultih*0.332f);
				small1.velocity.x = MathUtils.random(-0, -0);
				small1.velocity.y = MathUtils.random(-10, 10);
				model.enemies.add(small);
				model.enemies.add(small1);
			}
			if(bombechance==2){
				Enemy small = new Enemy(model, Type.bombes,2);
				small.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2+ultih*0.332f);
				small.velocity.x = MathUtils.random(-0, -0);
				small.velocity.y = MathUtils.random(-10, 10);
				Enemy small1 = new Enemy(model, Type.bombes,2);
				small.position.set(position.x+rect.width/2-ultiw*0.41f+ultiw*0.633f, position.y+rect.height/2-ultih*0.332f);
				small1.velocity.x = MathUtils.random(-0, -0);
				small1.velocity.y = MathUtils.random(-10, 10);
				model.enemies.add(small);
				model.enemies.add(small1);
			}
			
		}
		
	}

	public enum Type {
		asteroidbig,asteroidnormal,asteroidsmall,
		weak, normal, strong, big, bomber, ultima,
	    laserdot,
		bombes, probes, metal,
		cosmicray
	}
}
