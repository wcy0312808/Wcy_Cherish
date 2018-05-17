package wcy.godinsec.wcy_dandan.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wcy.godinsec.wcy_dandan.utils.LogUtils;

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
//    protected Map<Integer, CheckBoxTextBean> map = new HashMap<>();
    protected Map<Integer, Boolean> map = new HashMap<>();
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 布局id
     */
    protected int mLayoutID;

    /**
     * 数据集合
     */
    protected List<T> mData;

    /**
     * 当前Adapter所关联的Adapter
     */
    private CommonViewHolder mViewHolder;

    /**
     * 改变背景色的view的id
     */
    private int mViewID;

    /**
     * 资源ID,用于改变背景颜色
     */
    private int mResID = 0;

    /**
     * 记录点击的position
     */
    private int mPosition = 0;

    /**
     * 记录是否改变Item颜色的flag
     */
    boolean isChange = false;


    public CommonRecyclerViewAdapter(Context context, int layoutID, List<T> data) {
        this.mData = data;
        this.mContext = context;
        this.mLayoutID = layoutID;
        for(int i=0;i<mData.size();i++)
        {
            map.put(i,false);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化ViewHolder
        mViewHolder = CommonViewHolder.getViewHolder(mContext, mLayoutID, parent);
        mViewHolder.regist(this);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        if (isChange) {
            if (position == mPosition) {
                TextView view = holder.getView(mViewID);
                view.setTextColor(mResID);
            } else {
                TextView view = holder.getView(mViewID);
                view.setTextColor(Color.BLACK);
            }
        }


        //具体赋值逻辑留给用户处理
        conver(holder, mData.get(position));
    }

    public abstract void conver(CommonViewHolder viewHodler, T data);

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void changeItemBackground(int viewID, int resID, int position) {
        isChange = true;
        this.mPosition = position;
        this.mViewID = viewID;
        this.mResID = resID;
    }

//    public void setCheckBoxState(int position,CheckBoxTextBean state)
//    {
//        LogUtils.e("当前是第 "+position+" 条数据,当前CheckBox的状态值是"+state);
//        map.put(position, state);
//    }

    public void setCheckBoxState(int position,Boolean state)
    {
        LogUtils.e("当前是第 "+position+" 条数据,当前CheckBox的状态值是"+state);
        map.put(position, state);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


}
