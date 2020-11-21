package com.example.encryptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.encryptionlibrary.Encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    TextView text, text2;
    ImageView img, img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
        img = findViewById(R.id.image1);
        img1 = findViewById(R.id.image2);

        Encryption encryption = null;
        try {
            encryption = Encryption.getInstance(getApplicationContext());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Log.d("enn", encryption.ImageViewEncryption(img));
        img1.setImageBitmap(encryption.ImageViewDecryption(encryption.ImageViewEncryption(img)));
        Log.d("enn", encryption.AESDecryptionOrNull(encryption.AESEncryptionOrNull("lana")));
//        String name= encryption.AESEncryptionOrNull(getString(R.string.content));
//        text2.setText("Encrypted text");
//        text.setText(encryption.AESEncryptionOrNull(name));

    }
}