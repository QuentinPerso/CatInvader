package quentin.jeu.cat.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import quentin.jeu.cat.CatGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Cat Invaders";
		//cfg.useGL20 = true;
		cfg.width  = (int) (1200);
		cfg.height = (int) (1200*9/16);
		cfg.addIcon("ui/icon.png",FileType.Internal);
		
		new LwjglApplication(new CatGame(new DeskShop(), new DeskGoogleServices()), cfg);
	}
}
