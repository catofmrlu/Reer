// Generated code from Butter Knife. Do not modify!
package com.rssreader.mrlu.myrssreader.Controller;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RssFeedListActivity_ViewBinding<T extends RssFeedListActivity> implements Unbinder {
  protected T target;

  @UiThread
  public RssFeedListActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.lvRssList = Utils.findRequiredViewAsType(source, R.id.lv_rssList, "field 'lvRssList'", SwipeMenuListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.lvRssList = null;

    this.target = null;
  }
}
