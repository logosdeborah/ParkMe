package com.jeongeun.parkme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.jeongeun.parkme.utils.Constants;

/**
 * This class is for Parking zone polygon.
 */

public class ParkPolygon implements Parcelable {

    private LatLng[] points;
    /* A color to fill polygon */
    private int fillColor;
    private int strokeWidth;
    private int strokeColor;
    private Polygon polygon;

    public ParkPolygon(LatLng[] points, int fillColor, int strokeWidth, int strokeColor) {
        this.points = points;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.polygon = null;
    }


    public LatLng[] getPoints() {
        return points;
    }

    public void setPoints(LatLng[] points) {
        this.points = points;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(boolean isPaymentAllowed) {
        this.fillColor = isPaymentAllowed ? Constants.COLOR_PAYMENT_ALLOWED_ZONE : Constants.COLOR_NORMAL_ZONE;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        polygon.setClickable(true);
        this.polygon = polygon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedArray(this.points, flags);
        parcel.writeInt(this.fillColor);
        parcel.writeInt(this.strokeWidth);
        parcel.writeInt(this.strokeColor);
    }


    protected ParkPolygon(Parcel in) {
        this.points = in.createTypedArray(LatLng.CREATOR);
        this.fillColor = in.readInt();
        this.strokeWidth = in.readInt();
        this.strokeColor = in.readInt();
    }


    public static final Creator<ParkPolygon> CREATOR = new Creator<ParkPolygon>() {
        @Override
        public ParkPolygon createFromParcel(Parcel source) {
            return new ParkPolygon(source);
        }

        @Override
        public ParkPolygon[] newArray(int size) {
            return new ParkPolygon[size];
        }
    };
}
