package com.example.databasestudy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail, editDate;
    Button buttonSave;
    TextView textViewResult;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = openOrCreateDatabase("app", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, email VARCHAR, birth_date VARCHAR)");

        //connect elements

        EditText editName = findViewById(R.id.input_name);
        EditText editEmail = findViewById(R.id.input_email);
        EditText editDate = findViewById(R.id.input_date);
        Button buttonSave = findViewById(R.id.app_button);
        TextView textViewResult = findViewById(R.id.textView_result);

        buttonSave.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String date = editDate.getText().toString();

            //delete all before add
            database.execSQL("DELETE FROM users");

            database.execSQL("INSERT INTO users (name, email, birth_date) VALUES ('" + name + "', '" + email + "', '" + date + "')");


            Cursor cursor = database.rawQuery("SELECT * FROM users", null);
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                result.append("Name: ").append(cursor.getString(0))
                        .append("\nEmail: ").append(cursor.getString(1))
                        .append("\nBirth Date: ").append(cursor.getString(2))
                        .append("\n\n");
            }
            cursor.close();
            textViewResult.setText(result.toString());

        });


    }
}


