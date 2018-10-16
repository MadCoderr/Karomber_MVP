package com.example.eapple.tripdatacollection.data.pojo;

import java.util.List;

public class SourceData {
    private List<Data> data;
    String image;

    public SourceData() {}

    public SourceData(List<Data> data, String image) {
        this.data = data;
        this.image = image;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
