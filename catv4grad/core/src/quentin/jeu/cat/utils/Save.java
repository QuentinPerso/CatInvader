package quentin.jeu.cat.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class Save {
	
	public static GameData gd;
	private static FileHandle savefile1 = Gdx.files.local("bin/catsave.json");
	
	////////SAVE ARCADE SCORE/////////
	public static void save() {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		savefile1.writeString(Base64Coder.encodeString(json.toJson(gd)), false);
	}

	public static void load() {
		if(!savefile1.exists()) {
			raz();
		}
		else{
		Json json = new Json();
		gd = json.fromJson(GameData.class, Base64Coder.decodeString(savefile1.readString()));
		}
	}
	
	public static void raz() {
		gd = new GameData();
		gd.init();
		save();
	}
	
	public static void razHS() {
		gd.razHS();
		save();
	}
	
	
	
}

