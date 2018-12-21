package com.bw.ymy.zonghe1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.ymy.zonghe1.Apis;
import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.adapter.GoodAdapter;
import com.bw.ymy.zonghe1.bean.AddBean;
import com.bw.ymy.zonghe1.bean.Goods;
import com.bw.ymy.zonghe1.pewsenter.IPresenterlpl;
import com.bw.ymy.zonghe1.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private XRecyclerView xlistview;
    IPresenterlpl iPresenterlpl;
    private  int page=1;
    private GoodAdapter adapter;
    private ImageView cha;
    private EditText etname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        //获取资源id
        xlistview=findViewById(R.id.xlistview);
        cha=findViewById(R.id.cha);
        etname2=findViewById(R.id.etname2);
        iPresenterlpl=new IPresenterlpl(this);
        cha.setOnClickListener(this);
        //布局
        manager();

        //点击加入购物车
        adapter.setOnclickListener(new GoodAdapter.OnclickListener() {
            @Override
            public void onclick(int pid) {
                Map<String,String> map1=new HashMap<>();
                //值必须一样  否则加入失败
                map1.put("uid","80");
                map1.put("pid",pid+"");
                iPresenterlpl.getRequest(Apis.Add,map1,AddBean.class);
            }
        });
        //点击条目进入购物车
        adapter.setItemclickListener(new GoodAdapter.ItemclickListener() {
            @Override
            public void onclick() {
                Intent intent1=new Intent(ShowActivity.this,ShopActivity.class);
                startActivity(intent1);
            }
        });

    }

    //布局
    public  void  manager()
    {
        //布局
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xlistview.setLayoutManager(layoutManager);
        //适配器
        adapter=new GoodAdapter(this);
        xlistview.setAdapter(adapter);
        //加载更多
        xlistview.setPullRefreshEnabled(true);
        xlistview.setLoadingMoreEnabled(true);
        //回调
        xlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                lodata();
            }
            @Override
            public void onLoadMore() {
                lodata();
            }
        });
        lodata();
    }

    //加载数据
    public  void  lodata()
    {
        Map<String,String> map=new HashMap<>();

        map.put("keywords","手机");
        map.put("page",page+"");
        iPresenterlpl.getRequest(Apis.TYPE_TEXT,map,Goods.class);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.cha:
                page=1;
                Map<String,String> pa=new HashMap<>();
                String name=etname2.getText().toString();
                pa.put("keywords",name);
                iPresenterlpl.getRequest(Apis.TYPE_TEXT,pa,Goods.class);
                break;

        }
    }

    //判断
    @Override
    public void onsuccess(Object data) {
        if(data instanceof Goods)
        {
            Goods goods= (Goods) data;
            if(page==1)
            {
                adapter.setlist(goods.getData());
            }else
            {
                adapter.addlist(goods.getData());
            }
            page++;
            //停止
            xlistview.refreshComplete();
            xlistview.loadMoreComplete();

        }
        //Add类
        if(data instanceof AddBean){
            AddBean addCar= (AddBean) data;
            //加入购物车成功 吐司
            Toast.makeText(this, addCar.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
