package com.bw.ymy.zonghe1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.bean.GWCBean;

import java.util.ArrayList;
import java.util.List;

public class ShopNameAdapter extends RecyclerView.Adapter<ShopNameAdapter.MyViewHolder> {
    private List<GWCBean.DataBean> mlist=new ArrayList<>();
    private Context mcontext;
    public ShopNameAdapter(Context context) {
        this.mcontext = context;
    }
    public  void  setList(List<GWCBean.DataBean> list)
    {
        this.mlist=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.shopname,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        //设置商家的名字
        myViewHolder.mSell.setText(mlist.get(i).getSellerName());
        //布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mcontext);
        myViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        final  ShpGoodsAdapter shpGoodsAdapter=new ShpGoodsAdapter(mcontext,mlist.get(i).getList());

        myViewHolder.mRecyclerView.setAdapter(shpGoodsAdapter);
        myViewHolder.mcheck.setChecked(mlist.get(i).isCheck());
        shpGoodsAdapter.setListener(new ShpGoodsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if(mShopCallBackListener!=null)
                {
                    mShopCallBackListener.callBack(mlist);
                }
                List<GWCBean.DataBean.ListBean> listBeans=mlist.get(i).getList();
                boolean isAllCheck=true;
                for (GWCBean.DataBean.ListBean bean:listBeans)
                {
                    if(!bean.isCheck())
                    {
                        //只要由一个商品未选择，标志设为false,跳出循环
                        isAllCheck=false;
                        break;
                    }
                }
                //刷新
                myViewHolder.mcheck.setChecked(isAllCheck);
                mlist.get(i).setCheck(isAllCheck);

            }
        });
        //商品所有的状态
        myViewHolder.mcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //改变标志的位置
                mlist.get(i).setCheck(myViewHolder.mcheck.isChecked());
                //调用产品adapter的方法，用来全选he反选
                shpGoodsAdapter.selectOrRemoveAll(myViewHolder.mcheck.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class  MyViewHolder extends  RecyclerView.ViewHolder{
        RecyclerView mRecyclerView;
        TextView mSell;
        CheckBox mcheck;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSell=itemView.findViewById(R.id.tv_shop);
            mcheck=itemView.findViewById(R.id.check_shop);
            mRecyclerView=itemView.findViewById(R.id.recycler_shop);
        }
    }
    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<GWCBean.DataBean> list);
    }
}
