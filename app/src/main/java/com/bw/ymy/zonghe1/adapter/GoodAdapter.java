package com.bw.ymy.zonghe1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Goods.DataBean> mdata;
    private Context mcontext;

    public GoodAdapter(Context context) {
        this.mcontext = context;
        mdata=new ArrayList<>();
    }
    //刷新
    public  void setlist(List<Goods.DataBean> datas)
    {
        mdata.clear();
        mdata.addAll(datas);
        notifyDataSetChanged();
    }
    public  void addlist(List<Goods.DataBean> datas)
    {
        mdata.addAll(datas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.linear,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"吐司",Toast.LENGTH_LONG).show();
            }
        });
        final Goods.DataBean bean=mdata.get(i);

        ViewHolder viewHolder1= (ViewHolder) viewHolder;

        viewHolder1.title.setText(bean.getTitle());
        viewHolder1.price.setText("￥"+bean.getPrice());
        String images=mdata.get(i).getImages();
        String[] split=images.split("\\|");
        Glide.with(mcontext).load(split[0]).into(viewHolder1.icon);
        viewHolder1.but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclickListener!=null){
                    int pid = bean.getPid();
                    onclickListener.onclick(pid);
                }
            }
        });
        viewHolder1.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(this,CarsActivity.class);*/
                if(itemclickListener!=null){
                    itemclickListener.onclick();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title,price;
        private ImageView icon;
        private Button but1;
        private LinearLayout linear;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            price= itemView.findViewById(R.id.price);
            icon= itemView.findViewById(R.id.icon);
            but1=itemView.findViewById(R.id.but1);
            linear=itemView.findViewById(R.id.linear);
        }

    }
    OnclickListener onclickListener;
    ItemclickListener itemclickListener;

    public void setOnclickListener(OnclickListener listener){
        onclickListener=listener;
    }
    public void setItemclickListener(ItemclickListener listener){
        itemclickListener=listener;
    }
    public interface OnclickListener{
        void onclick(int pid);
    }
    public interface ItemclickListener{
        void onclick();
    }
}
