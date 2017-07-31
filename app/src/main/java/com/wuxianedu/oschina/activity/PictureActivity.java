package com.wuxianedu.oschina.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.utils.T;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<String> list;
    private List<View> viewList = new ArrayList<>();
    private TextView num;
    private String path;
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        int position = getIntent().getIntExtra(Constants.POSITION, 0);
        list = getIntent().getStringArrayListExtra(Constants.PICTURE);


        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        num = (TextView) findViewById(R.id.num_id);
        button = (FloatingActionButton) findViewById(R.id.floating_btn);

        for (int i = 0; i < list.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_pic, null);
            viewList.add(v);
        }

        viewPager.setAdapter(new MyPagerAdapter());
        loadImage(position);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                loadImage(position);
            }

            @Override
            public void onPageSelected(final int position) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save(netPicToBmp(list.get(position)));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 保存图片
     *
     * @param bitmap
     */
    public void save(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        
        path = getExternalCacheDir().getPath() + "/" + System.currentTimeMillis() + ".png";
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(path);
            boolean flag = bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            if(flag){
                updateImageContentResolver(PictureActivity.this,path);
                T.show("保存成功");
            }else{
                T.show("保存失败");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 向图库加入一条数据
     *
     * @param context
     * @param filePath
     */
    private void updateImageContentResolver(Context context, String filePath) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", filePath); //图片所在路径
        contentValues.put("mime_type", "image/jpeg"); //图片类型
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        resolver.insert(uri, contentValues);
    }

    /**
     * 加载图片
     */
    private void loadImage(int arg0) {
        View v = viewList.get(arg0);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_id);
        num.setText((arg0 + 1) + "/" + viewList.size());
        ImageLoaderFactory.getImageLoader().loadImage(list.get(arg0),imageView);
        PhotoViewAttacher mAttacher;
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.update();
    }


    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    /**
     * 网络图片加载为bitmap
     * @param src
     * @return
     */
    private Bitmap netPicToBmp(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);



            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //设置固定大小
            //需要的大小
            float newWidth = width;
            float newHeigth = height;

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
