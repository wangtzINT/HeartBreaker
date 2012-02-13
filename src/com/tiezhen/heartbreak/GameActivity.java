package com.tiezhen.heartbreak;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	private CCGLSurfaceView mGLSurfaceView;

	// TODO: Very bad design..
	private Context _cocos2dContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the window status, no tile, full screen and don't sleep
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		openGame();

	}

	/**
	 * 
	 */
	private void openGame() {
		mGLSurfaceView = new CCGLSurfaceView(this);

		setContentView(mGLSurfaceView);

		// attach the OpenGL view to a window
		CCDirector.sharedDirector().attachInView(mGLSurfaceView);

		// no effect here because device orientation is controlled by manifest
		CCDirector.sharedDirector().setDeviceOrientation(
				CCDirector.kCCDeviceOrientationPortrait);

		// show FPS
		// set false to disable FPS display, but don't delete fps_images.png!!
		CCDirector.sharedDirector().setDisplayFPS(true);

		// frames per second
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
		
		// Set Context
		_cocos2dContext = this;

		// Preload sound
		SoundEngine.sharedEngine().preloadSound(_cocos2dContext, R.raw.hitsound);
		
		// Start Home screen
		CCScene scene = HomeLayer.scene();

		// Make the Scene active
		CCDirector.sharedDirector().runWithScene(scene);
	}

	public void playTouchSound() {
			SoundEngine.sharedEngine().playSound(_cocos2dContext,
					R.raw.hitsound, false);
	}

	private void openGameOver() {
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();

		CCDirector.sharedDirector().pause();
	}

	@Override
	public void onResume() {
		super.onResume();

		CCDirector.sharedDirector().resume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		CCDirector.sharedDirector().end();
	}

}