package com.bw.ymy.zonghe1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.ymy.zonghe1.Apis;
import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.adapter.ShopNameAdapter;
import com.bw.ymy.zonghe1.bean.GWCBean;
import com.bw.ymy.zonghe1.pewsenter.IPresenterlpl;
import com.bw.ymy.zonghe1.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements IView,View.OnClickListener {
    private ShopNameAdapter shopNameAdapter;
    private RecyclerView recycliview;
    private TextView sum_price, sum_count;
    private CheckBox checkBox;
    private IPresenterlpl iPresenterlpl;
    private List<GWCBean.DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //获取资源id
        //全选//全不选
        checkBox = findViewById(R.id.checkbox1);
        sum_count = findViewById(R.id.sum_count);
        sum_price = findViewById(R.id.sum_price);
        iPresenterlpl = new IPresenterlpl(this);
        //全选的点击事件
        checkBox.setOnClickListener(this);
        //布局   获取主页面 的   布局
        RecyclerView recycliview = (RecyclerView) findViewById(R.id.recycliview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycliview.setLayoutManager(linearLayoutManager);
        //商家的adapter
        shopNameAdapter = new ShopNameAdapter(this);
        recycliview.setAdapter(shopNameAdapter);
        shopNameAdapter.setListener(new ShopNameAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<GWCBean.DataBean> list) {
                double totalprice = 0;
                int num = 0;
                int totalNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<GWCBean.DataBean.ListBean> listall = list.get(a).getList();
                    for (int i = 0; i < listall.size(); i++) {
                        totalNum = totalNum + listall.get(i).getNum();
                        if (listall.get(i).isCheck()) {
                            totalprice = totalprice + (listall.get(i).getPrice() * listall.get(i).getNum());
                            num = num + listall.get(i).getNum();
                        }
                    }
                }
                if (num < totalNum) {
                    //不是全部选中
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                sum_price.setText("合计：" + totalprice);
                sum_count.setText("去结算(" + num + ")");
            }

        });

        getdata();
    }
    //展示商家
    public void getdata() {
        Map<String, String> map = new HashMap<>();
        //值必须一样  否则加入失败
        map.put("uid", "80");
        iPresenterlpl.getRequest(Apis.Shop, map, GWCBean.class);

    }


    @Override
    public void onsuccess(Object data) {
        if (data instanceof GWCBean) {
            GWCBean bean = (GWCBean) data;
            mList = bean.getData();
            if (mList != null) {
                mList.remove(0);
                shopNameAdapter.setList(bean.getData());
            }

        }
    }

    //点击
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkbox1:
                checkSeller(checkBox.isChecked());
                shopNameAdapter.notifyDataSetChanged();
                break;

        }

    }

    //修改选中状态，获取价格数量
    private void checkSeller(boolean bool) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < mList.size(); a++) {
            //遍历商家，改变状态
            GWCBean.DataBean dataBean = mList.get(a);
            dataBean.setCheck(bool);

            List<GWCBean.DataBean.ListBean> listAll = mList.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                //遍历商品，改变状态
                listAll.get(i).setCheck(bool);
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                num = num + listAll.get(i).getNum();
            }
        }
    }


}
























