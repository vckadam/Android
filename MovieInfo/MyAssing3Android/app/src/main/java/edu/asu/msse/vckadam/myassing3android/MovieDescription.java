package edu.asu.msse.vckadam.myassing3android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/*
* Copyright 2016 Viplav Kadam
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose : To create which support storing movie description from OMDB as well as local server
 * and also plays video file
 *
 * This can be used by
 * @Professor Tim Lindquist
 * @University Arizona State University
 * @author Viplav Kadam mailto: vckadam@asu.edu
 * @version 26 April 2016
 */
public class MovieDescription implements Serializable {

    private String title;

    private String year;

    private String rated;

    private String released;

    private String runtime;

    private String genre;

    private String actors;

    private String plot;

    private String filename;

    byte bitmapBytes[];


    public MovieDescription(String title, String year, String rated, String released, String runtime, String genre, String actors, String plot, String filename){
        this.title = title;

        this.year = year;

        this.rated = rated;

        this.released = released;

        this.runtime = runtime;

        this.genre = genre;

        this.actors = actors;

        this.plot = plot;

        this.filename = filename;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
