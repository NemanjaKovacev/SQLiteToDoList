package com.example.nemanja.sqlitetodolist.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Item implements Parcelable {
    public int id;
    public String title;

    public Item() {
    }

    private Item(@NonNull Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @NonNull
        @Override
        public Item createFromParcel(@NonNull Parcel in) {
            return new Item(in);
        }

        @NonNull
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
