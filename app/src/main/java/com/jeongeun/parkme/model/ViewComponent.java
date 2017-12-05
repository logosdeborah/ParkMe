package com.jeongeun.parkme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * A class to hold view components
 */

public class ViewComponent implements Parcelable {

    private LatLng currentLocation;
    /* To display instructed bounds when the map first loaded */
    private LatLngBounds bounds;
    private List<ParkZone> parkZones;

    /* The marker in the center coordinate */
    private Marker marker;
    private ParkZone selectedZone;

    public ViewComponent(LatLng currentLocation, LatLngBounds bounds, List<ParkZone> parkZones) {
        this.currentLocation = currentLocation;
        this.bounds = bounds;
        this.parkZones = parkZones;
        this.marker = null;
        this.selectedZone = null;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
    }

    public List<ParkZone> getParkZones() {
        return parkZones;
    }

    public void setParkZones(List<ParkZone> parkZones) {
        this.parkZones = parkZones;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public ParkZone getSelectedZone() {
        return selectedZone;
    }

    public void setSelectedZone(ParkZone selectedZone) {
        this.selectedZone = selectedZone;
    }

    /**
     * To update the position of the marker whenever a user move the map.
     * The method will be called as soon as the camera is being idle/
     * @param position the marker position
     */
    public void updateMarkerPosition(LatLng position) {
        if (this.marker != null) {
            this.marker.setPosition(position);
        }
    }

    /**
     * To change the visibility of the marker.
     * The marker will be hidden during dragging or touching.
     * @param isVisible
     */
    public void setMarkerVisibility(boolean isVisible) {
        if (this.marker != null) {
            this.marker.setVisible(isVisible);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.currentLocation, flags);
        parcel.writeParcelable(this.bounds, flags);
        parcel.writeTypedList(this.parkZones);
    }

    protected ViewComponent(Parcel in) {
        this.currentLocation = in.readParcelable(LatLng.class.getClassLoader());
        this.bounds = in.readParcelable(LatLngBounds.class.getClassLoader());
        this.parkZones = in.createTypedArrayList(ParkZone.CREATOR);
    }

    public static final Creator<ViewComponent> CREATOR = new Creator<ViewComponent>() {
        @Override
        public ViewComponent createFromParcel(Parcel source) {
            return new ViewComponent(source);
        }

        @Override
        public ViewComponent[] newArray(int size) {
            return new ViewComponent[size];
        }
    };

}
