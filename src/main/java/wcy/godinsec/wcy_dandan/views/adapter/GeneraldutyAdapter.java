package wcy.godinsec.wcy_dandan.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Auther：杨玉安 on 2017/9/13 17:19
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： RecyclerView 的通用adapter
 */
public class GeneraldutyAdapter<DATA> extends RecyclerView.Adapter<GeneraldutyViewHolder> {
    //当前RecyclerView使用的item布局
    private int mLayoutID;

    //实例化view的LayoutInflate
    private LayoutInflater mLayoutInflater;

    //
    private List<DATA> mData;
    public GeneraldutyAdapter(Context context,List<DATA> data,int layoutId){
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mLayoutID = layoutId;
    }

    @Override
    public GeneraldutyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(mLayoutID, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(GeneraldutyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
