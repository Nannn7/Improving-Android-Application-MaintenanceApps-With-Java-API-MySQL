package com.example.maintenanceapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FullImageActivity extends Activity {
    private static final String TAG = "FULLIMAGE_ACTIVITY";
    PhotoView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);

        Intent i = getIntent();
        String url = i.getExtras().getString("url");
        Log.d(TAG, "url:" + url);
        String test = url.substring(0, 8);
        Log.d(TAG, "test: " + test);

        img = (PhotoView) findViewById(R.id.image);

        if (test.equals("/storage")) {
            File pict = new File(url);
            Picasso.get()
                    .load(pict)
                    .placeholder(R.drawable.loader)
                    .fit()
                    .centerInside()
                    .into(img);
        } else {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.loader)
                    .fit()
                    .centerInside()
                    .into(img);
        }
    }
}
