package net.arasy.moviecat;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class GetMovie extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> movieItemArrayList;
    private boolean ada=false;
    private String search_query;

    public GetMovie(final Context context, String teks){
        super(context);

        onContentChanged();
        this.search_query = teks;
    }

    @Override
    protected void onStartLoading(){
        if(takeContentChanged()) forceLoad();
        else if (ada) deliverResult(movieItemArrayList);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> movieList){
        movieItemArrayList = movieList;
        ada = true;
        super.deliverResult(movieList);

        MainActivity.pb.setVisibility(View.INVISIBLE);
        Log.v("releaseAll", movieList.toString());
        MainActivity.resultTv.setText(String.format("Ada "+movieList.size()+" hasil yang ditemukan :"));
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(ada){
            onReleaseResources(movieItemArrayList);
            movieItemArrayList =null;
            ada = false;
        }
    }

    private static final String API_KEY = "a0b9f0b0efb9008e042981f7a1110058";
    private static String search_url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=";

    @Override
    public ArrayList<MovieItem> loadInBackground(){
        try{
            movieItemArrayList = new ArrayList<>();
            URL url = new URL(search_url+search_query);
            HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(urlCon.getInputStream());
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while((inputString = buffRead.readLine()) != null){
                builder.append(inputString);
            }

            JSONObject respon = new JSONObject(builder.toString());
            JSONArray results = respon.getJSONArray("results");
            Log.v("getMresult",  results.toString());

            for (int i = 0; i < results.length(); i++) {
                try {
                    JSONObject movieJSON = results.getJSONObject(i);
                    MovieItem item = new MovieItem(movieJSON);
                    Log.v("getMitem", item.toString());
                    movieItemArrayList.add(item);
                    Log.v("getMadded", String.format("item added " + i));
                } catch (Exception e){
                    Log.v("getMCantAdd",String.format("item "+ i+" can't add"));
                }
            }

            Log.v("getMall", movieItemArrayList.toString());

        } catch (Exception e){
            Log.v("getMerror",e.toString());
        }
        return movieItemArrayList;
    }

    protected void onReleaseResources(ArrayList<MovieItem> movieItems)
    {
        //do nothing;
    }
}
