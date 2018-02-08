package quentin.jeu.cat.screens;



import quentin.jeu.cat.ui.MapUI;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.TimeCalc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Starmap implements Screen {
	
	static float cameraWidth = 16, cameraHeight = 16, cameraZoom = 0.4f, cameraZoomSpeed = 0.5f;
	
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private SpriteBatch batch;
	public MapUI ui;
	
	
	private Sprite galaxy, stars0;

	public boolean leftPressed;

	public boolean rightPressed;
	float touchmoveY;
	float testtime;
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(false);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);
		stars0 =new Sprite(Assets.manager.get(Assets.stars,Texture.class));
		stars0.setSize(16*16/9, 16);
		stars0.setPosition(-16/9*16+2, -8);
		galaxy =new Sprite(Assets.manager.get(Assets.galaxy,Texture.class));
		
		ui = new MapUI();
		Gdx.input.setInputProcessor(new InputMultiplexer(ui, ui.stage, ui.starstage));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		viewport.apply();
		batch.begin();
		
		stars0.draw(batch);
		
		galaxy.setSize(9, 9);
		galaxy.setColor(1f, 1, 1, 1);
		galaxy.setPosition(3/4*16/9, 3);
		galaxy.setOriginCenter();
		galaxy.rotate(-0.05f);
		galaxy.draw(batch);
		
		galaxy.setSize(12, 12);
		galaxy.setColor(1f, 1f, 1f, 1);
		galaxy.setPosition(-16*16/9+10, -13);
		galaxy.setOriginCenter();
		galaxy.rotate(-0.02f);
		galaxy.draw(batch);
		
		//stars1.draw(batch);
		batch.end();
		ui.render(delta);
	}
	
	public void resize (int width, int height) {
		viewport.update(width, height);
		ui.resize(width, height);
	}
	
	@Override
	public void hide() {
		ui.music.stop();
		dispose();
	}

	@Override
	public void pause() {
		ui.timecalc.dispose();
	}

	@Override
	public void resume() {
		ui.timecalc=new TimeCalc();
	}

	@Override
	public void dispose() {
		batch.dispose();
		ui.dispose();
	}
}
