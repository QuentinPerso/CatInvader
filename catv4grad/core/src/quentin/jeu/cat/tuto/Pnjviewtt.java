package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.tuto.Playertt.*;
import static quentin.jeu.cat.tuto.Modeltt.*;
import quentin.jeu.cat.tuto.Pnjtt.pnjType;
import quentin.jeu.cat.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** The view class for the player. */
public class Pnjviewtt extends CharacterViewtt{
	private Pnjtt pnj;
	private boolean canShoot;
	public float burstShots;
	private float burstTimer, weaponx, weapony, weapangle;
	private Vector2 temp1 = new Vector2();
	private TextureRegion playerRegion, weapRegion,  propRegion, wingRegion,nanakiRegion, stickR;
	private TextureRegion mshipRegion;
	private Sprite nanapawR;
	private Sound shoots;
	//private Music prop;
	

	Pnjviewtt (final Viewtt view, Pnjtt pnj) {
		super(view);
		this.pnj = pnj;
		playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship1,Texture.class));
		weapRegion   = new TextureRegion(Assets.manager.get(Assets.riceGun,Texture.class));
		propRegion   = new TextureRegion(Assets.manager.get(Assets.prop1,Texture.class));
		wingRegion   = new TextureRegion(Assets.manager.get(Assets.wing,Texture.class));
		nanakiRegion = new TextureRegion(Assets.manager.get(Assets.kuro,Texture.class));
		nanapawR     = new Sprite(Assets.manager.get(Assets.nanapaw,Texture.class));
		nanapawR.setColor(0, 0, 0, 1);
		stickR       = new TextureRegion(Assets.manager.get(Assets.stick,Texture.class));
		
		mshipRegion = new TextureRegion(Assets.manager.get(Assets.mship,Texture.class));
		
		shoots= Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
	}

	void update (float delta) {
	//	prop.setVolume(Math.abs(player.velocity.y)/(5*Playertt.playerMaxvely)+0.1f);
		// When not shooting, reset the number of burst shots.
		if (view.touchedshoot==-1 && burstTimer > 0) {
			burstTimer -= delta;
			if (burstTimer < 0) burstShots = 0;
		}
		canShoot=true;
	
	}
	void render (SpriteBatch batch) {
		
		if(pnj.type==pnjType.kuro){
			float bodyx = pnj.position.x - .45f;
			float bodyy = pnj.position.y - .2f*1.3f;
			float bodywidth  = Pnjtt.width +  1f;
			float bodyheight = Pnjtt.height+ .4f*1.6f;
			
			float ratio= (float)nanakiRegion.getRegionHeight()/ (float)nanakiRegion.getRegionWidth();
			
			float nanaheight = bodyheight/2f/1.35f;
			float nanawidth  = nanaheight/ratio;
			float nanax = pnj.position.x+bodywidth/3.2f;
			float nanay = pnj.position.y+bodyheight/9.4f;
			
			float pawwidth  = bodyheight/2.2f/1.3f;
			float pawx = pnj.position.x+bodywidth/2.8f;
			float pawy = pnj.velocity.y>0? pnj.position.y-bodyheight/12f/1.6f : pnj.position.y-bodyheight/12f/1.6f-pnj.velocity.y/100;
			
			float sticwidth  = bodyheight/2.2f/1.2f;
			float sticx = pnj.position.x+bodywidth/2.2f-pnj.velocity.y/100/1.2f;
			float sticy = pnj.position.y-bodyheight/12f/1.6f;
			
			ratio= (float)propRegion.getRegionHeight()/ (float)propRegion.getRegionWidth();
			float propwidth = 0.7f;
			float propheight = ratio*propwidth;
			float propx = pnj.position.x+bodywidth*0.29f-.5f-propwidth*0.818f;
			float propy = pnj.position.y+bodyheight*0.3f-.2f-propheight/2;
			
			ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
			float weapwidth = 0.45f;
			float weapheight = ratio*weapwidth;
			float weapx = pnj.position.x+Pnjtt.width+0.3f-weapwidth/3;
			float weapy = pnj.position.y-weapheight/2;
			
			float wingy      = bodyy+.1f      +pnj.velocity.y/50;
			float wingheight = bodyheight/1.3f     -pnj.velocity.y/10;
			
			batch.draw(nanakiRegion, nanax, nanay,nanawidth,nanaheight);
			nanapawR.setSize(pawwidth,pawwidth);
			nanapawR.setOrigin(pawwidth *0.2f,pawwidth/2f);
			nanapawR.setPosition(pawx , pawy);
			nanapawR.setRotation(pnj.velocity.y*3);
			nanapawR.draw(batch);
			batch.draw(stickR  , sticx, sticy, sticwidth*0.8f,sticwidth*0.2f,pawwidth,pawwidth,1,1,-pnj.velocity.y*3);
			
			batch.draw(propRegion , propx, propy,propwidth*0.818f,propwidth/2f,propwidth,propheight,1,1,pnj.velocity.y*2);
			batch.draw(playerRegion, bodyx, bodyy,bodywidth,bodyheight);
			batch.draw(weapRegion , weapx, weapy,weapwidth/6,weapheight/2,weapwidth,weapheight,1,1,weapangle);
			batch.draw(wingRegion, bodyx, wingy,bodywidth, wingheight);
		}
		if(pnj.type==pnjType.mship){
			float bodyx = pnj.position.x - .5f;
			float bodyy = pnj.position.y - .2f;
			float bodywidth  = pnj.rect.width +  1f;
			float bodyheight = pnj.rect.height+ .4f;
			batch.draw(mshipRegion, bodyx, bodyy,bodywidth,bodyheight);
			
		}
	}



	void moveup (float delta) {
		pnj.moveup(delta);
	}
	void movedown (float delta) {
		pnj.movedown(delta);
	}

	void shoot () {
		if (!canShoot || pnj.shootTimer >= 0) return;
		
		pnj.shootTimer = 1/Pnjtt.shootpsec;
		burstTimer = burstDuration;
		
		// Compute the position and velocity to spawn a new bullet.
		weaponx = pnj.position.x+Playertt.width+0.3f;
		weapony = pnj.position.y+0.f;
		
		float mouseX = Gdx.graphics.getWidth()*3/4, mouseY = 0;
		mouseY+=MathUtils.random(-Gdx.graphics.getHeight()*3/4, Gdx.graphics.getHeight()*3/4);
		weapangle = view.viewport.unproject(temp1.set(mouseX, mouseY)).sub(weaponx, weapony).angle();
		//float variance = minprecisionheated * Math.min(1, burstShots * heatperShot/maxShots2heat);
		//weapangle += MathUtils.random(-variance, variance);
		float cos = MathUtils.cosDeg(weapangle), sin = MathUtils.sinDeg(weapangle);
		float vx = cos * bulletSpeed + pnj.velocity.x * bulletInheritVelocity;
		float vy = sin * bulletSpeed;// + player.velocity.y * bulletInheritVelocity;
		
		weaponx += cos * shootOffsetX * scale;
		weapony += sin * shootOffsetX * scale;
		model.addPlayerBullet(weaponx, weapony, vx, vy, temp1.set(vx, vy).angle());

		/*view.camera.position.sub(view.shakeX, view.shakeY, 0);
		view.shakeX += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.shakeY += view.cameraShake * (MathUtils.randomBoolean() ? 1 : -1);
		view.camera.position.add(view.shakeX, view.shakeY, 0);*/

		shoots.play(0.5f);

		burstShots = Math.min(maxShots2heat, burstShots + 1);
	}

	void dispose(){
		shoots.dispose();
	}

}
