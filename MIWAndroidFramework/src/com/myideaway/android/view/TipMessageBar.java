package com.myideaway.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.myideaway.android.R;


public class TipMessageBar extends RelativeLayout {
	public static final int TIP_ICON_TYPE_PROGRESS = 1;
	public static final int TIP_ICON_TYPE_SUCCESS = 2;
	public static final int TIP_ICON_TYPE_FAULT = 3;
    public static final int SIZE_NORMAL = 1;
    public static final int SIZE_LARGE =  2;
	
	private LayoutInflater layoutInflater;
	private LinearLayout iconLinearLayout;
	private View iconView;
	private TextView messageTextView;
	
	public TipMessageBar(Context context) {
		super(context);
		initComponent();
	}

	public TipMessageBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent();
	}
	
	protected void initComponent(){
		layoutInflater = LayoutInflater.from(getContext());
		layoutInflater.inflate(R.layout.tip_message_bar, this);
		iconLinearLayout = (LinearLayout) findViewById(R.id.iconLinearLayout);
		messageTextView = (TextView) findViewById(R.id.messageTextView);
	}
	
	public void setTipIconType(int type){
		if(type == TIP_ICON_TYPE_PROGRESS){
			ProgressBar progressBar  = new ProgressBar(getContext());
			iconView = progressBar;
		} else if (type == TIP_ICON_TYPE_SUCCESS){
			TextView textView = new TextView(getContext());
			textView.setText("✓");
			iconView = textView;
		} else if (type == TIP_ICON_TYPE_FAULT) {
			TextView textView = new TextView(getContext());
			textView.setText("✕");
			iconView = textView;
		}
	
		iconLinearLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
		iconView.setLayoutParams(layoutParams);
		iconLinearLayout.addView(iconView);
	}
	
	public void setMessage(String msg){
		messageTextView.setText(msg);
	}

    public void setSize(int size){

    }
}
