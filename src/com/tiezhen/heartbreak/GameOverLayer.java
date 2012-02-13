package com.tiezhen.heartbreak;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

class GameOverLayer extends CCLayer {
	private CCSprite playAgainButton;
	private CCSprite mainMenuButton;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new GameOverLayer();
		scene.addChild(layer);
		return scene;
	}

	// TODO: How to get back to activity and show layout??
	protected GameOverLayer() {
		this.setIsTouchEnabled(true);
		// Show gameover

		// // Background should be at the bottom.
		// CCSprite background = CCSprite.sprite("background.png");
		// // image size will not be correct without the following line.
		// background.setAnchorPoint(0, 0);
		// this.addChild(background, -1);

		Helper.showBackgoundAndTopBanner(this);

		// Trial failed..
		// ImageButton playAgainButton = new
		// ImageButton(CCDirector.sharedDirector().getActivity());
		// // ?? How to convert frame to drawable???
		// playAgainButton.setBackgroundResource(R.drawable.icon);
		// playAgainButton.???

		playAgainButton = Helper.createImageLabelButton(this, "play_again");
		mainMenuButton = Helper.createImageLabelButton(this, "main_menu");

		CGSize winSize = CCDirector.sharedDirector().displaySize();

		CCLabel gameOverLabel = CCLabel.makeLabel("GameOver!", "toonish.ttf",
				96);
		gameOverLabel.setPosition((3.5f * winSize.width / 7),
				2 * winSize.height / 7);
		this.addChild(gameOverLabel);

		CCLabel yourScoreLabel = CCLabel.makeLabel("Score: " + GameLayer.score,
				"toonish.ttf", 48);
		yourScoreLabel.setPosition(3.5f * winSize.width / 7,
				1 * winSize.height / 7);
		this.addChild(yourScoreLabel);
	}

	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher()
				.addTargetedDelegate(this, 0, false);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint location = CCDirector.sharedDirector().convertToGL(
				CGPoint.ccp(event.getX(), event.getY()));

		if (CGRect.containsPoint(mainMenuButton.getBoundingBox(), location)) {
			// This one should be put on top of play Again in order to have more
			// spaces
			
			// Back to home screen
			CCDirector.sharedDirector().replaceScene(HomeLayer.scene());
		} else if (CGRect.containsPoint(playAgainButton.getBoundingBox(),
				location)) {
			// Play sound
			((GameActivity) CCDirector.sharedDirector().getActivity())
					.playTouchSound();
			// Play again
			GameLayer.restart();
			CCDirector.sharedDirector().replaceScene(GameLayer.scene());
		}

		// ?
		return CCTouchDispatcher.kEventHandled;
	}

}