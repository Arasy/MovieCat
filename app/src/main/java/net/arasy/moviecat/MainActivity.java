package net.arasy.moviecat;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    Button searchBtn;
    EditText searchEdt;
    static TextView resultTv;
    ListView resultLv;
    static ProgressBar pb;
    SearchAdapter adapter;

    static final String EXTRAS_QUERY = "EXTRAS_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SearchAdapter(this);
        adapter.notifyDataSetChanged();

        searchBtn = (Button) findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(onClickListener);
        searchEdt = (EditText) findViewById(R.id.et_search);
        resultTv = (TextView) findViewById(R.id.tv_result);
        resultLv = (ListView) findViewById(R.id.lv_result);
        resultLv.setAdapter(adapter);

        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(View.INVISIBLE);
        String movie = searchEdt.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_QUERY,movie);

        getLoaderManager().initLoader(0,bundle,this);

    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args){
        String movie = "";
        if(args != null){
            movie = args.getString(EXTRAS_QUERY);
        }

        return new GetMovie(this,movie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> items){
        adapter.setData(items);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader){
        adapter.setData(null);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("test","tombol search dipencet");
            String movie = searchEdt.getText().toString();
            Log.v("test","editText isinya "+movie);

            if(TextUtils.isEmpty(movie)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_QUERY,movie);
            pb.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);

            resultLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(String.format("Item "+position+" diclick, idnya "+id));
                    System.out.println(view.toString());
                    Intent goToDetailIntent = new Intent(MainActivity.this,DetailActivity.class);
                    goToDetailIntent.putExtra(DetailActivity.MOVIE_ID,"345644");
                    startActivity(goToDetailIntent);
                }
            });
        }
    };

}
