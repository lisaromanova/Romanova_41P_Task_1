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
    List<Books> sort;
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
                // your code here
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
        sort = data;
        switch(spinner.getSelectedItemPosition()){
            case 0:
                Collections.sort(sort, Comparator.comparing(Books::getName_book));
                break;
            case 1:
                Collections.sort(sort, Comparator.comparing(Books::getAuthor));
                break;
            case 2:
                Collections.sort(sort, Comparator.comparing(Books::getCost));
                break;
        }
       SetAdapter(sort);
    }

    public void Search(View v) {

    }
    public void GoAddData(View v){
        startActivity(new Intent(this, AddData.class));
    }
    public void AddItemToList(View v) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * From Books");
                while (resultSet.next()) {
                    Books tempBook = new Books
                            (
                                    Integer.parseInt(resultSet.getString("ID_book")),
                                    resultSet.getString("Name_book"),
                                    resultSet.getString("Author"),
                                    Float.parseFloat(resultSet.getString("Price")),
                                    resultSet.getString("Image")
                            );
                    data.add(tempBook);
                }
                connection.close();
            }
        } catch (Exception ex) {

        }
    }
    public void GetTextFromSql(View v){
        data = new ArrayList<Books>();
        AddItemToList(v);
        lstView = findViewById(R.id.listBooks);
        SetAdapter(data);

        /*TableLayout Books = findViewById(R.id.tbBooks);

        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            Books.removeAllViews();
            TableRow trHeader = new TableRow(MainActivity.this);
            TextView NameHeader = new TextView(MainActivity.this);
            NameHeader.setWidth(330);
            TextView AuthorHeader = new TextView(MainActivity.this);
            AuthorHeader.setWidth(210);
            TextView CostHeader = new TextView(MainActivity.this);
            CostHeader.setWidth(150);
            TextView btnHeader = new TextView(MainActivity.this);
            trHeader.setMinimumHeight(100);
            NameHeader.setText("Название");
            AuthorHeader.setText("Автор");
            CostHeader.setText("Цена");
            btnHeader.setText("Перейти");
            trHeader.addView(NameHeader);
            trHeader.addView(AuthorHeader);
            trHeader.addView(CostHeader);
            trHeader.addView(btnHeader);
            Books.addView(trHeader);
            if(connection!=null){
                String query = "Select * From Books";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    TableRow tr = new TableRow(MainActivity.this);
                    tr.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                    tr.setMinimumHeight(150);
                    TextView Name = new TextView(MainActivity.this);
                    Name.setWidth(330);
                    TextView Author = new TextView(MainActivity.this);
                    Author.setWidth(210);
                    TextView Cost = new TextView(MainActivity.this);
                    Cost.setWidth(150);
                    Button btnUpdate = new Button(MainActivity.this);
                    Resources resources = getResources();
                    btnUpdate.setBackground(resources.getDrawable(R.drawable.btn_background));
                    btnUpdate.setText("-->");
                    tr.addView(Name);
                    tr.addView(Author);
                    tr.addView(Cost);
                    tr.addView(btnUpdate);
                    btnUpdate.setTextColor(resources.getColor(R.color.white));
                    int id = Integer.parseInt(resultSet.getString(1));
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AddData.id = id;
                            GoAddData(view);
                        }
                    });
                    Books.addView(tr);

                    Name.setText(resultSet.getString(2));
                    Author.setText(resultSet.getString(3));
                    Cost.setText(resultSet.getString(4));

                }
            }
            else{
                ConnectionResult="Check Connection";
            }
        }
        catch(Exception ex){
            Log.e(ConnectionResult, ex.getMessage());
        }*/
    }

}