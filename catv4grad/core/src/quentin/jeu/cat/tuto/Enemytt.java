package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.catinvader.Model.*;
import quentin.jeu.cat.tuto.Modeltt.State;

import com.badlogic.gdx.math.MathUtils;

/** The model class for an enemy. */
public class Enemytt extends Charactertt {
	
	//ENEMY
	static float width = 160 * scale, height = 160 * scale;
	static float maxOffsetX = -10f, minOfsetX = 1f;
	static float maxVelocityMinX = -10f, maxVelocityMaxX = 1f;
	static float enemymaxVelocityY = 8f;
	static float hightaccel = 50, normalaccel = 10, lowaccel = 8;
	static float weakVelocityY = 20, bombeVelocityY = 1, normalVelocityY = 8, strongVelocityY = 10, becomesBigVelocityY = 8, bomberVelocityY = 6;
	static float hpWeak = 1, hpBombe = 1, hpNormal = 30, hpStrong = 40, hpBecomesBig = 8, hpBomber = 20;
	static float shootDWeak = 5, shootDNormal = 2f, shootDStrong = 0.9f, shootDBecomesBig = 8, shootDBomber = 2, bombDBomber = 2;
	static float corpseTime = .5f, fadeTime = 3;
	static float sizeWeak = 0.7f,sizeSmall = 0.5f, sizeBig = 2.5f, sizeStrong = 1.3f, bigDuration = 2, smallCount = 6;
	
	//graphic offset shoot
	static float shootOffsetX =50, shootOffsetY = 20;
	
	//weapons characteristics
	static float weaponpower = 100;
	static float bulletSpeed = 10, bulletInheritVelocity = 0.4f, burstDuration = 0.18f;
	static float maxShots2heat = 100000, heatperShot = 1, minprecisionheated = 1000;
	static float normalKnockbackX = 6+5, normalKnockbackY = 0, bigKnockbackX = 6+5, bigKnockbackY = 0;
	static float collisionDelay = 0.3f;
	
	//Asteroids
	static float SACount = 8, NACount=4;
	static float maxspeeds=5, maxspeedn=3,maxspeedb=2;
	
	float shootDelay;
	float shootTimer;
	float bombTimer;
	float deathTimer = corpseTime;
	float offsetX;
	float collisionTimer;
	float jumpDelayTimer, jumpDistance, jumpDelay;
	Type type;
	float size = 1;
	float bigTimer;
	float spawnSmallsTimer;
	float spawnSATimer,spawnNATimer;
	boolean move;
	boolean exploded;
	boolean forceJump;
	int collisions;
	float knockbackX = normalKnockbackX, knockbackY = normalKnockbackY;

	// This is here for convenience, the model should never touch the view.
	EnemyViewtt view;
	public boolean colected;

	Enemytt (Modeltt model, Type type) {
		super(model);
		this.type = type;
		exploded=false;
		colected= true;
		acceleration=normalaccel;
		shootTimer=MathUtils.random(0, 3);
		offsetX = MathUtils.random(maxVelocityMinX, maxVelocityMaxX);
		maxVelocityY = enemymaxVelocityY;
		maxVelocityX = MathUtils.random(-2, 4);
		
		/*for each enemy define : 
		 * - size, velocity y, hp                                           (needed)
		 * - fire speed, knockback, offset, acceleration y                  (optional)
		 * 
		 *lvl dependant parameter :
		 * -hp, fire power, firespeed, bullet speed
		 */
		//aliens
		if (type == Type.bomber) {
			offsetX=0;
			maxVelocityY = bomberVelocityY;
			shootDelay=shootDBomber;
			size = sizeBig;
			rect.width = width * size;
			rect.height = height * size * 0.8f;
			hp = hpBomber;
			knockbackX = bigKnockbackX;
			knockbackY = bigKnockbackY;
		} else if (type == Type.bombes) {
			size = sizeSmall;
			rect.width = width * size;
			rect.height = height * size;
			hp = hpBombe;
		} else if (type == Type.weak){
			maxVelocityY = weakVelocityY;
			acceleration=hightaccel;
			shootDelay=shootDWeak;
			size = sizeWeak;
			rect.width = width * size;
			rect.height = height * size * 0.8f;
			hp = hpWeak;
		} else if (type == Type.strong) {
			maxVelocityY = strongVelocityY;
			shootDelay=shootDStrong;
			hp = hpStrong;
			size = sizeStrong;
			rect.width = width * size;
			rect.height = height * size * 0.5f;
			jumpDistance *= 1.4f;
		} else if (type == Type.normal) {
			shootDelay=shootDNormal;
			size = 1;
			rect.width = width * size;
			rect.height = height * size * 0.5f;
			hp = hpNormal;
		} 
		//asteroids
		else if (type == Type.normalaster) {
			maxVelocityY = MathUtils.random(-maxspeedn+Playertt.ScrollVelocityX, maxspeedn+Playertt.ScrollVelocityX);
			maxVelocityX = MathUtils.random(-2, 8);
			size = 1;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpNormal;
			knockbackX = 3;
			knockbackY = 3;
		} else if (type == Type.bigaster) {
			maxVelocityX = MathUtils.random(-maxspeedb+Playertt.ScrollVelocityX, maxspeedb+Playertt.ScrollVelocityX);
			maxVelocityY =  MathUtils.random(5);
			size = sizeBig;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpStrong;
			knockbackX = 1;
			knockbackY = 1;
		}else if (type == Type.smallaster) {
			maxVelocityX = MathUtils.random(-maxspeeds+Playertt.ScrollVelocityX, maxspeeds+Playertt.ScrollVelocityX);
			maxVelocityY =  MathUtils.random(5);
			size = sizeSmall;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpWeak;
			knockbackX = 5;
			knockbackY = 5;
		}
		//probes
		else if (type == Type.probes) {
			exploded=true;
			colected=false;
			maxVelocityX = Playertt.ScrollVelocityX;
			maxVelocityY =  0;
			size = sizeSmall;
			rect.width = width * size*2/3;
			rect.height = height * size*2/3;
			hp = hpBomber;
			knockbackX = 5;
			knockbackY = 5;
		}
		
		
		jumpDelayTimer = MathUtils.random(0, jumpDelay);
	}

	void update (float delta) {
		shootTimer -= delta;
		if (state == State.death) {
			/*if (type == Type.toBig && size == 1) {
				bigTimer = bigDuration;
				collisionTimer = bigDuration;
				state = State.run;
				hp = hpBig;
				knockbackX = bigKnockbackX;
				knockbackY = bigKnockbackY;
				type = Type.bomber;
				jumpVelocity = jumpVelocityBig;
			} else*/ 
			if (type == Type.bomber) {
				spawnSmallsTimer = 0.4f;
				type = Type.normal;
			}
			if (type == Type.bigaster) {
				spawnNATimer = 0.4f;
				spawnSATimer = 0.4f;
				type = Type.smallaster;
			}
			if (type == Type.normalaster) {
				spawnSATimer = 0.4f;
				type = Type.smallaster;
			}
			
		}
		
		// Enemy grows to a big enemy.
		if (bigTimer > 0) {
			bigTimer -= delta;
			size = 1 + (sizeBig - 1) * (1 - Math.max(0, bigTimer / bigDuration));
			rect.width = width * size * 0.7f;
			rect.height = height * size * 0.7f;
		}

		//Bomber bomb
		if (type == Type.bomber) {
			bombTimer-=delta;
			if(bombTimer<0){
				bombTimer=MathUtils.random(bombDBomber, 2*bombDBomber);
				Enemytt small = new Enemytt(model, Type.bombes);
				small.position.set(position.x+width*sizeBig/3, position.y+height*3*sizeBig/4);
				small.velocity.x = MathUtils.random(-0, -0);
				small.velocity.y = MathUtils.random(-10, 10);
				Enemytt small1 = new Enemytt(model, Type.bombes);
				small1.position.set(position.x+width*sizeBig/3, position.y+height*sizeBig/4);
				small1.velocity.x = MathUtils.random(-0, -0);
				small1.velocity.y = MathUtils.random(-10, 10);
				model.enemies.add(small);
				model.enemies.add(small1);
			}
		}
		// Big enemy explodes into small ones.
		if (spawnSmallsTimer > 0) {
			spawnSmallsTimer -= delta;
			if (spawnSmallsTimer < 0) {
				for (int i = 0; i < smallCount; i++) {
					Enemytt small = new Enemytt(model, Type.bombes);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
				}
			}
		}
		if (spawnNATimer > 0) {
			spawnNATimer -= delta;
			if (spawnNATimer < 0) {
				for (int i = 0; i < NACount; i++) {
					Enemytt small = new Enemytt(model, Type.normalaster);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
				}
			}
		}
		
		if (spawnSATimer > 0) {
			spawnSATimer -= delta;
			if (spawnSATimer < 0) {
				for (int i = 0; i < smallCount; i++) {
					Enemytt small = new Enemytt(model, Type.smallaster);
					small.position.set(position.x, position.y);
					small.velocity.x = MathUtils.random(-10, 10);
					small.velocity.y = MathUtils.random(-10, 10);
					model.enemies.add(small);
				}
			}
		}

		// Nearly dead enemies jump at the player right away.
		if (hp == 1 && type != Type.weak && type != Type.bombes) jumpDelayTimer = 0;

		// Kill enemies stuck in the map or those that have somehow fallen out of the map.
		if (state != State.death && (hp <= 0 || position.y < -100 || collisions > 100)) {
			state = State.death;
			hp = 0;
		}

		
		// Simple enemy AI.
		move = true;
		
		if (type != Type.bombes && type != Type.smallaster && type != Type.normalaster && type != Type.bigaster && type != Type.probes ) {
			view.shoot();
		}
		
		collisionTimer -= delta;
		//enemy ship
		if ( type != Type.smallaster && type != Type.normalaster && type != Type.bigaster && type != Type.probes ) {
			if (state == State.death)
				deathTimer -= delta;
			else if (collisionTimer < 0) {
					// Move toward the player.
				if(type != Type.bombes && position.x>model.player.position.x+20+ offsetX) maxVelocityX*=0.7f;
				else if(type != Type.bombes) maxVelocityX=-Playertt.ScrollVelocityX;
				moveLeft(delta);
				if (model.player.position.y > position.y+MathUtils.random(-acceleration/6, acceleration/3)) {
					 moveup(delta);
				} 
				else 
					movedown(delta);
			}
			super.update(delta, true);
		}
		//asteroids
		else if( type == Type.smallaster || type == Type.normalaster || type == Type.bigaster){
			//moveLeft(delta);
			if(velocity.y==0 && velocity.x==0){
				velocity.x=MathUtils.random(-10,10);
				velocity.y=MathUtils.random(-5,5);
			}
			if (state == State.death)
				deathTimer -= delta;
			
			super.update(delta, false);
		}
		//probes
		else if( type == Type.probes){
			if (state == State.death)
				deathTimer -= delta;
			super.update(delta, true);
		}

		int previousCollision = collisions;
		if (collisions == previousCollision) collisions = 0;
		
		
	}

	

	enum Type {
		weak, normal, strong, /*toBig,*/ bomber, bombes, bigaster,normalaster,smallaster, probes
	}
}
