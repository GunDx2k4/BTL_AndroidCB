package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class BaiDangActivity extends AppCompatActivity {
    ArrayList<MonAn> mylist;
    MyAdapterMonAn myAdapterMonAn;
    private MonAnDAO DB;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_dang);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        listView = findViewById(R.id.listviewbaidang);
        mylist = new ArrayList<>();
        DB = new MonAnDAO(BaiDangActivity.this);
        myAdapterMonAn = new MyAdapterMonAn(BaiDangActivity.this, R.layout.layout_item, mylist);
        listView.setAdapter(myAdapterMonAn);
        readData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentct = new Intent(BaiDangActivity.this, ChiTietMonAnActivity.class);
                //Đưa bundle vào intent
                intentct.putExtra("id", mylist.get(position).getID());
                //Khởi động intent
                startActivity(intentct);

            }
        });
    }
    private  void readData(){
        var intent = getIntent();
        mylist.clear();
        mylist.addAll(DB.getData(intent.getStringExtra("username")));
        myAdapterMonAn.notifyDataSetChanged();
    }
}