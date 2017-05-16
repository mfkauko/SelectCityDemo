package vail.demo.util;

import android.os.AsyncTask;

/**
 * 异步简化操作工具类
 * Created by HMY on 2016/8/16.
 */
public class AsyncTaskUtil {

    public static void doAsync(final AsyncCallBack callBack) {
        if (callBack == null) {
            return;
        }
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                callBack.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                callBack.doInBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callBack.onPostExecute();
            }
        }.execute();
    }
}

