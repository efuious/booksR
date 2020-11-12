package com.example.sqltest;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {

    private List<View> views;

    public MyPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    //创建指定位置的视图
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        //将即将创建的view视图，加载到容器中
        container.addView(view);
        return view;
    }
    //从容器总移除某个视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
