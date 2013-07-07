package com.mac.android.goalmania;

import java.util.List;
import java.util.UUID;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.adapter.StableArrayAdapter;
import com.mac.android.goalmania.helper.DrawableRessourceHelper;
import com.mac.android.goalmania.model.JerseySize;
import com.mac.android.goalmania.model.JerseySleeves;
import com.mac.android.goalmania.model.OrderItem;

public class OrderDetailItemActivity extends CustomFragment {

	private ImageView imageJersey;
	private TextView nameJersey;
	private TextView typeJersey;
	private TextView yearJersey;
	private EditText flockingJersey;
	private EditText numberJersey;
	private Spinner listSizeJersey;
	private RadioGroup sleevesJersey;
	private Button buttonRemove;
	private Button buttonValidate;
	private OrderItem model;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setSlidingMenuContentId(R.layout.slidingmenu_menu_item);
		setLayoutIds(R.layout.slidingmenu_menu,
				R.layout.order_activity_item_detail);
		setAnimationDuration(300);
		setAnimationType(MENU_TYPE_SLIDEOVER);

		super.onCreate(savedInstanceState);

		processActivity();
	}

	@Override
	protected void initInterface() {
		imageJersey = (ImageView) this
				.findViewById(R.id.imageJerseyOrderDetailItem);
		nameJersey = (TextView) this
				.findViewById(R.id.nameJerseyOrderDetailItem);
		typeJersey = (TextView) this
				.findViewById(R.id.typeJerseyOrderDetailItem);
		yearJersey = (TextView) this
				.findViewById(R.id.yearJerseyOrderDetailItem);

		flockingJersey = (EditText) this
				.findViewById(R.id.editFlockingJerseyOrderDetailItem);
		numberJersey = (EditText) this
				.findViewById(R.id.editNumberJerseyOrderDetailItem);
		sleevesJersey = (RadioGroup) this.findViewById(R.id.sleevesGroupItem);
		listSizeJersey = (Spinner) this
				.findViewById(R.id.listSizeJerseyOrderDetailItem);

		buttonRemove = (Button) this
				.findViewById(R.id.buttonRemoveJerseyOrderDetailItem);
		buttonValidate = (Button) this
				.findViewById(R.id.buttonValidateJerseyOrderDetailItem);

	}

	@Override
	protected void getIntentData() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			model = (OrderItem) bundle.getSerializable("OrderItemDetail");

			handler = (Handler) ctx.getDatas("handler");
		}
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

		Resources resources = this.getResources();

		int ressourceId = resources.getIdentifier(model.getJersey()
				.getImageName(), resources.getString(R.string.drawable_folder),
				this.getApplicationContext().getPackageName());

		DrawableRessourceHelper.getDrawableImageViewById(imageJersey,
				ressourceId);

		nameJersey.setText(nameJersey.getText() + " : "
				+ model.getJersey().getImageTitle());
		typeJersey.setText(typeJersey.getText() + " : "
				+ model.getJersey().getType().name());
		yearJersey.setText(yearJersey.getText() + " : "
				+ model.getJersey().getYear());

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item,
				JerseySize.getStringValues());
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listSizeJersey.setAdapter(adapter);

		listSizeJersey.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				model.setSize(JerseySize.get(listSizeJersey.getSelectedItem()
						.toString()));

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		if (model.isValidate()) {
			flockingJersey.setText(flockingJersey.getText()
					+ model.getFlocking());
			numberJersey.setText(numberJersey.getText() + ""
					+ model.getNumber());

			RadioButton selectedRadioButton1 = (RadioButton) findViewById(R.id.sleevesShortItem);
			RadioButton selectedRadioButton2 = (RadioButton) findViewById(R.id.sleevesLargeItem);
			if (selectedRadioButton1.getText().toString()
					.equals(model.getSleeves().name())) {
				selectedRadioButton1.setChecked(true);
				selectedRadioButton2.setChecked(false);

			} else {
				selectedRadioButton1.setChecked(false);
				selectedRadioButton2.setChecked(true);
			}

			try {
				listSizeJersey.setSelection(JerseySize.indexOf(model.getSize()
						.value()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		buttonRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				OrderItem tempDeletedItem = getItemByRef(model.getRef());
				if (tempDeletedItem == null) {
					Toast.makeText(
							getApplicationContext(),
							"error : cannot delete order item ("
									+ model.getRef()
									+ ") is not Collectionable",
							Toast.LENGTH_SHORT).show();
					return;
				}
				tempDeletedItem.setValidate(false);
				boolean res = ctx.getOrder().getItems().remove(tempDeletedItem);

				if (res) {
					Message message = new Message();
					message.obj = tempDeletedItem;

					handler.handleMessage(message);
					finish();
				}else{
					System.exit(-1);
				}
			}

		});

		buttonValidate.setOnClickListener(new OnClickListener() {

			private boolean validate(OrderItem model) {
				return (isNotNullOrEmpty(model.getFlocking())
						&& isNotNullOrEmpty("" + model.getNumber())
						&& isNotNullOrEmpty(model.getSleeves().name()) && isNotNullOrEmpty(model
						.getSize().name()));
			}

			private boolean isNullOrEmpty(String value) {
				return (value == null || value.isEmpty() || value.equals("") || value.equals("null"));
			}

			private boolean isNotNullOrEmpty(String value) {
				return !isNullOrEmpty(value);
			}

			@Override
			public void onClick(View v) {

				OrderItem item = getItemByRef(model.getRef());
				item.setFlocking(flockingJersey.getText().toString());

				String number = numberJersey.getText().toString();

				if (isNotNullOrEmpty(number)) {
					item.setNumber(Integer.parseInt(number));
				}else{
					item.setNumber(null);
				}

				Object selectedSize = listSizeJersey.getSelectedItem();
				if (selectedSize != null) {
					item.setSize(JerseySize.get(selectedSize.toString()));
				}else{
					item.setSize(null);
				}

				int selectedId = sleevesJersey.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);

				item.setSleeves(JerseySleeves.valueOf(selectedRadioButton
						.getText().toString()));

				boolean isValid = validate(item);

				if (isValid) {
					item.setValidate(true);
					model = item;
					Message message = new Message();
					message.obj = model;
					handler.handleMessage(message);
					finish();
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Your order is not complete.\nPlease complete the order item !",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	private OrderItem getItemByRef(UUID uuid) {
		List<OrderItem> items = ctx.getOrder().getItems();
		OrderItem tempDeletedItem = null;
		for (OrderItem orderItem : items) {
			if (orderItem.getRef().equals(uuid)) {
				tempDeletedItem = orderItem;
				break;
			}
		}
		return tempDeletedItem;
	}

	@Override
	protected void initTitle(ActionBar bar) {
		bar.setTitle(R.string.order_item_title);
		bar.setLogo(this.getResources().getDrawable(
				R.drawable.icon_image_order_menu));
		bar.setSubtitle(R.string.order_subtitle);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
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

}
