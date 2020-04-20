package com.example.mcs_productstockmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    DBHelper myDB;
    EditText etname, etdesc, etqty;
    Button btnadd;
    RVAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        myDB = new DBHelper(this);
        etname = findViewById(R.id.etname);
        etdesc = findViewById(R.id.etdesc);
        etqty = findViewById(R.id.etqty);
        btnadd = findViewById(R.id.btnadd);
        adaptor = new RVAdaptor(this, myDB.getData());
        addData();
    }
    public void addData(){
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etname.getText().toString();
                String desc = etdesc.getText().toString();
                String qty = etqty.getText().toString();
                if(name.length() == 0){
                    Toast.makeText(AddProduct.this, "Name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(desc.length() == 0){
                    Toast.makeText(AddProduct.this, "Description cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(qty.length() == 0 || Integer.parseInt(qty) < 0){
                    Toast.makeText(AddProduct.this, "Description cannot be empty or less than 0", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isAdded = myDB.insertData(name, desc, qty);
                if(isAdded){
                    Toast.makeText(AddProduct.this, "Data successfully added", Toast.LENGTH_LONG).show();
                    Intent toMain = new Intent(AddProduct.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                }
                else{
                    Toast.makeText(AddProduct.this, "Data failed to be added", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}