package com.example.mustu.androidphotos31;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button openAlbum, deleteAlbum;
    EditText edit;
    public static ArrayList<Album> albums = new ArrayList<Album>();
    public ArrayAdapter<Album> adapter;
    //public AlbumListAdapter adapters;
    public static final int EDIT_ALBUM_CODE = 1;
    public static final int ADD_ALBUM_CODE = 2;
    public static final int OPEN_ALBUM_CODE = 3;
    public static final int DELETE_ALBUM_CODE = 4;

    int positions = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.album_list);
        //handleIntent(getIntent());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showAlbum(position);
                positions = position;
                Toast.makeText(getApplicationContext(), "Positions at: " + positions + " was clicked", Toast.LENGTH_SHORT).show();
                view.setBackgroundColor(Color.LTGRAY);
            }
        });
    }

    public void Create(View view){
        Intent intent = new Intent(this, AddEditAlbum.class);
        startActivityForResult(intent, ADD_ALBUM_CODE);
    }

    public void OpenAlbum(View view){
        Intent intent = new Intent(this, addPhoto.class);
        Bundle b = new Bundle();
        if(positions !=-1) {
            b.putString("b",albums.get(positions).getAlbumName());
            b.putParcelableArrayList("a", albums.get(positions).getPhotoList());
            intent.putExtras(b);
            startActivityForResult(intent, OPEN_ALBUM_CODE);
        }
    }
    /*
    public void handleIntent(Intent intent){
        showAlbumList();
    }

    private void showAlbumList(){
        listView.setAdapter(new ArrayAdapter<Album>(this, R.layout.album, albums));

    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode,intent);
       switch(requestCode) {
           case(EDIT_ALBUM_CODE):{
               Bundle bundle = intent.getExtras();
               if (bundle == null) {
                   return;
               }

               String name = bundle.getString(AddEditAlbum.ALBUM_NAME);
               int index = bundle.getInt(AddEditAlbum.ALBUM_INDEX);
               Album album = albums.get(index);
               album.albumName = name;
               break;
           }
           case(ADD_ALBUM_CODE): {

               Bundle bundle = intent.getExtras();
               if (bundle == null) {
                   return;
               }
               String name = bundle.getString(AddEditAlbum.ALBUM_NAME);
               ArrayList<Photo> photos = new ArrayList<>();
               albums.add(new Album(name, photos));
               break;
               //Toast.makeText(getApplicationContext(), positions, Toast.LENGTH_LONG).show();
           }
           case(OPEN_ALBUM_CODE) :{
               if(resultCode == addPhoto.RESULT_OK) {
                   Bundle b = intent.getExtras();
                   albums.set(positions,(Album) b.getParcelable("album"));
               }
               Toast.makeText(getApplicationContext(), albums.get(positions).getPhotoCount() + " hi", Toast.LENGTH_LONG).show();
               break;
           }
       }
        adapter = new ArrayAdapter<Album>(this, R.layout.album, albums);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*
    private void showAlbum(int position){
        Bundle bundle = new Bundle();
        Album album = albums.get(position);
        bundle.putInt(AddEditAlbum.ALBUM_INDEX, position);
        bundle.putString(AddEditAlbum.ALBUM_NAME, album.albumName);
        Intent intent = new Intent(this, AddEditAlbum.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_ALBUM_CODE);
    }
    */

    public void rename(View view){
        Bundle bundle = new Bundle();
        Album album = albums.get(positions);
        bundle.putInt(AddEditAlbum.ALBUM_INDEX, positions);
        bundle.putString(AddEditAlbum.ALBUM_NAME, album.albumName);
        Intent intent = new Intent(this, AddEditAlbum.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_ALBUM_CODE);
    }

    public void delete(View view){
        albums.remove(positions);
        adapter = new ArrayAdapter<Album>(this, R.layout.album, albums);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
