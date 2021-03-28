package com.example.android_test_3_22_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    recy_Adaptor adaptor;
    ArrayList<items> itemsArrayList;
    RecyclerView recyclerView;

    void calling() {


        recyclerView = findViewById(R.id.res);
        itemsArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemsArrayList.add(new items("item" + i, R.drawable.icons));
        }
        adaptor = new recy_Adaptor(this, itemsArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adaptor);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(s);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adaptor.setOnClickListener(new recy_Adaptor.OnItemClickListener() {
            @Override
            public void Remove_item(int position) {
                Toast.makeText(getApplicationContext(), "removed: " + position, Toast.LENGTH_SHORT).show();
                itemsArrayList.remove(position);
                adaptor.notifyItemRemoved(position);

            }

            @Override
            public void Edit_item(int position) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

            }
        });


    }

    ItemTouchHelper.SimpleCallback s = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int postion = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    itemsArrayList.remove(postion);
                    adaptor.notifyItemRemoved(postion);
            }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calling();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_src, menu);
        MenuItem item = menu.findItem(R.id.my_search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptor.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}