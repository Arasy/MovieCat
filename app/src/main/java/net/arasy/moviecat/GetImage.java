package net.arasy.moviecat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


import java.io.InputStream;
import java.net.URL;


public class GetImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public GetImage(ImageView imageView){
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls){

        String url = urls[0];
        Bitmap bmp = null;

        try{
            InputStream inputStream = new URL(url).openStream();
            bmp = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bmp;

    }

    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
