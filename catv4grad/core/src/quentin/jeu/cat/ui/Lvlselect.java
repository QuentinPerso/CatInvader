package quentin.jeu.cat.ui;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Lvlselect {
	
	//private TweenManager tweenManager;
	public Stage stage;	
	public boolean inlvlsel;
	
	private Skin skin;
	private Table table;
	
	
	public Lvlselect(final InputMultiplexer extmultiplexer, final Skin skin) 
	{
		this.skin=skin;
	
		stage = new Stage();
	
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);

		PagedScrollPane scroll = new PagedScrollPane();
		scroll.setScrollingDisabled(false, true);
		scroll.setFlingTime(0.1f);
		scroll.setPageSpacing(25);
		
		
		TextButton buttonBack = new TextButton("  Back  ", skin, "default");
		buttonBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				inlvlsel=false;
				Gdx.input.setInputProcessor(extmultiplexer);}
			;});
		
		int c = 1;
		//l=number of pages
		for (int l = 0; l < 4; l++) {
			Table level = new Table().pad(50);
			//levels.defaults();
			level.add(getLevelButton(c++));
				
			scroll.addPage(level);
		}
		
		table.debug();
		table.top().right().add(buttonBack).expandX().colspan(1).right().pad(15).row();
		table.add(scroll).expand().fill();
		stage.addActor(table);
		
	}
	
	public void update(float delta) {
		
		stage.act(delta);
		stage.draw();
		//Table.drawDebug(stage);
	}
	
	public Button getLevelButton(int level) {
		Button button = new Button(skin);
		ButtonStyle style = button.getStyle();                 ////////antipad????????
		style.up = 	style.down = null;
		switch(level){
		case 1: 
			button = createlvl1b();
			break;
		}
		return button;
		
	}

	private Button createlvl1b() {
		Button button = new Button(skin);
		ButtonStyle style = button.getStyle();
		style.up = 	style.down = null;
		
		// Create the label to show the level name
		Label title = new Label("Catch'em All", skin);
		title.setFontScale(0.9f);
		title.setAlignment(Align.center);
		
		// Create the label to show the level HS
		//Label hscore = new Label("Best : \n" + Integer.toString(Save.cd.highScore1), skin);
		//hscore.setFontScale(0.75f);
		//hscore.setAlignment(Align.center);
		// Create the image to show the level image
		//Image lvlimage = new Image(Assets.manager.get(Assets.lvl1b, Texture.class));
		//lvlimage.setAlign(Align.center);
		
		// Stack the image and the label at the top of our button
		
		button.debug();
		button.add(title).expand().fill();
		button.row();
		//button.add(lvlimage);
		button.row();
		//button.add(hscore).expand().fill();
		
		button.setName("Level1");
		button.addListener(levelClickListener);		
		return button;

	}
	
	

	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked (InputEvent event, float x, float y) {
		//	if(event.getListenerActor().getName().equalsIgnoreCase("level1"))((Game) Gdx.app.getApplicationListener()).setScreen(new Arcade1());
		//	if(event.getListenerActor().getName().equalsIgnoreCase("level2"))((Game) Gdx.app.getApplicationListener()).setScreen(new Arcade2());
		//	if(event.getListenerActor().getName().equalsIgnoreCase("level3"))((Game) Gdx.app.getApplicationListener()).setScreen(new Arcade3());
		}
	};

	public void resize(int width, int height) {
		//stage.setViewport(width, height);
		table.invalidateHierarchy();
		
	}

	public void dispose() {
	
		
	}
	
}