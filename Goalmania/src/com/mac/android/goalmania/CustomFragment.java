package com.mac.android.goalmania;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ws.munday.slidingmenu.MarginAnimation;
import ws.munday.slidingmenu.R;
import ws.munday.slidingmenu.R.layout;
import ws.munday.slidingmenu.Utility;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mac.android.goalmania.context.GoalmaniaContext;
import com.mac.android.goalmania.model.MenuItemModel;

public abstract class CustomFragment extends SherlockFragmentActivity {
	public static final int MENU_TYPE_SLIDING = 1;
	public static final int MENU_TYPE_SLIDEOVER = 2;
	public static final int MENU_TYPE_PARALLAX = 3;

	public static final String SL_IMAGE = "SL_MENU_IMAGE";
	public static final String SL_TITLE = "SL_MENU_TITLE";
	public static final String SL_DESCRIPTION = "SL_MENU_DESCRIPTION";
	public static final String SL_ACTIVITY_NAME = "SL_MENU_ACTIVITY_NAME";

	public static GoalmaniaContext ctx;

	protected Object currentModel;
	protected ActionBar ab;

	protected ListView slidingMenuList;
	private int smContentId;

	private boolean mIsLayoutShown = false;
	private int mMenuWidth;
	private int mMenuLayoutId;
	private int mContentLayoutId;
	private long mAnimationDuration = 400;
	private int mMaxMenuWidthDps = 600;// 375

	public int getmMaxMenuWidthDps() {
		switch (this.getResources().getConfiguration().orientation) {

		case Configuration.ORIENTATION_PORTRAIT:
			mMaxMenuWidthDps = 600;
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			mMaxMenuWidthDps = 1170;
			break;
		default:
			mMaxMenuWidthDps = 600;
			break;
		}
		return mMaxMenuWidthDps;
	}

	private int mMinMainWidthDps = 50;
	private Interpolator mInterpolator = new DecelerateInterpolator(1.4f);
	private int mType = MENU_TYPE_SLIDING;
	private boolean mSlideTitleBar = true;

	public CustomFragment() {
		this(true);
	}

	public CustomFragment(boolean slideTitleBar) {
		mSlideTitleBar = slideTitleBar;
	}

	protected final void restartActivity() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	protected ActionBar setActionBar() {
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		return mActionBar;
	}

	protected void showKeyBoard(View v) {
		v.requestFocus();
		getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	protected float convertPixelToDip(int pixel) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel,
				getResources().getDisplayMetrics());
	}

	protected void setVisibilityMenuOption(Menu menu, int id, boolean visible) {
		MenuItem item = menu.findItem(id);
		item.setVisible(visible);
	}

	protected void addMenuOption(Menu menu, int id, int order, int idString,
			int idDrawable, boolean visible, int action) {
		menu.add(0, id, order, getString(idString)).setIcon(idDrawable)
				.setVisible(visible).setShowAsAction(action);
	}

	protected abstract void initInterface();

	protected abstract void getIntentData();

	protected abstract void putIntentData();

	protected abstract Object loadDataBase();

	protected abstract void processActivity();

	protected abstract void initTitle(ActionBar bar);

	public void setLayoutIds(int menuLayoutId, int contentLayoutId) {
		mMenuLayoutId = menuLayoutId;
		mContentLayoutId = contentLayoutId;
	}

	public void setSlidingMenuContentId(int slidingMenuContentId) {
		smContentId = slidingMenuContentId;
	}

	public void setAnimationDuration(long duration) {
		mAnimationDuration = duration;
	}

	public void setMaxMenuWidth(int width) {
		mMaxMenuWidthDps = width;
	}

	public void setMinContentWidth(int width) {
		mMinMainWidthDps = width;
	}

	public void setAnimationType(int type) {
		mType = type;
	}

	public Interpolator getInterpolator() {
		return mInterpolator;
	}

	public void setInterpolator(Interpolator i) {
		mInterpolator = i;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = (GoalmaniaContext) getApplicationContext();

		ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		if (mContentLayoutId != 0) {
			if (!mSlideTitleBar) {

				setContentView(R.layout.ws_munday_slideovermenu);

				ViewGroup menu = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
				ViewGroup content = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_content_frame);

				LayoutInflater li = getLayoutInflater();

				content.addView(li.inflate(mContentLayoutId, null));
				menu.addView(li.inflate(mMenuLayoutId, null));

				menu.setVisibility(View.GONE);

				content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ismIsLayoutShown()) {
							toggleMenu();
						}
					}
				});

			} else {

				setContentView(mContentLayoutId);
				Window window = getWindow();

				ViewGroup decor = (ViewGroup) window.getDecorView();
				ViewGroup allcontent = (ViewGroup) decor.getChildAt(0);
				decor.removeView(allcontent);

				LayoutInflater li = getLayoutInflater();

				RelativeLayout main = (RelativeLayout) li.inflate(
						layout.ws_munday_slideovermenu, null);

				ViewGroup menu = (ViewGroup) main
						.findViewById(R.id.ws_munday_slidingmenu_menu_frame);
				ViewGroup content = (ViewGroup) main
						.findViewById(R.id.ws_munday_slidingmenu_content_frame);

				int statusbarHeight = (int) Utility.getTopStatusBarHeight(
						getResources(), getWindowManager());

				ViewGroup mnu = (ViewGroup) li.inflate(mMenuLayoutId, null);
				mnu.setPadding(mnu.getPaddingLeft(), mnu.getPaddingTop()
						+ statusbarHeight, mnu.getPaddingRight(),
						mnu.getPaddingTop());
				content.addView(allcontent);
				content.setBackgroundDrawable(Utility.getThemeBackground(this));
				menu.addView(mnu);

				decor.addView(main);
				menu.setVisibility(View.GONE);

				content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ismIsLayoutShown()) {
							toggleMenu();
						}
					}
				});
			}

			initMenu(false);
		}

		if (mMenuLayoutId != 0 && smContentId != 0) {
			initSlidingMenu();
		}

		currentModel = loadDataBase();

		initInterface();

		getIntentData();

		initTitle(ab);
	}

	private void initSlidingMenu() {
		// Récupération de la listview créée dans le fichier main.xml
		slidingMenuList = (ListView) findViewById(com.mac.android.goalmania.R.id.listview_menu);

		List<MenuItemModel> menuItems = getMenuListItem();

		// Création de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		for (MenuItemModel menuItemModel : menuItems) {

			// Création d'une HashMap pour insérer les informations du premier
			// item
			// de notre listView
			HashMap<String, String> map = new HashMap<String, String>();
			// on insère un élément titre que l'on récupérera dans le textView
			// titre
			// créé dans le fichier affichageitem.xml
			map.put(SL_TITLE, menuItemModel.getTitle());
			// on insère un élément description que l'on récupérera dans le
			// textView
			// description créé dans le fichier affichageitem.xml
			map.put(SL_ACTIVITY_NAME, menuItemModel.getActivityName());
			// on insère un élément description que l'on récupérera dans le
			// textView
			// description créé dans le fichier affichageitem.xml
			map.put(SL_DESCRIPTION, menuItemModel.getDescription());
			// on insère la référence à l'image (convertit en String car
			// normalement
			// c'est un int) que l'on récupérera dans l'imageView créé dans le
			// fichier affichageitem.xml
			map.put(SL_IMAGE, String.valueOf(menuItemModel.getImageId()));
			// enfin on ajoute cette hashMap dans la arrayList
			listItem.add(map);
		}

		// Création d'un SimpleAdapter qui se chargera de mettre les items
		// présent dans notre list (listItem) dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(
				this.getBaseContext(),
				listItem,
				smContentId,
				new String[] { SL_IMAGE, SL_TITLE, SL_DESCRIPTION },
				new int[] { com.mac.android.goalmania.R.id.sliding_menu_img,
						com.mac.android.goalmania.R.id.sliding_menu_titre,
						com.mac.android.goalmania.R.id.sliding_menu_description });

		// On attribut à notre listView l'adapter que l'on vient de créer
		slidingMenuList.setAdapter(mSchedule);

		// Enfin on met un écouteur d'évènement sur notre listView
		slidingMenuList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				// on récupère la HashMap contenant les infos de notre item
				// (titre, description, img)
				HashMap<String, String> map = (HashMap<String, String>) slidingMenuList
						.getItemAtPosition(position);

				MenuItemModel itemModel = new MenuItemModel(map
						.get(SL_ACTIVITY_NAME), Integer.parseInt(map
						.get(SL_IMAGE)), map.get(SL_TITLE), map
						.get(SL_DESCRIPTION));

				onSlidingMenuClick(a, v, position, id, itemModel);

				if (ismIsLayoutShown()) {
					toggleMenu();
				}
			}
		});
	}

	// protected abstract void onSlidingMenuClick(AdapterView<?> a, View v, int
	// position,
	// long id, MenuItemModel itemModel) ;

	protected void onSlidingMenuClick(AdapterView<?> a, View v, int position,
			long id, MenuItemModel itemModel) {

		try {

			ClassLoader cl = Thread.currentThread().getContextClassLoader();

			Class<?> clazz = cl.loadClass(itemModel.getActivityName());

			if (!this.getClass().equals(clazz)) {
				Intent intent = new Intent(getApplicationContext(), clazz);
				startActivity(intent);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	protected List<MenuItemModel> getMenuListItem() {
		MenuItemModel menuItem1 = new MenuItemModel(
				"com.mac.android.goalmania.GridViewActivity",
				com.mac.android.goalmania.R.drawable.icon_search_home_menu,
				"Accueil", "liste des maillots");
		MenuItemModel menuItem2 = new MenuItemModel(
				"com.mac.android.goalmania.OrderActivity",
				com.mac.android.goalmania.R.drawable.icon_order_caddie_image_menu,
				"Commande", "mes maillots");
		MenuItemModel menuItem3 = new MenuItemModel(
				"com.mac.android.goalmania.OrderActivity",
				com.mac.android.goalmania.R.drawable.icon_history_menu,
				"Historique", "mes commandes");

		List<MenuItemModel> menuItems = new ArrayList<MenuItemModel>();
		menuItems.add(menuItem1);
		menuItems.add(menuItem2);
		menuItems.add(menuItem3);
		return menuItems;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		initMenu(true);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			toggleMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (mIsLayoutShown) {
			toggleMenu();
		} else {
			super.onBackPressed();
		}
	}

	public void toggleMenu() {

		switch (mType) {
		case MENU_TYPE_SLIDEOVER:
			toggleSlideOverMenu();
			break;
		case MENU_TYPE_PARALLAX:
			toggleSlidingMenu(mAnimationDuration / 2);
			break;
		default: /* MENU_TYPE_SLIDING */
			toggleSlidingMenu();
			break;
		}
	}

	public void toggleSlideOverMenu() {

		View v2 = findViewById(R.id.ws_munday_slidingmenu_content_frame);
		v2.clearAnimation();
		v2.setDrawingCacheEnabled(true);

		if (mIsLayoutShown) {
			MarginAnimation a = new MarginAnimation(v2, mMenuWidth, 0,
					mInterpolator);
			a.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					ViewGroup v1 = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
					v1.setVisibility(View.GONE);
				}
			});

			a.setDuration(mAnimationDuration);
			v2.startAnimation(a);
		} else {
			MarginAnimation a = new MarginAnimation(v2, 0, mMenuWidth,
					mInterpolator);

			a.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
					ViewGroup v1 = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
					v1.setVisibility(View.VISIBLE);
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
				}
			});

			a.setDuration(mAnimationDuration);
			v2.startAnimation(a);
		}

		mIsLayoutShown = !mIsLayoutShown;

	}

	public void toggleSlidingMenu() {
		toggleSlidingMenu(mAnimationDuration);
	}

	public void toggleSlidingMenu(long menuAnimationDuration) {

		boolean parallax = menuAnimationDuration != mAnimationDuration;

		View v2 = findViewById(R.id.ws_munday_slidingmenu_content_frame);
		v2.clearAnimation();
		v2.setDrawingCacheEnabled(true);

		View vMenu = findViewById(R.id.ws_munday_slidingmenu_menu_frame);
		vMenu.clearAnimation();
		vMenu.setDrawingCacheEnabled(true);

		if (mIsLayoutShown) {

			MarginAnimation a = new MarginAnimation(v2, mMenuWidth, 0,
					mInterpolator);
			a.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					ViewGroup v1 = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
					v1.setVisibility(View.GONE);
				}
			});

			a.setDuration(menuAnimationDuration);
			v2.startAnimation(a);

			if (parallax) {
				MarginAnimation a2 = new MarginAnimation(vMenu, 0, -mMenuWidth,
						mInterpolator);
				a2.setDuration(mAnimationDuration);
				vMenu.startAnimation(a2);
			}
		} else {

			MarginAnimation a = new MarginAnimation(v2, 0, mMenuWidth,
					mInterpolator);
			a.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
					ViewGroup v1 = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
					v1.setVisibility(View.VISIBLE);
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
				}
			});

			a.setDuration(mAnimationDuration);
			v2.startAnimation(a);

			if (parallax) {
				MarginAnimation a2 = new MarginAnimation(vMenu, -mMenuWidth, 0,
						mInterpolator);
				a2.setDuration(menuAnimationDuration);
				vMenu.startAnimation(a2);
			}
		}

		mIsLayoutShown = !mIsLayoutShown;

	}

	public void initMenu(boolean isConfigChange) {

		switch (mType) {

		case MENU_TYPE_SLIDEOVER:
			initSlideOverMenu(isConfigChange);
			break;

		default:
			initSlideOutMenu(isConfigChange);
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public void initSlideOutMenu(boolean isConfigChange) {
		// get menu and main layout
		FrameLayout menu = (FrameLayout) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
		FrameLayout root = (FrameLayout) findViewById(R.id.ws_munday_slidingmenu_content_frame);

		// get screen width
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int x = 0;
		try {
			Method m = Display.class.getMethod("getSize", new Class[] {});
			m.invoke(display, size);
			x = size.x;
		} catch (NoSuchMethodException nsme) {
			x = display.getWidth();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// make sure that the content doesn't slide all the way off screen
		int minContentWidth = Utility.dipsToPixels(this, mMinMainWidthDps);
		mMenuWidth = Math.min(x - minContentWidth, getmMaxMenuWidthDps());

		// update sizes and margins for sliding menu
		RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams(
				mMenuWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(x,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		if (isConfigChange) {
			if (mIsLayoutShown) {
				mp.leftMargin = 0;
				rp.leftMargin = mMenuWidth;
				rp.rightMargin = -mMenuWidth;
			} else {
				mp.leftMargin = -mMenuWidth;
				rp.leftMargin = 0;
				rp.rightMargin = 0;
			}
		} else {
			mp.leftMargin = -mMenuWidth;
			rp.leftMargin = 0;
			rp.rightMargin = -mMenuWidth;
			mIsLayoutShown = false;
		}

		menu.setLayoutParams(mp);
		menu.requestLayout();

		root.setLayoutParams(rp);
		root.requestLayout();
	}

	public boolean ismIsLayoutShown() {
		return mIsLayoutShown;
	}

	@SuppressWarnings("deprecation")
	public void initSlideOverMenu(boolean isConfigChange) {
		// get menu and main layout
		ViewGroup menu = (ViewGroup) findViewById(R.id.ws_munday_slidingmenu_menu_frame);
		// ViewGroup content = (ViewGroup)
		// findViewById(R.id.ws_munday_slidingmenu_content_frame);

		// get screen width
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int x = 0;
		try {
			Method m = Display.class.getMethod("getSize", new Class[] {});
			m.invoke(display, size);
			x = size.x;
		} catch (NoSuchMethodException nsme) {
			x = display.getWidth();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// make sure that the content doesn't slide all the way off screen
		int minContentWidth = Utility.dipsToPixels(this, mMinMainWidthDps);
		mMenuWidth = Math.min(x - minContentWidth, getmMaxMenuWidthDps());

		// update sizes and margins for sliding menu
		menu.setLayoutParams(new RelativeLayout.LayoutParams(mMenuWidth,
				RelativeLayout.LayoutParams.MATCH_PARENT));
		// menu.requestLayout();

		if (isConfigChange) {
			mIsLayoutShown = !mIsLayoutShown;
			toggleMenu();
		}
	}

	public static class DialogHelper {

		private static DialogHelper self;
		
		public static DialogHelper getIstance(){
			if(self == null){
				self = new DialogHelper();
			}
			return self;
		}
		private DialogHelper() {
			// TODO Auto-generated constructor stub
		}
		
		public class DialogProperties {
			String title;
			String message;
			Boolean cancelable;
			DialogOnClickListener okListener;
			DialogOnClickListener cancelListener;

			public DialogProperties(String title, String message,
					Boolean cancelable, DialogOnClickListener okListener,
					DialogOnClickListener cancelListener) {
				super();
				this.title = title;
				this.message = message;
				this.cancelable = cancelable;
				this.okListener = okListener;
				this.cancelListener = cancelListener;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public Boolean isCancelable() {
				return cancelable;
			}

			public void setCancelable(Boolean cancelable) {
				this.cancelable = cancelable;
			}

			public DialogOnClickListener getOkListener() {
				return okListener;
			}

			public void setOkListener(DialogOnClickListener listener) {
				this.okListener = listener;
			}

			public DialogOnClickListener getCancelListener() {
				return cancelListener;
			}

			public void setCancelListener(DialogOnClickListener cancelListener) {
				this.cancelListener = cancelListener;
			}

		}
		
		public static  Dialog onCreateDialog(int id,
				DialogProperties dialogProperties,
				OnContentLayout onContentLayout) {
			
			return onCreateDialog(id, -1, dialogProperties, onContentLayout);
		}

		public static Dialog onCreateDialog(int id, int layoutId,
				DialogProperties dialogProperties,
				OnContentLayout onContentLayout) {
			Dialog dialog = null;
			switch (id) {
			case 1:
				// Create out AlterDialog
				AlertDialog.Builder builder = new AlertDialog.Builder(CustomFragment.ctx.getApplicationContext());
				builder.setMessage(dialogProperties.getMessage());
				builder.setTitle(dialogProperties.getTitle());
				builder.setCancelable(dialogProperties.isCancelable());
				builder.setPositiveButton(dialogProperties.getOkListener()
						.getLabel(), dialogProperties.getOkListener());
				builder.setNegativeButton(dialogProperties.getCancelListener()
						.getLabel(), dialogProperties.getCancelListener());
				dialog = builder.create();
				
				if(layoutId != -1){
					dialog.setContentView(layoutId);
				}
				onContentLayout.dialogContent(dialog);

				dialog.show();
			}
			return dialog;
		}

		public interface OnContentLayout {
			void dialogContent(Dialog dialog);
		}
		public interface OnClickDialog{
			void onClickDialog(DialogInterface dialog, int which);
			}

		public class DialogOnClickListener implements
				DialogInterface.OnClickListener {

			private String label;
			private OnClickDialog clickDialog;
			private Activity activity;

			public DialogOnClickListener(OnClickDialog clickDialog, Activity activity, String label) {
				this.label = label;
				this.activity = activity;
				this.clickDialog = clickDialog;
			}

			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(ctx, "Activity will continue", Toast.LENGTH_LONG)
						.show();
				clickDialog.onClickDialog(dialog, which);

			}
			
			public Activity getActivity() {
				return activity;
			}
			
			public String getLabel() {
				return label;
			}
			
			/**
			 * @return the clickDialog
			 */
			public OnClickDialog getClickDialog() {
				return clickDialog;
			}
		}
	}
}
