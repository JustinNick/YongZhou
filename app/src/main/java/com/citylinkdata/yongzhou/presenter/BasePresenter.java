package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Created by liqing on 2017/10/26
 */
public abstract class BasePresenter<T extends IBaseView> {

    private WeakReference<T> weakReference;

    protected HttpManager httpManager;

    public void attach(T t) {
        weakReference = new WeakReference<>(t);
        httpManager = new HttpManager(t.getContext());
    }

    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
            httpManager=null;
        }
    }

    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    public T getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }



}
