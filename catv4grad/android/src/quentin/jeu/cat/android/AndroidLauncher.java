package quentin.jeu.cat.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import quentin.jeu.cat.CatGame;

public class AndroidLauncher extends AndroidApplication {
	private DroidShop shop;
	private DroidGoogleServices gservices;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		if(android.os.Build.VERSION.SDK_INT>18)config.useImmersiveMode=true;
		
		shop=new DroidShop(this);
		gservices = new DroidGoogleServices(this);
		initialize(new CatGame(shop, gservices), config);
	}
	
	/*@Override
	protected void onStart(){
		super.onStart();
		gservices.gameHelper.onStart(this);
	}

	@Override
	protected void onStop(){
		super.onStop();
		gservices.gameHelper.onStop();
		
	}*/
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 gservices.gameHelper.onActivityResult(requestCode, resultCode, data);
		 
        if (shop.mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!shop.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            //Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
}

