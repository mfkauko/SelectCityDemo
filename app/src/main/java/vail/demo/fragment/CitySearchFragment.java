package vail.demo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import vail.demo.R;
import vail.demo.adapter.CitySearchAdapter;
import vail.demo.model.City;
import vail.demo.model.CityModel;
import vail.demo.recyclerview.CustomDividerItemDecoration;
import vail.demo.util.DisplayUtil;

/**
 * Created by VailWei on 2017/5/8/008.
 */

public class CitySearchFragment extends BaseFragment {

    public static CitySearchFragment newInstance(List<City> list) {
        CitySearchFragment fragment = new CitySearchFragment();
        Bundle bundle = new Bundle();
        CityModel model = new CityModel();
        model.cityList = list;
        bundle.putSerializable(KEY_DATA, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private CitySearchAdapter mAdapter;
    private List<City> mList;

    @Override
    protected void getBundleData(Bundle bundle) {
        CityModel model = (CityModel) bundle.getSerializable(KEY_DATA);
        if(model != null) {
            mList = model.cityList;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_search;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.city_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getHoldingActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new CustomDividerItemDecoration(getHoldingActivity(), LinearLayoutManager.HORIZONTAL,
                DisplayUtil.dpToPx(getHoldingActivity(), 0.5f), getResources().getColor(R.color.standard_gray_color_1)));
        mAdapter = new CitySearchAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
