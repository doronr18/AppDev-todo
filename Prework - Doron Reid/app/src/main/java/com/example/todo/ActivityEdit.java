package com.example.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityEdit extends AppCompatActivity {


    EditText etItem;
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etItem = findViewById(R.id.etItem);
        btnsave = findViewById(R.id.btnsave);
        getSupportActionBar().setTitle("Edit Item");
        etItem.setText(getIntent().getStringExtra(MainActivity.Key_Item_Text));
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.Key_Item_Text, etItem.getText().toString());
                intent.putExtra(MainActivity.Key_Item_Position, getIntent().getExtras().getInt(MainActivity.Key_Item_Position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

}