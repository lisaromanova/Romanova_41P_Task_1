package com.example.romanova_41p_task_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import java.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddData extends AppCompatActivity {
    Connection connection;
    Books book;
    Bundle arg;
    ImageView img;
    Bitmap bitmap=null, b;
    EditText etName, etAuthor, etCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        etName = findViewById(R.id.AddName);
        etAuthor = findViewById(R.id.AddAuthor);
        etCost = findViewById(R.id.AddCost);
        img = findViewById(R.id.imgPhoto);
        arg = getIntent().getExtras();
        b = BitmapFactory.decodeResource(AddData.this.getResources(), R.drawable.picture);
        if(arg!=null) {
            TextView Header = findViewById(R.id.txtHeader);
            Header.setText("Изменение данных");
            Button btnAdd = findViewById(R.id.btnAddData);
            btnAdd.setText("Изменить");
            book = arg.getParcelable(Books.class.getSimpleName());
            etName.setText(book.getName_book());
            etAuthor.setText(book.getAuthor());
            etCost.setText(Float.toString(book.getCost()));
            MethodsBooks m = new MethodsBooks(AddData.this);
            if (book.getImage() == null) {
                img.setImageBitmap(b);
            } else {
                bitmap = m.getUserImage(book.getImage());
                img.setImageBitmap(bitmap);
            }
        }
        else{
            img.setImageBitmap(b);
            Button btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        else{
            return "";
        }
    }

    private String Image(){
        if(bitmap==null){
            return "NULL";
        }
        else{
            String img = encodeImage(bitmap);
            return "'"+ img+"'";
        }
    }

    private boolean Check(EditText Name, EditText Author, EditText Cost){
        if(Name.getText().toString().isEmpty())  {
            Toast.makeText(this, "Заполните поле Наименование", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            if(Author.getText().toString().isEmpty()){
                Toast.makeText(this, "Заполните поле Автор", Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                if (Cost.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Заполните поле Стоимость", Toast.LENGTH_LONG).show();
                    return false;
                }
                else{
                    return true;
                }
            }
        }
    }


    public void AddDataMethod(View v) {
        if (Check(etName, etAuthor, etCost))
        {
            String Name = etName.getText().toString();
            String Author = etAuthor.getText().toString();
            float Cost = Float.parseFloat(etCost.getText().toString());
            if (arg != null) {
                String query = "Update Books Set Name_book = '" + Name + "', Author = '" + Author + "', Price = " + Cost + ", Image="+Image()+" Where ID_book = " + book.getId();
                ExecuteQuerySql(v, query, "Данные успешно изменены!");
            } else {
                String query = "Insert into Books Values('" + Name + "', '" + Author + "', " + Cost + ", "+Image()+");";
                ExecuteQuerySql(v, query, "Данные успешно добавлены!");
            }
        }
    }

    public void ExecuteQuerySql(View v, String query, String s) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                connection.close();
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                GoViewData(v);
            }
        } catch (Exception ex) {
            Log.e(ex.toString(), ex.getMessage());
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    public void DeleteData(View v){
        String s = "Delete From Books Where ID_book="+ book.getId();
        ExecuteQuerySql(v, s, "Запись успешно удалена");
    }
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }
}