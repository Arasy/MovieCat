package net.arasy.moviecat;

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

public class GetDetail extends AsyncTask<String,Void,JSONObject> {

    JSONObject result;
    public GetDetail(JSONObject object){
        this.result = object;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String movie_id = strings[0];
        final String API_KEY = "a0b9f0b0efb9008e042981f7a1110058";
        String search_url = String.format("https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=" + API_KEY);

        try{
            URL url = new URL(search_url);
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
            Log.v("getDetailError", e.toString());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject object) {
        super.onPostExecute(object);
    }
}
