package quentin.jeu.cat.tuto;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.screens.Starmap;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

/** The user interface displayed on top of the game (menu, health bar, splash screens). */
public class UItt extends InputAdapter {
	
	private Viewtt view;
	private Modeltt model;
	
	private float uiscale;
	
	public  Stage stage, pausedstage;
	private Skin skin;
	public Table menu;
	public Table gameoverTable, quit;
	private static float pad=5;
	private Label gover;
	public  TextButton pauseButton;
	private TextButton mapButton, resumButton, soundButton, razButton;
	private ProgressBar healthBar,weaponBar;
	private Group dialog, tuto ;
	private Image kuro, nanak, arrow, arrow2, shoot, arrow3, arrow4;
	public Image  black;
	private Label kuro1, kuro2,kuro3, kuro4, kuro5, kuro6, kuro7, kuro8;
	private Label kuro9, kuro10, nanak3;
	private Label nanak1, nanak2;
	private Label moveup, moved, moveaccel, shoottxt, hpbartxt, weapbartxt;
	private boolean play, pausedui=false;
	private Array<Image> pathImages;
	private static float sw=Gdx.graphics.getWidth(), sh=Gdx.graphics.getHeight();
	private Music prop;
	
	
	
	private int windowWidth, windowHeight;

	public UItt (final Viewtt viewtt) {
		this.view = viewtt;
		this.model = viewtt.model;

		prop= Gdx.audio.newMusic(Gdx.files.internal("sounds/propulseur.wav"));
		prop.setLooping(true);
		prop.setVolume(0);
		if(Save.gd.soundEnabled) prop.play();
		
		stage = new Stage(new ScreenViewport());
		pausedstage = new Stage(new ScreenViewport());
		loadSkin();
		createdialog();
		create();
		layout();
		events();
	}

	private void createdialog() {
		
		dialog=new Group();
		tuto=new Group();
		Image diaIm1=new Image(skin.newDrawable("white", Color.BLACK));
		diaIm1.getColor().a=0.9f;
		diaIm1.setSize(stage.getWidth(), stage.getHeight()/5);
		diaIm1.setPosition(0, 0);
		Image diaIm2=new Image(skin.newDrawable("white",new Color(0x5287ccff)));
		diaIm2.getColor().a=0.2f;
		diaIm2.setSize(stage.getWidth()-4*pad, stage.getHeight()/5-4*pad);
		diaIm2.setPosition(2*pad, 2*pad);
		
		kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
		kuro.setPosition(2*pad, 2*pad);
		kuro.setSize(stage.getHeight()/5-4*pad, stage.getHeight()/5-4*pad);
		
		nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
		nanak.setPosition(2*pad, 2*pad);
		nanak.setSize(stage.getHeight()/5-4*pad, stage.getHeight()/5-4*pad);
		
		kuro1 =new Label(CatGame.myBundle.get("kuro1"), skin,"txt");
		kuro1.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-kuro1.getHeight()/2);
		kuro2 =new Label(CatGame.myBundle.get("kuro2"), skin,"txt");
		kuro2.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-kuro1.getHeight()/2);
		kuro3 =new Label(CatGame.myBundle.get("kuro3"), skin,"txt");
		kuro3.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-kuro1.getHeight()/2);
		
		nanak1 =new Label(CatGame.myBundle.get("nanak1"), skin,"txt");
		nanak1.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		nanak2 =new Label(CatGame.myBundle.get("nanak2"), skin,"txt");
		nanak2.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		
		kuro4 =new Label(CatGame.myBundle.get("kuro4"), skin,"txt");
		kuro4.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		kuro5 =new Label(CatGame.myBundle.get("kuro5"), skin,"txt");
		kuro5.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		
		kuro6 =new Label(CatGame.myBundle.get("kuro6"), skin,"txt");
		kuro6.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		kuro7 =new Label(CatGame.myBundle.get("kuro7"), skin,"txt");
		kuro7.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		kuro8 =new Label(CatGame.myBundle.get("kuro8"), skin,"txt");
		kuro8.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		
		kuro9 =new Label(CatGame.myBundle.get("kuro9"), skin,"txt");
		kuro9.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		kuro10 =new Label(CatGame.myBundle.get("kuro10"), skin,"txt");
		kuro10.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		
		nanak3 =new Label(CatGame.myBundle.get("nanak3"), skin,"txt");
		nanak3.setPosition(2*pad+stage.getHeight()/5, stage.getHeight()/10-nanak1.getHeight()/2);
		
		pathImages = new Array<Image>();
		for(int i=0; i<40*uiscale;i++){
				Image pathImage = new Image(skin.newDrawable("white", new Color(0x5287ccff)));
				pathImage.setSize(5*uiscale, 15*uiscale);
				pathImage.setPosition(Gdx.graphics.getWidth()/5f-pathImage.getWidth()/2,20*uiscale*i);
				pathImage.addAction(sequence(show(), alpha(1, 1f, fade)));
				pathImages.add(pathImage);
			}
		arrow = new Image(Assets.manager.get(Assets.arrow,Texture.class));
		arrow.setSize(200*uiscale, 200*uiscale);
		arrow.setColor(new Color(0x5287ccff));
		
		moveup=new Label(CatGame.myBundle.get("moveup"), skin);
		moveup.setAlignment(Align.center);
		moveup.setPosition(sw/10-moveup.getWidth()/2, sh/2-moveup.getHeight()/2);
		arrow.setPosition(sw/10-arrow.getWidth()/2, sh/2-moveup.getHeight()+100);
		arrow.addAction(forever(sequence(
				alpha(0,0),
				moveTo(sw/10-arrow.getWidth()/2,sh/2,0),
				alpha(1,0.2f),
				moveTo(sw/10-arrow.getWidth()/2,sh/2+moveup.getHeight()/2,0.5f),
				alpha(0,0.2f)
				)));
		
		moved=new Label(CatGame.myBundle.get("moved"), skin);
		moved.setAlignment(Align.center);
		moved.setPosition(sw/10-moveup.getWidth()/2, sh/2-moveup.getHeight()/2);
		arrow2 = new Image(Assets.manager.get(Assets.arrow,Texture.class));
		arrow2.setSize(200*uiscale, -200*uiscale);
		arrow2.setColor(new Color(0x5287ccff));
		arrow2.setPosition(sw/10-arrow.getWidth()/2, sh/2-moveup.getHeight());
		arrow2.addAction(forever(sequence(
				alpha(0,0),
				moveTo(sw/10-arrow.getWidth()/2,sh/2-moveup.getHeight()/2-arrow2.getHeight()/8,0),
				alpha(1,0.2f),
				moveTo(sw/10-arrow.getWidth()/2,sh/2-moveup.getHeight()-arrow2.getHeight()/8,0.5f),
				alpha(0,0.2f)
				)));
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
			moveaccel =new Label(CatGame.myBundle.get("accelt"), skin);
			moveaccel.setAlignment(Align.center);
			moveaccel.setPosition(sw/2-moveaccel.getWidth()/2, sh/2-moveaccel.getHeight()/2);
			moveaccel.getColor().a=0;
		}
		shoottxt=new Label(CatGame.myBundle.get("shoottxt"), skin);
		shoottxt.setAlignment(Align.center);
		shoottxt.setPosition(sw/2-moveup.getWidth()/2, sh/2-moveup.getHeight()/2);
		shoot=new Image(Assets.manager.get(Assets.target,Texture.class));
		shoot.setSize(100*uiscale, 100*uiscale);
		shoot.setColor(new Color(0x5287ccff));
		shoot.setPosition(sw-sw*4/10, sh/2-shoot.getHeight());
		shoot.addAction(forever((sequence(alpha(0,0),alpha(1,0.2f),delay(0.5f),alpha(0,0.2f)))));
		shoot.addAction(forever(sequence(moveTo(sw-sw*4/10, sh*4/6,1.5f),moveTo(sw-sw*4/10, sh*1/6,1.5f))));
		
		arrow3 = new Image(Assets.manager.get(Assets.arrow,Texture.class));
		arrow3.setSize(200*uiscale, 200*uiscale);
		arrow3.setRotation(-45);
		arrow3.setColor(0.7f,0.1f,0.1f,1);
		arrow3.setPosition(sw-sw/4.5f-200*uiscale, sh-60*uiscale);
		hpbartxt=new Label(CatGame.myBundle.get("hpbar"), skin);
		hpbartxt.setAlignment(Align.center);
		hpbartxt.setPosition(arrow3.getX()+arrow3.getHeight()/2-hpbartxt.getWidth()/2, arrow3.getY()-arrow3.getHeight()/2-20*uiscale+hpbartxt.getHeight()/2);
		
		
		arrow4 = new Image(Assets.manager.get(Assets.arrow,Texture.class));
		arrow4.setSize(200*uiscale, 200*uiscale);
		arrow4.setRotation(-45);
		arrow4.setColor(0.1f,0.1f,0.7f,1);
		arrow4.setPosition(sw-sw/4.5f-200*uiscale, sh-85*uiscale);
		weapbartxt=new Label(CatGame.myBundle.get("weaponbar"), skin);
		weapbartxt.setAlignment(Align.center);
		weapbartxt.setPosition(arrow4.getX()+arrow4.getHeight()/2-weapbartxt.getWidth()/2, arrow4.getY()-arrow4.getHeight()/2-20*uiscale+weapbartxt.getHeight()/2);
		
		
		
		for(int i=0;i<pathImages.size;i++){
			tuto.addActor(pathImages.get(i));
		}
		tuto.addActor(moveup);
		tuto.addActor(arrow);
		tuto.addActor(moved);
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
			tuto.addActor(moveaccel);
		}
		tuto.addActor(arrow2);
		tuto.addActor(shoottxt);
		tuto.addActor(shoot);
		tuto.addActor(hpbartxt);
		tuto.addActor(arrow3);
		tuto.addActor(weapbartxt);
		tuto.addActor(arrow4);
		
		moveup.getColor().a=0;
		arrow.setVisible(false);
		moved.getColor().a=0;
		arrow2.setVisible(false);
		shoottxt.getColor().a=0;
		shoot.setVisible(false);
		hpbartxt.getColor().a=0;
		arrow3.getColor().a=0;
		weapbartxt.getColor().a=0;
		arrow4.getColor().a=0;
		tuto.setVisible(false);
		
		
		dialog.addActor(diaIm1);
		dialog.addActor(diaIm2);
		dialog.addActor(kuro);
		dialog.addActor(kuro1);
		dialog.addActor(kuro2);
		dialog.addActor(kuro3);
		dialog.addActor(kuro4);
		dialog.addActor(kuro5);
		dialog.addActor(kuro6);
		dialog.addActor(kuro7);
		dialog.addActor(kuro8);
		dialog.addActor(kuro9);
		dialog.addActor(kuro10);
		dialog.addActor(nanak);
		dialog.addActor(nanak1);
		dialog.addActor(nanak2);
		dialog.addActor(nanak3);
		dialog.setColor(1, 1, 1, 0);
		dialog.setVisible(false);
		
		kuro.getColor().a=0;
		kuro1.getColor().a=0;
		kuro2.getColor().a=0;
		kuro3.getColor().a=0;
		nanak.getColor().a=0;
		nanak1.getColor().a=0;
		nanak2.getColor().a=0;
		kuro4.getColor().a=0;
		kuro5.getColor().a=0;
		kuro6.getColor().a=0;
		kuro7.getColor().a=0;
		kuro8.getColor().a=0;
		kuro9.getColor().a=0;
		kuro10.getColor().a=0;
		nanak3.getColor().a=0;
		
	}
	
	private void create () {
		
		gameoverTable=new Table(skin);
		gameoverTable.setFillParent(true);
		gover = new Label(CatGame.myBundle.get("gover1"), skin);
		gover.setAlignment(Align.center);
		
		quit=new Table(skin);
		quit.setFillParent(true);
		
		weaponBar = new ProgressBar(0, Playertt.maxShots2heat, Playertt.heatperShot, false, skin,"blue");
		weaponBar.setAnimateDuration(0.3f);
		weaponBar.setAnimateInterpolation(fade);
		
		healthBar = new ProgressBar(0, Playertt.hpStart, 1, false, skin, "red");
		healthBar.setAnimateDuration(0.3f);
		healthBar.setAnimateInterpolation(fade);
		
		mapButton   = button (CatGame.myBundle.get("map"), true);
		resumButton = button (CatGame.myBundle.get("resume"), true);
		soundButton = button (CatGame.myBundle.get("sound") , true);
		razButton   = button (CatGame.myBundle.get("restart"), false);
		soundButton.setChecked(Save.gd.soundEnabled);

		pauseButton = button("Menu", true);
		pauseButton.getColor().a = 0.3f;
	}

	private void layout () {
		Table buttons = new Table();
		buttons.defaults().uniformX().fillX();
		buttons.defaults().padTop(5);
		
		buttons.add(resumButton).row();
		buttons.add(soundButton).row();
		//buttons.add(razButton).row();

		menu = new Table(skin);
		stage.addActor(menu);
		menu.setFillParent(true);
		//menu.defaults().space(20);
		menu.center().add("PAUSE").center().row();
		menu.center().add(buttons).center().padTop(10).colspan(1);
		menu.setVisible(false);

		pauseButton.setSize(100*uiscale, 40*uiscale);
		pauseButton.setPosition(pad*uiscale, Gdx.graphics.getHeight()-pauseButton.getHeight()-pad*uiscale);
		stage.addActor(pauseButton);
		healthBar.setSize(Gdx.graphics.getWidth()/4.5f, 20*uiscale);
		healthBar.setPosition(Gdx.graphics.getWidth()-healthBar.getWidth()-pad*uiscale, Gdx.graphics.getHeight()-healthBar.getHeight()-pad*uiscale);
		stage.addActor(healthBar);
		weaponBar.setSize(Gdx.graphics.getWidth()/4.5f, 20*uiscale);
		weaponBar.setPosition(Gdx.graphics.getWidth()-healthBar.getWidth()-pad*uiscale, Gdx.graphics.getHeight()-2*(healthBar.getHeight()+pad*uiscale));
		stage.addActor(weaponBar);
		
		//back button handle
		quit.center().add(CatGame.myBundle.get("exit")).colspan(2).row();
		TextButton okbutton = button(CatGame.myBundle.get("yes"),true);
		okbutton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		final TextButton nobutton = button(CatGame.myBundle.get("no"),true);
		nobutton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				model.timeScale=1;
				quit.addAction(sequence(fadeOut(1),hide()));
				pauseButton.setTouchable(Touchable.enabled);
				nobutton.setChecked(false);
			}
		});
		
		quit.setVisible(false);
		quit.defaults().uniformX().fillX();
		quit.center().add(okbutton).pad(10);
		quit.center().add(nobutton).pad(10);
		stage.addActor(quit);
		
		quit.setVisible(false);
		quit.defaults().uniformX().fillX();
		quit.center().add(okbutton).pad(10);
		quit.center().add(nobutton).pad(10);
		stage.addActor(quit);
		pausedstage.addActor(dialog);
		pausedstage.addActor(tuto);
		
	}

	private void events () {
		
		pauseButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				menu.clearActions();
				menu.getColor().a = menu.isVisible() ? 1 : 0;
				if (menu.isVisible()){
					model.timeScale=1;
					pausedui=false;
					menu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				}
				else{
					model.timeScale=0;
					pausedui=true;
					menu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
				}
				pauseButton.getColor().a = menu.isVisible() ? 0.3f : 1;
			}
		});
		
		mapButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Save.gd.playerGo=Save.gd.playerPos;
				((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
			}
		});
		
		resumButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				pauseButton.setChecked(false);
				resumButton.setChecked(false);
			}
		});

		soundButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Save.gd.soundEnabled=soundButton.isChecked();
				if(soundButton.isChecked()) prop.play();
				else prop.stop();
				Save.save();
			}
		});
		
		razButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				model.controller.restart();
				pauseButton.setTouchable(Touchable.enabled);
				model.win=false;
				gameoverTable.clearActions();
				gameoverTable.getColor().a = 1;
				gameoverTable.clear();
				gameoverTable.addAction(sequence(fadeOut(1, fade), removeActor()));
				pauseButton.setChecked(false);
			}
		});
		
	}

	void loadSkin () {
		skin = new Skin();
		if(Gdx.graphics.getHeight()<1000){
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			skin.add("txt", new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			uiscale=1;
		}
		else{
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			skin.add("txt", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			uiscale=2;
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
		labelStyle.font = skin.getFont("txt");
		skin.add("txt", labelStyle);

		ProgressBarStyle progressBarStyle = new ProgressBarStyle();
		progressBarStyle.background = skin.newDrawable("white", new Color(0.25f, 0.25f, 0.25f, 0.66f));
		progressBarStyle.background.setMinHeight(20*uiscale);
		progressBarStyle.knobBefore = skin.newDrawable("white", Color.CLEAR);
		progressBarStyle.knobBefore.setMinHeight(20*uiscale);
		progressBarStyle.knobAfter = skin.newDrawable("white", new Color(1, 0, 0, 0.66f));
		progressBarStyle.knobAfter.setMinHeight(20*uiscale);
		skin.add("red", progressBarStyle);
		
		ProgressBarStyle progressBarStyle1 = new ProgressBarStyle();
		progressBarStyle1.background = skin.newDrawable("white", new Color(0.25f, 0.25f, 0.25f, 0.66f));
		progressBarStyle1.background.setMinHeight(20*uiscale);
		progressBarStyle1.knobBefore = skin.newDrawable("white", Color.CLEAR);
		progressBarStyle1.knobBefore.setMinHeight(20*uiscale);
		progressBarStyle1.knobAfter = skin.newDrawable("white", new Color(0, 0, 1, 0.66f));
		progressBarStyle1.knobAfter.setMinHeight(20*uiscale);
		skin.add("blue", progressBarStyle1);
	}

	private TextButton button (String text, boolean toggle) {
		TextButton button = new TextButton(text, skin, toggle ? "toggle" : "default");
		button.pad(2, 12, 2, 12);
		return button;
	}

	public void render () {
		
		prop.setVolume(Math.abs(model.player.velocity.y)/(5*Save.gd.speed)+0.1f);
		
		if(!play) play();
		
		if(black!=null && black.getColor().a==1){
			prop.stop();
			((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
		}
			
		
		
		view.touched=false;

		healthBar.setValue(Playertt.hpStart - model.player.hp);
		weaponBar.setValue(model.player.view.burstShots *Playertt.heatperShot);

		
		if(!pausedui){
			pausedstage.act();
			
			
		}
		pausedstage.getViewport().apply(true);
		pausedstage.draw();
		stage.act();
		stage.getViewport().apply(true);
		stage.draw();
	}
	
	public void playMSdead(){
		kuro9.setColor(1,1,1,0);
		dialog.addAction  (sequence(show(),  alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade),hide()   ));
		kuro.addAction    (sequence(         alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade)));
		kuro10.addAction   (sequence(        alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade)));
	}
	
	
	
	public void playKNdead(){
		kuro10.setColor(1,1,1,0);
		dialog.addAction  (sequence(show(),  alpha(1, 0.5f, fade), delay(7), alpha(0, 0.5f, fade),hide()   ));
		kuro.addAction    (sequence(         alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade)));
		kuro9.addAction   (sequence(         alpha(1, 0.5f, fade) , delay(3), alpha(0, 0.5f, fade)));
		nanak.addAction   (sequence(   delay(3.5f),     alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade)));
		nanak3.addAction  (sequence(  delay(3.5f),     alpha(1, 0.5f, fade), delay(3), alpha(0, 0.5f, fade)));
		
	}

	private void play() {
		dialog.addAction  (sequence(delay(3),show(), alpha(1, 0.5f, fade), delay(8), alpha(0, 0.5f, fade),hide()   ));
		kuro.addAction    (sequence(delay(3),        alpha(1, 0.5f, fade), delay(9), alpha(0, 0.5f, fade)));
		
		kuro1.addAction   (sequence(delay(3),        alpha(1, 0.5f, fade), delay(2), alpha(0, 0.5f, fade)));
		kuro2.addAction   (sequence(delay(6),        alpha(1, 0.5f, fade), delay(2), alpha(0, 0.5f, fade)));
		kuro3.addAction   (sequence(delay(9),        alpha(1, 0.5f, fade), delay(2), alpha(0, 0.5f, fade)));
	

	 if(moveaccel==null){
		tuto.addAction      (sequence( delay(12),show(), alpha(1, 0.5f, fade)));
		
		arrow.addAction     (sequence( delay(12),              show(), delay(3),hide()));
		moveup.addAction    (sequence( delay(12),              alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		arrow2.addAction    (sequence( delay(12),delay(3.5f) , show(), delay(3),hide()));
		moved.addAction     (sequence( delay(12),delay(3.5f) , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		shoot.addAction     (sequence( delay(12),delay(7f)   , show(), delay(3),hide()));
		shoottxt.addAction  (sequence( delay(12),delay(7f)   , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		arrow3.addAction    (sequence( delay(12),delay(10.5f), alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		hpbartxt.addAction  (sequence( delay(12),delay(10.5f), alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		arrow4.addAction    (sequence( delay(12),delay(14f)  , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		weapbartxt.addAction (sequence( delay(12),delay(14f)  , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		
		tuto.addAction      (sequence( delay(12),delay(17.5f), alpha(0, 0.5f, fade),hide()));
	 }
	 else{
		 tuto.addAction      (sequence( delay(12),show(), alpha(1, 0.5f, fade)));
			
			arrow.addAction     (sequence( delay(12),              show(), delay(3),hide()));
			moveup.addAction    (sequence( delay(12),              alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			arrow2.addAction    (sequence( delay(12),delay(3.5f) , show(), delay(3),hide()));
			moved.addAction     (sequence( delay(12),delay(3.5f) , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			moveaccel.addAction (sequence( delay(12),delay(7f)   , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			shoot.addAction     (sequence( delay(12),delay(10.5f), show(), delay(3),hide()));
			shoottxt.addAction  (sequence( delay(12),delay(10.5f), alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			arrow3.addAction    (sequence( delay(12),delay(13f)  , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			hpbartxt.addAction  (sequence( delay(12),delay(13f)  , alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			arrow4.addAction    (sequence( delay(12),delay(15.5f), alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			weapbartxt.addAction(sequence( delay(12),delay(15.5f), alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			
			tuto.addAction      (sequence( delay(12),delay(17.5f), alpha(0, 0.5f, fade),hide()));
	 }
		
		dialog.addAction    (sequence(delay(30) ,show(), alpha(1, 0.5f, fade), delay(10), alpha(1, 0.5f, fade),hide()   ));
		
		nanak.addAction     (sequence(delay(30),         alpha(1, 0.5f, fade), delay(5),alpha(0, 0.5f, fade)));
		nanak1.addAction    (sequence(delay(30),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		nanak2.addAction    (sequence(delay(33),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		
		kuro .addAction     (sequence(delay(36),         alpha(1, 0.5f, fade), delay(5),alpha(0, 0.5f, fade)));
		kuro4.addAction     (sequence(delay(36),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		kuro5.addAction     (sequence(delay(39),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		
		dialog.addAction    (sequence(delay(48) ,show(), alpha(1, 0.5f, fade), delay(7), alpha(1, 0.5f, fade),hide()   ));
		kuro.addAction      (sequence(delay(48),         alpha(1, 0.5f, fade), delay(7),alpha(0, 0.5f, fade)));
		kuro6.addAction     (sequence(delay(48),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		kuro7.addAction     (sequence(delay(51),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		kuro8.addAction     (sequence(delay(54),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
		
		play=true;
		
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
		menu.invalidateHierarchy();
	}

	void toggleFullscreen () {
		if (Gdx.graphics.isFullscreen())
			Gdx.graphics.setDisplayMode(windowWidth, windowHeight, false);
		else {
			windowWidth = Gdx.graphics.getWidth();
			windowHeight = Gdx.graphics.getHeight();
			DisplayMode desktopDisplayMode = Gdx.graphics.getDesktopDisplayMode();
			Gdx.graphics.setDisplayMode(desktopDisplayMode.width, desktopDisplayMode.height, true);
		}
	}

	public void showGO (boolean won) {
		
		black=new Image(skin.newDrawable("white", Color.BLACK));
		black.getColor().a=0f;
		black.setSize(stage.getWidth(), stage.getHeight());
		black.setPosition(0, 0);
		black.addAction(alpha(1,4));
		gameoverTable.addActor(black);
		stage.addActor(gameoverTable);
		gameoverTable.clearActions();
		gameoverTable.getColor().a = 1;
		gameoverTable.addAction(show());
	}
	
	public void showQuit () {
		pauseButton.setTouchable(Touchable.disabled);
		pauseButton.setChecked(false);
		model.timeScale=0;
		quit.clearActions();
		quit.addAction(sequence(fadeIn(0),show()));
	}
	
	void dispose(){
		prop.dispose();
		stage.dispose();
	}
}
