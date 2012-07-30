package com.myideaway.android.util;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-4-20
 * Time: AM9:56
 */
public class DataUtil {
    public static double parseDouble(Object obj) {
        double data = 0;

        if (obj == null) {
            return data;
        }

        String str = obj.toString();
        if (str != null && !"".equals(str)) {
            try {
                data = Double.parseDouble(str);
            } catch (NumberFormatException ex) {

            }
        }

        return data;
    }

    public static float parseFloat(Object obj) {
        float data = 0;
        if(obj == null){
            return data;
        }

        String str = obj.toString();
        if (str != null && !"".equals(str)) {
            try {
                data = Float.parseFloat(str);
            } catch (NumberFormatException ex) {

            }
        }

        return data;
    }

    public static int parseInt(Object obj) {
        int data = 0;

        if(obj == null){
            return data;
        }

        String str = obj.toString();
        if (str != null && !"".equals(str)) {
            try {
                data = Integer.parseInt(str);
            } catch (NumberFormatException ex) {

            }
        }

        return data;
    }
}
