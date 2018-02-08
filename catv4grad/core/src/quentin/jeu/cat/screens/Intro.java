package quentin.jeu.cat.screens;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class Intro implements Screen {
	public static String piece1= "4qVKnqsK3myF7oEQzEpkayCHdZn0RFFqE8dKn2vwIXdFK34R+5q5m6pLvmKg70yA7ROrWbHac3U9R4b+c";
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
	private  Music music;
	public Image black;
	
	private Sprite bg1;
	private boolean auto=true;
	private float txtspeed;
	public static String piece5="Wdm8mCqL2qxXH+p7tZqNB9gimGP3Qgrd2pd1Tte8yI+w6Zrk2e86JZmfWIrArbYZFlFGx";
	
	@Override
	public void show() {
		Save.load();
		CatGame.gservices.signIn();
		stage = new Stage(new ScreenViewport());
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicspace.mp3"));
		music.setLooping(true);
		music.setVolume(0.0f);
		music.play();
		
		loadSkin();
		create();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);
		
		bg1= new Sprite(Assets.manager.get(Assets.stars,Texture.class));
		bg1.setSize(18*16/9,18);
		txtspeed=0;
		Gdx.input.setInputProcessor(new InputMultiplexer(stage));
		black=new Image(skin.newDrawable("white", Color.BLACK));
		black.getColor().a=0.9f;
		black.setSize(stage.getWidth(), stage.getHeight());
		black.setPosition(0, 0);
		black.setTouchable(Touchable.disabled);
		black.addAction(sequence(alpha(0,3,fade)));
		stage.addActor(black);
	}
	
	private void create   () {
		introtxt=new Label(CatGame.myBundle.get("intro"), skin);
		introtxt.setAlignment(Align.center);
		playButton = button(CatGame.myBundle.get("ok"), true);
		playButton.addAction(forever(sequence(alpha(0.9f,1),alpha(0.3f,1.5f, pow2Out))));
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playButton.addAction(forever(sequence(fadeOut(0.07f), fadeIn(0.07f))));
				black.addAction(sequence(alpha(1,2,fade)));
			}
		});
		root = new Table(skin);
		root.setFillParent(true);
		root.add(introtxt).align(Align.center).padTop(50).fillX().row();
		root.add(playButton).padBottom(0).padTop(60).padBottom(Gdx.graphics.getHeight());
		root.getColor().a=0;
		root.addAction(fadeIn(1));
		
		pane=new ScrollPane(root);
		pane.setFillParent(true);
		pane.getColor().a=0;
		pane.addAction(fadeIn(2,pow2In));
		pane.addListener(new InputListener() {
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		 		auto=false;
				return false;
		 	}
		 });

		
		
		stage.addActor(pane);
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
		if(auto){
			txtspeed+=30*delta;
			pane.setScrollY(txtspeed);
		}
		if(black!=null ){
			music.setVolume(0.2f-0.2f*black.getColor().a);
			if(black.getColor().a==1)
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			
		}
		/*if(stage.getRoot().getColor().a==1){
			music.setVolume(pane.getScrollPercentY()/5f);
		}
		else if(stage.getRoot().getColor().a>=0.02f){
			music.setVolume(stage.getRoot().getColor().a/5f);
		}*/
			
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
		music.dispose();
		stage.dispose();
		skin.dispose();
		batch.dispose();
		
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
