package com.myideaway.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CompassUtil extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder holder;
	Paint paint;
	Bitmap bit;
	Bitmap background;
	Context contexts;

	public float degree;
	private int width;
	private int heigth;

	public CompassUtil(Context context, Bitmap img, Bitmap bgimg) {
		super(context);
		this.setKeepScreenOn(true);
		paint = new Paint();
		contexts = context;
		bit = img;
		background = bgimg;
		background = Bitmap.createScaledBitmap(background, 320, 450, false);
		holder = this.getHolder();
		holder.addCallback(this);
	}

	public float getDegree() {
		return degree - 90;
	}

	public void setDegree(float degree) {
		this.degree = degree;
	}

	// 锟斤拷锟斤拷一锟斤拷锟皆讹拷锟斤拷锟斤拷叱锟�
	public void surfaceCreated(SurfaceHolder holder) {
		Thread t = new MyThread();
		t.start();
	}

	class MyThread extends Thread {
		public void run() {
			while (true) {
				Canvas c = holder.lockCanvas();
				if (c != null) {
					c.save();
					c.drawBitmap(background, 0, 0, paint);
					// 锟街伙拷转锟斤拷锟斤拷锟劫度ｏ拷锟酵伙拷锟狡革拷锟侥讹拷锟劫度ｏ拷锟斤拷锟街伙拷锟狡碉拷指锟斤拷指锟斤拷0锟饺ｏ拷锟斤拷指锟津北凤拷
					c.rotate(-1 * getDegree());
					c.drawBitmap(bit, 148, 40, paint);
					c.restore();
					holder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public static float getDegreeByPoint(double fromPointX, double fromPointY,
			double toPointX, double toPointY) {
		double x = Math.abs(toPointX) - Math.abs(fromPointX);
		double y = Math.abs(toPointY) - Math.abs(fromPointY);
		double temp = Math.atan2(Math.abs(y), Math.abs(x));
		double dushu;
		dushu = Math.toDegrees(temp);
		if (x > 0 && y > 0) {
			dushu = 180 - dushu;
		} else if (x < 0 && y < 0) {
			dushu = 360 - dushu;
		} else if (x > 0 && y < 0) {
			dushu = 180 + dushu;
		}
		return (float) dushu;
	}
}
