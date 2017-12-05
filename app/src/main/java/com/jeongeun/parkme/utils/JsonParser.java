package com.jeongeun.parkme.utils;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.jeongeun.parkme.R;
import com.jeongeun.parkme.builder.ParkPolygonBuilder;
import com.jeongeun.parkme.builder.ParkZoneBuilder;
import com.jeongeun.parkme.builder.ViewComponentBuilder;
import com.jeongeun.parkme.model.ParkPolygon;
import com.jeongeun.parkme.model.ParkZone;
import com.jeongeun.parkme.model.ViewComponent;

import static org.junit.Assert.*;

/**
 * Created by JeongEun on 2017-10-24.
 */


public class JsonParser {

    public static final String TAG = "JsonParser";

    /**
     * To open a resource and get Input stream.
     * @param context
     * @return View Component for the display of zones
     */
    public static ViewComponent parse(Context context) {

        try (InputStream inputStream = context.getResources().openRawResource(R.raw.json)){
            return readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * To get JsonReader and call several methods to parse.
     */
    public static ViewComponent readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        ViewComponentBuilder builder = new ViewComponentBuilder();
        readCurrentLocation(reader, builder);
        readLocationData(reader, builder);
        reader.close();
        return builder.build();
    }


    /**
     * It's only to get current location.
     */
    public static void readCurrentLocation(JsonReader reader,
                                           ViewComponentBuilder viewCompBuilder) throws IOException {
        LatLng currentLatLng = null;
        reader.beginObject();
        String name = reader.nextName();
        if (name.equals("current_location")) {
            String[] latLng = reader.nextString().split(", ");
            currentLatLng = new LatLng(Double.valueOf(latLng[0]), Double.valueOf(latLng[1]));
            viewCompBuilder.setCurrentLocation(currentLatLng);
        }
    }


    /**
     * To get the bound and the zones information.
     * Each information will get from the separated methods.
     */
    public static void readLocationData(JsonReader reader,
                                        ViewComponentBuilder viewCompBuilder) throws IOException {

        String name = reader.nextName();

        if (name.equals("location_data")) {
            reader.beginObject();

            while (reader.hasNext()) {
                name = reader.nextName();
                if (name.equals("bounds")) {
                    viewCompBuilder.setBounds(readBounds(reader));
                } else if (name.equals("zones")) {
                    viewCompBuilder.setParkZones(readZonesArray(reader));
                }
            }
            reader.endObject();
        }
    }

    public static LatLngBounds readBounds(JsonReader reader) throws IOException {

        double north = 0.0;
        double south = 0.0;
        double west = 0.0;
        double east = 0.0;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("north")) {
                north = reader.nextDouble();
            } else if (name.equals("south")) {
                south = reader.nextDouble();
            } else if (name.equals("west")) {
                west = reader.nextDouble();
            } else if (name.equals("east")) {
                east = reader.nextDouble();
            }
        }
        reader.endObject();

        return new LatLngBounds(new LatLng(south, west), new LatLng(north, east));
    }

    public static List<ParkZone> readZonesArray(JsonReader reader) throws IOException {

        List<ParkZone> zones = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            zones.add(readZone(reader));
        }
        reader.endArray();
        return zones;
    }


    /**
     * To get park zone information. According to 'payment_is_allowed' value,
     * the background color will be decided.
     */
    public static ParkZone readZone(JsonReader reader) throws IOException {

        ParkPolygon polygon = null;
        boolean isPaymentAllowed = false;
        double servicePrice = 0.0d;
        String currency = "";
        String parkName = "";
        String email = "";
        double maxDuration = 0.0d;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("polygon")) {
                polygon = readPolygon(reader);
            } else if (name.equals("name")) {
                parkName = reader.nextString();
            } else if (name.equals("payment_is_allowed")) {
                isPaymentAllowed = reader.nextInt() != 0;
                polygon.setFillColor(isPaymentAllowed);
            } else if (name.equals("max_duration")) {
                maxDuration = reader.nextDouble();
            } else if (name.equals("service_price")) {
                servicePrice = reader.nextDouble();
            } else if (name.equals("currency")) {
                currency = reader.nextString();
            } else if (name.equals("contact_email")) {
                email = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return new ParkZoneBuilder().setParkPolygon(polygon)
                                    .setName(parkName)
                                    .setPaymentAllowed(isPaymentAllowed)
                                    .setMaxDuration(maxDuration)
                                    .setService_price(servicePrice)
                                    .setCurrency(currency)
                                    .setContactEmail(email)
                                    .build();
    }

    public static ParkPolygon readPolygon(JsonReader reader) throws IOException {

        String[] polygonArray = reader.nextString().split(", ");
        LatLng[] points = new LatLng[polygonArray.length];

        int i = 0;
        for (String s : polygonArray) {
            String[] latLng = s.split(" ");
            points[i++] = new LatLng(Double.valueOf(latLng[0]), Double.valueOf(latLng[1]));
        }

        return new ParkPolygonBuilder().setPoints(points)
                                        .build();
    }

}
