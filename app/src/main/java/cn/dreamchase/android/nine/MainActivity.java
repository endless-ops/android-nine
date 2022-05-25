package cn.dreamchase.android.nine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 滚动间隔
    private static final int CAROUSEL_TIME = 5000;
    private ViewPager viewPager;

    private ViewGroup viewGroup;

    private BannerAdapter bannerAdapter;

    private Handler handler = new Handler();

    private int currentTtem = 0;

    private final Runnable ticker = new Runnable() {
        @Override
        public void run() {
            long now = SystemClock.uptimeMillis();
            long next = now + (CAROUSEL_TIME - now % CAROUSEL_TIME);

            handler.postAtTime(ticker,next);

            currentTtem++;

            viewPager.setCurrentItem(currentTtem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vp_banner);
        bannerAdapter = new BannerAdapter(this);

        bannerAdapter.setOnBannerClickListener(onBannerClickListener);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(bannerAdapter);

        viewPager.addOnPageChangeListener(onPageChangeListener);

        viewGroup = findViewById(R.id.viewGroup);

        for (int i = 0; i < bannerAdapter.getBanners().length; i++) {
            ImageView imageView = new ImageView(this);

            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));

            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.icon_page_select);
            }else {
                imageView.setBackgroundResource(R.drawable.icon_page_normal);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;

            viewGroup.addView(imageView);
        }

        currentTtem = bannerAdapter.getBanners().length * 50;
        viewPager.setCurrentItem(currentTtem);

        handler.postDelayed(ticker,CAROUSEL_TIME);
    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentTtem = position;

            setImageBackground(position %= bannerAdapter.getBanners().length);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void setImageBackground(int selectItem) {
        for (int i = 0; i < bannerAdapter.getBanners().length; i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            imageView.setBackground(null);
            if (i == selectItem) {
                imageView.setImageResource(R.drawable.icon_page_select);
            }else {
                imageView.setImageResource(R.drawable.icon_page_normal);
            }
        }
    }


    private View.OnClickListener onBannerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Toast.makeText(MainActivity.this,"点前点击位置：" + position,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 删除计时器
        handler.removeCallbacks(ticker);
    }
}