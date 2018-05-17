package wcy.godinsec.wcy_dandan.views.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.views.customview.CustomProgressBar;


/**
 * @作者 : 杨玉安
 * @日期 : 2017/2/5 0005 09:42
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private  Context mContext;
    /**
     * 使用集合来存储item上的控件
     */
    private SparseArray<View> mViewList;

    /**maF
     * 加载item的布局
     */
    private View mConvertView;

    /**
     * 记录改变背景的ViewID
     */
    private int mViewID;

    /**
     * 背景颜色的资源ID
     */
    private int mResID;

    /**
     * 当前ViewHolder所关联的Adapter
     */
    private CommonRecyclerViewAdapter mAdapter;

    /**
     * 记录是否 进行背景颜色更改的监听
     */
    boolean isChange = false;

    /**
     * 初始id是4
     *
     * @param itemView
     */
    private CommonViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        mViewList = new SparseArray<View>();
    }

//    private List<SubItemBean.DatasBean.SubItemListBean> subItemListDatas;

    /**
     * 获取ViewHolder的方法
     *
     * @param context  上下文
     * @param layoutID 布局ID
     * @param parent   父控件
     * @return 返回当前的布局ID所对应的ViewHolder的实例
     */
    public static CommonViewHolder getViewHolder(Context context, int layoutID, ViewGroup parent) {
        //将布局ID转化为视图
        View itemView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        //实例化当前ViewHolder
        CommonViewHolder viewHolder = new CommonViewHolder(context,itemView);
        //返回
        return viewHolder;
    }

    /**
     * 通过ID取控件的方法 对ItemView的重用已经进行了缓存
     *
     * @param viewID 控件的ID
     * @return 返回id所对应的控件
     */
    public <T extends View> T getView(int viewID) {
        //从集合中取控件
        View view = mViewList.get(viewID);
        //如果没有就通过findViewById创建一个,并缓存到集合中
        if (view == null) {
            view = mConvertView.findViewById(viewID);
            mViewList.put(viewID, view);
        }
        return (T) view;
    }
    /**
     * 辅助方法
     */
    /**
     * text
     *
     * @param viewID
     * @param string
     * @return
     */
    public CommonViewHolder setText(int viewID, String string) {
        TextView textView = getView(viewID);
        textView.setText(string);
        return this;
    }

    public CommonViewHolder setImageUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        Glide.with(mContext).load(url).centerCrop().placeholder(R.drawable.ic_launcher).into(imageView);
        return this;
    }

    public CommonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public CommonViewHolder setProgressText(int viewID, String string) {
        CustomProgressBar textView = getView(viewID);
        textView.setText(string);
        return this;
    }

    public CommonViewHolder setCheckBox(int viewID) {
        final CheckBox checkBox = getView(viewID);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.e("当前是第"+getLayoutPosition()+"条数据");
//                if(mAdapter.map.containsKey(getLayoutPosition()))
//                {
//                    checkBox.setChecked(false);
//                    mAdapter.map.remove(getLayoutPosition());
//                }else {
//                    checkBox.setChecked(true);
//                    mAdapter.map.put(getLayoutPosition(),mAdapter.mData.get(getLayoutPosition()));
//                }
//            }
//        });
//        if(mAdapter.map.get(getLayoutPosition()) != null)
//        {
//            checkBox.setChecked(true);
//        }else {
//            checkBox.setChecked(false);
//        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.e("当前是第"+getLayoutPosition()+"条数据,当前CheckBox的状态值是"+isChecked);
                mAdapter.setCheckBoxState(getLayoutPosition(), isChecked);
            }
        });
        checkBox.setChecked((Boolean) mAdapter.map.get(getLayoutPosition()));
        return this;
    }

    /**
     * 图片
     *
     * @param viewID
     * @param resID
     * @return
     */
    public CommonViewHolder setimageResouse(int viewID, int resID) {
        ImageView imageView = getView(viewID);
        imageView.setImageResource(resID);
        return this;
    }

    /**
     * 按钮
     *
     * @param viewID
     * @param string
     * @return
     */
    public CommonViewHolder setButtpn(int viewID, String string) {
        Button button = getView(viewID);
        button.setText(string);
        return this;
    }

    /**
     * 条目的点击事件
     *
     * @param listener
     * @return
     */
    public CommonViewHolder setOnItemClickLisenter(final OnItemClickLisenter listener) {
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChange) {
                    mAdapter.changeItemBackground(mViewID, mResID, getLayoutPosition());
                    mAdapter.notifyDataSetChanged();
                }
                listener.onItemClickLisenter(itemView, getLayoutPosition());
            }
        });
        return this;
    }

    public CommonViewHolder setOnDeleteClickLisenter(int viewID) {
        View view = getView(viewID);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChange) {
                    mAdapter.changeItemBackground(mViewID, mResID, getLayoutPosition());
                    mAdapter.removeItem(getLayoutPosition());
                    mAdapter.notifyItemRemoved(getAdapterPosition());
                }
            }
        });
        return this;
    }

    /**
     * 条目子布局的点击事件
     * @param viewID
     * @param listener
     * @return
     */
    public CommonViewHolder setOnClickLisenter(int viewID, View.OnClickListener listener) {
        View view = getView(viewID);
        view.setOnClickListener(listener);
        return this;
    }

    public CommonViewHolder setTextViewClickColor(final int viewID, final int colorID) {
        isChange = true;
        this.mViewID = viewID;
        this.mResID = colorID;
        return this;
    }

    public void regist(CommonRecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }



    public interface OnItemClickLisenter {
        void onItemClickLisenter(View view, int position);
    }

    public interface OnDeleteClickLisenter {
        void onDeleteClickLisenter(View view, int position);
    }
}
