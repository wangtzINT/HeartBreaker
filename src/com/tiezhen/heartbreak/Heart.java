package com.tiezhen.heartbreak;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintTo;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

public class Heart extends CCSprite {

	// Interval of scaleIn and ScaleOUt
	float tickInterval = 0.5f;
	float livingTime = 5f;

	float basicScale = 2f;

	protected Heart() {

		// Call parent method to create the heart sprite object
		super(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(
				"rozpad_01.png"));

		// Get window size
		CGSize winSize = CCDirector.sharedDirector().displaySize();

		// Set random position inside current window
		int x = new Random().nextInt((int) (winSize.width));
		int y = new Random().nextInt((int) (winSize.height));
		this.setPosition(CGPoint.ccp(x, y));

		this.setScale(2);

		this.showLivingAnimation();
		this.showDeyingAnimation();
	}

	public boolean handleTouches(MotionEvent event) {
		// Return true if in the scope of this sprite.

		// If the heart is hit, hide it
		CGPoint location = CCDirector.sharedDirector().convertToGL(
				CGPoint.ccp(event.getX(), event.getY()));
		if (CGRect.containsPoint(this.getBoundingBox(), location)) {
			// Play sound
			((GameActivity) CCDirector.sharedDirector().getActivity()).playTouchSound();
			// show break heart animation then hide the broken heart
			this.showBreakAnimation();
			return true;
		} else {
			return false;
		}
	}

	private CCAction livingAction;
	private CCAction breakAction;
	private CCAction deyingAction;

	public void showDeyingAnimation() {
		// Hear is turing from red to black.
		deyingAction = CCSequence.actions(
				CCTintTo.action(livingTime, new ccColor3B(80, 80, 80)),
				CCCallFunc.action(this, "lostHeart"));
		this.runAction(deyingAction);
	}

	public void showLivingAnimation() {
		livingAction = CCRepeatForever.action(CCSequence.actions(
				CCScaleTo.action(tickInterval / 2, basicScale * 1.2f),
				CCScaleTo.action(tickInterval / 2, basicScale)));
		this.runAction(livingAction);
	}

	public void showBreakAnimation() {
		this.stopAction(livingAction);
		this.stopAction(deyingAction);

		// Load AnimationSpriteFrames
		ArrayList<CCSpriteFrame> breakAnimationFrames = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i <= 10; i++) {
			// Sprites are named from 01, 02, .., 10
			breakAnimationFrames.add(CCSpriteFrameCache
					.sharedSpriteFrameCache().getSpriteFrame(
							"rozpad_" + String.format("%02d", i) + ".png"));
		}

		// Load Animation of Frames
		CCAnimation breakAnimation = CCAnimation.animation("break", 0.1f,
				breakAnimationFrames);

		// Load Animation action
		// Show heart break animation then hide the broken heart.
		breakAction = CCSequence.actions(
				CCAnimate.action(breakAnimation, false),
				CCCallFunc.action(this, "hideBrokenHeart"));

		// Set action
		this.runAction(breakAction);

	}

	public void registerWithToucheDispatcher() {
		// TODO: this method is not fully ported to android.
		// Comments can be found at org.cocos2d.events.CCTouchDispatcher.java
		// Good reference :
		// http://blog.csdn.net/xiaominghimi/article/details/6665887
	}

	public void hideBrokenHeart() {
		// Hide the element
		this.setVisible(false);
	}
	
	public void lostHeart(){
		// Heart was lost => lose one life
		// This one is not working
//		((GameLayer) CCDirector.sharedDirector().getRunningScene().getChildByTag(123)).loseLife();
		((GameLayer)this.getParent()).loseLife();
	}

}
