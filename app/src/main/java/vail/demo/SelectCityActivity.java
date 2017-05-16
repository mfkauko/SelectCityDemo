package vail.demo;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vail.demo.db.DBManager;
import vail.demo.fragment.CityListFragment;
import vail.demo.fragment.CitySearchFragment;
import vail.demo.model.City;
import vail.demo.util.AsyncCallBack;
import vail.demo.util.AsyncTaskUtil;

public class SelectCityActivity extends AppCompatActivity {

    private static final String[] FRAGMENT_TAGS = new String[] {"CITY_LIST", "CITY_SEARCH"};

    private CityListFragment mCityListFragment;
    private CitySearchFragment mCitySearchFragment;

    private EditText mSearchEdt;

    private List<City> mList;
    private List<City> mSearchList;

    private DBManager mDbManager;
    private SQLiteDatabase mDataBase;

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        mSearchEdt = (EditText) findViewById(R.id.search_edt);

        mSearchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)  {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    final String keyWords = mSearchEdt.getText().toString();
                    if(!TextUtils.isEmpty(keyWords)) {
                        if(mDbManager != null && mDataBase != null) {
                            AsyncTaskUtil.doAsync(new AsyncCallBack() {
                                @Override
                                public void doInBackground() {
                                    String selection = null;
                                    if(isChinese(keyWords)) {
                                        selection = "Name like ?";
                                    }else {
                                        selection = "PinYin like ?";
                                    }
                                    String[] selectionArgs = new String[] {"%" + keyWords + "%"};
                                    mSearchList = mDbManager.query(mDataBase, null, selection, selectionArgs);
                                }

                                @Override
                                public void onPostExecute() {
                                    if(mSearchList != null) {
                                        mCitySearchFragment = CitySearchFragment.newInstance(mSearchList);
                                        showFragment(mCurrentFragment, mCitySearchFragment, 1);
                                    }
                                }
                            });

                        }
                    }else {
                        showFragment(mCurrentFragment, mCityListFragment, 0);
                    }
                    return true;
                }
                return false;
            }
        });

        initData();
    }

    private void initData() {
        AsyncTaskUtil.doAsync(new AsyncCallBack() {
            @Override
            public void doInBackground() {
                mDbManager = new DBManager(SelectCityActivity.this);
                mDataBase = mDbManager.initDataBaseManager(SelectCityActivity.this.getPackageName());
                mList = mDbManager.getAllCity(mDataBase);
            }

            @Override
            public void onPostExecute() {
                if(mList != null) {
                    mCityListFragment = CityListFragment.newInstance(mList);
                    if(mCurrentFragment == null) {
                        mCurrentFragment = mCityListFragment;
                    }
                    showFragment(mCurrentFragment, mCityListFragment, 0);
                }
            }
        });
    }

    private void showFragment(Fragment from, Fragment to, int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mCurrentFragment = to;
        boolean isAdded = to.isAdded();
        if(!isAdded) {
            transaction.hide(from).add(R.id.fragment_root, to, FRAGMENT_TAGS[position]).show(to).commitAllowingStateLoss();
        }else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    private boolean isChinese(String input) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(input);
        if(m.matches()){
            return true;
        }
        return false;
    }
}
