package com.bw.ymy.zonghe1.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.adapter.ShpGoodsAdapter;
import com.bw.ymy.zonghe1.bean.GWCBean;

import java.util.ArrayList;
import java.util.List;

//数量 +  -
public class CountView extends RelativeLayout implements View.OnClickListener {
    private EditText editCha;
    private ImageView add,jian;

    public CountView(Context context) {
        super(context);
        init(context);
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
        private  Context context;
    private  void init(Context context)
    {
            this.context=context;

            View view=View.inflate(context, R.layout.shopgoods,null);
              add =  view.findViewById(R.id.jia);
              jian =  view.findViewById(R.id.jian);
             editCha=view.findViewById(R.id.add_count);
             add.setOnClickListener(this);
             jian.setOnClickListener(this);
             addView(view);
             editCha.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 }

                 @Override
                 public void onTextChanged(CharSequence s, int start, int before, int count) {

                 }

                 @Override
                 public void afterTextChanged(Editable s) {

                 }
             });

    }

    private  int num;

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //点击改变数量
            case R.id.jia:
                num++;
                editCha.setText(num+"");
                list.get(position).setNum(num);
                listener.callBack();
                shpGoodsAdapter.notifyItemChanged(position);
                break;
            case  R.id.jian:
                if(num>1)
                {
                    num--;
                }else
                {
                    toast("不能再减了！");
                }
                    editCha.setText(num+"");
                list.get(position).setNum(num);
                listener.callBack();
                shpGoodsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }

    }
    //吐司
    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    //传统
    private  List<GWCBean.DataBean.ListBean> list=new ArrayList<>();
    private  int position;
    private  ShpGoodsAdapter shpGoodsAdapter;
    //显示 隐藏   list  重点
    public void setData(ShpGoodsAdapter shpGoodsAdapter, List<GWCBean.DataBean.ListBean> list, int i) {
        //重点
        this.list=list;
        this.shpGoodsAdapter=shpGoodsAdapter;
        position=i;
        num=list.get(i).getNum();
        editCha.setText(num+"");
    }

    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }



    public interface CallBackListener {
        void callBack();
    }
}
