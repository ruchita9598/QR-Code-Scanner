package com.kushang_android_examples.qrscanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;

    private static final String FILE_NAME = "Scanned Data.csv";
    String scanned_data;
    String directory = "QR Scanner.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScannerView = new ZXingScannerView(this);

        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {
        scanned_data = result.getText();
        Main.resultTextview.setText(scanned_data);
        onBackPressed();

        if(isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            scanned_data = scanned_data + "\n";

            File textFile = new File(Environment.getExternalStorageDirectory(), directory);

            try (FileOutputStream fos = new FileOutputStream(textFile, true))
            {
                fos.write(scanned_data.getBytes());
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Toast.makeText(this,"Data Saved", Toast.LENGTH_LONG).show();

        /*
        try
        {
            fos = openFileOutput(FILE_NAME,Context.MODE_APPEND);
            fos.write(data_to_write.getBytes());
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }*/
    }

    private boolean isExternalStorageWritable()
    {
       if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
       {
            return true;
       }
       else
       {
           return false;
       }
    }

    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onPause(){
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume(){
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
