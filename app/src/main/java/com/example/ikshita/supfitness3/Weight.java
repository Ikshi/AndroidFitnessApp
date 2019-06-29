package com.example.ikshita.supfitness3;


import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Weight extends Fragment {
    DBManagerWeight myDB;

    private Button btnAdd;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabweight, container, false);





       btnAdd = (Button) view.findViewById(R.id.buttonadd);

       btnAdd.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
               View mView = getLayoutInflater().inflate(R.layout.dialog_addweight_test,null);
               final EditText EdtAddWeight = (EditText) mView.findViewById(R.id.editAddWeight);
              // final EditText EdtAddDate = (EditText) mView.findViewById(R.id.editAddDate);
               Button btnDialogAdd = (Button) mView.findViewById(R.id.btnaddweight);

               //dialog box
               btnDialogAdd.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       String newWeight = EdtAddWeight.getText().toString();
                      // String newDate = EdtAddDate.getText().toString();
                       if(EdtAddWeight.length() != 0 ){
                           Toast.makeText(getActivity(), newWeight, Toast.LENGTH_LONG).show();

                           Double doubleNewWeight = Double.parseDouble(newWeight);
                           AddData(doubleNewWeight);
                           GetData();
                           //GetDate();
                           EdtAddWeight.setText("");


                       }else {
                           Toast.makeText(getActivity(), "Enter weight", Toast.LENGTH_LONG).show();

                       }

                   }
               });

               mBuilder.setView(mView);
               AlertDialog dialog = mBuilder.create();
               dialog.show();

           }
        });

        return view;
    }

    //method to add weight to db
    public void AddData(Double newEntry){

      // Toast.makeText(getActivity(), newEntry, Toast.LENGTH_LONG).show();
        DBManagerWeight myDB = new DBManagerWeight(getActivity());
        boolean insertData = myDB .addData(newEntry);
        if(insertData == true){
            Toast.makeText(getActivity(), "Weight saved!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Error Weight not saved!", Toast.LENGTH_LONG).show();
        }




    }

    //get weight from db
    public void GetData(){
        ListView listView = (ListView) getActivity().findViewById(R.id.listweight);
        myDB = new DBManagerWeight(getActivity());

        ArrayList<String>theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "db was empty", Toast.LENGTH_LONG).show();
        }else{
            while (data.moveToNext()){

                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
    }

  /*  public void GetDate(){
        ListView listView = (ListView) getActivity().findViewById(R.id.listweight);
        myDB = new DBManagerWeight(getActivity());

        ArrayList<String>theList = new ArrayList<>();
        Cursor data = myDB.getListDates();

        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "db was empty", Toast.LENGTH_LONG).show();
        }else{
            while (data.moveToNext()){
                Date date = new Date(data.getLong(2));
                String strDate = date.toString();

              //  Toast.makeText(getActivity(), data.getString(2), Toast.LENGTH_LONG).show();

                theList.add(strDate);
               ListAdapter listAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
    }*/
}
