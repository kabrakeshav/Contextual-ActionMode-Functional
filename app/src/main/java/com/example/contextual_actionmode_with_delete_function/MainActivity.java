package com.example.contextual_actionmode_with_delete_function;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    TextView itemCounter;
    static boolean isContextualModeEnabled = false;
    int counter = 0;


    private int []arr = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
            R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t};
    String []name;
    ArrayList<Image> ImageList;
    ArrayList<Image> SelectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemCounter = findViewById(R.id.itemCounter);
        itemCounter.setText("App");
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("App");
        name = getResources().getStringArray(R.array.image_name);
        ImageList = new ArrayList<>();
        SelectionList = new ArrayList<>();

        for(int i=0;i<name.length;i++){
            Image image = new Image(arr[i], name[i]);
            ImageList.add(image);
        }

        recyclerView = findViewById(R.id.recyclerView);
        imageAdapter = new ImageAdapter(ImageList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onLongClick(View v) {
        isContextualModeEnabled = true;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.contextual_menu);
        toolbar.setBackgroundColor(getColor(R.color.colorAccent));
        imageAdapter.notifyDataSetChanged();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void MakeSelection(View v, int adapterPosition) {
        if(((CheckBox)v).isChecked()){
            SelectionList.add(ImageList.get(adapterPosition));
            counter++;
            UpdateCounter();

        }
        else{
            SelectionList.remove(ImageList.get(adapterPosition));
            counter--;
            UpdateCounter();
        }
    }

    public void UpdateCounter(){
        itemCounter.setText(counter+" Item(s) Selected");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Refresh){
            name = getResources().getStringArray(R.array.image_name);
            ImageList.clear();
            for(int i=0;i<name.length;i++){
                Image image = new Image(arr[i], name[i]);
                ImageList.add(image);
            }
            recyclerView = findViewById(R.id.recyclerView);
            imageAdapter = new ImageAdapter(ImageList, MainActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(imageAdapter);
            Toast.makeText(MainActivity.this, "List was Refreshed !", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.Info){
            Toast.makeText(MainActivity.this,
                    "     Developed By : Keshav Kabra\n(keshavkabra.official@gmail.com)",
                    Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.delete){
            if(SelectionList.isEmpty()){
                Toast.makeText(MainActivity.this, "No item was selected !", Toast.LENGTH_SHORT).show();
                RemoveContextualActionMode();
            }
            else {
                imageAdapter.RemoveItem(SelectionList);
                Toast.makeText(MainActivity.this, "Selected item(s) Deleted !", Toast.LENGTH_SHORT).show();
                RemoveContextualActionMode();
            }
        }
        else if(item.getItemId() == android.R.id.home){
            RemoveContextualActionMode();
            imageAdapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.share){
            Toast.makeText(MainActivity.this, "Not Functional Yet !", Toast.LENGTH_SHORT).show();
            RemoveContextualActionMode();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void RemoveContextualActionMode() {
        isContextualModeEnabled = false;
        itemCounter.setText("App");
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.normal_menu);
        counter = 0;
        SelectionList.clear();
        imageAdapter.notifyDataSetChanged();
        toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
