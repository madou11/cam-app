package com.mac.android.goalmania;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.context.GoalmaniaContext;
import com.mac.android.goalmania.helper.DrawableRessourceHelper;
import com.mac.android.goalmania.model.Club;
import com.mac.android.goalmania.model.Jersey;
import com.mac.android.goalmania.model.Order;
import com.mac.android.goalmania.model.OrderItem;

public class JerseyDetailActivity extends GeneralFragmentActivity {

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jersey_detail);

		ctx = (GoalmaniaContext) getApplicationContext();

		this.model = (Club) getIntent().getExtras()
				.getSerializable("ClubValue");

		initTitle(ab);

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
		inflater.inflate(R.menu.jersey_detail_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.im_button_ajout:
			OrderItem orderItem = new OrderItem();
			orderItem.setJersey(mSectionsPagerAdapter.getDummySectionFragment().getJersey());
			Order order = ctx.getOrder();
			
			order.getItems().add(orderItem);
			Toast.makeText(getApplicationContext(),
					"le maillot est ajout� � votre commande ("+order.getItems().size()+")!", Toast.LENGTH_SHORT).show();
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
		public DummySectionFragment getDummySectionFragment() {
			return fragment;
		}

		public SectionsPagerAdapter(FragmentManager fm, List<Jersey> list) {
			super(fm);
			this.list = list;
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
		public Jersey getJersey() {
			return jersey;
		}

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

	protected class MyTabsListener implements ActionBar.TabListener {

		private Fragment fragment;

		public MyTabsListener() {
			// TODO Auto-generated constructor stub
		}

		public MyTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.add(com.actionbarsherlock.R.id.abs__action_bar_container,
					fragment, null);

		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

	}

}
