package com.example.eapple.tripdatacollection;


public class AttractionWithoutID {
    private String UserId;
    private String name;
    private double longitude;
    private double latitude;
    private String classification;
    private String description;
    private String imgTitleInCam;
    private String access;
    private String type;

    /**
     * @file AttractionWithoutID.java
     * @author Haroon khan
     * @date 08 Sept, 2018
     *
     * @section Description
     * This class for creating objects without ID for
     * the purpose to upload to Firebase
     * We could also do it without creating object but
     * we want to upload the complete object to firebase at
     * in single access
     */


    /**
     * @breif Constructor
     * @param userId
     * @param name
     * @param longitude
     * @param latitude
     * @param classification
     * @param description
     * @param imgTitleInCam
     */
    public AttractionWithoutID(String userId, String name, double longitude, double latitude, String classification, String description, String imgTitleInCam, String access, String type) {
        this.UserId = userId;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.classification = classification;
        this.description = description;
        this.imgTitleInCam = imgTitleInCam;
        this.access = access;
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
     * Gets access
     *
     * @return value of access
     */

    public String getAccess() {
        return access;
    }

    /**
     * @brief Getter for user id
     * @return UserId
     */
    public String getUserId() {
        return UserId;
    }

    /**
     * @brief Getter for Attraction Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Getter for longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @brief Getter for latitude
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @brief Getter for classification
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * @brief Getter for attraction Details
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Getter for Ratings
     * @return rating
     */
    public String getImgTitleInCam() {
        return imgTitleInCam;
    }

    /**
     * @brief Setter for user id
     * @param UserId
     */
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    /**
     * @brief Setter for attraction name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Setter for longitude
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @breif Setter for latitude
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @brief Setter for Attraction type
     * @param classification
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * Setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for ratings
     * @param imgTitleInCam
     */
    public void setImgTitleInCam(String imgTitleInCam) {
        this.imgTitleInCam = imgTitleInCam;
    }

    /**
     * Setter for Access
     * @param access
     */
    public void setAccess(String access) {
        this.access = access;
    }

    /**
     * @brief Setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}

