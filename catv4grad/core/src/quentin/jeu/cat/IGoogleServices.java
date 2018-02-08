package quentin.jeu.cat;

public interface IGoogleServices {

	public void signIn();
	public void signOut();
	public void rateGame();
	public void submitScore(long score);
	public void incrementexplore(int explored);
	public void incrementscore(int score);
	public void incrementmetal(int metal);
	public void showAchieve();
	public void showScores();
	public boolean isSignedIn();
	
}
