package net.arasy.moviecat;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetDetail extends AsyncTaskLoader<JSONObject> {

    private JSONObject result;
    private boolean ada=false;
    private String movie_id;

    public GetDetail(final Context context, String teks){
        super(context);
        onContentChanged();
        this.movie_id = teks;
        Log.v("getDetailDeclare", teks);
    }

    @Override
    protected void onStartLoading(){
        if(takeContentChanged()) forceLoad();
        else if (ada) deliverResult(result);
    }

    @Override
    public void deliverResult(final JSONObject object){
        result = object;
        ada = true;
        super.deliverResult(object);

        DetailActivity.pb.setVisibility(View.INVISIBLE);
        Log.v("detailDeliverResult", result.toString());
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(ada){
            onReleaseResources(result);
            result = null;
            ada = false;
        }
    }

    private final String API_KEY = "a0b9f0b0efb9008e042981f7a1110058";
    private String search_url;

    @Override
    public JSONObject loadInBackground(){
        try{
            search_url = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=" + API_KEY;
            URL url = new URL(search_url);
            Log.v("getDetailLoad", search_url);
            HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(urlCon.getInputStream());
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while((inputString = buffRead.readLine()) != null){
                builder.append(inputString);
            }

            result = new JSONObject(builder.toString());

            Log.v("getDetailResult",  result.toString());
        } catch (Exception e){
            Log.v("getDetailError",e.toString());
        }
        return result;
    }

    protected void onReleaseResources(JSONObject object)
    {
        //do nothing;
    }

}
