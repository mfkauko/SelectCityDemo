package vail.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import vail.demo.R;
import vail.demo.model.City;

/**
 * Created by VailWei on 2017/5/9/009.
 */

public class CitySearchAdapter extends RecyclerView.Adapter<CityHolder> {

    private List<City> mData;

    public CitySearchAdapter(List<City> cityList) {
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
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
