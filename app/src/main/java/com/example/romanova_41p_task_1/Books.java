package com.example.romanova_41p_task_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Books implements Parcelable {
    private int id;
    private String Name_book;
    private String Author;
    private float Cost;

    protected Books(Parcel in) {
        id = in.readInt();
        Name_book = in.readString();
        Author = in.readString();
        Cost = in.readFloat();
        Image = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setName_book(String name_book) {
        Name_book = name_book;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setCost(float cost) {
        Cost = cost;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId() {
        return id;
    }

    public String getName_book() {
        return Name_book;
    }

    public String getAuthor() {
        return Author;
    }

    public float getCost() {
        return Cost;
    }

    public String getImage() {
        return Image;
    }

    public Books(int id, String name_book, String author, float cost, String image) {
        this.id = id;
        Name_book = name_book;
        Author = author;
        Cost = cost;
        Image = image;
    }

    private String Image;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Name_book);
        parcel.writeString(Author);
        parcel.writeFloat(Cost);
        parcel.writeString(Image);
    }
}
