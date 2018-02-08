package quentin.jeu.cat.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.catinvader.Model;
import quentin.jeu.cat.catinvader.View;
import quentin.jeu.cat.utils.Save;

public class FightScreen implements Screen {
	private static Vector2 temp = new Vector2();
	private View view;
	private Model model;
	public int cost;
	private Sound hit= Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
	
	public FightScreen(int nrjcost) {
		this.cost=nrjcost;
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		model = new Model(this);
		view = new View(model,cost);
		//prop.setVolume(prop.loop(),0.1f);		
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
		Vector2 offset = temp.set(vx, vy).nor().scl(15 * Model.scale);
		view.hits.add(View.bulletHitTime);
		view.hits.add(x + offset.x);
		view.hits.add(y + offset.y);
		view.hits.add(temp.angle() + 90);
		if(Save.gd.soundEnabled)hit.play(0.2f);
		
	}
	public void eventHitBulletlz (float x, float y, float vx, float vy) {
		Vector2 offset = temp.set(vx, vy).nor().scl(15 * Model.scale);
		view.hits.add(View.bulletHitTime);
		view.hits.add(x + offset.x);
		view.hits.add(y + offset.y);
		view.hits.add(temp.angle() + 90);
		if(Save.gd.soundEnabled)hit.play(0.1f);
		
	}

	public void eventGameOver (boolean win) {
		int playerwas;
		if (!view.ui.gameoverTable.hasParent()) {
			view.ui.showGO(win);
			
			if (win) {
				if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro){
					Save.gd.eventdefeatkuro=true;
				}
				if(Save.gd.playerPos>=CatGame.EVENTBOSSCATIOUS && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatcatious){
					Save.gd.eventdefeatcatious=true;
				}
				if(Save.gd.playerPos>=CatGame.EVENTBOSSN && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatn){
					Save.gd.eventdefeatn=true;
				}
				playerwas = Save.gd.playerPos;
				Save.gd.playerPos = Save.gd.playerGo;
				Save.gd.playerGo = playerwas;
				Save.save();
			}
		}
	}
	
	public void resize (int width, int height) {
		view.resize(width, height);
	}

	public void restart () {
		model.restart();
		view.restart();
	}

	@Override
	public void hide() {
		view.ui.prop.stop();
		dispose();
	}

	@Override
	public void pause() {
		if(!view.ui.gameoverTable.hasParent()&&!view.ui.quit.isVisible()&&!view.ui.menu.isVisible())
			view.ui.pauseButton.setChecked(true);;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		hit.dispose();
		view.ui.dispose();
		view.dipsose();
	}
}
