package cn.dreamchase.android.nine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private View.OnClickListener onBannerClickListener;

    private int[] banners = new int[]{
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3,
            R.drawable.banner_4
    };

    public BannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // postion 的取值范围在 0~2147483647，将这个值对图片长度求余之后，position 的取值范围是0~banners.length - 1
        position %= banners.length;

        ImageView imageView = new ImageView(context);
        // 设置图片缩放类型
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // 将当前的下标通过setTag方法设置进去
        imageView.setTag(position);
        imageView.setImageResource(banners[position]);
        imageView.setOnClickListener(onClickListener);
        container.addView(imageView);
        return imageView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onBannerClickListener != null) {
                onBannerClickListener.onClick(v);
            }
        }
    };

    public void setOnBannerClickListener(View.OnClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public int[] getBanners() {
        return banners;
    }
}
