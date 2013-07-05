package com.mac.android.goalmania;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.adapter.ImageAdapter;
import com.mac.android.goalmania.helper.DrawableRessourceHelper;
import com.mac.android.goalmania.model.AbstractImageModel;
import com.mac.android.goalmania.model.Championship;
import com.mac.android.goalmania.model.Club;
import com.mac.android.goalmania.model.Collectionable;
import com.mac.android.goalmania.model.Football;

public class GridViewActivity extends CustomFragment {

	private static boolean isFirstLoad = true;
	private GridView gridView;
	private AbstractImageModel model;
	private static Football football;
	private int positionId;
	protected View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_gridview_generic);
		setSlidingMenuContentId(R.layout.slidingmenu_menu_item);
		setLayoutIds(R.layout.slidingmenu_menu,
				R.layout.activity_gridview_generic);
		setAnimationDuration(300);
		setAnimationType(MENU_TYPE_SLIDEOVER);
		super.onCreate(savedInstanceState);

		// initInterface();
		//
		// getIntentData();
		//
		// initTitle(ab);

		if (model != null) {
			processActivity();
		}
	}

	protected void initTitle(ActionBar bar) {
		if (model != null) {
			if (model instanceof Football) {
				bar.setTitle(R.string.championship_title);
			} else if (model instanceof Championship) {
				bar.setTitle(R.string.club_title);
			}

			bar.setLogo(DrawableRessourceHelper.getDrawableByName(
					model.getImageName(), this));
			bar.setSubtitle(model.getImageTitle());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processActivity() {

		if (model instanceof Collectionable) {
			gridView.setAdapter(new ImageAdapter(getApplicationContext(),
					(List<AbstractImageModel>) ((Collectionable) model)
							.getItems()));

			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					if (ismIsLayoutShown()) {
						toggleMenu();
					} else {
						positionId = position;
						view = v;
						putIntentData();
					}
				}
			});
		} else {
			Toast.makeText(getApplicationContext(),
					"error : " + model.getClass() + " is not Collectionable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (model instanceof Football) {
				toggleMenu();
			} else {
				finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void initInterface() {
		gridView = (GridView) this.findViewById(R.id.gridView1);
	}

	@Override
	protected void getIntentData() {
		if (isFirstLoad) {
			model = (AbstractImageModel) currentModel;
		} else {
			Bundle bundle = getIntent().getExtras();
			if (bundle != null) {
				model = (AbstractImageModel) bundle
						.getSerializable("GridViewListValue");
			} else {
				model = (AbstractImageModel) loadDataBase();
			}
		}
	}

	@Override
	protected void putIntentData() {

		if (!(model instanceof Championship)) {
			isFirstLoad = false;
			Intent intent = new Intent(getApplicationContext(),
					GridViewActivity.class);
			intent.putExtra(
					"GridViewListValue",
					(Serializable) ((Collectionable) model).getItems().get(
							positionId));

			Toast.makeText(
					getApplicationContext(),
					((TextView) view.findViewById(R.id.grid_item_label))
							.getText(), Toast.LENGTH_SHORT).show();

			startActivity(intent);
		} else {

			Intent intent = new Intent(getApplicationContext(),
					JerseyDetailActivity.class);
			intent.putExtra("ClubValue", (Club) model.getItems()
					.get(positionId));
			startActivity(intent);
		}
	}

	@Override
	protected Object loadDataBase() {
		if (football == null) {

			InputStream inputStream = null;
			try {
				Serializer serializer = new Persister();
				inputStream = this.getAssets().open(
						getResources().getString(R.string.jersey_resources));

				football = serializer.read(Football.class, inputStream);

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"error occuried during datas loading",
						Toast.LENGTH_SHORT).show();
				football = null;
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return football;
	}

	



}