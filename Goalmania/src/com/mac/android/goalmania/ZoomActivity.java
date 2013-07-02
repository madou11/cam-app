package com.mac.android.goalmania;

import java.lang.ref.WeakReference;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.model.AbstractImageModel;

public class ZoomActivity extends CustomFragment implements OnClickListener {

	private AbstractImageModel model;
	private ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_zoom);
		super.onCreate(savedInstanceState);

//		initInterface();
//		getIntentData();
		processActivity();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		finish();
	}

	@Override
	protected void initInterface() {
		imageView = (ImageView) findViewById(R.id.zoom_image);
	}

	@Override
	protected void getIntentData() {
		this.model = (AbstractImageModel) getIntent().getExtras()
				.getSerializable("JerseyValue");
	}

	@Override
	protected void putIntentData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object loadDataBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void processActivity() {
		int ressourceId = this.getResources().getIdentifier(
				this.model.getImageName(),
				this.getResources().getString(R.string.drawable_folder),
				this.getPackageName());

		if (ressourceId != 0) {
			try {
				WeakReference<Drawable> reference = new WeakReference<Drawable>(
						this.getResources().getDrawable(ressourceId));

				imageView.setImageDrawable(reference.get());

				imageView.setOnClickListener(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void initTitle(ActionBar bar) {
		bar.setTitle(R.string.jersey_title);
	}
}