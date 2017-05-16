package vail.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vail.demo.R;
import vail.demo.model.City;
import vail.demo.recyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by VailWei on 2017/5/4/004.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityHolder> implements StickyRecyclerHeadersAdapter<CityHolder>{

    private List<City> mData;

    public CityListAdapter(List<City> cityList) {
        this.mData = cityList;
    }

    public void setDataSource(List<City> cityList) {
        mData.clear();
        mData.addAll(cityList);
        notifyDataSetChanged();
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        CityHolder holder = new CityHolder(view);
        holder.name = (TextView) view.findViewById(R.id.item_name);
        return holder;
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        holder.name.setText(mData.get(position).getCityName());
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getFirstLetter().charAt(0);
    }

    @Override
    public CityHolder onCreateHeaderViewHolder(ViewGroup parent, long viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
        CityHolder holder = new CityHolder(view);
        holder.name = (TextView) view.findViewById(R.id.item_header);
        return holder;
    }

    @Override
    public void onBindHeaderViewHolder(CityHolder holder, int position) {
        holder.name.setText(mData.get(position).getFirstLetter());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
