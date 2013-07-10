package com.mac.android.goalmania;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.adapter.LazyAdapter;
import com.mac.android.goalmania.context.GoalmaniaContext;
import com.mac.android.goalmania.model.OrderItem;
import com.mac.android.goalmania.utils.AsyncInvokeURLTask;
import com.mac.android.goalmania.utils.AsyncInvokeURLTask.OnPostExecuteListener;

public class OrderActivity extends CustomFragment {

	private ListView list;
	private LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setSlidingMenuContentId(R.layout.slidingmenu_menu_item);
		setLayoutIds(R.layout.slidingmenu_menu, R.layout.order_activity);
		setAnimationDuration(300);
		setAnimationType(MENU_TYPE_SLIDEOVER);

		super.onCreate(savedInstanceState);

		processActivity();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		if (orderIsValid()) {
			inflater.inflate(R.menu.validationorder_settings_menu, menu);
		} else {
			inflater.inflate(R.menu.settings_menu, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			toggleMenu();
			return true;
		case R.id.im_button_validate:
			// envoi ws
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void initInterface() {
		list = (ListView) findViewById(R.id.list_order);
	}

	@Override
	protected void getIntentData() {
		// TODO Auto-generated method stub

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

		final GoalmaniaContext ctx = (GoalmaniaContext) getApplicationContext();

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(this, ctx.getOrder().getItems());
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (ismIsLayoutShown()) {
					toggleMenu();
				} else {

					OrderItem item = ctx.getOrder().getItems().get(position);

					final Handler handler = new Handler() {

						public void handleMessage(Message msg) {
							OrderActivity.this.restartActivity();
						}
					};

					Intent intent = new Intent(getApplicationContext(),
							OrderDetailItemActivity.class);
					intent.putExtra("OrderItemDetail", (Serializable) item);

					ctx.putDatas("handler", handler);
					ctx.putDatas("OrderActivity", OrderActivity.this);
					startActivity(intent);
				}
			}
		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> a, View viw,
					int arg2, long arg3) {
				if (ismIsLayoutShown()) {
					toggleMenu();
				} else {

					AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
							OrderActivity.this);

					// Setting Dialog Title
					alertDialog2.setTitle("Envoi de la commande...");

					// Setting Dialog Message
					alertDialog2
							.setMessage("Voullez-vous envoyer votre commande ?");

					// Setting Icon to Dialog
					alertDialog2.setIcon(R.drawable.icon_upload_image);

					// Setting Positive "Yes" Btn
					alertDialog2.setPositiveButton("YES",
							new DialogInterface.OnClickListener() {
								public void onClick(final DialogInterface dialog,
										int which) {
									// Write your code here to execute after
									// dialog

									ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
											2);
									nameValuePairs.add(new BasicNameValuePair(
											"command", "do_get_shop_list"));
									nameValuePairs.add(new BasicNameValuePair(
											"arg1", String.valueOf(1)));

									final ProgressDialog simpleWaitDialog = new ProgressDialog(
											OrderActivity.this);

									AsyncInvokeURLTask task = null;
									try {
										task = new AsyncInvokeURLTask(
												nameValuePairs,
												new OnPostExecuteListener() {
													@Override
													public void onPostExecute(
															String result) {

														Toast.makeText(
																getApplicationContext(),
																"Envoi terminé avec succès",
																Toast.LENGTH_SHORT)
																.show();

														simpleWaitDialog
																.dismiss();
														dialog.cancel();
														
													}

													@Override
													public void onDoingBackground(
															String result) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onPreExecute() {
														simpleWaitDialog
																.show(OrderActivity.this,
																		"Envoi de la commande", "Patienter...");

													}
												});
									} catch (Exception e) {
										Toast.makeText(
												getApplicationContext(),
												"Echec envoi.\nProblème de transmission.",
												Toast.LENGTH_SHORT).show();
									}
									task.execute();
								}
							});
					// Setting Negative "NO" Btn
					alertDialog2.setNegativeButton("NO",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Write your code here to execute after
									// dialog
									Toast.makeText(getApplicationContext(),
											"You clicked on NO",
											Toast.LENGTH_SHORT).show();
									dialog.cancel();
								}
							});

					// Showing Alert Dialog
					alertDialog2.show();

				}
				return false;
			}
		});
	}

	@Override
	protected void initTitle(ActionBar bar) {
		bar.setTitle(R.string.order_title);
		bar.setLogo(this.getResources().getDrawable(
				R.drawable.icon_image_order_menu));
		bar.setSubtitle(R.string.order_subtitle);
	}

	private boolean orderIsValid() {
		List<OrderItem> orderItems = ctx.getOrder().getItems();

		if (orderItems == null || orderItems.size() == 0) {
			return false;
		}

		for (OrderItem orderItem : orderItems) {
			if (!orderItem.isValidate()) {
				return false;
			}
		}
		return true;
	}

}
