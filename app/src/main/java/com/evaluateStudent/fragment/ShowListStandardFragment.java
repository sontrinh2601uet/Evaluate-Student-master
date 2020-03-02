package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evaluateStudent.R;
import com.evaluateStudent.data.Book;
import com.evaluateStudent.data.RecyclerViewAdapter;

import java.util.ArrayList;

public class ShowListStandardFragment extends Fragment {

    public static ShowListStandardFragment createInstance() {
        return new ShowListStandardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_standard, null);

        ArrayList<Book> lstBook = new ArrayList<>();

        lstBook.add(new Book("The Vegitarian", "Categorie Book", "Description book", R.drawable.qrcode));
        lstBook.add(new Book("The Wild Robot", "Categorie Book", "Description book", R.drawable.qrcode));
        lstBook.add(new Book("Maria Semples", "Categorie Book", "Description book", R.drawable.qrcode));
        lstBook.add(new Book("The Martian", "Categorie Book", "Description book", R.drawable.qrcode));
        lstBook.add(new Book("He Died with...", "Categorie Book", "Description book", R.drawable.qrcode));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstBook, getActivity().getSupportFragmentManager());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}
