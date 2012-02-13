package com.tiezhen.heartbreak;

import java.util.Random;
import java.util.Vector;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;

import android.view.MotionEvent;

class GameLayer extends CCLayer {
	Vector<Heart> _Hearts = null;

	float heartGenerationInterval = 0.1f;
	static int lifeLeft = 3;
	static int score = 0;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new GameLayer();

		scene.addChild(layer);

		return scene;
	}

	public static void restart() {
		lifeLeft = 3;
		score = 0;
	}

	CCAction generateHeartAction;

	private CCLabel scoreLable;

	protected GameLayer() {
		this.setIsTouchEnabled(true);
		_Hearts = new Vector<Heart>();

		scoreLable = Helper.showBackgoundAndTopBanner(this);

		// Do an infinitive loop to have more hearts with time.
		this.generateHeartAction = CCRepeatForever.action(CCSequence.actions(
				CCDelayTime.action(this.heartGenerationInterval),
				CCCallFunc.action(this, "newHeartGeneration")));
		this.runAction(generateHeartAction);
	}

	public void newHeartGeneration() {
		// Change the difficulty according to current score
		// More score => more fast heart generation
		
		// In order to be not so hard
		// Func: score = 0 => generation factor should be not higher than 1
		// y value = 10 => (Hardest) one should be occur when x = 1000 + 
		
		// Change x factor to change complexity incresase rate
		if (new Random().nextInt(10) < (int) (Math.log((score/30f-1.4f) / 80f + 0.03f) + 6f )) {
			// Create a new heart which should be on the top of screen.
			Heart newHeart = new Heart();

			// Add new heart in the screen
			addChild(newHeart, _Hearts.size()); // second parameter is the
												// z-axis value in integer

			// Add new heart in the list of hearts
			_Hearts.add(newHeart);
		}

	}

	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher()
				.addTargetedDelegate(this, 0, false);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		if (_Hearts != null) {
			// For each heart, hide the hit one.
			for (Heart heart : _Hearts) {
				// If one heart is not visible (already removed), no need to
				// handle this.
				if (heart.getVisible()) {
					// One heart bitten, one score
					if (heart.handleTouches(event)) {
						score += 1;
						this.scoreLable.setString("Score :" + score);
					}
				}

				// If a heart is not visible, just remove it.
				if (!heart.getVisible()) {
					this.removeChild(heart, true);
				}

				// TODO: remove heart from list also. => ?
			}
		}

		// ?
		return CCTouchDispatcher.kEventHandled;
	}

	public void loseLife() {
		if (GameLayer.lifeLeft > 0) {
			GameLayer.lifeLeft -= 1;
			// Start over
			CCDirector.sharedDirector().replaceScene(GameLayer.scene());
		} else {
			CCDirector.sharedDirector().replaceScene(GameOverLayer.scene());
		}
	}
}