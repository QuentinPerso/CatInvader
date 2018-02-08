package quentin.jeu.cat.tuto;

import static quentin.jeu.cat.tuto.Modeltt.scale;
import static quentin.jeu.cat.tuto.Enemytt.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** The view class for an enemy. */
class EnemyViewtt extends CharacterViewtt {
	Enemytt enemy;
	Color headColor;
	boolean canShoot;
	float burstShots, burstTimer;
	float animtimer=1;
	Vector2 temp1 = new Vector2();
		
	EnemyViewtt (final Viewtt view, Enemytt enemy) {
		super(view);
		this.enemy = enemy;
	}
	
	void update (float delta) {
		// When not shooting, reset the number of burst shots.
			canShoot=true;
	}

	void shoot () {
		if ( enemy.shootTimer >= 0) return;
		enemy.shootTimer = enemy.shootDelay;
		burstTimer = burstDuration;
		
		// Compute the position and velocity to spawn a new bullet.
		float x = enemy.position.x-.2f;
		float y = enemy.position.y+0.f;
		//weaponx += width / 2;
		//weapony += height / 2;
		
		
		float angle = temp1.set(model.player.position).sub(x, y).angle();
		float variance = minprecisionheated * Math.min(1, burstShots * heatperShot/maxShots2heat);
		angle += MathUtils.random(-variance, variance);

		float cos = MathUtils.cosDeg(angle), sin = MathUtils.sinDeg(angle);
		float vx = cos * bulletSpeed;// + enemy.velocity.x * bulletInheritVelocity;
		float vy = sin * bulletSpeed;// + player.velocity.y * bulletInheritVelocity;
		
		x += cos * shootOffsetX * scale;
		y += sin * shootOffsetX * scale;
		model.addEnemyBullet(x, y, vx, vy, temp1.set(vx, vy).angle());

		
		//SoundEffect.shoot.play();

		burstShots = Math.min(maxShots2heat, burstShots + 1);
	}



}
