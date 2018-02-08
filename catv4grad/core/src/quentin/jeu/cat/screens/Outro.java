package quentin.jeu.cat.screens;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.ui.Scenario;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Outro implements Screen {
	private static float cameraWidth = 16, cameraHeight = 16;
	
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private SpriteBatch batch;
	public  Stage stage;
	private Skin skin;
	private Table root;
	private Label introtxt;
	private TextButton playButton;
	private ScrollPane pane;
	
	private Sprite bg1;
	private Image black;
	private boolean auto=true;
	private float txtspeed;
	
	@Override
	public void show() {
		Save.load();
		Save.gd.anomaly=true;
		Save.save();
		stage = new Stage(new ScreenViewport());
		
		loadSkin();
		create();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);
		
		bg1= new Sprite(Assets.manager.get(Assets.outro,Texture.class));
		bg1.setSize(18*16/9,18);
		
		txtspeed=0;
		Gdx.input.setInputProcessor(new InputMultiplexer(stage));
	}
	
	private void create   () {
		introtxt=new Label(CatGame.myBundle.get("outro"), skin);
		introtxt.setAlignment(Align.center);
		
		//OK button
		playButton = button(CatGame.myBundle.get("ok"), true);
		playButton.addAction(forever(sequence(alpha(0.9f,1),alpha(0.3f,1.5f, pow2Out))));
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playButton.addAction(forever(sequence(fadeOut(0.07f), fadeIn(0.07f))));
				stage.addAction(sequence(fadeOut(1), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					}
				})));
			}
		});
		root = new Table(skin);
		root.setFillParent(true);
		root.add(introtxt).align(Align.center).fillX().row();
		root.add(playButton).padBottom(0).padTop(60).padBottom(Gdx.graphics.getHeight());
		root.getColor().a=0;
		root.addAction(fadeIn(1));
		
		//text pane
		pane=new ScrollPane(root);
		pane.setFillParent(true);
		pane.getColor().a=0;
		pane.addAction(sequence(delay(82),fadeIn(2,pow2In)));
		pane.addListener(new InputListener() {
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		 		auto=false;
				return false;
		 	}
		 });
		
		stage.addActor(Scenario.createdialog(27, skin));
		
		//white fade in
		stage.addActor(pane);
		black=new Image(skin.newDrawable("white", Color.WHITE));
		black.setSize(stage.getWidth(), stage.getHeight());
		black.setPosition(0, 0);
		black.addAction(alpha(0,9,fade));
		black.setTouchable(Touchable.disabled);
		stage.addActor(black);
	}


	private void loadSkin () {
		skin = new Skin();
		if(Gdx.graphics.getHeight()<1000){
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			skin.add("big", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
		}
		else{
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			skin.add("big", new BitmapFont(Gdx.files.internal("font/arialbig.fnt")));
		}

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.down = skin.newDrawable("white", new Color(0x416ba1ff));
		textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		textButtonStyle = new TextButtonStyle(textButtonStyle);
		textButtonStyle.checked = skin.newDrawable("white", new Color(0x5287ccff));
		skin.add("toggle", textButtonStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);
		labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("big");
		skin.add("big", labelStyle);
	}

	private TextButton button (String text, boolean toggle) {
		TextButton button = new TextButton(text, skin, toggle ? "toggle" : "default");
		button.pad(2, 12, 2, 12);
		return button;
	}

	@Override
	public void render(float delta) {
		if(pane.getScrollPercentY()==1){
			auto=false;
		}
		if(pane.getColor().a!=0 && auto){
			txtspeed+=30*delta;
			pane.setScrollY(txtspeed);
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		camera.position.x=0;
		camera.position.y=0;
		viewport.apply();
		
		
		batch.begin();
		//draw background
		bg1.setFlip(false, false);
		bg1.setPosition(camera.position.x- 9*16/9,-9f);
		bg1.draw(batch);
		batch.end();
		stage.act();
		stage.getViewport().apply(true);
		stage.draw();
	
	}
	
	public void resize (int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height, true);
		root.invalidateHierarchy();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
