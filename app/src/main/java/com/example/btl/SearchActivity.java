package com.example.btl;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ListView listViewTK;
    ArrayList<MonAn> mylist;
    MyAdapterMonAn myAdapterMonAn;
    private  MonAnDAO DB;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Tìm Kiếm");
        listViewTK = findViewById(R.id.listviewtimkiem);
        mylist = new ArrayList<>();
        DB = new MonAnDAO(SearchActivity.this);
        myAdapterMonAn = new MyAdapterMonAn(SearchActivity.this, R.layout.layout_item, mylist);
        listViewTK.setAdapter(myAdapterMonAn);
        readData();
        listViewTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentct = new Intent(SearchActivity.this, ChiTietMonAnActivity.class);

                intentct.putExtra("id", mylist.get(position).getID());
                //Khởi động intent
                startActivity(intentct);

            }
        });
    }

    private  void readData() {
        myAdapterMonAn.notifyDataSetChanged();
        mylist.clear();
        mylist.addAll(DB.getAllData());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //Căn lề cho searchview settting các thuộc tính của searchview
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //ánh xa về main_menu đã tạo
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myAdapterMonAn.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapterMonAn.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}