package com.example.mcs_productstockmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper myDB;
    RVAdaptor adaptor;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent toAddProduct = new Intent(MainActivity.this, AddProduct.class);
        startActivity(toAddProduct);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DBHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new RVAdaptor(this, myDB.getData());
        recyclerView.setAdapter(adaptor);
        adaptor.setItemClickListener(new RVAdaptor.OnRecycleClickListener() {
            @Override
            public void onItemClick(int position) {
                String pos = String.valueOf(position+1);
                Cursor c = myDB.getSpecificData(pos);
                if(c.moveToFirst()){

                    String id = c.getString(0);
                    String name = c.getString(1);
                    String desc = c.getString(2);
                    String qty = c.getString(3);
                    Intent toEdit = new Intent(MainActivity.this, EditData.class);
                    toEdit.putExtra("leId", id);
                    toEdit.putExtra("leName", name);
                    toEdit.putExtra("leDesc", desc);
                    toEdit.putExtra("leQty", qty);
                    startActivity(toEdit);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "hm", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}