package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

public class ShowDescriptionActivity extends AppCompatActivity {

    private TextView tvTitile;
    private ScrollView slContent;
    private TextView tvContent;

    private String itemLink;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);

        initView();

        //取出intent传递的数据
        Bundle bundle = getIntent().getExtras();

        title = bundle.getString("title");
        itemLink = bundle.getString("itemLink");
        String pubdate = bundle.getString("pubdate");
        String description = bundle.getString("description");

        if (description != null) {
            if (description.equals(""))
                tvContent.setText("内容已滚回了火星");
            else
                tvContent.setText(description + "ssssssss/n版权归作者所有，任何形式转载请联系作者。\n" +
                        "作者：一颗大黑米 （来自豆瓣）\n" +
                        "来源：https://movie.douban.com/review/8811640/\n" +
                        "\n" +
                        "\n" +
                        "相当于其他片子而言，不得不承认本片的叙事风格是我比较喜欢的。它的亮点在于将年轻时期的成大器与" +
                        "中年时期的成大器穿插着叙述，丝毫没有给人一种凌乱感，反而使人眼前一亮，这种通过倒叙的形式将" +
                        "人物过往一段段呈现出来的方法，使观众更清晰地理清了故事。\n" +
                        "\n" +
                        "下面说说主人公成大器，作为以前一个初出茅庐、什么的不懂的小人物，自拜了洪寿亭为师而变成了如" +
                        "今大上海鼎鼎大名，独占鳌头的大先生，他的影响似乎大过了他的师傅。他有聪明的智慧，也有过人的胆" +
                        "识，片中洪寿亭被浙江省督军儿子抓住后他不管安危前往营救，面对对方提出的三个过分条件，他从始至" +
                        "终沉着镇定，灵活应对，这里凸显了他的才华。\n" +
                        "\n" +
                        "对于他的爱情，他身边有两个重要的女人，第一个是初恋情人叶知秋，另一个则是一心一意待他的阿" +
                        "宝。虽说成大器心心念念叶知秋，但我认为这只是一种对初恋的感觉，看见叶知秋他就会想起年轻时的那" +
                        "段美好，他认为他还像以前一样喜欢着叶知秋，但其实真正对他重要的人已经变了，在不知不觉中变成了" +
                        "那个一直陪在他身边，于他而言似乎并没有那么重要的阿宝。\n" +
                        "\n" +
                        "在成大器和两个女人之间，我选择站他和阿宝。相对于叶知秋，阿宝无论遇到什么都会以成大器为中心，" +
                        "在她心里成大器的地位占了大半，为了成大器，她可以无怨无悔说出任何牺牲，而叶知秋则不会，她比阿" +
                        "宝更独立有思想，以至于她喜欢成大器，但她也会因为自己不能理解成大器的某些行为而放弃他，如果说" +
                        "成大器在阿宝心中占了百分之八十的分量，那么他在叶知秋心中则占了百分之五十。\n" +
                        "\n" +
                        "最后阿宝在关键时刻为救成大器和叶知秋而中枪死去，此刻的成大器才明白自己最最爱的女人不是叶知秋" +
                        "，是躺在他身边慢慢冰冷的阿宝。这幕可谓是片中亮点，结局悲伤但却真正揭开了这段爱情真正的男女" +
                        "主角。");
        } else {
            Log.e("ShowDescriptionActivity", "description为空！！");
            tvContent.setText("内容已滚回了火星");
        }

        tvTitile.setText(title);
    }

    private void initView() {

        slContent = (ScrollView) findViewById(R.id.sl_content);
        tvTitile = (TextView) findViewById(R.id.tv_titile);
        tvContent = (TextView) findViewById(R.id.tv_content);
        final ImageView ivStar = (ImageView) findViewById(R.id.iv_star);
        final LinearLayout llDescriptionTop = (LinearLayout) findViewById(R.id.ll_description_top);
        ImageView ivShare = (ImageView) findViewById(R.id.iv_share);

        slContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startY = 0;
                float endY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endY = event.getY();
                        break;
                }

                //根据endY-startY的值来判断乡下或向上滑，进而进行处理
                if (startY - endY > 50) {
                    tvTitile.setVisibility(com.mingle.widget.View.GONE);
                    llDescriptionTop.setVisibility(View.GONE);
                } else if (endY - startY > 50) {
                    tvTitile.setVisibility(View.VISIBLE);
                    llDescriptionTop.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });

        //标记为星标项目
        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivStar.setImageResource(R.drawable.long_press_starred);
                boolean isStared = true;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
            }
        });

        //分享按钮点击执行分享操作
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "这篇文章于君或有益哦：「" + title + "」" + ":"
                        + itemLink);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
    }


}
