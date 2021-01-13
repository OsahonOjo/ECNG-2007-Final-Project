package com.osahonojo.sharedpreferencesactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    private String FILE_PERSON = "file_person";

    private Person person;

    private EditText firstName, lastName, school;
    private Button save, clear, load, delete;
    private ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        school = findViewById(R.id.school);
        save = findViewById(R.id.buttonSave);
        clear = findViewById(R.id.buttonClear);
        load = findViewById(R.id.buttonLoad);
        delete = findViewById(R.id.buttonDelete);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = MainActivity.this.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || school.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Form incomplete! Cannot save.", Toast.LENGTH_SHORT).show();
                }
                else {
                    person = new Person(firstName.getText().toString(), lastName.getText().toString(), school.getText().toString());
                    editor.putString(FILE_PERSON, gson.toJson(person));
                    editor.apply();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person tmpPerson = getPerson();
                if (tmpPerson != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    clearForm();
                    progressBar.setVisibility(View.GONE);
                    firstName.setText(tmpPerson.getFname());
                    lastName.setText(tmpPerson.getLname());
                    school.setText(tmpPerson.getSchool());
                }
                else {
                    Toast.makeText(MainActivity.this, "No data found. Complete and save the form.", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person tmpPerson = getPerson();
                if (tmpPerson != null) {
                    editor.clear();
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Persisted data deleted.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No data found.", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    private void clearForm() {
        firstName.setText("");
        lastName.setText("");
        school.setText("");
    }

    private Person getPerson(){
        Type type = new TypeToken<Person>(){}.getType();
        Person tmpPerson = gson.fromJson(sharedPreferences.getString(FILE_PERSON, null), type);
        return tmpPerson;
    }
}