package quentin.jeu.cat.ui;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.catinvader.Model;
import quentin.jeu.cat.catinvader.View;
import quentin.jeu.cat.screens.Outro;
import quentin.jeu.cat.screens.Starmap;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

public class UI extends InputAdapter {
	private View view;
	private Model model;
	
	private float uiscale;
	//private ShapeRenderer shapes;
	public  Stage stage;
	private Skin skin;
	public Table menu;
	public Table gameoverTable, quit;
	private Group buymenu;
	public  TextButton buy1, buy2, buy3, backbt;
	private static float pad=5;
	private Label gover, makiwont,metalwont, cost, maki, score;
	public  TextButton pauseButton;
	private TextButton mapButton1, mapButton2,  resumButton, musicButton, soundButton;
	//private TextButton debugButton;
	private Button razButton, weap1B, weap2B, weap3B;
	private ProgressBar healthBar, shieldBar,weaponBar1, weaponBar2, weaponBar3;
	private float rewardTimer;
	private int makiwon,metalwon,makiwoni,metalwoni;
	public int weapon1, weapon2, weapon3;
	private Sound no,click;
	public Music prop, music;
	public Image black;
	
//	private int windowWidth, windowHeight;

	public UI (final View view) {
		this.view = view;
		this.model = view.model;

		no    = Gdx.audio.newSound(Gdx.files.internal("sounds/no.wav"));
		click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		prop= Gdx.audio.newMusic(Gdx.files.internal("sounds/propulseur.wav"));
		prop.setLooping(true);
		prop.setVolume(0);
		int musicchance = MathUtils.random(1);
		if(Save.gd.playerPos>=CatGame.EVENTBOSSKURO && Save.gd.playerGo>Save.gd.playerPos && !Save.gd.eventdefeatkuro)
			musicchance=0;
		if(musicchance==0)
			music= Gdx.audio.newMusic(Gdx.files.internal("sounds/musiclvl1.mp3"));
		else
			music= Gdx.audio.newMusic(Gdx.files.internal("sounds/musiclvl2.mp3"));
		music.setLooping(true);
		music.setVolume(0.25f);
		if(Save.gd.soundEnabled){
			prop.play();
		}
		if(Save.gd.musicEnabled){
			music.play();
		}
		
		//shapes = new ShapeRenderer();

		stage = new Stage(new ScreenViewport());
		loadSkin();
		create();
		layout();
		events();
	}

	private void create () {
		
		score=new Label("100 000", skin);
		score.setAlignment(Align.center);
		score.getColor().a=0.5f;
		
		makiwont =new Label(" : +0",skin);
		metalwont=new Label(" : +0",skin);
		gameoverTable=new Table(skin);
		gameoverTable.setFillParent(true);
		gover = new Label(CatGame.myBundle.get("gover1"), skin, "big");
		gover.setAlignment(Align.center);
		new Label("- 5",skin);
		quit=new Table(skin);
		quit.setFillParent(true);
		
		
		healthBar = new ProgressBar(0, Save.gd.hp, 0.05f, false, skin, "red");
		healthBar.setAnimateDuration(0.03f);
		healthBar.setAnimateInterpolation(fade);
		
		if(Save.gd.shieldOwn)
			shieldBar = new ProgressBar(0, Save.gd.shieldhp, 0.001f, false, skin, "red");
		
		
	//	debugButton = button ("Debug" , true);
		mapButton1   = button (CatGame.myBundle.get("map"), true);
		mapButton2   = button (CatGame.myBundle.get("map"), true);
		resumButton = button (CatGame.myBundle.get("resume"), false);
		musicButton = button (CatGame.myBundle.get("music") , true);
		soundButton = button (CatGame.myBundle.get("sound") , true);
		
		cost= new Label(Integer.toString(view.cost), skin);
		razButton    = new Button (skin);
		razButton.add(" ");
		razButton.add(CatGame.myBundle.get("restart")).center();
		razButton.add("  (-");
		razButton.add(cost);
		Image makiIm=new Image(Assets.manager.get(Assets.maki,Texture.class));
		razButton.add(makiIm).size(30*uiscale).pad(pad);
		razButton.add(") ").pad(pad);
		
		if (Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1 && Save.gd.weapon3!=-1) {
			//weapon button 1
			weap1B = new Button(skin, "toggle");
			if (Save.gd.weapon1 == CatGame.RG) {
				Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
				weap1B.add(RG).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.RG;
				weaponBar1 = new ProgressBar(0, model.player.RGmaxShots2heat,1, false, skin, "blue");
				weaponBar1.setAnimateDuration(0.3f);
				weaponBar1.setAnimateInterpolation(fade);
			} else if (Save.gd.weapon1 == CatGame.SM) {
				Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
				weap1B.add(SM).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.SM;
				weaponBar1 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
				weaponBar1.setAnimateDuration(0.3f);
				weaponBar1.setAnimateInterpolation(fade);
			}else if (Save.gd.weapon1 == CatGame.LZ) {
				Image laser = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
				weap1B.add(laser).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.LZ;
				weaponBar1 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			}
			weap1B.getColor().a = 0.5f;
			
			//weapon button 2
			weap2B = new Button(skin, "toggle");
			if (Save.gd.weapon2 == CatGame.RG) {
				Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
				weap2B.add(RG).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.RG;
				weaponBar2 = new ProgressBar(0, model.player.RGmaxShots2heat,1, false, skin, "blue");
				weaponBar2.setAnimateDuration(0.3f);
				weaponBar2.setAnimateInterpolation(fade);
			} else if (Save.gd.weapon2 == CatGame.SM) {
				Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
				weap2B.add(SM).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.SM;
				weaponBar2 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
				weaponBar2.setAnimateDuration(0.3f);
				weaponBar2.setAnimateInterpolation(fade);
			}
			else if (Save.gd.weapon2 == CatGame.LZ) {
				Image laser = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
				weap2B.add(laser).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.LZ;
				weaponBar2 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			}
			weap2B.getColor().a = 0.5f;
			//weapon button 3
			weap3B = new Button(skin, "toggle");
			if (Save.gd.weapon3 == CatGame.RG) {
				Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
				weap3B.add(RG).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon3 = CatGame.RG;
				weaponBar3 = new ProgressBar(0, model.player.RGmaxShots2heat,1, false, skin, "blue");
				weaponBar3.setAnimateDuration(0.3f);
				weaponBar3.setAnimateInterpolation(fade);
			} else if (Save.gd.weapon3 == CatGame.SM) {
				Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
				weap3B.add(SM).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon3 = CatGame.SM;
				weaponBar3 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
				weaponBar3.setAnimateDuration(0.3f);
				weaponBar3.setAnimateInterpolation(fade);
			}
			else if (Save.gd.weapon3 == CatGame.LZ) {
				Image laser = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
				weap3B.add(laser).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon3 = CatGame.LZ;
				weaponBar3 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			}
			weap3B.getColor().a = 0.5f;
			
			new ButtonGroup<Button>(weap1B, weap2B, weap3B);
			weap1B.setChecked(true);
		}
		
		//only 2 weapon
		else if ((Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1) || 
				 (Save.gd.weapon2!=-1 && Save.gd.weapon3!=-1) || 
				 (Save.gd.weapon1!=-1 && Save.gd.weapon3!=-1)) {
			if(Save.gd.weapon1==-1){
				Save.gd.weapon1=Save.gd.weapon2;
				Save.gd.weapon2=Save.gd.weapon3;
				Save.gd.weapon3=-1;
			}
			else if(Save.gd.weapon2==-1){
				Save.gd.weapon2=Save.gd.weapon3;
				Save.gd.weapon3=-1;
			}
			//weapon button 1
			weap1B = new Button(skin, "toggle");
			if (Save.gd.weapon1 == CatGame.RG) {
				Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
				weap1B.add(RG).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.RG;
				weaponBar1 = new ProgressBar(0, model.player.RGmaxShots2heat,1, false, skin, "blue");
				weaponBar1.setAnimateDuration(0.3f);
				weaponBar1.setAnimateInterpolation(fade);
			} else if (Save.gd.weapon1 == CatGame.SM) {
				Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
				weap1B.add(SM).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.SM;
				weaponBar1 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
				weaponBar1.setAnimateDuration(0.3f);
				weaponBar1.setAnimateInterpolation(fade);
			}else if (Save.gd.weapon1 == CatGame.LZ) {
				Image laser = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
				weap1B.add(laser).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon1 = CatGame.LZ;
				weaponBar1 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			}
			weap1B.getColor().a = 0.5f;
			
			//weapon button 2
			weap2B = new Button(skin, "toggle");
			if (Save.gd.weapon2 == CatGame.RG) {
				Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
				weap2B.add(RG).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.RG;
				weaponBar2 = new ProgressBar(0, model.player.RGmaxShots2heat,1, false, skin, "blue");
				weaponBar2.setAnimateDuration(0.3f);
				weaponBar2.setAnimateInterpolation(fade);
			} else if (Save.gd.weapon2 == CatGame.SM) {
				Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
				weap2B.add(SM).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.SM;
				weaponBar2 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
				weaponBar2.setAnimateDuration(0.3f);
				weaponBar2.setAnimateInterpolation(fade);
			}
			else if (Save.gd.weapon2 == CatGame.LZ) {
				Image laser = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
				weap2B.add(laser).size(70 * uiscale, 30 * uiscale).pad(pad);
				weapon2 = CatGame.LZ;
				weaponBar2 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			}
			weap2B.getColor().a = 0.5f;
			new ButtonGroup<Button>(weap1B, weap2B);
			weap1B.setChecked(true);
		}
		//only 1 weap equip
		else if(Save.gd.weapon1==CatGame.RG || Save.gd.weapon2==CatGame.RG || Save.gd.weapon3==CatGame.RG){
			weapon1 = CatGame.RG;
			view.activWeap = CatGame.RG;
			weaponBar1 = new ProgressBar(0, model.player.RGmaxShots2heat,
					1, false, skin, "blue");
			weaponBar1.setAnimateDuration(0.3f);
			weaponBar1.setAnimateInterpolation(fade);
		}
		else if(Save.gd.weapon1==CatGame.SM || Save.gd.weapon2==CatGame.SM || Save.gd.weapon3==CatGame.SM){
			weapon1 = CatGame.SM;
			view.activWeap = CatGame.SM;
			weaponBar1 = new ProgressBar(0, model.player.SMmaxShots2heat,1, false, skin, "blue");
			weaponBar1.setAnimateDuration(0.3f);
			weaponBar1.setAnimateInterpolation(fade);
		}
		else if(Save.gd.weapon1==CatGame.LZ || Save.gd.weapon2==CatGame.LZ || Save.gd.weapon3==CatGame.LZ){
			weaponBar1 = new ProgressBar(0, model.player.laserpower, Save.gd.lasermax*2/1000, false, skin, "blue");
			weapon1 = CatGame.LZ;
			view.activWeap = CatGame.LZ;
		}
		
		musicButton.setChecked(Save.gd.musicEnabled);
		soundButton.setChecked(Save.gd.soundEnabled);

		pauseButton = button("Menu", true);
		pauseButton.getColor().a = 0.3f;
		
		createshop();
		
		stage.addActor(buymenu);
		
		//splashImage = new Image();
		//splashImage.setScaling(Scaling.fit);

		//splashTextImage = new Image();
		//splashTextImage.addAction(forever(sequence(fadeOut(0.4f, pow2In), fadeIn(0.4f, pow2Out))));
	}

	private void layout () {
		Table buttons = new Table();
		buttons.defaults().uniformX().fillX();
		buttons.defaults().padTop(5);
		//buttons.add(debugButton).row();
		buttons.add(resumButton).row();
		buttons.add(musicButton).row();
		buttons.add(soundButton).row();
		buttons.add(mapButton2).row();
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
		if(Save.gd.shieldOwn){
			healthBar.setSize(Gdx.graphics.getWidth()/8+ pad * uiscale/2, 20*uiscale);
			healthBar.setPosition(
					Gdx.graphics.getWidth()-healthBar.getWidth()-pad*uiscale,
					Gdx.graphics.getHeight()-healthBar.getHeight()-pad*uiscale);
			stage.addActor(healthBar);
			
			shieldBar.setSize(Gdx.graphics.getWidth() / 8+ pad * uiscale/2, 20 * uiscale);
			shieldBar.setPosition(
					Gdx.graphics.getWidth() - 2* (shieldBar.getWidth() + pad * uiscale),
					Gdx.graphics.getHeight()-healthBar.getHeight()-pad*uiscale);
			stage.addActor(shieldBar);
		}
		else{
			healthBar.setSize(Gdx.graphics.getWidth()/4 + pad* uiscale, 20*uiscale);
			healthBar.setPosition(
					Gdx.graphics.getWidth()-healthBar.getWidth()-pad*uiscale,
					Gdx.graphics.getHeight()-healthBar.getHeight()-pad*uiscale);
			stage.addActor(healthBar);
		}
		////////weapons bar/////////
		if (Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1 && Save.gd.weapon3!=-1) {
			weaponBar1.setSize(Gdx.graphics.getWidth() / 4*1/3f, 20 * uiscale);
			weaponBar1.setPosition(
					Gdx.graphics.getWidth() - 3* (weaponBar1.getWidth() + pad * uiscale),
					Gdx.graphics.getHeight() - 2* (weaponBar1.getHeight() + pad * uiscale));
			stage.addActor(weaponBar1);
			weaponBar2.setSize(Gdx.graphics.getWidth() / 4*1/3f, 20 * uiscale);
			weaponBar2.setPosition(
					Gdx.graphics.getWidth() - 2*(weaponBar2.getWidth() + pad* uiscale), 
					Gdx.graphics.getHeight() - 2* (weaponBar2.getHeight() + pad * uiscale));
			stage.addActor(weaponBar2);
			weaponBar3.setSize(Gdx.graphics.getWidth() / 4*1/3f, 20 * uiscale);
			weaponBar3.setPosition(
					Gdx.graphics.getWidth() - weaponBar3.getWidth() - pad* uiscale, 
					Gdx.graphics.getHeight() - 2* (weaponBar3.getHeight() + pad * uiscale));
			stage.addActor(weaponBar3);
		
			weap3B.setSize(100*uiscale, 40*uiscale);
			weap3B.setPosition(stage.getWidth()-weap3B.getWidth()-pad*uiscale, pad*uiscale);
			stage.addActor(weap3B);
			weap2B.setSize(100*uiscale, 40*uiscale);
			weap2B.setPosition(stage.getWidth()-2*(weap2B.getWidth()+pad*uiscale), pad*uiscale);
			stage.addActor(weap2B);
			weap1B.setSize(100*uiscale, 40*uiscale);
			weap1B.setPosition(stage.getWidth()-3*(weap1B.getWidth()+pad*uiscale), pad*uiscale);
			stage.addActor(weap1B);
		}
		//2 weapon equip
		else if (Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1) {
			weaponBar1.setSize(Gdx.graphics.getWidth() / 8, 20 * uiscale);
			weaponBar1.setPosition(
					Gdx.graphics.getWidth() - 2* (weaponBar1.getWidth() + pad * uiscale),
					Gdx.graphics.getHeight() - 2* (weaponBar1.getHeight() + pad * uiscale));
			stage.addActor(weaponBar1);
			weaponBar2.setSize(Gdx.graphics.getWidth() / 8, 20 * uiscale);
			weaponBar2.setPosition(
					Gdx.graphics.getWidth() - weaponBar2.getWidth() - pad* uiscale, 
					Gdx.graphics.getHeight() - 2* (weaponBar2.getHeight() + pad * uiscale));
			stage.addActor(weaponBar2);
		
			weap2B.setSize(100*uiscale, 40*uiscale);
			weap2B.setPosition(stage.getWidth()-weap2B.getWidth()-pad*uiscale, pad*uiscale);
			stage.addActor(weap2B);
			weap1B.setSize(100*uiscale, 40*uiscale);
			weap1B.setPosition(stage.getWidth()-2*(weap1B.getWidth()+pad*uiscale), pad*uiscale);
			stage.addActor(weap1B);
		}
		//1 weapon equip
		else{
			weaponBar1.setSize(Gdx.graphics.getWidth() / 4 + pad*uiscale, 20 * uiscale);
			weaponBar1.setPosition(
					Gdx.graphics.getWidth() - 1
							* (weaponBar1.getWidth() + pad * uiscale),
					Gdx.graphics.getHeight() - 2
							* (weaponBar1.getHeight() + pad * uiscale));
			stage.addActor(weaponBar1);
		}
		score.setPosition(stage.getWidth()/2-score.getWidth()/2, stage.getHeight()-pauseButton.getHeight()/2-score.getHeight()/2- pad*uiscale);
		stage.addActor(score);

		
		//back button handle
		quit.center().add(CatGame.myBundle.get("exit")).colspan(2).row();
		TextButton okbutton = button(CatGame.myBundle.get("yes"),true);
		okbutton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(mapButton2.isChecked()){
					((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
					music.stop();
					prop.stop();
				}
				else Gdx.app.exit();
			}
		});
		final TextButton nobutton = button(CatGame.myBundle.get("no"),true);
		nobutton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(mapButton2.isChecked()){
					mapButton2.setChecked(false);
					pauseButton.setChecked(true);
					quit.addAction(sequence(fadeOut(0.3f),hide()));
				}
				else{
					model.timeScale=1;
					quit.addAction(sequence(fadeOut(1),hide()));
					pauseButton.setTouchable(Touchable.enabled);
					nobutton.setChecked(false);
				}
			}
		});
		
		quit.setVisible(false);
		quit.defaults().uniformX().fillX();
		quit.center().add(okbutton).pad(10);
		quit.center().add(nobutton).pad(10);
		stage.addActor(quit);
	}

	private void events () {
		
		pauseButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				menu.clearActions();
				menu.getColor().a = menu.isVisible() ? 1 : 0;
				if (menu.isVisible()){
					model.timeScale=1;
					menu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				}
				else{
					model.timeScale=0;
					menu.addAction(sequence(show(), alpha(1, 0.5f, fade)));
				}
				pauseButton.getColor().a = menu.isVisible() ? 0.3f : 1;
			}
		});
		
		mapButton1.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Save.gd.playerGo=Save.gd.playerPos;
				((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
			}
		});
		mapButton2.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				showQuit();
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
				if(!Save.gd.soundEnabled)click.play();
				Save.gd.soundEnabled=soundButton.isChecked();
				if(soundButton.isChecked()){
					prop.play();
				}
				else{
					prop.stop();
				}
				Save.save();
			}
		});
		musicButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)click.play();
				Save.gd.musicEnabled=musicButton.isChecked();
				if(musicButton.isChecked()){
					music.play();
				}
				else{
					music.stop();
				}
				Save.save();
			}
		});
		
		razButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.maki>=view.cost){
					model.controller.restart();
					Save.gd.maki-=view.cost;
					Save.save();
					pauseButton.setTouchable(Touchable.enabled);
					model.win=false;
					gameoverTable.clearActions();
					gameoverTable.getColor().a = 1;
					gameoverTable.clear();
					gameoverTable.addAction(sequence(fadeOut(1, fade), removeActor()));
					pauseButton.setChecked(false);
				}
				else{
					if(Save.gd.soundEnabled)no.play(1,0.6f,0);
					maki.addAction(sequence(color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f),
							color(Color.RED,0.2f),
							color(Color.WHITE,0.2f)));
					buymenu.addAction(sequence(delay(1.2f),show(), alpha(1, 0.5f, fade)));
					gameoverTable.addAction(sequence(alpha(0, 0.5f, fade),hide()));
				}
				/*if(Save.gd.maki>5){
					Image black=new Image(skin.newDrawable("white", Color.BLACK));
					black.getColor().a=0f;
					black.setSize(stage.getWidth(), stage.getHeight());
					black.setPosition(0, 0);
					cost.addAction(moveTo(stage.getWidth()/2+cost.getWidth()*4-pad,
							-stage.getHeight()/2+cost.getHeight()+4*pad,2,sine));
					stage.addActor(black);
					black.addAction(sequence(
							alpha(1,2),run(new Runnable() {
								@Override
								public void run() {
									model.controller.restart();
								}
							})));
					Save.gd.maki-=5;
					pauseButton.setTouchable(Touchable.enabled);
					model.win=false;
					//gameoverTable.clearActions();
					//gameoverTable.getColor().a = 1;
					//gameoverTable.clear();
					gameoverTable.addAction(sequence(delay(2),fadeOut(1, fade), removeActor()));
					black.addAction(sequence(delay(3), removeActor()));
					pauseButton.setChecked(false);
				}*/
			}
		});
		
		if (Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1) {
			weap1B.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if(Save.gd.soundEnabled)click.play();
					if(weap1B.isChecked()) view.activWeap=weapon1;
				}
			});
			
			weap2B.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if(Save.gd.soundEnabled)click.play();
					if(weap2B.isChecked()) view.activWeap=weapon2;
				}
			});
		}
		if (Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1 && Save.gd.weapon3!=-1) {
			weap1B.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if(Save.gd.soundEnabled)click.play();
					if(weap1B.isChecked()) view.activWeap=weapon1;
				}
			});
			
			weap2B.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if(Save.gd.soundEnabled)click.play();
					if(weap2B.isChecked()) view.activWeap=weapon2;
				}
			});
			weap3B.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if(Save.gd.soundEnabled)click.play();
					if(weap3B.isChecked()) view.activWeap=weapon3;
				}
			});
		}
		
		//shop
		backbt.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				buymenu.addAction(sequence(alpha(0, 0.5f, fade), hide()));
				gameoverTable.addAction(sequence(show(),alpha(1, 0.5f, fade)));
			}
		});
	
		buy1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buyMaki1();
				maki.setText(Integer.toString(Save.gd.maki));
			}
		});
		
		buy2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buyMaki2();
				maki.setText(Integer.toString(Save.gd.maki));
			}
		});
		buy3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)click.play();
				CatGame.shop.buymetal();;
			}
		});
	
	}

	void loadSkin () {
		skin = new Skin();
		if(Gdx.graphics.getHeight()<1000){
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			skin.add("big", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			skin.add("small"  , new BitmapFont(Gdx.files.internal("font/arialverysmall.fnt")));
			uiscale=1;
		}
		else{
			skin.add("default", new BitmapFont(Gdx.files.internal("font/arial.fnt")));
			skin.add("big", new BitmapFont(Gdx.files.internal("font/arialbig.fnt")));
			skin.add("small"  , new BitmapFont(Gdx.files.internal("font/arialsmall.fnt")));
			uiscale=2;
		}

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		ButtonStyle buttonstyle = new ButtonStyle();
		buttonstyle.up   = skin.newDrawable("white", Color.BLACK);
		buttonstyle.down = skin.newDrawable("white", new Color(0x416ba1ff));
		buttonstyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		skin.add("default", buttonstyle);
		
		buttonstyle = new ButtonStyle(buttonstyle);
		buttonstyle.checked = skin.newDrawable("white", new Color(0x5287ccff));
		skin.add("toggle", buttonstyle);
		
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
		labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("small");
		skin.add("small", labelStyle);

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
		score.setText(Integer.toString(model.score));
		prop.setVolume(Math.abs(model.player.velocity.y)/(5*Save.gd.speed)+0.1f);
		
		if(black!=null ){
			float fade=1-black.getColor().a;
			music.setVolume(0.25f*fade);
			if(Save.gd.playerPos==CatGame.ANOMALY && black.getColor().a==1)
				((Game) Gdx.app.getApplicationListener()).setScreen(new Outro());
			else if(Save.gd.playerPos!=CatGame.ANOMALY && black.getColor().a==1)
				((Game) Gdx.app.getApplicationListener()).setScreen(new Starmap());
			view.batch.setColor(fade, fade, fade, 1);
		}
		
		if(metalwoni<metalwon || makiwoni<makiwon){
			rewardTimer++;
			if(rewardTimer%5==0){
				if(makiwoni<makiwon){
					makiwoni++;
					makiwont.setText(" : +"+Integer.toString(makiwoni));
				}
				if(metalwoni<metalwon){
					metalwoni++;
					metalwont.setText(" : +"+Integer.toString(metalwoni));
				}
			}
		}
		//HP bar
		healthBar.setValue(Save.gd.hp - model.player.hp);
		//shield bar
		if(Save.gd.shieldOwn){
			if(model.player.shieldhp>0)
				shieldBar.setValue(Save.gd.shieldhp - (model.player.shieldhp-model.player.shieldRLTimer/(20-Save.gd.shieldreload))-1);
			else
				shieldBar.setValue(Save.gd.shieldhp - Save.gd.shieldhp*(1-model.player.shieldRLTimer/(20-Save.gd.shieldreload)));
		}
		
		//weapBAR 1
		if(weapon1==CatGame.RG) weaponBar1.setValue(model.player.view.rgShots);
		if(weapon1==CatGame.SM) weaponBar1.setValue(model.player.view.smShots);//model.player.shootSMTimer*model.player.SMshootpsec);
		if(weapon1==CatGame.LZ) weaponBar1.setValue(Save.gd.lasermax-model.player.laserpower);
		
		//weapBAR 2
		if(Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1){
			if(weapon2==CatGame.RG) weaponBar2.setValue(model.player.view.rgShots);
			if(weapon2==CatGame.SM) weaponBar2.setValue(model.player.view.smShots);
			if(weapon2==CatGame.LZ) weaponBar2.setValue(Save.gd.lasermax-model.player.laserpower);
		}
		
		//weapBAR 3
		if(Save.gd.weapon3!=-1 && Save.gd.weapon2!=-1 && Save.gd.weapon1!=-1){
			if(weapon3==CatGame.RG) weaponBar3.setValue(model.player.view.rgShots);
			if(weapon3==CatGame.SM) weaponBar3.setValue(model.player.view.smShots);
			if(weapon3==CatGame.LZ) weaponBar3.setValue(Save.gd.lasermax-model.player.laserpower);
		}
		

		/*if (debugButton.isChecked()) {
			shapes.setTransformMatrix(view.batch.getTransformMatrix());
			shapes.setProjectionMatrix(view.batch.getProjectionMatrix());
			shapes.begin(ShapeType.Line);

			shapes.setColor(Color.GREEN);

			FloatArray bullets = model.playerRGbullets;
			for (int i = bullets.size - 5; i >= 0; i -= 5) {
				float x = bullets.get(i + 2);
				float y = bullets.get(i + 3);
				shapes.x(x, y, 0.1f);
			}

			FloatArray hits = view.hits;
			for (int i = hits.size - 4; i >= 0; i -= 4) {
				float x = hits.get(i + 1);
				float y = hits.get(i + 2);
				shapes.x(x, y, 0.1f);
			}

			for (Enemy enemy : model.enemies) {
				if(enemy.type==Type.ultima){
					Rectangle rect = enemy.rect;
					shapes.rect(rect.x, rect.y, rect.width, rect.height);
					Rectangle rect1 = enemy.shield;
					shapes.rect(rect1.x, rect1.y, rect1.width, rect1.height);
					Rectangle rect2 = enemy.prot1;
					shapes.rect(rect2.x, rect2.y, rect2.width, rect2.height);
					Rectangle rect3 = enemy.gen1;
					shapes.rect(rect3.x, rect3.y, rect3.width, rect3.height);
					Rectangle rect4 = enemy.prot2;
					shapes.rect(rect4.x, rect4.y, rect4.width, rect4.height);
					Rectangle rect5 = enemy.gen2;
					shapes.rect(rect5.x, rect5.y, rect5.width, rect5.height);
				}
				else{
					Rectangle rect = enemy.rect;
					shapes.rect(rect.x, rect.y, rect.width, rect.height);
					Rectangle rect1 = enemy.shield;
					shapes.rect(rect1.x, rect1.y, rect1.width, rect1.height);
				}
			}
			Rectangle rect1 = model.player.shield;
			shapes.rect(rect1.x, rect1.y, rect1.width, rect1.height);
			Rectangle rect = model.player.rect;
			shapes.rect(rect.x, rect.y, rect.width, rect.height);
			shapes.end();
		
		}*/
		stage.act();
		stage.getViewport().apply(true);
		stage.draw();
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
		menu.invalidateHierarchy();
	}

	/*void toggleFullscreen () {
		if (Gdx.graphics.isFullscreen())
			Gdx.graphics.setDisplayMode(windowWidth, windowHeight, false);
		else {
			windowWidth = Gdx.graphics.getWidth();
			windowHeight = Gdx.graphics.getHeight();
			DisplayMode desktopDisplayMode = Gdx.graphics.getDesktopDisplayMode();
			Gdx.graphics.setDisplayMode(desktopDisplayMode.width, desktopDisplayMode.height, true);
		}
	}*/

	public void showGO (boolean won) {
		if(won){
			if(Save.gd.playerGo==CatGame.ANOMALY){
				Save.gd.lose=0;
				black=new Image(skin.newDrawable("white", Color.WHITE));
				black.getColor().a=0f;
				black.setSize(stage.getWidth(), stage.getHeight());
				black.setPosition(0, 0);
				black.addAction(alpha(1,4));
				gameoverTable.addActor(black);
			}
			else if(Save.gd.playerGo>=CatGame.EVENTDSHIELD2) {
				Save.gd.lose=0;
				black=new Image(skin.newDrawable("white", Color.BLACK));
				black.getColor().a=0f;
				black.setSize(stage.getWidth(), stage.getHeight());
				black.setPosition(0, 0);
				stage.addActor(black);
				gover.setText(CatGame.myBundle.get("gover2"));
				TextButton okbutton = button("  ok  ",true);
				okbutton.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						black.addAction(alpha(1,2));
					}
				});
				Image probim=  new Image(Assets.manager.get(Assets.probe,Texture.class));
				probim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image metalim=  new Image(Assets.manager.get(Assets.metal,Texture.class));
				metalim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image metalim2=  new Image(Assets.manager.get(Assets.metal,Texture.class));
				metalim2.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image makiim=  new Image(Assets.manager.get(Assets.maki,Texture.class));
				makiim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				gameoverTable.center().add(gover).colspan(4).spaceBottom(30).row();
				gameoverTable.right().add(probim).size(60, 60).right().padTop(10).padBottom(10);
				gameoverTable.left().add("  x "+Integer.toString(model.probeCollect)).left().padTop(10).padBottom(10);
				gameoverTable.right().add(metalim2).size(60, 60).right().padTop(10).padBottom(10);
				gameoverTable.left().add("  x "+Integer.toString(model.metalCollect)).left().padTop(10).padBottom(10).row();
				gameoverTable.right().add(metalim).colspan(2).size(40, 40).right().pad(10);
				gameoverTable.left().add(metalwont).colspan(2).left().pad(10).row();
				gameoverTable.right().add(makiim).colspan(2).size(40, 40).right().pad(10);
				gameoverTable.left().add(makiwont).colspan(2).left().pad(10).row();
				gameoverTable.center().add(okbutton).spaceTop(20).colspan(4);
				gameoverTable.getColor().a=0.7f;
				for(int i=1;i<=model.probeCollect;i++){
					if(!Save.gd.hmakiOwn) makiwon +=MathUtils.random(2, 5);
					else                  makiwon +=MathUtils.random(0, 1);
					metalwon+=MathUtils.random(5, 10);
				}
				metalwon+=(2.5f+Save.gd.norisdefeated)*model.metalCollect;
				Save.gd.maki+=makiwon;
				Save.gd.metal+=metalwon;
				Save.gd.probes+=model.probeCollect;
				Save.gd.score+=model.score;
				if(CatGame.gservices.isSignedIn()){
					CatGame.gservices.incrementscore(model.score);
				}
				else 
					Save.gd.scoregs+=model.score;
				Save.save();
				pauseButton.setTouchable(Touchable.disabled);
			}
			else {
				Save.gd.lose=0;
				black=new Image(skin.newDrawable("white", Color.BLACK));
				black.getColor().a=0f;
				black.setSize(stage.getWidth(), stage.getHeight());
				black.setPosition(0, 0);
				black.setTouchable(Touchable.disabled);
				stage.addActor(black);
				gover.setText(CatGame.myBundle.get("gover2"));
				TextButton okbutton = button("  ok  ",true);
				okbutton.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						black.addAction(alpha(1,2));
					}
				});
				Image probim=  new Image(Assets.manager.get(Assets.probe,Texture.class));
				probim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image metalim=  new Image(Assets.manager.get(Assets.metal,Texture.class));
				metalim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image metalim2=  new Image(Assets.manager.get(Assets.metal,Texture.class));
				metalim2.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				Image makiim=  new Image(Assets.manager.get(Assets.maki,Texture.class));
				makiim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
				gameoverTable.center().add(gover).colspan(4).spaceBottom(30).row();
				gameoverTable.right().add(probim).size(60, 60).right().padTop(10).padBottom(10);
				gameoverTable.left().add("  x "+Integer.toString(model.probeCollect)).left().padTop(10).padBottom(10);
				gameoverTable.right().add(metalim2).size(60, 60).right().padTop(10).padBottom(10);
				gameoverTable.left().add("  x "+Integer.toString(model.metalCollect)).left().padTop(10).padBottom(10).row();
				gameoverTable.right().add(metalim).colspan(2).size(40, 40).right().pad(10);
				gameoverTable.left().add(metalwont).colspan(2).left().pad(10).row();
				gameoverTable.right().add(makiim).colspan(2).size(40, 40).right().pad(10);
				gameoverTable.left().add(makiwont).colspan(2).left().pad(10).row();
				gameoverTable.center().add(okbutton).spaceTop(20).colspan(4);
				gameoverTable.getColor().a=0.7f;
				for(int i=1;i<=model.probeCollect;i++){
					if(!Save.gd.hmakiOwn) makiwon +=MathUtils.random(2, 5);
					else                  makiwon +=MathUtils.random(0, 1);
					metalwon+=MathUtils.random(3, 8);
				}
				metalwon+=2*model.metalCollect;
				Save.gd.maki+=makiwon;
				Save.gd.metal+=metalwon;
				Save.gd.probes+=model.probeCollect;
				Save.gd.score+=model.score;
				if(CatGame.gservices.isSignedIn()){
					CatGame.gservices.incrementscore(model.score);
				}
				else 
					Save.gd.scoregs+=model.score;
				Save.save();
				pauseButton.setTouchable(Touchable.disabled);
			}
		}
		else 
		{
			gover.setText(CatGame.myBundle.get("gover1"));
			gameoverTable.defaults().uniformX().fillX();
			gameoverTable.center().add(gover).colspan(2).row();
			gameoverTable.center().add(razButton).pad(10).row();;
			gameoverTable.center().add(mapButton1).pad(10);
			Image makiim=  new Image(Assets.manager.get(Assets.maki,Texture.class));
			makiim.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
			makiim.setSize(60, 60);
			maki =new Label(Integer.toString(Save.gd.maki),skin);
			maki.setPosition(stage.getWidth()-60-maki.getWidth()-2*pad, 60/2-maki.getHeight()/2+pad);
			makiim.setPosition(stage.getWidth()-pad-60, pad);
			gameoverTable.addActor(makiim);
			gameoverTable.addActor(maki);
			model.lose+=1;
			Save.gd.lose=model.lose;
		}
		stage.addActor(gameoverTable);
		gameoverTable.clearActions();
		gameoverTable.getColor().a = 0;
		gameoverTable.addAction(sequence(fadeIn(0.6f),show()));
	}
	
	public void showQuit () {
		pauseButton.setTouchable(Touchable.disabled);
		pauseButton.setChecked(false);
		model.timeScale=0;
		quit.clearActions();
		quit.addAction(sequence(fadeIn(0),show()));
	}
	
	private void createshop(){
		buymenu = new Group();
		buy1 = button (MapUI.price1, false);
		buy2 = button (MapUI.price2, false);
		buy3 = button (MapUI.price3, false);
		backbt = button (CatGame.myBundle.get("quit"), false);
		Image bgbuy1 = new Image(skin.newDrawable("white", Color.BLACK));
		bgbuy1.setSize(stage.getWidth()/2, stage.getHeight()*3/4);
		bgbuy1.setPosition(stage.getWidth()/4, stage.getHeight()/8);
		bgbuy1.getColor().a=0.9f;
		Image bgbuy2 = new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		bgbuy2.setSize(stage.getWidth()/2-stage.getWidth()/60, stage.getHeight()*3/4-stage.getWidth()/60);
		bgbuy2.setPosition(stage.getWidth()/4+stage.getWidth()/120, stage.getHeight()/8+stage.getWidth()/120);
		bgbuy2.getColor().a=0.2f;
		buymenu.addActor(bgbuy1);
		buymenu.addActor(bgbuy2);
		
		Label buytitle=new Label(CatGame.myBundle.get("sushishop"), skin);
		buytitle.setPosition(stage.getWidth()/2-buytitle.getWidth()/2, stage.getHeight()*3/4-pad/2);
		buymenu.addActor(buytitle);
		
		Image makiimb1=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiimb1.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiimb1.setSize(60, 60);
		makiimb1.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*3/5);
		buymenu.addActor(makiimb1);
		Label buynbr1 = new Label("x15", skin);
		buynbr1.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), makiimb1.getY()+makiimb1.getHeight()/2-buynbr1.getHeight()/2);
		buymenu.addActor(buynbr1);
		buy1.setSize(100*uiscale, 40*uiscale);
		buy1.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy1.getWidth(), makiimb1.getY()+makiimb1.getHeight()/2-buy1.getHeight()/2);
		buymenu.addActor(buy1);
		Label buytxt1 = new Label(CatGame.myBundle.get("maki1"), skin, "small");
		buytxt1.setPosition((buy1.getX()+(buynbr1.getX()+buynbr1.getWidth()))/2-buytxt1.getWidth()/2, makiimb1.getY()+makiimb1.getHeight()/2-buytxt1.getHeight()/2);
		buymenu.addActor(buytxt1);
		
		Image makiimb2=  new Image(Assets.manager.get(Assets.maki,Texture.class));
		makiimb2.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		makiimb2.setSize(60, 60);
		makiimb2.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*2/5);
		buymenu.addActor(makiimb2);
		Label buynbr2 = new Label("x40", skin);
		buynbr2.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), makiimb2.getY()+makiimb2.getHeight()/2-buynbr2.getHeight()/2);
		buymenu.addActor(buynbr2);
		buy2.setSize(100*uiscale, 40*uiscale);
		buy2.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy1.getWidth(), makiimb2.getY()+makiimb2.getHeight()/2-buy2.getHeight()/2);
		buymenu.addActor(buy2);
		Label buytxt2 = new Label(CatGame.myBundle.get("maki2"), skin, "small");
		buytxt2.setPosition((buy2.getX()+(buynbr2.getX()+buynbr2.getWidth()))/2-buytxt2.getWidth()/2, makiimb2.getY()+makiimb2.getHeight()/2-buytxt2.getHeight()/2);
		buymenu.addActor(buytxt2);
		
		Image metalimb=  new Image(Assets.manager.get(Assets.metal,Texture.class));
		metalimb.addAction(forever(sequence(rotateTo(4,1.5f,sine),rotateTo(-4,1.5f,sine))));
		metalimb.setSize(60, 60);
		metalimb.setPosition(bgbuy2.getX()+pad, bgbuy2.getY()+bgbuy2.getHeight()*1/5);
		buymenu.addActor(metalimb);
		Label buynbr3 = new Label("x20", skin);
		buynbr3.setPosition(bgbuy2.getX()+2*pad+makiimb1.getWidth(), metalimb.getY()+metalimb.getHeight()/2-buynbr3.getHeight()/2);
		buymenu.addActor(buynbr3);
		buy3.setSize(100*uiscale, 40*uiscale);
		buy3.setPosition(bgbuy2.getX()+bgbuy2.getWidth()-pad-buy3.getWidth(), metalimb.getY()+metalimb.getHeight()/2-buy3.getHeight()/2);
		buymenu.addActor(buy3);
		Label buytxt3 = new Label(CatGame.myBundle.get("metal"), skin, "small");
		buytxt3.setPosition((buy3.getX()+(buynbr3.getX()+buynbr3.getWidth()))/2-buytxt3.getWidth()/2, metalimb.getY()+metalimb.getHeight()/2-buytxt3.getHeight()/2);
		buymenu.addActor(buytxt3);

		backbt.setSize(100*uiscale, 40*uiscale);
		backbt.setPosition(stage.getWidth()/2-backbt.getWidth()/2, bgbuy2.getY()+pad);
		buymenu.addActor(backbt);
		
		buymenu.getColor().a=0;
		buymenu.setVisible(false);
		
	}
	
	public void dispose(){
		//Save.save();
		//shapes.dispose();
		stage.dispose();
		no.dispose();
		click.dispose();
		prop.dispose();
		music.dispose();
		skin.dispose();
	}
}
