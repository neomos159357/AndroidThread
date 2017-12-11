package com.example.user.androidthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_image_1);
        Button button_2 = (Button) findViewById(R.id.btn_image_2);
        image = (ImageView) findViewById(R.id.iv_image);

        button.setOnClickListener(this);
        button_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_image_1) {
            try {
                show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (view.getId() == R.id.btn_image_2) {
            new LoadImageTask().execute("https://pre00.deviantart.net/4e01/th/pre/i/2016/158/8/9/junkrat_by_kin0716-da5bv5b.png");
        }
    }

    private void show() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL img_url = null;
                try {
                    img_url = new URL("https://www.petdrugsonline.co.uk/images/page-headers/cats-master-header");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap finalBmp = bmp;
                image.post(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(finalBmp);
                    }
                });
            }
        }).start();
    }

    private class LoadImageTask extends AsyncTask <String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;

            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}