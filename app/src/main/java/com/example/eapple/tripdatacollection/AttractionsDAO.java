package com.example.eapple.tripdatacollection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * @file AttractionDAO.java
 * @author Haroon khan
 * @date 8 Sept, 2018
 *
 * @section Description
 * This file contain all the queries in new style of
 * Room database (Abstraction of sqlite db) in the form
 * of new functions
 */
@Dao
public interface AttractionsDAO {


    /**
     * @brief This function inserts the attraction
     * object in table with the name Attraction
     * @param attr
     */
    @Insert
    void insertAttraction(Attraction attr);


    /**
     * @breif This function (query) returns those attraction
     * objects which matches any of the listed parameters
     * @param attrName
     * @param longit
     * @param latit
     * @return Attraction
     */
    @Query("select * from Attraction where name = :attrName OR longitude = :longit AND latitude = :latit LIMIT 1")
    Attraction selectIfmatches(String attrName, double longit, double latit);


    /**
     * @brief This function returns all the attraction objects
     * @return List<Attraction>
     */
    @Query("select * from Attraction")
    List<Attraction> loadAllAttractions();

    /**
     * @brief This function deletes those attractions which
     * matches the id
     * @param id
     */
    @Query("delete from Attraction where aid = :id")
    void deleteIfIdMatches(int id);
}
