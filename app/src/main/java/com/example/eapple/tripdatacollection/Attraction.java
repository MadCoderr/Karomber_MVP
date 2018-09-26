package com.example.eapple.tripdatacollection;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @file Attraction.java
 * @author Haroon khan
 * @data: 08 Sept, 2018
 *
 * @section Description
 * This class is the declaration of database for
 * storage of data in the internal database for
 * data storage which is later synchronized to firebase.
 * Here Room database is used instead of traditional sqlite db
 * which is an abstraction over sqlite db
 */


@Entity
public class Attraction {

    //Declarations
    @PrimaryKey(autoGenerate = true)
    private int aid;

    private String name;
    private double longitude;
    private double latitude;
    private String classification;
    private String description;
    private String imgTitleInCam;
    private String accessibility;
    private String type;

    /**
     * @brief Constructor for attraction object
     *
     * @param name
     * @param longitude
     * @param latitude
     * @param classification
     * @param description
     * @param imgTitleInCam
     * @param type
     */
    public Attraction(String name, double longitude, double latitude, String classification, String description, String imgTitleInCam, String accessibility, String type) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.classification = classification;
        this.description = description;
        this.imgTitleInCam = imgTitleInCam;
        this.accessibility = accessibility;
        this.type = type;
    }

    /**
     * Gets type
     *
     * @return value of type
     */

    public String getType() {
        return type;
    }

    /**
     * Gets accessibility
     * 
     * @return value of accessibility
     */

    public String getAccessibility() {
        return accessibility;
    }

    /**
     *@brief Getter for attraction Id
     * @return aid
     */
    public int getAid() {
        return aid;
    }


    /**
     * @brief Getter for Attractoin name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @breif Getter for longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @breif getter for attraction type
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * @brief Getter for attraction description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Getter for imgTitleInCam
     * @return imgTitleInCam
     */
    public String getImgTitleInCam() {
        return imgTitleInCam;
    }

    /**
     * @breif Getter for latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets Accessibility
     * @param accessibility
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * @breif Setter for attraction Id
     * @param aid
     */
    public void setAid(int aid) {
        this.aid = aid;
    }

    /**
     * @brief Setter attraction name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @breif Setter for longitude
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @brief Setter for latitude
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *@breif Setter for attraction type
     * @param classification
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * @breif Setter for attraction Details
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @breif Setter for imgTitleInCam
     * @param imgTitleInCam
     */
    public void setImgTitleInCam(String imgTitleInCam) {
        this.imgTitleInCam = imgTitleInCam;
    }

    /**
     * @brief Setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
