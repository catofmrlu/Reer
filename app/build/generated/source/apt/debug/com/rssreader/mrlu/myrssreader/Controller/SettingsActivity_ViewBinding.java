// Generated code from Butter Knife. Do not modify!
package com.rssreader.mrlu.myrssreader.Controller;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Switch;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rssreader.mrlu.myrssreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingsActivity_ViewBinding<T extends SettingsActivity> implements Unbinder {
  protected T target;

  @UiThread
  public SettingsActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.tbNightMode = Utils.findRequiredViewAsType(source, R.id.tb_nightMode, "field 'tbNightMode'", Switch.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tbNightMode = null;

    this.target = null;
  }
}
