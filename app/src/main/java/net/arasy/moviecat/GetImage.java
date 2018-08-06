package net.arasy.moviecat;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.ContactsContract;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.ByteArrayInputStream;

import cz.msebera.android.httpclient.Header;

public class GetImage {//extends AsyncTaskLoader<Drawable> {
/*    private Drawable poster;
    private boolean ada=false;
    private String poster_path;

    public GetImage(final Context context, String path){
        super(context);
        onContentChanged();
        this.poster_path = path;
    }

    @Override
    protected void onStartLoading(){
        if(takeContentChanged()) forceLoad();
        else if(ada) deliverResult(poster);
    }

    @Override
    public void deliverResult(final Drawable resultImage){
        poster = resultImage;
        ada = true;
        super.deliverResult(resultImage);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(ada){
            onReleaseResources(poster);
            poster=null;
            ada =false;
        }
    }

    protected void onReleaseResources(Drawable image){
        //do nothing;
    }

    public Drawable loadInBackground(){
        SyncHttpClient client = new SyncHttpClient();

        Drawable resultImage;

        String url = "http://image.tmdb.org/t/p/w185/"+poster_path;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    resultImage = Drawable.createFromStream(new ByteArrayInputStream(responseBody),null);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return resultImage;
    }*/
}
