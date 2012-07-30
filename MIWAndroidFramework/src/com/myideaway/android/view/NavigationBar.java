package com.myideaway.android.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myideaway.android.R;

public class NavigationBar extends RelativeLayout {
	protected LayoutInflater layoutInflater;
	protected LinearLayout leftLinearLayout;
	protected LinearLayout centerLinearlayout;
	protected LinearLayout rightLinearLayout;
	protected Button backButton;
	protected ArrayList<View> leftViews;
	protected ArrayList<View> rightViews;
	protected TextView titleTextView;

	public NavigationBar(Context context) {
		super(context);
		initComponent();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent();
	}

	public void addRightView(View view) {
		rightLinearLayout.addView(view);
	}

	public void clearRightViews() {
		rightLinearLayout.removeAllViews();
	}

	public void addLeftView(View view) {
		leftLinearLayout.addView(view);
	}

	public void clearLeftViews() {
		leftLinearLayout.removeAllViews();
	}

	public void showBackButton() {
		backButton.setVisibility(View.VISIBLE);
	}

	public void hideBackButton() {
		backButton.setVisibility(View.GONE);
	}

	protected void initComponent() {
		leftViews = new ArrayList<View>();
		rightViews = new ArrayList<View>();

		layoutInflater = LayoutInflater.from(getContext());
		layoutInflater.inflate(R.layout.navigation_bar, this);

		leftLinearLayout = (LinearLayout) findViewById(R.id.leftLinearLayout);
		centerLinearlayout = (LinearLayout) findViewById(R.id.centerLinearLayout);
		rightLinearLayout = (LinearLayout) findViewById(R.id.rightLinearLayout);

		titleTextView = (TextView) findViewById(R.id.titleTextView);

		// 默认左侧添加一个返回按钮
		backButton = (Button) (layoutInflater.inflate(
				R.layout.navigation_bar_back_button, null)
				.findViewById(R.id.navBackButton));
		backButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				backButtonOnClick(v);
			}
		});
		leftLinearLayout.addView(backButton);
	}

	protected void backButtonOnClick(View v) {
		((Activity) getContext()).finish();
	}

	public void setTitle(String title) {
		titleTextView.setText(title);
	}

}
