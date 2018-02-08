package quentin.jeu.cat.screens;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.Locale;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen {
	
	static float cameraWidth = 16, cameraHeight = 16, cameraZoom = 0.4f, cameraZoomSpeed = 0.5f;
	
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private SpriteBatch batch;
	public  Stage stage;
	private Skin skin;
	private Table root, optMenu, langmenu, hofmenu;
	private TextButton frButton, enButton,okbutton2;
	private TextButton achievButton, scoreButton,okbutton3;
	private TextButton languageButton, accelerometer, musicButton, soundButton,okbutton, rateButton;
	private Image titleImage;
	private TextButton playButton, optionButton, hallOfButton;
	
	private ParticleEffect propEffect;
	private TextureRegion playerRegion, weapRegion,  propRegion, wingRegion,nanakiRegion,nanapawR, stickR;
	private Sprite bg1, bg2;
	private float bgAnimtimer=0, playerX = -1.75f, playerY = -0.65f, vy=-5, range,animtimer=0 ;
	private Sound click,playSound;
	private Music prop;
	
	@Override
	public void show() {
		Save.load();
		if(CatGame.gservices.isSignedIn() && Save.gd.visitnbrgs!=0){
			CatGame.gservices.incrementexplore(Save.gd.visitnbrgs);
			Save.gd.visitnbrgs=0;
		}
		if(CatGame.gservices.isSignedIn() && Save.gd.metalspentgs!=0){
			CatGame.gservices.incrementexplore(Save.gd.metalspentgs);
			Save.gd.metalspentgs=0;
		}
		if(CatGame.gservices.isSignedIn() && Save.gd.scoregs!=0){
			CatGame.gservices.incrementexplore(Save.gd.scoregs);
			Save.gd.scoregs=0;
		}
		if(Save.gd.lang==-1) {
			if(Locale.getDefault().getLanguage()=="en") Save.gd.lang=0;
			if(Locale.getDefault().getLanguage()=="en") Save.gd.lang=1;
		}
		else if(Save.gd.lang==0){
			FileHandle baseFileHandle = Gdx.files.internal("internation/MyBundle");
			Locale locale = new Locale("en");
			CatGame.myBundle = I18NBundle.createBundle(baseFileHandle, locale);
		}
		else if(Save.gd.lang==1){
			FileHandle baseFileHandle = Gdx.files.internal("internation/MyBundle");
			Locale locale = new Locale("fr");
			CatGame.myBundle = I18NBundle.createBundle(baseFileHandle, locale);
		}
		
		
		stage = new Stage(new ScreenViewport());
		click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		playSound= Gdx.audio.newSound(Gdx.files.internal("sounds/zoom2.wav"));
		prop= Gdx.audio.newMusic(Gdx.files.internal("sounds/propulseur.wav"));
		prop.setLooping(true);
		prop.setVolume(0);
		if(Save.gd.soundEnabled) prop.play();
		
		loadSkin();
		create();
		layout();
		events();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(cameraWidth, cameraHeight, camera);
		
		bg1= new Sprite(Assets.manager.get(Assets.stars,Texture.class));
		bg1.setSize(18*16/9,18);
		bg2= new Sprite(Assets.manager.get(Assets.galaxybg,Texture.class));
		bg2.setSize(18*16/9,18);
		
		playerRegion = new TextureRegion(Assets.manager.get(Assets.spaceship1,Texture.class));
		weapRegion   = new TextureRegion(Assets.manager.get(Assets.riceGun,Texture.class));
		propRegion   = new TextureRegion(Assets.manager.get(Assets.prop1,Texture.class));
		wingRegion   = new TextureRegion(Assets.manager.get(Assets.wing,Texture.class));
		nanakiRegion = new TextureRegion(Assets.manager.get(Assets.nanaki,Texture.class));
		nanapawR     = new TextureRegion(Assets.manager.get(Assets.nanapaw,Texture.class));
		stickR       = new TextureRegion(Assets.manager.get(Assets.stick,Texture.class));
		propEffect = new ParticleEffect();
		propEffect.load(Gdx.files.internal("effects/prop2.p"), Gdx.files.internal("effects/"));
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage));
	}
	
	private void create   () {
		titleImage   = new Image(Assets.manager.get(Assets.title, Texture.class));
		titleImage.addAction(forever(sequence(alpha(0.9f,1),alpha(0.3f,1.5f, pow2Out))));
		titleImage.getColor().a=0.5f;

		playButton = button(CatGame.myBundle.get("play"), true);
		playButton.addAction(forever(sequence(alpha(0.9f,1, sine),alpha(0.3f,1, sine))));
		
		optionButton = button("Option", true);
		//optionButton.addAction(forever(sequence(alpha(0.9f,1),alpha(0.3f,1.5f, pow2Out))));
		optionButton.setColor(1, 1, 1, 0.5f);
		
		hallOfButton = button(CatGame.myBundle.get("hof"), false);
		//hallOfButton.addAction(forever(sequence(alpha(0.9f,1),alpha(0.3f,1.5f, pow2Out))));
		hallOfButton.setColor(1, 1, 1, 0.5f);
		
		//options
		languageButton = button (CatGame.myBundle.get("lang"), true);
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
			accelerometer = button (CatGame.myBundle.get("accel"), true);
			accelerometer.setChecked(Save.gd.accelerometer);
		}
		rateButton = button (CatGame.myBundle.get("rate"), false);
		okbutton = button ("Ok", true);
		
		musicButton = button (CatGame.myBundle.get("music") , true);
		soundButton = button (CatGame.myBundle.get("sound") , true);
		musicButton.setChecked(Save.gd.musicEnabled);
		soundButton.setChecked(Save.gd.soundEnabled);
		
		
		//language
		frButton = button ("Français" , true);
		enButton = button ("English" , true);
		new ButtonGroup<Button>(frButton, enButton);
		if(Save.gd.lang==0)enButton.setChecked(true);
		if(Save.gd.lang==1)frButton.setChecked(true);
		
		okbutton2 = button ("Ok", true);
		
		//GSERVICES
		achievButton = button (CatGame.myBundle.get("achiev") , false);
		scoreButton     = button (CatGame.myBundle.get("score") , false);
		
		okbutton3 = button ("Ok", false);
	}

	private void layout   () {
		
		//Create menu
		Table menubuttons = new Table();
		menubuttons.defaults().uniformX().fillX();
		menubuttons.defaults().padTop(5);
		menubuttons.add(languageButton).row();
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) menubuttons.add(accelerometer).row();
		menubuttons.add(rateButton).row();
		menubuttons.add(musicButton).row();
		menubuttons.add(soundButton).padBottom(20).row();
		menubuttons.add(okbutton).row();
		
		//Menu layout
		optMenu = new Table(skin);
		optMenu.setFillParent(true);
		
		optMenu.center().add(menubuttons).center().padTop(10).colspan(1);
		optMenu.setVisible(false);
		
		//Create language
		Table langbuttons= new Table();
		langbuttons.defaults().uniformX().fillX();
		langbuttons.defaults().padTop(5);
		langbuttons.add(frButton).row();
		langbuttons.add(enButton).padBottom(20).row();
		langbuttons.add(okbutton2).row();
		
		//Language layout
		langmenu = new Table(skin);
		langmenu.setFillParent(true);
		
		langmenu.center().add(langbuttons).center().padTop(10).colspan(1);
		langmenu.setVisible(false);
		
		//HOF layout
		Table hofbuttons= new Table();
		hofbuttons.defaults().uniformX().fillX();
		hofbuttons.defaults().padTop(5);
		hofbuttons.add(achievButton).row();
		hofbuttons.add(scoreButton).padBottom(20).row();
		hofbuttons.add(okbutton3).row();
		
		hofmenu = new Table(skin);
		hofmenu.setFillParent(true);
		
		hofmenu.center().add(hofbuttons).center().padTop(10).colspan(1);
		hofmenu.setVisible(false);
		
		//root menu 
		root = new Table(skin);
		root.setFillParent(true);
		root.top().add(titleImage).size(Gdx.graphics.getWidth()/1.5f, Gdx.graphics.getHeight()/2).colspan(3).padBottom(Gdx.graphics.getHeight()/5).padTop(Gdx.graphics.getHeight()/20).fillX().row();
		root.defaults().uniformX().fillX();
		root.add(optionButton).padBottom(0).padLeft(125).padRight(20);
		root.add(playButton).padBottom(0).padLeft(20).padRight(20);
		root.add(hallOfButton).padBottom(0).padLeft(20).padRight(125);
		root.getColor().a=0;
		root.addAction(fadeIn(1));
		
		stage.addActor(root);
		stage.addActor(optMenu);
		stage.addActor(langmenu);
		stage.addActor(hofmenu);
	}

	private void events   () {
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)playSound.play(1,0.3f,0);
				playButton.addAction(forever(sequence(fadeOut(0.07f), fadeIn(0.07f))));
				stage.addAction(sequence(fadeOut(3), run(new Runnable() {
					@Override
					public void run() {
						if(Save.gd.playerPos==-1){
							//Save.gd.playerPos=0;Save.gd.playerGo = 0; Save.save(); ///////////////delete for tutorial
							((Game) Gdx.app.getApplicationListener()).setScreen(new Tuto()); //new Tuto()
						}
						else ((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
					}
				})));
			}
		});
		
		rateButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.gservices.rateGame();
			}
		});
		
		hallOfButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				root.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				hofmenu.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(),alpha(1, 0.1f, fade)));
			}
		});
		
		scoreButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.gservices.showScores();
			}
		});
		achievButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.gservices.showAchieve();
			}
		});
		
		optionButton .addListener(new ClickListener() {
			public void clicked ( InputEvent event, float xbut, float ybut) {
				if(Save.gd.soundEnabled)click.play();
				root.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				optMenu.clearActions();
				optMenu.getColor().a = optMenu.isVisible() ? 1 : 0;
				if (optMenu.isVisible()){
					optMenu.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				}
				else{
					optMenu.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(),alpha(1, 0.1f, fade)));
				}
			}
		});
		
		languageButton.addListener(new ClickListener() {
			public void clicked ( InputEvent event, float xbut, float ybut) {
				if(Save.gd.soundEnabled)click.play();
				optMenu.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				langmenu.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(),alpha(1, 0.1f, fade)));
			}
		});
		
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
			accelerometer.addListener(new ClickListener() {
				public void clicked ( InputEvent event, float xbut, float ybut) {
					if(Save.gd.soundEnabled)click.play();
					Save.gd.accelerometer=accelerometer.isChecked();
					Save.save();
				}
			});
		}
		
		okbutton2.addListener(new ClickListener() {
			public void clicked ( InputEvent event, float xbut, float ybut) {
				if(Save.gd.soundEnabled)click.play();
				languageButton.setChecked(false);
				okbutton2.setChecked(false);
				langmenu.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				optMenu.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(), alpha(1, 0.1f, fade) ));
			}
		});

		okbutton3.addListener(new ClickListener() {
			public void clicked ( InputEvent event, float xbut, float ybut) {
				if(Save.gd.soundEnabled)click.play();
				hallOfButton.setChecked(false);
				okbutton3.setChecked(false);
				root.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(), alpha(1, 0.1f, fade) ));
				hofmenu.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
			}
		});
		
		musicButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				Save.gd.musicEnabled=musicButton.isChecked();
			//	if(musicButton.isChecked()) music.play();
			//	else music.stop();
				Save.save();
			}
		});
		
		soundButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(!Save.gd.soundEnabled)click.play();
				Save.gd.soundEnabled=soundButton.isChecked();
				if(soundButton.isChecked()) prop.play();
				else prop.stop();
				Save.save();
			}
		});
		
		okbutton.addListener(new ClickListener() {
			public void clicked ( InputEvent event, float xbut, float ybut) {
				if(Save.gd.soundEnabled)click.play();
				optionButton.setChecked(false);
				okbutton.setChecked(false);
				optMenu.addAction(sequence(alpha(0, 0.1f, fade), com.badlogic.gdx.scenes.scene2d.actions.Actions.hide()));
				root.addAction(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.show(), alpha(1, 0.1f, fade) ));
			}
		});
		
		frButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				if(Save.gd.lang!=1) {
					Save.gd.lang=1;
					Save.save();
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				}
			}
		});
		
		enButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				if(Save.gd.lang!=0) {
					Save.gd.lang=0;
					Save.save();
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				}
				
			}
		});
		
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
		
		animtimer+=delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		float a=stage.getRoot().getColor().a;
		batch.setColor(a, a, a, 1);
		bg1.setColor(a, a, a, a);
		bg2.setColor(a, a, a, a);
		float[] colorshield = new float[]{1*a*a,0.2f*a*a,0.047f*a*a};
		propEffect.getEmitters().get(0).getTint().setColors(colorshield);
		batch.setProjectionMatrix(camera.combined);
		
		playerY+=vy*delta;
		
		if(animtimer>5){
			range=MathUtils.random(-1, 7);
			animtimer=0;
		}
		if(playerY> range && vy>-100) {
			vy-=0.5f;
		}
		if(playerY<-range && vy<100){
			vy+=0.5f;
		}
		float scale=5;
		camera.position.x=playerX+1.25f*scale;
		camera.position.y=0;
		viewport.apply();
		
		propEffect.setPosition(playerX, playerY+0.3f*1.6f);
		propEffect.getEmitters().get(0).getAngle().setHighMax(180+vy*1.f+20);
		propEffect.getEmitters().get(0).getAngle().setLowMax (180+vy*1.f-20);
		propEffect.getEmitters().get(0).getAngle().setHighMin(180+vy*1.f);
		propEffect.getEmitters().get(0).getAngle().setLowMin (180+vy*1.f);
		
		
		
		float bodyx = playerX - .5f*scale;
		float bodyy = playerY - .2f*scale*1.3f;
		float bodywidth  = (1.5f +  1f)*scale;
		float bodyheight = (0.5f+ .4f*1.6f)*scale;
		
		float ratio= (float)propRegion.getRegionHeight()/ (float)propRegion.getRegionWidth();
		float propwidth = 0.7f*scale;
		float propheight = ratio*propwidth;
		float propx = playerX+bodywidth*0.29f-.5f*scale-propwidth*0.818f;
		float propy = playerY+bodyheight*0.3f-.2f*scale-propheight/2;
		
		ratio= (float)nanakiRegion.getRegionHeight()/ (float)nanakiRegion.getRegionWidth();
		float nanaheight = bodyheight/2f/1.3f;
		float nanawidth  = nanaheight/ratio;
		float nanax = playerX+bodywidth/3.2f;
		float nanay = playerY+bodyheight/9.4f;
		
		float pawwidth  = bodyheight/2.2f/1.3f;
		float pawx = playerX+bodywidth/2.8f;
		float pawy = vy>0? playerY-bodyheight/12f/1.6f : playerY-bodyheight/12f/1.6f-vy/100;
		
		float sticwidth  = bodyheight/2.2f/1.3f;
		float sticx = playerX+bodywidth/2.2f-vy/100;
		float sticy = playerY-bodyheight/12f/1.3f;
		
		ratio= (float)weapRegion.getRegionHeight()/ (float)weapRegion.getRegionWidth();
		float weapwidth = 0.4f*scale;
		float weapheight = ratio*weapwidth;
		float weapx = playerX+1.5f*scale+weapwidth/7;
		float weapy = playerY-weapheight/2;
		
		float wingy      = bodyy+.1f*scale;//   	+animsens*4*delta/50;
		float wingheight = bodyheight ;//  -animsens*4*delta/10;
		
		batch.begin();
		//draw background
		bgAnimtimer+=1*delta;
		bg1.setFlip(false, false);
		bg1.setPosition(camera.position.x-bgAnimtimer- 9*16/9,-9f);
		bg1.draw(batch);
		bg1.setPosition(camera.position.x-bgAnimtimer+ 9*16/9,-9f);
		bg1.draw(batch);
		bg2.setPosition(camera.position.x-bgAnimtimer+27*16/9,-9f);
		bg2.draw(batch); 
		bg1.setFlip(true, false);
		bg1.setPosition(camera.position.x-bgAnimtimer+45*16/9,-9f);
		bg1.draw(batch);
		bg1.setPosition(camera.position.x-bgAnimtimer+63*16/9,-9f);
		bg1.draw(batch);
		
		if(camera.position.x-bgAnimtimer+63*16/9<-60) bgAnimtimer=-40;
				
		//prop effect
		if(a>=0.05)propEffect.draw(batch, delta);
		
		batch.draw(nanakiRegion, nanax, nanay,nanawidth,nanaheight);
		batch.draw(nanapawR, pawx , pawy , pawwidth *0.2f,pawwidth/2f   ,pawwidth,pawwidth,1,1, vy*1);
		batch.draw(stickR  , sticx, sticy, sticwidth*0.8f,sticwidth*0.2f,pawwidth,pawwidth,1,1,-vy*1);
		//rotated
		
		batch.draw(propRegion  , propx, propy, propwidth*0.818f, propwidth/2f, propwidth, propheight,1,1, vy*2f);
		
		//not rotated
		batch.draw(playerRegion, bodyx, bodyy, bodywidth,bodyheight);
		batch.draw(wingRegion  , bodyx, wingy, bodywidth, wingheight);
		batch.draw(weapRegion  , weapx, weapy, weapwidth/6     , weapheight/2, weapwidth, weapheight,1,1, -vy);
		batch.end();
		stage.act();
		stage.getViewport().apply(true);
		stage.draw();
		
		//Propeller
		prop.setVolume(Math.abs(vy)/(100)+0.01f);
	
	}
	
	public void resize (int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height, true);
		root.invalidateHierarchy();
	}

	@Override
	public void hide() {
		prop.stop();
		click.dispose();
		playSound.dispose();
		prop.dispose();
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
