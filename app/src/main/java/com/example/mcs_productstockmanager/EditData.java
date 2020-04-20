package com.example.mcs_productstockmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditData extends AppCompatActivity {
    DBHelper myDB;
    EditText etname, etdesc, etqty;
    Button btnedit;
    String id;
    RVAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        myDB = new DBHelper(this);
        etname = findViewById(R.id.etname);
        etdesc = findViewById(R.id.etdesc);
        etqty = findViewById(R.id.etqty);
        btnedit = findViewById(R.id.btnsubmit);
        adaptor = new RVAdaptor(this, myDB.getData());
        Intent fromMain = getIntent();
        id = fromMain.getStringExtra("leId");
        String name = fromMain.getStringExtra("leName");
        String desc = fromMain.getStringExtra("leDesc");
        String qty = fromMain.getStringExtra("leQty");
        etname.setText(name);
        etdesc.setText(desc);
        etqty.setText(qty);
        editData();
    }
    public void editData(){
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testname = etname.getText().toString();
                String testdesc = etdesc.getText().toString();
                String testqty = etqty.getText().toString();
                if(testname.length() == 0){
                    Toast.makeText(EditData.this, "Name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(testdesc.length() == 0){
                    Toast.makeText(EditData.this, "Description cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(testqty.length() == 0 || Integer.parseInt(testqty) < 0){
                    Toast.makeText(EditData.this, "Description cannot be empty or less than 0", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isUpdate = myDB.editData(id, testname, testdesc, testqty);
                if(isUpdate){
                    Toast.makeText(EditData.this, "Data updated", Toast.LENGTH_LONG).show();
                    Intent toMain = new Intent(EditData.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                }
                else{
                    Toast.makeText(EditData.this, "Data failed to update", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}