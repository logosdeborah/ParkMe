package com.jeongeun.parkme.builder;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.jeongeun.parkme.model.ParkPolygon;
import com.jeongeun.parkme.utils.Constants;


/**
 * A builder for ParkPolygon data
 */

public class ParkPolygonBuilder {

    private LatLng[] points;

    /** The default value is normal.
    * Later will be changed depends on the payment allowance.
     */
    private int fillColor = Constants.COLOR_NORMAL_ZONE;
    private int strokeWidth = 2;
    private int strokeColor = Color.BLACK;

    public ParkPolygonBuilder setPoints(LatLng[] points) {
        this.points = points;
        return this;
    }

    public ParkPolygonBuilder setFillColor(int fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public ParkPolygonBuilder setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public ParkPolygonBuilder setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public ParkPolygon build() {
        if (points == null || points.length < 3) {
            throw new IllegalStateException("Polygon cannot be null or less than 3 points!");
        }
        return new ParkPolygon(points, fillColor, strokeWidth, strokeColor);
    }


}
