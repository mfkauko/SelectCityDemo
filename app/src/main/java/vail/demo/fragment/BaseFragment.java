package vail.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by HMY on 2016/7/5/005.
 * 修改：
 * 不预加载。
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected static final String KEY_DATA = "key_data";

    protected Activity mActivity;
    protected Context mContext;

    protected LayoutInflater inflater;
    private View contentView;
    private ViewGroup container;

    protected boolean isViewPrepared;
    protected boolean hasFetchData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            getBundleData(getArguments());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity =  activity;
        mContext = activity;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.contentView = inflater.inflate(getLayoutId(), container, false);
        isViewPrepared = true;
        initData();
        initView(contentView);
        setListener();
        processLogic(savedInstanceState);
        lazyFetchDataIfPrepared();
        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewPrepared = false;
        contentView = null;
        container = null;
        inflater = null;
    }

    protected boolean shouldRefresh() {
        return false;
    }

    protected void refreshData() {

    }

    //获取宿主Activity
    protected Activity getHoldingActivity() {
        return mActivity;
    }

    protected abstract int getLayoutId();

    protected void getBundleData(Bundle bundle) {
    }

    protected void initData() {
    }

    protected void lazyLoadData() {

    }

    protected abstract void initView(View view);

    protected void setListener() {
    }

    protected abstract void processLogic(Bundle savedInstanceState);

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(View group, int id) {
        return group == null ? null : (T) group.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    protected <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    protected void processClick(View v) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!shouldRefresh()) {
                lazyFetchDataIfPrepared();
            } else {
                refreshData();
            }
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true; //已加载过数据
            lazyLoadData();
        }
    }
}
