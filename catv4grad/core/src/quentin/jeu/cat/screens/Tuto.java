package quentin.jeu.cat.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import quentin.jeu.cat.tuto.Modeltt;
import quentin.jeu.cat.tuto.Viewtt;
import quentin.jeu.cat.utils.Save;


public class Tuto implements Screen {
	public static String piece2="AEnZtkqQ1AqdJUC8m0nitCA2E0UhflJtHJOvFACE+DsEXTaiPruz7mvzoFVEL/djFEYp+wBDL+7rmf3O";
	private static Vector2 temp = new Vector2();
	private Viewtt view;
	private Modeltt model;
	private Sound hit= Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
	public int lvl=Save.gd.playerPos;
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		model = new Modeltt(this);
		view = new Viewtt(model);
	}

	@Override
	public void render(float delta) {
		delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f) * model.getTimeScale();
		if (delta > 0) {
			model.update(delta);
			view.update(delta);
		}
		view.render(delta);
		
	}

	public void eventHitBullet (float x, float y, float vx, float vy) {
		Vector2 offset = temp.set(vx, vy).nor().scl(15 * Modeltt.scale);
		view.hits.add(Viewtt.bulletHitTime);
		view.hits.add(x + offset.x);
		view.hits.add(y + offset.y);
		view.hits.add(temp.angle() + 90);
		hit.play(0.2f);
		
	}

	public void eventGameOver (boolean win) {
		if (!view.ui.gameoverTable.hasParent()) {
			view.ui.showGO(win);
			if (win) {
				Save.gd.playerPos = 0;
				Save.gd.playerGo = 0;
				Save.save();
			}
		}
	}
	
	public void resize (int width, int height) {
		view.resize(width, height);
	}

	public void restart () {
		model.restart(lvl);
		view.restart();
	}

	@Override
	public void hide() {
		view.dispose();
	}

	@Override
	public void pause() {
		if(!view.ui.gameoverTable.hasParent()&&!view.ui.quit.isVisible()&&!view.ui.menu.isVisible())
			view.ui.pauseButton.setChecked(true);
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
