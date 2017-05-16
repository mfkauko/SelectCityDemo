package vail.demo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vail.demo.R;
import vail.demo.adapter.CityListAdapter;
import vail.demo.model.City;
import vail.demo.model.CityModel;
import vail.demo.recyclerview.CustomDividerItemDecoration;
import vail.demo.recyclerview.HeaderAndFooterWrapper;
import vail.demo.recyclerview.StickyRecyclerHeadersDecoration;
import vail.demo.util.DisplayUtil;
import vail.demo.view.SliderView;

/**
 * Created by VailWei on 2017/5/8/008.
 */

public class CityListFragment extends BaseFragment {

    public static CityListFragment newInstance(List<City> list) {
        CityListFragment fragment = new CityListFragment();
        Bundle bundle = new Bundle();
        CityModel model = new CityModel();
        model.cityList = list;
        bundle.putSerializable(KEY_DATA, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private CityListAdapter mAdapter;
    private List<City> mList;
    private SliderView mSliderView;
    private Map<String, Integer> mIndexer = new HashMap<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private List<String> mAlphas;

    @Override
    protected void getBundleData(Bundle bundle) {
        CityModel model = (CityModel) bundle.getSerializable(KEY_DATA);
        if(model != null) {
            mList = model.cityList;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_list;
    }

    @Override
    protected void initView(View view) {
        mSliderView = (SliderView) view.findViewById(R.id.city_slider);
        mSliderView.setOnItemClickListener(new SliderView.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if(mIndexer.get(s) != null) {
                    ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mIndexer.get(s), 0);
                }
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.city_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getHoldingActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new CustomDividerItemDecoration(getHoldingActivity(), LinearLayoutManager.HORIZONTAL,
                DisplayUtil.dpToPx(getHoldingActivity(), 0.5f), getResources().getColor(R.color.standard_gray_color_1)));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        AsyncTaskUtil.doAsync(new AsyncCallBack() {
//            @Override
//            public void doInBackground() {
//                DBManager dbManager = new DBManager(getHoldingActivity());
//                SQLiteDatabase sqLiteDatabase = dbManager.initDataBaseManager(getHoldingActivity().getPackageName());
//                mList = dbManager.getAllCity(sqLiteDatabase);
//            }
//
//            @Override
//            public void onPostExecute() {
                if(mAdapter == null) {
                    mAdapter = new CityListAdapter(mList);
//                    mRecyclerView.setAdapter(mAdapter);
                    View headerView = LayoutInflater.from(getHoldingActivity()).inflate(R.layout.item_city_header, null);
                    mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
                    mHeaderAndFooterWrapper.addHeaderView(headerView);
                    HashMap<String, List<City>> maps = getCityMap();
                    if(maps.size() > 0) {
                        mAlphas = sortedHashKeys(maps);
                        if(mAlphas == null) {
                            return;
                        }
                        if(mSliderView != null) {
                            mSliderView.setAlphas((String[]) mAlphas.toArray());
                        }
                        int size = mAlphas.size();
                        int position = mHeaderAndFooterWrapper.getHeadersCount();
                        for(int i=0;i<size;i++) {
                            mIndexer.put(mAlphas.get(i), position);
                            position += maps.get(mAlphas.get(i)).size();
                        }
                    }
                    mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
                    mRecyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(mHeaderAndFooterWrapper));
                }else {
                    mAdapter.setDataSource(mList);
                }
//            }
//        });
    }

    private HashMap<String, List<City>> getCityMap() {

        HashMap<String, List<City>> cityMap = new HashMap<>();
        if(mList != null) {
            for(City city : mList) {
                String letter = city.getFirstLetter();
                if(cityMap.containsKey(letter)) {
                    List<City> cities = cityMap.get(letter);
                    cities.add(city);
                    cityMap.put(letter, cities);
                }else {
                    List<City> cities = new ArrayList<>();
                    cities.add(city);
                    cityMap.put(letter, cities);
                }
            }
        }
        return cityMap;
    }

    private List<String> sortedHashKeys(HashMap<String, List<City>> map) {
        if(map == null) {
            return null;
        }
        Set<String> keys = map.keySet();
        if(keys == null) {
            return null;
        }
        String[] keyArray = keys.toArray(new String[0]);
        Arrays.sort(keyArray);
        return Arrays.asList(keyArray);
    }
}
