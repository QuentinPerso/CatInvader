package quentin.jeu.cat.catinvader;


/** The view class for an enemy or player that moves around the map. */
class CharacterView {
	View view;
	Model model;

	CharacterView (View view) {
		this.view = view;
		model = view.model;
	}
}
