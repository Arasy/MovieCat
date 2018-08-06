package net.arasy.moviecat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    public static String MOVIE_ID = "0";
    TextView title;
    ImageView poster;
    TextView overview;
    TextView genre;
    TextView date;
    String link = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.detail_title);
        poster = (ImageView) findViewById(R.id.detail_poster);
        overview = (TextView) findViewById(R.id.detail_overview);
        genre = (TextView) findViewById(R.id.detail_genre);
        date = (TextView) findViewById(R.id.detail_date);

        loadData(MOVIE_ID);
    }

    private void loadData(String movie_id){
        JSONObject content= new JSONObject();
        new GetDetail(content).execute("45254"); //movie_id
        new GetImage(poster).execute(content.getString(link+"poster_path"));
        title.setText(content.getString("title"));
        overview.setText(content.getString("overview"));

        String teks = "";
        JSONArray genres = content.getJSONArray("genres");
        for (int i = 0; i < genres.length() ; i++) {
            teks = teks+genres.get(i).toString()+", ";
        }
        teks = teks.substring(0,teks.length()-2);
        genre.setText(teks);

        date.setText(content.getString("release_date"));
    }
}
