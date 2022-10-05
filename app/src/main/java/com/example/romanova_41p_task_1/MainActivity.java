package com.example.romanova_41p_task_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.net.IDN;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Connection connection;
    Spinner spinner;
    List<Books> data;
    ListView lstView;
    AdapterBooks adapterBooks;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFromSql(v);
        String[]items = {"Наименование","Автор", "Цена"};
        spinner = findViewById(R.id.spSort);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Sort(v);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    public void SetAdapter(List<Books> list){
        adapterBooks = new AdapterBooks(MainActivity.this,list);
        adapterBooks.notifyDataSetInvalidated();
        lstView.setAdapter(adapterBooks);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(View v){
        switch(spinner.getSelectedItemPosition()){
            case 0:
                Collections.sort(data, Comparator.comparing(Books::getName_book));
                break;
            case 1:
                Collections.sort(data, Comparator.comparing(Books::getAuthor));
                break;
            case 2:
                Collections.sort(data, Comparator.comparing(Books::getCost));
                break;
        }
       SetAdapter(data);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Search(View v) {
        data.clear();
        EditText etSearch = findViewById(R.id.etSearchName);
        AddItemToList(v, data, "Select * From Books Where Name_book Like '"+etSearch.getText().toString()+"%'");
        Sort(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ClearFilter(View v){
        data.clear();
        EditText etSearch = findViewById(R.id.etSearchName);
        AddItemToList(v, data, "Select * From Books");
        etSearch.setText("");
        Sort(v);
    }
    public void GoAddData(View v){
        AddData.id=0;
        startActivity(new Intent(this, AddData.class));
    }
    public void AddItemToList(View v, List<Books> list, String s) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(s);
                while (resultSet.next()) {
                    Books tempBook = new Books
                            (
                                    Integer.parseInt(resultSet.getString("ID_book")),
                                    resultSet.getString("Name_book"),
                                    resultSet.getString("Author"),
                                    Float.parseFloat(resultSet.getString("Price")),
                                    resultSet.getString("Image")
                            );
                    list.add(tempBook);
                }
                connection.close();
            }
        } catch (Exception ex) {

        }
    }
    public void GetTextFromSql(View v){
        data = new ArrayList<Books>();
        AddItemToList(v, data, "Select * From Books");
        lstView = findViewById(R.id.listBooks);
        SetAdapter(data);
    }

}