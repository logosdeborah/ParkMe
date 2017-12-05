package com.jeongeun.parkme.builder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.jeongeun.parkme.model.ParkZone;
import com.jeongeun.parkme.model.ViewComponent;

import java.util.List;

/**
 * A builder class for ViewComponent data.
 */

public class ViewComponentBuilder {

    private LatLng currentLocation;
    private LatLngBounds bounds;
    private List<ParkZone> parkZones;

    public ViewComponentBuilder setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public ViewComponentBuilder setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
        return this;
    }

    public ViewComponentBuilder setParkZones(List<ParkZone> parkZones) {
        this.parkZones = parkZones;
        return this;
    }

    public ViewComponent build() {
        if (currentLocation == null || bounds == null || parkZones == null) {
            throw new IllegalStateException("Please provide correct information!");
        }
        return new ViewComponent(currentLocation, bounds, parkZones);
    }
}
