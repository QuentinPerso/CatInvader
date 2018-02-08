package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Model.*;
import static quentin.jeu.cat.catinvader.Player.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.catinvader.Enemy.Type;
import quentin.jeu.cat.catinvader.Model.State;
import quentin.jeu.cat.ui.UI;
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
public class View extends InputAdapter {
	
	public static float bulletHitTime = 0.2f;
	private static float cameraWidth = 16, cameraHeight = 16, cameraMinX = 1, cameraSpeed = 5f;
	public float cameraShake = 12 * scale;
	public Model model;
	public Player player;
	public Pnj kuro;
	public OrthographicCamera camera;
	public ExtendViewport viewport;
	public SpriteBatch batch;
	private Sprite bg1, bg2,bg3,bg4;
	public UI ui;
	
	private ParticleEffectPool bombEffectPool;
	private Array<PooledEffect> effects = new Array<PooledEffect>();
	private ParticleEffect propEffect, pnjpropEffect, lasereffect,shieldeffect;
	
	private float animtimer;
	private float lzanimtimer;
	private float timer=0;
	private float ultimatimer1,ultimatimer2;
	
	public float shakeX;
	public float shakeY;
	
	public FloatArray hits = new FloatArray();
	
	public boolean upPressed, downPressed, touchmove;
	public int touchedshoot, touchedmove;
	public float touchmoveorigin;
	private Sound explode= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
	private Sound sondecolect= Gdx.audio.newSound(Gdx.files.internal("sounds/sonde.wav"));
	public int cost;
	public int activWeap=Save.gd.weapon1;
	private float power1, power2, power3;
	public static float t1=410, t2=600, t3=800;
	
	public View (Model model, int cost) {
		this.model = model;
		this.cost = cost;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);
		if(Save.gd.weapon2==-1) activWeap=Save.gd.weapon1;
		else if(Save.gd.weapon1==-1) activWeap=Save.gd.weapon2;
		else activWeap=Save.gd.weapon1;
		propEffect = new ParticleEffect();
		
		if(Save.gd.speed+Save.gd.acceleration<18){
			propEffect.load(Gdx.files.internal("effects/prop.p"), Gdx.files.internal("effects/"));
		}
		else if(Save.gd.speed+Save.gd.acceleration<37){
			propEffect.load(Gdx.files.internal("effects/prop2.p"), Gdx.files.internal("effects/"));
		}
		else{
			propEffect.load(Gdx.files.internal("effects/prop2.p"), Gdx.files.internal("effects/"));
		}
		
		if(model.kuro!=null) {
			pnjpropEffect = new ParticleEffect();
			pnjpropEffect.load(Gdx.files.internal("effects/prop.p"), Gdx.files.internal("effects/"));
		}
		
		lasereffect = new ParticleEffect();
		lasereffect.load(Gdx.files.internal("effects/laser.p"), Gdx.files.internal("effects/"));
		
		shieldeffect = new ParticleEffect();
		shieldeffect.load(Gdx.files.internal("effects/shield2.p"), Gdx.files.internal("effects/"));
		
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal("effects/"));
		bombEffectPool = new ParticleEffectPool(bombEffect, 10, 10);
		
		ui = new UI(this);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(ui, ui.stage, this));
		
		//background
		
		int bgchance=MathUtils.random(1,5);
		if(Save.gd.playerGo==CatGame.ANOMALY) bgchance=5;
		if(Save.gd.playerGo==CatGame.NORIS1 ) bgchance=2;
		if(Save.gd.playerGo==CatGame.NORIS2 ) bgchance=1;
		if(Save.gd.playerGo==CatGame.NORIS3 ) bgchance=2;
		if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn)
			bgchance=5;
		if(bgchance==1){
			bg1 =bg2=bg4  = new Sprite(Assets.manager.get(Assets.stars,Texture.class));
			bg3 = new Sprite(Assets.manager.get(Assets.galaxybg,Texture.class));
		}
		else if(bgchance==2){
			bg1 =bg2=bg4  = new Sprite(Assets.manager.get(Assets.stars,Texture.class));
			bg3 = new Sprite(Assets.manager.get(Assets.nebulabg,Texture.class));
		}
		else if(bgchance==3){
			bg1 = new Sprite(Assets.manager.get(Assets.stars,Texture.class));
			bg2 = new Sprite(Assets.manager.get(Assets.sunbg1,Texture.class));
			bg3 = new Sprite(Assets.manager.get(Assets.sunbg2,Texture.class));
			bg4 = new Sprite(Assets.manager.get(Assets.sunbg3,Texture.class));
		}
		else {
			bg1=bg2=bg3=bg4 = new Sprite(Assets.manager.get(Assets.stars,Texture.class));
		}
		bg1.setSize(18*16/9,18);
		bg2.setSize(18*16/9,18);
		bg3.setSize(18*16/9,18);
		bg4.setSize(18*16/9,18);
		
		
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
		player.view = new PlayerView(this);
		if(model.kuro!=null){
			kuro = model.kuro;
			kuro.view = new Pnjview(this, kuro);
		}
		bg1.setAlpha(1);
		bg2.setAlpha(1);
		bg3.setAlpha(1);
		bg4.setAlpha(1);
		Enemy.bhed=false;
		player.velocity.y=0;
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
		if(kuro!=null){
			kuro.view.update(delta);
		}
		for (Enemy enemy : model.enemies) {
			if (enemy.view == null) enemy.view = new EnemyView(this, enemy);
			enemy.view.update(delta);
		}
	}

	void updateInput (float delta) {
		if (player.hp <= 0) return;

		if (touchedshoot!=-1 && activWeap==CatGame.RG){
			player.view.shootRG();
		}
		if (touchedshoot!=-1 && activWeap==CatGame.LZ){
			player.view.shootLaser();
			model.shootlz=true;
		}
		else model.shootlz=false;
		
		if(upPressed){
			player.moveup(delta);
		}
		if(downPressed){
			player.movedown(delta);
		}
		else if(!upPressed && !upPressed && touchedmove==-1 && !player.view.move){
			player.velocity.y*= inertia;
		}
		if(kuro!=null)
		for(int i=0;i<model.enemies.size;i++){
			if(model.enemies.get(i).type!=Type.probes && kuro.state!=State.death && kuro.position.x-player.position.x<15)kuro.view.shoot();
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
			camera.position.x +=   Player.ScrollVelocityX*delta;//player.position.x+11;
			if(player.position.x<camera.position.x-11) player.maxVelocityX=9;
			else  player.maxVelocityX=Player.ScrollVelocityX;
			if(player.position.x>camera.position.x-10.9f) player.moveLeft(delta);
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
			if(touchedshoot!=-1 && player.shootRGTimer>0){
				// Create effect:
				//player.shootTimer=Player.shootDelay;
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(player.position.x+MathUtils.random(-5, 5), player.position.y+MathUtils.random(-5, 5));
				float[] color = new float[]{MathUtils.random(),MathUtils.random(),MathUtils.random(),MathUtils.random(),MathUtils.random(),MathUtils.random()};
				effect.getEmitters().get(0).getTint().setColors(color);
				effect.getEmitters().get(1).getTint().setColors(color);
				effects.add(effect);
				if(effects.size%5==0)explode.play(1,0.8f,0);
				player.exploded=true;
			}
		}
		
		if (kuro!=null) {
			//Pnj 2 camera
			if (kuro.hp > 0) {
				if (kuro.position.x < camera.position.x - 11)
					kuro.maxVelocityX = 9;
				else
					kuro.maxVelocityX = Player.ScrollVelocityX;
				if (kuro.position.x > camera.position.x - 8){
					kuro.velocity.x=0;
				}
			} else
				kuro.maxVelocityX *= 0.995f;
		}
			camera.update();
			batch.setProjectionMatrix(camera.combined);
	
			camera.position.add(-shakeX, -shakeY, 0);
			shakeX = 0;
			shakeY = 0;
		
	}

	public void render (float delta) {
		viewport.apply();
		if(Save.gd.playerGo==CatGame.ANOMALY || Save.gd.playerPos==CatGame.ANOMALY){
			if(player.position.x<=t1){
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				bg1.setAlpha(1-player.position.x/t1);
				bg2.setAlpha(1-player.position.x/t1);
				bg3.setAlpha(1-player.position.x/t1);
				bg4.setAlpha(1-player.position.x/t1);
			}
			else if(player.position.x<=t2){
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				bg1.setAlpha(0);
				bg2.setAlpha(0);
				bg3.setAlpha(0);
				bg4.setAlpha(0);
			}
			else if(player.position.x<=t3){
				float color=player.position.x/(t3-t2)-t2/(t3-t2);
				Gdx.gl.glClearColor(color, color, color, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
			else{
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
		}
		else{
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
		batch.begin();
		//draw background
		timer+=delta;
		if(timer>=5* 9*16/9+bg4.getWidth()+camera.viewportWidth/2) timer=0;
		bg1.setFlip(false, false);
		bg1.setPosition(camera.position.x-timer+-1* 9*16/9,-9);
		bg1.draw(batch);
		bg2.setPosition(camera.position.x-timer+1* 9*16/9,-9);
		bg2.draw(batch);
		bg3.setPosition(camera.position.x-timer+3* 9*16/9,-9);
		bg3.draw(batch);
		bg4.setPosition(camera.position.x-timer+5* 9*16/9,-9);
		bg4.draw(batch);
		bg1.setFlip(true, false);
		bg1.setPosition(camera.position.x-timer+7* 9*16/9,-9);
		bg1.draw(batch);
		
		//========================ENEMIES==============================//
		//probes
		Sprite probeR = new Sprite(Assets.manager.get(Assets.probe,Texture.class));
		probeR.rotate(20*timer);
		
		//probes
		Sprite metalR = new Sprite(Assets.manager.get(Assets.metal,Texture.class));
		metalR.rotate(50*timer);
		
		//Enemy ship 
		Sprite normalRegion, weakRegion,strongRegion,bomberRegion,bomb1Region,bomb2Region, bigcatReg = null;
		
		if(Save.gd.playerGo==CatGame.ANOMALY){ //go back in time
			if(player.position.x<t1){
				normalRegion  = new Sprite(Assets.manager.get(Assets.normal1,Texture.class));
				weakRegion    = new Sprite(Assets.manager.get(Assets.weak1,Texture.class));
				strongRegion  = new Sprite(Assets.manager.get(Assets.strong1,Texture.class));
				bomberRegion  = new Sprite(Assets.manager.get(Assets.bomber1,Texture.class));
				bomb1Region   = new Sprite(Assets.manager.get(Assets.bombe1,Texture.class));
				bomb2Region   = new Sprite(Assets.manager.get(Assets.bombe2,Texture.class));
			}
			else{//cat
				normalRegion  = new Sprite(Assets.manager.get(Assets.normal2,Texture.class));
				weakRegion    = new Sprite(Assets.manager.get(Assets.weak2,Texture.class));
				strongRegion  = new Sprite(Assets.manager.get(Assets.strong2,Texture.class));
				bomberRegion  = new Sprite(Assets.manager.get(Assets.bomber1,Texture.class));
				bomb1Region   = new Sprite(Assets.manager.get(Assets.bombe1,Texture.class));
				bomb2Region   = new Sprite(Assets.manager.get(Assets.bombe2,Texture.class));
				bigcatReg     = new Sprite(Assets.manager.get(Assets.mship,Texture.class));
			}
		}
		else if(Save.gd.eventendcat){//alien
			normalRegion  = new Sprite(Assets.manager.get(Assets.normal1,Texture.class));
			weakRegion    = new Sprite(Assets.manager.get(Assets.weak1,Texture.class));
			strongRegion  = new Sprite(Assets.manager.get(Assets.strong1,Texture.class));
			bomberRegion  = new Sprite(Assets.manager.get(Assets.bomber1,Texture.class));
			bomb1Region   = new Sprite(Assets.manager.get(Assets.bombe1,Texture.class));
			bomb2Region   = new Sprite(Assets.manager.get(Assets.bombe2,Texture.class));
		}
		else{//cat
			normalRegion  = new Sprite(Assets.manager.get(Assets.normal2,Texture.class));
			weakRegion    = new Sprite(Assets.manager.get(Assets.weak2,Texture.class));
			strongRegion  = new Sprite(Assets.manager.get(Assets.strong2,Texture.class));
			bomberRegion  = new Sprite(Assets.manager.get(Assets.bomber1,Texture.class));
			bomb1Region   = new Sprite(Assets.manager.get(Assets.bombe1,Texture.class));
			bomb2Region   = new Sprite(Assets.manager.get(Assets.bombe2,Texture.class));
			bigcatReg     = new Sprite(Assets.manager.get(Assets.mship,Texture.class));
		}
		//shield
		Sprite shieldregion = new Sprite(Assets.manager.get(Assets.shield,Texture.class));
		//asteroids
		Sprite asteroidRegion0     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion0.rotate(180*timer);
		Sprite asteroidRegion1     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion1.rotate(180*timer);
		Sprite asteroidRegion2     = new Sprite(Assets.manager.get(Assets.asteroid0,Texture.class));
		asteroidRegion2.rotate(180*timer);
		
		
		animtimer+=delta;
		if(animtimer>10) animtimer=0;
		for (Enemy enemy : model.enemies) {
			power1=enemy.rgpower1;
			power2=enemy.rgpower2;
			power3=enemy.rgpower3;
			float x = enemy.position.x-enemy.rect.width*0.25f;
			float y = enemy.position.y-enemy.rect.height*0.25f;
			float width  = enemy.rect.width*1.5f;
			float height = enemy.rect.height*1.5f;
			if(Save.gd.playerGo==CatGame.NORIS3 && enemy.type==Type.weak && Enemy.bhed==true){
				Sprite hole = new Sprite(Assets.manager.get(Assets.star5,Texture.class));
				hole.setAlpha((10-enemy.bhtimer)/10);
				hole.setSize(1.8f*(10-enemy.bhtimer), 1.8f*(10-enemy.bhtimer));
				hole.setPosition(enemy.position.x-1.8f*(10-enemy.bhtimer)/2, -1.8f*(10-enemy.bhtimer)/2);
				hole.setOrigin(1.8f*(10-enemy.bhtimer)/2, 1.8f*(10-enemy.bhtimer)/2);
				hole.rotate(100*timer);
				Sprite hole1 = new Sprite(Assets.manager.get(Assets.star51,Texture.class));
				hole1.setAlpha((10-enemy.bhtimer)/10);
				hole1.setSize(2*(10-enemy.bhtimer), 2*(10-enemy.bhtimer));
				hole1.setPosition(enemy.position.x-(10-enemy.bhtimer), -(10-enemy.bhtimer));
				hole1.setOrigin((10-enemy.bhtimer), (10-enemy.bhtimer));
				hole1.rotate(160*timer);
				bg1.setAlpha(enemy.bhtimer/10);
				bg2.setAlpha(enemy.bhtimer/10);
				bg3.setAlpha(enemy.bhtimer/10);
				bg4.setAlpha(enemy.bhtimer/10);
				hole .draw(batch);
				hole1.draw(batch);
				if(enemy.bhtimer>0){
					weakRegion.setSize(width, height);
					enemy.position.y=-enemy.rect.height*0.25f;
					enemy.bombTimer=2;
					for(int i =0;i<10; i++){
						float angle = 360/10*i;
						float cos = MathUtils.cosDeg(angle+ 300*(10-enemy.bhtimer)), sin = MathUtils.sinDeg(angle+ 300*(10-enemy.bhtimer));
						weakRegion.setPosition(x-enemy.rect.width*0.25f+(10-enemy.bhtimer)*cos, sin*(10-enemy.bhtimer)-enemy.rect.height/2);
						weakRegion.draw(batch);
					}
				}
			}
			if (enemy.type == Type.bombes){
				batch.draw(bomb1Region, x, y,width,height);
				if ((int)(animtimer / (flashTime*2)) % 3 != 0){
					batch.draw(bomb2Region, x, y,width,height);
				}
			}
			else if (enemy.type==Type.ultima) {
				Sprite ultimaR = new Sprite(Assets.manager.get(Assets.ultima,Texture.class));
				Sprite protR1  = new Sprite(Assets.manager.get(Assets.prot,Texture.class));
				Sprite protR2  = new Sprite(Assets.manager.get(Assets.prot,Texture.class));
				Sprite genR1   = new Sprite(Assets.manager.get(Assets.gen,Texture.class));
				Sprite genR2   = new Sprite(Assets.manager.get(Assets.gen,Texture.class));
				
				power1=enemy.rgpower1;
				power2=enemy.rgpower2;
				power3=enemy.rgpower3;
				
				ultimatimer1+= 3*(1-(50-enemy.gen1hp)/50+Math.max((enemy.repairtimer1-4.85)/5, 0));
				ultimatimer2+=-3*(1-(50-enemy.gen2hp)/50+Math.max((enemy.repairtimer2-4.85)/5, 0));
				if(ultimatimer1>=360)ultimatimer1=0;
				if(ultimatimer2>=360)ultimatimer2=0;
				
				float bodyh = enemy.ultih;
				float bodyw  = enemy.ultiw;
				float bodyx = enemy.position.x+enemy.rect.width/2-bodyw*0.41f;
				float bodyy = enemy.position.y+enemy.rect.height/2-bodyh/2;
				
				float colorbody=Math.max(0, 1-(20 -enemy.hp)/20);
				ultimaR.setColor(1f, colorbody, colorbody, 1);
				ultimaR.setSize(bodyw, bodyh);
				ultimaR.setPosition(bodyx, bodyy);
				ultimaR.draw(batch);
				
				genR1.setSize(0.19f*bodyh, 0.19f*bodyh);
				genR1.setPosition(bodyx+enemy.ultiw*0.773f-genR1.getWidth()/2
						,enemy.position.y+enemy.rect.height/2+enemy.ultih*0.148f-genR1.getHeight()/2);
				genR1.setOriginCenter();
				genR1.rotate(ultimatimer1);
				if (enemy.gen1hp<=0){
					if(enemy.repairtimer1<4)
						genR1.setColor(1f, 0, 0, 1);
					else if( (int)(animtimer / 0.07f) % 3!= 0)
						genR1.setColor(1f, 0, 0, 1);
				}
				genR1.draw(batch);
				
				genR2.setSize(0.19f*bodyh, 0.19f*bodyh);
				genR2.setPosition(bodyx+enemy.ultiw*0.773f-genR2.getWidth()/2
						,enemy.position.y+enemy.rect.height/2-enemy.ultih*0.148f-genR2.getHeight()/2);
				genR2.setOriginCenter();
				genR2.rotate(ultimatimer2);
				if (enemy.gen2hp<=0){
					if(enemy.repairtimer2<4)
						genR2.setColor(0, 0, 1, 1);
					else if( (int)(animtimer / 0.07f) % 3!= 0)
						genR2.setColor(0, 0, 1, 1);
				}
				genR2.draw(batch);
				
				
				float colorprot1=Math.max(0, 1-(100 -enemy.prot1hp)/100);
				protR1.setColor(1f, colorprot1, colorprot1, 1);
				protR1.setSize(0.5f*bodyw, 0.2f*bodyh);
				protR1.setPosition(bodyx+enemy.ultiw*0.816f-protR1.getWidth()/2
						, enemy.position.y+enemy.rect.height/2+enemy.ultih*0.148f-protR1.getHeight()/2);
				protR1.setOrigin(enemy.prot1.width, 0);
				if(enemy.prot1hp>0){
					protR1.draw(batch);
				}
				else if(enemy.prot1hp<=0 && enemy.destructiontimer2>0){
					PooledEffect effect = bombEffectPool.obtain();
					effect.setPosition(bodyx+enemy.ultiw*0.773f,enemy.position.y+enemy.rect.height/2+enemy.ultih*0.148f);
					if(enemy.destructiontimer2==10){
						if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
						effects.add(effect);
					}
					if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
					enemy.destructiontimer2-=4*delta;
					protR1.setPosition(enemy.prot1.x+(enemy.destructiontimer2-10)*-3, enemy.prot1.y);
					protR1.rotate((enemy.destructiontimer2-10)*-10);
					protR1.draw(batch);
				}
				
				float colorprot2=Math.max(0, 1-(100 -enemy.prot2hp)/100);
				protR2.setColor(1f, colorprot2, colorprot2, 1);
				protR2.setSize(0.5f*bodyw, 0.2f*bodyh);
				protR2.setPosition(bodyx+enemy.ultiw*0.816f-protR2.getWidth()/2
						, enemy.position.y+enemy.rect.height/2-enemy.ultih*0.148f-protR2.getHeight()/2);
				protR2.setOrigin(enemy.prot1.width, 0);
				if(enemy.prot2hp>0){
					protR2.draw(batch);
				}
				else if(enemy.prot2hp<=0 && enemy.destructiontimer3>0){
					PooledEffect effect = bombEffectPool.obtain();
					effect.setPosition(bodyx+enemy.ultiw*0.773f,enemy.position.y+enemy.rect.height/2-enemy.ultih*0.148f);
					if(enemy.destructiontimer3==10){
						if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
						effects.add(effect);
					}
					enemy.destructiontimer3-=2*delta;
					protR2.rotate((enemy.destructiontimer3-10)*14);
					protR2.setPosition(enemy.prot2.x+(enemy.destructiontimer3-10)*-4, enemy.prot2.y);
					protR2.draw(batch);
				}
				
			}
			else if (enemy.type == Type.bomber){
				float colorvar;
				if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn){
					colorvar=Math.max(0, 1-(200 -enemy.hp)/200);
				}
				else if(Save.gd.playerGo==CatGame.NORIS1)
					colorvar=Math.max(0, 1-(270 -enemy.hp)/270);
				else
					colorvar=Math.max(0, 1-(enemy.hpBomber -enemy.hp)/enemy.hpBomber);
				
				bomberRegion.setColor(1f, colorvar, colorvar, 1);
				bomberRegion.setPosition(x, y);
				bomberRegion.setSize(width, height);
				bomberRegion.draw(batch);
			}
			else if (enemy.type == Type.normal){
				float colorvar=Math.max(0, 1-(enemy.hpNormal -enemy.hp)/enemy.hpNormal);
				normalRegion.setColor(1f, colorvar, colorvar, 1);
				normalRegion.setPosition(x, y);
				normalRegion.setSize(width, height);
				normalRegion.draw(batch);
			}
			else if (enemy.type == Type.strong){
				float colorvar;
				if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro){
					colorvar=Math.max(0, 1-(enemy.hpkuro -enemy.hp)/enemy.hpkuro);
				}
				else
					colorvar=Math.max(0, 1-(enemy.hpStrong -enemy.hp)/enemy.hpStrong);
				
				strongRegion.setColor(1f, colorvar, colorvar, 1);
				strongRegion.setPosition(x, y);
				strongRegion.setSize(width, height);
				strongRegion.draw(batch);
			}
			else if (enemy.type == Type.weak){
				float colorvar;
				colorvar=Math.max(0, 1-(enemy.hpWeak -enemy.hp)/enemy.hpWeak);
				weakRegion.setColor(1f, colorvar, colorvar, 1);
				weakRegion.setSize(width, height);
				weakRegion.setPosition(x, y);
				weakRegion.draw(batch);
			}
			else if (enemy.type == Type.big){
				float colorvar=Math.max(0, 1-(enemy.hpBigCat -enemy.hp)/enemy.hpBigCat);
				if(Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatcatious){
					colorvar=Math.max(0, 1-(enemy.hpcatious -enemy.hp)/enemy.hpcatious);
				}
				
				bigcatReg.setFlip(true, false);
				bigcatReg.setColor(1f, colorvar, colorvar, 1);
				bigcatReg.setPosition(x, y);
				bigcatReg.setSize(width, height);
				bigcatReg.draw(batch);
			}
			else if ( enemy.type == Type.asteroidsmall){
				float colorvar=Math.max(0, 1-(Enemy.hpSmall -enemy.hp)/Enemy.hpSmall);
				asteroidRegion0.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion0.setPosition(x-width/6, y-width/6);
				asteroidRegion0.setSize(width*4/3, height*4/3);
				asteroidRegion0.setOriginCenter();
				asteroidRegion0.draw(batch);
			}
			else if (enemy.type == Type.asteroidnormal ){
				float colorvar=Math.max(0, 1-(Enemy.hpNormA -enemy.hp)/(Enemy.hpNormA));
				asteroidRegion1.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion1.setPosition(x-width/6, y-width/6);
				asteroidRegion1.setSize(width*4/3, height*4/3);
				asteroidRegion1.setOriginCenter();
				asteroidRegion1.draw(batch);
			}
			else if (enemy.type == Type.asteroidbig){
				float colorvar=Math.max(0, 1-(Enemy.hpBig -enemy.hp)/(Enemy.hpBig));
				asteroidRegion2.setColor(1f, colorvar, colorvar, 1);
				asteroidRegion2.setPosition(x-width/6, y-width/6);
				asteroidRegion2.setSize(width*4/3, height*4/3);
				asteroidRegion2.setOriginCenter();
				asteroidRegion2.draw(batch);
			}
			else if (enemy.type == Type.probes){
				float colorvar=Math.max(0, 1-(10 -enemy.hp)/10);
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
			else if (enemy.type == Type.metal){
				float colorvar=Math.max(0, 1-(2 -enemy.hp)/2);
				width  = enemy.rect.width;
				height = enemy.rect.height;
				metalR.setPosition(x, y);
				metalR.setSize(width,height);
				metalR.setOriginCenter();
				if(enemy.state!=State.death){
					metalR.setSize(width+0.4f,height+0.4f);
					metalR.setColor(1f, colorvar, colorvar, 1);
				}
				else if(enemy.deathTimer*2>0) {
					metalR.setColor(1f, 1, 1, (enemy.deathTimer*2));
					metalR.setSize((width+0.4f)/(enemy.deathTimer*2),(height+0.4f)/(enemy.deathTimer*2));
				}
				metalR.draw(batch);
			}
			
			if (enemy.state==State.death && enemy.position.x>camera.position.x-20 
					&& enemy.position.x<camera.position.x+20 
					&& enemy.position.y<camera.position.y+10
					&& enemy.position.y>camera.position.y-10){
				// Create effect:
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(x+width/2, y+height/2);
				if( enemy.exploded==false){
					effects.add(effect);
					if(Save.gd.soundEnabled)explode.play(1,MathUtils.random(0.4f, 1)*0.4f,0);
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
		
		if (kuro!=null) {
			
			//draw kuro
			pnjpropEffect.setPosition(kuro.position.x, kuro.position.y + 0.1f);
			pnjpropEffect.getEmitters().get(0).getAngle()
					.setHighMax(180 + kuro.velocity.y * 2 + 20);
			pnjpropEffect.getEmitters().get(0).getAngle()
					.setLowMax(180 + kuro.velocity.y * 2 - 20);
			pnjpropEffect.getEmitters().get(0).getAngle()
					.setHighMin(180 + kuro.velocity.y * 2);
			pnjpropEffect.getEmitters().get(0).getAngle()
					.setLowMin(180 + kuro.velocity.y * 2);
			pnjpropEffect.draw(batch, delta);
			if (((kuro.collisionTimer < 0 || (int) (kuro.collisionTimer / flashTime) % 3 != 0))
					|| kuro.hp == 0) {
				if (kuro.hp <= 0) {
					// Create effect:
					PooledEffect effect = bombEffectPool.obtain();
					effect.setPosition(kuro.position.x + kuro.rect.width / 2,
							kuro.position.y + kuro.rect.height / 2);
					if (kuro.exploded == false) {
						effects.add(effect);
						if (Save.gd.soundEnabled)
							explode.play(1, MathUtils.random(0.4f, 1) * 0.4f, 0);
						kuro.exploded = true;
					}
				}
				kuro.view.render(batch);
			}
		}
		// Draw player.
		propEffect.setPosition(player.position.x, player.position.y+0.16f);
		propEffect.getEmitters().get(0).getAngle().setHighMax(180+player.velocity.y*2+20);
		propEffect.getEmitters().get(0).getAngle().setLowMax(180+player.velocity.y*2-20);
		propEffect.getEmitters().get(0).getAngle().setHighMin(180+player.velocity.y*2);
		propEffect.getEmitters().get(0).getAngle().setLowMin(180+player.velocity.y*2);
		if(Save.gd.speed+Save.gd.acceleration>37){
			if ((int)(animtimer / (flashTime*2)) % 3 != 0){
				float color3=1.3f-Math.abs(player.velocity.y)/Save.gd.speed;
				float color2=2.4f-2*Math.abs(player.velocity.y)/Save.gd.speed;
				float color1=3.5f-3.1f*Math.abs(player.velocity.y)/Save.gd.speed;
				float[] color = new float[]{color1,color2,color3  ,0,0,0};
				propEffect.getEmitters().get(0).getTint().setColors(color);
			}
		}
		propEffect.draw(batch, delta);
		if ((player.collisionTimer < 0 || (int)(player.collisionTimer / flashTime) % 3 != 0) || player.hp<=0){
			if(player.hp<=0){
				// Create effect:
				PooledEffect effect = bombEffectPool.obtain();
				effect.setPosition(player.position.x+player.rect.width/2, player.position.y+player.rect.height/2);
				if( player.exploded==false){
					effects.add(effect);
					explode.play(1,0.25f,0);
					player.exploded=true;
				}
			}
			player.view.render(batch);
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
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		batch.begin();
		
		
		// Draw Player RGbullets.
		TextureRegion bulletRegion = new TextureRegion(Assets.manager.get(Assets.ricebullet,Texture.class));
		float bulletWidth = bulletRegion.getRegionWidth() * scale/8;
		float bulletHeight = bulletRegion.getRegionHeight() * scale / 8;
		for (int i = 2, n = model.playerRGbullets.size; i < n; i += 5) {
			float x = model.playerRGbullets.get(i), y = model.playerRGbullets.get(i + 1);
			float angle = model.playerRGbullets.get(i + 2);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * bulletWidth * 0.65f;
			y -= vy * bulletWidth * 0.65f;
			x += vy * bulletHeight / 2;
			y += -vx * bulletHeight / 2;
			batch.draw(bulletRegion, x, y, 0, 0, bulletWidth, bulletHeight, 1, 1, angle);
		}
		// Draw Player SM.
		TextureRegion missilRegion = new TextureRegion(Assets.manager.get(Assets.sushiMissil,Texture.class));
		float missilWidth = bulletRegion.getRegionWidth() * scale/6;
		float missilHeight = bulletRegion.getRegionHeight() * scale / 6;
		for (int i = 2, n = model.playerSM.size; i < n; i += 5) {
			float x = model.playerSM.get(i), y = model.playerSM.get(i + 1);
			float angle = model.playerSM.get(i + 2);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * missilWidth * 0.65f;
			y -= vy * missilWidth * 0.65f;
			x += vy * missilHeight / 2;
			y += -vx * missilHeight / 2;
			batch.draw(missilRegion, x, y, 0, 0, missilWidth, missilHeight, 1, 1, player.view.weapangle);
		}
		// Draw Player SMbullets.
		for (int i = 2, n = model.playerSMbullets.size; i < n; i += 5) {
			float x = model.playerSMbullets.get(i), y = model.playerSMbullets.get(i + 1);
			float angle = model.playerSMbullets.get(i + 2);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * bulletWidth * 0.65f;
			y -= vy * bulletWidth * 0.65f;
			x += vy * bulletHeight / 2;
			y += -vx * bulletHeight / 2;
			batch.draw(bulletRegion, x, y, 0, 0, bulletWidth, bulletHeight, 1, 1, angle);
		}
		
		
		
		
		//draw player laser test
		Sprite laserRegion10 = new Sprite(Assets.manager.get(Assets.laser10,Texture.class));
		Sprite laserRegion11 = new Sprite(Assets.manager.get(Assets.laser11,Texture.class));
		Sprite laserRegion00 = new Sprite(Assets.manager.get(Assets.laser00,Texture.class));
		Sprite laserRegion01 = new Sprite(Assets.manager.get(Assets.laser01,Texture.class));
		Sprite laserRegion20 = new Sprite(Assets.manager.get(Assets.laser20,Texture.class));
		Sprite laserRegion21 = new Sprite(Assets.manager.get(Assets.laser21,Texture.class));
		Sprite laserRegion3  = new Sprite(Assets.manager.get(Assets.laser3 ,Texture.class));
		
		float laserWidth  = bulletRegion.getRegionWidth() * scale/2;
		float laserHeight = bulletRegion.getRegionWidth() * scale/2*player.laserpower*3;
		
		float laserangle = player.view.weapangle;
		float laservx = MathUtils.cosDeg(laserangle);
		float laservy = MathUtils.sinDeg(laserangle);
		float laseroffset=-0.76f;
		
		float effectconvx=player.rect.x+player.rect.width+0.19f+laserWidth*(1+laseroffset)*laservx;
		float effectconvy=player.rect.y                        +laserWidth*(1+laseroffset)*laservy;
		
		lasereffect.setPosition(effectconvx, effectconvy);
		float[] colorlzef1;
		float colorswitch=0.35f;
		float r=  player.laserpower/(1-colorswitch)-colorswitch/(1-colorswitch);
		float b1= -player.laserpower/(1-colorswitch)+1/(1-colorswitch);
		float b2 = player.laserpower/colorswitch;
		float g = 1-player.laserpower/colorswitch;
		float fadeOut=player.laserpower/(player.laserminpower+0.05f); //0.05 = power threshold fade start

		if(player.laserpower>colorswitch) colorlzef1 = new float[]{r,0,b1 ,r,0,b1};
		else if(player.laserpower>player.laserminpower+0.05f) colorlzef1 = new float[]{0,g,b2,0,g,b2};
		else colorlzef1 = new float[]{0,g*fadeOut,b2*fadeOut ,0,g*fadeOut,b2*fadeOut};
		lasereffect.update(1.25f*delta);
		
		float shootposx=player.rect.x+player.rect.width+0.19f;
		
		if(activWeap==CatGame.LZ && touchedshoot!=-1){
			float laserEnd =  Math.max(Math.abs(player.laserendx), Math.abs(player.laserendy) ); 
			lzanimtimer+=0.2f;
			if(lzanimtimer>=laserWidth/2)lzanimtimer=-laserWidth/2;
			for(int i = 1; i < 50; i ++){
				float x=player.rect.x+player.rect.width+0.19f+laserWidth*(i+laseroffset)*laservx;
				float y=player.rect.y                        +laserWidth*(i+laseroffset)*laservy;
				x -=  laservx * laserWidth  /2;
				y -=  laservy * laserWidth  /2;
				x +=  laservy * laserHeight /2;
				y += -laservx * laserHeight /2;
				
				
				if( (x<laserEnd && shootposx<player.laserendx-0.2f ) || ( x>laserEnd && shootposx>player.laserendx+0.2f )
						 && y<laserEnd){
					if(i==1){
						if(player.laserpower>colorswitch) 
							batch.setColor(r,0,b1,1);
						else batch.setColor(0,g,b2,1);
						batch.draw(laserRegion00, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
						batch.setColor(1,1,1,1);
						batch.draw(laserRegion01, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
					}
					else{
						//batch.setColor(player.laserpower,0,1-player.laserpower,1);
						if(player.laserpower>colorswitch) batch.setColor(r,0,b1,1);
						else batch.setColor(0,g,b2,1);
						batch.draw(laserRegion10, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
						if(i==2){
							batch.setColor(1,1,1,(lzanimtimer+laserWidth/2)*2/laserWidth);
							batch.draw(laserRegion3 , x+lzanimtimer*laservx, y+lzanimtimer*laservy, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
							batch.setColor(1,1,1,1);
							batch.draw(laserRegion11, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
						}
						else{
							batch.setColor(1,1,1,1);
							batch.draw(laserRegion3 , x+lzanimtimer*laservx, y+lzanimtimer*laservy, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
							batch.draw(laserRegion11, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
						}
					}
				}
				else if(( x<laserEnd+laserWidth*laservx && shootposx<player.laserendx-0.2f ) || 
						( x>laserEnd+laserWidth*laservx && shootposx>player.laserendx+0.2f )
								 && y<laserEnd+laserWidth*laservy){
					x=player.rect.x+player.rect.width+0.19f+laserWidth*(i+laseroffset)*laservx;
					y=player.rect.y                        +laserWidth*(i+laseroffset)*laservy;
					x -=  laservx * laserWidth  /2;
					y -=  laservy * laserWidth  /2;
					x +=  laservy * laserHeight /2;
					y += -laservx * laserHeight /2;
					if(player.laserpower>colorswitch) batch.setColor(r,0,b1,1);
					else batch.setColor(0,g,b2,1);
					batch.draw(laserRegion20, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
					batch.setColor(1,1,1,1);
					batch.draw(laserRegion21, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
				}
			}
			
			lasereffect.getEmitters().get(0).getTint().setColors(colorlzef1);
			lasereffect.getEmitters().get(0).getTransparency().setRelative(true);
			if(player.laserpower>0) lasereffect.draw(batch);
		}
		
		// Draw Enemy bullets.
		TextureRegion enemybulletRegion;
		float enemybulletWidth,enemybulletHeight;
		if(Save.gd.playerGo==CatGame.ANOMALY){
			if(player.position.x>=t1){
				enemybulletRegion= new TextureRegion(Assets.manager.get(Assets.ricebullet,Texture.class));
				enemybulletWidth = enemybulletRegion.getRegionWidth() * scale/6;
				enemybulletHeight = enemybulletRegion.getRegionHeight() * scale / 6;
			}
			else{
				enemybulletRegion= new TextureRegion(Assets.manager.get(Assets.bulletRegion,Texture.class));
				enemybulletWidth = enemybulletRegion.getRegionWidth() * scale/3;
				enemybulletHeight = enemybulletRegion.getRegionHeight() * scale /5;
			}
		}
		else if(!Save.gd.eventendcat){
			enemybulletRegion= new TextureRegion(Assets.manager.get(Assets.ricebullet,Texture.class));
			enemybulletWidth = enemybulletRegion.getRegionWidth() * scale/6;
			enemybulletHeight = enemybulletRegion.getRegionHeight() * scale / 6;
		}
		else{
			enemybulletRegion= new TextureRegion(Assets.manager.get(Assets.bulletRegion,Texture.class));
			enemybulletWidth = enemybulletRegion.getRegionWidth() * scale/3;
			enemybulletHeight = enemybulletRegion.getRegionHeight() * scale /5;
		}
		for (int i = 2, n = model.enemybullets.size; i < n; i += 6) {
			float x = model.enemybullets.get(i), y = model.enemybullets.get(i + 1);
			float angle = model.enemybullets.get(i + 2);
			float power = model.enemybullets.get(i + 3);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * enemybulletWidth * 0.65f;
			y -= vy * enemybulletWidth * 0.65f;
			x += vy * enemybulletHeight / 2;
			y += -vx * enemybulletHeight / 2;
			if(!Save.gd.eventendcat || (Save.gd.playerGo==CatGame.ANOMALY && player.position.x>=t1)){
				batch.draw(enemybulletRegion, x, y, 0, 0, enemybulletWidth, enemybulletHeight, 1, 1, angle);
			}
			else{
				if(power==power1)
					batch.setColor(0,1,0,1);
				else if(power==power2)
					batch.setColor(0,0,1,1);
				else if(power==power3)
					batch.setColor(1,0,0,1);
				batch.draw(enemybulletRegion, x, y, 0, 0, enemybulletWidth, enemybulletHeight, 1, 1, angle);
				batch.setColor(1,1,1,0.7f);
				batch.draw(enemybulletRegion, x, y, 0, 0, enemybulletWidth, enemybulletHeight, 1, 1, angle);
				batch.setColor(1,1,1,1);
			}
		}
		
		// Draw Enemy SM.
		if(Save.gd.playerGo==CatGame.ANOMALY){
			if(player.position.x<t1){
				missilRegion = new TextureRegion(Assets.manager.get(Assets.sushiMissil2,Texture.class));
			}
		}
		else if(Save.gd.eventendcat){
			missilRegion = new TextureRegion(Assets.manager.get(Assets.sushiMissil2,Texture.class));
		}
		missilWidth = bulletRegion.getRegionWidth() * scale/4;
		missilHeight = bulletRegion.getRegionHeight() * scale / 4;
		for (int i = 2, n = model.enemySM.size; i < n; i += 6) {
			float x = model.enemySM.get(i), y = model.enemySM.get(i + 1);
			float angle = model.enemySM.get(i + 2);
			float power = model.enemySM.get(i + 3);
			float vx = MathUtils.cosDeg(angle);
			float vy = MathUtils.sinDeg(angle);
			// Adjust position so bullet region is drawn with the bullet position in the center of the fireball.
			x -= vx * missilWidth * 0.65f;
			y -= vy * missilWidth * 0.65f;
			x += vy * missilHeight / 2;
			y += -vx * missilHeight / 2;
			if(!Save.gd.eventendcat || (Save.gd.playerGo==CatGame.ANOMALY && player.position.x>=t1)){
				batch.draw(missilRegion, x, y, 0, 0, missilWidth, missilHeight, 1, 1, angle);
			}
			else{
				if(power==power1)
					batch.setColor(0,1,0,1);
				else if(power==power2)
					batch.setColor(0,0,1,1);
				else if(power==power3)
					batch.setColor(1,0,0,1);
				batch.draw(missilRegion, x, y, 0, 0, missilWidth, missilHeight, 1, 1, angle);
				batch.setColor(1,1,1,0.7f);
				batch.draw(missilRegion, x, y, 0, 0, missilWidth, missilHeight, 1, 1, angle);
				batch.setColor(1,1,1,1);
			}
		}
		
		
		Sprite laserdot  = new Sprite(Assets.manager.get(Assets.particle ,Texture.class));
		float laserdotsize= 0.7f;
		laserHeight = 1f;
		for (Enemy enemy : model.enemies) {
			if(enemy.laserpower==enemy.laserpower1)
				{batch.setColor(0,1,0,1);laserHeight = 1f;}
			else if(enemy.laserpower==enemy.laserpower2)
				{batch.setColor(0,0,1,1);laserHeight = 1.3f;laserdotsize= 0.7f*1.3f;}
			else if(enemy.laserpower==enemy.laserpower3)
				{batch.setColor(1,0,0,1);laserHeight = 1.6f;laserdotsize= 0.7f*1.6f;}
			if(enemy.type==Type.cosmicray)
				batch.setColor(MathUtils.random(1),MathUtils.random(1),MathUtils.random(1),1);
			if(enemy.shootinglz){
				float x = enemy.laserstartx, y = enemy.laserstarty;
				float laserlenght = (float) Math.sqrt((enemy.laserendx-x) * (enemy.laserendx-x)+(enemy.laserendy-y) * (enemy.laserendy-y));
				float angle = enemy.shootlzangle;
				float vx = MathUtils.cosDeg(angle);
				float vy = MathUtils.sinDeg(angle);
				x += vy * laserHeight / 2;
				y += -vx * laserHeight / 2;
				batch.draw(laserRegion10, x, y, 0, 0, laserlenght, laserHeight, 1, 1, enemy.shootlzangle);
				if(Save.gd.playerGo==CatGame.NORIS3){
					float x1 = enemy.laserstartx, y1 = enemy.laserstarty;
					float vx1 = MathUtils.cosDeg(angle+90);
					float vy1 = MathUtils.sinDeg(angle+90);
					x1 += vy1 * laserHeight / 2;
					y1 += -vx1 * laserHeight / 2;
					batch.draw(laserRegion10, x1, y1, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+90);
					float x2 = enemy.laserstartx, y2 = enemy.laserstarty;
					float vx2 = MathUtils.cosDeg(angle+180);
					float vy2 = MathUtils.sinDeg(angle+180);
					x2 += vy2 * laserHeight / 2;
					y2 += -vx2 * laserHeight / 2;
					batch.draw(laserRegion10, x2, y2, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+180);
					float x3 = enemy.laserstartx, y3 = enemy.laserstarty;
					float vx3 = MathUtils.cosDeg(angle+270);
					float vy3 = MathUtils.sinDeg(angle+270);
					x3 += vy3 * laserHeight / 2;
					y3 += -vx3 * laserHeight / 2;
					batch.draw(laserRegion10, x3, y3, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+270);
				}
				batch.draw(laserdot, enemy.laserstartx-laserdotsize/2,
						enemy.laserstarty-laserdotsize/2
						, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx-laserdotsize/2,enemy.laserendy-laserdotsize/2, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.setColor(1,1,1,1);
				batch.draw(laserRegion11, x, y, 0, 0, laserlenght, laserHeight, 1, 1, enemy.shootlzangle);
				if(Save.gd.playerGo==CatGame.NORIS3){
					float x1 = enemy.laserstartx, y1 = enemy.laserstarty;
					float vx1 = MathUtils.cosDeg(angle+90);
					float vy1 = MathUtils.sinDeg(angle+90);
					x1 += vy1 * laserHeight / 2;
					y1 += -vx1 * laserHeight / 2;
					batch.draw(laserRegion11, x1, y1, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+90);
					float x2 = enemy.laserstartx, y2 = enemy.laserstarty;
					float vx2 = MathUtils.cosDeg(angle+180);
					float vy2 = MathUtils.sinDeg(angle+180);
					x2 += vy2 * laserHeight / 2;
					y2 += -vx2 * laserHeight / 2;
					batch.draw(laserRegion11, x2, y2, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+180);
					float x3 = enemy.laserstartx, y3 = enemy.laserstarty;
					float vx3 = MathUtils.cosDeg(angle+270);
					float vy3 = MathUtils.sinDeg(angle+270);
					x3 += vy3 * laserHeight / 2;
					y3 += -vx3 * laserHeight / 2;
					batch.draw(laserRegion11, x3, y3, 0, 0, 100, laserHeight, 1, 1, enemy.shootlzangle+270);
				}
				batch.draw(laserdot, enemy.laserstartx-laserdotsize/4,enemy.laserstarty-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx-laserdotsize/4,enemy.laserendy-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
			}
			////laser warning
			else if(enemy.lasertimer>enemy.lasershoottime*4/5f && (int)(animtimer / (flashTime)) % 3 != 0){
				if((Save.gd.playerGo==CatGame.ANOMALY && player.position.x<t1 ) || (Save.gd.eventendcat && Save.gd.playerGo!=CatGame.ANOMALY)){
					if(enemy.type!=Type.bombes || Save.gd.playerGo==CatGame.NORIS3){
						if(enemy.laserpower==enemy.laserpower1)
							{batch.setColor(0,1,0,1);laserHeight = 1f;}
						else if(enemy.laserpower==enemy.laserpower2)
							{batch.setColor(0,0,1,1);laserHeight = 1.3f;}
						else if(enemy.laserpower==enemy.laserpower3)
							{batch.setColor(1,0,0,1);laserHeight = 1.6f;}
						batch.draw(laserdot, enemy.laserstartx-laserdotsize/2,
								enemy.laserstarty-laserdotsize/2
								, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
						batch.setColor(1,1,1,1);
						batch.draw(laserdot, enemy.laserstartx-laserdotsize/4,enemy.laserstarty-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
					}
				}
			}
			
			if(enemy.shootinglz2){
				float x  = enemy.position.x+enemy.rect.width/2-enemy.ultiw*0.41f+enemy.ultiw*0.0844f;
				float y  = enemy.lzstarty2;
				float laserlenght = enemy.laserendx2-x;
				float vx = 1;
				float vy = 0;
				x += vy * laserHeight / 2;
				y += -vx * laserHeight / 2;
				if(enemy.laserpower==enemy.laserpower1)
					batch.setColor(0,1,0,1);
				else if(enemy.laserpower==enemy.laserpower2)
					batch.setColor(0,0,1,1);
				else if(enemy.laserpower==enemy.laserpower3)
					batch.setColor(1,0,0,1);
				batch.draw(laserRegion10, x, y, 0, 0, laserlenght, laserHeight, 1, 1, 0);
				batch.draw(laserdot,x-laserdotsize/2,enemy.lzstarty2-laserdotsize/2, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx2-laserdotsize/2,enemy.lzstarty2-laserdotsize/2, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.setColor(1,1,1,1);
				batch.draw(laserRegion11, x, y, 0, 0, laserlenght, laserHeight, 1, 1, 0);
				batch.draw(laserdot, x-laserdotsize/4,enemy.lzstarty2-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx2-laserdotsize/4,enemy.lzstarty2-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
			}
			if(enemy.shootinglz3){
				float x  = enemy.position.x+enemy.rect.width/2-enemy.ultiw*0.41f+enemy.ultiw*0.0844f;
				float y  = enemy.lzstarty3;
				float laserlenght = enemy.laserendx3-x;
				float vx = 1;
				float vy = 0;
				x += vy * laserHeight / 2;
				y += -vx * laserHeight / 2;
				if(enemy.laserpower==enemy.laserpower1)
					batch.setColor(0,1,0,1);
				else if(enemy.laserpower==enemy.laserpower2)
					batch.setColor(0,0,1,1);
				else if(enemy.laserpower==enemy.laserpower3)
					batch.setColor(1,0,0,1);
				batch.draw(laserRegion10, x, y, 0, 0, laserlenght, laserHeight, 1, 1, 0);
				batch.draw(laserdot, x-laserdotsize/2,enemy.lzstarty3-laserdotsize/2, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx3-laserdotsize/2,enemy.lzstarty3-laserdotsize/2, 0, 0, laserdotsize, laserdotsize, 1, 1, 0);
				batch.setColor(1,1,1,1);
				batch.draw(laserRegion11, x, y, 0, 0, laserlenght, laserHeight, 1, 1, 0);
				batch.draw(laserdot, x-laserdotsize/4,enemy.lzstarty3-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
				batch.draw(laserdot, enemy.laserendx3-laserdotsize/4,enemy.lzstarty3-laserdotsize/4, 0, 0, laserdotsize/2, laserdotsize/2, 1, 1, 0);
			}
		}
		
		//draw enemy shield
		for (Enemy enemy : model.enemies) {
			if(enemy.shieldtype==2){
				shieldregion.setSize(enemy.shield.width*1.365f, enemy.shield.width*1.365f);
				shieldregion.setPosition(enemy.position.x+enemy.rect.width/2-shieldregion.getWidth()/2, enemy.position.y+enemy.rect.height/2-shieldregion.getHeight()/2);
				float shieldb;
				float shieldr;
				if(enemy.shieldhp>=1){                                              //shield become red (red at 1 HP) 
					shieldb=1/(enemy.shieldMaxhp-1)*enemy.shieldhp-1/(enemy.shieldMaxhp-1);
					shieldr=-1/(enemy.shieldMaxhp-1)*enemy.shieldhp+enemy.shieldMaxhp/(enemy.shieldMaxhp-1);
				}
				else{
					shieldb=0;
					shieldr=1*enemy.shieldhp;
				}
				if(enemy.shieldviewtimer>0){
					enemy.shieldviewtimer-=20*delta;
					shieldregion.setColor(shieldr, 0, shieldb, 0.4f + 0.6f/40f* enemy.shieldviewtimer);
				}
				else{
					shieldregion.setColor(shieldr, 0, shieldb,0.4f);
				}
				if(enemy.shieldhp>0){
					shieldregion.draw(batch);
				}
			}
			if(enemy.shieldtype==1){
				shieldregion.setSize(enemy.shield.width*1.365f, enemy.shield.width*1.365f);
				shieldregion.setPosition(enemy.position.x+enemy.rect.width/2-shieldregion.getWidth()/2, enemy.position.y+enemy.rect.height/2-shieldregion.getHeight()/2);
				float shieldb;
				if(enemy.shieldhp>=1){                                              //shield become red (red at 1 HP) 
					shieldb=1/(enemy.shieldMaxhp-1)*enemy.shieldhp-1/(enemy.shieldMaxhp-1);
				}
				else{
					shieldb=0;
				}
				if(enemy.shieldviewtimer>0){
					enemy.shieldviewtimer-=20*delta;
					shieldregion.setColor(1, shieldb, shieldb, 0.3f + 0.7f/40f* enemy.shieldviewtimer);
				}
				else{
					shieldregion.setColor(1, shieldb, shieldb, 0.3f);
				}
				if(enemy.shieldhp>0){
					shieldregion.draw(batch);
				}
			}
			if(enemy.shieldtype==3){
				shieldregion.setSize(enemy.shield.width*1.28f, enemy.shield.width*1.28f);
				float shieldb;
				float shieldr;
				if(enemy.shieldhp>=1){                                              //shield become red (red at 1 HP) 
					shieldb=1/(enemy.shieldMaxhp-1)*enemy.shieldhp-1/(enemy.shieldMaxhp-1);
					shieldr=-1/(enemy.shieldMaxhp-1)*enemy.shieldhp+enemy.shieldMaxhp/(enemy.shieldMaxhp-1);
				}
				else{
					shieldb=0;
					shieldr=1*enemy.shieldhp;
				}
				if(enemy.shieldviewtimer>0){
					enemy.shieldviewtimer-=20*delta;
					shieldregion.setColor(shieldr, shieldb, 0, 0.4f + 0.6f/40f* enemy.shieldviewtimer);
				}
				else{
					shieldregion.setColor(shieldr, shieldb, 0, 0.3f);
				}
				if(enemy.shieldhp>0){
						shieldregion.setPosition(enemy.position.x+enemy.rect.width/2-shieldregion.getWidth()/2, enemy.position.y+enemy.rect.height/2-shieldregion.getHeight()/2);
						shieldregion.draw(batch);
					}
				}
			}
		
		//draw player shield
		if(Save.gd.shieldOwn){
			shieldregion.setSize(2.73f, 2.73f);
			shieldregion.setPosition(player.position.x+player.rect.width/2-shieldregion.getWidth()/2, player.position.y+player.rect.height/2-shieldregion.getHeight()/2);
			shieldeffect.setPosition(player.position.x+player.rect.width/2, player.position.y+player.rect.height/2);
			float shieldb;
			float shieldr;
			if(player.shieldhp>=1){                                              //shield become red (red at 1 HP) 
				shieldb=1/(Save.gd.shieldhp-1)*player.shieldhp-1/(Save.gd.shieldhp-1);
				shieldr=-1/(Save.gd.shieldhp-1)*player.shieldhp+Save.gd.shieldhp/(Save.gd.shieldhp-1);
			}
			else{
				shieldb=0;
				shieldr=1*player.shieldhp;
			}
			if(player.shieldviewTimer>0){
				player.shieldviewTimer-=20*delta;
				float effalpha=0.3f + 0.7f/40f* player.shieldviewTimer;
				float[] colorshield = new float[]{shieldr*effalpha,0,shieldb*effalpha,shieldr*effalpha,0,shieldb*effalpha};
				shieldeffect.getEmitters().get(0).getTint().setColors(colorshield);
				shieldregion.setColor(shieldr, 0, shieldb, 0.3f + 0.7f/40f* player.shieldviewTimer);
			}
			else{
				float effalpha=0.3f;
				float[] colorshield = new float[]{shieldr*effalpha,0,shieldb*effalpha,shieldr*effalpha,0,shieldb*effalpha};
				shieldeffect.getEmitters().get(0).getTint().setColors(colorshield);
				shieldregion.setColor(shieldr, 0, shieldb, 0.3f);
			}
			if(player.shieldhp>0){
				shieldregion.draw(batch);
				shieldeffect.draw(batch, delta);
			}
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
		//camera.position.y = player.position.y + viewport.getWorldHeight() / 2 - cameraBottom;
		//mapRenderer.setView(camera);
		ui.resize(width, height);
	}

	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if(!Save.gd.accelerometer){
			if(screenX < Gdx.graphics.getWidth()/6f) { //move
				touchedmove=pointer;
				touchmoveorigin=screenY;
				touchmove=true;
			}
			if(screenX > Gdx.graphics.getWidth() / 6f) { //shoot
				touchedshoot = pointer;
				if(activWeap==CatGame.SM){player.view.shootSM();}
				
			}
		}
		else{
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
		case Keys.X:
			Save.gd.maki=0;
			Save.save();
			
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
	
	public void dipsose(){
		batch.dispose();
		explode.dispose();
		sondecolect.dispose();
		propEffect.dispose();
		if(pnjpropEffect!=null){
			pnjpropEffect.dispose();
			model.kuro.view.dispose();
		}
		lasereffect.dispose();
		shieldeffect.dispose();
	}
}



/*// Draw Player laser.
Sprite laserRegion10 = new Sprite(Assets.manager.get(Assets.laser10,Texture.class));
Sprite laserRegion11 = new Sprite(Assets.manager.get(Assets.laser11,Texture.class));
Sprite laserRegion00 = new Sprite(Assets.manager.get(Assets.laser00,Texture.class));
Sprite laserRegion01 = new Sprite(Assets.manager.get(Assets.laser01,Texture.class));
Sprite laserRegion20 = new Sprite(Assets.manager.get(Assets.laser20,Texture.class));
Sprite laserRegion21 = new Sprite(Assets.manager.get(Assets.laser21,Texture.class));
Sprite laserRegion3  = new Sprite(Assets.manager.get(Assets.laser3 ,Texture.class));

float laserWidth  = bulletRegion.getRegionWidth() * scale/2;
float laserHeight = bulletRegion.getRegionWidth() * scale/2*player.laserpower*3;

float laserangle = player.view.weapangle;
float laservx = MathUtils.cosDeg(laserangle);
float laservy = MathUtils.sinDeg(laserangle);
float laseroffset=-0.76f;

float effectconvx=player.rect.x+player.rect.width+0.19f+laserWidth*(1+laseroffset)*laservx;
float effectconvy=player.rect.y                        +laserWidth*(1+laseroffset)*laservy;

lasereffect.setPosition(effectconvx, effectconvy);
float[] colorlzef1;
float colorswitch=0.35f;
float r=  player.laserpower/(1-colorswitch)-colorswitch/(1-colorswitch);
float b1= -player.laserpower/(1-colorswitch)+1/(1-colorswitch);
float b2 = player.laserpower/colorswitch;
float g = 1-player.laserpower/colorswitch;
float fadeOut=player.laserpower/(player.laserminpower+0.05f); //0.05 = power threshold fade start

if(player.laserpower>colorswitch) colorlzef1 = new float[]{r,0,b1 ,r,0,b1};
else if(player.laserpower>player.laserminpower+0.05f) colorlzef1 = new float[]{0,g,b2,0,g,b2};
else colorlzef1 = new float[]{0,g*fadeOut,b2*fadeOut ,0,g*fadeOut,b2*fadeOut};
lasereffect.update(1.25f*delta);
float shootposx=player.rect.x+player.rect.width+0.19f;

if(model.playerLaserbullets.size!=0 && touchedshoot!=-1){
	float laserEnd = Math.max(Math.abs(model.playerLaserbullets.get(2)),
				              Math.abs(model.playerLaserbullets.get(3)) );
	lzanimtimer+=0.2f;
	if(lzanimtimer>=laserWidth/2)lzanimtimer=-laserWidth/2;
	for(int i = 1; i < 50; i ++){
		float x=player.rect.x+player.rect.width+0.19f+laserWidth*(i+laseroffset)*laservx;
		float y=player.rect.y                        +laserWidth*(i+laseroffset)*laservy;
		x -=  laservx * laserWidth  /2;
		y -=  laservy * laserWidth  /2;
		x +=  laservy * laserHeight /2;
		y += -laservx * laserHeight /2;
		
		
		if( (x<laserEnd && shootposx<model.playerLaserbullets.get(2)-0.2f ) || ( x>laserEnd && shootposx>model.playerLaserbullets.get(2)+0.2f )
				 && y<laserEnd){
			if(i==1){
				if(player.laserpower>colorswitch) batch.setColor(r,0,b1,1);
				else batch.setColor(0,g,b2,1);
				batch.draw(laserRegion00, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
				batch.setColor(Color.WHITE);
				batch.draw(laserRegion01, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
			}
			else{
				//batch.setColor(player.laserpower,0,1-player.laserpower,1);
				if(player.laserpower>colorswitch) batch.setColor(r,0,b1,1);
				else batch.setColor(0,g,b2,1);
				batch.draw(laserRegion10, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
				if(i==2){
					batch.setColor(1,1,1,(lzanimtimer+laserWidth/2)*2/laserWidth);
					batch.draw(laserRegion3 , x+lzanimtimer*laservx, y+lzanimtimer*laservy, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
					batch.setColor(Color.WHITE);
					batch.draw(laserRegion11, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
				}
				else{
					batch.setColor(Color.WHITE);
					batch.draw(laserRegion3 , x+lzanimtimer*laservx, y+lzanimtimer*laservy, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
					batch.draw(laserRegion11, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
				}
			}
		}
		else if(
		( x<laserEnd+laserWidth*laservx && shootposx<model.playerLaserbullets.get(2)-0.2f ) || ( x>laserEnd+laserWidth*laservx && shootposx>model.playerLaserbullets.get(2)+0.2f )
				 && y<laserEnd+laserWidth*laservy){
			x=player.rect.x+player.rect.width+0.19f+laserWidth*(i+laseroffset)*laservx;
			y=player.rect.y                        +laserWidth*(i+laseroffset)*laservy;
			x -=  laservx * laserWidth  /2;
			y -=  laservy * laserWidth  /2;
			x +=  laservy * laserHeight /2;
			y += -laservx * laserHeight /2;
			if(player.laserpower>colorswitch) batch.setColor(r,0,b1,1);
			else batch.setColor(0,g,b2,1);
			batch.draw(laserRegion20, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
			batch.setColor(Color.WHITE);
			batch.draw(laserRegion21, x, y, 0, 0, laserWidth, laserHeight, 1, 1, laserangle);
		}
	}
	
	lasereffect.getEmitters().get(0).getTint().setColors(colorlzef1);
	lasereffect.getEmitters().get(0).getTransparency().setRelative(true);
	if(player.laserpower>0) lasereffect.draw(batch);
	
	
}*/
