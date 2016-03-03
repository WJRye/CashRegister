package com.wj.cashregister;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Goods;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private final String BARCODE_BADMINTON = "ITEM000001";
    private final String BARCODE_APPLE = "ITEM000003";
    private final String BARCODE_COCACALA = "ITEM000005";
    private EditText mCocaCalaCountView, mBadmintonCountView, mAppleCountView;
    private CheckBox mCocaCalaDiscountView, mBadmintonDiscountView, mAppleDiscountView;
    private CheckBox mCocaCalaFreeView, mBadmintonFreeView, mAppleFreeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        GoodsDao goodsDao = GoodsDao.getInstance(this);
        List<Goods> goodses = new ArrayList<>(3);

        Goods badminton = new Goods();
        badminton.setBarCode(BARCODE_BADMINTON);
        badminton.setName("羽毛球");
        badminton.setUnit("个");
        badminton.setPrice(1.00f);
        goodses.add(badminton);

        Goods apple = new Goods();
        apple.setBarCode(BARCODE_APPLE);
        apple.setName("苹果");
        apple.setUnit("斤");
        apple.setPrice(5.50f);
        goodses.add(apple);

        Goods cocaCala = new Goods();
        cocaCala.setBarCode(BARCODE_COCACALA);
        cocaCala.setName("可口可乐");
        cocaCala.setUnit("瓶");
        cocaCala.setPrice(3.00f);
        goodses.add(cocaCala);

        try {
            goodsDao.insertGoodses(goodses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        getActionBar().setDisplayShowHomeEnabled(false);
        int[] layoutIds = {R.id.goods_cocacala, R.id.goods_badminton, R.id.goods_apple};
        int[] nameStringIds = {R.string.goods_cocacala, R.string.goods_badminton, R.string.goods_apple};
        int[] unitStringIds = {R.string.goods_cocacala_unit, R.string.goods_badminton_unit, R.string.goods_apple_unit};
        EditText[] countViews = {mCocaCalaCountView, mBadmintonCountView, mAppleCountView};
        CheckBox[] discountViews = {mCocaCalaDiscountView, mBadmintonDiscountView, mAppleDiscountView};
        CheckBox[] freeViews = {mCocaCalaFreeView, mBadmintonFreeView, mAppleFreeView};
        int length = 3;
        for (int i = 0; i < length; i++) {
            View rootView = findViewById(layoutIds[i]);
            ((TextView) rootView.findViewById(R.id.goods_item_name)).setText(nameStringIds[i]);
            ((TextView) rootView.findViewById(R.id.goods_item_unit)).setText(unitStringIds[i]);
            countViews[i] = (EditText) rootView.findViewById(R.id.goods_item_count);
            discountViews[i] = (CheckBox) rootView.findViewById(R.id.goods_item_discount);
            freeViews[i] = (CheckBox) rootView.findViewById(R.id.goods_item_free);
        }
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ok: {
                String cocaCalaCount = mCocaCalaCountView.getText().toString();
                String badmintonCount = mBadmintonCountView.getText().toString();
                String appleCount = mAppleCountView.getText().toString();
                if (isCountEmpty(cocaCalaCount) || isCountEmpty(badmintonCount) || isCountEmpty(appleCount)) {
                    Toast.makeText(this, R.string.goods_count_warning, Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
            }
            default:
                break;

        }

    }

    private boolean isCountEmpty(String count) {
        if (count == null || "".equals(count.trim())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
