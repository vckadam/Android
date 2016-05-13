package edu.asu.msse.vckadam.myassing3android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<String>();

    Context ctx;

    public RecyclerAdapter(ArrayList<String> arrayList, Context ctx) {

        this.arrayList = arrayList;

        this.ctx = ctx;

    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, ctx, arrayList);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String dataProvider = arrayList.get(position);

        holder.f_name.setText(dataProvider);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView f_name;

        ArrayList<String> arrayList = new ArrayList<String>();

        Context ctx;


        public RecyclerViewHolder(View view, Context ctx, ArrayList<String> arrayList) {

            super(view);

            view.setOnClickListener(this);

            this.arrayList = arrayList;

            this.ctx = ctx;

            f_name = (TextView)view.findViewById(R.id.f_name);


        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            String movieTitle = this.arrayList.get(position);

            Intent i = new Intent(this.ctx, Movie_Information.class);

            i.putExtra("movie", movieTitle);

            this.ctx.startActivity(i);

        }
    }
}
