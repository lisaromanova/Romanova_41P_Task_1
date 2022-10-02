package com.example.romanova_41p_task_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddData extends AppCompatActivity {
    public static int id=0;
    Connection connection;
    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        EditText etName = findViewById(R.id.AddName);
        EditText etAuthor = findViewById(R.id.AddAuthor);
        EditText etCost = findViewById(R.id.AddCost);
        if(id!=0){
            try{
                TextView Header = findViewById(R.id.txtHeader);
                Header.setText("Изменение данных");
                Button btnAdd = findViewById(R.id.btnAddData);
                btnAdd.setText("Изменить");
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connection = connectionHelper.connectionClass();
                if(connection!=null){
                    String query = "Select Name_book, Author, Price From Books Where ID_book = "+id;
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        etName.setText(resultSet.getString(1));
                        etAuthor.setText(resultSet.getString(2));
                        etCost.setText(resultSet.getString(3));
                    }
                }
                else{
                    ConnectionResult="Check connection";
                }
            }
            catch(Exception ex){
                Log.e(ConnectionResult, ex.getMessage());
                Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
            }
        }else{
            Button btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    public void AddDataMethod(View v){
        EditText etName = findViewById(R.id.AddName);
        EditText etAuthor = findViewById(R.id.AddAuthor);
        EditText etCost = findViewById(R.id.AddCost);

            try{
                String Name = etName.getText().toString();
                String Author = etAuthor.getText().toString();
                float Cost = Float.parseFloat(etCost.getText().toString());
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connection = connectionHelper.connectionClass();
                if(connection!=null){
                    if(id!=0){
                        String query = "Update Books Set Name_book = '"+Name+"', Author = '"+Author+"', Price = "+ Cost+" Where ID_book = "+id;
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                        Toast.makeText(this, "Данные успешно изменены!", Toast.LENGTH_LONG).show();
                        GoViewData(v);
                    }
                    else{
                        String query = "Insert into Books Values('"+Name+"', '"+Author+"', "+Cost+");";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                        etName.setText("");
                        etAuthor.setText("");
                        etCost.setText("");
                        Toast.makeText(this, "Данные успешно добавлены!", Toast.LENGTH_LONG).show();
                    }

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

    public void DeleteData(View v){
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if(connection!=null){
                String query = "Delete From Books Where ID_book="+id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                Toast.makeText(this, "Запись успешно удалена!", Toast.LENGTH_LONG).show();
                GoViewData(v);
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
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ImageView imageView = findViewById(R.id.imgPhoto);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });

    public void GoViewData(View v){
        id=0;
        startActivity(new Intent(this, MainActivity.class));
    }
}