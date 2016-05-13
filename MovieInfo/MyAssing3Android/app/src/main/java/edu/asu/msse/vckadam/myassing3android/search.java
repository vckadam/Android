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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;

public class search extends AppCompatActivity {

    private Menu menu;

    private SearchView searchView;

    String searchString;

    Handler aHandler;

    JsonRPCRequestViaHttp names, namesG;

    Context ctx;

    EditText title, year, rated, released, runtime, genre, actors, plot;

    String filename;

    JSONObject selectedMovie;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.filename = "Not available";

        ctx = getApplicationContext();

        setContentView(R.layout.activity_search);

        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MovieDescription m = new MovieDescription(title.getText().toString(), year.getText().toString(), rated.getText().toString(), released.getText().toString(), runtime.getText().toString(), genre.getText().toString(), actors.getText().toString(), plot.getText().toString(),search.this.filename);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                i.putExtra("searchMovie", m);

                startActivity(i);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(this.getClass().getSimpleName(), "in onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.menu_main3, menu);

        this.menu = menu;


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (android.widget.SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(

                new SearchView.OnQueryTextListener() {

                    public boolean onQueryTextChange(String query) {
                        return false;
                    }

                    public boolean onQueryTextSubmit(String query) {

                        android.util.Log.d(this.getClass().getSimpleName(), "in onQueryTextChange: " + query);

                        try {
                            title = (EditText) findViewById(R.id.titleValue);

                            year = (EditText) findViewById(R.id.yearValue);

                            released = (EditText) findViewById(R.id.releasedValue);

                            genre = (EditText) findViewById(R.id.genreValue);

                            rated = (EditText) findViewById(R.id.ratedValue);

                            runtime = (EditText) findViewById(R.id.runtimeValue);

                            actors = (EditText) findViewById(R.id.actorsValue);

                            plot = (EditText) findViewById(R.id.plotValue);

                            aHandler = new Handler();

                            namesG = new JsonRPCRequestViaHttp(new URL("http://10.0.2.2:8080/"), aHandler, "get", "[\"" + query + "\"]");
                            namesG.start();

                            namesG.join();

                            search.this.selectedMovie = namesG.jo.getJSONObject("result");

                            if ((search.this.selectedMovie.get("Title").toString()).equals("Unknown")) {

                                StringBuilder sb = new StringBuilder();

                                for(int i = 0; i < query.length(); i++) {

                                     if(query.charAt(i) != ' ')  {

                                      sb.append(query.charAt(i));

                                     }

                                    else {

                                      sb.append('+');

                                     }

                                }
                                String str = "http://www.omdbapi.com/?t=" + sb.toString() + "&y=&plot=short&r=json";

                                names = new JsonRPCRequestViaHttp(new URL(str), aHandler, "get", "[\"" + query + "\"]");

                                names.start();

                                names.join();

                                title.setText(names.jo.get("Title").toString());

                                year.setText(names.jo.get("Year").toString());

                                released.setText(names.jo.get("Released").toString());

                                genre.setText(names.jo.get("Genre").toString());

                                rated.setText(names.jo.get("Rated").toString());

                                runtime.setText(names.jo.get("Runtime").toString());

                                actors.setText(names.jo.get("Actors").toString());

                                plot.setText(names.jo.get("Plot").toString());

                            } else {
                                title.setText(search.this.selectedMovie.get("Title").toString());

                                year.setText(search.this.selectedMovie.get("Year").toString());

                                released.setText(search.this.selectedMovie.get("Released").toString());

                                genre.setText(search.this.selectedMovie.get("Genre").toString());

                                rated.setText(search.this.selectedMovie.get("Rated").toString());

                                runtime.setText(search.this.selectedMovie.get("Runtime").toString());

                                actors.setText(search.this.selectedMovie.get("Actors").toString());

                                plot.setText(search.this.selectedMovie.get("Plot").toString());

                                search.this.filename = search.this.selectedMovie.get("Filename").toString();
                            }

                        } catch (Exception ex) {
                            android.util.Log.w(this.getClass().getSimpleName(), "Exception constructing URL" + " message " + ex.getMessage());
                        }

                        return false;
                    }

                }
        );
        return true;
    }

}
