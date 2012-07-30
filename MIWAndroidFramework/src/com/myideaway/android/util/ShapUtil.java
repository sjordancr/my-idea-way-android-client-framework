package com.myideaway.android.util;

import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-4-20
 * Time: AM10:18
 */
public class ShapUtil {
    public static boolean isPolygonContainPoint(PointF pointF,PointF[] vertexPointFs){
        int nCross = 0;
        for (int i = 0; i < vertexPointFs.length; i++) {
            PointF p1 = vertexPointFs[i];
            PointF p2 = vertexPointFs[(i + 1) % vertexPointFs.length];
            if (p1.y == p2.y)
                continue;
            if (pointF.y < Math.min(p1.y, p2.y))
                continue;
            if (pointF.y >= Math.max(p1.y, p2.y))
                continue;
            double x = (double) (pointF.y - p1.y) * (double) (p2.x - p1.x)
                    / (double) (p2.y - p1.y) + p1.x;
            if (x > pointF.x)
                nCross++;
        }
        return (nCross % 2 == 1);
    }

    public static boolean isPointInPolygon(double px, double py,
                                    ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
        boolean isInside = false;
        double ESP = 1e-9;
        int count = 0;
        double linePoint1x;
        double linePoint1y;
        double linePoint2x = 180;
        double linePoint2y;

        linePoint1x = px;
        linePoint1y = py;
        linePoint2y = py;

        for (int i = 0; i < polygonXA.size() - 1; i++) {
            double cx1 = polygonXA.get(i);
            double cy1 = polygonYA.get(i);
            double cx2 = polygonXA.get(i + 1);
            double cy2 = polygonYA.get(i + 1);
            if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
                return true;
            }
            if (Math.abs(cy2 - cy1) < ESP) {
                continue;
            }

            if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x,
                    linePoint2y)) {
                if (cy1 > cy2)
                    count++;
            } else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y,
                    linePoint2x, linePoint2y)) {
                if (cy2 > cy1)
                    count++;
            } else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x,
                    linePoint1y, linePoint2x, linePoint2y)) {
                count++;
            }
        }
        if (count % 2 == 1) {
            isInside = true;
        }

        return isInside;
    }

    public static double Multiply(double px0, double py0, double px1, double py1,
                           double px2, double py2) {
        return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
    }

    public static boolean isPointOnLine(double px0, double py0, double px1,
                                 double py1, double px2, double py2) {
        boolean flag = false;
        double ESP = 1e-9;
        if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP)
                && ((px0 - px1) * (px0 - px2) <= 0)
                && ((py0 - py1) * (py0 - py2) <= 0)) {
            flag = true;
        }
        return flag;
    }

    public static boolean isIntersect(double px1, double py1, double px2, double py2,
                               double px3, double py3, double px4, double py4) {
        boolean flag = false;
        double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
        if (d != 0) {
            double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3))
                    / d;
            double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1))
                    / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        }
        return flag;
    }
}
