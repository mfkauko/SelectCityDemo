package vail.demo.util;

/**
 * Created by HMY on 2016/8/16.
 */
public abstract class AsyncCallBack {

    public void onPreExecute() {
    }

    public abstract void doInBackground();

    public abstract void onPostExecute();
}
