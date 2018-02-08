package quentin.jeu.cat.ui;

import static com.badlogic.gdx.math.Interpolation.fade;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.hide;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.show;
import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Scenario {

	public static Group dialog;
	private static float pad=5;
	
	public static Group createdialog(int event, Skin skin) {
		
		dialog=new Group();
		Image diaIm1=new Image(skin.newDrawable("white", Color.BLACK));
		diaIm1.getColor().a=0.9f;
		diaIm1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/5);
		diaIm1.setPosition(0, 0);
		Image diaIm2=new Image(skin.newDrawable("white",new Color(0x416ba1ff)));
		diaIm2.getColor().a=0.2f;
		diaIm2.setSize(Gdx.graphics.getWidth()-4*pad, Gdx.graphics.getHeight()/5-4*pad);
		diaIm2.setPosition(2*pad, 2*pad);
		dialog.addActor(diaIm1);
		dialog.addActor(diaIm2);
		dialog.getColor().a=0;
		
		if(event==0){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak0"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==1){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak5"), skin);
		//	nanak1.setColor(Color.BLACK);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			Label nanak2 =new Label(CatGame.myBundle.get("nanak6"), skin);
		//	nanak2.setColor(Color.BLACK);
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			Label nanak3 =new Label(CatGame.myBundle.get("nanak7"), skin);
		//	nanak3.setColor(Color.BLACK);
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			Label nanak4 =new Label(CatGame.myBundle.get("nanak8"), skin);
			//nanak4.setColor(Color.BLACK);
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			Label nanak5 =new Label(CatGame.myBundle.get("nanak9"), skin);
		//	nanak5.setColor(Color.BLACK);
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			
			dialog.addAction  (sequence(delay(3),show(),   alpha(1, 0.5f, fade), delay(13), alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(3),          alpha(1, 0.5f, fade), delay(14), alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(3),          alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(6),          alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(9),          alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(12),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			nanak5.addAction  (sequence(delay(15),         alpha(1, 0.5f, fade), delay(2),alpha(0, 0.5f, fade)));
			
		}
		if(event==2){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak10"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak11"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak12"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		if(event==3){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak13"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak14"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak15"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak16"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak17"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak18"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		if(event==4){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak19"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak20"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak21"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak22"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==5){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak23"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak24"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak25"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak26"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("kuro11"), skin);
			Label kuro2 =new Label(CatGame.myBundle.get("kuro12"), skin);
			Label kuro3 =new Label(CatGame.myBundle.get("kuro13"), skin);
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			kuro2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro2);
			kuro2.getColor().a=0;
			kuro3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro3);
			kuro3.getColor().a=0;
			
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(21.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro.addAction    (sequence(delay(7),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			kuro1.addAction   (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro2.addAction   (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak .addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro.addAction    (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			kuro3.addAction   (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==6){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak27"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak28"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak29"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak30"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak31"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak32"), skin);
			Label nanak7 =new Label(CatGame.myBundle.get("nanak33"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			nanak7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak7);
			nanak7.getColor().a=0;
			
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("kuro14"), skin);
			Label kuro2 =new Label(CatGame.myBundle.get("kuro15"), skin);
			Label kuro3 =new Label(CatGame.myBundle.get("kuro16"), skin);
			Label kuro4 =new Label(CatGame.myBundle.get("kuro17"), skin);
			Label kuro5 =new Label(CatGame.myBundle.get("kuro18"), skin);
			Label kuro6 =new Label(CatGame.myBundle.get("kuro19"), skin);
			Label kuro7 =new Label(CatGame.myBundle.get("kuro20"), skin);
			Label kuro8 =new Label(CatGame.myBundle.get("kuro21"), skin);
			Label kuro9 =new Label(CatGame.myBundle.get("kuro22"), skin);
			Label kuro10=new Label(CatGame.myBundle.get("kuro23"), skin);
			Label kuro11=new Label(CatGame.myBundle.get("kuro24"), skin);
			Label kuro12=new Label(CatGame.myBundle.get("kuro25"), skin);
			Label kuro13=new Label(CatGame.myBundle.get("kuro26"), skin);
			
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			kuro2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro2);
			kuro2.getColor().a=0;
			kuro3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro3);
			kuro3.getColor().a=0;
			kuro4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro4);
			kuro4.getColor().a=0;
			kuro5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro5);
			kuro5.getColor().a=0;
			kuro6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro6);
			kuro6.getColor().a=0;
			kuro7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro7);
			kuro7.getColor().a=0;
			kuro8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro8);
			kuro8.getColor().a=0;
			kuro9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro9);
			kuro9.getColor().a=0;
			kuro10.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro10);
			kuro10.getColor().a=0;
			kuro11.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro11);
			kuro11.getColor().a=0;
			kuro12.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro12);
			kuro12.getColor().a=0;
			kuro13.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro13);
			kuro13.getColor().a=0;
			
			Image catious= new Image(Assets.manager.get(Assets.catious, Texture.class));
			catious.setPosition(2*pad, 2*pad);
			catious.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(catious);
			catious.getColor().a=0;
			Label catious1 =new Label(CatGame.myBundle.get("catious1"), skin);
			Label catious2 =new Label(CatGame.myBundle.get("catious2"), skin);
			Label catious3 =new Label(CatGame.myBundle.get("catious3"), skin);
			Label catious4 =new Label(CatGame.myBundle.get("catious4"), skin);
			Label catious5 =new Label(CatGame.myBundle.get("catious5"), skin);
			Label catious6 =new Label(CatGame.myBundle.get("catious6"), skin);
			
			catious1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious1);
			catious1.getColor().a=0;
			catious2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious2);
			catious2.getColor().a=0;
			catious3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious3);
			catious3.getColor().a=0;
			catious4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious4);
			catious4.getColor().a=0;
			catious5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious5);
			catious5.getColor().a=0;
			catious6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious6);
			catious6.getColor().a=0;
			
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(77f) , alpha(0, 0.5f, fade),hide()   ));
			catious .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			catious1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(7),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction   (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction   (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious .addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			catious3.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro.addAction    (sequence(delay(16),         alpha(1, 0.5f, fade), delay(35f) , alpha(0, 0.5f, fade)));
			kuro1.addAction   (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro2.addAction   (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro3.addAction   (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro4.addAction   (sequence(delay(25),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro5.addAction   (sequence(delay(28),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro6.addAction   (sequence(delay(31),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro7.addAction   (sequence(delay(34),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro8.addAction   (sequence(delay(37),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro9.addAction   (sequence(delay(40),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro10.addAction   (sequence(delay(43),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro11.addAction   (sequence(delay(46),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro12.addAction   (sequence(delay(49),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious .addAction  (sequence(delay(52),         alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			catious4.addAction  (sequence(delay(52),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious5.addAction  (sequence(delay(55),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro.addAction     (sequence(delay(58),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			kuro13.addAction   (sequence(delay(58),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(61),          alpha(1, 0.5f, fade), delay(14f) , alpha(0, 0.5f, fade)));
			nanak3.addAction   (sequence(delay(61),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction   (sequence(delay(64),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction   (sequence(delay(67),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction   (sequence(delay(70),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak7.addAction   (sequence(delay(73),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious .addAction  (sequence(delay(76),         alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			catious6.addAction  (sequence(delay(76),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==7){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak34"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak35"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak36"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==8){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak37"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak38"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak39"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("kuro27"), skin);
			Label kuro2 =new Label(CatGame.myBundle.get("kuro28"), skin);
			Label kuro3 =new Label(CatGame.myBundle.get("kuro29"), skin);
			Label kuro4 =new Label(CatGame.myBundle.get("kuro30"), skin);
			Label kuro5 =new Label(CatGame.myBundle.get("kuro31"), skin);
			Label kuro6 =new Label(CatGame.myBundle.get("kuro32"), skin);
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			kuro2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro2);
			kuro2.getColor().a=0;
			kuro3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro3);
			kuro3.getColor().a=0;
			kuro4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro4);
			kuro4.getColor().a=0;
			kuro5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro5);
			kuro5.getColor().a=0;
			kuro6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro6);
			kuro6.getColor().a=0;
			
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(27.5f) , alpha(0, 0.5f, fade),hide()   ));
			kuro .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			kuro1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro .addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			kuro3.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro4.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro .addAction  (sequence(delay(22),         alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			kuro5.addAction  (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro6.addAction  (sequence(delay(25),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
		}
		
		if(event==9){
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("kuro33"), skin);
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade),hide()   ));
			kuro .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			kuro1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==10){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak40"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak41"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak42"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak43"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==11){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak44"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak45"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			Image catious= new Image(Assets.manager.get(Assets.catious, Texture.class));
			catious.setPosition(2*pad, 2*pad);
			catious.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(catious);
			catious.getColor().a=0;
			Label catious1 =new Label(CatGame.myBundle.get("catious7"), skin);
			Label catious2 =new Label(CatGame.myBundle.get("catious8"), skin);
			Label catious3 =new Label(CatGame.myBundle.get("catious9"), skin);
			Label catious4 =new Label(CatGame.myBundle.get("catious10"), skin);
			Label catious5 =new Label(CatGame.myBundle.get("catious11"), skin);
			Label catious6 =new Label(CatGame.myBundle.get("catious12"), skin);
			catious1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious1);
			catious1.getColor().a=0;
			catious2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious2);
			catious2.getColor().a=0;
			catious3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious3);
			catious3.getColor().a=0;
			catious4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious4);
			catious4.getColor().a=0;
			catious5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious5);
			catious5.getColor().a=0;
			catious6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious6);
			catious6.getColor().a=0;
			
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(25.5f) , alpha(0, 0.5f, fade),hide()   ));
			catious .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(11f)  , alpha(0, 0.5f, fade)));
			catious1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(13),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak1.addAction (sequence(delay(13),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious .addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			catious5.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			catious .addAction  (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			catious6.addAction  (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
		}
		
		if(event==12){
			Image catious= new Image(Assets.manager.get(Assets.catious, Texture.class));
			catious.setPosition(2*pad, 2*pad);
			catious.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(catious);
			catious.getColor().a=0;
			Label catious1 =new Label(CatGame.myBundle.get("catious13"), skin);
			catious1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-catious1.getHeight()/2);
			dialog.addActor(catious1);
			catious1.getColor().a=0;
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(2.5f) , alpha(0, 0.5f, fade),hide()   ));
			catious .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			catious1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==13){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak46"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak47"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak48"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak49"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==14){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak50"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak51"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak52"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak53"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==15){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak54"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak55"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak56"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak57"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak58"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak59"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==16){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak60"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak61"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak62"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak63"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak64"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak65"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(18.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction  (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==17){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak66"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak67"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak68"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak69"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak70"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin1"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin2"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin3"), skin);
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(24.5f) , alpha(0, 0.5f, fade),hide()));
			dolphin .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(5f)  , alpha(0, 0.5f, fade)));
			dolphin1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(11f) , alpha(0, 0.5f, fade)));
			nanak1.addAction (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin .addAction  (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			dolphin3.addAction  (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==18){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak71"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak72"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak73"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak74"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak75"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(16.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(16.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction  (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==19){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak76"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak77"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak78"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(10.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(10.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==20){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak79"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak80"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==21){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak81"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(2.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==22){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak82"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak83"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak84"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak85"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(12.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction  (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==23){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak86"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak87"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak88"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak89"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak90"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak91"), skin);
			Label nanak7 =new Label(CatGame.myBundle.get("nanak92"), skin);
			Label nanak8 =new Label(CatGame.myBundle.get("nanak93"), skin);
			Label nanak9 =new Label(CatGame.myBundle.get("nanak94"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			nanak7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak7);
			nanak7.getColor().a=0;
			nanak8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak8);
			nanak8.getColor().a=0;
			nanak9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak9);
			nanak9.getColor().a=0;
			
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin4"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin5"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin6"), skin);
			Label dolphin4 =new Label(CatGame.myBundle.get("dolphin7"), skin);
			Label dolphin5 =new Label(CatGame.myBundle.get("dolphin8"), skin);
			Label dolphin6 =new Label(CatGame.myBundle.get("dolphin9"), skin);
			Label dolphin7 =new Label(CatGame.myBundle.get("dolphin10"), skin);
			Label dolphin8 =new Label(CatGame.myBundle.get("dolphin11"), skin);
			Label dolphin9 =new Label(CatGame.myBundle.get("dolphin12"), skin);
			Label dolphin10=new Label(CatGame.myBundle.get("dolphin13"), skin);
			Label dolphin11=new Label(CatGame.myBundle.get("dolphin14"), skin);
			Label dolphin12=new Label(CatGame.myBundle.get("dolphin15"), skin);
			Label dolphin13=new Label(CatGame.myBundle.get("dolphin16"), skin);
			Label dolphin14=new Label(CatGame.myBundle.get("dolphin17"), skin);
			Label dolphin15=new Label(CatGame.myBundle.get("dolphin18"), skin);
			Label dolphin16=new Label(CatGame.myBundle.get("dolphin19"), skin);
			Label dolphin17=new Label(CatGame.myBundle.get("dolphin20"), skin);
			Label dolphin18=new Label(CatGame.myBundle.get("dolphin21"), skin);
			Label dolphin19=new Label(CatGame.myBundle.get("dolphin22"), skin);
			Label dolphin20=new Label(CatGame.myBundle.get("dolphin23"), skin);
			Label dolphin21=new Label(CatGame.myBundle.get("dolphin24"), skin);
			Label dolphin22=new Label(CatGame.myBundle.get("dolphin25"), skin);
			Label dolphin23=new Label(CatGame.myBundle.get("dolphin26"), skin);
			Label dolphin24=new Label(CatGame.myBundle.get("dolphin27"), skin);
			Label dolphin25=new Label(CatGame.myBundle.get("dolphin28"), skin);
			Label dolphin26=new Label(CatGame.myBundle.get("dolphin29"), skin);
			
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			dolphin4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin4);
			dolphin4.getColor().a=0;
			dolphin5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin5);
			dolphin5.getColor().a=0;
			dolphin6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin6);
			dolphin6.getColor().a=0;
			dolphin7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin7);
			dolphin7.getColor().a=0;
			dolphin8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin8);
			dolphin8.getColor().a=0;
			dolphin9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin9);
			dolphin9.getColor().a=0;
			dolphin10.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin10);
			dolphin10.getColor().a=0;
			dolphin11.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin11);
			dolphin11.getColor().a=0;
			dolphin12.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin12);
			dolphin12.getColor().a=0;
			dolphin13.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin13);
			dolphin13.getColor().a=0;
			
			dolphin14.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin14);
			dolphin14.getColor().a=0;
			dolphin15.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin15);
			dolphin15.getColor().a=0;
			dolphin16.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin16);
			dolphin16.getColor().a=0;
			dolphin17.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin17);
			dolphin17.getColor().a=0;
			dolphin18.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin18);
			dolphin18.getColor().a=0;
			dolphin19.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin19);
			dolphin19.getColor().a=0;
			dolphin20.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin20);
			dolphin20.getColor().a=0;
			dolphin21.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin21);
			dolphin21.getColor().a=0;
			dolphin22.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin22);
			dolphin22.getColor().a=0;
			dolphin23.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin23);
			dolphin23.getColor().a=0;
			dolphin24.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin24);
			dolphin24.getColor().a=0;
			dolphin25.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin25);
			dolphin25.getColor().a=0;
			dolphin26.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin26);
			dolphin26.getColor().a=0;

			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(106f) , alpha(0, 0.5f, fade),hide()   ));
			nanak.addAction    (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak1.addAction   (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(4),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin1.addAction   (sequence(delay(4),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak2.addAction   (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(10),         alpha(1, 0.5f, fade), delay(11f) , alpha(0, 0.5f, fade)));
			dolphin2.addAction   (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin3.addAction   (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin4.addAction   (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin5.addAction   (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(22),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak3.addAction   (sequence(delay(22),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction   (sequence(delay(25),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(28),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin6.addAction    (sequence(delay(28),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(31),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak5.addAction   (sequence(delay(31),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction   (sequence(delay(34),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(37),         alpha(1, 0.5f, fade), delay(8f) , alpha(0, 0.5f, fade)));
			dolphin7.addAction    (sequence(delay(37),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin8.addAction    (sequence(delay(40),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin9.addAction    (sequence(delay(43),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(46),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak7.addAction   (sequence(delay(46),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(49),         alpha(1, 0.5f, fade), delay(44f) , alpha(0, 0.5f, fade)));
			dolphin10.addAction   (sequence(delay(49),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin11.addAction   (sequence(delay(52),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin12.addAction   (sequence(delay(55),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin13.addAction   (sequence(delay(58),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin14.addAction   (sequence(delay(61),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin15.addAction   (sequence(delay(64),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin16.addAction   (sequence(delay(67),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin17.addAction   (sequence(delay(70),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin18.addAction   (sequence(delay(73),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin19.addAction   (sequence(delay(76),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin20.addAction   (sequence(delay(79),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin21.addAction   (sequence(delay(82),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin22.addAction   (sequence(delay(85),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin23.addAction   (sequence(delay(88),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin24.addAction   (sequence(delay(91),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(94),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak8.addAction   (sequence(delay(94),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak9.addAction   (sequence(delay(97),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(100),        alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			dolphin25.addAction   (sequence(delay(100),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin26.addAction   (sequence(delay(103),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==24){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak95"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak96"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==25){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak97"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak98"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==26){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak99"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak100"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak101"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak102"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak103"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin30"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin31"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin32"), skin);
			Label dolphin4 =new Label(CatGame.myBundle.get("dolphin33"), skin);
			Label dolphin5 =new Label(CatGame.myBundle.get("dolphin34"), skin);
			Label dolphin6 =new Label(CatGame.myBundle.get("dolphin35"), skin);
			Label dolphin7 =new Label(CatGame.myBundle.get("dolphin36"), skin);
			Label dolphin8 =new Label(CatGame.myBundle.get("dolphin37"), skin);
			Label dolphin9 =new Label(CatGame.myBundle.get("dolphin38"), skin);
			Label dolphin10=new Label(CatGame.myBundle.get("dolphin39"), skin);
			Label dolphin11=new Label(CatGame.myBundle.get("dolphin40"), skin);
			Label dolphin12=new Label(CatGame.myBundle.get("dolphin41"), skin);
			Label dolphin13=new Label(CatGame.myBundle.get("dolphin42"), skin);
			Label dolphin14=new Label(CatGame.myBundle.get("dolphin43"), skin);
			Label dolphin15=new Label(CatGame.myBundle.get("dolphin44"), skin);
			
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			dolphin4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin4);
			dolphin4.getColor().a=0;
			dolphin5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin5);
			dolphin5.getColor().a=0;
			dolphin6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin6);
			dolphin6.getColor().a=0;
			dolphin7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin7);
			dolphin7.getColor().a=0;
			dolphin8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin8);
			dolphin8.getColor().a=0;
			dolphin9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin9);
			dolphin9.getColor().a=0;
			dolphin10.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin10);
			dolphin10.getColor().a=0;
			dolphin11.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin11);
			dolphin11.getColor().a=0;
			dolphin12.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin12);
			dolphin12.getColor().a=0;
			dolphin13.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin13);
			dolphin13.getColor().a=0;
			
			dolphin14.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin14);
			dolphin14.getColor().a=0;
			dolphin15.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin15);
			dolphin15.getColor().a=0;
			
			dialog.addAction  (sequence(delay(6), delay(1),show(),   alpha(1, 0.5f, fade), delay(60.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak.addAction    (sequence(delay(6),delay(1),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction   (sequence(delay(6),delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction   (sequence(delay(6),delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction  (sequence(delay(6),delay(7),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin1.addAction (sequence(delay(6),delay(7),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(6),delay(10),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak3.addAction   (sequence(delay(6),delay(10),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(6),delay(13),         alpha(1, 0.5f, fade), delay(11f) , alpha(0, 0.5f, fade)));
			dolphin2.addAction   (sequence(delay(6),delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin3.addAction   (sequence(delay(6),delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin4.addAction   (sequence(delay(6),delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin5.addAction   (sequence(delay(6),delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(6),delay(25),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak4.addAction   (sequence(delay(6),delay(25),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak5.addAction   (sequence(delay(6),delay(28),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(6),delay(31),         alpha(1, 0.5f, fade), delay(29f) , alpha(0, 0.5f, fade)));
			dolphin6.addAction    (sequence(delay(6),delay(31),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin7.addAction    (sequence(delay(6),delay(34),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin8.addAction    (sequence(delay(6),delay(37),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin9.addAction    (sequence(delay(6),delay(40),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin10.addAction   (sequence(delay(6),delay(43),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin11.addAction   (sequence(delay(6),delay(46),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin12.addAction   (sequence(delay(6),delay(49),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin13.addAction   (sequence(delay(6),delay(52),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin14.addAction   (sequence(delay(6),delay(55),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin15.addAction   (sequence(delay(6),delay(58),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==27){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak104"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak105"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak106"), skin);
			Label nanak4 =new Label(CatGame.myBundle.get("nanak107"), skin);
			Label nanak5 =new Label(CatGame.myBundle.get("nanak108"), skin);
			Label nanak6 =new Label(CatGame.myBundle.get("nanak109"), skin);
			Label nanak7 =new Label(CatGame.myBundle.get("nanak110"), skin);
			Label nanak8 =new Label(CatGame.myBundle.get("nanak111"), skin);
			Label nanak9 =new Label(CatGame.myBundle.get("nanak112"), skin);
			Label nanak10 =new Label(CatGame.myBundle.get("nanak113"), skin);
			Label nanak11 =new Label(CatGame.myBundle.get("nanak114"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			nanak4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak4);
			nanak4.getColor().a=0;
			nanak5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak5);
			nanak5.getColor().a=0;
			nanak6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak6);
			nanak6.getColor().a=0;
			nanak7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak7);
			nanak7.getColor().a=0;
			nanak8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak8);
			nanak8.getColor().a=0;
			nanak9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak9);
			nanak9.getColor().a=0;
			nanak10.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak10);
			nanak10.getColor().a=0;
			nanak11.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak11);
			nanak11.getColor().a=0;
			
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin45"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin46"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin47"), skin);
			Label dolphin4 =new Label(CatGame.myBundle.get("dolphin48"), skin);
			Label dolphin5 =new Label(CatGame.myBundle.get("dolphin49"), skin);
			Label dolphin6 =new Label(CatGame.myBundle.get("dolphin50"), skin);
			Label dolphin7 =new Label(CatGame.myBundle.get("dolphin51"), skin);
			Label dolphin8 =new Label(CatGame.myBundle.get("dolphin52"), skin);
			Label dolphin9 =new Label(CatGame.myBundle.get("dolphin53"), skin);
			Label dolphin10=new Label(CatGame.myBundle.get("dolphin54"), skin);
			Label dolphin11=new Label(CatGame.myBundle.get("dolphin55"), skin);
			Label dolphin12=new Label(CatGame.myBundle.get("dolphin56"), skin);
			Label dolphin13=new Label(CatGame.myBundle.get("dolphin57"), skin);
			
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			dolphin4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin4);
			dolphin4.getColor().a=0;
			dolphin5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin5);
			dolphin5.getColor().a=0;
			dolphin6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin6);
			dolphin6.getColor().a=0;
			dolphin7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin7);
			dolphin7.getColor().a=0;
			dolphin8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin8);
			dolphin8.getColor().a=0;
			dolphin9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin9);
			dolphin9.getColor().a=0;
			dolphin10.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin10);
			dolphin10.getColor().a=0;
			dolphin11.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin11);
			dolphin11.getColor().a=0;
			dolphin12.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin12);
			dolphin12.getColor().a=0;
			dolphin13.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin13);
			dolphin13.getColor().a=0;
			
			dialog.addAction  (sequence(delay(10),show(),   alpha(1, 0.5f, fade), delay(70f) , alpha(0, 0.5f, fade),hide()   ));
			nanak.addAction    (sequence(delay(10),          alpha(1, 0.5f, fade), delay(11f) , alpha(0, 0.5f, fade)));
			nanak1.addAction   (sequence(delay(10),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction   (sequence(delay(13),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction   (sequence(delay(16),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak4.addAction   (sequence(delay(19),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin1.addAction   (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(25),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak5.addAction   (sequence(delay(25),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak6.addAction   (sequence(delay(28),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(31),         alpha(1, 0.5f, fade), delay(14f) , alpha(0, 0.5f, fade)));
			dolphin2.addAction   (sequence(delay(31),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin3.addAction   (sequence(delay(34),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin4.addAction   (sequence(delay(37),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin5.addAction   (sequence(delay(40),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin6.addAction   (sequence(delay(43),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(46),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak7.addAction   (sequence(delay(46),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak8.addAction   (sequence(delay(49),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(52),         alpha(1, 0.5f, fade), delay(17f) , alpha(0, 0.5f, fade)));
			dolphin7.addAction    (sequence(delay(52),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin8.addAction    (sequence(delay(55),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin9.addAction    (sequence(delay(58),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin10.addAction   (sequence(delay(61),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin11.addAction   (sequence(delay(64),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin12.addAction   (sequence(delay(67),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(70),          alpha(1, 0.5f, fade), delay(8f) , alpha(0, 0.5f, fade)));
			nanak9.addAction   (sequence(delay(70),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak10.addAction  (sequence(delay(73),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak11.addAction  (sequence(delay(76),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction     (sequence(delay(79),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin13.addAction    (sequence(delay(79),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		if(event==28){
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin58"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin59"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin60"), skin);
			Label dolphin4 =new Label(CatGame.myBundle.get("dolphin61"), skin);
			Label dolphin5 =new Label(CatGame.myBundle.get("dolphin62"), skin);
			Label dolphin6 =new Label(CatGame.myBundle.get("dolphin63"), skin);
			Label dolphin7 =new Label(CatGame.myBundle.get("dolphin64"), skin);
			
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			dolphin4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin4);
			dolphin4.getColor().a=0;
			dolphin5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin5);
			dolphin5.getColor().a=0;
			dolphin6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin6);
			dolphin6.getColor().a=0;
			dolphin7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin7);
			dolphin7.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(20f) , alpha(0, 0.5f, fade),hide()   ));
			dolphin.addAction    (sequence(delay(1),         alpha(1, 0.5f, fade), delay(20f) , alpha(0, 0.5f, fade)));
			dolphin1.addAction   (sequence(delay(1),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin2.addAction   (sequence(delay(4),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin3.addAction   (sequence(delay(7),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin4.addAction   (sequence(delay(10),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin5.addAction   (sequence(delay(13),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin6.addAction   (sequence(delay(16),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin7.addAction   (sequence(delay(19),        alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==29){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak115"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak116"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("kuro34"), skin);
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			kuro .addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2f)  , alpha(0, 0.5f, fade)));
			kuro1.addAction  (sequence(delay(7),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			
		}
		
		if(event==30){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak117"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak118"), skin);
			Label nanak3 =new Label(CatGame.myBundle.get("nanak119"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			nanak3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak3);
			nanak3.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade),hide()   ));
			nanak .addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(9.5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction  (sequence(delay(1),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak3.addAction  (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		if(event==31){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak120"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak121"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			Image kuro= new Image(Assets.manager.get(Assets.kuro, Texture.class));
			kuro.setPosition(2*pad, 2*pad);
			kuro.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(kuro);
			kuro.getColor().a=0;
			Label kuro1 =new Label(CatGame.myBundle.get("noris1"), skin);
			kuro1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-kuro1.getHeight()/2);
			dialog.addActor(kuro1);
			kuro1.getColor().a=0;
			
			
			dialog.addAction (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(8.5f) , alpha(0, 0.5f, fade),hide()   ));
			kuro1.addAction  (sequence(delay(1),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction  (sequence(delay(4),          alpha(1, 0.5f, fade), delay(5f) , alpha(0, 0.5f, fade)));
			nanak1.addAction (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak2.addAction (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			
		}
		
		if(event==32){
			Image nanak= new Image(Assets.manager.get(Assets.nanaki, Texture.class));
			nanak.setPosition(2*pad, 2*pad);
			nanak.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(nanak);
			nanak.getColor().a=0;
			Label nanak1 =new Label(CatGame.myBundle.get("nanak122"), skin);
			Label nanak2 =new Label(CatGame.myBundle.get("nanak123"), skin);
			nanak1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak1);
			nanak1.getColor().a=0;
			nanak2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-nanak1.getHeight()/2);
			dialog.addActor(nanak2);
			nanak2.getColor().a=0;
			
			Image dolphin= new Image(Assets.manager.get(Assets.dolphin, Texture.class));
			dolphin.setPosition(2*pad, 2*pad);
			dolphin.setSize(Gdx.graphics.getHeight()/5-4*pad, Gdx.graphics.getHeight()/5-4*pad);
			dialog.addActor(dolphin);
			dolphin.getColor().a=0;
			Label dolphin1 =new Label(CatGame.myBundle.get("dolphin65"), skin);
			Label dolphin2 =new Label(CatGame.myBundle.get("dolphin66"), skin);
			Label dolphin3 =new Label(CatGame.myBundle.get("dolphin67"), skin);
			Label dolphin4 =new Label(CatGame.myBundle.get("dolphin68"), skin);
			Label dolphin5 =new Label(CatGame.myBundle.get("dolphin69"), skin);
			Label dolphin6 =new Label(CatGame.myBundle.get("dolphin70"), skin);
			Label dolphin7 =new Label(CatGame.myBundle.get("dolphin71"), skin);
			Label dolphin8 =new Label(CatGame.myBundle.get("dolphin72"), skin);
			Label dolphin9 =new Label(CatGame.myBundle.get("dolphin73"), skin);
			
			dolphin1.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin1);
			dolphin1.getColor().a=0;
			dolphin2.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin2);
			dolphin2.getColor().a=0;
			dolphin3.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin3);
			dolphin3.getColor().a=0;
			dolphin4.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin4);
			dolphin4.getColor().a=0;
			dolphin5.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin5);
			dolphin5.getColor().a=0;
			dolphin6.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin6);
			dolphin6.getColor().a=0;
			dolphin7.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin7);
			dolphin7.getColor().a=0;
			dolphin8.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin8);
			dolphin8.getColor().a=0;
			dolphin9.setPosition(2*pad+Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/10-dolphin1.getHeight()/2);
			dialog.addActor(dolphin9);
			dolphin9.getColor().a=0;
			
			dialog.addAction  (sequence(delay(1),show(),   alpha(1, 0.5f, fade), delay(32.2f) , alpha(0, 0.5f, fade),hide()   ));
			dolphin.addAction    (sequence(delay(1),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin1.addAction   (sequence(delay(1),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak1.addAction   (sequence(delay(4),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(7),         alpha(1, 0.5f, fade), delay(20f) , alpha(0, 0.5f, fade)));
			dolphin2.addAction   (sequence(delay(7),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin3.addAction   (sequence(delay(10),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin4.addAction   (sequence(delay(13),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin5.addAction   (sequence(delay(16),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin6.addAction   (sequence(delay(19),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin7.addAction   (sequence(delay(22),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin8.addAction   (sequence(delay(25),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			nanak.addAction    (sequence(delay(28),          alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			nanak2.addAction   (sequence(delay(28),          alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
			dolphin.addAction    (sequence(delay(31),         alpha(1, 0.5f, fade), delay(2f) , alpha(0, 0.5f, fade)));
			dolphin9.addAction   (sequence(delay(31),         alpha(1, 0.5f, fade), delay(2) , alpha(0, 0.5f, fade)));
		}
		
		
		
		
		return dialog;
	}
}
