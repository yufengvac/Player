package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import beans.T;

/**
 * Created by yufengvac on 15/8/24.
 */
public abstract class MBaseAdapter extends BaseAdapter{
    private List<T> mData;
    private Context mContext;
    private LayoutInflater inflater;
    public MBaseAdapter(List<T> data,Context context){
        this.mData = data;
        this.mContext = context;
        mData = new ArrayList<T>();
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);


    /**
     * @description 添加一项
     * @param t
     */
    public void addOneItem(T t){
        mData.add(t);
    }

    /**
     * @description 在某一个位置添加某一项
     * @param t
     * @param location
     */
    public void addOneItemInPosition(T t,int location){
        mData.add(location,t);
    }

    /**
     * @description 添加一个list的数据
     * @param list
     */
    public void addAllItems(List<T> list){
        mData.addAll(list);
    }



}
