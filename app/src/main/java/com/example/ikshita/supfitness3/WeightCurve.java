package com.example.ikshita.supfitness3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;


public class WeightCurve extends Fragment {

    DBManagerWeight myDB;
    GraphView graph;
    LineGraphSeries<DataPoint> coordinates;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabweightcurve, container, false);



        myDB = new DBManagerWeight(getActivity());
       db = myDB.getWritableDatabase();

       graph = (GraphView) rootView.findViewById(R.id.graph);

       //getData();

        try{
            coordinates = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(1,2),
                    new DataPoint(2, 10),
                    new DataPoint(3,50),
                    new DataPoint(4,1),
                    new DataPoint(8, 10),
                    new DataPoint(10,50),
                    new DataPoint(11,2),
                    new DataPoint(12, 10),
                    new DataPoint(14,50),
                    new DataPoint(16,1),
                    new DataPoint(18, 10),
                    new DataPoint(20,50),
                    new DataPoint(21,2),
                    new DataPoint(22, 10),
                    new DataPoint(24,50),
                    new DataPoint(26,1),
                    new DataPoint(28, 10),
                    new DataPoint(30,50)

            });

            graph.addSeries(coordinates);
        }
        catch (Exception e){
            Toast.makeText(getActivity(), getData().length, Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private DataPoint[] getData() {
        //Read data from db
        String[] columns = {"WEIGHT","DATE"};
       //Cursor cursor = db.rawQuery("SELECT * FROM supfitness_table", columns,null);

        try{
            Cursor cursor = db.query("supfitness_table",columns,null,null,null,null,null);

            //Cursor cursor = db.rawQuery("SELECT * FROM supfitness_table", columns,null);

            DataPoint[] dp = new DataPoint[cursor.getCount()];

            for(int i = 0; i<cursor.getCount();i++){
                cursor.moveToNext();
                Date date = new Date(cursor.getLong(2)*1000);
                long milliseconds =date.getTime();

                dp[i]=new DataPoint(cursor.getDouble(1),milliseconds);
               //DataPoint data =new DataPoint(cursor.getDouble(1),cursor.getInt(0));
              //dp[i]= data;

            }
            return dp;
        }

        catch (Exception e){
            System.out.println("can't query" + e.getMessage());
            return null;

        }

    }
}

