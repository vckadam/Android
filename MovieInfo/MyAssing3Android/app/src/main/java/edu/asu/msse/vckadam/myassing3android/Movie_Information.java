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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Movie_Information extends AppCompatActivity {

    String movieTitle;

    String filename;

    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie__information);

        Intent i = getIntent();

        movieTitle = i.getExtras().getString("movie");

        play = (Button)findViewById(R.id.button);

        try {

            CourseDB db = new CourseDB((Context) this);

            SQLiteDatabase crsDB = db.openDB();

            Cursor cur = crsDB.rawQuery("select * from movieInfo1 where movieTitle=?;",new String[]{movieTitle});

        while(cur.moveToNext()) {

            EditText titleTextView = (EditText) findViewById(R.id.titleValue);

            titleTextView.setText(cur.getString(0));

            EditText yearTextView = (EditText) findViewById(R.id.yearValue);

            yearTextView.setText(cur.getString(1));

            EditText ratedTextView = (EditText) findViewById(R.id.ratedValue);

            ratedTextView.setText(cur.getString(5));

            EditText releasedTextView = (EditText) findViewById(R.id.releasedValue);

            releasedTextView.setText(cur.getString(6));

            EditText genreTextVew = (EditText) findViewById(R.id.genreValue);

            genreTextVew.setText(cur.getString(3));

            EditText runtimeTextView = (EditText) findViewById(R.id.runtimeValue);

            runtimeTextView.setText(cur.getString(2));

            EditText actorsTextView = (EditText) findViewById(R.id.actorsValue);

            actorsTextView.setText(cur.getString(4));

            EditText plotTextView = (EditText) findViewById(R.id.plotValue);

            plotTextView.setText(cur.getString(7));

            filename = cur.getString(8);

            if(filename.equals("Not available")) {

                play.setVisibility(View.INVISIBLE);

            }
            else {

                play.setVisibility(View.VISIBLE);

            }
        }
            cur.close();

            crsDB.close();

            db.close();

        }  catch (Exception ex){

            android.util.Log.w(this.getClass().getSimpleName(),"Exception  info: "+
                    ex.getMessage());
        }

       play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Movie_Information.this,VideoPlayer.class);

                i.putExtra("filename",filename);

                startActivity(i);

            }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        android.util.Log.d(this.getClass().getSimpleName(), "called onCreateOptionsMenu()");

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main2, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_gimp2) {

            Intent i = new Intent(this, MainActivity.class);

            i.putExtra("deleteMovie",movieTitle);

            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

