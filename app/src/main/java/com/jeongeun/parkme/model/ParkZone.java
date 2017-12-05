package com.jeongeun.parkme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JeongEun on 2017-10-24.
 * This class is to contain Parking zone information.
 */

public class ParkZone implements Parcelable {

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

/*
    public ParkZone(int id, ParkPolygon parkPolygon, String name,
                    boolean isPaymentAllowed, int maxDuration, double service_price,
                    int depth, int draw, boolean stickerRequired, String currency,
                    String contactEmail, LatLng point, String country,
                    int providerId, String providerName) {
        this.id = id;
        this.parkPolygon = parkPolygon;
        this.name = name;
        this.isPaymentAllowed = isPaymentAllowed;
        this.maxDuration = maxDuration;
        this.service_price = service_price;
        this.depth = depth;
        this.draw = draw;
        this.stickerRequired = stickerRequired;
        this.currency = currency;
        this.contactEmail = contactEmail;
        this.point = point;
        this.country = country;
        this.providerId = providerId;
        this.providerName = providerName;
    }
*/

    public ParkZone(ParkPolygon parkPolygon, String name, boolean isPaymentAllowed,
                    double maxDuration, double service_price, String currency, String contactEmail) {
        this.parkPolygon = parkPolygon;
        this.name = name;
        this.isPaymentAllowed = isPaymentAllowed;
        this.maxDuration = maxDuration;
        this.service_price = service_price;
        this.currency = currency;
        this.contactEmail = contactEmail;
    }

    public ParkPolygon getParkPolygon() {
        return parkPolygon;
    }

    public void setParkPolygon(ParkPolygon parkPolygon) {
        this.parkPolygon = parkPolygon;
    }

    public boolean isPaymentAllowed() {
        return isPaymentAllowed;
    }

    public void setPaymentAllowed(boolean paymentAllowed) {
        isPaymentAllowed = paymentAllowed;
    }

    public double getService_price() {
        return service_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setService_price(double service_price) {
        this.service_price = service_price;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public boolean isStickerRequired() {
        return stickerRequired;
    }

    public void setStickerRequired(boolean stickerRequired) {
        this.stickerRequired = stickerRequired;
    }


    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.parkPolygon, flags);
        parcel.writeString(this.name);
        parcel.writeByte(this.isPaymentAllowed ? (byte) 0 : (byte) 1);
        parcel.writeDouble(this.maxDuration);
        parcel.writeDouble(this.service_price);
        parcel.writeString(this.currency);
        parcel.writeString(this.contactEmail);
    }

    protected ParkZone(Parcel in) {
        this.parkPolygon = in.readParcelable(ParkPolygon.class.getClassLoader());
        this.name = in.readString();
        this.isPaymentAllowed = in.readByte() != 0;
        this.maxDuration = in.readDouble();
        this.service_price = in.readDouble();
        this.currency = in.readString();
        this.contactEmail = in.readString();
    }

    public static final Parcelable.Creator<ParkZone> CREATOR = new Parcelable.Creator<ParkZone>() {
        @Override
        public ParkZone createFromParcel(Parcel source) {
            return new ParkZone(source);
        }

        @Override
        public ParkZone[] newArray(int size) {
            return new ParkZone[size];
        }
    };
}
