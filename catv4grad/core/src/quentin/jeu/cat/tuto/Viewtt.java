package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.tuto.Modeltt.*;
import static quentin.jeu.cat.tuto.Playertt.*;
import quentin.jeu.cat.tuto.Enemytt.Type;
import quentin.jeu.cat.tuto.Modeltt.State;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** The core of the view logic. The view knows about the model and manages everything needed to draw to the screen. */
public class Viewtt extends InputAdapter {
	
	public static float bulletHitTime = 0.2f;

	private static float cameraWidth = 16, cameraHeight = 16, cameraMinX = 1, cameraSpeed = 5f;
	public float cameraShake = 12 * scale;
	public Modeltt model;
	public Playertt player;
	public Pnjtt kuro, mship;
	public OrthographicCamera camera;
	public ExtendViewport viewport;
	public SpriteBatch batch;
	public UItt ui;
	
	private ParticleEffectPool bombEffectPool;
	private Array<PooledEffect> effects = new Array<PooledEffect>();
	private ParticleEffect propEffect, pnjpropEffect;
	
	private float animtimer;
	private float timer=0;
	
	public float shakeX;
	public float shakeY;
	
	public FloatArray hits = new FloatArray();
	
	public boolean upPressed, downPressed, touchmove, touched;
	public int touchedshoot, touchedmove;
	public float touchmoveorigin;
	
	private Sound explode= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
	private Sound sondecolect= Gdx.audio.newSound(Gdx.files.internal("sounds/sonde.wav"));
	
	public Viewtt (Modeltt model) {
		this.model = model;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);

		
		propEffect = new ParticleEffect();
		propEffect.load(Gdx.files.internal("effects/prop.p"), Gdx.files.internal("effects/"));
		pnjpropEffect = new ParticleEffect();
		pnjpropEffect.load(Gdx.files.internal("effects/prop.p"), Gdx.files.internal("effects/"));
		
		
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal("effects/"));
		bombEffectPool = new ParticleEffectPool(bombEffect, 10, 10);
		
		ui = new UItt(this);

		Gdx.input.setInputProcessor(new InputMultiplexer(ui, ui.stage, this));

		restart();
		
	}

	public void restart () {
		camera.zoom=0.1f;
		upPressed=false;
		downPressed=false;
		touchmove=false;
		touchedmove=-1;
		touchedshoot=-1;
		touchmoveorigin=0;
		player = model.player;
		player.view = new PlayerViewtt(this);
		player.velocity.y=0;
		kuro = model.kuro;
		kuro.view = new Pnjviewtt(this, kuro);
		
		mship = model.mship;
		mship.view = new Pnjviewtt(this, mship);
		
		camera.position.x=0;
		camera.position.y=player.position.y;
		hits.clear();
		timer=0;
	}

	public void update (float delta) {
		// Update the hit marker images.
		for (int i = hits.size - 4; i >= 0; i -= 4) {
			float time = hits.get(i) - delta;
			if (time < 0)
				hits.removeRange(i, i + 3);
			else
				hits.set(i, time);
		}
		updateInput(delta);
		updateCamera(delta);

		player.view.update(delta);
		kuro.view.update(delta);
		mship.view.update(delta);

		for (Enemytt enemy : model.enemies) {
			if (enemy.view == null) enemy.view = new EnemyViewtt(this, enemy);
			//enemy.view.update(delta);
		}
	}

	void updateInput (float delta) {
		if (player.hp == 0) return;

		if (touchedshoot!=-1){
			player.view.shoot();
		}
		if(upPressed){
			player.moveup(delta);
		}
		if(downPressed){
			player.movedown(delta);
		}
		else if(!upPressed && !upPressed && touchedmove==-1){
			player.velocity.y*= inertia;
		}
		for(int i=0;i<model.enemies.size;i++){
			if(model.enemies.get(i).type!=Type.probes && kuro.state!=State.death)kuro.view.shoot();
		}
	}

	void updateCamera (float delta) {
		// Move camera to the player position over time, adjusting for lookahead.
		if(!model.win && camera.zoom<1){
			camera.zoom+=camera.zoom/100;
			camera.position.x=player.position.x+2+10*(camera.zoom-0.1f);
		}
		else if(!model.win){
			camera.zoom=1;
			camera.position.x +=   Playertt.ScrollVelocityX*delta;//player.position.x+11;
			if(player.position.x<camera.position.x-11) player.maxVelocityX=9;
			else  player.maxVelocityX=Playertt.ScrollVelocityX;
			if(player.position.x>camera.position.x-9) player.moveLeft(delta);
		}
		else{
			if(camera.zoom>0.2f)
				camera.zoom-=delta*0.1f;
			cameraShake=0.5f*scale;
			float minX = player.position.x+2;
			if (camera.position.x < minX) {
				camera.position.x += (minX - camera.position.x) * cameraSpeed * delta;
			}
			
			if(player.position.y+camera.zoom*8<8 && player.position.y-camera.zoom*8>-8)
				if(camera.position.y+0.5f>player.position.y || camera.position.y-0.5f<player.position.y) 
					camera.position.y+= (player.position.y-camera.position.y)* cameraSpeed * delta;
			camera.position.x = Math.max(viewport.getWorldWidth() / 2 - cameraMinX, camera.position.x);
		}
	
		//Pnj 2 camera
		if(kuro.hp>0){
			if(kuro.position.x<camera.position.x-11) kuro.maxVelocityX=9;
			else  kuro.maxVelocityX=Playertt.ScrollVelocityX;
			if(kuro.position.x>camera.position.x-8) kuro.moveLeft(delta);
		}
		else kuro.maxVelocityX*=0.995f;
		
		//mship 2 cam
		if(mship.hp>0){
			if(mship.position.x<camera.position.x-17) mship.maxVelocityX=9;
			else if(mship.position.x>camera.position.x-16) mship.maxVelocityX=3;
			else  mship.maxVelocityX=Playertt.ScrollVelocityX;
		}
		else mship.maxVelocityX*=0.995f;
		
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		camera.position.add(-shakeX, -shakeY, 0);
		shakeX = 0;
		shakeY = 0;
		
	}

	public void render (float delta) {
		viewport.apply();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		//draw background
		Sprite bg  = new Sprite(Assets.manager.get(Assets.galaxybg,Texture.class));
		Sprite bg1 = new Sprite(Assets.manager.get(Assets.stars,Texture.class));
		
		//TextureRegion asteroid0 = new TextureRegion(Assets.manager.get(Assets.asteroid0,Texture.class));
		//TextureRegion asteroid1 = new TextureRegion(Assets.manager.get(Assets.asteroid1,Texture.class));
		if(ui.black!=null){
			float a=1-ui.black.getColor().a;
			batch.setColor(a, a, a, 1);
			bg1.setColor(a, a, a, 1);
			bg.setColor(a, a, a, 1);
			float[] colorshield = new float[]{1*a*a,0.2f*a*a,0.047f*a*a};
			propEffect.getEmitters().get(0).getTint().setColors(colorshield);
		}
		
		timer+=delta;
		bg1.setSize(18*16/9,18);
		bg1.setFlip(false, false);
		bg1.setPosition(camera.position.x-timer- 9*16/9,-9);
		bg1.draw(batch);
		bg1.setPosition(camera.position.x-timer+ 9*16/9,-9);
		bg1.draw(batch);
		bg.setSize(18*16/9,18);
		bg.setPosition (camera.position.x-timer+24*16/9,-9);
		bg.draw(batch);
		bg1.setFlip(true, false);
		bg1.setPosition(camera.position.x-timer+42*16/9,-9);
		bg1.draw(batch);
		bg1.setPosition(camera.position.x-timer+60*16/9,-9);
		bg1.draw(batch);
		/*for(int i=0;i<100;i++){
			batch.draw(asteroid0, camera.position.x-10*grot+25*i ,-5,5,5,10,10,1, 1, 100*grot);
		}*/
		
		
	
		
		//draw mship
		
		if(mship.hp<=0 && mship.position.x>camera.position.x-20){
			// Create effect:
			PooledEffect effect = bombEffectPool.obtain();
			effect.setPosition(mship.position.x+MathUtils.random(-1, 1)*mship.rect.width/2, mship.position.y+MathUtils.random(-1, 1)*mship.rect.height/2);
			if((int)mship.deathTimer%1==0){
				
				effects.add(effect);
			}
			if(mship.exploded==false){
				ui.playMSdead();
				if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
				mship.exploded=true;
			}
			
		}
		mship.view.render(batch);
		
		//draw kuro
		pnjpropEffect.setPosition(kuro.position.x, kuro.position.y+0.1f);
		pnjpropEffect.getEmitters().get(0).getAngle().setHighMax(180+kuro.velocity.y*2+20);
		pnjpropEffect.getEmitters().get(0).getAngle().setLowMax(180+kuro.velocity.y*2-20);
		pnjpropEffect.getEmitters().get(0).getAngle().setHighMin(180+kuro.velocity.y*2);
		pnjpropEffect.getEmitters().get(0).getAngle().setLowMin(180+kuro.velocity.y*2);
		pnjpropEffect.draw(batch, delta);
		if (( (kuro.collisionTimer < 0 || (int)(kuro.collisionTimer / flashTime) % 3 != 0)) || kuro.hp==0){
			if(kuro.hp<=0){
				// Create effect:
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(kuro.position.x+kuro.rect.width/2, kuro.position.y+kuro.rect.height/2);
				if( kuro.exploded==false){
					effects.add(effect);
					ui.playKNdead();
					if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
					kuro.exploded=true;
				}
			}
			kuro.view.render(batch);
		}
	
		
		// Draw player.
		propEffect.setPosition(player.position.x, player.position.y+0.1f);
		propEffect.getEmitters().get(0).getAngle().setHighMax(180+player.velocity.y*2+20);
		propEffect.getEmitters().get(0).getAngle().setLowMax(180+player.velocity.y*2-20);
		propEffect.getEmitters().get(0).getAngle().setHighMin(180+player.velocity.y*2);
		propEffect.getEmitters().get(0).getAngle().setLowMin(180+player.velocity.y*2);
		propEffect.draw(batch, delta);
		if ((player.hp>=1 && (player.collisionTimer < 0 || (int)(player.collisionTimer / flashTime) % 3 != 0)) || player.hp==0){
			if(player.hp<=0){
				// Create effect:
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(player.position.x+player.rect.width/2, player.position.y+player.rect.height/2);
				if( player.exploded==false){
					effects.add(effect);
					if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
					player.exploded=true;
				}
				
			}
			player.view.render(batch);
		}
		// Draw enemies.
		Sprite normalRegion       = new Sprite(Assets.manager.get(Assets.normal1,Texture.class));
		Sprite weakRegion         = new Sprite(Assets.manager.get(Assets.weak1,Texture.class));
		Sprite strongRegion       = new Sprite(Assets.manager.get(Assets.strong1,Texture.class));
		Sprite bomberRegion       = new Sprite(Assets.manager.get(Assets.bomber1,Texture.class));
		TextureRegion bomb1Region = new Sprite(Assets.manager.get(Assets.bombe1,Texture.class));
		TextureRegion bomb2Region = new Sprite(Assets.manager.get(Assets.bombe2,Texture.class));
		
		
		Sprite asteroidRegion0     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion0.rotate(180*timer);
		Sprite asteroidRegion1     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion1.rotate(180*timer);
		Sprite asteroidRegion2     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion2.rotate(180*timer);
		
		Sprite probeR = new Sprite(Assets.manager.get(Assets.probe,Texture.class));
		
		animtimer-=delta;
		if(animtimer>10) animtimer=0;
		for (Enemytt enemy : model.enemies) {
			float x = enemy.position.x;
			float y = enemy.position.y;
			float width  = enemy.rect.width;
			float height = enemy.rect.height;
			if (enemy.type == Type.bombes){
				batch.draw(bomb1Region, x, y,width,height);
				if ((int)(animtimer / (flashTime*2)) % 3 != 0){
					batch.draw(bomb2Region, x, y,width,height);
				}
			}
			else if (enemy.type == Type.bomber){
				float colorvar=Math.max(0, 1-(Enemytt.hpBomber -enemy.hp)/Enemytt.hpBomber);
				bomberRegion.setColor(1f, colorvar, colorvar, 1);
				bomberRegion.setPosition(x, y);
				bomberRegion.setSize(width, height);
				bomberRegion.draw(batch);
			}
			else if (enemy.type == Type.normal){
				float colorvar=Math.max(0, 1-(Enemytt.hpNormal -enemy.hp)/Enemytt.hpNormal);
				normalRegion.setColor(1f, colorvar, colorvar, 1);
				normalRegion.setPosition(x, y);
				normalRegion.setSize(width, height);
				normalRegion.draw(batch);
			}
			else if (enemy.type == Type.strong){
				float colorvar=Math.max(0, 1-(Enemytt.hpStrong -enemy.hp)/Enemytt.hpStrong);
				strongRegion.setColor(1f, colorvar, colorvar, 1);
				strongRegion.setPosition(x, y);
				strongRegion.setSize(width, height);
				strongRegion.draw(batch);
			}
			else if (enemy.type == Type.weak){
				float colorvar=Math.max(0, 1-(Enemytt.hpWeak -enemy.hp)/Enemytt.hpWeak);
				weakRegion.setColor(1f, colorvar, colorvar, 1);
				weakRegion.setPosition(x, y);
				weakRegion.setSize(width, height);
				weakRegion.draw(batch);
			}
			else if ( enemy.type == Type.smallaster){
				float colorvar=Math.max(0, 1-(Enemytt.hpWeak -enemy.hp)/Enemytt.hpWeak);
				asteroidRegion0.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion0.setPosition(x-width/6, y-width/6);
				asteroidRegion0.setSize(width*4/3, height*4/3);
				asteroidRegion0.setOriginCenter();
				asteroidRegion0.draw(batch);
			}
			else if (enemy.type == Type.normalaster ){
				float colorvar=Math.max(0, 1-(Enemytt.hpNormal -enemy.hp)/Enemytt.hpNormal);
				asteroidRegion1.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion1.setPosition(x-width/6, y-width/6);
				asteroidRegion1.setSize(width*4/3, height*4/3);
				asteroidRegion1.setOriginCenter();
				asteroidRegion1.draw(batch);
			}
			else if (enemy.type == Type.bigaster){
				float colorvar=Math.max(0, 1-(Enemytt.hpStrong -enemy.hp)/Enemytt.hpStrong);
				asteroidRegion2.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion2.setPosition(x-width/6, y-width/6);
				asteroidRegion2.setSize(width*4/3, height*4/3);
				asteroidRegion2.setOriginCenter();
				asteroidRegion2.draw(batch);
			}
			else if (enemy.type == Type.probes){
				float colorvar=Math.max(0, 1-(Enemytt.hpBomber -enemy.hp)/Enemytt.hpBomber);
				probeR.setPosition(x-0.2f, y-0.2f);
				probeR.setSize(width+0.4f,height+0.4f);
				probeR.setOriginCenter();
				if(enemy.state!=State.death){
					probeR.setSize(width+0.4f,height+0.4f);
					probeR.setColor(1f, colorvar, colorvar, 1);
				}
				else if(enemy.deathTimer*2>0) {
					probeR.setColor(1f, 1, 1, (enemy.deathTimer*2));
					probeR.setSize((width+0.4f)/(enemy.deathTimer*2),(height+0.4f)/(enemy.deathTimer*2));
				}
				probeR.draw(batch);
			}
			
			
			if (enemy.state==State.death && enemy.position.x>camera.position.x-20 
					&& enemy.position.x<camera.position.x+20 
					&& enemy.position.y<camera.position.y+10
					&& enemy.position.y>camera.position.y-8){
				// Create effect:
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(x+width/2, y+height/2);
				if( enemy.exploded==false){
					effects.add(effect);
					float vol=MathUtils.random(0.2f, 1);
					if(Save.gd.soundEnabled)explode.play(1,vol,0);
					enemy.exploded=true;
				}
				if( enemy.colected==false){
					if(Save.gd.soundEnabled)sondecolect.play(0.5f,1,0);
					enemy.colected=true;
				}
			}
		}
		
		// Update and draw effects:
		for (int i = effects.size - 1; i >= 0; i--) {
		    PooledEffect effect = effects.get(i);
		    effect.draw(batch, delta);
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}
		
		/*// Reset all effects:
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();*/
		batch.end();

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		batch.begin();

		// Draw Player bullets.
		TextureRegion bulletRegion = new TextureRegion(Assets.manager.get(Assets.ricebullet,Texture.class));
		float bulletWidth = bulletRegion.getRegionWidth() * scale/6;
		float bulletHeight = bulletRegion.getRegionHeight() * scale / 6;
		for (int i = 2, n = model.playerbullets.size; i < n; i += 5) {
			float x = model.playerbullets.get(i), y = model.playerbullets.get(i + 1);
			float angle = model.playerbullets.get(i + 2);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * bulletWidth * 0.65f;
			y -= vy * bulletWidth * 0.65f;
			x += vy * bulletHeight / 2;
			y += -vx * bulletHeight / 2;
			batch.draw(bulletRegion, x, y, 0, 0, bulletWidth, bulletHeight, 1, 1, angle);
		}
		
		// Draw enemy bullets.
		TextureRegion enemybulletRegion = new TextureRegion(Assets.manager.get(Assets.bulletRegion,Texture.class));
		float enemybulletWidth = enemybulletRegion.getRegionWidth() * scale/2;
		float enemybulletHeight = enemybulletRegion.getRegionHeight() * scale / 4;
		for (int i = 2, n = model.enemybullets.size; i < n; i += 5) {
			float x = model.enemybullets.get(i), y = model.enemybullets.get(i + 1);
			float angle = model.enemybullets.get(i + 2);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * enemybulletWidth * 0.65f;
			y -= vy * enemybulletWidth * 0.65f;
			x += vy * enemybulletHeight / 2;
			y += -vx * enemybulletHeight / 2;
			batch.draw(enemybulletRegion, x, y, 0, 0, enemybulletWidth, enemybulletHeight, 1, 1, angle);
		}

		// Draw hit markers.
		TextureRegion hitRegion = new TextureRegion(Assets.manager.get(Assets.hitRegion,Texture.class));
		Color color = batch.getColor().set(1, 1, 1, 1);
		float hitWidth = hitRegion.getRegionWidth() * scale;
		float hitHeight = hitRegion.getRegionWidth() * scale;
		for (int i = hits.size - 4; i >= 0; i -= 4) {
			float time = hits.get(i);
			float x = hits.get(i + 1);
			float y = hits.get(i + 2);
			float angle = hits.get(i + 3);
			color.a = time / bulletHitTime;
			batch.setColor(color);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x += vy * enemybulletHeight * 0.2f;
			y += -vx * enemybulletHeight * 0.2f;
			batch.draw(hitRegion, x - hitWidth / 2, y, hitWidth / 2, 0, hitWidth, hitHeight, 1, 1, angle);
		}
		batch.setColor(Color.WHITE);

		batch.end();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		ui.render();
	}

	public void resize (int width, int height) {
		viewport.update(width, height);
		camera.position.x = player.position.x;
		//camera.position.y = player.position.y + viewport.getWorldHeight() / 2 - cameraBottom;
		//mapRenderer.setView(camera);
		ui.resize(width, height);
	}

	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		touched=true;
		if(screenX < Gdx.graphics.getWidth() / 5f) { //move
			touchedmove=pointer;
			touchmoveorigin=screenY;
			touchmove=true;
		}
		if(screenX > Gdx.graphics.getWidth() / 5f) { //shoot
			touchedshoot = pointer;
		}
		return true;
	}

	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if(pointer==touchedmove) { //move
			touchedmove=-1;
		}
		if(pointer==touchedshoot) { //shoot
			touchedshoot = -1;
		}
		return true;
	}

	public boolean keyDown (int keycode) {
		switch (keycode) {
		case Keys.UP:
			if (player.hp == 0) return false;
			upPressed = true;
			return true;
		case Keys.DOWN:
			if (player.hp == 0) return false;
			downPressed = true;
			return true;
		case Keys.BACK:
			
		}
		return false;
	}

	public boolean keyUp (int keycode) {
		switch (keycode) {
		case Keys.UP:
			if (player.hp == 0) return false;
			upPressed = false;
			return true;
		case Keys.DOWN:
			if (player.hp == 0) return false;
			downPressed = false;
			return true;
		case Keys.BACK:
			if(!ui.gameoverTable.hasParent())ui.showQuit();
		}
		
			
		return false;
	}
	public void dispose(){
		batch.dispose();
		player.view.dispose();
		model.kuro.view.dispose();
		ui.dispose();
		propEffect.dispose();
		pnjpropEffect.dispose();
		explode.dispose();
		sondecolect.dispose();
	}
}
