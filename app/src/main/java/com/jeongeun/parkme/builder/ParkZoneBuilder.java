package com.jeongeun.parkme.builder;

import com.jeongeun.parkme.model.ParkPolygon;
import com.jeongeun.parkme.model.ParkZone;

/**
 * A builder class for ParkZone data.
 * Some variables and methods are commented out for later use.
 */

public class ParkZoneBuilder {

    //private int id;
    private ParkPolygon parkPolygon;
    private String name;
    private boolean isPaymentAllowed;
    private double maxDuration;
    private double service_price;
    //private int depth;
    //private int draw;
    //private boolean stickerRequired;
    private String currency;
    private String contactEmail;
    //private LatLng point;
    //private String country;
    //private int providerId;
    //private String providerName;

    public ParkZoneBuilder setParkPolygon(ParkPolygon parkPolygon) {
        this.parkPolygon = parkPolygon;
        return this;
    }

    public ParkZoneBuilder setPaymentAllowed(boolean paymentAllowed) {
        isPaymentAllowed = paymentAllowed;
        return this;
    }

    public ParkZoneBuilder setService_price(double service_price) {
        this.service_price = service_price;
        return this;
    }

    public ParkZoneBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ParkZoneBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public ParkZoneBuilder setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    public ParkZoneBuilder setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

/*
    public ParkZoneBuilder setId(int id) {
        this.id = id;
        return this;
    }


    public ParkZoneBuilder setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public ParkZoneBuilder setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public ParkZoneBuilder setStickerRequired(boolean stickerRequired) {
        this.stickerRequired = stickerRequired;
        return this;
    }

    public ParkZoneBuilder setPoint(LatLng point) {
        this.point = point;
        return this;
    }

    public ParkZoneBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public ParkZoneBuilder setProviderId(int providerId) {
        this.providerId = providerId;
        return this;
    }

    public ParkZoneBuilder setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }
*/

/*    public ParkZone build() {
        if (parkPolygon == null) {
            throw new IllegalStateException("ParkPolygon can not be null!");
        }
        return new ParkZone(id, parkPolygon, name, isPaymentAllowed,
                maxDuration, service_price, depth, draw, stickerRequired,
                currency, contactEmail, point, country, providerId, providerName);
    }*/

    public ParkZone build() {
        if (parkPolygon == null) {
            throw new IllegalStateException("ParkPolygon can not be null!");
        }
        return new ParkZone(parkPolygon, name, isPaymentAllowed, maxDuration, service_price, currency, contactEmail);
    }
}
