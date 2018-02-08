package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Model.scale;
import static quentin.jeu.cat.catinvader.Enemy.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.catinvader.Enemy.Type;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** The view class for an enemy. */
class EnemyView extends CharacterView {
	Enemy enemy;
	public  float  lZangle, coslz, sinlz;
	int rafalshot;
	Vector2 temp1 = new Vector2();
		
	EnemyView (final View view, Enemy enemy) {
		super(view);
		this.enemy = enemy;
		rafalshot=enemy.rafalnbr;
		
	}
	
	void update (float delta) {
		if (!enemy.shootinglz && enemy.lasertimer<enemy.lasershoottime) {
			enemy.lasertimer+=0.055f*2/500*enemy.laserRLtime;
		}
		else if(!enemy.shootinglz && enemy.lasertimer>=enemy.lasershoottime){
			enemy.canShootlz=true;
		}
	}

	void shoot () {
		if ( enemy.shootTimer >= 0) return;
		if((enemy.type==Type.strong && rafalshot==enemy.rafalnbr && (!Save.gd.eventendcat|| //strong cats fire missiles
				(Save.gd.playerGo==CatGame.ANOMALY && model.player.position.x>View.t1)))){  //but not strong dolphin
			int randomweap = MathUtils.random(2); 
			if(randomweap==1) {
				shootSM();
				return;
			}
		}
		if(((enemy.type==Type.normal && rafalshot==enemy.rafalnbr  && enemy.offsetX>-4
				&& Save.gd.eventendcat && Save.gd.playerGo!=CatGame.ANOMALY) || //normal dolphin do
			(enemy.type==Type.normal && rafalshot==enemy.rafalnbr  && enemy.offsetX>-4
			&& Save.gd.playerGo==CatGame.ANOMALY && model.player.position.x<View.t1))){ 
			int randomweap = MathUtils.random(7);
			if(randomweap==1) {
				shootSM();
				return;
			}
		}
		if(enemy.type==Type.bomber && rafalshot==enemy.rafalnbr){ 
			int randomweap = MathUtils.random(5);
			if(randomweap==1) {
				shootSM();
				return;
			}
		}
		
		
		if(rafalshot==0){
			enemy.shootTimer = MathUtils.random(enemy.shootDelay/2,enemy.shootDelay);
			rafalshot=enemy.rafalnbr;
		}
		else{
			rafalshot--;
			enemy.shootTimer = 0.1f;
		}
		
		// Compute the position and velocity to spawn a new bullet.
		float x,y;
		if(enemy.type==Type.big){
			x = enemy.position.x-0.5f;
			y = enemy.position.y+enemy.rect.height*0.46f;
		}
		else if(enemy.type==Type.bomber){
			x = enemy.position.x-.3f;
			y = enemy.position.y+enemy.rect.height/2+enemy.rect.height*0.1f;
		}
		else{
			x = enemy.position.x-.3f;
			y = enemy.position.y+enemy.rect.height*0.25f;
		}
		
		float angle = temp1.set(model.player.position).sub(x, y).angle();
		angle += MathUtils.random(- precision,  precision);

		float cos = MathUtils.cosDeg(angle), sin = MathUtils.sinDeg(angle);
		float vx = cos * bulletSpeed;
		float vy = sin * bulletSpeed;
		
		//x += cos * shootOffsetX * scale;
		//y += sin * shootOffsetX * scale;
		model.addEnemyBullet(x, y, vx, vy, temp1.set(vx, vy).angle(),enemy.rgpower);
		if(enemy.type==Type.big){
			x = enemy.position.x-0.5f;
			y = enemy.position.y+enemy.rect.height*0.57f;
			model.addEnemyBullet(x, y, vx, vy, temp1.set(vx, vy).angle(),enemy.rgpower);
		}
		//SoundEffect.shoot.play();
	}
	
	void shootSM () {
		if ( enemy.shootTimerSM >= 0) return;
		float x , y;
		x = enemy.position.x-.3f;
		y = enemy.position.y+enemy.rect.height/2+enemy.rect.height*0.1f;
		if(enemy.type==Type.big){
			int canon = MathUtils.random(1);
			// Compute the position and velocity to spawn a new bullet.
			x = enemy.position.x-.2f;
			if(canon==1) y = enemy.position.y+enemy.rect.height*0.3f;
			else y = enemy.position.y+enemy.rect.height*0.7f;
		}
		if(enemy.type==Type.ultima){
			int canon = MathUtils.random(1);
			// Compute the position and velocity to spawn a new bullet.
			x = enemy.position.x+enemy.rect.width/2-enemy.ultiw*(0.41f-0.2187f);
			if     (canon==0) y = enemy.position.y+enemy.rect.height/2+enemy.ultih*0.247f;
			else if(canon==2) y = enemy.position.y+enemy.rect.height/2-enemy.ultih*0.242f;
		}
		if(enemy.type==Type.strong){
			enemy.shootTimer = 2*enemy.shootDelay;
			x = enemy.position.x-.3f;
			y = enemy.position.y+enemy.rect.height*0.25f;
		}
		if(((enemy.type==Type.normal && Save.gd.eventendcat && Save.gd.playerGo!=CatGame.ANOMALY) || //normal dolphin do
				(enemy.type==Type.normal && Save.gd.playerGo==CatGame.ANOMALY && model.player.position.x<View.t1))){ 
			enemy.shootTimer = 3f*enemy.shootDelay;
		}
		if(enemy.type==Type.bomber){
			enemy.shootTimer = 2*enemy.shootDelay;
		}
		enemy.shootTimerSM = enemy.shootDelaySM;
		
		

		float angle = temp1.set(model.player.position).sub(x, y).angle();
		angle += MathUtils.random(- precision,  precision);

		float cos = MathUtils.cosDeg(angle), sin = MathUtils.sinDeg(angle);
		float vx = cos * smSpeed;
		float vy = sin * smSpeed;
		
		x += cos * shootOffsetX * scale;
		y += sin * shootOffsetX * scale;
		model.addEnemySM(x, y, vx, vy, temp1.set(vx, vy).angle(), enemy.smpower);
	

	}
	
	void shootLaser () {
		if(!enemy.canShootlz) return;
		if(enemy.lasertimer>0) enemy.lasertimer-=0.055f*2/300;
		else{
			enemy.lasertimer=0;
			enemy.shootinglz=false;
			enemy.canShootlz=false;
		}
		if (enemy.lasertimer==0) return;
			
		//float prevlzangle=lZangle;
		lZangle = temp1.set(model.player.position.x+Player.width/2,model.player.position.y+Player.height/2).sub(enemy.laserstartx, enemy.laserstarty).angle();
		if(!enemy.shootinglz){
			float lzprec=MathUtils.random(-enemy.laserprec,enemy.laserprec);
			enemy.shootlzangle=lZangle+lzprec;
		}
		
		if(enemy.lasertimer>enemy.laseraimtime && enemy.type!=Type.cosmicray && enemy.laserendx<model.player.position.x){
			if(lZangle>enemy.shootlzangle+0.7f){
				enemy.shootlzangle+=enemy.laserspeed;
			}
			else  if(lZangle<enemy.shootlzangle-0.7f){
				enemy.shootlzangle-=enemy.laserspeed;
			}
		}
		if(enemy.type==Type.bombes) enemy.shootlzangle+=enemy.laserspeed;
		enemy.shootinglz=true;

	}

}
