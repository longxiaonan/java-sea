package com.iee.geogMap;

import java.text.DecimalFormat;

/**
 * Created by yuanqiang on 2017/11/21.
 */
public class Coordinate {
    private static double PI = Math.PI;
    private static double AXIS = 6378245.0; //
    private static double OFFSET = 0.00669342162296594323; // (a^2 - b^2) / a^2
    private static double X_PI = PI * 3000.0 / 180.0;

    private static DecimalFormat format = new DecimalFormat("0.00000");
    public static void main(String[] args) {

    	double [] wgs = wgs2BD09(114.391512,30.453084);
        for (int i = 0; i < wgs.length; i++) {
            System.out.println(format.format(wgs[i]));
        }
    }

    /**
     * 转换坐标为百度
     * @param glat 纬度
     * @param glon 经度
     * @return
     */
    public static double[] gcj2BD09(double glat, double glon) {
        double x = glon;
        double y = glat;
        double[] latlon = new double[2];
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        latlon[0] = z * Math.sin(theta) + 0.006;
        latlon[1] = z * Math.cos(theta) + 0.0065;
        return latlon;
    }

    /**
     * GPS坐标 转换 百度坐标
     * @param wgLat
     * @param wgLon
     * @return
     */
    public static double[] wgs2BD09(double wgLon, double wgLat) {
        double[] latlon = wgs2GCJ(wgLat, wgLon);
        return gcj2BD09(latlon[0], latlon[1]);
    }

    /**
     * 验证坐标 ，修改坐标偏差值
     * @param wgLat 纬度
     * @param wgLon 经度
     * @return
     */
    public static double[] wgs2GCJ(double wgLat, double wgLon) {
        double[] latlon = new double[2];
        if (outOfChina(wgLat, wgLon)) {
            latlon[0] = wgLat;
            latlon[1] = wgLon;
            return latlon;
        }
        double[] deltaD = delta(wgLat, wgLon);
        latlon[0] = wgLat + deltaD[0];
        latlon[1] = wgLon + deltaD[1];
        return latlon;
    }

    /**
     * 根据圆周率求弧度等等，地球半径。。各种正弦／余弦
     * @param wgLat
     * @param wgLon
     * @return
     */
    public static double[] delta(double wgLat, double wgLon) {
        double[] latlng = new double[2];
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0)
                / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        latlng[0] = dLat;
        latlng[1] = dLon;
        return latlng;
    }


    /**
     * 验证是否在中国，不在则不适用
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }


    /**
     * 计数纬度，根据圆周率。各种正弦，绝对值
     * @param x
     * @param y
     * @return
     */
    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 计数经度，根据圆周率。各种正弦，绝对值
     * @param x
     * @param y
     * @return
     */
    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
                * PI)) * 2.0 / 3.0;
        return ret;
    }
}
