package com.mac.android.goalmania;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mac.android.goalmania.adapter.ImageAdapter;
import com.mac.android.goalmania.model.AbstractImageModel;
import com.mac.android.goalmania.model.Club;
import com.mac.android.goalmania.model.Collectionable;
import com.mac.android.goalmania.model.Football;
import com.mac.android.goalmania.model.Jersey;

public class GridViewActivity extends Activity {

	private static boolean isFirstLoad = true;
	private GridView gridView;
	private AbstractImageModel football;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview_generic);

		if (isFirstLoad) {
			try {
				football = loadDatas();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"error occuried during datas loading",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			football = (AbstractImageModel) getIntent().getExtras().getSerializable("GridViewListValue");
		}

		if (football instanceof Collectionable) {

			gridView = (GridView) findViewById(R.id.gridView1);

			gridView.setAdapter(new ImageAdapter(getApplicationContext(),
					(List<AbstractImageModel>) ((Collectionable) football).getItems()));

			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					if (!(football instanceof Club)) {
						isFirstLoad = false;
						Intent intent = new Intent(getApplicationContext(),	GridViewActivity.class);
						intent.putExtra("GridViewListValue", (Serializable) ((Collectionable) football).getItems().get(position));
						startActivity(intent);

						Toast.makeText(getApplicationContext(),	((TextView) v.findViewById(R.id.grid_item_label))
										.getText(), Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(getApplicationContext(),	ZoomActivity.class);
						intent.putExtra("GridViewListItemValue", (Serializable) ((Collectionable) football).getItems().get(position));
						startActivity(intent);
					}
				}
			});
		} else {
			Toast.makeText(getApplicationContext(), "error : " + football.getClass() + " is not Collectionable",
					Toast.LENGTH_SHORT).show();
		}
	}

	private Football loadDatas() throws Exception {
		Serializer serializer = new Persister();
		InputStream inputStream = this.getAssets().open(
				getResources().getString(R.string.jersey_resources));
		return serializer.read(Football.class, inputStream);
	}
}