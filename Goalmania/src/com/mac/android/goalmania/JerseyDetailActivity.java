package com.mac.android.goalmania;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.context.GoalmaniaContext;
import com.mac.android.goalmania.helper.DrawableRessourceHelper;
import com.mac.android.goalmania.model.Club;
import com.mac.android.goalmania.model.Jersey;
import com.mac.android.goalmania.model.MenuItemModel;
import com.mac.android.goalmania.model.Order;
import com.mac.android.goalmania.model.OrderItem;

public class JerseyDetailActivity extends CustomFragment {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private Club model;

	private static GoalmaniaContext ctx;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setSlidingMenuContentId(R.layout.slidingmenu_menu_item);
		setLayoutIds(R.layout.slidingmenu_menu,
				R.layout.activity_jersey_detail);
		setAnimationDuration(300);
		setAnimationType(MENU_TYPE_SLIDEOVER);
		
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_jersey_detail);

		ctx = (GoalmaniaContext) getApplicationContext();

//		getIntentData();
//
//		initTitle(ab);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), this.model.getJerseys());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.addorder_settings_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.im_button_ajout:
			OrderItem orderItem = new OrderItem();
			orderItem.setJersey(mSectionsPagerAdapter.getCurrentItem());
			Order order = ctx.getOrder();
			
			order.getItems().add(orderItem);
			Toast.makeText(getApplicationContext(),
					"le maillot est ajouté à votre commande ("+order.getItems().size()+")!", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private List<Jersey> list;
		private DummySectionFragment fragment;
		

		public SectionsPagerAdapter(FragmentManager fm, List<Jersey> list) {
			super(fm);
			this.list = list;
		}
		
		
		public Jersey getCurrentItem(){
			int index = mViewPager.getCurrentItem();
			return list.get(index);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putSerializable("Jersey", list.get(position));
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return model.getJerseys().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			return model.getJerseys().get(position).getImageTitle()
					.toUpperCase(l);
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends SherlockFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private Jersey jersey;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_jersey_detail_dummy, container, false);

			Bundle bundle = getArguments();
			jersey = (Jersey) bundle.getSerializable("Jersey");
			TextView yearTextView = (TextView) rootView
					.findViewById(R.id.year_jersey_value);
			yearTextView.setText(jersey.getYear());

			TextView typeTextView = (TextView) rootView
					.findViewById(R.id.type_jersey_value);
			typeTextView.setText(jersey.getType().name());

			TextView teamTextView = (TextView) rootView
					.findViewById(R.id.team_name_value);
			teamTextView.setText(jersey.getImageTitle());

			ImageView imageView = (ImageView) rootView
					.findViewById(R.id.imageJerseyDetail);

			ImageView result = DrawableRessourceHelper
					.getDrawableImageViewByName(imageView,
							jersey.getImageName());
			if (result != null) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(ctx, ZoomActivity.class);
						intent.putExtra("JerseyValue", jersey);
						startActivity(intent);
					}
				});
			}
			return rootView;
		}
	}

	@Override
	protected void initInterface() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void getIntentData() {
		this.model = (Club) getIntent().getExtras()
				.getSerializable("ClubValue");
	}

	@Override
	protected void putIntentData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object loadDataBase() {
	return null;
	}

	@Override
	protected void processActivity() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initTitle(ActionBar bar) {
		bar.setTitle(R.string.jersey_title);
		if (model != null) {
			bar.setLogo(DrawableRessourceHelper.getDrawableByName(
					model.getImageName(), this));
			bar.setSubtitle(model.getImageTitle());

			// bar.addTab(bar.newTab().setText("mac"));
		}
	}
	
	@Override
	protected void onSlidingMenuClick(AdapterView<?> a, View v, int position,
			long id, MenuItemModel itemModel) {
		System.out.println("popo");
	}
}
