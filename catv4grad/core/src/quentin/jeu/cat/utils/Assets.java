package quentin.jeu.cat.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	
	public static AssetManager manager = new AssetManager();
	
	public final static String title          = "ui/title4.png";
	public final static String arrow          = "ui/arow.png";
	//bg
	public final static String galaxybg       = "bg/galaxy.jpg";
	public final static String nebulabg       = "bg/nebula.jpg";
	public final static String sunbg1         = "bg/sun1.jpg";
	public final static String sunbg2         = "bg/sun2.jpg";
	public final static String sunbg3         = "bg/sun3.jpg";
	public final static String galaxy         = "bg/galaxy2.png";
	public final static String stars          = "bg/stars.jpg";
	public final static String outro          = "bg/outrobg.png";
	
	//player
	public final static String spaceship1     = "player/body1.png";
	public final static String spaceship2     = "player/body2.png";
	public final static String spaceship3     = "player/body3.png";
	public final static String wing           = "player/wing1.png";
	public final static String riceGun        = "player/weapon1.png";
	public final static String sushiGun       = "player/weapon2.png";
	public final static String laserGun       = "player/weapon3.png";
//	public final static String laserGun0      = "player/weapon30.png";
//	public final static String laserGun1      = "player/weapon31.png";
	public final static String prop1          = "player/prop1.png";
	public final static String prop2          = "player/prop2.png";
	public final static String stick          = "player/stick.png";
	public final static String nanaki         = "player/nanaki.png";
	public final static String nanapaw        = "player/nanapaw.png";
	public final static String shield         = "player/shield.png";
	
	//enemies
	public final static String asteroid0      = "enemies/asteroid0.png";
	
	public final static String weak1          = "enemies/weak1.png";
	public final static String normal1        = "enemies/normal1.png";
	public final static String strong1        = "enemies/strong1.png";
	public final static String bomber1        = "enemies/bomber1.png";
	public final static String bombe1         = "enemies/bombe1.png";
	public final static String bombe2         = "enemies/bombe2.png";
	
	public final static String weak2          = "enemies/weak2.png";
	public final static String normal2        = "enemies/normal2.png";
	public final static String strong2        = "enemies/strong2.png";
	
	public final static String kuro           = "enemies/kuro.png";
	public final static String catious        = "enemies/catious.png";
	public final static String dolphin        = "enemies/dauphin.png";
	
	public final static String mship          = "enemies/mship.png";
	public final static String probe          = "enemies/probe.png";
	
	public final static String ultima         = "enemies/ultima.png";
	public final static String prot           = "enemies/prot.png";
	public final static String gen            = "enemies/gen.png";
	
	//weapon
	public final static String bulletRegion   = "weapon/bullet.png";
	public final static String ricebullet     = "weapon/ricebullet.png";
	public final static String sushiMissil    = "weapon/sushim.png";
	public final static String sushiMissil2   = "weapon/sushim2.png";
	public final static String hitRegion      = "weapon/bullet-hit.png";
	public final static String power          = "weapon/poweric.png";
	public final static String speed          = "weapon/speedic.png";
	public final static String fragment       = "weapon/fragic.png";
	public final static String reload1        = "weapon/loadic1.png";
	public final static String reload2        = "weapon/loadic0.png";
	public final static String lzmax          = "weapon/lzmax.png";
	public final static String lzmin          = "weapon/lzmin.png";
	public final static String lzanim         = "weapon/lzanim.png";
	public final static String laser00        = "weapon/laser00.png";
	public final static String laser01        = "weapon/laser01.png";
	public final static String laser10        = "weapon/laser10.png";
	public final static String laser11        = "weapon/laser11.png";
	public final static String laser20        = "weapon/laser20.png";
	public final static String laser21        = "weapon/laser21.png";
	public final static String laser3         = "weapon/laser31.png";
	public final static String particle       = "effects/particle.png";
	
	//map
	public final static String star0          = "map/star0.png";
	public final static String star1          = "map/star1.png";
	public final static String star2          = "map/star2.png";
	public final static String star3          = "map/star3.png";
	public final static String star4          = "map/star4.png";
	public final static String star5          = "map/star5.png";
	public final static String star51         = "map/star51.png";
	public final static String planet1        = "map/planet1.png";
	public final static String planet11       = "map/planet11.png";
	public final static String planet2        = "map/planet2.png";
	public final static String planet3        = "map/planet3.png";
	public final static String planet31       = "map/planet31.png";
	public final static String range          = "map/range.png";
	public final static String path           = "map/path.png";
	public final static String target         = "map/target.png";
	public final static String ship           = "map/ship.png";
	public final static String maki           = "map/maki.png";
	public final static String metal          = "map/metal.png";
	public final static String shipb          = "map/shipb.png";
	public final static String armor          = "map/armor.png";
	public final static String wrench         = "map/wrench.png";
	public final static String hmaki          = "map/hmaki.png";
	public final static String makikit        = "map/makikit.png";
	public final static String drill          = "map/drill.png";
	public final static String plus           = "map/plus.png";
	
	
	public static void load(){
		
		manager.load( title          , Texture.class);
		manager.load( arrow          , Texture.class);
		
		manager.load( sunbg1         , Texture.class);
		manager.load( sunbg2         , Texture.class);
		manager.load( sunbg3         , Texture.class);
		manager.load( galaxybg       , Texture.class);
		manager.load( nebulabg       , Texture.class);
		manager.load( galaxy         , Texture.class);
		manager.load( asteroid0      , Texture.class);
		
		manager.load( spaceship1     , Texture.class);
		manager.load( spaceship2     , Texture.class);
		manager.load( spaceship3     , Texture.class);
		manager.load( wing           , Texture.class);
		manager.load( riceGun        , Texture.class);
		manager.load( sushiGun       , Texture.class);
		manager.load( laserGun       , Texture.class);
	//  manager.load( laserGun0      , Texture.class);
	//	manager.load( laserGun1      , Texture.class);
		manager.load( prop1          , Texture.class);
		manager.load( prop2          , Texture.class);
		manager.load( nanaki         , Texture.class);
		manager.load( nanapaw        , Texture.class);
		manager.load( stick          , Texture.class);
		manager.load( shield         , Texture.class);
		
		manager.load( weak1          , Texture.class);
		manager.load( normal1        , Texture.class);
		manager.load( strong1        , Texture.class);
		manager.load( bomber1        , Texture.class);
		manager.load( bombe1         , Texture.class);
		manager.load( bombe2         , Texture.class);
		
		manager.load( weak2          , Texture.class);
		manager.load( normal2        , Texture.class);
		manager.load( strong2        , Texture.class);
		
		manager.load( kuro           , Texture.class);
		manager.load( catious        , Texture.class);
		manager.load( dolphin        , Texture.class);
		manager.load( mship          , Texture.class);
		manager.load( probe          , Texture.class);
		
		manager.load( gen            , Texture.class);
		manager.load( prot           , Texture.class);
		manager.load( ultima         , Texture.class);
		
		manager.load( bulletRegion   , Texture.class);
		manager.load( ricebullet     , Texture.class);
		manager.load( sushiMissil    , Texture.class);
		manager.load( sushiMissil2   , Texture.class);
		manager.load( hitRegion      , Texture.class);
		manager.load( power          , Texture.class);
		manager.load( speed          , Texture.class);
		manager.load( fragment       , Texture.class);
		manager.load( reload2        , Texture.class);
		manager.load( reload1        , Texture.class);
		manager.load( lzmax          , Texture.class);
		manager.load( lzmin          , Texture.class);
		manager.load( lzanim         , Texture.class);
		manager.load( laser00        , Texture.class);
		manager.load( laser01        , Texture.class);
		manager.load( laser10        , Texture.class);
		manager.load( laser11        , Texture.class);
		manager.load( laser20        , Texture.class);
		manager.load( laser21        , Texture.class);
		manager.load( laser3         , Texture.class);
		manager.load( particle       , Texture.class);
		
		manager.load(star0           , Texture.class);
		manager.load(star1           , Texture.class);
		manager.load(star2           , Texture.class);
		manager.load(star3           , Texture.class);
		manager.load(star4           , Texture.class);
		manager.load(star5           , Texture.class);
		manager.load(star51          , Texture.class);
		manager.load(planet1         , Texture.class);
		manager.load(planet11        , Texture.class);
		manager.load(planet2         , Texture.class);
		manager.load(planet3         , Texture.class);
		manager.load(planet31        , Texture.class);
		manager.load(range           , Texture.class);
		manager.load(stars           , Texture.class);
		manager.load(outro           , Texture.class);
		manager.load(path            , Texture.class);
		manager.load(target          , Texture.class);
		manager.load(ship            , Texture.class);
		manager.load(maki            , Texture.class);
		manager.load(metal           , Texture.class);
		manager.load(shipb           , Texture.class);
		manager.load(armor           , Texture.class);
		manager.load(makikit         , Texture.class);
		manager.load(hmaki           , Texture.class);
		manager.load(drill           , Texture.class);
		manager.load(plus            , Texture.class);
		manager.load(wrench          , Texture.class);

		
	}
	
	public static void dispose(){
		manager.dispose();
		manager=null;
	}
}
