package com.mac.android.goalmania;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class GeneralFragmentActivity extends SherlockFragmentActivity{
	protected ActionBar ab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		initTitle(ab);
	}

	protected final void restartActivity() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	protected ActionBar setActionBar() {
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		return mActionBar;
	}

	protected void showKeyBoard(View v) {
		v.requestFocus();
		getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	protected float convertPixelToDip(int pixel) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel,
				getResources().getDisplayMetrics());
	}

	protected void setVisibilityMenuOption(Menu menu, int id, boolean visible) {
		MenuItem item = menu.findItem(id);
		item.setVisible(visible);
	}

	protected void addMenuOption(Menu menu, int id, int order, int idString,
			int idDrawable, boolean visible, int action) {
		menu.add(0, id, order, getString(idString))
		.setIcon(idDrawable)
		.setVisible(visible)
		.setShowAsAction(action);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	protected abstract void initInterface();

	protected abstract void getIntentData();

	protected abstract void putIntentData();

	protected abstract Object loadDataBase();
	
	protected abstract void processActivity();

	protected abstract void initTitle(ActionBar bar);

}