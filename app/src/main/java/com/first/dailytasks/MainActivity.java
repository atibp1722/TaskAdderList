package com.first.dailytasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText task;
    private Button btnAdd, btnClose;
    private ListView list;
    ArrayList<String> items =  new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = findViewById(R.id.taskName);
        btnAdd = findViewById(R.id.btnAdd);
        btnClose = findViewById(R.id.btnExit);
        items = Helper.readItems(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
        list.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = task.getText().toString();
                items.add(taskName);
                task.setText("");
                task.requestFocus();
                Toast.makeText(getApplicationContext(), "Task added!!", Toast.LENGTH_LONG).show();
                //Make sure the task added is saved and visible when app reopened
                Helper.writeItems(items, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //Deleting task when user clicks on the item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete Task");
                alert.setMessage("Are you sure you want to delete this task?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Remove the task which user clicks and make sure change is saved when app reopened
                        items.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        Helper.writeItems(items, getApplicationContext());
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.create();
                alert.show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Exit Application");
                alertDialog.setMessage("Are you sure you want to exit?");
                alertDialog.setCancelable(true);
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });
    }
}