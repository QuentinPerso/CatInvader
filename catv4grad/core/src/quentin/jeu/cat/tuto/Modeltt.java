package quentin.jeu.cat.tuto;


import quentin.jeu.cat.tuto.Enemytt.Type;
import quentin.jeu.cat.tuto.Pnjtt.pnjType;
import quentin.jeu.cat.screens.Tuto;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/** The core of the game logic. The model manages all game information but knows nothing about the view, ie it knows nothing about
 * how this information might be drawn to the screen. This model-view separation is a clean way to organize the code. */
public class Modeltt {
	public  static float scale = 1 / 64f;
	private static float gameOverSlowdown = 3f;
	public  static float worldheight=8;

	public  Tuto controller;
	public  Playertt player;
	public  Pnjtt kuro, mship;
	public  float timeScale = 1;
	
	public  FloatArray playerbullets = new FloatArray();
	public  FloatArray enemybullets  = new FloatArray();
	private Array<Trigger> triggers  = new Array<Trigger>();
	public  Array<Enemytt> enemies   = new Array<Enemytt>();
	
	private float gameOverTimer; //slow down timer
	public  boolean win=false;
	public  int probeCollect=0;
	
	public Modeltt (Tuto tuto) {
		this.controller = tuto;
		restart(tuto.lvl);
	}

	public Modeltt() {
		restart(controller.lvl);
	}

	public void restart (int lvl) {
		player = new Playertt(this);
		player.position.set(0, 0);
		player.velocity.set(0, 0);
		kuro = new Pnjtt(this, pnjType.kuro);
		kuro.position.set(3, 3);
		kuro.velocity.set(0, 0);
		mship = new Pnjtt(this, pnjType.mship);
		mship.position.set(4, -7);
		mship.velocity.set(0, 0);
		playerbullets.clear();
		enemies.clear();
		gameOverTimer = 0;
		probeCollect=0;

		// Setup triggers to spawn enemies based on the x coordinate of the player.
		
		triggers.clear();
	/*	addTrigger(20 , Type.smallaster  ,  8);
		addTrigger(40 , Type.smallaster  ,  8);
		addTrigger(50 , Type.normalaster,  10);
		addTrigger(60 , Type.smallaster  ,  8);
		addTrigger(80 , Type.smallaster  ,  8);
		addTrigger(80 , Type.normalaster ,  7);
		addTrigger(100 , Type.smallaster ,  8);
		addTrigger(110, Type.smallaster  , 15);
		addTrigger(120 , Type.smallaster ,  8);
		addTrigger(140, Type.normalaster ,  7);
		addTrigger(140 , Type.smallaster ,  8);*/
		/*addTrigger(160 , Type.smallaster ,  8);
		addTrigger(170, Type.bigaster    ,  1);
		addTrigger(180 , Type.smallaster ,  8);
		addTrigger(200 , Type.smallaster ,  8);
		addTrigger(200, Type.normalaster ,  2);
		addTrigger(220 , Type.smallaster ,  8);
		addTrigger(230, Type.bigaster    ,  2);
		addTrigger(240 , Type.smallaster ,  8);
		addTrigger(260, Type.smallaster  ,  16);
		addTrigger(260 , Type.smallaster ,  8);
		addTrigger(280 , Type.smallaster ,  8);
		addTrigger(290, Type.normalaster ,  3);
		addTrigger(300 , Type.smallaster ,  8);
		addTrigger(320, Type.bigaster    ,  2);
		addTrigger(340 , Type.smallaster ,  8);
		addTrigger(320, Type.smallaster  ,  10);
		addTrigger(360 , Type.smallaster ,  8);
		addTrigger(380 , Type.smallaster ,  8);*/
		addTrigger(225 , Type.probes     ,  1);
		
		addTrigger(300 , Type.normalaster,  1);
		addTrigger(310 , Type.smallaster ,  8);
		addTrigger(330 , Type.smallaster ,  8);
		addTrigger(330 , Type.normalaster,  2);
		addTrigger(350 , Type.smallaster ,  8);
		addTrigger(360 , Type.normalaster,  2);
		addTrigger(370 , Type.smallaster ,  8);
		addTrigger(390 , Type.smallaster ,  16);
		addTrigger(400 , Type.smallaster ,  8);
		addTrigger(420 , Type.normalaster,  2);
		addTrigger(450 , Type.smallaster ,  8);
		addTrigger(470 , Type.smallaster ,  8);
		addTrigger(470 , Type.bigaster   ,  1);
		addTrigger(480 , Type.smallaster ,  16);
		addTrigger(500 , Type.smallaster ,  8);
		addTrigger(500 , Type.normalaster,  2);
		addTrigger(520 , Type.smallaster ,  8);
		addTrigger(540 , Type.smallaster ,  8);
		addTrigger(530 , Type.bigaster   ,  2);
		
		
	}

	void addTrigger (float triggerX, Type type, int count) {
		Trigger trigger = new Trigger();
		trigger.x = triggerX;
		float spawnX=triggerX+35;
		triggers.add(trigger);
		for (int i = 0; i < count; i++) {
			Enemytt enemy = new Enemytt(this, type);
			if(type!=Type.probes)enemy.position.set(spawnX+MathUtils.random(-7,7), 0+MathUtils.random(-9, 9));
			else                 enemy.position.set(spawnX, 0);
			trigger.enemies.add(enemy);
		}
	}

	public void update (float delta) {
		if (player.hp == 0) {
			gameOverTimer += delta / getTimeScale() * timeScale; // Isn't affected by player death time scaling.
			controller.eventGameOver(false);
		}
		updateEnemies(delta);
		updateEnemyBullets(delta);
		updatePlayerBullets(delta);
		player.update(delta);
		kuro.update(delta);
		mship.update(delta);
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
			Enemytt enemy = enemies.get(i);
			enemy.update(delta);
			if (enemy.deathTimer < 0) {
				enemies.removeIndex(i);
				continue;
			}
			
			
			//collision enemy 
			if (enemy.hp > 0 && player.hp > 0) {
				//bombes
				if (enemy.type==Type.bombes && enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
					if (player.collisionTimer < 0) {
						// Player gets hit.
						player.dir = enemy.position.x + enemy.rect.width / 2 < player.position.x + player.rect.width / 2 ? -1 : 1;
						float amount = Playertt.knockbackX * player.dir;
						player.velocity.x = -amount;
						player.velocity.y += Playertt.knockbackY;
						player.hp--;
						enemy.hp=0;
						if (player.hp > 0) {
							//player.setState(State.fall);
							player.collisionTimer = Playertt.collisionDelay;
						} else {
							player.state=State.death;
							player.velocity.y *= 0.5f;
						}
						enemy.collisionTimer = Enemytt.collisionDelay;
					}
				}
				//asteroids
				if ((enemy.type==Type.bigaster || enemy.type==Type.normalaster || enemy.type==Type.smallaster) 
						&& enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
					if (player.collisionTimer < 0) {
						// Player gets hit.
						player.dir = enemy.position.x + enemy.rect.width / 2 < player.position.x + player.rect.width / 2 ? -1 : 1;
						float amount = Playertt.knockbackX * player.dir;
						player.velocity.x = -amount;
						player.velocity.y += Playertt.knockbackY;
						//player.setGrounded(false);
						player.hp--;
						enemy.hp=0;
						if (player.hp > 0) {
							player.collisionTimer = Playertt.collisionDelay;
						} else {
							player.velocity.y *= 0.5f;
						}
						enemy.collisionTimer = Enemytt.collisionDelay;
					}
					
				}
				
				//collision probes
				if ((enemy.type==Type.probes) 
						&& enemy.collisionTimer < 0 && enemy.rect.overlaps(player.rect)) {
						enemy.hp=0;
						probeCollect+=1;
						enemy.velocity.x=Playertt.ScrollVelocityX;
						enemy.collisionTimer = Enemytt.collisionDelay;
				}
				
				// hit pnj
				if ((enemy.type==Type.bigaster || enemy.type==Type.normalaster || enemy.type==Type.smallaster) 
						&& enemy.collisionTimer < 0 && enemy.rect.overlaps(kuro.rect)) {
					if (kuro.collisionTimer < 0 && mship.exploded) {
						// kuro gets hit.
						kuro.dir = enemy.position.x + enemy.rect.width / 2 < kuro.position.x + kuro.rect.width / 2 ? -1 : 1;
						float amount = Playertt.knockbackX * kuro.dir;
						kuro.velocity.x = -amount;
						kuro.velocity.y += Playertt.knockbackY;
						kuro.hp--;
						enemy.hp=0;
						if (kuro.hp > 0) {
							kuro.collisionTimer = Playertt.collisionDelay;
						} else {
							kuro.state=State.death;
							kuro.velocity.y *= 0.5f;
						}
						enemy.collisionTimer = Enemytt.collisionDelay;
					}
				}
				if ((enemy.type==Type.bigaster || enemy.type==Type.normalaster || enemy.type==Type.smallaster) 
						&& enemy.collisionTimer < 0 && enemy.rect.overlaps(mship.rect)) {
					if (mship.collisionTimer < 0) {
						// mship gets hit.
						mship.hp--;
						enemy.hp=0;
						if (mship.hp > 0) {
							mship.collisionTimer = Playertt.collisionDelay;
						} else {
							mship.state=State.death;
							mship.velocity.y *= 0.5f;
						}
						enemy.collisionTimer = Enemytt.collisionDelay;
					}
				}
				if(player.position.x>510) kuro.state=State.death;
				//remove enemies
				if(enemy.position.x<player.position.x-10) enemies.removeIndex(i);
				if(enemy.position.x>player.position.x+16*16/9 && enemy.velocity.x>Playertt.ScrollVelocityX-5) enemies.removeIndex(i);
				if(enemy.position.y<-12) {
					if (enemy.type==Type.bigaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpStrong)enemies.removeIndex(i);
					if (enemy.type==Type.normalaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpNormal)enemies.removeIndex(i);
					if (enemy.type==Type.smallaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpWeak)enemies.removeIndex(i);
					
					enemy.position.y=10;
					enemy.position.x+=10;
					}
				if(enemy.position.y>12) {
					if (enemy.type==Type.bigaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpStrong)enemies.removeIndex(i);
					if (enemy.type==Type.normalaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpNormal)enemies.removeIndex(i);
					if (enemy.type==Type.smallaster) 
						if(enemy.velocity.x>Playertt.ScrollVelocityX-2 || enemy.hp!=Enemytt.hpWeak)enemies.removeIndex(i);
					enemy.position.y=-11;
					enemy.position.x+=10;
					}
			}
		}
		
		// End the game when all enemies are dead and all triggers have occurred.
		if (kuro.state==State.death && mship.state==State.death && enemies.size==0 && triggers.size == 0){
			win=true;
			controller.eventGameOver(true);
		}
	}

	void updatePlayerBullets (float delta) {
		outer:
		for(int i = playerbullets.size - 5; i >= 0; i -= 5) {
			float vx = playerbullets.get(i);
			float vy = playerbullets.get(i + 1);
			float x  = playerbullets.get(i + 2);
			float y  = playerbullets.get(i + 3);
		/*	if (collisionLayer.getCell((int)x, (int)y) != null) {
				// Bullet hit map.
				controller.eventHitBullet(x, y, vx, vy);
				bullets.removeRange(i, i + 4);
				continue;
			}*/
			if (Math.abs(x - player.position.x) > 25) {
				// Bullet traveled too far.
				playerbullets.removeRange(i, i + 4);
				continue;
			}
			for (Enemytt enemy : enemies) {
				if (enemy.hp <= 0){
					enemy.velocity.x=0;
					enemy.velocity.y=0;
				}
				if (enemy.bigTimer <= 0 && enemy.rect.contains(x, y)) {
					// Bullet hit enemy.
					playerbullets.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					enemy.collisionTimer = Enemytt.collisionDelay;
					enemy.hp-=Playertt.weaponpower;
					if (enemy.hp <= 0) {
						enemy.state = State.death;
						enemy.velocity.y *= 0.5f;
					} 
					
					enemy.velocity.x += MathUtils.random(enemy.knockbackX / 2, enemy.knockbackX)
						* (player.position.x < enemy.position.x + enemy.rect.width / 2 ? 1 : -1);
					enemy.velocity.y += MathUtils.random(enemy.knockbackX / 2, enemy.knockbackX)
							* (player.position.y < enemy.position.y + enemy.rect.height / 2 ? 1 : -1);
					continue outer;
				}
			}
			x += vx * delta;
			y += vy * delta;
			playerbullets.set(i + 2, x);
			playerbullets.set(i + 3, y);
		}
	}

	void addPlayerBullet (float startX, float startY, float vx, float vy, float angle) {
		playerbullets.add(vx);
		playerbullets.add(vy);
		playerbullets.add(startX);
		playerbullets.add(startY);
		playerbullets.add(angle);
	}
	
	void updateEnemyBullets (float delta) {
		outer:
		for(int i = enemybullets.size - 5; i >= 0; i -= 5) {
			float vx = enemybullets.get(i);
			float vy = enemybullets.get(i + 1);
			float x  = enemybullets.get(i + 2);
			float y  = enemybullets.get(i + 3);
			if (Math.abs(x - player.position.x) > 25) {
				// Bullet traveled too far.
				enemybullets.removeRange(i, i + 4);
				continue;
			}
			
			if (player.state == State.death) continue;
			
			if (player.rect.contains(x, y)) {
				if (player.collisionTimer < 0) {
					// Bullet hit player.
					enemybullets.removeRange(i, i + 4);
					controller.eventHitBullet(x, y, vx, vy);
					//controller.eventHitPlayer(player);
					player.collisionTimer = Playertt.collisionDelay;
					player.velocity.x = -Playertt.knockbackX/2;
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
			enemybullets.set(i + 2, x);
			enemybullets.set(i + 3, y);
		}
	}
	
	void addEnemyBullet (float startX, float startY, float vx, float vy, float angle) {
		enemybullets.add(vx);
		enemybullets.add(vy);
		enemybullets.add(startX);
		enemybullets.add(startY);
		enemybullets.add(angle);
	}

	public float getTimeScale () {
		if (player.hp == 0)
			return timeScale * Interpolation.pow2In.apply(0.1f, 0, MathUtils.clamp(gameOverTimer / gameOverSlowdown, 0.01f, 1));
		return timeScale;
	}

	enum State {
		death
	}

	public static class Trigger {
		float x;
		Array<Enemytt> enemies = new Array<Enemytt>();
	}

	

	
	
}
