package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.catinvader.Model.*;
import quentin.jeu.cat.utils.Save;

/** The model class for the player. */
public class Playertt extends Charactertt {
	//level speed
	static float ScrollVelocityX = 6f;
	
	//graphic offset shoot
	static float shootOffsetX =100, shootOffsetY = 20;
	
	//ship    characteristics 
	static float width = 90 * scale, height = 30 * scale;
	public static float hpStart = 40;
	static float playerAcceleration=10f;
	static float playerMaxvely=5f;
	static float inertia = 0.95f;

	//weapons characteristics
	static float bulletSpeed = 33, bulletInheritVelocity = 0.4f, burstDuration = 0.18f;
	public static float heatperShot = 1;

	static float minprecisionheated = 70;

	static float knockbackX = 14, knockbackY = 5, collisionDelay = 2.5f, flashTime = 0.07f;
	static float headBounceX = 12, headBounceY = 20;

	float shootTimer;
	float collisionTimer;
	float hpTimer;
	boolean exploded;
	
	public static float shootpsec     = Save.gd.riceGspeed;
	public static float maxShots2heat = Save.gd.riceGprec;
	public static float weaponpower   = Save.gd.riceGpower;

	// This is here for convenience, the model should never touch the view.
	public PlayerViewtt view;

	public Playertt (Modeltt model) {
		super(model);
		shootpsec     = Save.gd.riceGspeed;
		maxShots2heat = Save.gd.riceGprec;
		weaponpower   = 1;
		exploded=false;
		rect.width = width;
		rect.height = height;
		hp = hpStart;
		acceleration=playerAcceleration;
		maxVelocityX = ScrollVelocityX ;
		maxVelocityY = playerMaxvely;
	}

	void update (float delta) {
		//stay in world
		if(position.y> worldheight-height-1){
			velocity.y = Math.max(velocity.y - 2 * acceleration * delta, -maxVelocityY);
			//position.y= worldheight-height-0.4f;
		}
		if(position.y<-worldheight+1){
			velocity.y = Math.min(velocity.y + 2 * acceleration * delta, maxVelocityY);
			//position.y=-worldheight+0.2f;
		}
		shootTimer -= delta;
		moveRight(delta);
		collisionTimer -= delta;
		//rect.height = height - collisionOffsetY;
		super.update(delta, true);
	}
}
