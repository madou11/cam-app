package com.mac.android.goalmania;

import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mac.android.goalmania.adapter.ImageAdapter;
import com.mac.android.goalmania.model.AbstractImageModel;
import com.mac.android.goalmania.model.Collectionable;
import com.mac.android.goalmania.model.Football;

public class MainActivity extends Activity {

	private static boolean isFirstLoad = true;
	private GridView gridView;
	private AbstractImageModel football;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview_generic);

	
		football =(AbstractImageModel) getIntent().getExtras().getSerializable("GridViewListValue");
			
		if (football instanceof Collectionable) {

			gridView = (GridView) findViewById(R.id.gridView1);

			gridView.setAdapter(new ImageAdapter(getApplicationContext(),
				(List<AbstractImageModel>) football.getItems()));

			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
					Toast.makeText(
							getApplicationContext(),
							((TextView) v.findViewById(R.id.grid_item_label))
									.getText(), Toast.LENGTH_SHORT).show();

				}
			});
		}else{
			Toast.makeText(
					getApplicationContext(),"error : " + football.getClass()
					 +" is not Collectionable", Toast.LENGTH_SHORT).show();
		}
	}

	private Football loadDatas() throws Exception {
		Serializer serializer = new Persister();
		InputStream inputStream = this.getAssets().open("resources.xml");
		return serializer.read(Football.class, inputStream);
	}
}