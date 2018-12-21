package com.bw.ymy.zonghe1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.ymy.zonghe1.R;
import com.bw.ymy.zonghe1.vview.CurstomLayout;

public class MainActivity extends AppCompatActivity {
    private EditText etname;
    CurstomLayout history;
    CurstomLayout re;
    private  String[] desc=new String[]{"男装","中山装","霸王洗发水","霸王沐浴露","手机","电器","华为mate20X","性感连衣裙","霸王洗发水",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        etname=findViewById(R.id.et_name);
        history=findViewById(R.id.history);
        re=findViewById(R.id.re);
        //热门内容
        for(String str:desc){
            final TextView textView=new TextView(MainActivity.this);
            textView.setText(str);
            textView.setTextSize(24);
            re.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,textView.getText().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }

        //点击搜素
        findViewById(R.id.sou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=new TextView(MainActivity.this);
                tv.setText(etname.getText());
                tv.setTextColor(Color.RED);
                history.addView(tv);
                //点击吐司
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,etname.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });
    }
}
