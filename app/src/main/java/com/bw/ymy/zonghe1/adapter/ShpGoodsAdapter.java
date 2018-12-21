package com.bw.ymy.zonghe1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.bean.GWCBean;
import com.bw.ymy.zonghe1.view.CountView;

import java.util.ArrayList;
import java.util.List;

public class ShpGoodsAdapter extends RecyclerView.Adapter<ShpGoodsAdapter.MyViewHolder> {
    private List<GWCBean.DataBean.ListBean> mlist=new ArrayList<>();
    private Context mcontext;

    public ShpGoodsAdapter(Context context,List<GWCBean.DataBean.ListBean> list) {
        this.mlist = list;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.goods,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        //
        String url = mlist.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(mcontext).load(url).into(myViewHolder.mImage);

        myViewHolder.mTitle.setText(mlist.get(i).getTitle());
        myViewHolder.mPrice.setText(mlist.get(i).getPrice() + "");
        myViewHolder.mCustomShopCarPrice.setData(this,mlist,i);
        //勾选
        myViewHolder.mCheckBox.setChecked(mlist.get(i).isCheck());
        //判断勾选状态
        myViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优先改变自己的状态
                mlist.get(i).setCheck(isChecked);
                if(mShopCallBackListener!=null)
                {
                    mShopCallBackListener.callBack();
                }

            }
        });
        myViewHolder.mCustomShopCarPrice.setOnCallBack(new CountView.CallBackListener() {
            @Override
            public void callBack() {
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
        CountView mCustomShopCarPrice;
        TextView mTitle, mPrice;
        ImageView mImage;
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.icon_goods);
            mTitle = (TextView) itemView.findViewById(R.id.title_goods);
            mPrice = (TextView) itemView.findViewById(R.id.price_goods);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_goods);
            mCustomShopCarPrice = (CountView) itemView.findViewById(R.id.counview_goods);
        }
    }
    //修改全选/反选
    public void selectOrRemoveAll(boolean isSelectAll) {
        for (GWCBean.DataBean.ListBean listBean : mlist) {
            listBean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();


    }
}
