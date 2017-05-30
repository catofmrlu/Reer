package com.example.stest;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        //定义菜单适配器
         private MenuAdapter menuAdapter  ;
        //定义ListView菜单
              ListView menuList ;

              //保存当前的活动菜单项
              int currentActiveItem = -1 ;

         //定义内容视图
              TextView contentText ;

               //定义MenuDrawer对象
               private MenuDrawer menuDrawer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            //创建MenuDrawer，并设定加载模式
            menuDrawer = MenuDrawer.attach(this);

            //创建菜单视图
            menuList = new ListView(this);
            menuAdapter = new MainActivity.MenuAdapter();
            menuList.setAdapter(menuAdapter);

            //为菜单视图添加事件响应
            menuList.setOnItemClickListener(mItemClickListener);
            menuList.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               /*为什么要进行重绘呢？因为我们为菜单项设置了一个指示及“指针”
                * 如果我们不重绘的话，那么当滚动菜单栏的时候，那个“指针”就不会移动
                * 也就是说，那个指针不会随着当前的那个活动的菜单项上下移动
                * 此外还要注意的一点是，这个方法必须在
                * menuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT)；
                * 之后调用，因为在menuDrawer还没有创建之前，是不能够调用这个方法的，否则会抛出
                * NullPointException**/
                    menuDrawer.invalidate();
                }
            });

            //创建内容视图
            contentText = new TextView(this);
            //contentText.setBackgroundResource(R.drawable.img_frame_background);

            //加载菜单视图和内容视图
            menuDrawer.setMenuView(menuList);
            menuDrawer.setContentView(contentText);

            //Animates the drawer slightly open until the user opens the drawer.
            menuDrawer.peekDrawer();
            //设置ActionBar中的程序图标可见，并且显示那个向左的指示箭头标志
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }




    //定义菜单视图的菜单项的监听器
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //当前的活动菜单项为position
            currentActiveItem = position ;

            //为当前的活动项添加“指针”
            menuDrawer.setActiveView(view, position);

            //改变contentText中的内容
            contentText.setText(((TuiCool_MenuItem)(menuAdapter.getItem(position))).getMenuText()) ;


            //关闭菜单视图
            menuDrawer.closeMenu(true);
        }
    };



    //《当用户按下了"手机上的返回功能按键"的时候会回调这个方法》
    @Override
    public void onBackPressed() {
        final int drawerState = menuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            menuDrawer.closeMenu();
            return;
        }
        //也就是说，当按下返回功能键的时候，不是直接对Activity进行弹栈，而是先将菜单视图关闭
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // 当单击了程序图标的位置时返回android.R.id.home
                //一次点击菜单视图打开，再一次单击则关闭菜单视图
                menuDrawer.toggleMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //定义菜单分隔条类
    private static class Category {
        String mTitle;
        Category(String title) {
            mTitle = title;
        }
    }

    //定义菜单项类
    private static class TuiCool_MenuItem{

        private String menuText ;
        private int menuIcon ;

        public TuiCool_MenuItem(String menuText , int menuIcon){
            this.menuText=menuText ;
            this.menuIcon=menuIcon ;
        }

        public String getMenuText(){
            return this.menuText ;
        }

        public int getMenuIcon(){
            return this.menuIcon ;
        }
    }

    //定义自定义菜单项组Adapter
    private class MenuAdapter extends BaseAdapter {

        //用来存贮菜单项和菜单分隔条对象
        private List<Object> menuItems = new ArrayList<Object>() ;

        //加载所有的菜单项和菜单分隔条
        public MenuAdapter(){

            menuItems.add(new Category("分组一")) ;
            menuItems.add(new TuiCool_MenuItem("离线",R.drawable.list23)) ;
            menuItems.add(new TuiCool_MenuItem("站点",R.drawable.list26));

            menuItems.add(new Category("分组二"));
            menuItems.add(new TuiCool_MenuItem("搜索",R.drawable.list23)) ;
            menuItems.add(new TuiCool_MenuItem("发现",R.drawable.list26)) ;
            menuItems.add(new TuiCool_MenuItem("设置",R.drawable.list23)) ;
        }

        @Override
        public int getCount() {
            return  menuItems.size();
        }

        @Override
        public Object getItem(int position) {
            return menuItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /*这个方法和下面的一个方法只是用于标定你所创建的菜单中有几种类型的项目，
         *一般来说就有两种,一种是分隔条项目、一种是实际的可选项目，这两个方法不会自动回调
         *只是为让程序员在getView（）等方法中能够方便使用这两个方法来判定当前的项目的类型
         **/
        public int getItemViewType(int position) {
            return getItem(position) instanceof TuiCool_MenuItem ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        //当前的对象对应的组件是否能够被选中或者被点击，即菜单项对象能够被点击，分隔条对象不能够被点击
        @Override
        public boolean isEnabled(int position) {
            return getItem(position) instanceof TuiCool_MenuItem;
        }

        //指明Adapter中的所有的对象对应的组件是否都能够被点击
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView ;
            Object item = menuItems.get(position) ;

            if(item instanceof TuiCool_MenuItem){
                if(view == null){
                    view =getLayoutInflater().inflate(R.layout.menu_tuicool, parent, false);
                }
                ((TextView) view).setText(((TuiCool_MenuItem)item).getMenuText());
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(
                        ((TuiCool_MenuItem)item).getMenuIcon(), 0, 0, 0);
            }else{
                view = getLayoutInflater().inflate(R.layout.category_style, parent, false);
                ((TextView) view).setText(((Category)item).mTitle);
            }

            //为每个view添加Tag
            view.setTag(R.id.mdActiveViewPosition, position);

            if (position == currentActiveItem) {
                menuDrawer.setActiveView(view, position);
            }
       /*当我们将MenuDrawer库加入到当前工程中后，R文件中会自动生成上面的R.id.mdActivity
        *在调用setActiveView（）方法之前，必须得为每个view指定Tag，
        *并且第一个参数属性值必须为R.id.mdActiveViewPosition
        *既然我们已经在菜单的监听器中为当前活动的菜单项添加了“指针”为什么还要添加呢？
        *因为，我们在监听方法中将菜单关闭了，当在一次显示菜单视图时，会进行重绘
        *即重新调用这个getview（）方法，所以我们要重新设定一下这个“指针”**/
            return view;
        }
    }
}


