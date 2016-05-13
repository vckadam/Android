package edu.asu.msse.vckadam.myassing3android;
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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    RecyclerView.LayoutManager layoutManager;

    ArrayList<String> al = new ArrayList<String>();

    String str = null;

    MovieDescription md, md1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);

        try{
            CourseDB db = new CourseDB((Context)this);

            SQLiteDatabase crsDB = db.openDB();


            Cursor cur = crsDB.rawQuery("select * from movieInfo1;",null);

            while(cur.moveToNext()) {

                al.add(cur.getString(0));
            }
            cur.close();

            crsDB.close();

            db.close();

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            adapter = new RecyclerAdapter((ArrayList<String>) al, this);

            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

            Intent i = getIntent();

            Bundle extra = i.getExtras();

            if(extra != null) {

                str = (String) extra.getString("deleteMovie");

                md = (MovieDescription)extra.getSerializable("newMovie");

                md1 = (MovieDescription)extra.getSerializable("searchMovie");

                if (str != null) {

                    db = new CourseDB((Context) this);

                    crsDB = db.openDB();

                    crsDB.execSQL("DELETE FROM movieInfo1 WHERE movieTitle=?;", new String[]{str});

                    cur = crsDB.rawQuery("select * from movieInfo1;", null);

                    al = new ArrayList<String>();

                    while (cur.moveToNext()) {

                        al.add(cur.getString(0));

                    }
                    adapter = new RecyclerAdapter((ArrayList<String>) al, this);

                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(this);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);

                    cur.close();

                    crsDB.close();

                    db.close();

                }
                if(md != null) {

                    db = new CourseDB((Context) this);

                    crsDB = db.openDB();

                    ContentValues initialValues = new ContentValues();

                    initialValues.put("movieTitle", md.getTitle());

                    initialValues.put("movieYear", md.getYear());

                    initialValues.put("movieRuntime", md.getRuntime());

                    initialValues.put("movieGenre", md.getGenre());

                    initialValues.put("movieActors", md.getActors());

                    initialValues.put("movieRated", md.getRated());

                    initialValues.put("movieReleased", md.getReleased());

                    initialValues.put("moviePlot", md.getPlot());

                    initialValues.put("movieFile",md.getFilename());


                    crsDB.insert("movieInfo1", null, initialValues);

                    cur = crsDB.rawQuery("select * from movieInfo1;", null);

                    al = new ArrayList<String>();

                    while (cur.moveToNext()) {

                        al.add(cur.getString(0));

                    }
                    adapter = new RecyclerAdapter((ArrayList<String>) al, this);

                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(this);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);

                    cur.close();

                    crsDB.close();

                    db.close();
                }
                if(md1 != null) {
                    db = new CourseDB((Context) this);

                    crsDB = db.openDB();

                    ContentValues initialValues = new ContentValues();

                    initialValues.put("movieTitle", md1.getTitle());

                    initialValues.put("movieYear", md1.getYear());

                    initialValues.put("movieRuntime", md1.getRuntime());

                    initialValues.put("movieGenre", md1.getGenre());

                    initialValues.put("movieActors", md1.getActors());

                    initialValues.put("movieRated", md1.getRated());

                    initialValues.put("movieReleased", md1.getReleased());

                    initialValues.put("moviePlot", md1.getPlot());

                    initialValues.put("movieFile",md1.getFilename());


                    crsDB.insert("movieInfo1", null, initialValues);


                    cur = crsDB.rawQuery("select * from movieInfo1;", null);


                    al = new ArrayList<String>();

                    while (cur.moveToNext()) {

                        al.add(cur.getString(0));

                    }
                    adapter = new RecyclerAdapter((ArrayList<String>) al, this);

                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(this);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);

                    cur.close();

                    crsDB.close();

                    db.close();
                }
            }



        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting student info: "+
                    ex.getMessage());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        android.util.Log.d(this.getClass().getSimpleName(), "called onCreateOptionsMenu()");

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if(id == R.id.search_menu) {

            Intent i = new Intent(this, search.class);

            startActivity(i);

            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}