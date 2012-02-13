package com.tiezhen.heartbreak;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;

class HomeLayer extends CCLayer {

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new HomeLayer();
		scene.addChild(layer);
		return scene;
	}

	CCSprite playButton;
	CCSprite rateButton;

	// TODO: How to get back to activity and show layout??
	protected HomeLayer() {
		loadPlistAndFrames();
		this.setIsTouchEnabled(true);
		Helper.showBackgoundAndTopBanner(this);

		playButton = Helper.createImageLabelButton(this, "play");
		rateButton = Helper.createImageLabelButton(this, "rate");
		
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		
		CCSprite logo = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(
				"logo.png"));
		logo.setPosition(3.5f*winSize.width/7, 1.5f*winSize.height/7);
		logo.setScale(1.4f);
		this.addChild(logo);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint location = CCDirector.sharedDirector().convertToGL(
				CGPoint.ccp(event.getX(), event.getY()));

		if (CGRect.containsPoint(playButton.getBoundingBox(), location)) {
			// Play sound
			((GameActivity) CCDirector.sharedDirector().getActivity()).playTouchSound();
			
			// Play (again)
			GameLayer.restart();
			CCDirector.sharedDirector().replaceScene(GameLayer.scene());
		} else if (CGRect.containsPoint(rateButton.getBoundingBox(), location)) {
			// Rate application in android market
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://search?q=pname:com.tiezhen.heartbreak"));
			CCDirector.sharedDirector().getActivity()
					.startActivity(browserIntent);
		}

		// ?
		return CCTouchDispatcher.kEventHandled;
	}

	/**
	 * 
	 */
	private void loadPlistAndFrames() {
		// Load res in SpriteFrameCache
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(
				"heartbreakertexture.plist");

		// Pre-load the photos in memory
		// The program works even without the following section
		CCSpriteSheet spriteSheet = CCSpriteSheet
				.spriteSheet("heartbreakertexture.png");
		this.addChild(spriteSheet);
	}
}