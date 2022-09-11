package com.example.romanova_41p_task_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.net.IDN;
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

    public void GoAddData(View v){
        startActivity(new Intent(this, AddData.class));
    }
    public void GetTextFromSql(View v){
        TableLayout Books = findViewById(R.id.tbBooks);

        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            Books.removeAllViews();
            TableRow trHeader = new TableRow(MainActivity.this);

            TextView NameHeader = new TextView(MainActivity.this);
            TextView AuthorHeader = new TextView(MainActivity.this);
            TextView GenreHeader = new TextView(MainActivity.this);
            TextView CostHeader = new TextView(MainActivity.this);
            NameHeader.setText("Название");
            AuthorHeader.setText("Автор");
            GenreHeader.setText("Жанр");
            CostHeader.setText("Цена");
            trHeader.addView(NameHeader, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            trHeader.addView(AuthorHeader, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            trHeader.addView(GenreHeader, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            trHeader.addView(CostHeader, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            Books.addView(trHeader);
            if(connection!=null){
                String query = "Select * From Books";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    TableRow tr = new TableRow(MainActivity.this);
                    TextView Name = new TextView(MainActivity.this);
                    TextView Author = new TextView(MainActivity.this);
                    TextView Genre = new TextView(MainActivity.this);
                    TextView Cost = new TextView(MainActivity.this);
                    tr.addView(Name, new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
                    tr.addView(Author, new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
                    tr.addView(Genre, new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
                    tr.addView(Cost, new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
                    Books.addView(tr);
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
            Log.e(ConnectionResult, ex.getMessage());
        }
    }

}