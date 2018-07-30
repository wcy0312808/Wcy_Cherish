package wcy.godinsec.wcy_dandan.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import wcy.godinsec.wcy_dandan.interfaces.OnSelectAllListener;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    //这个集合是用来针对于CheckBox这个控件在条目复用的时候防止错乱做处理的
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

    private int mSelectNumber;

    private OnSelectAllListener mOnSelectAllListener;


    public CommonRecyclerViewAdapter(Context context, int layoutID, List<T> data, OnSelectAllListener onSelectAllListener) {
        this.mData = data;
        this.mContext = context;
        this.mLayoutID = layoutID;
        mOnSelectAllListener = onSelectAllListener;
        mSelectNumber = 0;

        //首先，每次初始化将所有的CheckBox的状态值设置成false，
        for (int i = 0; i < mData.size(); i++) {
            map.put(i, false);
        }
    }

    public void allSelect(boolean select) {
        if (select) {
            mSelectNumber = 0;
        }
        for (int i = 0; i < mData.size(); i++) {
            map.put(i, select);
            if (select) {
                mSelectNumber++;
            } else {
                mSelectNumber--;
            }
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

    //在当前页面，那个item的状态值改变了就重新赋值，
    // 我们需要标记的就是当RecycleView重新展示这个Item的时候我们需要从这个集合中取值，所有的都是.
    // 这样才能保证不被复用错乱
    public void setCheckBoxState(int position, Boolean state) {
        map.put(position, state);
        if (state) {
            mSelectNumber++;
            if (mSelectNumber == mData.size()) {
                mOnSelectAllListener.changeState(true);
            }
        } else {
            mSelectNumber--;
            if (mData.size() - mSelectNumber == 1)
                mOnSelectAllListener.changeState(false);
        }
        LogUtils.e("当前是第 " + position + " 条数据,当前CheckBox的状态值是" + state);
        LogUtils.e("mSelectNumber == ==  " + mSelectNumber + " mData.size() == ==  " + mData.size());
    }


    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


}
