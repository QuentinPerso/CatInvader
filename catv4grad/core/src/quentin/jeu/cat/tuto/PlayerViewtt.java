package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.tuto.Playertt.*;
import static quentin.jeu.cat.tuto.Modeltt.*;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** The view class for the player. */
public class PlayerViewtt extends CharacterViewtt {
	private Playertt player;
	private boolean canShoot;
	public float burstShots;
	private float burstTimer, weaponx, weapony, weapangle, ytouchO;
	private Vector2 temp1 = new Vector2();
	private TextureRegion playerRegion, weapRegion,  propRegion, wingRegion,nanakiRegion, stickR;
	private Sprite nanapawR;
	private Sound shoots;
	
	PlayerViewtt (final Viewtt view) {
		super(view);
		player = view.player;
		playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship1,Texture.class));
		weapRegion   = new TextureRegion(Assets.manager.get(Assets.riceGun,Texture.class));
		propRegion   = new TextureRegion(Assets.manager.get(Assets.prop1,Texture.class));
		wingRegion   = new TextureRegion(Assets.manager.get(Assets.wing,Texture.class));
		nanakiRegion = new TextureRegion(Assets.manager.get(Assets.nanaki,Texture.class));
		nanapawR     = new Sprite(Assets.manager.get(Assets.nanapaw,Texture.class));
		stickR       = new TextureRegion(Assets.manager.get(Assets.stick,Texture.class));
		
		shoots= Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
		
		//prop.setVolume(prop.loop(),player.velocity.y/player.maxVelocityY);
		
	}

	void update (float delta) {
		// When not shooting, reset the number of burst shots.
		if (view.touchedshoot==-1 && burstTimer > 0) {
			burstTimer -= delta;
			if (burstTimer < 0) burstShots = 0;
		}
		canShoot=true;
		
		// Move toward touch
		if(view.touchedmove!=-1){
			if(view.touchmove){
				ytouchO = view.touchmoveorigin;
				view.touchmove=false;
			}
			float mY = Gdx.input.getY(view.touchedmove);
			float disttotap = ytouchO-mY;
			if(disttotap>15 ) ytouchO=mY+15;
			if(disttotap<-15) ytouchO=mY-15;
			
			if(disttotap>0) player.moveup(delta);
			if(disttotap<0) player.movedown(delta);
		}
	}
	void render (SpriteBatch batch) {
		
		float bodyx = player.position.x - .45f;
		float bodyy = player.position.y - .2f*1.3f;
		float bodywidth  = Pnjtt.width +  1f;
		float bodyheight = Pnjtt.height+ .4f*1.6f;
		
		float ratio= (float)nanakiRegion.getRegionHeight()/ (float)nanakiRegion.getRegionWidth();
		
		float nanaheight = bodyheight/2f/1.35f;
		float nanawidth  = nanaheight/ratio;
		float nanax = player.position.x+bodywidth/3.2f;
		float nanay = player.position.y+bodyheight/9.4f;
		
		float pawwidth  = bodyheight/2.2f/1.3f;
		float pawx = player.position.x+bodywidth/2.8f;
		float pawy = player.velocity.y>0? player.position.y-bodyheight/12f/1.6f : player.position.y-bodyheight/12f/1.6f-player.velocity.y/100;
		
		float sticwidth  = bodyheight/2.2f/1.2f;
		float sticx = player.position.x+bodywidth/2.2f-player.velocity.y/100/1.2f;
		float sticy = player.position.y-bodyheight/12f/1.6f;
		
		ratio= (float)propRegion.getRegionHeight()/ (float)propRegion.getRegionWidth();
		float propwidth = 0.7f;
		float propheight = ratio*propwidth;
		float propx = player.position.x+bodywidth*0.29f-.5f-propwidth*0.818f;
		float propy = player.position.y+bodyheight*0.3f-.2f-propheight/2;
		
		ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
		float weapwidth = 0.45f;
		float weapheight = ratio*weapwidth;
		float weapx = player.position.x+Pnjtt.width+0.3f-weapwidth/3;
		float weapy = player.position.y-weapheight/2;
		
		float wingy      = bodyy+.1f      +player.velocity.y/50;
		float wingheight = bodyheight/1.3f     -player.velocity.y/10;
		
		batch.draw(nanakiRegion, nanax, nanay,nanawidth,nanaheight);
		nanapawR.setSize(pawwidth,pawwidth);
		nanapawR.setOrigin(pawwidth *0.2f,pawwidth/2f);
		nanapawR.setPosition(pawx , pawy);
		nanapawR.setRotation(player.velocity.y*3);
		nanapawR.draw(batch);
		batch.draw(stickR  , sticx, sticy, sticwidth*0.8f,sticwidth*0.2f,pawwidth,pawwidth,1,1,-player.velocity.y*3);
		
		batch.draw(propRegion , propx, propy,propwidth*0.818f,propwidth/2f,propwidth,propheight,1,1,player.velocity.y*2);
		batch.draw(playerRegion, bodyx, bodyy,bodywidth,bodyheight);
		batch.draw(weapRegion , weapx, weapy,weapwidth/6,weapheight/2,weapwidth,weapheight,1,1,weapangle);
		batch.draw(wingRegion, bodyx, wingy,bodywidth, wingheight);
	}



	void moveup (float delta) {
		player.moveup(delta);
	}
	void movedown (float delta) {
		player.movedown(delta);
	}

	void shoot () {
		
		if (!canShoot || player.shootTimer >= 0) return;
		player.shootTimer = 1/Playertt.shootpsec;
		burstTimer = burstDuration;
		
		// Compute the position and velocity to spawn a new bullet.
		weaponx = player.position.x+Playertt.width+0.3f;
		weapony = player.position.y+0.f;
		
		float mouseX = Gdx.input.getX(view.touchedshoot), mouseY = Gdx.input.getY(view.touchedshoot);
		
		weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
		float variance = minprecisionheated * Math.min(1, burstShots * heatperShot/maxShots2heat);
		weapangle += MathUtils.random(-variance, variance);
		float cos = MathUtils.cosDeg(weapangle), sin = MathUtils.sinDeg(weapangle);
		float vx = cos * bulletSpeed + player.velocity.x * bulletInheritVelocity;
		float vy = sin * bulletSpeed;// + player.velocity.y * bulletInheritVelocity;
		
		weaponx += cos * shootOffsetX * scale;
		weapony += sin * shootOffsetX * scale;
		model.addPlayerBullet(weaponx, weapony, vx, vy, temp1.set(vx, vy).angle());

		view.camera.position.sub(view.shakeX, view.shakeY, 0);
		view.shakeX += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.shakeY += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.camera.position.add(view.shakeX, view.shakeY, 0);

		if(Save.gd.soundEnabled)shoots.play(0.5f);

		burstShots = Math.min(maxShots2heat, burstShots + 1);
	}

	void dispose(){
		shoots.dispose();
	}

}
