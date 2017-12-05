package com.jeongeun.parkme.utils;

import android.support.design.widget.BottomSheetBehavior;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;
import com.jeongeun.parkme.databinding.ActivityMapsBinding;
import com.jeongeun.parkme.model.ParkPolygon;
import com.jeongeun.parkme.model.ParkZone;
import com.jeongeun.parkme.model.ViewComponent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by JeongEun on 2017-10-24.
 */


public class MapUtils {

    /**
     * To display components on top of the map.
     * This method can be called by two conditions.
     * One is when the parsing is finished and the another is the mop is ready to use.
     * But to change camera, the map should be fully loaded.
     * So before displaying, register onMapLoadedCallback.
     *
     * 1. Add the center coordinated marker
     * 2. Add park polygons
     * 3. Set the initial view port
     * 4. Register listeners for interaction with the map
     *
     * @param googleMap
     * @param googleMap
     * @param viewComp All component information to display.
     * @param bottomSheetBehavior To change bottom sheet status if there's any selected zone exist.
     * @param binding To display the zone information.
     */
    public static void displayComponent(final GoogleMap googleMap, final ViewComponent viewComp,
                                        final BottomSheetBehavior bottomSheetBehavior,
                                        final ActivityMapsBinding binding) {

        // If the viewComp is null, it means parsing is not completed.
        if (viewComp == null) {
            return;
        }

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                // add a marker
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(viewComp.getBounds().getCenter())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .draggable(true));
                viewComp.setMarker(marker);

                // add polygons
                for (ParkZone parkZone : viewComp.getParkZones()) {
                    ParkPolygon parkPolygon = parkZone.getParkPolygon();

                    Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                            .add(parkPolygon.getPoints())
                            .fillColor(parkPolygon.getFillColor())
                            .strokeColor(parkPolygon.getStrokeColor())
                            .strokeWidth(parkPolygon.getStrokeWidth()));

                    // for updating fillColor Polygon instance should be saved
                    parkPolygon.setPolygon(polygon);
                }

                // Set the initial viewport
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(viewComp.getBounds(), 0));

                /**
                 * To detect a user stop interacting with the map.
                 * When a user stop interacting the map, the marker should be visible
                 * and the title (service price) should be shown if there's any selected zone.
                 */
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        updateComponent(googleMap, viewComp, bottomSheetBehavior, binding);
                        viewComp.setMarkerVisibility(true);
                        viewComp.getMarker().showInfoWindow();
                    }
                });

                /**
                 * To detect a user start to move the map.
                 * Ignore other cases rather than a user moves.
                 */
                googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                    @Override
                    public void onCameraMoveStarted(int reason) {
                        if (reason == REASON_GESTURE) {
                            viewComp.setMarkerVisibility(false);
                            viewComp.getMarker().hideInfoWindow();
                        }
                    }
                });
            }
        });
    }

    /**
     * To update components whenever a user move the map.
     * 1. The position and the title of the marker
     * 2. The information and the status of the bottom sheet
     *
     * @param googleMap
     * @param viewComp All component information to display.
     * @param bottomSheetBehavior To change bottom sheet status if there's any selected zone exist.
     * @param binding To display the zone information.
     */
    public static void updateComponent(GoogleMap googleMap, ViewComponent viewComp,
                                       BottomSheetBehavior bottomSheetBehavior,
                                       ActivityMapsBinding binding) {
        if (viewComp == null) {
            return;
        }

        // update the center marker
        LatLng centerPosition = googleMap.getCameraPosition().target;
        viewComp.updateMarkerPosition(centerPosition);
        ParkZone selectedZone = null;

        /* To find if there is any zone that contains the position of the marker.
        *  If there is any, change the color of zone.
        */
        for (ParkZone parkZone : viewComp.getParkZones()) {
            ParkPolygon parkPolygon = parkZone.getParkPolygon();

            if (PolyUtil.containsLocation(centerPosition, Arrays.asList(parkPolygon.getPoints()), true)) {
                selectedZone = parkZone;
                parkPolygon.getPolygon().setFillColor(Constants.COLOR_SELECTED_ZONE);
            } else {
                // back to original color for not selected zone
                parkPolygon.getPolygon().setFillColor(parkPolygon.getFillColor());
            }
        }

        // change marker info and bottom sheet status
        if (selectedZone != null) {
            // display service price for the selected zone
            viewComp.getMarker().setTitle(getMarkerTitle(selectedZone));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            binding.setZone(selectedZone);
        } else {
            viewComp.getMarker().setTitle("");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        viewComp.setSelectedZone(selectedZone);
    }

    private static String getMarkerTitle(ParkZone zone) {
        double price = zone.getService_price();
        return price == 0 ? "free" : price + zone.getCurrency();
    }

    public static String getCurrentTime() {
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
        return time.format(new Date()).toString();
    }
}
