package quentin.jeu.cat.tuto;


/** The view class for an enemy or player that moves around the map. */
class CharacterViewtt {
	Viewtt view;
	Modeltt model;

	CharacterViewtt (Viewtt view) {
		this.view = view;
		model = view.model;
	}

/*	boolean setAnimation (StateView state, boolean force) {
		// Changes the current animation on track 0 of the AnimationState, if needed.
		Animation animation = state.animation;
		TrackEntry current = animationState.getCurrent(0);
		Animation oldAnimation = current == null ? null : current.getAnimation();
		if (force || oldAnimation != animation) {
			if (state.animation == null) return true;
			TrackEntry entry = animationState.setAnimation(0, state.animation, state.loop);
			if (oldAnimation != null) entry.setTime(state.startTimes.get(oldAnimation, state.defaultStartTime));
			if (!state.loop) entry.setEndTime(9999);
			return true;
		}
		return false;
	}*/
}
