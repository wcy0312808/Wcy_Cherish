package wcy.godinsec.wcy_dandan.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Auther：杨玉安 on 2017/9/13 17:20
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：  ViewHolder的主要作用就是绑定和实例化View
 */
public class  GeneraldutyViewHolder<T   extends  View> extends RecyclerView.ViewHolder {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 当前传递过来的View中所有的子空间
     */
    private SparseArray<View> mItemViewDatas;

    /**
     * 当前需要使用的控件
     */
    private View mCurrentItemView;

    /**
     * 通用的adapter
     */
    private GeneraldutyAdapter mGeneraldutyAdapter;

    /**
     * 这里的View就是我们需要展示的view
     * @param itemView
     */
    public GeneraldutyViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mCurrentItemView = itemView;
        mItemViewDatas = new SparseArray<>();
    }

    /**
     * 实例化VIewHolder 此方法以便外部调用
     * @param context
     * @param layoutID
     * @param parent
     * @return
     */
    public  static  GeneraldutyViewHolder instancesGeneraldutyViewHolder(Context context, int layoutID, ViewGroup parent)
    {
        View view = LayoutInflater.from(context).inflate(layoutID,parent,false);
        GeneraldutyViewHolder viewHolder = new GeneraldutyViewHolder(view,context);
        return viewHolder;
    }

    /**
     * 用传递过来的viewID在mItemViewDatas中先找view，如果没有就findViewByid，然后再存入集合中
     * @param viewID
     * @param <T>
     * @return
     */
    public   <T extends View>  T   getView(int viewID)
    {
        View view = mItemViewDatas.get(viewID);

        if(view != null)
        {
            view = mCurrentItemView.findViewById(viewID);
            mItemViewDatas.append(viewID,view);
        }

        return (T)view;
    }


}
