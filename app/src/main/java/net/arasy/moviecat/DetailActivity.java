package net.arasy.moviecat;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    public static String MOVIE_ID;
    static final String EXTRAS_ID = "extras_id";

    static ProgressBar pb;
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

        pb = (ProgressBar) findViewById(R.id.detail_pb);
        pb.setVisibility(View.VISIBLE);

        MOVIE_ID = getIntent().getStringExtra(MOVIE_ID);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_ID,MOVIE_ID);

        getLoaderManager().initLoader(0,bundle,this);

    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args){
        String movie = "";
        if(args!=null){
            movie = args.getString(EXTRAS_ID);
        }
        return new GetDetail(this,movie);
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject items){
        loadData(items);
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader){
        resetData();

    }

    private void loadData(JSONObject content){
        String link_poster="";
        try {
            link_poster = link + content.getString("poster_path");
            new GetImage(poster).execute(link_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            title.setText(content.getString("title"));
            overview.setText(content.getString("overview"));

            String teks = "";
            JSONArray genres = content.getJSONArray("genres");
            for (int i = 0; i < genres.length(); i++) {
                teks = teks + genres.getJSONObject(i).getString("name") + ", ";
            }
            teks = teks.substring(0, teks.length() - 2);
            genre.setText("Genre = "+teks);

            date.setText("Tanggal rilis = "+content.getString("release_date"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void resetData(){
        poster.setImageDrawable(null);
        title.setText("Title");
        overview.setText("overview");
        genre.setText("genres");
        date.setText("release date");
    }
}
