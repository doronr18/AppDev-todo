package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;


public class MainActivity extends AppCompatActivity {

    public static final String Key_Item_Text = "item_Text";
    public static final String Key_Item_Position = "item_position";
    public static final int Edit_Text_Code = 20;
    List<String> items;
    Button button4;
    EditText edittx;
    RecyclerView edittxt;
    new_adapter itemsAdapter;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button4 = findViewById(R.id.button4);
        edittx = findViewById(R.id.edittxt);
        edittxt = findViewById(R.id.edtot);


        loadItems();


        new_adapter.OnLongClickListener onLongClickListener = new new_adapter.OnLongClickListener() {

            @Override
            public void OnItemLongClicked(int position) {
                // delete the item long pressed
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        new_adapter.OnClickListener onClickListener = new new_adapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // delete the item from the model 
                Log.d( "MainActivity", "Signle Click at position" + position);
                // Notify the adapter
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(Key_Item_Text, items.get(position));
                i.putExtra(Key_Item_Position, position);
                startActivityForResult(i, Edit_Text_Code);
            }
        };



        itemsAdapter = new new_adapter(items, onLongClickListener, onClickListener);
        edittxt.setAdapter(itemsAdapter);
        edittxt.setLayoutManager(new LinearLayoutManager(this));

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoitem = edittx.getText().toString();
                //add item to the model
                items.add(todoitem);
                //notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                edittx.setText("");
                Toast.makeText(getApplicationContext(), "item was added", Toast.LENGTH_SHORT).show();
                saveItems();


            }
        });

    }
    public void onActivityReenter(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Edit_Text_Code);
        String itemText = data.getStringExtra(Key_Item_Text);
        int position = data.getExtras().getInt(Key_Item_Position);
        items.set(position, itemText);
        itemsAdapter.notifyItemChanged(position);
        saveItems();
        Toast.makeText(getApplicationContext(),"Item updated succefully!", Toast.LENGTH_SHORT).show();

    }
        {
        Log.w( "MainActivity", "Uknown call to onActivityResult" );
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // This function will load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivty", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // this function saves items by writing them into the data
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}