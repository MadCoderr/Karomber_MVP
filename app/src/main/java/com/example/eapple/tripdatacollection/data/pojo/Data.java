package com.example.eapple.tripdatacollection.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Data implements Parcelable {
    private String accessiblity;
    private String classification;
    private String description;
    private List<String> gallery;
    private String image;
    private double latitude;
    private double longitude;
    private String name;

    public Data() {

    }

    public Data(String accessiblity, String classification, String description, List<String> gallery,
                String image, double longitude, double latitude, String name) {
        this.accessiblity = accessiblity;
        this.classification = classification;
        this.description = description;
        this.gallery = gallery;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }

    public String getAccessiblity() {
        if (accessiblity != null)
            return accessiblity;
        else
            return "-";
    }

    public void setAccessiblity(String accessiblity) {
        this.accessiblity = accessiblity;
    }

    public String getClassification() {
        if (classification != null)
            return classification;
        else
            return "-";
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        if (description != null)
            return description;
        else
            return "-";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        if (name != null)
            return name;
        else
            return "No Name";
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected Data(Parcel in) {
        accessiblity = in.readString();
        classification = in.readString();
        description = in.readString();
        gallery = in.createStringArrayList();
        image = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
    }



    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accessiblity);
        parcel.writeString(classification);
        parcel.writeString(description);
        parcel.writeStringList(gallery);
        parcel.writeString(image);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel parcel) {
            return new Data(parcel);
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };

}
