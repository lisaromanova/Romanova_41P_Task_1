package com.example.romanova_41p_task_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterBooks extends BaseAdapter{
    Context mContext;
    List<Books> booksList;

    public AdapterBooks(Context mContext, List<Books> booksList) {
        this.mContext = mContext;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int i) {
        return booksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return booksList.get(i).getId();
    }

    private Bitmap getUserImage(String encodedImg)
    {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext,R.layout.item_book,null);
        TextView Name_book = v.findViewById(R.id.txtNameBook);
        TextView Author = v.findViewById(R.id.txtAuthor);
        TextView Cost = v.findViewById(R.id.txtCost);
        ImageView Img = v.findViewById(R.id.img);
        Books book = booksList.get(i);
        Name_book.setText(book.getName_book());
        Author.setText(book.getAuthor());
        Cost.setText(Float.toString(book.getCost()));
        Img.setImageBitmap(getUserImage(book.getImage()));

        return v;
    }
}
