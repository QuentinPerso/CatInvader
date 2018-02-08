package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Player.*;
import static quentin.jeu.cat.catinvader.Model.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** The view class for the player. */
public class PlayerView extends CharacterView {
	private Player player;
	public float rgShots, smShots;
	private float rgBurstTimer, smBurstTimer, lzBurstTimer;
	private float  weaponx, weapony;
	float weapangle;
	private float ytouchO, accelx0;
	private Vector2 temp1 = new Vector2();
	private TextureRegion playerRegion,  propRegion, wingRegion,nanakiRegion,nanapawR, stickR;
	private Sound shoots;
	boolean move;
	
	PlayerView (final View view) {
		super(view);
		player = view.player;
		playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship1,Texture.class));
		propRegion   = new TextureRegion(Assets.manager.get(Assets.prop1,Texture.class));
		wingRegion   = new TextureRegion(Assets.manager.get(Assets.wing,Texture.class));
		nanakiRegion = new TextureRegion(Assets.manager.get(Assets.nanaki,Texture.class));
		nanapawR     = new TextureRegion(Assets.manager.get(Assets.nanapaw,Texture.class));
		stickR       = new TextureRegion(Assets.manager.get(Assets.stick,Texture.class));
		
		if(Save.gd.hp+Save.gd.speed>=(CatGame.maxhp+CatGame.maxSpeed)*6/7){
			playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship3,Texture.class));
			wingRegion   = null;
		}
		
		else if(Save.gd.hp+Save.gd.speed>=(CatGame.maxhp+CatGame.maxSpeed)*4/7){
			playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship2,Texture.class));
			wingRegion   = new TextureRegion(Assets.manager.get(Assets.wing,Texture.class));
		}
		
		if(Save.gd.acceleration+Save.gd.speed>=(CatGame.maxAcc+CatGame.maxSpeed)*3/5){
			propRegion   = new TextureRegion(Assets.manager.get(Assets.prop2,Texture.class));
		}
		if(Save.gd.accelerometer){
			accelx0= Gdx.input.getAccelerometerX();
		}
		shoots= Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
	}

	void update (float delta) {
		// When not shooting, reset the number of burst shots.
		//RG
		if ((view.touchedshoot==-1 || view.activWeap!=CatGame.RG) && rgBurstTimer > 0) {
			rgBurstTimer -= delta;
			if (rgBurstTimer < 0 && rgShots>0){
				rgShots -= 1;
				rgBurstTimer=player.RGreloadTime;
			}
			else if(rgShots<=0) rgShots=0;
		}
		//SM
		if ((view.touchedshoot==-1 || view.activWeap!=CatGame.SM) && smBurstTimer > 0) {
			smBurstTimer -= delta;
			if (smBurstTimer < 0 && smShots!=0){
				smShots -= 1;
				smBurstTimer=1/player.SMshootpsec+player.SMreloadTime;
			}
			else if(smShots<=0) smShots=0;
		}
		player.SMnumber=player.SMmaxShots2heat*3-smShots*3+3;
		player.SMbulletSpeed = Math.max(Save.gd.sushiGspeed/2, player.SMmaxShots2heat*3-smShots*3+3);
		
		//LZ
		if ((view.touchedshoot==-1 || view.activWeap!=CatGame.LZ) && player.laserpower<Save.gd.lasermax) {
			if(lzBurstTimer > 0) lzBurstTimer-=delta;
			else
				player.laserpower+=Save.gd.lasermax/2f*laserdecade/2f*player.laserReload;
		
		}
		move=false;
		// Move toward touch
		if(view.touchedmove!=-1){
			move=true;
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
		if(Save.gd.accelerometer){
			float accelX = Gdx.input.getAccelerometerX();
			if(accelX>accelx0+1 ){
				player.movedown(delta);
				move=true;
			}
			if(accelX<accelx0-1){
				player.moveup(delta);
				move=true;
			}
		    
		}
	}
	void render (SpriteBatch batch) {
		
		float bodyx = player.position.x - .45f;
		float bodyy = player.position.y - .2f*1.3f;
		float bodywidth  = Player.width +  1f;
		float bodyheight = Player.height+ .4f*1.6f;
		
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
		
		batch.draw(nanakiRegion, nanax, nanay,nanawidth,nanaheight);
		batch.draw(nanapawR, pawx , pawy , pawwidth *0.2f,pawwidth/2f   ,pawwidth,pawwidth,1,1, player.velocity.y*3);
		batch.draw(stickR  , sticx, sticy, sticwidth*0.8f,sticwidth*0.2f,pawwidth,pawwidth,1,1,-player.velocity.y*3);
		
		batch.draw(propRegion , propx, propy,propwidth*0.818f,propwidth/2f,propwidth,propheight,1,1,player.velocity.y*2);
		batch.draw(playerRegion, bodyx, bodyy,bodywidth,bodyheight);
		if(view.activWeap==CatGame.RG){
			TextureRegion weapRegion   = new TextureRegion(Assets.manager.get(Assets.riceGun,Texture.class));
			ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
			float weapwidth = 0.45f;
			float weapheight = ratio*weapwidth;
			float weapx = player.position.x+Player.width+0.3f-weapwidth/3;
			float weapy = player.position.y-weapheight/2;
			batch.draw(weapRegion , weapx, weapy,weapwidth/6,weapheight/2,weapwidth,weapheight,1,1,weapangle);
		}
		if(view.activWeap==CatGame.SM){
			TextureRegion weapRegion   = new TextureRegion(Assets.manager.get(Assets.sushiGun,Texture.class));
			ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
			float weapwidth = 0.57f; //0.4*SMwhidth/RGw
			float weapheight = ratio*weapwidth;
			float weapx = player.position.x+Player.width+weapwidth/7;
			float weapy = player.position.y-weapheight/2;
			batch.draw(weapRegion , weapx, weapy,weapwidth/6,weapheight/2,weapwidth,weapheight,1,1,weapangle);
		}
		if(view.activWeap==CatGame.LZ){
			TextureRegion weapRegion   = new TextureRegion(Assets.manager.get(Assets.laserGun,Texture.class));
			ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
			float weapwidth = 0.57f;
			float weapheight = ratio*weapwidth;
			float weapx = player.position.x+Player.width+weapwidth/7;
			float weapy = player.position.y-weapheight/2;
			batch.draw(weapRegion , weapx, weapy,weapwidth/6,weapheight/2,weapwidth,weapheight,1,1,weapangle);
		}
		if(wingRegion!=null){
			float wingy      = bodyy+.1f      +player.velocity.y/50;
			float wingheight = bodyheight/1.3f     -player.velocity.y/10;
			batch.draw(wingRegion, bodyx, wingy,bodywidth, wingheight);
		}
	}



	void moveup (float delta) {
		player.moveup(delta);
	}
	void movedown (float delta) {
		player.movedown(delta);
	}

	void shootRG () {
		
		if (player.shootRGTimer >= 0) return;
		player.shootRGTimer = Math.min(0.5f, 1/player.RGshootpsec*
				((2.5f*rgShots+player.RGmaxShots2heat)/player.RGmaxShots2heat)); //heat 
		rgBurstTimer = player.RGreloadTime;
		// Compute the position and velocity to spawn a new bullet.
		weaponx = player.position.x+Player.width+0.3f;
		weapony = player.position.y+0.f;
		
		float mouseX = Gdx.input.getX(view.touchedshoot), mouseY = Gdx.input.getY(view.touchedshoot);
		
		weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
		
		float variance = player.RGminprecHeated * Math.min(1, rgShots/player.RGmaxShots2heat*rgShots/player.RGmaxShots2heat);
		weapangle += MathUtils.random(-variance, variance);
		float cos = MathUtils.cosDeg(weapangle), sin = MathUtils.sinDeg(weapangle);
		float vx = cos * RGbulletSpeed + player.velocity.x;
		float vy = sin * RGbulletSpeed;// + player.velocity.y * bulletInheritVelocity;
		
		weaponx += cos * shootOffsetX;
		weapony += sin * shootOffsetX;
		model.addPlayerRGBullet(weaponx, weapony, vx, vy, temp1.set(vx, vy).angle());

		view.camera.position.sub(view.shakeX, view.shakeY, 0);
		view.shakeX += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.shakeY += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.camera.position.add(view.shakeX, view.shakeY, 0);

		if(Save.gd.soundEnabled)shoots.play(0.5f);

		rgShots = Math.min(player.RGmaxShots2heat, rgShots + 1);
	}

	void shootSM(){
		if (player.shootSMTimer >= 0) return;
		player.shootSMTimer = 1/player.SMshootpsec*((3*smShots+player.SMmaxShots2heat)/player.SMmaxShots2heat); //heat 
		
		smBurstTimer = 1/player.SMshootpsec*((3*smShots+player.SMmaxShots2heat)/player.SMmaxShots2heat)+player.SMreloadTime;
		
		// Compute the position and velocity to spawn a new bullet.
		weaponx = player.position.x+Player.width+0.3f;
		weapony = player.position.y+0.f;
		
		float mouseX = Gdx.input.getX(view.touchedshoot), mouseY = Gdx.input.getY(view.touchedshoot);
		
		weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
		float cos = MathUtils.cosDeg(weapangle), sin = MathUtils.sinDeg(weapangle);
		float vx = cos * player.SMbulletSpeed + player.velocity.x;
		float vy = sin * player.SMbulletSpeed;// + player.velocity.y * bulletInheritVelocity;
		
		weaponx += cos * shootOffsetX;
		weapony += sin * shootOffsetX;
		model.addPlayerSM(weaponx, weapony, vx, vy, temp1.set(vx, vy).angle());

		view.camera.position.sub(view.shakeX, view.shakeY, 0);
		view.shakeX += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.shakeY += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.camera.position.add(view.shakeX, view.shakeY, 0);

		if(Save.gd.soundEnabled)shoots.play(0.5f,0.25f,0);

		smShots = Math.min(player.SMmaxShots2heat, smShots + 1);
	}

	void explodeSM(int sushinbr){
		float sushix = model.playerSM.get(sushinbr+2);
		float sushiy = model.playerSM.get(sushinbr+3);
		for(int i =0;i<player.SMnumber; i++){
			
			float angle = 360/player.SMnumber*i;
			float cos = MathUtils.cosDeg(angle), sin = MathUtils.sinDeg(angle);
			float vx = cos * Player.RGbulletSpeed;
			float vy = sin * Player.RGbulletSpeed;
			
			sushix += cos * 2 * scale;
			sushiy += sin * 2 * scale;
			model.addPlayerSMBullet(sushix, sushiy, vx, vy, temp1.set(vx, vy).angle());
		}
		
	}
	
	void shootLaser(){
		if(player.laserpower>player.laserminpower) player.laserpower-=Save.gd.lasermax/2f*laserdecade;
		else player.laserpower=player.laserminpower;
		if (player.laserpower <= 0){
			player.shootlz=false;
			float mouseX = Gdx.input.getX(view.touchedshoot), mouseY = Gdx.input.getY(view.touchedshoot);
			weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
			return;
		}
		player.shootlz=true;
		lzBurstTimer=laserreloadTime;
		
		weaponx = player.position.x+Player.width+0.3f;
		weapony = player.position.y+0.f;
		
		float mouseX = Gdx.input.getX(view.touchedshoot), mouseY = Gdx.input.getY(view.touchedshoot);
		
		weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
		

		if(Save.gd.soundEnabled)shoots.play(0.4f*player.laserpower/CatGame.maxLZpower+0.1f,1-0.5f*player.laserpower/CatGame.maxLZpower,0);

		//smShots = Math.min(player.SMmaxShots2heat, smShots + 1);
	
	}
	void dispose(){
		shoots.dispose();
	}
}
