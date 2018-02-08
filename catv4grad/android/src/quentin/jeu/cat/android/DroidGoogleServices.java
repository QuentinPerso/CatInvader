package quentin.jeu.cat.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import quentin.jeu.cat.CatGame;
import quentin.jeu.cat.IGoogleServices;
import quentin.jeu.cat.utils.Save;

public class DroidGoogleServices implements IGoogleServices{

	GameHelper gameHelper;
	private Activity context;
	private final static int REQUEST_CODE_UNUSED = 9002;

	
	public DroidGoogleServices(Activity context){
		this.context=context;
		// Create the GameHelper.
		gameHelper = new GameHelper(context, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener(){
			@Override
			public void onSignInSucceeded(){
			}

			@Override
			public void onSignInFailed(){
			}
		};
		gameHelper.setup(gameHelperListener);
	}
	
	
	@Override
	public void signIn() {
		try{
			context.runOnUiThread(new Runnable(){
				@Override
				public void run(){
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e){
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
		
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
	}

	@Override
	public void signOut() {
		try{
		context.runOnUiThread(new Runnable(){
			//@Override
			public void run(){
				gameHelper.signOut();
			}
			});
		}
		catch (Exception e){
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
		
	}

	@Override
	public void rateGame() {
		// Replace the end of the URL with the package of your game
		String str ="https://play.google.com/store/apps/details?id=quentin.jeu.cat.android";
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
		
	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true){
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), context.getString(R.string.leaderboard_id), score);
			//context.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), context.getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else{
			//signIn();
		}
		
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true)
			context.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), context.getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		else{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void showAchieve() {
		if (isSignedIn() == true)
			context.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), REQUEST_CODE_UNUSED);
		else{
			signIn();
		}
		
	}


	@Override
	public void incrementexplore(int explored) {
		if (isSignedIn() == true){
			Games.Achievements.getAchievementsIntent(gameHelper.getApiClient());
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_beginner_explorer), explored);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_bronze_explorer), explored);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_silver_explorer), explored);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_gold_explorer), explored);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_master_explorer), explored);
		}
		else{
			//signIn();
		}
		
	}


	@Override
	public void incrementscore(int score) {
		if (isSignedIn() == true){
			Games.Achievements.getAchievementsIntent(gameHelper.getApiClient());
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_high_scorer), score);
		}
		else{
			//signIn();
		}
		
	}


	@Override
	public void incrementmetal(int metal) {
		if (isSignedIn() == true){
			Games.Achievements.getAchievementsIntent(gameHelper.getApiClient());
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_tinker), metal);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_mechanic), metal);
			Games.Achievements.increment(gameHelper.getApiClient(),context.getString(R.string.achievement_master_grease_cat), metal);
		}
		else{
			//signIn();
		}
		
	}
	
}
