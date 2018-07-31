package com.example.allen.fileioexample;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {
    private EditText fileName;
    private EditText msgBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitButtonClicked(View v) throws IOException {

//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//        alertDialog.setTitle("FYI");
//        alertDialog.setMessage("The submit button has been clicked");
//        alertDialog.show();

        Log.i("tag","Submit button has been clicked");
        fileName = (EditText)findViewById(R.id.fileName);
        msgBody = (EditText)findViewById(R.id.msgBody);
        Log.i("EditText", msgBody.getText().toString());

        if (isExternalStorageWritable()){
            File file = new File(this.getExternalFilesDir(null), fileName.getText().toString() + ".txt");
            if (file.exists()){
                Log.i("tag","File has already been created");
                Context context = getApplicationContext();
                CharSequence text = "Error: File name already in existence";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
            Log.i("tag", "You have just created a new file");
            Context context = getApplicationContext();
            CharSequence text = "Writing to file...";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            FileOutputStream fileOutput = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutput);
            outputStreamWriter.write(msgBody.getText().toString());
            outputStreamWriter.flush();
            fileOutput.getFD().sync();
            outputStreamWriter.close();
        }
    }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

}
