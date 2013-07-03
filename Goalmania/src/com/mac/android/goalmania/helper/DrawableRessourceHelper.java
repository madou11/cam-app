package com.mac.android.goalmania.helper;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.mac.android.goalmania.R;

public class DrawableRessourceHelper {

	public static ImageView getDrawableImageViewById(View view, int resourceId) {
		if(resourceId == 0){
			return null;
		}
		WeakReference<Drawable> reference = new WeakReference<Drawable>(view
				.getResources().getDrawable(resourceId));

		ImageView imageView = (ImageView) view;

		imageView.setImageDrawable(reference.get());
		return imageView;
	}
	
	
	public static ImageView getDrawableImageViewByName(View view, String resourceName) {
		Resources resources = view.getResources();
		int resourceId = view.getResources().getIdentifier(resourceName,
				resources.getString(R.string.drawable_folder), view.getContext().getPackageName());
	
		return getDrawableImageViewById(view, resourceId);
	}

	public static Drawable getDrawableByName( String name, Context context ) {
		int drawableResource = context.getResources().getIdentifier(
						name,
						"drawable",
						context.getPackageName());
		if ( drawableResource == 0 ) {
			throw new RuntimeException("Can't find drawable with name: " + name );
		}
		return context.getResources().getDrawable(drawableResource);
	}
	
//	public static int getDrawableIdByName( String name, Context context ) {
//		int drawableResource = context.getResources().getIdentifier(
//						name,
//						"drawable",
//						context.getPackageName());
//		if ( drawableResource == 0 ) {
//			throw new RuntimeException("Can't find drawable with name: " + name );
//		}
//		return drawableResource;
//	}

}
