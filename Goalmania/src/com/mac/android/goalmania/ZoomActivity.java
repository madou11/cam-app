package com.mac.android.goalmania;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mac.android.goalmania.model.AbstractImageModel;

public class ZoomActivity extends Activity implements OnClickListener {

	private AbstractImageModel model;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom);

		
		this.model = (AbstractImageModel) getIntent().getExtras().getSerializable(
				"GridViewListItemValue");

		Resources resources = this.getResources();

		int ressourceId = resources.getIdentifier(this.model.getImageName(),
				resources.getString(R.string.drawable_folder),
				this.getPackageName());

		if (ressourceId != 0) {
			try {
				WeakReference<Drawable> reference = new WeakReference<Drawable>(
						resources.getDrawable(ressourceId));

				ImageView imageView = (ImageView) findViewById(R.id.zoom_image);

				imageView.setImageDrawable(reference.get());
				
				imageView.setOnClickListener(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}