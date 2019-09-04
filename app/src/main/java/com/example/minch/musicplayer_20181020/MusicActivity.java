package com.example.minch.musicplayer_20181020;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by minch on 2018-10-21.
 */

public class MusicActivity extends AppCompatActivity implements ListViewBtnAdapter.ListBtnClickListener {
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"};



    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list) {

        ListViewBtnItem item;
        int i;

        if (list == null) {
            list = new ArrayList<ListViewBtnItem>();
        }

        // 순서를 위한 i 값을 1로 초기화.
        i = 1;

        // 아이템 생성.
        item = new ListViewBtnItem();
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.sample));
        item.setTitle("예제"+Integer.toString(i));
        item.setDesc("가수"+Integer.toString(i));
        list.add(item);
        i++;

        item = new ListViewBtnItem();
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.sample2));
        item.setTitle("예제"+Integer.toString(i));
        item.setDesc("가수"+Integer.toString(i));
        list.add(item);
        i++;

        item = new ListViewBtnItem();
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.sample3));
        item.setTitle("예제"+Integer.toString(i));
        item.setDesc("가수"+Integer.toString(i));
        list.add(item);
        i++;

        item = new ListViewBtnItem();
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.sample4));
        item.setTitle("예제"+Integer.toString(i));
        item.setDesc("가수"+Integer.toString(i));
        list.add(item);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);

        final ListView listview;
        ListViewBtnAdapter adapter;

        ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>();
        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, this);

        // items 로드.
        loadItemsFromDB(items);

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO : item click
            }
        });

        EditText editTextFilter = (EditText)findViewById(R.id.editText1) ;

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                if (filterText.length() > 0) {
                    listview.setFilterText(filterText) ;
                } else {
                    listview.clearTextFilter() ;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;

    }

    @Override
    public void onListBtnClick(int position) {
        Toast.makeText(this, Integer.toString(position + 1) + " 번 곡을 재생합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
        intent.putExtra("Num", position);
        startActivity(intent);
    }
}