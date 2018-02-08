package quentin.jeu.cat.screens;

import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {
	
	public static String piece1  = "NJJCJkBOChlriljH:x1CBRFGBBPDBR9BNJJCDhLDBRFB";
	private SpriteBatch batch;
	private Sprite logo;
	private Sprite logoname;
	
	@Override
	public void show() {
		Save.load();
		batch = new SpriteBatch();
		
		logo = new Sprite(new Texture("splash/shadowcat.png"));
		logoname = new Sprite(new Texture("splash/shadowcat1.png"));
		logo.setPosition(Gdx.graphics.getWidth()/2-logo.getWidth()/2, Gdx.graphics.getHeight()/2-logo.getHeight()/2.75f);
		logoname.setPosition(Gdx.graphics.getWidth()/2-logoname.getWidth()/2, Gdx.graphics.getHeight()/2-logo.getHeight()/2.75f-logoname.getHeight());
		Assets.load();
		
	}
	@Override
	public void render(float delta) {
		
		float prog=Assets.manager.getProgress()*Assets.manager.getProgress()*Assets.manager.getProgress();
		Gdx.gl.glClearColor(prog,prog,prog, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		logo.draw(batch);
		logoname.draw(batch,prog);
		batch.end();
		if(Assets.manager.update()){
			// all the assets are loaded
			if(Save.gd.playerPos==-1)((Game) Gdx.app.getApplicationListener()).setScreen(new Intro());
			else ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
	}
	@Override
	public void resize(int width, int height) {
		
	}
	@Override
	public void hide() {
		dispose();
		
	}
	@Override
	public void pause() {
		
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void dispose() {
		batch.dispose();
		logo.getTexture().dispose();
	}
}
