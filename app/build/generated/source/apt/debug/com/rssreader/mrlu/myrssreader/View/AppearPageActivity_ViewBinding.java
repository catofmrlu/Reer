// Generated code from Butter Knife. Do not modify!
package com.rssreader.mrlu.myrssreader.View;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;

import com.rssreader.mrlu.myrssreader.Controller.AppearPageActivity;
import com.rssreader.mrlu.myrssreader.R;
import com.viewpagerindicator.CirclePageIndicator;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppearPageActivity_ViewBinding<T extends AppearPageActivity> implements Unbinder {
  protected T target;

  @UiThread
  public AppearPageActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.vpAppear = Utils.findRequiredViewAsType(source, R.id.vp_appear, "field 'vpAppear'", ViewPager.class);
    target.indicator = Utils.findRequiredViewAsType(source, R.id.indicator, "field 'indicator'", CirclePageIndicator.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.vpAppear = null;
    target.indicator = null;

    this.target = null;
  }
}
