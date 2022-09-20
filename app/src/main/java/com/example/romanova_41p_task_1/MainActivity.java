package com.example.romanova_41p_task_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
            TextView CostHeader = new TextView(MainActivity.this);
            NameHeader.setText("Название");
            AuthorHeader.setText("Автор");
            CostHeader.setText("Цена");
            trHeader.addView(NameHeader, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            trHeader.addView(AuthorHeader, new TableRow.LayoutParams(
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
                    tr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    TextView Name = new TextView(MainActivity.this);
                    params.weight = 3.0f;
                    Name.setLayoutParams(params);
                    tr.addView(Name);
                    TextView Author = new TextView(MainActivity.this);
                    params.weight = 2.0f;
                    Author.setLayoutParams(params);
                    tr.addView(Author);
                    TextView Cost = new TextView(MainActivity.this);
                    params.weight = 1.0f;
                    Cost.setLayoutParams(params);
                    tr.addView(Cost);
                    Button btnUpdate = new Button(MainActivity.this);
                    btnUpdate.setLayoutParams(params);
                    tr.addView(btnUpdate);
                    Resources resources = getResources();
                    btnUpdate.setBackground(resources.getDrawable(R.drawable.btn_background));
                    btnUpdate.setText("Обновить");
                    btnUpdate.setTextSize(12);
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
        }
    }

}