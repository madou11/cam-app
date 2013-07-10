package com.mac.android.goalmania.adapter;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mac.android.goalmania.R;
import com.mac.android.goalmania.helper.DrawableRessourceHelper;
import com.mac.android.goalmania.model.OrderItem;

public class LazyAdapter extends BaseAdapter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3489988071989552199L;
	private Context context;
	private final List<OrderItem> items;
	private View listView = null;
	
	public List<OrderItem> getItems() {
		return items;
	}

	public LazyAdapter(Context context, List<OrderItem> items) {
		this.context = context;
		this.items = items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		

		if (convertView == null) {
			Resources resources = this.context.getResources();

			OrderItem model = this.items.get(position);

			listView = new View(this.context);
			if (model != null) {

				listView = inflater.inflate(R.layout.order_activity_item, null);

				TextView textViewTitle = (TextView) listView
						.findViewById(R.id.jersey_title);
				textViewTitle.setText(model.getJersey().getImageTitle());

				TextView textViewFlocking = (TextView) listView
						.findViewById(R.id.flocking);
				textViewFlocking.setText(resources.getString(R.string.order_flocking) + " " + model.getFlocking());

				TextView textViewNumber = (TextView) listView
						.findViewById(R.id.number);
				textViewNumber.setText(resources.getString(R.string.order_number) + " " + model.getNumber());

				TextView textViewSize = (TextView) listView
						.findViewById(R.id.size);
				if (model.getSize() != null) {					
					textViewSize.setText(resources.getString(R.string.order_size) + " " + model.getSize().value());
				}else{
					textViewSize.setText(resources.getString(R.string.order_size) + " " + null);
				}
				TextView textViewSleeves = (TextView) listView
						.findViewById(R.id.sleeves);
				if (model.getSleeves() != null) {
					textViewSleeves.setText(resources.getString(R.string.order_sleeves) + " " + model.getSleeves().name());
				}else{
					textViewSleeves.setText(resources.getString(R.string.order_sleeves) + " " + null);
				}

				ImageView imageViewJersey = (ImageView) listView
						.findViewById(R.id.order_jersey_image);
				int jerseyId = resources.getIdentifier(model.getJersey()
						.getImageName(), resources
						.getString(R.string.drawable_folder), this.context
						.getPackageName());

				DrawableRessourceHelper.getDrawableImageViewById(
						imageViewJersey, jerseyId);

				ImageView imageViewValidation = (ImageView) listView
						.findViewById(R.id.order_state_validation);

				int iconValidatorId;

				if (model.isValidate()) {
					iconValidatorId = R.string.icon_validate;
				} else {
					iconValidatorId = R.string.icon_error;
				}

				int imageViewValidationId = resources.getIdentifier(
						this.context.getResources().getString(iconValidatorId),
						resources.getString(R.string.drawable_folder),
						this.context.getPackageName());

				DrawableRessourceHelper.getDrawableImageViewById(
						imageViewValidation, imageViewValidationId);
			}

		} else {
			listView = (View) convertView;
		}

		return listView;
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
		return ((OrderItem) getItem(position)).getId();
	}

}
