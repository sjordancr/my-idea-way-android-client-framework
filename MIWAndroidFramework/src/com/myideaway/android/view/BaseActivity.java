package com.myideaway.android.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.myideaway.android.R;
import com.myideaway.android.common.exception.RemoteServiceException;
import com.myideaway.android.util.LogUtil;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseActivity extends Activity {
	public static final int TOOLBAR_MODE_FILL = 1;
	public static final int TOOLBAR_MODE_OVERLAP = 2;

	protected LayoutInflater layoutInflater;
	protected Handler handler;
	protected RelativeLayout rootRelativeLayout;
	protected RelativeLayout mainRelativeLayout;
	protected NavigationBar navigationBar;
	protected ToolBar toolBar;
	protected TipMessageBar tipMessageBar;
	protected View lockMainViewMask;
	protected ProgressDialog progressDialog;
	protected AlertDialog alertDialog;
	protected Toast toast;
	protected Timer hideTipMessageTimer;
	protected Context mContext;
	protected Activity mActivity;
	protected int toolbarMode = TOOLBAR_MODE_FILL;
	private boolean isFullScreen;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogUtil.setTag(getPackageName());

		// 初始化基本字段
		initBasic();

		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置方向竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// 加载根布局
		handler = new Handler();
		layoutInflater = LayoutInflater.from(this);
		rootRelativeLayout = (RelativeLayout) layoutInflater.inflate(
				R.layout.base, null);
		setContentView(rootRelativeLayout);

		mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);

		// 加载导航栏
		navigationBar = (NavigationBar) findViewById(R.id.navigationBar);

		// 加载工具栏
		toolBar = (ToolBar) findViewById(R.id.toolBar);

		// 加载提示栏
		tipMessageBar = (TipMessageBar) findViewById(R.id.tipMessageBar);

		// 加载主视图锁定窗口
		lockMainViewMask = findViewById(R.id.lockMainViewMask);
		lockMainViewMask.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		// 创建Toast对话框
		toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);

		// 创建主试图
		createMainView();

		// 将工具栏移动到最上层。
		toolBar.bringToFront();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();
	}

    private void initBasic() {
		mContext = this;
		mActivity = this;

	}

	protected void setMainView(int resId) {
		layoutInflater.inflate(resId, mainRelativeLayout);
	}

	protected void createMainView() {
		onCreateMainView();

		// 加载自定义工具栏
		View customToolBar = findViewById(R.id.customToolBar);
		if (customToolBar != null) {
			// 先将原来的自定义工具栏从父控件移除
			Method parentRemoveChildMethod;
			try {
				parentRemoveChildMethod = customToolBar.getParent().getClass()
						.getMethod("removeView", View.class);
				parentRemoveChildMethod.invoke(customToolBar.getParent(),
						customToolBar);
			} catch (Exception e) {
				LogUtil.error("Can't add custom too bar", e);
			}

			// 将自定义工具栏添加到原有工具栏中
			toolBar.addContentView(customToolBar);

		}

	}

	public void setLocale(Locale newLocale) {

		// 更新默认语言
		Locale.setDefault(newLocale);

		Configuration config = getBaseContext().getResources()
				.getConfiguration();

		config.locale = newLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	public void showProgressDialog(String message) {
		// 创建进度等待对话框
		progressDialog = new ProgressDialog(this);
		
		progressDialog.setMessage(message);

		progressDialog.show();
	}

	public void hideProgressDialog() {
		progressDialog.dismiss();
	}

	public void showAlertDialog(String message,
			final DialogInterface.OnClickListener okListener,
			final DialogInterface.OnClickListener noListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setTitle(null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (okListener != null) {
					okListener.onClick(dialog, which);
				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (noListener != null) {
					noListener.onClick(dialog, which);
				}
			}

		});

		alertDialog = builder.show();
	}

	public void showAlertDialog(String message,
			final DialogInterface.OnClickListener okListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setTitle(null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (okListener != null) {
					okListener.onClick(dialog, which);
				}
			}
		});

		alertDialog = builder.show();
	}

	public void showAlertDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setTitle(null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog = builder.show();
	}

	public void showToastMessage(String message) {
		toast.setText(message);
		toast.show();
	}

	public void hideToastMessage() {
		toast.cancel();
	}

	public void showTipMessage(String message, int type) {
		stopHideTipMessageTimer();

		tipMessageBar.setTipIconType(type);
		tipMessageBar.setMessage(message);

		if (tipMessageBar.getVisibility() == View.VISIBLE) {
			return;
		}

		tipMessageBar.setVisibility(View.VISIBLE);

		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.tip_message_bar_show);
		tipMessageBar.startAnimation(anim);
	}

	public void showTipMessageAndHide(String message, int type, int delay) {
		showTipMessage(message, type);
		hideTipMessage(delay);
	}

	public void showTipMessageAndHide(String message, int type) {
		showTipMessage(message, type);
		hideTipMessage(3000);
	}

	public void showLoadingNewDataTipMessageAndHide() {
		showTipMessageAndHide(getString(R.string.loading_new_data),
				TipMessageBar.TIP_ICON_TYPE_PROGRESS);
	}

	public void showRefreshingTipMessageAndHide() {
		showTipMessageAndHide(getString(R.string.refreshing),
				TipMessageBar.TIP_ICON_TYPE_PROGRESS);
	}

	public void showFaultTipMessageAndHide(String msg) {
		showTipMessageAndHide(msg, TipMessageBar.TIP_ICON_TYPE_FAULT);
	}

	public void showSuccessTipMessageAndHide(String msg) {
		showTipMessageAndHide(msg, TipMessageBar.TIP_ICON_TYPE_SUCCESS);
	}

	public void hideTipMessage() {

		if (tipMessageBar.getVisibility() == View.GONE) {
			return;
		}

		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.tip_message_bar_hide);
		tipMessageBar.startAnimation(anim);
		tipMessageBar.setVisibility(View.GONE);
	}

	public void hideTipMessage(int delay) {

		if (tipMessageBar.getVisibility() == View.GONE) {
			return;
		}

		startHideTipMessageTimer(delay);
	}

	public void startHideTipMessageTimer(int delay) {
		stopHideTipMessageTimer();

		hideTipMessageTimer = new Timer();
		hideTipMessageTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(new Runnable() {

					public void run() {
						hideTipMessage();
					}
				});

			}
		}, delay);
	}

	public void stopHideTipMessageTimer() {
		if (hideTipMessageTimer != null) {
			hideTipMessageTimer.cancel();
		}
	}

	public void lockMainView() {
		if (lockMainViewMask.getVisibility() == View.VISIBLE) {
			return;
		}

		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.lock_main_view_mask_show);
		lockMainViewMask.startAnimation(anim);
		lockMainViewMask.setVisibility(View.VISIBLE);
	}

	public void unlockMainView() {
		if (lockMainViewMask.getVisibility() == View.GONE) {
			return;
		}

		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.lock_main_view_mask_hide);
		lockMainViewMask.startAnimation(anim);
		lockMainViewMask.setVisibility(View.GONE);
	}

	public void showToolBar() {
		if (toolBar.getVisibility() == View.VISIBLE) {
			return;
		}

		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.tool_bar_show);

		LinearInterpolator li = new LinearInterpolator();

		anim.setInterpolator(li);

		toolBar.startAnimation(anim);

		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

				toolBar.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				toolBar.clearAnimation();
			}
		});

	}

	public void hideToolBar(boolean animated) {
		if (toolBar.getVisibility() == View.GONE) {
			return;
		}

		// Animation anim = AnimationUtils.loadAnimation(this,
		// R.anim.tool_bar_hide);

		if (!animated) {
			toolBar.setVisibility(View.GONE);
			return;
		}

		Animation trans = new TranslateAnimation(0, 0, 0,
				toolBar.getMeasuredHeight());

		trans.setDuration(300);

		LinearInterpolator li = new LinearInterpolator();

		trans.setInterpolator(li);

		trans.setFillAfter(true);

		trans.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				toolBar.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				toolBar.clearAnimation();

			}
		});

		toolBar.startAnimation(trans);

	}

	public void hideNavigationBar(boolean animated) {
		if (navigationBar.getVisibility() == View.GONE) {
			return;
		}

		navigationBar.setVisibility(View.GONE);
	}

	public void showNavigationBar(boolean animated) {
		if (navigationBar.getVisibility() == View.VISIBLE) {
			return;
		}

		navigationBar.setVisibility(View.VISIBLE);
	}

	public void hideStatusBar() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void showStatusBar() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

	}

	public void showExceptionMessage(Exception ex) {
		showExceptionMessage(ex, "");
	}

	public void showExceptionMessage(Exception ex, String addition) {
		if (ex instanceof RemoteServiceException) {
			showFaultTipMessageAndHide("网络连接异常，无法获取数据 " + addition);
		} else {
			showFaultTipMessageAndHide("出现异常，请稍后重试 " + addition);
		}
	}

	/**
	 * 全屏
	 */
	public void fullScreen() {
		isFullScreen = true;
		hideStatusBar();
		hideToolBar(true);
	}

	/**
	 * 退出全屏
	 */
	public void quitFullScreen() {
		isFullScreen = false;
		showStatusBar();
		showToolBar();
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public boolean isSDCardEnable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void exitApp() {
		System.exit(0);
	}

	public void setToolbarMode(int mode) {
		toolbarMode = mode;

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRelativeLayout
				.getLayoutParams();
		if (mode == TOOLBAR_MODE_FILL) {
			layoutParams.addRule(RelativeLayout.ABOVE, R.id.toolBar);
			toolBar.setBackgroundResource(R.drawable.tool_bar_bg);
		} else if (mode == TOOLBAR_MODE_OVERLAP) {
			layoutParams.addRule(RelativeLayout.ABOVE);
			toolBar.setBackgroundResource(R.drawable.tool_bar_bg_overlap);
		}
		mainRelativeLayout.setLayoutParams(layoutParams);

	}

	protected abstract void onCreateMainView();

}
