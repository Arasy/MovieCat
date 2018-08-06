package net.arasy.moviecat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private ArrayList<MovieItem> movieItemArrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public SearchAdapter(Context context){
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items){
        movieItemArrayList = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item){
        movieItemArrayList.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        movieItemArrayList.clear();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public int getViewTypeCount(){
        return 1;
    }

    @Override
    public int getCount() {
        if (movieItemArrayList== null) return 0;
        return movieItemArrayList.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return movieItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.movie_listitem, null);
            //viewHolder.vh_poster = (ImageView)convertView.findViewById(R.id.img_Poster);
            viewHolder.vh_title = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.vh_subtitle = (TextView)convertView.findViewById(R.id.tv_subtitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ///viewHolder.vh_poster.setImageDrawable(null); //new GetImage(this.context,movieItemArrayList.get(position).getPoster_path()));
        viewHolder.vh_title.setText(movieItemArrayList.get(position).getTitle());
        viewHolder.vh_subtitle.setText(movieItemArrayList.get(position).getOverview());
        return convertView;
    }

    private static class ViewHolder {
        //ImageView vh_poster;
        TextView vh_title;
        TextView vh_subtitle;
    }
}
