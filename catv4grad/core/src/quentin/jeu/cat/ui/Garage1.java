package quentin.jeu.cat.ui;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Assets;
import quentin.jeu.cat.utils.Save;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class Garage1 {
	
	public Group lab;
	public boolean upgraded;
	private DragAndDrop dragAndDrop = new DragAndDrop();
	private Skin skin;
	private float screenW, screenH, scale;
	private Button technob, shipb, rgb, smb, shieldb, lzb;
	private Image techno, ship, rg, sm, shield, lz;
	//techno
	private Group descriptionG, equipG;
	private Label descriptiontxt, costTec;
	private TextButton equipButton;
	private Button weap1B, weap2B, weap3B;
	private boolean drillcheck, hmakicheck, sMcheck, rGcheck, lzcheck, shieldcheck;
	static int drillcost=50, hmakicost=50,  sMcost=50, lzcost=50,shieldcost =50;
	private TextButton canceltec;
	
	//ship
	private ProgressBar hpBar, speedBar,accBar;
	private static float maxhp=CatGame.maxhp, maxSpeed=CatGame.maxSpeed, maxAcc=CatGame.maxAcc;
	private float hp, speed, acc, hpmetal, speedmetal ,accmetal;
	private Label costShip;
	private TextButton cancelship;
	int metalship;
	
	//rice gun
	private ProgressBar rgprecBar, rgFRBar,rgpowerBar;
	private static float maxRGprec=CatGame.maxRGprec, maxRGFR=CatGame.maxRGFR, maxRGpower=CatGame.maxRGpower;
	private float rPrec, rFR, rPower, rpowmetal, rsmetal ,rprecmetal;
	private Label costRG;
	int metalRG;
	private TextButton cancelRG;
	
	//Sushi Missile
	private ProgressBar smNbrBar, smFRBar, smspeedBar;
	private static float maxSMnbr=CatGame.maxSMnbr, maxSMFR=CatGame.maxSMFR, maxSMspeed=CatGame.maxSMspeed;
	private float snbr, sFR, sSpeed, snbrMetal, sRrMetal ,sSpeedMetal;
	private Label costSM;
	private TextButton cancelSM;
	int metalSM;
	
	//laser gun
	private ProgressBar lzmaxBar, lzminBar, lzrlBar;
	private static float maxLZpower=CatGame.maxLZpower, minLZpower=CatGame.minLZpower, reloadLZ=CatGame.reloadLZ;
	private float lzmax, lzmin, lreload, lzmaxMetal, lzminMetal ,lzrlMetal;
	private Label costLZ;
	int metalLZ;
	private TextButton cancelLZ;
	
	//Shield
	private ProgressBar shieldhpBar, shieldRLBar;//, zzlzrlBar;
	private static float maxshieldhp=CatGame.maxshieldhp, maxshieldRL=CatGame.maxshieldRL;//, zzreloadLZ=3;
	private float shieldhp, shieldrl, zzlreload, shieldhpMetal, shieldrlMetal;// ,zzlzrlMetal;
	private Label costshield;
	int metalshield;
	private TextButton cancelshield;
	
	
	
	public Garage1(final Skin skin, float screenW, float screenH, final float scale){
		this.skin=skin;
		this.screenH=screenH;
		this.screenW=screenW;
		this.scale=scale;
		
		
		/////////TOTAL GROUP
		lab=new Group();
		lab.setSize(screenW, screenH);
		
		Image bg1image=new Image(skin.newDrawable("white", Color.BLACK));
		bg1image.getColor().a=0.9f;
		bg1image.setSize(screenW/8, screenH*3/4);
		bg1image.setPosition(screenW/16, screenH/8);
		float pad1=screenW/60;
		
		Image bg1image1=new Image(skin.newDrawable("white", Color.BLACK));
		bg1image1.getColor().a=0.9f;
		bg1image1.setSize(screenW*3/4, screenH*3/4);
		bg1image1.setPosition(screenW/8+screenW/16, screenH/8);
		
		Image bg2image1=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		bg2image1.getColor().a=0.2f;
		float width1 = screenW*3/4-screenW/30;
		float height1= screenH*3/4-screenW/30;
		bg2image1.setSize(width1, height1);
		float posx1 = screenW/8+screenW/60+screenW/16, posy1 = screenH/8+screenW/60;
		bg2image1.setPosition(posx1, posy1);
		
		technob = new Button(skin, "toggle");
		techno = new Image(Assets.manager.get(Assets.wrench,Texture.class));
		technob.setSize(screenW/8, 50 * scale);
		technob.getColor().a=0.2f;
		technob.setPosition(bg1image.getX()+bg1image.getWidth()-technob.getWidth()+pad1, bg1image.getY()+bg1image.getHeight()-technob.getHeight()-pad1);
		techno.setSize(70 * scale, 30* scale);
		techno.setPosition(technob.getX()+technob.getWidth()/2-techno.getWidth()/2, technob.getY()+technob.getHeight()/2-techno.getHeight()/2);
		techno.setTouchable(Touchable.disabled);
		techno.setOrigin(Align.center);
		techno.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
		
		shipb = new Button(skin, "toggle");
		if(Save.gd.hp+Save.gd.speed>=(CatGame.maxhp+CatGame.maxSpeed)*6/7){
			ship = new Image(Assets.manager.get(Assets.spaceship3,Texture.class));
		}
		else if(Save.gd.hp+Save.gd.speed>=(CatGame.maxhp+CatGame.maxSpeed)*4/7){
			ship = new Image(Assets.manager.get(Assets.spaceship2,Texture.class));
		}
		else {
			ship = new Image(Assets.manager.get(Assets.spaceship1,Texture.class));
		}
		
		shipb.setSize(screenW/8, 50 * scale);
		shipb.getColor().a=0.2f;
		shipb.setPosition(bg1image.getX()+bg1image.getWidth()-shipb.getWidth()+pad1, technob.getY()-shipb.getHeight()-pad1/2);
		ship.setSize(84 * scale, 36* scale);
		ship.setPosition(shipb.getX()+shipb.getWidth()/2-ship.getWidth()/2, shipb.getY()+shipb.getHeight()/2-ship.getHeight()/2);
		ship.setTouchable(Touchable.disabled);
		ship.setOrigin(Align.center);
		
		rgb = new Button(skin, "toggle");
		rg = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
		rgb.setSize(screenW/8, 50 * scale);
		rgb.getColor().a=0.2f;
		rgb.setPosition(bg1image.getX()+bg1image.getWidth()-rgb.getWidth()+pad1, shipb.getY()-rgb.getHeight()-pad1/2);
		rg.setSize(70 * scale, 30* scale);
		rg.setPosition(rgb.getX()+rgb.getWidth()/2-rg.getWidth()/2, rgb.getY()+rgb.getHeight()/2-rg.getHeight()/2);
		rg.setTouchable(Touchable.disabled);
		rg.setOrigin(Align.center);
		
		smb = new Button(skin, "toggle");
		sm = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
		smb.setSize(screenW/8, 50 * scale);
		smb.getColor().a=0.2f;
		smb.setPosition(bg1image.getX()+bg1image.getWidth()-smb.getWidth()+pad1, rgb.getY()-smb.getHeight()-pad1/2);
		sm.setSize(70 * scale, 30* scale);
		sm.setPosition(smb.getX()+smb.getWidth()/2-sm.getWidth()/2, smb.getY()+smb.getHeight()/2-sm.getHeight()/2);
		sm.setTouchable(Touchable.disabled);
		sm.setOrigin(Align.center);
		
		shieldb = new Button(skin, "toggle");
		shield = new Image(Assets.manager.get(Assets.shield,Texture.class));
		shieldb.setSize(screenW/8, 50 * scale);
		shieldb.getColor().a=0.2f;
		shieldb.setPosition(bg1image.getX()+bg1image.getWidth()-shieldb.getWidth()+pad1, smb.getY()-shieldb.getHeight()-pad1/2);
		shield.setSize(84 * scale, 36* scale);
		shield.setPosition(shieldb.getX()+shieldb.getWidth()/2-shield.getWidth()/2, shieldb.getY()+shieldb.getHeight()/2-shield.getHeight()/2);
		shield.setTouchable(Touchable.disabled);
		shield.setColor(0, 0, 0.7f, 1f);
		shield.setOrigin(Align.center);
		
		lzb = new Button(skin, "toggle");
		lz = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
		lzb.setSize(screenW/8, 50 * scale);
		lzb.getColor().a=0.2f;
		lzb.setPosition(bg1image.getX()+bg1image.getWidth()-lzb.getWidth()+pad1, shieldb.getY()-lzb.getHeight()-pad1/2);
		lz.setSize(70 * scale, 30* scale);
		lz.setPosition(lzb.getX()+lzb.getWidth()/2-lz.getWidth()/2, lzb.getY()+lzb.getHeight()/2-lz.getHeight()/2);
		lz.setTouchable(Touchable.disabled);
		lz.setOrigin(Align.center);
		
		new ButtonGroup<Button>(technob, shipb, rgb, smb, shieldb, lzb);
		technob.setChecked(true);
		if(!Save.gd.smOwn){
			smb.setVisible(false);
			sm.setVisible(false);
		}
		if(!Save.gd.lzOwn){
			lzb.setVisible(false);
			lz.setVisible(false);
		}
		if(!Save.gd.shieldOwn){
			shieldb.setVisible(false);
			shield.setVisible(false);
		}
		
		final Group technopage=createTechnoPage();
		technopage.setVisible(true);
		final Group shippage  =createShipPage();
		shippage.setVisible(false);
		final Group rgpage    =createRGPage();
		rgpage.setVisible(false);
		final Group smpage    =createSMPage();
		smpage.setVisible(false);
		final Group lzpage    =createLZPage();
		lzpage.setVisible(false);
		final Group shieldpage=createShieldPage();
		shieldpage.setVisible(false);
		
		technob.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (technopage.isVisible()){
					technopage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					techno.clearActions();
				}
				else{
					technopage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					techno.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
				}
			}
		});
		shipb.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (shippage.isVisible()){
					shippage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					ship.clearActions();
				}
				else{
					shippage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					ship.addAction(forever(sequence(sizeTo(70*scale,30*scale,0.5f,sine),sizeTo(80*scale,36*scale,0.5f,sine))));
				}
			}
		});
		rgb.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (rgpage.isVisible()){
					rgpage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					rg.clearActions();
				}
				else{
					rgpage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					rg.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
				}
			}
		});
		smb.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (smpage.isVisible()){
					smpage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					sm.clearActions();
				}
				else{
					smpage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					sm.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
				}
			}
		});
		lzb.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (lzpage.isVisible()){
					lzpage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					lz.clearActions();
				}
				else{
					lzpage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					lz.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
				}
			}
		});
		shieldb.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if (shieldpage.isVisible()){
					shieldpage.addAction(sequence(alpha(0, 0.1f, fade), hide()));
					shield.addAction(forever(sequence(sizeTo(70*scale,30*scale,0.5f,sine),sizeTo(80*scale,36*scale,0.5f,sine))));
					shield.clearActions();
				}
				else{
					shieldpage.addAction(sequence(show(), alpha(1, 0.1f, fade)));
					shield.addAction(forever(sequence(sizeTo(60*scale,26*scale,0.5f,sine),sizeTo(70*scale,30*scale,0.5f,sine))));
				}
			}
		});
		
		lab.addActor(bg1image);
		lab.addActor(bg1image1);
		lab.addActor(bg2image1);
		lab.addActor(shippage);
		lab.addActor(technopage);
		lab.addActor(rgpage);
		lab.addActor(smpage);
		lab.addActor(lzpage);
		lab.addActor(shieldpage);
		lab.addActor(shieldb);
		lab.addActor(shield);
		lab.addActor(lzb);
		lab.addActor(lz);
		lab.addActor(smb);
		lab.addActor(sm);
		lab.addActor(rgb);
		lab.addActor(rg);
		lab.addActor(technob);
		lab.addActor(techno);
		lab.addActor(shipb);
		lab.addActor(ship);
		
		
		lab.setPosition(0, 0);
		lab.setVisible(false);
		
		
		
	}
	
	
	
	private Group createTechnoPage(){
		
		final Group technoPage =new Group();
		
		
		//background
		float width = screenW*3/4-screenW/30;
		float height= screenH*3/4-screenW/30;
		float pad=screenW/60;
		float posx = screenW/8+screenW/60+screenW/16;
		
		
		//heading and bottom
		Label title = new Label(CatGame.myBundle.get("technologies"), skin);
		Image titlebg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image titleImbg=new Image(skin.newDrawable("white", Color.BLACK));
		Image titleIm =new Image(Assets.manager.get(Assets.wrench,Texture.class));
	
		
		final Image drillIm  =new Image(Assets.manager.get(Assets.drill,Texture.class));
		final Image hmakiIm  =new Image(Assets.manager.get(Assets.hmaki,Texture.class));
		final Image sushiMIm =new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
		final Image laserIm =new Image(Assets.manager.get(Assets.laserGun,Texture.class));
		final Image shieldIm =new Image(Assets.manager.get(Assets.shield,Texture.class));
		
		final TextButton upbutton     = new TextButton(CatGame.myBundle.get("buy") ,skin);
		upbutton.pad(2, 12, 2, 12);
		upbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(hmakicheck && Save.gd.metal>hmakicost && !Save.gd.hmakiOwn){
					hmakicheck=false;
					Save.gd.metal-=hmakicost;
					Save.gd.metalspent+=hmakicost;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(hmakicost);
					}
					else Save.gd.metalspentgs+=hmakicost;
					Save.gd.hmakiOwn=true;
					Save.save();
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					upbutton.setText("");
					hmakiIm.addAction(sequence(delay(1), color(Color.WHITE,1)));
					
				}
				if(drillcheck && Save.gd.metal>drillcost && !Save.gd.drillOwn){
					drillcheck=false;
					Save.gd.metal-=drillcost;
					Save.gd.metalspent+=drillcost;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(drillcost);
					}
					else Save.gd.metalspentgs+=drillcost;
					Save.gd.drillOwn=true;
					Save.save();
					descriptionG.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					upbutton.setText("");
					drillIm.addAction(sequence(delay(1), color(Color.WHITE,1)));
					
				}
				if(sMcheck && Save.gd.metal>sMcost && !Save.gd.smOwn){
					Save.gd.metal-=sMcost;
					Save.gd.metalspent+=sMcost;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(sMcost);
					}
					else Save.gd.metalspentgs+=sMcost;
					Save.gd.smOwn=true;
					Save.save();
					smb.addAction(sequence(alpha(0), show(), alpha(0.2f,0.4f)));
					sm.addAction(sequence(alpha(0), show(), alpha(1,0.4f)));
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					upbutton.setText("");
					sushiMIm.addAction(sequence(delay(1), color(Color.WHITE,1)));
					equipButton.addAction(sequence(alpha(0), show(), alpha(1,0.4f)));
					
				}
				if(lzcheck && Save.gd.metal>lzcost && !Save.gd.lzOwn){
					Save.gd.metal-=lzcost;
					Save.gd.metalspent+=lzcost;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(lzcost);
					}
					else Save.gd.metalspentgs+=lzcost;
					Save.gd.lzOwn=true;
					Save.save();
					lzb.addAction(sequence(alpha(0), show(), alpha(0.2f,0.4f)));
					lz.addAction(sequence(alpha(0), show(), alpha(1,0.4f)));
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					upbutton.setText("");
					laserIm.addAction(sequence(delay(1), color(Color.WHITE,1)));
					equipButton.addAction(sequence(alpha(0), show(), alpha(1,0.4f)));
				}
				if(shieldcheck && Save.gd.metal>shieldcost && !Save.gd.shieldOwn){
					shieldcheck=false;
					Save.gd.metalspent+=shieldcost;
					Save.gd.metal-=shieldcost;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(shieldcost);
						Save.gd.metalspentgs=0;
					}
					else Save.gd.metalspentgs+=shieldcost;
					Save.gd.shieldOwn=true;
					Save.save();
					shieldb.addAction(sequence(alpha(0), show(), alpha(0.2f,0.4f)));
					shield.addAction(sequence(alpha(0), show(), alpha(1,0.4f)));
					descriptionG.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					upbutton.setText("");
					shieldIm.addAction(sequence(delay(1), alpha(1,1)));
					
				}
				upgraded=true;
				
			}
		});
		canceltec = new TextButton(CatGame.myBundle.get("quit"), skin,  "toggle" );
		canceltec.pad(2, 12, 2, 12);
		canceltec.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(!descriptionG.isVisible()){
					lab.clearActions();
					//lab.getColor().a = technoPage.isVisible() ? 1 : 0;
					lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					canceltec.setChecked(false);
				}
				else{
					descriptionG.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					equipButton.addAction(sequence(alpha(0,0.2f),hide()));
					costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
					costTec.setColor(1, 1, 1, 1);
					hmakicheck=false;
					drillcheck=false;
				}
				canceltec.setChecked(false);
			}
		});
		
		Image costbg   =new Image(skin.newDrawable("white", Color.BLACK));
		costTec =new Label("100/200", skin);
		Image metalIm =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		//Create
		
		Image case1 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case2 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case3 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case4 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case5 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case6 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case7 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case8 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case9 =new Image(skin.newDrawable("white", Color.BLACK));
		Image case10=new Image(skin.newDrawable("white", Color.BLACK));
		Image case11=new Image(skin.newDrawable("white", Color.BLACK));
		Image case12=new Image(skin.newDrawable("white", Color.BLACK));
		
		Image shipIm   =new Image(Assets.manager.get(Assets.shipb,Texture.class));
		Image propIm   =new Image(Assets.manager.get(Assets.prop1,Texture.class));
		Image rizgrowIm=new Image(Assets.manager.get(Assets.makikit,Texture.class));
		Image rgIm     =new Image(Assets.manager.get(Assets.riceGun,Texture.class));
		
		if     (Save.gd.smOwn) sushiMIm.setColor(1f, 1f, 1f, 1);
		else if(Save.gd.smDisp)sushiMIm.setColor(0.25f, 0.25f, 0.25f, 1);
		else                   sushiMIm.setColor(0.25f, 0.25f, 0.25f, 0);
		
		if     (Save.gd.lzOwn) laserIm.setColor(1f, 1f, 1f, 1);
		else if(Save.gd.lzDisp)laserIm.setColor(0.25f, 0.25f, 0.25f, 1);
		else                   laserIm.setColor(0.25f, 0.25f, 0.25f, 0);
		
		if     (Save.gd.drillOwn) drillIm.setColor(1f, 1f, 1f, 1);
		else if(Save.gd.drillDisp)drillIm.setColor(0.25f, 0.25f, 0.25f, 1);
		else                      drillIm.setColor(0.25f, 0.25f, 0.25f, 0);
		
		if     (Save.gd.hmakiOwn) hmakiIm.setColor(1f, 1f, 1f, 1);
		else if(Save.gd.hmakiDisp)hmakiIm.setColor(0.25f, 0.25f, 0.25f, 1);
		else                      hmakiIm.setColor(0.25f, 0.25f, 0.25f, 0);
		
		if     (Save.gd.shieldOwn) shieldIm.setColor(0f, 0f, 0.7f, 1f);
		else if(Save.gd.shieldDisp)shieldIm.setColor(0, 0, 0.7f, 0.5f);
		else                       shieldIm.setColor(0, 0, 0.4f, 0);
		
		//layout
		
		titleImbg.setPosition(posx+4*pad+width/12, screenH*6/8-width/24);
		titleImbg.setSize(width/5, height-(screenH*5/8-width/12)-2*pad);
		titleIm.setSize(width/6, height/8);
		titleIm.setPosition(titleImbg.getX()+titleImbg.getWidth()/2-titleIm.getWidth()/2, titleImbg.getY()+titleImbg.getHeight()/2-titleIm.getHeight()/2);
		titleIm.addAction(forever(sequence(sizeTo(width/6*0.85f,height/8*0.85f,0.5f,sine),sizeTo(width/6*1.07f, height/8*1.07f,0.5f,sine))));
		
		titlebg.setPosition(posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*6/8-width/24);
		titlebg.setSize(title.getWidth()+2*pad, height-(screenH*5/8-width/12)-2*pad);
		title.setPosition(titlebg.getX()+titlebg.getWidth()/2-title.getWidth()/2, titlebg.getY()+titlebg.getHeight()/2-title.getHeight()/2);
		
		case1   .setSize(width/9, width/9);
		case1   .setPosition(posx+0*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		case2   .setSize(width/9, width/9);
		case2   .setPosition(posx+1*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		case3.setSize(width/9, width/9);
		case3.setPosition(posx+2*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		case4 .setSize(width/9, width/9);
		case4 .setPosition(posx+3*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		case5  .setSize(width/9, width/9);
		case5  .setPosition(posx+4*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		case6  .setSize(width/9, width/9);
		case6  .setPosition(posx+5*(width/9+pad)+4.5f*pad, titlebg.getY()-width/9-1.3f*pad);
		
		case7 .setSize(width/9, width/9);
		case7 .setPosition(posx+0*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		case8 .setSize(width/9, width/9);
		case8 .setPosition(posx+1*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		case9 .setSize(width/9, width/9);
		case9 .setPosition(posx+2*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		case10.setSize(width/9, width/9);
		case10.setPosition(posx+3*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		case11.setSize(width/9, width/9);
		case11.setPosition(posx+4*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		case12.setSize(width/9, width/9);
		case12.setPosition(posx+5*(width/9+pad)+4.5f*pad, case1.getY()-width/9-1.3f*pad);
		
		//description
		Image descbg=new Image(skin.newDrawable("white", Color.BLACK));
		descbg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				descriptionG.addAction(sequence(alpha(0,0.2f),hide()));
				equipButton.addAction(sequence(alpha(0,0.2f),hide()));
				hmakicheck =false;
				drillcheck=false;
				sMcheck=false;
				rGcheck=false;
				lzcheck=false;
				shieldcheck=false;
				costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
				costTec.setColor(1, 1, 1, 1);
				upbutton.setText("");
				
			}
		});
		descbg.setSize(screenW*3/4, screenH-(screenH/8+case7.getY()));
		descbg.setPosition(screenW/8+screenW/16,  case7.getY());
		
		Image descbg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		descbg2.getColor().a=0.2f;
		descbg2.setSize(screenW*3/4-screenW/30, screenH-(screenH/8+case7.getY())-screenW/30);
		descbg2.setPosition(screenW/8+screenW/60+screenW/16, case7.getY()+screenW/60);
		descbg2.setTouchable(Touchable.disabled);
		
		descriptiontxt= new Label(CatGame.myBundle.get("catfighter"), skin);
		descriptiontxt.setAlignment(Align.center);
		descriptiontxt.setTouchable(Touchable.disabled);
		descriptiontxt.setPosition(descbg.getX()+descbg.getWidth()/2-descriptiontxt.getWidth()/2, screenH/2);
		
		//equip weapon
		
		Image equipbg=new Image(skin.newDrawable("white", Color.BLACK));
		equipbg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				equipG.addAction(sequence(delay(0.1f),alpha(0,0.1f),hide()));
				equipButton.addAction(sequence(delay(0.1f),alpha(0,0.1f),hide()));
				hmakicheck =false;
				drillcheck=false;
				rGcheck=false;
				sMcheck=false;
				lzcheck=false;
				costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
				upbutton.setText("");
			}
		});
		equipbg.setSize(screenW*3/4, screenH-(screenH/8+case7.getY()));
		equipbg.setPosition(screenW/8+screenW/16,  case7.getY());
		
		Image equipbg2=new Image(skin.newDrawable("white", new Color(0x5287ccff)));
		equipbg2.getColor().a=0.2f;
		equipbg2.setSize(screenW*3/4-screenW/30, screenH-(screenH/8+case7.getY())-screenW/30);
		equipbg2.setPosition(screenW/8+screenW/60+screenW/16, case7.getY()+screenW/60);
		equipbg2.setTouchable(Touchable.disabled);
		
		Label equiplabel = new Label(CatGame.myBundle.get("slot"), skin);
		equiplabel.setPosition(equipbg.getX()+equipbg.getWidth()/2 -equiplabel.getWidth()/2, screenH/2 +3*pad+equiplabel.getHeight());
		final Image RG = new Image(Assets.manager.get(Assets.riceGun,Texture.class));
		RG.setSize(70 * scale, 30 * scale);
		RG.setTouchable(Touchable.disabled);
		RG.getColor().a=0;
		final Image SM = new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
		SM.setSize(70 * scale, 30 * scale);
		SM.setTouchable(Touchable.disabled);
		SM.getColor().a=0;
		final Image LZ = new Image(Assets.manager.get(Assets.laserGun,Texture.class));
		LZ.setSize(70 * scale, 30 * scale);
		LZ.setTouchable(Touchable.disabled);
		LZ.getColor().a=0;
		//weapon button 1
		weap1B = new Button(skin);
		weap1B.setSize(scale*140,width/12);
		weap1B.setPosition(equipbg.getX()+equipbg.getWidth()*1/4 -weap1B.getWidth()/2, screenH/2 -weap1B.getHeight()/2);
		weap1B.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.weapon1==CatGame.RG) RG.addAction(alpha(0,0.1f));
				if(Save.gd.weapon1==CatGame.SM) SM.addAction(alpha(0,0.1f));
				if(Save.gd.weapon1==CatGame.LZ) SM.addAction(alpha(0,0.1f));
				if(sMcheck){
					if(Save.gd.weapon2==CatGame.SM) Save.gd.weapon2=-1;
					if(Save.gd.weapon3==CatGame.SM) Save.gd.weapon3=-1;
					SM.addAction(sequence(alpha(0),
							moveTo(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-SM.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon1=CatGame.SM;
				}
				if(rGcheck){
					if(Save.gd.weapon2==CatGame.RG) Save.gd.weapon2=-1;
					if(Save.gd.weapon3==CatGame.RG) Save.gd.weapon3=-1;
					RG.addAction(sequence(alpha(0),
							moveTo(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-RG.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon1=CatGame.RG;
				}
				if(lzcheck){
					if(Save.gd.weapon2==CatGame.LZ) Save.gd.weapon2=-1;
					if(Save.gd.weapon3==CatGame.LZ) Save.gd.weapon3=-1;
					LZ.addAction(sequence(alpha(0),
							moveTo(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-SM.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon1=CatGame.LZ;
				}
				Save.save();
			}
		});
		if (Save.gd.weapon1 == CatGame.RG) {
			RG.setPosition(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-RG.getHeight()/2);
			RG.getColor().a=1;
		} 
		else if (Save.gd.weapon1 == CatGame.SM) {
			SM.setPosition(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-SM.getHeight()/2);
			SM.getColor().a=1;
		}
		else if (Save.gd.weapon1 == CatGame.LZ) {
			LZ.setPosition(weap1B.getX()+weap1B.getWidth()/2-RG.getWidth()/2, weap1B.getY()+weap1B.getHeight()/2-SM.getHeight()/2);
			LZ.getColor().a=1;
		}
		
		//weapon button 2
		weap2B = new Button(skin);
		weap2B.setSize(scale*140,width/12);
		weap2B.setPosition(equipbg.getX()+equipbg.getWidth()*2/4- weap2B.getWidth()/2 , screenH/2 -weap2B.getHeight()/2);
		weap2B.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.weapon2==CatGame.RG) RG.addAction(alpha(0,0.1f));
				if(Save.gd.weapon2==CatGame.SM) SM.addAction(alpha(0,0.1f));
				if(Save.gd.weapon2==CatGame.LZ) LZ.addAction(alpha(0,0.1f));
				if(sMcheck){
					if(Save.gd.weapon1==CatGame.SM) Save.gd.weapon1=-1;
					if(Save.gd.weapon3==CatGame.SM) Save.gd.weapon3=-1;
					SM.addAction(sequence(alpha(0),
							moveTo(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-SM.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon2=CatGame.SM;
				}
				if(rGcheck){
					if(Save.gd.weapon1==CatGame.RG) Save.gd.weapon1=-1;
					if(Save.gd.weapon3==CatGame.RG) Save.gd.weapon3=-1;
					RG.addAction(sequence(alpha(0),
							moveTo(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-RG.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon2=CatGame.RG;
				}
				if(lzcheck){
					if(Save.gd.weapon1==CatGame.LZ) Save.gd.weapon1=-1;
					if(Save.gd.weapon3==CatGame.LZ) Save.gd.weapon3=-1;
					LZ.addAction(sequence(alpha(0),
							moveTo(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-RG.getHeight()/2),
							alpha(1,0.1f)));
					Save.gd.weapon2=CatGame.LZ;
				}
				Save.save();
			}
		});
		if (Save.gd.weapon2 == CatGame.RG) {
			RG.setPosition(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-RG.getHeight()/2);
			RG.getColor().a=1;
		} 
		else if (Save.gd.weapon2 == CatGame.SM) {
			SM.setPosition(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-SM.getHeight()/2);
			SM.getColor().a=1;
		}
		else if (Save.gd.weapon2 == CatGame.LZ) {
			LZ.setPosition(weap2B.getX()+weap2B.getWidth()/2-RG.getWidth()/2, weap2B.getY()+weap2B.getHeight()/2-SM.getHeight()/2);
			LZ.getColor().a=1;
		}
		
		//weapon button 3
			weap3B = new Button(skin);
			weap3B.setSize(scale*140,width/12);
			weap3B.setPosition(equipbg.getX()+equipbg.getWidth()*3/4 -weap3B.getWidth()/2, screenH/2 -weap2B.getHeight()/2);
			weap3B.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(Save.gd.soundEnabled)MapUI.click.play();
					if(Save.gd.weapon3==CatGame.RG) RG.addAction(alpha(0,0.1f));
					if(Save.gd.weapon3==CatGame.SM) SM.addAction(alpha(0,0.1f));
					if(Save.gd.weapon3==CatGame.LZ) LZ.addAction(alpha(0,0.1f));
					if(sMcheck){
						if(Save.gd.weapon1==CatGame.SM) Save.gd.weapon1=-1;
						if(Save.gd.weapon2==CatGame.SM) Save.gd.weapon2=-1;
						
						SM.addAction(sequence(alpha(0),
								moveTo(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-SM.getHeight()/2),
								alpha(1,0.1f)));
						Save.gd.weapon3=CatGame.SM;
					}
					if(rGcheck){
						if(Save.gd.weapon1==CatGame.RG) Save.gd.weapon1=-1;
						if(Save.gd.weapon2==CatGame.RG) Save.gd.weapon2=-1;
						RG.addAction(sequence(alpha(0),
								moveTo(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-RG.getHeight()/2),
								alpha(1,0.1f)));
						Save.gd.weapon3=CatGame.RG;
					}
					if(lzcheck){
						if(Save.gd.weapon1==CatGame.LZ) Save.gd.weapon1=-1;
						if(Save.gd.weapon2==CatGame.LZ) Save.gd.weapon2=-1;
						LZ.addAction(sequence(alpha(0),
								moveTo(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-RG.getHeight()/2),
								alpha(1,0.1f)));
						Save.gd.weapon3=CatGame.LZ;
					}
					Save.save();
				}
			});
			if (Save.gd.weapon3 == CatGame.RG) {
				RG.setPosition(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-RG.getHeight()/2);
				RG.getColor().a=1;
			} 
			else if (Save.gd.weapon3 == CatGame.SM) {
				SM.setPosition(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-SM.getHeight()/2);
				SM.getColor().a=1;
			}
			else if (Save.gd.weapon2 == CatGame.LZ) {
				LZ.setPosition(weap3B.getX()+weap3B.getWidth()/2-RG.getWidth()/2, weap3B.getY()+weap3B.getHeight()/2-SM.getHeight()/2);
				LZ.getColor().a=1;
			}
		
		equipG=new Group();
		equipG.addActor(equipbg);
		equipG.addActor(equipbg2);
		equipG.addActor(equiplabel);
		equipG.addActor(weap1B);
		equipG.addActor(weap2B);
		equipG.addActor(weap3B);
		equipG.addActor(RG);
		equipG.addActor(SM);
		equipG.addActor(LZ);
		
		equipG.setVisible(false);
		equipG.getColor().a=0;
		
		equipButton = new TextButton("Equip", skin);
		equipButton.setSize(scale*140,width/18);
		equipButton.setPosition(equipbg.getX()+equipbg.getWidth()/2-equipButton.getWidth()/2, descbg2.getY()+pad);
		equipButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				descriptionG.addAction(sequence(delay(0.1f),alpha(0,0.1f), hide()));
				equipG.addAction(sequence(show(), alpha(1,0.1f)));
			}
		});
		equipButton.setVisible(false);
		
		descriptionG=new Group();
		descriptionG.addActor(descbg);
		descriptionG.addActor(descbg2);
		descriptionG.addActor(descriptiontxt);
		descriptionG.addActor(equipButton);
		
		descriptionG.setVisible(false);
		descriptionG.getColor().a=0;
		
		//technologies
		shipIm .setSize(width/10, width/20);
		shipIm .setPosition(posx+0*(width/9+pad)+4.5f*pad+width/18-shipIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-shipIm.getHeight()/2);
		shipIm.setOrigin(width/20, width/40);
		shipIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		shipIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				descriptiontxt.setText(CatGame.myBundle.get("catfighter"));
				descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
			}
		});
		
		propIm .setSize(width/12, width/18);
		propIm .setPosition(posx+1*(width/9+pad)+4.5f*pad+width/18-propIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-propIm.getHeight()/2);
		propIm.setOrigin(Align.center);
		propIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		propIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				descriptiontxt.setText(CatGame.myBundle.get("propeler"));
				descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
			}
		});
		
		rgIm .setSize(width/12, width/20);
		rgIm .setPosition(posx+2*(width/9+pad)+4.5f*pad+width/18-rgIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-rgIm.getHeight()/2);
		rgIm.setOrigin(Align.center);
		rgIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		rgIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				rGcheck=true;
				equipButton.addAction(sequence(alpha(1), show()));
				descriptiontxt.setText(CatGame.myBundle.get("rgtxt"));
				descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
			}
		});
		
		rizgrowIm .setSize(width/12, width/12);
		rizgrowIm .setPosition(posx+3*(width/9+pad)+4.5f*pad+width/18-rizgrowIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-rizgrowIm.getHeight()/2);
		rizgrowIm.setOrigin(Align.center);
		rizgrowIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		rizgrowIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				descriptiontxt.setText(CatGame.myBundle.get("makikit"));
				descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
			}
		});
		
		hmakiIm .setSize(width/14, width/14);
		hmakiIm .setPosition(posx+4*(width/9+pad)+4.5f*pad+width/18-hmakiIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-hmakiIm.getHeight()/2);
		hmakiIm.setOrigin(Align.center);
		hmakiIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		hmakiIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.hmakiDisp){
					hmakicheck=true;
					descriptiontxt.setText(CatGame.myBundle.get("hmaki"));
					descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
					if(!Save.gd.hmakiOwn){
						upbutton.setText(CatGame.myBundle.get("buy"));
						costTec.setText(Integer.toString(hmakicost)+"/" +Integer.toString(Save.gd.metal));
						if(Save.gd.metal<hmakicost)costTec.setColor(1, 0, 0, 1);
					}
					
				}
				
				
			}
		});
		
		drillIm .setSize(width/12, width/12);
		drillIm .setPosition(posx+5*(width/9+pad)+4.5f*pad+width/18-drillIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-drillIm.getHeight()/2);
		drillIm.setOrigin(Align.center);
		drillIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		drillIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.drillDisp){
					drillcheck=true;
					descriptiontxt.setText(CatGame.myBundle.get("drilltxt"));
					descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
					if(!Save.gd.drillOwn){
						upbutton.setText(CatGame.myBundle.get("buy"));
						costTec.setText(Integer.toString(drillcost)+"/" +Integer.toString(Save.gd.metal));
						if(Save.gd.metal<drillcost)costTec.setColor(1, 0, 0, 1);
					}
				}
			}
		});
		
		sushiMIm .setSize(width/12, width/20);
		sushiMIm .setPosition(posx+0*(width/9+pad)+4.5f*pad+width/18-sushiMIm.getWidth()/2, case1.getY()-width/9-1.3f*pad+width/18-sushiMIm.getHeight()/2);
		sushiMIm.setOrigin(Align.center);
		sushiMIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		sushiMIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.smDisp){
					sMcheck=true;
					if(Save.gd.smOwn)equipButton.addAction(sequence(alpha(1), show()));
					descriptiontxt.setText(CatGame.myBundle.get("sushigtxt"));
					descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
					if(!Save.gd.smOwn){
						upbutton.setText(CatGame.myBundle.get("buy"));
						costTec.setText(Integer.toString(sMcost)+"/" +Integer.toString(Save.gd.metal));
						if(Save.gd.metal<sMcost)costTec.setColor(1, 0, 0, 1);
					}
				}
			}
		});
		
		laserIm .setSize(width/12, width/20);
		laserIm .setPosition(posx+1*(width/9+pad)+4.5f*pad+width/18-sushiMIm.getWidth()/2, case1.getY()-width/9-1.3f*pad+width/18-sushiMIm.getHeight()/2);
		laserIm.setOrigin(Align.center);
		laserIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		laserIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.lzDisp){
					lzcheck=true;
					if(Save.gd.lzOwn)equipButton.addAction(sequence(alpha(1), show()));
					descriptiontxt.setText(CatGame.myBundle.get("lasertxt"));
					descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
					if(!Save.gd.lzOwn){
						upbutton.setText(CatGame.myBundle.get("buy"));
						costTec.setText(Integer.toString(lzcost)+"/" +Integer.toString(Save.gd.metal));
						if(Save.gd.metal<lzcost)costTec.setColor(1, 0, 0, 1);
					}
				}
			}
		});
		
		shieldIm .setSize(width/12, width/12);
		shieldIm .setPosition(posx+2*(width/9+pad)+4.5f*pad+width/18-shieldIm.getWidth()/2, case1.getY()-width/9-1.3f*pad+width/18-shieldIm.getHeight()/2);
		shieldIm.setOrigin(Align.center);
		shieldIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		shieldIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.shieldDisp){
					shieldcheck=true;
					descriptiontxt.setText(CatGame.myBundle.get("shieldtxt"));
					descriptionG.addAction(sequence(show(),alpha(1,0.2f)));
					if(!Save.gd.shieldOwn){
						upbutton.setText(CatGame.myBundle.get("buy"));
						costTec.setText(Integer.toString(shieldcost)+"/" +Integer.toString(Save.gd.metal));
						if(Save.gd.metal<shieldcost)costTec.setColor(1, 0, 0, 1);
					}
				}
			}
		});
		
		/*propIm .setSize(width/12, width/12);
		propIm .setPosition(posx+1*(width/9+pad)+4.5f*pad+width/18-propIm.getWidth()/2, titlebg.getY()-width/9-1.3f*pad+width/18-propIm.getHeight()/2);
		propIm.setOrigin(Align.center);
		propIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));*/
		
		upbutton.setSize(140*scale, width/12);
		upbutton.setPosition(posx+4*pad+width/12, screenH*2/8-upbutton.getHeight()/2);
		
		
		costbg.setPosition  (posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*2/8-upbutton.getHeight()/2);
		costbg.setSize(costTec.getWidth()+2*pad+costTec.getHeight()+pad/2, costTec.getHeight()+pad);
		
		metalIm.setPosition(costbg.getX()+2*pad+costTec.getWidth(), costbg.getY()+pad/2);
		metalIm.setSize(costTec.getHeight(), costTec.getHeight());
		costTec.setPosition(costbg.getX()+3*pad/2, costbg.getY()+pad/2);
		
		canceltec.setSize(scale*140,width/12);
		canceltec.setPosition(posx+screenW/14+4*pad+width/12+width-(posx+2*pad+width/12)+4*pad-canceltec.getWidth(), screenH*2/8-canceltec.getHeight()/2);
		
		technoPage.addActor(titleImbg);
		technoPage.addActor(titlebg);
		technoPage.addActor(titleIm);
		technoPage.addActor(title);
		technoPage.addActor(case1);
		technoPage.addActor(case2);
		technoPage.addActor(case3);
		technoPage.addActor(case4);
		technoPage.addActor(case5);
		technoPage.addActor(case6);
		technoPage.addActor(case7);
		technoPage.addActor(case8);
		technoPage.addActor(case9);
		technoPage.addActor(case10);
		technoPage.addActor(case11);
		technoPage.addActor(case12);
		technoPage.addActor(shipIm);
		technoPage.addActor(propIm);
		technoPage.addActor(rizgrowIm);
		technoPage.addActor(rgIm);
		technoPage.addActor(drillIm);
		technoPage.addActor(sushiMIm);
		technoPage.addActor(laserIm);
		technoPage.addActor(hmakiIm);
		technoPage.addActor(shieldIm);
		technoPage.addActor(descriptionG);
		technoPage.addActor(equipG);
		technoPage.addActor(upbutton);
		technoPage.addActor(costbg);
		technoPage.addActor(metalIm);
		technoPage.addActor(costTec);
		technoPage.addActor(canceltec);
		upbutton.setText("");
		
		technoPage.setWidth(screenW);
		technoPage.setPosition(0*screenW*0.83f, 0);
		costTec.setText(Integer.toString(0)+"/" +Integer.toString(Save.gd.metal));
		return technoPage;
		
	}

	
	private Group createShipPage(){
		///////////////////////////////////////////////////////////SHIP PAGE///////////////////////////////////////////////////
		final Group shipPage =new Group();
		Label title1 = new Label(CatGame.myBundle.get("ship"), skin);
		
		float width1 = screenW*3/4-screenW/30;
		float height1= screenH*3/4-screenW/30;
		float pad1=screenW/60;
		float posx1 = screenW/8+screenW/60+screenW/16;
		
		Image weaponbg1 =new Image(skin.newDrawable("white", Color.BLACK));
		Image titlebg1  =new Image(skin.newDrawable("white", Color.BLACK));
		Image costbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image precbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image speedbg1  =new Image(skin.newDrawable("white", Color.BLACK));
		Image powerbg1  =new Image(skin.newDrawable("white", Color.BLACK));
		Image pbarbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image sbarbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image wbarbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image shipIm;
		//heading
		if(Save.gd.hp>CatGame.maxhp*2/3){
			shipIm = new Image(Assets.manager.get(Assets.spaceship3,Texture.class));
		}
		else if(Save.gd.hp>CatGame.maxhp/3){
			shipIm = new Image(Assets.manager.get(Assets.spaceship2,Texture.class));
		}
		else {
			shipIm = new Image(Assets.manager.get(Assets.spaceship1,Texture.class));
		}
		Image metalIm1 =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		//Create
		Image armorIm=new Image(Assets.manager.get(Assets.armor,Texture.class));
		Image speedIm=new Image(Assets.manager.get(Assets.prop1,Texture.class));
		Image accIm =new Image(Assets.manager.get(Assets.arrow,Texture.class));
		accIm.setColor(Color.RED);
		
		hpBar    = new ProgressBar(0, maxhp   , 1, false, skin, "red");
		hpBar.setAnimateDuration(0.3f);
		hpBar.setAnimateInterpolation(fade);
	
		speedBar = new ProgressBar(0, maxSpeed, 0.5f, false, skin, "red");
		speedBar.setAnimateDuration(0.3f);
		speedBar.setAnimateInterpolation(fade);
		
		accBar   = new ProgressBar(0, maxAcc  , 1, false, skin, "red");
		accBar.setAnimateDuration(0.3f);
		accBar.setAnimateInterpolation(fade);
		
		
		Image plusbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg2   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg3   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image plushp    = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plusspeed = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plusacc   = new Image(Assets.manager.get(Assets.plus,Texture.class));
		
		final TextButton upbutton1     = new TextButton(CatGame.myBundle.get("upgrade") ,skin);
		upbutton1.pad(2, 12, 2, 12);
		upbutton1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.metal>=metalship){
					Save.gd.metal-=metalship;
					Save.gd.metalspent+=metalship;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(metalship);
					}
					else Save.gd.metalspentgs+=metalship;
					Save.gd.hp           = hpBar.getValue();
					Save.gd.speed        = speedBar.getValue();
					Save.gd.acceleration = accBar.getValue();
					Save.save();
					hp    =0;
					speed =0;
					acc   =0;
					upgraded=true;
				}
			}
		});
		cancelship = new TextButton(CatGame.myBundle.get("cancel"), skin,  "toggle" );
		cancelship.pad(2, 12, 2, 12);
		cancelship.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(hp==0&&speed==0&&acc==0){
					lab.clearActions();
					lab.getColor().a = lab.isVisible() ? 1 : 0;
					if (lab.isVisible()){
						lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
					cancelship.setChecked(false);
				}
				hp   =0;
				speed=0;
				acc  =0;
				cancelship.setChecked(false);
			}
		});
			
		
		costShip =new Label("100/200", skin);
		
		//layout
		
		precbg1.setSize(width1/12, width1/12);
		precbg1.setPosition(posx1+2*pad1, screenH*5/8-width1/24);
		speedbg1.setSize(width1/12, width1/12);
		speedbg1.setPosition(posx1+2*pad1, screenH*4/8-width1/24);
		powerbg1.setSize(width1/12, width1/12);
		powerbg1.setPosition(posx1+2*pad1, screenH*3/8-width1/24);
		armorIm .setSize(width1/12, width1/12);
		speedIm.setSize(width1/12, width1/18);
		accIm.setSize(width1/12, width1/12);
		armorIm .setPosition(posx1+2*pad1, screenH*5/8-width1/24);
		speedIm.setPosition(posx1+2*pad1, screenH*4/8-width1/36);
		accIm.setPosition(posx1+2*pad1, screenH*3/8-width1/24);
		armorIm.setOrigin(width1/24, width1/24);
		armorIm.addAction(forever(sequence(rotateTo(5,0.5f,sine),rotateTo(-5,0.5f,sine))));
		speedIm.addAction(forever(
				sequence(rotateTo(5,1),
						 rotateTo(-5,1)
						 )));
		accIm.addAction(forever(
				sequence(delay(0.3f),moveTo(posx1+2*pad1, screenH*3/8-width1/24-10*scale),
						 alpha(1, 0.1f),
						 moveTo(posx1+2*pad1, screenH*3/8-width1/24+10*scale,0.2f),
						 alpha(0,0.2f)
						 )));
		final Label hplabel = new Label(CatGame.myBundle.get("armor"), skin);
		hplabel.setTouchable(Touchable.disabled);
		armorIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				hplabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label speedlabel = new Label(CatGame.myBundle.get("speed"), skin);
		speedlabel.setTouchable(Touchable.disabled);
		speedIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				speedlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label acclabel = new Label(CatGame.myBundle.get("acceleration"), skin);
		acclabel.setTouchable(Touchable.disabled);
		accIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				acclabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		
		weaponbg1.setPosition(posx1+4*pad1+width1/12, screenH*6/8-width1/24);
		weaponbg1.setSize(width1/5, height1-(screenH*5/8-width1/12)-2*pad1);
		shipIm.setSize(width1/6, height1/8);
		shipIm.setPosition(weaponbg1.getX()+weaponbg1.getWidth()/2-shipIm.getWidth()/2, weaponbg1.getY()+weaponbg1.getHeight()/2-shipIm.getHeight()/2);
		shipIm.addAction(forever(sequence(sizeTo(width1/6*0.85f,height1/8*0.85f,0.5f,sine),sizeTo(width1/6*1.07f, height1/8*1.07f,0.5f,sine))));
		titlebg1.setPosition(posx1+4*pad1+width1/12+width1/5+ screenH*1/8-width1/12, screenH*6/8-width1/24);
		titlebg1.setSize(title1.getWidth()+2*pad1, height1-(screenH*5/8-width1/12)-2*pad1);
		title1.setPosition(titlebg1.getX()+titlebg1.getWidth()/2-title1.getWidth()/2, titlebg1.getY()+titlebg1.getHeight()/2-title1.getHeight()/2);
		
		pbarbg1.setPosition(posx1+4*pad1+width1/12, screenH*5/8-width1/24);
		pbarbg1.setSize(width1-(posx1+2*pad1+width1/6)+4*pad1, width1/12);
		hpBar .setSize(width1-(posx1+2*pad1+width1/6), 30*scale);
		hpBar .setPosition(posx1+6*pad1+width1/12, pbarbg1.getY()+pbarbg1.getHeight()/2-15*scale);
		hplabel.setPosition(hpBar.getX()+hpBar.getWidth()/2-hplabel.getWidth()/2, hpBar.getY()+hpBar.getHeight()/1.75f-hplabel.getHeight()/2);
		hplabel.setAlignment(Align.center);
		hplabel.getColor().a=0;
		
		plusbg1.setPosition(posx1+width1-2*pad1-width1/12, screenH*5/8-width1/24);
		plusbg1.setSize(width1/12, width1/12);
		plushp.setPosition(posx1+width1-2*pad1-width1/24-width1/36, screenH*5/8-width1/36);
		plushp.setSize(width1/18, width1/18);
		plushp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				hplabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(hp<=maxhp)hp=hp+1;
				hpBar.setValue(hp);
				
			}
		});
		
		sbarbg1.setPosition(posx1+4*pad1+width1/12, screenH*4/8-width1/24);
		sbarbg1.setSize(width1-(posx1+2*pad1+width1/6)+4*pad1, width1/12);
		speedBar.setSize(width1-(posx1+2*pad1+width1/6), 30*scale);
		speedBar.setPosition(posx1+6*pad1+width1/12, sbarbg1.getY()+sbarbg1.getHeight()/2-15*scale);
		speedlabel.setPosition(speedBar.getX()+speedBar.getWidth()/2-speedlabel.getWidth()/2, speedBar.getY()+speedBar.getHeight()/1.75f-speedlabel.getHeight()/2);
		speedlabel.setAlignment(Align.center);
		speedlabel.getColor().a=0;
		
		plusbg2.setPosition(posx1+width1-2*pad1-width1/12, screenH*4/8-width1/24);
		plusbg2.setSize(width1/12, width1/12);
		plusspeed.setPosition(posx1+width1-2*pad1-width1/24-width1/36, screenH*4/8-width1/36);
		plusspeed.setSize(width1/18, width1/18);
		plusspeed.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				speedlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(speed<=maxSpeed)speed=speed+0.5f;
				speedBar.setValue(speed);
				
			}
		});
		
		wbarbg1.setPosition(posx1+4*pad1+width1/12, screenH*3/8-width1/24);
		wbarbg1.setSize(width1-(posx1+2*pad1+width1/6)+4*pad1, width1/12);
		accBar.setSize(width1-(posx1+2*pad1+width1/6), 30*scale);
		accBar.setPosition(posx1+6*pad1+width1/12, wbarbg1.getY()+wbarbg1.getHeight()/2-15*scale);
		acclabel.setPosition(accBar.getX()+accBar.getWidth()/2-acclabel.getWidth()/2, accBar.getY()+accBar.getHeight()/1.75f-acclabel.getHeight()/2);
		acclabel.setAlignment(Align.center);
		acclabel.getColor().a=0;
		
		plusbg3.setPosition(posx1+width1-2*pad1-width1/12, screenH*3/8-width1/24);
		plusbg3.setSize(width1/12, width1/12);
		plusacc.setPosition(posx1+width1-2*pad1-width1/24-width1/36, screenH*3/8-width1/36);
		plusacc.setSize(width1/18, width1/18);
		plusacc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				acclabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(acc<=maxAcc)acc=acc+1;
				accBar.setValue(acc);
				
				
			}
		});
		
		upbutton1.setSize(140*scale, width1/12);
		upbutton1.setPosition(posx1+4*pad1+width1/12, screenH*2/8-upbutton1.getHeight()/2);
		
		
		costbg1.setPosition  (posx1+4*pad1+width1/12+width1/5+ screenH*1/8-width1/12, screenH*2/8-upbutton1.getHeight()/2);
		costbg1.setSize(costShip.getWidth()+2*pad1+costShip.getHeight()+pad1/2, costShip.getHeight()+pad1);
		
		metalIm1.setPosition(costbg1.getX()+2*pad1+costShip.getWidth(), costbg1.getY()+pad1/2);
		metalIm1.setSize(costShip.getHeight(), costShip.getHeight());
		costShip.setPosition(costbg1.getX()+3*pad1/2, costbg1.getY()+pad1/2);
		
		cancelship.setSize(scale*140,width1/12);
		cancelship.setPosition(6*pad1+width1+screenW/14-cancelship.getWidth(), screenH*2/8-cancelship.getHeight()/2);
	
		
		
		//DnD
		dragAndDrop.setDragActorPosition(-(armorIm.getWidth()/2), armorIm.getHeight());

		//source
		dragAndDrop.addSource(new Source(hpBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("hp");
				payload.setDragActor(new Label(CatGame.myBundle.get("armor"),skin));
				return payload;
			}
		});
		
		dragAndDrop.addSource(new Source(speedBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("speed");
				payload.setDragActor(new Label(CatGame.myBundle.get("speed"),skin));
				return payload;
			}
			
		});
		
		dragAndDrop.addSource(new Source(accBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("acc");
				payload.setDragActor(new Label(CatGame.myBundle.get("acceleration"),skin));
				return payload;
			}
		});
		
		//Target
		
		dragAndDrop.addTarget(new Target(hpBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="hp" && x*maxhp/hpBar.getWidth()>Save.gd.hp){
					hp=x*maxhp/hpBar.getWidth()-Save.gd.hp;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="speed"){
			//		speed=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="speed"){
			//		speed=x*maxspeed/speedBar.getWidth()-Save.gd.riceGspeed;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(speedBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="speed"&& x*maxSpeed/speedBar.getWidth()>Save.gd.speed){
					speed=x*maxSpeed/speedBar.getWidth()-Save.gd.speed;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="precision"){
			//		prec=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="precision"){
			//		prec=x*maxprec/10/precBar.getWidth()-Save.gd.riceGprec/10;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(accBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="acc" && x*maxAcc/accBar.getWidth()>Save.gd.acceleration){
					acc=x*maxAcc/accBar.getWidth()-Save.gd.acceleration;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="power"){
			//		power=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="power"){
			//		power=power;
			//	}
			}
		});
		
		shipPage.addActor(weaponbg1);
		shipPage.addActor(titlebg1);
		shipPage.addActor(precbg1);
		shipPage.addActor(speedbg1);
		shipPage.addActor(powerbg1);
		shipPage.addActor(pbarbg1);
		shipPage.addActor(sbarbg1);
		shipPage.addActor(wbarbg1);
		shipPage.addActor(shipIm);
		shipPage.addActor(title1);
		shipPage.addActor(armorIm);
		shipPage.addActor(hpBar);
		shipPage.addActor(plusbg1);
		shipPage.addActor(plushp);
		shipPage.addActor(speedIm);
		shipPage.addActor(speedBar);
		shipPage.addActor(plusbg2);
		shipPage.addActor(plusspeed);
		shipPage.addActor(accIm);
		shipPage.addActor(accBar);
		shipPage.addActor(plusbg3);
		shipPage.addActor(plusacc);
		shipPage.addActor(upbutton1);
		shipPage.addActor(costbg1);
		shipPage.addActor(metalIm1);
		shipPage.addActor(costShip);
		shipPage.addActor(cancelship);
		shipPage.addActor(hplabel);
		shipPage.addActor(speedlabel);
		shipPage.addActor(acclabel);
		
		shipPage.setWidth(screenW);
		shipPage.setPosition(0, 0);
		return shipPage;
	}
	
	private Group createRGPage(){
		final Group ricegunPage =new Group();
		Label title = new Label(CatGame.myBundle.get("ricegun"), skin);
		
		float width = screenW*3/4-screenW/30;
		float height= screenH*3/4-screenW/30;
		float pad=screenW/60;
		float posx = screenW/8+screenW/60+screenW/16;
		
		Image weaponbg=new Image(skin.newDrawable("white", Color.BLACK));
		Image titlebg =new Image(skin.newDrawable("white", Color.BLACK));
		Image costbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image precbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image speedbg =new Image(skin.newDrawable("white", Color.BLACK));
		Image powerbg =new Image(skin.newDrawable("white", Color.BLACK));
		Image pbarbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image sbarbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image wbarbg  =new Image(skin.newDrawable("white", Color.BLACK));
		
		//heading
		Image weaponIm=new Image(Assets.manager.get(Assets.riceGun,Texture.class));
		Image metalIm =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		
		//Create
		Image precIm=new Image(Assets.manager.get(Assets.target,Texture.class));
		precIm.setColor(1, 0, 0, 1);
		Image firerateIm=new Image(Assets.manager.get(Assets.speed,Texture.class));
		Image powerIm=new Image(Assets.manager.get(Assets.power,Texture.class));
		
		Image plusbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg2   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg3   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image plusprec    = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plusFR      = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image pluspower   = new Image(Assets.manager.get(Assets.plus,Texture.class));
		
		rgprecBar = new ProgressBar(0, maxRGprec/10, 1, false, skin, "red");
		rgprecBar.setAnimateDuration(0.3f);
		rgprecBar.setAnimateInterpolation(fade);
	
		rgFRBar = new ProgressBar(0, maxRGFR, 1, false, skin, "red");
		rgFRBar.setAnimateDuration(0.3f);
		rgFRBar.setAnimateInterpolation(fade);
		
		rgpowerBar = new ProgressBar(0, maxRGpower/10, 1, false, skin, "red");
		rgpowerBar.setAnimateDuration(0.3f);
		rgpowerBar.setAnimateInterpolation(fade);
		
		final TextButton upbutton     = new TextButton(CatGame.myBundle.get("upgrade") ,skin);
		upbutton.pad(2, 12, 2, 12);
		upbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.metal>=metalRG){
					Save.gd.metal-=metalRG;
					Save.gd.metalspent+=metalRG;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(metalRG);
					}
					else Save.gd.metalspentgs+=metalRG;
					Save.gd.riceGpower=rgpowerBar.getValue()*10;
					Save.gd.riceGprec =rgprecBar .getValue()*10;
					Save.gd.riceGspeed=rgFRBar   .getValue();
					Save.save();
					rPower=0;
					rPrec =0;
					rFR   =0;
					upgraded=true;
				}
			}
		});
		cancelRG = new TextButton(CatGame.myBundle.get("cancel"), skin,  "toggle" );
		cancelRG.pad(2, 12, 2, 12);
		cancelRG.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(rPower==0&&rPrec==0&&rFR==0){
					lab.clearActions();
					lab.getColor().a = ricegunPage.isVisible() ? 1 : 0;
					if (lab.isVisible()){
						lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
					cancelRG.setChecked(false);
				}
				rPower=0;
				rPrec=0;
				rFR=0;
				cancelRG.setChecked(false);
			}
		});
			
		
		costRG =new Label("100/200", skin);
		
		//layout
		
		precbg.setSize(width/12, width/12);
		precbg.setPosition(posx+2*pad, screenH*5/8-width/24);
		speedbg.setSize(width/12, width/12);
		speedbg.setPosition(posx+2*pad, screenH*4/8-width/24);
		powerbg.setSize(width/12, width/12);
		powerbg.setPosition(posx+2*pad, screenH*3/8-width/24);
		precIm .setSize(width/12, width/12);
		firerateIm.setSize(width/12, width/12);
		powerIm.setSize(width/12, width/12);
		precIm .setPosition(posx+2*pad, screenH*5/8-width/24);
		firerateIm.setPosition(posx+2*pad, screenH*4/8-width/24);
		powerIm.setPosition(posx+2*pad, screenH*3/8-width/24);
		precIm.setOrigin(width/24, width/24);
		precIm.addAction(forever(sequence(rotateTo(180,2f,sine),rotateTo(-180,2,sine))));
		firerateIm.addAction(forever(
				sequence(delay(0.3f),moveTo(posx+2*pad-10*scale, screenH*4/8-width/24-10*scale),
						 alpha(1, 0.1f),
						 moveTo(posx+2*pad+10*scale, screenH*4/8-width/24+10*scale,0.2f),
						 alpha(0,0.2f)
						 )));
		powerIm.addAction(forever(sequence(alpha(1, 0.1f),delay(0.7f), alpha(0,0.2f))));
		
		final Label preclabel = new Label(CatGame.myBundle.get("precision"), skin);
		preclabel.setTouchable(Touchable.disabled);
		precIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				preclabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label frlabel = new Label(CatGame.myBundle.get("ricerate"), skin);
		frlabel.setTouchable(Touchable.disabled);
		firerateIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				frlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label pwrlabel = new Label(CatGame.myBundle.get("ricepower"), skin);
		pwrlabel.setTouchable(Touchable.disabled);
		powerIm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				pwrlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		
		
		weaponbg.setPosition(posx+4*pad+width/12, screenH*6/8-width/24);
		weaponbg.setSize(width/5, height-(screenH*5/8-width/12)-2*pad);
		weaponIm.setSize(width/6, height/8);
		weaponIm.setPosition(weaponbg.getX()+weaponbg.getWidth()/2-weaponIm.getWidth()/2, weaponbg.getY()+weaponbg.getHeight()/2-weaponIm.getHeight()/2);
		weaponIm.addAction(forever(sequence(sizeTo(width/6*0.85f,height/8*0.85f,0.5f,sine),sizeTo(width/6*1.07f, height/8*1.07f,0.5f,sine))));
		titlebg.setPosition(posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*6/8-width/24);
		titlebg.setSize(title.getWidth()+2*pad, height-(screenH*5/8-width/12)-2*pad);
		title.setPosition(titlebg.getX()+titlebg.getWidth()/2-title.getWidth()/2, titlebg.getY()+titlebg.getHeight()/2-title.getHeight()/2);
		
		pbarbg.setPosition(posx+4*pad+width/12, screenH*5/8-width/24);
		pbarbg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		rgprecBar .setSize(width-(posx+2*pad+width/6), 30*scale);
		rgprecBar .setPosition(posx+6*pad+width/12, pbarbg.getY()+pbarbg.getHeight()/2-15*scale);
		preclabel.setPosition(rgprecBar.getX()+rgprecBar.getWidth()/2-preclabel.getWidth()/2, rgprecBar.getY()+rgprecBar.getHeight()/1.75f-preclabel.getHeight()/2);
		preclabel.setAlignment(Align.center);
		preclabel.getColor().a=0;
		
		sbarbg.setPosition(posx+4*pad+width/12, screenH*4/8-width/24);
		sbarbg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		rgFRBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		rgFRBar.setPosition(posx+6*pad+width/12, sbarbg.getY()+sbarbg.getHeight()/2-15*scale);
		frlabel.setPosition(rgFRBar.getX()+rgFRBar.getWidth()/2-frlabel.getWidth()/2, rgFRBar.getY()+rgFRBar.getHeight()/1.75f-frlabel.getHeight()/2);
		frlabel.setAlignment(Align.center);
		frlabel.getColor().a=0;
		
		wbarbg.setPosition(posx+4*pad+width/12, screenH*3/8-width/24);
		wbarbg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		rgpowerBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		rgpowerBar.setPosition(posx+6*pad+width/12, wbarbg.getY()+wbarbg.getHeight()/2-15*scale);
		pwrlabel.setPosition(rgpowerBar.getX()+rgpowerBar.getWidth()/2-pwrlabel.getWidth()/2, rgpowerBar.getY()+rgpowerBar.getHeight()/1.75f-pwrlabel.getHeight()/2);
		pwrlabel.setAlignment(Align.center);
		pwrlabel.getColor().a=0;
		
		plusbg1.setPosition(posx+width-2*pad-width/12, screenH*5/8-width/24);
		plusbg1.setSize(width/12, width/12);
		plusprec.setPosition(posx+width-2*pad-width/24-width/36, screenH*5/8-width/36);
		plusprec.setSize(width/18, width/18);
		plusprec.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				preclabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(rPrec<=maxRGprec)rPrec=rPrec+1;
				rgprecBar.setValue(rPrec);
				
			}
		});
		
		plusbg2.setPosition(posx+width-2*pad-width/12, screenH*4/8-width/24);
		plusbg2.setSize(width/12, width/12);
		plusFR.setPosition(posx+width-2*pad-width/24-width/36, screenH*4/8-width/36);
		plusFR.setSize(width/18, width/18);
		plusFR.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				frlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(rFR<=maxRGFR)rFR=rFR+1;
				rgFRBar.setValue(rFR);
				
			}
		});
		
		plusbg3.setPosition(posx+width-2*pad-width/12, screenH*3/8-width/24);
		plusbg3.setSize(width/12, width/12);
		pluspower.setPosition(posx+width-2*pad-width/24-width/36, screenH*3/8-width/36);
		pluspower.setSize(width/18, width/18);
		pluspower.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				pwrlabel.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(rPower<=maxRGpower)rPower=rPower+1;
				accBar.setValue(rPower);
				
			}
		});
		
		upbutton.setSize(140*scale, width/12);
		upbutton.setPosition(posx+4*pad+width/12, screenH*2/8-upbutton.getHeight()/2);
		
		
		costbg.setPosition  (posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*2/8-upbutton.getHeight()/2);
		costbg.setSize(costRG.getWidth()+2*pad+costRG.getHeight()+pad/2, costRG.getHeight()+pad);
		
		metalIm.setPosition(costbg.getX()+2*pad+costRG.getWidth(), costbg.getY()+pad/2);
		metalIm.setSize(costRG.getHeight(), costRG.getHeight());
		costRG.setPosition(costbg.getX()+3*pad/2, costbg.getY()+pad/2);
		
		cancelRG.setSize(scale*140,width/12);
		cancelRG.setPosition(posx+screenW/14+4*pad+width/12+width-(posx+2*pad+width/12)+4*pad-cancelRG.getWidth(), screenH*2/8-cancelRG.getHeight()/2);
	
		
		
		//DnD
		dragAndDrop.setDragActorPosition(-(precIm.getWidth()/2), precIm.getHeight());

		//source
		
		dragAndDrop.addSource(new Source(rgprecBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("precision");
				payload.setDragActor(new Label(CatGame.myBundle.get("precision"),skin));
				return payload;
			}
		});
		
		dragAndDrop.addSource(new Source(rgFRBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("fr");
				payload.setDragActor(new Label(CatGame.myBundle.get("ricerate"),skin));
				return payload;
			}
			
		});
		
		dragAndDrop.addSource(new Source(rgpowerBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("power");
				payload.setDragActor(new Label(CatGame.myBundle.get("ricepower"),skin));
				return payload;
			}
		});
		
		//Target
		
		dragAndDrop.addTarget(new Target(rgFRBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="fr" && x*maxRGFR/rgFRBar.getWidth()>Save.gd.riceGspeed){
					rFR=x*maxRGFR/rgFRBar.getWidth()-Save.gd.riceGspeed;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="speed"){
			//		speed=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="speed"){
			//		speed=x*maxspeed/speedBar.getWidth()-Save.gd.riceGspeed;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(rgprecBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="precision"&& x*maxRGprec/rgprecBar.getWidth()>Save.gd.riceGprec){
					rPrec=x*maxRGprec/10/rgprecBar.getWidth()-Save.gd.riceGprec/10;
				}
				
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="precision"){
			//		prec=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="precision"){
			//		prec=x*maxprec/10/precBar.getWidth()-Save.gd.riceGprec/10;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(rgpowerBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="power" && x*maxRGpower/rgpowerBar.getWidth()>Save.gd.riceGpower){
					rPower=x*maxRGpower/10/rgpowerBar.getWidth()-Save.gd.riceGpower/10;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="power"){
			//		power=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="power"){
			//		power=power;
			//	}
			}
		});
		
		ricegunPage.addActor(weaponbg);
		ricegunPage.addActor(titlebg);
		ricegunPage.addActor(precbg);
		ricegunPage.addActor(speedbg);
		ricegunPage.addActor(powerbg);
		ricegunPage.addActor(pbarbg);
		ricegunPage.addActor(sbarbg);
		ricegunPage.addActor(wbarbg);
		ricegunPage.addActor(weaponIm);
		ricegunPage.addActor(title);
		ricegunPage.addActor(precIm);
		ricegunPage.addActor(rgprecBar);
		ricegunPage.addActor(firerateIm);
		ricegunPage.addActor(rgFRBar);
		ricegunPage.addActor(powerIm);
		ricegunPage.addActor(rgpowerBar);
		ricegunPage.addActor(plusbg1);
		ricegunPage.addActor(plusbg2);
		ricegunPage.addActor(plusbg3);
		ricegunPage.addActor(plusprec);
		ricegunPage.addActor(plusFR);
		ricegunPage.addActor(pluspower);
		ricegunPage.addActor(upbutton);
		ricegunPage.addActor(costbg);
		ricegunPage.addActor(metalIm);
		ricegunPage.addActor(costRG);
		ricegunPage.addActor(cancelRG);
		ricegunPage.addActor(pwrlabel);
		ricegunPage.addActor(frlabel);
		ricegunPage.addActor(preclabel);
		
		
		ricegunPage.setWidth(screenW);
		ricegunPage.setPosition(0, 0);
		
		return ricegunPage;
		
	}
	
	private Group createSMPage(){
		final Group smPage =new Group();
		Label title = new Label(CatGame.myBundle.get("sushilauncher"), skin);
		
		float width = screenW*3/4-screenW/30;
		float height= screenH*3/4-screenW/30;
		float pad=screenW/60;
		float posx = screenW/8+screenW/60+screenW/16;
		
		Image weaponbg=new Image(skin.newDrawable("white", Color.BLACK));
		Image titlebg =new Image(skin.newDrawable("white", Color.BLACK));
		Image costbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec1bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec2bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec3bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar1bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar2bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar3bg  =new Image(skin.newDrawable("white", Color.BLACK));
		
		//heading
		Image weaponIm=new Image(Assets.manager.get(Assets.sushiGun,Texture.class));
		Image metalIm =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		
		//Create
		Image spec1Im=new Image(Assets.manager.get(Assets.fragment,Texture.class));
		Image spec1Im2=new Image(Assets.manager.get(Assets.fragment,Texture.class));
		spec1Im2.setTouchable(Touchable.disabled);
		Image spec2Im=new Image(Assets.manager.get(Assets.reload1,Texture.class));
		Image spec2Im2=new Image(Assets.manager.get(Assets.reload2,Texture.class));
		spec2Im2.setTouchable(Touchable.disabled);
		spec2Im2.setColor(Color.RED);
		Image spec3Im=new Image(Assets.manager.get(Assets.sushiMissil,Texture.class));
		
		Image plusbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg2   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg3   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image plus1    = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plus2      = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plus3   = new Image(Assets.manager.get(Assets.plus,Texture.class));
		
		smNbrBar = new ProgressBar(0, maxSMnbr, 1, false, skin, "red");
		smNbrBar.setAnimateDuration(0.3f);
		smNbrBar.setAnimateInterpolation(fade);
	
		smFRBar = new ProgressBar(0, maxSMFR*10, 2, false, skin, "red");
		smFRBar.setAnimateDuration(0.3f);
		smFRBar.setAnimateInterpolation(fade);
		
		smspeedBar = new ProgressBar(0, maxSMspeed, 0.5f, false, skin, "red");
		smspeedBar.setAnimateDuration(0.3f);
		smspeedBar.setAnimateInterpolation(fade);
		
		final TextButton upbutton     = new TextButton(CatGame.myBundle.get("upgrade") ,skin);
		upbutton.pad(2, 12, 2, 12);
		upbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(Save.gd.metal>= metalSM){
					Save.gd.metalspent+=metalSM;
					Save.gd.metal-=metalSM;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(metalSM);
					}
					else Save.gd.metalspentgs+=metalSM;
					Save.gd.sushiGnbr = smNbrBar  .getValue();
					Save.gd.sushiGfr  = smFRBar   .getValue()/10;
					Save.gd.sushiGspeed= smspeedBar.getValue();
					Save.save();
					snbr=0;
					sFR =0;
					sSpeed=0;
					upgraded=true;
				}
			}
		});
		cancelSM = new TextButton(CatGame.myBundle.get("cancel"), skin);
		cancelSM.pad(2, 12, 2, 12);
		cancelSM.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(snbr==0&&sFR==0&&sSpeed==0){
					lab.clearActions();
					lab.getColor().a = smPage.isVisible() ? 1 : 0;
					if (lab.isVisible()){
						lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
					cancelSM.setChecked(false);
				}
				snbr=0;
				sFR =0;
				sSpeed=0;
			}
		});
			
		
		costSM =new Label("100/200", skin);
		
		//layout
		
		spec1bg.setSize(width/12, width/12);
		spec1bg.setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2bg.setSize(width/12, width/12);
		spec2bg.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec3bg.setSize(width/12, width/12);
		spec3bg.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im .setSize(width/12, width/12);
		spec1Im2 .setSize(width/12, width/12);
		spec2Im.setSize(width/12, width/12);
		spec2Im2.setSize(width/12, width/12);
		spec3Im.setSize(width/12, width/12);
		spec1Im .setPosition(posx+2*pad, screenH*5/8-width/24);
		spec1Im2.setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2Im.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec2Im2.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec3Im.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im.setOrigin(width/24, width/24);
		spec1Im2.setOrigin(width/24, width/24);
		spec1Im2.rotateBy(22.5f);
		spec1Im2.addAction(forever(sequence(alpha(1, 0.1f),delay(0.7f), alpha(0,0.2f))));
		spec2Im2.setOrigin(Align.center);
		spec2Im2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
		
		spec3Im.addAction(forever(
				sequence(delay(0.3f),moveTo(posx+2*pad-10*scale, screenH*3/8-width/24),
						 alpha(1, 0.1f),
						 moveTo(posx+2*pad+10*scale, screenH*3/8-width/24,0.2f),
						 alpha(0,0.2f)
						 )));
		
		final Label spec1label = new Label(CatGame.myBundle.get("fragments"), skin);
		spec1label.setTouchable(Touchable.disabled);
		spec1Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label spec2label = new Label(CatGame.myBundle.get("sushirate"), skin);
		spec2label.setTouchable(Touchable.disabled);
		spec2Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label spec3label = new Label(CatGame.myBundle.get("sushispeed"), skin);
		spec3label.setTouchable(Touchable.disabled);
		spec3Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		
		
		weaponbg.setPosition(posx+4*pad+width/12, screenH*6/8-width/24);
		weaponbg.setSize(width/5, height-(screenH*5/8-width/12)-2*pad);
		weaponIm.setSize(width/6, height/8);
		weaponIm.setPosition(weaponbg.getX()+weaponbg.getWidth()/2-weaponIm.getWidth()/2, weaponbg.getY()+weaponbg.getHeight()/2-weaponIm.getHeight()/2);
		weaponIm.addAction(forever(sequence(sizeTo(width/6*0.85f,height/8*0.85f,0.5f,sine),sizeTo(width/6*1.07f, height/8*1.07f,0.5f,sine))));
		
		titlebg.setPosition(posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*6/8-width/24);
		titlebg.setSize(title.getWidth()+2*pad, height-(screenH*5/8-width/12)-2*pad);
		title.setPosition(titlebg.getX()+titlebg.getWidth()/2-title.getWidth()/2, titlebg.getY()+titlebg.getHeight()/2-title.getHeight()/2);
		
		bar1bg.setPosition(posx+4*pad+width/12, screenH*5/8-width/24);
		bar1bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		smNbrBar .setSize(width-(posx+2*pad+width/6), 30*scale);
		smNbrBar .setPosition(posx+6*pad+width/12, bar1bg.getY()+bar1bg.getHeight()/2-15*scale);
		
		bar2bg.setPosition(posx+4*pad+width/12, screenH*4/8-width/24);
		bar2bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		smFRBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		smFRBar.setPosition(posx+6*pad+width/12, bar2bg.getY()+bar2bg.getHeight()/2-15*scale);
		
		bar3bg.setPosition(posx+4*pad+width/12, screenH*3/8-width/24);
		bar3bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		smspeedBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		smspeedBar.setPosition(posx+6*pad+width/12, bar3bg.getY()+bar3bg.getHeight()/2-15*scale);
		
		spec1label.setPosition(smNbrBar.getX()+smNbrBar.getWidth()/2-spec1label.getWidth()/2, smNbrBar.getY()+smNbrBar.getHeight()/1.75f-spec1label.getHeight()/2);
		spec1label.setAlignment(Align.center);
		spec1label.getColor().a=0;
		spec2label.setPosition(smFRBar.getX()+smFRBar.getWidth()/2-spec2label.getWidth()/2, smFRBar.getY()+smFRBar.getHeight()/1.75f-spec2label.getHeight()/2);
		spec2label.setAlignment(Align.center);
		spec2label.getColor().a=0;
		spec3label.setPosition(smspeedBar.getX()+smspeedBar.getWidth()/2-spec3label.getWidth()/2, smspeedBar.getY()+smspeedBar.getHeight()/1.75f-spec3label.getHeight()/2);
		spec3label.setAlignment(Align.center);
		spec3label.getColor().a=0;
		
		plusbg1.setPosition(posx+width-2*pad-width/12, screenH*5/8-width/24);
		plusbg1.setSize(width/12, width/12);
		plus1.setPosition(posx+width-2*pad-width/24-width/36, screenH*5/8-width/36);
		plus1.setSize(width/18, width/18);
		plus1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(snbr<=maxSMnbr)snbr=snbr+1;
				smNbrBar.setValue(snbr);
				
			}
		});
		
		plusbg2.setPosition(posx+width-2*pad-width/12, screenH*4/8-width/24);
		plusbg2.setSize(width/12, width/12);
		plus2.setPosition(posx+width-2*pad-width/24-width/36, screenH*4/8-width/36);
		plus2.setSize(width/18, width/18);
		plus2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(sFR<=maxSMFR*10)sFR=sFR+2;
				smFRBar.setValue(sFR);
				
			}
		});
		
		plusbg3.setPosition(posx+width-2*pad-width/12, screenH*3/8-width/24);
		plusbg3.setSize(width/12, width/12);
		plus3.setPosition(posx+width-2*pad-width/24-width/36, screenH*3/8-width/36);
		plus3.setSize(width/18, width/18);
		plus3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(sSpeed<=maxSMspeed)sSpeed=sSpeed+1;
				smspeedBar.setValue(sSpeed);
				
			}
		});
		
		upbutton.setSize(140*scale, width/12);
		upbutton.setPosition(posx+4*pad+width/12, screenH*2/8-upbutton.getHeight()/2);
		
		
		costbg.setPosition  (posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*2/8-upbutton.getHeight()/2);
		costbg.setSize(costRG.getWidth()+2*pad+costRG.getHeight()+pad/2, costRG.getHeight()+pad);
		
		metalIm.setPosition(costbg.getX()+2*pad+costRG.getWidth(), costbg.getY()+pad/2);
		metalIm.setSize(costRG.getHeight(), costRG.getHeight());
		costSM.setPosition(costbg.getX()+3*pad/2, costbg.getY()+pad/2);
		
		cancelSM.setSize(scale*140,width/12);
		cancelSM.setPosition(posx+screenW/14+4*pad+width/12+width-(posx+2*pad+width/12)+4*pad-cancelSM.getWidth(), screenH*2/8-cancelSM.getHeight()/2);
	
		
		
		//DnD
		dragAndDrop.setDragActorPosition(-(spec1Im.getWidth()/2), spec1Im.getHeight());

		//source
		dragAndDrop.addSource(new Source(spec1Im) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("nbr");
				payload.setDragActor(new Label(CatGame.myBundle.get("fragments"),skin));
				return payload;
			}
		});
		dragAndDrop.addSource(new Source(smNbrBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("nbr");
				payload.setDragActor(new Label(CatGame.myBundle.get("fragments"),skin));
				return payload;
			}
		});
		
		
		dragAndDrop.addSource(new Source(smFRBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smfr");
				payload.setDragActor(new Label(CatGame.myBundle.get("sushirate"),skin));
				return payload;
			}
			
		});
		
		dragAndDrop.addSource(new Source(spec3Im) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smspeed");
				payload.setDragActor(new Label(CatGame.myBundle.get("sushispeed"),skin));
				return payload;
			}
		});
		dragAndDrop.addSource(new Source(smspeedBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smspeed");
				payload.setDragActor(new Label(CatGame.myBundle.get("sushispeed"),skin));
				return payload;
			}
		});
		
		//Target
		
		dragAndDrop.addTarget(new Target(smNbrBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="nbr" && x*maxSMnbr/smNbrBar.getWidth()>Save.gd.sushiGnbr){
					snbr=x*maxSMnbr/smNbrBar.getWidth()-Save.gd.sushiGnbr;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="speed"){
			//		speed=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="speed"){
			//		speed=x*maxspeed/speedBar.getWidth()-Save.gd.riceGspeed;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(smFRBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smfr"&& x*maxSMFR*10/smFRBar.getWidth()>Save.gd.sushiGfr*10){
					sFR=x*maxSMFR*10/smFRBar.getWidth()-Save.gd.sushiGfr*10;
				}
				
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="precision"){
			//		prec=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="precision"){
			//		prec=x*maxprec/10/precBar.getWidth()-Save.gd.riceGprec/10;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(smspeedBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smspeed" && x*maxSMspeed/smspeedBar.getWidth()>Save.gd.sushiGspeed){
					sSpeed=x*(maxSMspeed)/smspeedBar.getWidth()-Save.gd.sushiGspeed;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="power"){
			//		power=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="power"){
			//		power=power;
			//	}
			}
		});
		
		smPage.addActor(weaponbg);
		smPage.addActor(titlebg);
		smPage.addActor(spec1bg);
		smPage.addActor(spec2bg);
		smPage.addActor(spec3bg);
		smPage.addActor(bar1bg);
		smPage.addActor(bar2bg);
		smPage.addActor(bar3bg);
		smPage.addActor(weaponIm);
		smPage.addActor(title);
		smPage.addActor(spec1Im);
		smPage.addActor(spec1Im2);
		smPage.addActor(smNbrBar);
		smPage.addActor(spec2Im);
		smPage.addActor(spec2Im2);
		smPage.addActor(smFRBar);
		smPage.addActor(spec3Im);
		smPage.addActor(smspeedBar);
		smPage.addActor(plusbg1);
		smPage.addActor(plusbg2);
		smPage.addActor(plusbg3);
		smPage.addActor(plus1);
		smPage.addActor(plus2);
		smPage.addActor(plus3);
		smPage.addActor(upbutton);
		smPage.addActor(costbg);
		smPage.addActor(metalIm);
		smPage.addActor(costSM);
		smPage.addActor(cancelSM);
		smPage.addActor(spec1label);
		smPage.addActor(spec2label);
		smPage.addActor(spec3label);
		
		smPage.setWidth(screenW);
		smPage.setPosition(0f, 0);
		
		return smPage;
		
	}
	
	
	private Group createLZPage(){
		final Group page =new Group();
		Label title = new Label(CatGame.myBundle.get("laser"), skin);
		
		float width = screenW*3/4-screenW/30;
		float height= screenH*3/4-screenW/30;
		float pad=screenW/60;
		float posx = screenW/8+screenW/60+screenW/16;
		
		Image weaponbg=new Image(skin.newDrawable("white", Color.BLACK));
		Image titlebg =new Image(skin.newDrawable("white", Color.BLACK));
		Image costbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec1bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec2bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec3bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar1bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar2bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar3bg  =new Image(skin.newDrawable("white", Color.BLACK));
		
		//heading
		Image weaponIm=new Image(Assets.manager.get(Assets.laserGun,Texture.class));
		Image metalIm =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		
		//Create
		Image spec1Im =new Image(Assets.manager.get(Assets.lzmax  ,Texture.class));
		//Image spec1Im2=new Image(Assets.manager.get(Assets.lzanim ,Texture.class));
		Image spec2Im =new Image(Assets.manager.get(Assets.lzmin  ,Texture.class));
		Image spec3Im2=new Image(Assets.manager.get(Assets.reload2,Texture.class));
		Image spec3Im =new Image(Assets.manager.get(Assets.reload1,Texture.class));
		spec3Im2.setColor(Color.RED);
		
		Image plusbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg2   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg3   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image plus1    = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plus2      = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plus3   = new Image(Assets.manager.get(Assets.plus,Texture.class));
		
		lzmaxBar = new ProgressBar(0, maxLZpower, 0.05f, false, skin, "red");
		lzmaxBar.setAnimateDuration(0.3f);
		lzmaxBar.setAnimateInterpolation(fade);
	
		lzminBar = new ProgressBar(0,  minLZpower, 0.01f, false, skin, "red");
		lzminBar.setAnimateDuration(0.3f);
		lzminBar.setAnimateInterpolation(fade);
		
		lzrlBar = new ProgressBar(0, reloadLZ, 0.05f, false, skin, "red");
		lzrlBar.setAnimateDuration(0.3f);
		lzrlBar.setAnimateInterpolation(fade);
		
		final TextButton upbutton     = new TextButton(CatGame.myBundle.get("upgrade") ,skin);
		upbutton.pad(2, 12, 2, 12);
		upbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MapUI.click.play();
				if(Save.gd.metal>= metalLZ){
					Save.gd.metalspent+=metalLZ;
					Save.gd.metal-=metalLZ;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(metalLZ);
					}
					else Save.gd.metalspentgs+=metalLZ;
					Save.gd.lasermax   = lzmaxBar.getValue();
					Save.gd.lasermin   = lzminBar.getValue();
					Save.gd.laserreload= lzrlBar .getValue();
					Save.save();
					lzmax=0;
					lzmin =0;
					lreload=0;
					upgraded=true;
				}
			}
		});
		cancelLZ = new TextButton(CatGame.myBundle.get("cancel"), skin);
		cancelLZ.pad(2, 12, 2, 12);
		cancelLZ.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(lzmax==0 && lzmin==0 && lreload==0){
					lab.clearActions();
					lab.getColor().a = page.isVisible() ? 1 : 0;
					if (lab.isVisible()){
						lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
				}
				lzmax=0;
				lzmin =0;
				lreload=0;
			}
		});
			
		
		costLZ =new Label("100/200", skin);
		
		//layout
		
		spec1bg.setSize(width/12, width/12);
		spec1bg.setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2bg.setSize(width/12, width/12);
		spec2bg.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec3bg.setSize(width/12, width/12);
		spec3bg.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im .setSize(width/12, width/12);
	//	spec1Im2 .setSize(width/12, width/12);
		spec2Im.setSize(width/12, width/12);
		spec3Im2.setSize(width/12, width/12);
		spec3Im.setSize(width/12, width/12);
		spec1Im .setPosition(posx+2*pad, screenH*5/8-width/24);
	//	spec1Im2.setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2Im.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec3Im.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec3Im2.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im.setOrigin(width/24, width/24);
		spec3Im2.setOrigin(Align.center);
		spec3Im2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
		
		spec1Im.addAction(forever(sequence(alpha(1, 0.8f,sine), alpha(0.5f,0.8f,sine))));
		spec1Im.addAction(forever(sequence(color(Color.WHITE, 0.8f,sine), color(Color.CYAN,0.8f,sine))));
		spec2Im.addAction(forever(sequence(alpha(1, 0.7f,sine), alpha(0.5f,0.6f,sine))));
		spec2Im.addAction(forever(sequence(color(Color.WHITE, 0.7f,sine), color(Color.GREEN,0.6f,sine))));
		
		final Label spec1label = new Label(CatGame.myBundle.get("maxpower"), skin);
		spec1label.setTouchable(Touchable.disabled);
		spec1Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label spec2label = new Label(CatGame.myBundle.get("minpower"), skin);
		spec2label.setTouchable(Touchable.disabled);
		spec2Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label spec3label = new Label(CatGame.myBundle.get("laserload"), skin);
		spec3label.setTouchable(Touchable.disabled);
		spec3Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		
		
		
		weaponbg.setPosition(posx+4*pad+width/12, screenH*6/8-width/24);
		weaponbg.setSize(width/5, height-(screenH*5/8-width/12)-2*pad);
		weaponIm.setSize(width/6, height/8);
		weaponIm.setPosition(weaponbg.getX()+weaponbg.getWidth()/2-weaponIm.getWidth()/2, weaponbg.getY()+weaponbg.getHeight()/2-weaponIm.getHeight()/2);
		weaponIm.addAction(forever(sequence(sizeTo(width/6*0.85f,height/8*0.85f,0.5f,sine),sizeTo(width/6*1.07f, height/8*1.07f,0.5f,sine))));
		
		titlebg.setPosition(posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*6/8-width/24);
		titlebg.setSize(title.getWidth()+2*pad, height-(screenH*5/8-width/12)-2*pad);
		title.setPosition(titlebg.getX()+titlebg.getWidth()/2-title.getWidth()/2, titlebg.getY()+titlebg.getHeight()/2-title.getHeight()/2);
		
		bar1bg.setPosition(posx+4*pad+width/12, screenH*5/8-width/24);
		bar1bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		lzmaxBar .setSize(width-(posx+2*pad+width/6), 30*scale);
		lzmaxBar .setPosition(posx+6*pad+width/12, bar1bg.getY()+bar1bg.getHeight()/2-15*scale);
		
		bar2bg.setPosition(posx+4*pad+width/12, screenH*4/8-width/24);
		bar2bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		lzminBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		lzminBar.setPosition(posx+6*pad+width/12, bar2bg.getY()+bar2bg.getHeight()/2-15*scale);
		
		bar3bg.setPosition(posx+4*pad+width/12, screenH*3/8-width/24);
		bar3bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		lzrlBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		lzrlBar.setPosition(posx+6*pad+width/12, bar3bg.getY()+bar3bg.getHeight()/2-15*scale);
		
		spec1label.setPosition(lzmaxBar.getX()+lzmaxBar.getWidth()/2-spec1label.getWidth()/2, lzmaxBar.getY()+lzmaxBar.getHeight()/1.75f-spec1label.getHeight()/2);
		spec1label.setAlignment(Align.center);
		spec1label.getColor().a=0;
		spec2label.setPosition(lzminBar.getX()+lzminBar.getWidth()/2-spec2label.getWidth()/2, lzminBar.getY()+lzminBar.getHeight()/1.75f-spec2label.getHeight()/2);
		spec2label.setAlignment(Align.center);
		spec2label.getColor().a=0;
		spec3label.setPosition(lzrlBar.getX()+lzrlBar.getWidth()/2-spec3label.getWidth()/2, lzrlBar.getY()+lzrlBar.getHeight()/1.75f-spec3label.getHeight()/2);
		spec3label.setAlignment(Align.center);
		spec3label.getColor().a=0;
		
		plusbg1.setPosition(posx+width-2*pad-width/12, screenH*5/8-width/24);
		plusbg1.setSize(width/12, width/12);
		plus1.setPosition(posx+width-2*pad-width/24-width/36, screenH*5/8-width/36);
		plus1.setSize(width/18, width/18);
		plus1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(lzmax<=maxLZpower)lzmax=lzmax+0.05f;
				lzmaxBar.setValue(lzmax);
				
			}
		});
		
		plusbg2.setPosition(posx+width-2*pad-width/12, screenH*4/8-width/24);
		plusbg2.setSize(width/12, width/12);
		plus2.setPosition(posx+width-2*pad-width/24-width/36, screenH*4/8-width/36);
		plus2.setSize(width/18, width/18);
		plus2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(lzmin<= minLZpower)lzmin=lzmin+0.01f;
				lzminBar.setValue(lzmin);
				
			}
		});
		
		plusbg3.setPosition(posx+width-2*pad-width/12, screenH*3/8-width/24);
		plusbg3.setSize(width/12, width/12);
		plus3.setPosition(posx+width-2*pad-width/24-width/36, screenH*3/8-width/36);
		plus3.setSize(width/18, width/18);
		plus3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(lreload<=reloadLZ)lreload=lreload+0.05f;
				lzrlBar.setValue(lreload);
				
			}
		});
		
		upbutton.setSize(140*scale, width/12);
		upbutton.setPosition(posx+4*pad+width/12, screenH*2/8-upbutton.getHeight()/2);
		
		
		costbg.setPosition  (posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*2/8-upbutton.getHeight()/2);
		costbg.setSize(costRG.getWidth()+2*pad+costRG.getHeight()+pad/2, costRG.getHeight()+pad);
		
		metalIm.setPosition(costbg.getX()+2*pad+costRG.getWidth(), costbg.getY()+pad/2);
		metalIm.setSize(costRG.getHeight(), costRG.getHeight());
		costLZ.setPosition(costbg.getX()+3*pad/2, costbg.getY()+pad/2);
		
		cancelLZ.setSize(scale*140,width/12);
		cancelLZ.setPosition(posx+screenW/14+4*pad+width/12+width-(posx+2*pad+width/12)+4*pad-cancelLZ.getWidth(), screenH*2/8-cancelLZ.getHeight()/2);
	
		
		
		//DnD
		dragAndDrop.setDragActorPosition(-(spec1Im.getWidth()/2), spec1Im.getHeight());

		//source
		
	//	spec1Im2.setTouchable(Touchable.disabled);
		dragAndDrop.addSource(new Source(spec1Im) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("nbr");
				payload.setDragActor(new Label(CatGame.myBundle.get("maxpower"),skin));
				return payload;
			}
		});
		dragAndDrop.addSource(new Source(lzmaxBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("nbr");
				payload.setDragActor(new Label(CatGame.myBundle.get("maxpower"),skin));
				return payload;
			}
		});
		
		spec3Im2.setTouchable(Touchable.disabled);
		dragAndDrop.addSource(new Source(spec2Im) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smfr");
				payload.setDragActor(new Label(CatGame.myBundle.get("minpower"),skin));
				return payload;
			}
		});
		dragAndDrop.addSource(new Source(lzminBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smfr");
				payload.setDragActor(new Label(CatGame.myBundle.get("minpower"),skin));
				return payload;
			}
			
		});
		
		dragAndDrop.addSource(new Source(spec3Im) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smspeed");
				payload.setDragActor(new Label(CatGame.myBundle.get("laserload"),skin));
				return payload;
			}
		});
		dragAndDrop.addSource(new Source(lzrlBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smspeed");
				payload.setDragActor(new Label(CatGame.myBundle.get("laserload"),skin));
				return payload;
			}
		});
		
		//Target
		
		dragAndDrop.addTarget(new Target(lzmaxBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="nbr" && x*maxLZpower/lzmaxBar.getWidth()>Save.gd.lasermax){
					lzmax=x*maxLZpower/lzmaxBar.getWidth()-Save.gd.lasermax;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="speed"){
			//		speed=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="speed"){
			//		speed=x*maxspeed/speedBar.getWidth()-Save.gd.riceGspeed;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(lzminBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smfr"&& x* minLZpower/lzminBar.getWidth()>Save.gd.lasermin){
					lzmin=x* minLZpower/lzminBar.getWidth()-Save.gd.lasermin;
				}
				
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="precision"){
			//		prec=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="precision"){
			//		prec=x*maxprec/10/precBar.getWidth()-Save.gd.riceGprec/10;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(lzrlBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smspeed" && x*reloadLZ/lzrlBar.getWidth()>Save.gd.laserreload){
					lreload=x*(reloadLZ)/lzrlBar.getWidth()-Save.gd.laserreload;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="power"){
			//		power=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="power"){
			//		power=power;
			//	}
			}
		});
		
		page.addActor(weaponbg);
		page.addActor(titlebg);
		page.addActor(spec1bg);
		page.addActor(spec2bg);
		page.addActor(spec3bg);
		page.addActor(bar1bg);
		page.addActor(bar2bg);
		page.addActor(bar3bg);
		page.addActor(weaponIm);
		page.addActor(title);
		page.addActor(spec1Im);
		page.addActor(lzmaxBar);
		page.addActor(spec2Im);
	//	smPage.addActor(spec1Im2);
		page.addActor(lzminBar);
		page.addActor(spec3Im);
		page.addActor(spec3Im2);
		page.addActor(lzrlBar);
		page.addActor(plusbg1);
		page.addActor(plusbg2);
		page.addActor(plusbg3);
		page.addActor(plus1);
		page.addActor(plus2);
		page.addActor(plus3);
		page.addActor(upbutton);
		page.addActor(costbg);
		page.addActor(metalIm);
		page.addActor(costLZ);
		page.addActor(cancelLZ);
		page.addActor(spec1label);
		page.addActor(spec2label);
		page.addActor(spec3label);
		
		page.setWidth(screenW);
		page.setPosition(0, 0);
		
		return page;
		
	}
	
	
	private Group createShieldPage(){
		final Group page =new Group();
		Label title = new Label(CatGame.myBundle.get("shield"), skin);
		
		float width = screenW*3/4-screenW/30;
		float height= screenH*3/4-screenW/30;
		float pad=screenW/60;
		float posx = screenW/8+screenW/60+screenW/16;
		
		Image weaponbg=new Image(skin.newDrawable("white", Color.BLACK));
		Image titlebg =new Image(skin.newDrawable("white", Color.BLACK));
		Image costbg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec1bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image spec2bg =new Image(skin.newDrawable("white", Color.BLACK));
//		Image spec3bg =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar1bg  =new Image(skin.newDrawable("white", Color.BLACK));
		Image bar2bg  =new Image(skin.newDrawable("white", Color.BLACK));
//		Image bar3bg  =new Image(skin.newDrawable("white", Color.BLACK));
		
		//heading
		Image weaponIm=new Image(Assets.manager.get(Assets.shield,Texture.class));
		weaponIm.setColor(Color.BLUE);
		Image metalIm =new Image(Assets.manager.get(Assets.metal ,Texture.class));
		
		
		//Create
		Image spec1Im =new Image(Assets.manager.get(Assets.shield  ,Texture.class));
		spec1Im.setColor(Color.BLUE);
		//Image spec1Im2=new Image(Assets.manager.get(Assets.lzanim ,Texture.class));
		Image spec2Im=new Image(Assets.manager.get(Assets.reload1,Texture.class));
		Image spec2Im2=new Image(Assets.manager.get(Assets.reload2,Texture.class));
		spec2Im2.setColor(Color.GREEN);
		spec2Im2.setTouchable(Touchable.disabled);
//		Image spec3Im2=new Image(Assets.manager.get(Assets.reload2,Texture.class));
//		Image spec3Im =new Image(Assets.manager.get(Assets.reload1,Texture.class));
//		spec3Im2.setColor(Color.RED);
		
		Image plusbg1   =new Image(skin.newDrawable("white", Color.BLACK));
		Image plusbg2   =new Image(skin.newDrawable("white", Color.BLACK));
//		Image plusbg3   =new Image(skin.newDrawable("white", Color.BLACK));
		
		Image plus1    = new Image(Assets.manager.get(Assets.plus,Texture.class));
		Image plus2      = new Image(Assets.manager.get(Assets.plus,Texture.class));
//		Image plus3   = new Image(Assets.manager.get(Assets.plus,Texture.class));
		
		shieldhpBar = new ProgressBar(0, maxshieldhp, 1, false, skin, "red");
		shieldhpBar.setAnimateDuration(0.3f);
		shieldhpBar.setAnimateInterpolation(fade);
	
		shieldRLBar = new ProgressBar(0,  maxshieldRL, 1f, false, skin, "red");
		shieldRLBar.setAnimateDuration(0.3f);
		shieldRLBar.setAnimateInterpolation(fade);
		
//		zzlzrlBar = new ProgressBar(0.8f, zzreloadLZ, 0.1f, false, skin, "red");
//		zzlzrlBar.setAnimateDuration(0.3f);
//		zzlzrlBar.setAnimateInterpolation(fade);
		
		final TextButton upbutton     = new TextButton(CatGame.myBundle.get("upgrade") ,skin);
		upbutton.pad(2, 12, 2, 12);
		upbutton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MapUI.click.play();
				if(Save.gd.metal>= metalshield){
					Save.gd.metalspent+=metalshield;
					Save.gd.metal-=metalshield;
					if(CatGame.gservices.isSignedIn()){
						CatGame.gservices.incrementmetal(metalshield);
					}
					else Save.gd.metalspentgs+=metalshield;
					Save.gd.shieldhp   = shieldhpBar.getValue();
					Save.gd.shieldreload   = shieldRLBar.getValue();
		//			Save.gd.laserreload= zzlzrlBar .getValue();
					Save.save();
					shieldhp=0;
					shieldrl =0;
					zzlreload=0;
					upgraded=true;
				}
			}
		});
		cancelshield = new TextButton(CatGame.myBundle.get("cancel"), skin);
		cancelshield.pad(2, 12, 2, 12);
		cancelshield.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				if(shieldhp==0 && shieldrl==0 && zzlreload==0){
					lab.clearActions();
					lab.getColor().a = page.isVisible() ? 1 : 0;
					if (lab.isVisible()){
						lab.addAction(sequence(alpha(0, 0.5f, fade), hide()));
					}
					else{
						lab.addAction(sequence(show(), alpha(1, 0.5f, fade)));
					}
				}
				shieldhp=0;
				shieldrl =0;
				zzlreload=0;
			}
		});
			
		
		costshield =new Label("100/200", skin);
		
		//layout
		
		spec1bg.setSize(width/12, width/12);
		spec1bg.setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2bg.setSize(width/12, width/12);
		spec2bg.setPosition(posx+2*pad, screenH*4/8-width/24);
//		spec3bg.setSize(width/12, width/12);
//		spec3bg.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im .setSize(width/12, width/12);
	//	spec1Im2 .setSize(width/12, width/12);
		spec2Im.setSize(width/12, width/12);
		spec2Im2.setSize(width/12, width/12);
		spec1Im .setPosition(posx+2*pad, screenH*5/8-width/24);
		spec2Im.setPosition(posx+2*pad, screenH*4/8-width/24);
		spec2Im2.setPosition(posx+2*pad, screenH*4/8-width/24);
//		spec3Im2.setSize(width/12, width/12);
//		spec3Im.setSize(width/12, width/12);
		spec1Im .setPosition(posx+2*pad, screenH*5/8-width/24);
	//	spec1Im2.setPosition(posx+2*pad, screenH*5/8-width/24);
//		spec3Im.setPosition(posx+2*pad, screenH*3/8-width/24);
//		spec3Im2.setPosition(posx+2*pad, screenH*3/8-width/24);
		spec1Im.setOrigin(width/24, width/24);
//		spec3Im2.setOrigin(Align.center);
//		spec3Im2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
		
		spec1Im.addAction(forever(sequence(alpha(1, 0.8f,sine), alpha(0.5f,0.8f,sine))));
		spec2Im2.setOrigin(Align.center);
		spec2Im2.addAction(forever(sequence(rotateTo(0),rotateTo(360,MathUtils.random(3, 6)))));
		
		final Label spec1label = new Label(CatGame.myBundle.get("shieldhp"), skin);
		spec1label.setTouchable(Touchable.disabled);
		spec1Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
		final Label spec2label = new Label(CatGame.myBundle.get("shieldload"), skin);
		spec2label.setTouchable(Touchable.disabled);
		spec2Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});
//		final Label spec3label = new Label(CatGame.myBundle.get("laserload"), skin);
//		spec3label.setTouchable(Touchable.disabled);
/*		spec3Im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				
			}
		});*/
		
		
		
		weaponbg.setPosition(posx+4*pad+width/12, screenH*6/8-width/24);
		weaponbg.setSize(width/5, height-(screenH*5/8-width/12)-2*pad);
		weaponIm.setSize(width/6, height/8);
		weaponIm.setPosition(weaponbg.getX()+weaponbg.getWidth()/2-weaponIm.getWidth()/2, weaponbg.getY()+weaponbg.getHeight()/2-weaponIm.getHeight()/2);
		weaponIm.addAction(forever(sequence(sizeTo(width/6*0.85f,height/8*0.85f,0.5f,sine),sizeTo(width/6*1.07f, height/8*1.07f,0.5f,sine))));
		
		titlebg.setPosition(posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*6/8-width/24);
		titlebg.setSize(title.getWidth()+2*pad, height-(screenH*5/8-width/12)-2*pad);
		title.setPosition(titlebg.getX()+titlebg.getWidth()/2-title.getWidth()/2, titlebg.getY()+titlebg.getHeight()/2-title.getHeight()/2);
		
		bar1bg.setPosition(posx+4*pad+width/12, screenH*5/8-width/24);
		bar1bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		shieldhpBar .setSize(width-(posx+2*pad+width/6), 30*scale);
		shieldhpBar .setPosition(posx+6*pad+width/12, bar1bg.getY()+bar1bg.getHeight()/2-15*scale);
		
		bar2bg.setPosition(posx+4*pad+width/12, screenH*4/8-width/24);
		bar2bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
		shieldRLBar.setSize(width-(posx+2*pad+width/6), 30*scale);
		shieldRLBar.setPosition(posx+6*pad+width/12, bar2bg.getY()+bar2bg.getHeight()/2-15*scale);
		
//		bar3bg.setPosition(posx+4*pad+width/12, screenH*3/8-width/24);
//		bar3bg.setSize(width-(posx+2*pad+width/6)+4*pad, width/12);
//		zzlzrlBar.setSize(width-(posx+2*pad+width/6), 30*scale);
//		zzlzrlBar.setPosition(posx+6*pad+width/12, bar3bg.getY()+bar3bg.getHeight()/2-15*scale);
		
		spec1label.setPosition(shieldhpBar.getX()+shieldhpBar.getWidth()/2-spec1label.getWidth()/2, shieldhpBar.getY()+shieldhpBar.getHeight()/1.75f-spec1label.getHeight()/2);
		spec1label.setAlignment(Align.center);
		spec1label.getColor().a=0;
		spec2label.setPosition(shieldRLBar.getX()+shieldRLBar.getWidth()/2-spec2label.getWidth()/2, shieldRLBar.getY()+shieldRLBar.getHeight()/1.75f-spec2label.getHeight()/2);
		spec2label.setAlignment(Align.center);
		spec2label.getColor().a=0;
//		spec3label.setPosition(zzlzrlBar.getX()+zzlzrlBar.getWidth()/2-spec3label.getWidth()/2, zzlzrlBar.getY()+zzlzrlBar.getHeight()/1.75f-spec3label.getHeight()/2);
//		spec3label.setAlignment(Align.center);
//		spec3label.getColor().a=0;
		
		plusbg1.setPosition(posx+width-2*pad-width/12, screenH*5/8-width/24);
		plusbg1.setSize(width/12, width/12);
		plus1.setPosition(posx+width-2*pad-width/24-width/36, screenH*5/8-width/36);
		plus1.setSize(width/18, width/18);
		plus1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec1label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(shieldhp<=maxshieldhp)shieldhp=shieldhp+1f;
				shieldhpBar.setValue(shieldhp);
				
			}
		});
		
		plusbg2.setPosition(posx+width-2*pad-width/12, screenH*4/8-width/24);
		plusbg2.setSize(width/12, width/12);
		plus2.setPosition(posx+width-2*pad-width/24-width/36, screenH*4/8-width/36);
		plus2.setSize(width/18, width/18);
		plus2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec2label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(shieldrl<= maxshieldRL)shieldrl=shieldrl+1;
				shieldRLBar.setValue(shieldrl);
				
			}
		});
		
		/*plusbg3.setPosition(posx+width-2*pad-width/12, screenH*3/8-width/24);
		plusbg3.setSize(width/12, width/12);
		plus3.setPosition(posx+width-2*pad-width/24-width/36, screenH*3/8-width/36);
		plus3.setSize(width/18, width/18);
		plus3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Save.gd.soundEnabled)MapUI.click.play();
				spec3label.addAction(sequence(alpha(0.7f,0.2f), delay(1),alpha(0,0.2f)));
				if(zzlreload<=zzreloadLZ)zzlreload=zzlreload+0.1f;
				//zzlzrlBar.setValue(zzlreload);
				
			}
		});*/
		
		upbutton.setSize(140*scale, width/12);
		upbutton.setPosition(posx+4*pad+width/12, screenH*2/8-upbutton.getHeight()/2);
		
		
		costbg.setPosition  (posx+4*pad+width/12+width/5+ screenH*1/8-width/12, screenH*2/8-upbutton.getHeight()/2);
		costbg.setSize(costRG.getWidth()+2*pad+costRG.getHeight()+pad/2, costRG.getHeight()+pad);
		
		metalIm.setPosition(costbg.getX()+2*pad+costRG.getWidth(), costbg.getY()+pad/2);
		metalIm.setSize(costRG.getHeight(), costRG.getHeight());
		costshield.setPosition(costbg.getX()+3*pad/2, costbg.getY()+pad/2);
		
		cancelshield.setSize(scale*140,width/12);
		cancelshield.setPosition(posx+screenW/14+4*pad+width/12+width-(posx+2*pad+width/12)+4*pad-cancelshield.getWidth(), screenH*2/8-cancelshield.getHeight()/2);
	
		
		
		//DnD
		dragAndDrop.setDragActorPosition(-(spec1Im.getWidth()/2), spec1Im.getHeight());

		//source
		
	//	spec1Im2.setTouchable(Touchable.disabled);
		dragAndDrop.addSource(new Source(shieldhpBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("nbr");
				payload.setDragActor(new Label(CatGame.myBundle.get("shieldhp"),skin));
				return payload;
			}
		});
		
//		spec3Im2.setTouchable(Touchable.disabled);
		dragAndDrop.addSource(new Source(shieldRLBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smfr");
				payload.setDragActor(new Label(CatGame.myBundle.get("shieldload"),skin));
				return payload;
			}
			
		});
	
/*		dragAndDrop.addSource(new Source(zzlzrlBar) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("smspeed");
				payload.setDragActor(new Label(CatGame.myBundle.get("laserload"),skin));
				return payload;
			}
		});*/
		
		//Target
		
		dragAndDrop.addTarget(new Target(shieldhpBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="nbr" && x*maxshieldhp/shieldhpBar.getWidth()>Save.gd.shieldhp){
					shieldhp=x*maxshieldhp/shieldhpBar.getWidth()-Save.gd.shieldhp;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="speed"){
			//		speed=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="speed"){
			//		speed=x*maxspeed/speedBar.getWidth()-Save.gd.riceGspeed;
			//	}
			}
		});
		
		dragAndDrop.addTarget(new Target(shieldRLBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smfr"&& x* maxshieldRL/shieldRLBar.getWidth()>Save.gd.shieldreload){
					shieldrl=x* maxshieldRL/shieldRLBar.getWidth()-Save.gd.shieldreload;
				}
				
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="precision"){
			//		prec=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="precision"){
			//		prec=x*maxprec/10/precBar.getWidth()-Save.gd.riceGprec/10;
			//	}
			}
		});
		
		/*dragAndDrop.addTarget(new Target(zzlzrlBar) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				if(payload.getObject()=="smspeed" && x*zzreloadLZ/zzlzrlBar.getWidth()>Save.gd.laserreload){
					zzlreload=x*(zzreloadLZ)/zzlzrlBar.getWidth()-Save.gd.laserreload;
				}
				return true;
			}

			public void reset (Source source, Payload payload) {
			//	if(payload.getObject()=="power"){
			//		power=0;
			//	}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			//	if(payload.getObject()=="power"){
			//		power=power;
			//	}
			}
		});*/
		
		page.addActor(weaponbg);
		page.addActor(titlebg);
		page.addActor(spec1bg);
		page.addActor(spec2bg);
//		page.addActor(spec3bg);
		page.addActor(bar1bg);
		page.addActor(bar2bg);
//		page.addActor(bar3bg);
		page.addActor(weaponIm);
		page.addActor(title);
		page.addActor(spec1Im);
		page.addActor(shieldhpBar);
		page.addActor(spec2Im);
		page.addActor(spec2Im2);
	//	smPage.addActor(spec1Im2);
		page.addActor(shieldRLBar);
//		page.addActor(spec3Im);
//		page.addActor(spec3Im2);
//		page.addActor(zzlzrlBar);
		page.addActor(plusbg1);
		page.addActor(plusbg2);
//		page.addActor(plusbg3);
		page.addActor(plus1);
		page.addActor(plus2);
//		page.addActor(plus3);
		page.addActor(upbutton);
		page.addActor(costbg);
		page.addActor(metalIm);
		page.addActor(costshield);
		page.addActor(cancelshield);
		page.addActor(spec1label);
		page.addActor(spec2label);
//		page.addActor(spec3label);
		
		page.setWidth(screenW);
		page.setPosition(0, 0);
		
		return page;
		
	}
	
	
	public void render(){
		
		///////TECHNO
		//////////////////////////////////////////////////////////////////SHIP
		if(Save.gd.hp  !=hpBar.getValue())
			hpmetal  = hpBar.getValue()*hpBar.getValue();
		else hpmetal =0;
		
		if(Save.gd.speed  !=speedBar.getValue())
			speedmetal    = speedBar.getValue()  *speedBar.getValue()*4;
		else speedmetal   =0;
		
		if(Save.gd.acceleration!=accBar.getValue())
			accmetal = accBar .getValue()  *accBar .getValue()/4;
		else accmetal=0;
		
		hpBar   .setValue(Save.gd.hp          +hp);
		speedBar.setValue(Save.gd.speed       +speed);
		accBar  .setValue(Save.gd.acceleration+acc);
		
		metalship=(int) (hpmetal+speedmetal+accmetal);
		costShip.setAlignment(Align.right);
		if(metalship>Save.gd.metal) costShip.setColor(1, 0, 0, 1);
		else costShip.setColor(1, 1, 1, 1);
		costShip.setText(Integer.toString(metalship)+"/" +Integer.toString(Save.gd.metal));
		
		if(metalship!=0)
			cancelship.setText(CatGame.myBundle.get("cancel"));
		else
			cancelship.setText(CatGame.myBundle.get("quit"));
		
		//////////////////////////////////////////////////////////////RICE GUN
		if(Save.gd.riceGpower/10  !=rgpowerBar.getValue())
			rpowmetal  = rgpowerBar.getValue()*rgpowerBar.getValue();
		else rpowmetal =0;
		
		if(Save.gd.riceGspeed  !=rgFRBar.getValue())
			rsmetal    = rgFRBar.getValue()  *rgFRBar.getValue();
		else rsmetal   =0;
		
		if(Save.gd.riceGprec/10!=rgprecBar.getValue())
			rprecmetal = rgprecBar .getValue()  *rgprecBar .getValue();
		else rprecmetal=0;
		
		rgprecBar .setValue(Save.gd.riceGprec/10+rPrec);
		rgFRBar   .setValue(Save.gd.riceGspeed+rFR);
		rgpowerBar.setValue(Save.gd.riceGpower/10+rPower);
		
		metalRG=(int) (rpowmetal+ rsmetal +rprecmetal);
		costRG.setAlignment(Align.right);
		if(metalRG>Save.gd.metal) costRG.setColor(1, 0, 0, 1);
		else costRG.setColor(1, 1, 1, 1);
		costRG.setText(Integer.toString(metalRG)+"/" +Integer.toString(Save.gd.metal));
		
		if(metalRG!=0)
			cancelRG.setText(CatGame.myBundle.get("cancel"));
		else
			cancelRG.setText(CatGame.myBundle.get("quit"));
		
		///////////////////////////////////////////////////////////////SUSHI MISSIL
		if (Save.gd.sushiGnbr != smNbrBar.getValue())
			snbrMetal = smNbrBar.getValue() * smNbrBar.getValue() / 16;
		else
			snbrMetal = 0;
		if (Save.gd.sushiGfr * 10 != smFRBar.getValue())
			sRrMetal = (smFRBar.getValue()) * (smFRBar.getValue())/16;
		else
			sRrMetal = 0;
		if (Save.gd.sushiGspeed != smspeedBar.getValue())
			sSpeedMetal = (smspeedBar.getValue()) * (smspeedBar.getValue());
		else
			sSpeedMetal = 0;
		
		smNbrBar  .setValue(Save.gd.sushiGnbr     + snbr  );
		smFRBar   .setValue(Save.gd.sushiGfr * 10 + sFR   );
		smspeedBar.setValue(Save.gd.sushiGspeed   + sSpeed);
		
		//cost label
		metalSM = (int) (snbrMetal + sRrMetal + sSpeedMetal);
		costSM.setAlignment(Align.right);
		if (metalSM > Save.gd.metal)
			costSM.setColor(1, 0, 0, 1);
		else
			costSM.setColor(1, 1, 1, 1);
		costSM.setText(Integer.toString(metalSM) + "/"
				+ Integer.toString(Save.gd.metal));
		
		//exit button
		if(metalSM!=0)
			cancelSM.setText(CatGame.myBundle.get("cancel"));
		else
			cancelSM.setText(CatGame.myBundle.get("quit"));
		
	
	
		///////////////////////////////////////////////////////////////////  LASER
		if (Save.gd.lasermax != lzmaxBar.getValue())
			lzmaxMetal = lzmaxBar.getValue() * lzmaxBar.getValue() *400;
		else
			lzmaxMetal = 0;
		if (Save.gd.lasermin != lzminBar.getValue())
			lzminMetal = (lzminBar.getValue()-0.0099f) * (lzminBar.getValue()-0.0099f) *10000;
		else
			lzminMetal = 0;
		if (Save.gd.laserreload != lzrlBar.getValue())
			lzrlMetal = (lzrlBar.getValue()) * (lzrlBar.getValue())*400/3.2375f;
		else
			lzrlMetal = 0;
		
		lzmaxBar .setValue(Save.gd.lasermax      + lzmax  );
		lzminBar .setValue(Save.gd.lasermin      + lzmin   );
		lzrlBar  .setValue(Save.gd.laserreload   + lreload);
		
		metalLZ = (int) (lzmaxMetal + lzminMetal + lzrlMetal);
		costLZ.setAlignment(Align.right);
		if (metalLZ > Save.gd.metal)
			costLZ.setColor(1, 0, 0, 1);
		else
			costLZ.setColor(1, 1, 1, 1);
		costLZ.setText(Integer.toString(metalLZ) + "/"
				+ Integer.toString(Save.gd.metal));
		
		if(metalLZ!=0)
			cancelLZ.setText(CatGame.myBundle.get("cancel"));
		else
			cancelLZ.setText(CatGame.myBundle.get("quit"));
	
		
		
		/////////////////////////SHIELD////////////////////////////////
		if (Save.gd.shieldhp != shieldhpBar.getValue())
			shieldhpMetal = shieldhpBar.getValue() * shieldhpBar.getValue();
		else
			shieldhpMetal = 0;
		if (Save.gd.shieldreload != shieldRLBar.getValue())
			shieldrlMetal = (shieldRLBar.getValue()) * (shieldRLBar.getValue())/2;
		else
			 shieldrlMetal = 0;
		/*if (Save.gd.laserreload != zzlzrlBar.getValue())
			zzlzrlMetal = (zzlzrlBar.getValue()-0.8f) * (zzlzrlBar.getValue()-0.8f)*100;
		else
			zzlzrlMetal = 0;
		zzlzrlBar  .setValue(Save.gd.laserreload    + lreload);*/
		
		shieldhpBar .setValue(Save.gd.shieldhp      + shieldhp  );
		shieldRLBar .setValue(Save.gd.shieldreload  + shieldrl  );
	
		
		metalshield = (int) (shieldhpMetal +  shieldrlMetal);// + zzlzrlMetal);
		costshield.setAlignment(Align.right);
		if (metalshield > Save.gd.metal)
			costshield.setColor(1, 0, 0, 1);
		else
			costshield.setColor(1, 1, 1, 1);
		costshield.setText(Integer.toString(metalshield) + "/"
				+ Integer.toString(Save.gd.metal));
		
		if(metalshield!=0)
			cancelshield.setText(CatGame.myBundle.get("cancel"));
		else
			cancelshield.setText(CatGame.myBundle.get("quit"));
		
	}
}
