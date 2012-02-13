package com.tiezhen.heartbreak;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGSize;

public class Helper {
	static public CCSprite createImageLabelButton(CCLayer layer, String type) {
		// TODO: label rotaion with heart
		// TODO: better label placement
		float xPosition;
		String labelName;
		float rotation;
		int zAxisBonus;
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		if (type.equals("play")) {
			xPosition = winSize.width * 7 / 11;
			labelName = "Play";
			rotation = 20f;
			zAxisBonus = 10;
		} else if (type.equals("play_again")) {
			xPosition = winSize.width * 7 / 11;
			labelName = "Play Again";
			rotation = 20f;
			zAxisBonus = 10;
		} else if (type.equals("main_menu")) {
			xPosition = winSize.width * 3.7f / 11;
			labelName = "Main Menu";
			rotation = -20f;
			zAxisBonus = 0;
		} else if (type.equals("rate")) {
			xPosition = winSize.width * 3.7f / 11;
			labelName = "Rate";
			rotation = -20f;
			zAxisBonus = 0;
		} else {
			// TODO:
			return null;
		}
		// Try to create from scretch..
		CCSprite button = CCSprite.sprite(CCSpriteFrameCache
				.sharedSpriteFrameCache().getSpriteFrame("srdce.png"));
		button.setRotation(rotation);
		
		button.runAction(CCRepeatForever.action(CCSequence.actions(
				CCScaleTo.action(1f / 2, 1 * 1.2f),
				CCScaleTo.action(1f / 2, 1))));

		button.setPosition(xPosition, winSize.height * 7 / 11);
		CCLabel label = CCLabel.makeLabel(labelName, "toonish.ttf", 24);
		// playAgainButton.addChild(playAgainLabel);
		label.setPosition(button.getPosition().x + 10, button.getPosition().y);
		layer.addChild(button, 1000 + zAxisBonus);
		layer.addChild(label, 1200 + zAxisBonus);

		return button;
	}

	/**
	 * Load background image, scale it to whole screen Show score and life
	 * banner
	 */

	// TODO: Stupid design...
	public static CCLabel showBackgoundAndTopBanner(CCLayer layer) {
		showBackground(layer);

		CCLabel scoreLable = CCLabel.makeLabel("Score: " + GameLayer.score,
				"toonish.ttf", 48);
		scoreLable.setAnchorPoint(0, 1);
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		scoreLable.setPosition(15, winSize.height - 15);
		layer.addChild(scoreLable, 9999);

		// Life banner is on top-right corner. One heart means on life left.
		for (int i = 0; i < GameLayer.lifeLeft; i++) {
			CCSprite lifeSprite = CCSprite.sprite(CCSpriteFrameCache
					.sharedSpriteFrameCache().getSpriteFrame("life.png"));
			lifeSprite.setAnchorPoint(1, 1);
			lifeSprite.setPosition(winSize.width
					- lifeSprite.getContentSize().width * i,
					winSize.height - 15);
			layer.addChild(lifeSprite, 9999);
		}

		return scoreLable;
	}

	/**
	 * @param layer
	 */
	public static void showBackground(CCLayer layer) {
		// Background should be at the bottom.
		CCSprite background = CCSprite.sprite("background.png");
		// image size will not be correct without the following line.
		background.setAnchorPoint(0, 0);
		layer.addChild(background, -1);
	}
}
