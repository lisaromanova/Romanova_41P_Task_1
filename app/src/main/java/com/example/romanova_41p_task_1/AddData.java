package com.example.romanova_41p_task_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class AddData extends AppCompatActivity {
    Connection connection;
    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    public void AddDataMethod(View v){
        EditText etName = findViewById(R.id.AddName);
        EditText etAuthor = findViewById(R.id.AddAuthor);
        EditText etGenre = findViewById(R.id.AddGenre);
        EditText etCost = findViewById(R.id.AddCost);
        try{
            String Name = etName.getText().toString();
            String Author = etAuthor.getText().toString();
            String Genre = etGenre.getText().toString();
            float Cost = Float.parseFloat(etCost.getText().toString());
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
                if(connection!=null){
                    String query = "Insert into Books Values('"+Name+"', '"+Author+"', '"+Genre+"', "+Cost+");";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                    etName.setText("");
                    etAuthor.setText("");
                    etGenre.setText("");
                    etCost.setText("");
                    Toast.makeText(this, "Данные успешно добавлены!", Toast.LENGTH_LONG).show();
                }
            else{
                ConnectionResult="Check connection";
            }
        }
        catch(Exception ex){
            Log.e(ConnectionResult, ex.getMessage());
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}