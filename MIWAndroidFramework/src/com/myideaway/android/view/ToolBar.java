package com.myideaway.android.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.myideaway.android.R;

public class ToolBar extends RelativeLayout {
	protected LayoutInflater layoutInflater;
	protected LinearLayout contentLinearLayout;
	protected ArrayList<View> contentViews;
	private View childView = null;

	public ToolBar(Context context) {
		super(context);
		initComponent();
	}

	public ToolBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent();
	}

	public void addContentView(View view) {
		contentLinearLayout.addView(view);
	}

	public void clearContentViews() {
		contentLinearLayout.removeAllViews();
	}

	public void setContentView(int res) {
		setChildView(layoutInflater.inflate(res, this));
	}

	protected void initComponent() {
		contentViews = new ArrayList<View>();

		layoutInflater = LayoutInflater.from(getContext());
		layoutInflater.inflate(R.layout.tool_bar, this);

		contentLinearLayout = (LinearLayout) findViewById(R.id.contentLinearLayout);
	}

	public View getChildView() {
		return childView;
	}

	private void setChildView(View childView) {
		this.childView = childView;
	}

}
