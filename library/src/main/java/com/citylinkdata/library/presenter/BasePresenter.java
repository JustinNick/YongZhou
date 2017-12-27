package com.citylinkdata.library.presenter;

import java.lang.ref.WeakReference;

/**
 * <基础业务类>
 *
 * @author caoyinfei
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class BasePresenter<T , M> {
    private WeakReference<T> weakReference;
    protected M model;
    public void attach(T t) {
        weakReference = new WeakReference<>(t);
        model = getModel();
    }

    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
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
    protected abstract M getModel();
}
