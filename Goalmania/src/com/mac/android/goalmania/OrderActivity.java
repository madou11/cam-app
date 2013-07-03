package com.mac.android.goalmania;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.adapter.LazyAdapter;
import com.mac.android.goalmania.context.GoalmaniaContext;
import com.mac.android.goalmania.model.MenuItemModel;

public class OrderActivity extends CustomFragment {

	private ListView list;
	private LazyAdapter adapter;

	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

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
		inflater.inflate(R.menu.settings_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggleMenu();
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

		GoalmaniaContext ctx = (GoalmaniaContext) getApplicationContext();

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(this, ctx.getOrder().getItems());
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				System.out.println("bonjour");
			}
		});
	}

	@Override
	protected void initTitle(ActionBar bar) {
		bar.setTitle(R.string.order_title);
		bar.setLogo(this.getResources().getDrawable(R.drawable.icon_image_order_menu));
		bar.setSubtitle(R.string.order_subtitle);
	}

	@Override
	protected void onSlidingMenuClick(AdapterView<?> a, View v, int position,
			long id, MenuItemModel itemModel) {
		super.onSlidingMenuClick(a, v, position, id, itemModel);
	}

}
