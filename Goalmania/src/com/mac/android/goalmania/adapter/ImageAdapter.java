package com.mac.android.goalmania.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mac.android.goalmania.R;
import com.mac.android.goalmania.model.AbstractImageModel;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final List<AbstractImageModel> items;

	public ImageAdapter(Context context, List<AbstractImageModel> items) {
		this.context = context;
		this.items = items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(this.context);

			AbstractImageModel model = this.items.get(position);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.grid_template_item, null);

			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(model.getImageTitle());

			Resources resources = this.context.getResources();

			int ressourceId = resources.getIdentifier(model.getImageName(),
					"drawable", this.context.getPackageName());

			if (ressourceId != 0) {
				try {
					WeakReference<Drawable> reference = new WeakReference<Drawable>(
							resources.getDrawable(ressourceId));
					// WeakReference<Bitmap> reference = new
					// WeakReference<Bitmap>(
					// BitmapFactory.decodeResource(resources, ressourceId));

					// set image based on selected text
					ImageView imageView = (ImageView) gridView
							.findViewById(R.id.grid_item_image);

					imageView.setImageDrawable(reference.get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
