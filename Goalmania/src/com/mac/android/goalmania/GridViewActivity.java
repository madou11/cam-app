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

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.adapter.ImageAdapter;
import com.mac.android.goalmania.model.AbstractImageModel;
import com.mac.android.goalmania.model.Club;
import com.mac.android.goalmania.model.Collectionable;
import com.mac.android.goalmania.model.Football;

public class GridViewActivity extends GeneralActivity {

	private static boolean isFirstLoad = true;
	private GridView gridView;
	private AbstractImageModel model;
	private int positionId;
	protected View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview_generic);

		initInterface();

		getIntentData();
		
		if (model != null) {
			processActivity();
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

					positionId = position;
					view = v;
					putIntentData();

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
		inflater.inflate(R.menu.default_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
			// case R.id.im_button_ajout:
			// Intent intent = new Intent(DetailRepasActivity.this,
			// AjoutAlimentRepas.class);
			// intent.putExtra("fr.calfast.idRepas", idRepas);
			// startActivityForResult(intent, 1);
			// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void initInterface() {
		gridView = (GridView) findViewById(R.id.gridView1);

	}

	@Override
	protected void getIntentData() {
		if (isFirstLoad) {
			try {
				model = (AbstractImageModel) loadDataBase();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"error occuried during datas loading",
						Toast.LENGTH_SHORT).show();
			}
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

		if (!(model instanceof Club)) {
			isFirstLoad = false;
			Intent intent = new Intent(getApplicationContext(),
					GridViewActivity.class);
			intent.putExtra(
					"GridViewListValue",
					(Serializable) ((Collectionable) model).getItems().get(
							positionId));
			startActivity(intent);

			Toast.makeText(
					getApplicationContext(),
					((TextView) view.findViewById(R.id.grid_item_label))
							.getText(), Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(getApplicationContext(),
					ZoomActivity.class);
			intent.putExtra(
					"GridViewListItemValue",
					(Serializable) ((Collectionable) model).getItems().get(
							positionId));
			startActivity(intent);

		}
	}

	@Override
	protected Object loadDataBase() {
		InputStream inputStream = null;
		try {
			Serializer serializer = new Persister();
			inputStream = this.getAssets().open(
					getResources().getString(R.string.jersey_resources));

			return serializer.read(Football.class, inputStream);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"error : cannot load datas", Toast.LENGTH_SHORT).show();
			return null;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

}