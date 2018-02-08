package quentin.jeu.cat.tuto;

import quentin.jeu.cat.tuto.Modeltt.State;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** The model class for an enemy or player that moves around the map. */
public class Charactertt {
	public static String piece2="zetvZharEasbeLVswOBDz92HL0qxwKqs0DBCeH6QA+Q2KpCzQdX+i64aa0LRMcVOnZB";
	static float minVelocityX = 0.001f;
	static float dampingValue = 15;

	public Modeltt model;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public State state;
	public float dir;
	public Rectangle rect = new Rectangle();
	public float hp;

	float maxVelocityX;
	float maxVelocityY;
	float collisionOffsetY;
	float acceleration;

	Charactertt (Modeltt model) {
		this.model = model;
	}

	void update (float delta, boolean damp) {

		if(damp){
			// Damping reduces velocity so the character eventually comes to a complete stop.
			float damping = (dampingValue) * delta;
			if (velocity.x > 0)
				velocity.x = Math.max(0, velocity.x - damping);
			else
				velocity.x = Math.min(0, velocity.x + damping);
		}
		

		velocity.scl(delta); // Change velocity from units/sec to units since last frame.
		collideX();
		position.add(velocity);
		velocity.scl(1 / delta); // Change velocity back.
	}


	boolean collideX () {
		rect.x = position.x + velocity.x;
		rect.y = position.y + collisionOffsetY;
		return false;
	}

	boolean collideY () {
		rect.x = position.x;
		rect.y = position.y + velocity.y + collisionOffsetY;
		return false;
	}

	void moveLeft (float delta) {
		velocity.x = Math.max(velocity.x - 45 * delta, -maxVelocityX);
		dir = -1;
	}

	void moveRight (float delta) {
		velocity.x = Math.min(velocity.x + 45 * delta, maxVelocityX);
		dir = 1;
	}


	void moveup (float delta) {
		if (velocity.y < maxVelocityY) velocity.y = Math.min(velocity.y + acceleration * delta, maxVelocityY);
	}
	
	void movedown (float delta) {
		if (velocity.y > -maxVelocityY) velocity.y = Math.max(velocity.y - acceleration * delta, -maxVelocityY);
	}
}
