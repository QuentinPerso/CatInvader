package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Model.*;

import com.badlogic.gdx.math.Rectangle;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Save;

/* player's model */
public class Player extends Character {
	//level speed
	static float ScrollVelocityX = 6f;
	
	//graphic offset shoot
	static float shootOffsetX =0.4f+0.3f;//weapon width+bullet width/2
	
	//ship characteristics 
    static float width = 90 * scale, height = 30 * scale; //1.4  0.47
    static float inertia = 0.95f;
    //shield
    public float shieldhp;
    
	//RG characteristics
    static float RGbulletSpeed   = 33;
	public float RGshootpsec, RGmaxShots2heat,RGweaponpower, RGminprecHeated, RGreloadTime;
	float shootRGTimer;
	
	//SM characteristics
    static float SMweaponpower = 1;
	public float SMshootpsec,SMnumber,SMbulletSpeed, SMmaxShots2heat, SMreloadTime;
	public float shootSMTimer;
	     
	//laser characteristics
	static float laserreloadTime = 0.9f, laserdecade=5/1000f;
	public float laserpower, laserminpower, laserReload;
	float laserendx, laserendy;
	public boolean shootlz;
	//Collision stuff
	static float knockbackX = 14, knockbackY = 5, collisionDelay = 2.5f, flashTime = 0.07f;
	public Rectangle shield = new Rectangle();

	//Timers
	float collisionTimer;
	float shieldviewTimer;
	public float shieldRLTimer;
	boolean exploded;

	// This is here for convenience, the model should never touch the view.
	public PlayerView view;

	

	public Player (Model model) {
		super(model);
		exploded=false;
		rect.width = width;   //1.4
		rect.height = height; //0.47
		shield.width =2f;     // width*1.43
		shield.height=1.8f;   // height*3.8
		//ship 
		hp           = Save.gd.hp;
		maxVelocityY = Save.gd.speed;
		acceleration = Save.gd.acceleration;
		maxVelocityX = ScrollVelocityX ;
		//shield
		shieldhp        = Save.gd.shieldhp;
		shieldRLTimer   = 20 - Save.gd.shieldreload;
		//RG
		RGshootpsec     = Save.gd.riceGspeed;
		RGmaxShots2heat = Save.gd.riceGprec*2/10+Save.gd.riceGpower*8/10;
		RGweaponpower   = Math.max(1, Save.gd.riceGpower/60);
		RGminprecHeated = 90-65*Save.gd.riceGprec/CatGame.maxRGprec;
		RGreloadTime    = 0.44f-0.06f*Save.gd.riceGspeed/CatGame.maxRGFR
							  -0.2f *Save.gd.riceGpower/CatGame.maxRGpower-0.11f*Save.gd.riceGprec/CatGame.maxRGprec;
		RGmaxShots2heat = RGmaxShots2heat/(RGmaxShots2heat/190f+1-1/19f);
		//SM
		SMshootpsec     = Save.gd.sushiGfr;    //min 0.5?
		SMnumber        = Save.gd.sushiGnbr*2/3+5;   //min 10?
		SMbulletSpeed   = Save.gd.sushiGspeed/1.3f; //min 10?
		SMmaxShots2heat = (float) Math.sqrt(Save.gd.sushiGnbr)*1.9f;
		SMreloadTime    = 2.2f-0.35f*Save.gd.sushiGfr/CatGame.maxSMFR;
		//Laser
		laserpower      = Save.gd.lasermax;    //0.2<1
		laserminpower   = Save.gd.lasermin;    //0<0.2
		laserReload     = Save.gd.laserreload; //1<3
		
	}

	void update (float delta) {
		//stay in world
		if(position.y> worldheight-height-1){
			velocity.y = Math.max(velocity.y - 2 * acceleration * delta, -maxVelocityY);
		}
		if(position.y<-worldheight+1){
			velocity.y = Math.min(velocity.y + 2 * acceleration * delta, maxVelocityY);
		}
		if(Save.gd.shieldOwn){
			if(shieldhp<Save.gd.shieldhp){
				shieldRLTimer-=delta;
				if(shieldRLTimer<0){
					shieldhp++;
					shieldRLTimer=35-Save.gd.shieldreload;
				}
			}
			shield.x=position.x+rect.width/1.6f -shield.width/2;
			shield.y=position.y+rect.height/2-shield.height/2;
		}
		if(Enemy.bhed){
			maxVelocityY = Save.gd.speed/1.5f;
			acceleration = Save.gd.acceleration/2;
		}
		stateChanged = false;
		shootRGTimer -= delta;
		shootSMTimer -= delta;
		moveRight(delta);
		collisionTimer -= delta;
		super.update(delta, true);
	}
}
