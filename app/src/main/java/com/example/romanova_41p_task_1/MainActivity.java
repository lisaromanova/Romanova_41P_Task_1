package com.example.romanova_41p_task_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void GetTextFromSql(View v){
        TableLayout Books = findViewById(R.id.tbBooks);
        Button View = findViewById(R.id.btnView);
        Button Add = findViewById(R.id.btnAdd);

        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection!=null){
                String query = "Select * From Books";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    TableRow tr = new TableRow(MainActivity.this);
                    TextView Id = new TextView(MainActivity.this);
                    TextView Name = new TextView(MainActivity.this);
                    TextView Author = new TextView(MainActivity.this);
                    TextView Genre = new TextView(MainActivity.this);
                    TextView Cost = new TextView(MainActivity.this);
                    tr.addView(Id);
                    tr.addView(Name);
                    tr.addView(Author);
                    tr.addView(Genre);
                    tr.addView(Cost);
                    Books.addView(tr);
                    Id.setText(resultSet.getString(1));
                    Name.setText(resultSet.getString(2));
                    Author.setText(resultSet.getString(3));
                    Genre.setText(resultSet.getString(4));
                    Cost.setText(resultSet.getString(5));

                }
            }
            else{
                ConnectionResult="Check Connection";
            }
        }
        catch(Exception ex){

        }
    }

}