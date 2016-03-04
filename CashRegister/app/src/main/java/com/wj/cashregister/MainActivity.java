package com.wj.cashregister;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wj.cashregister.checkout.Checkout;
import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Constant;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.model.type.GoodsTypeNormal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类主要用于与用户交互
 */
public class MainActivity extends Activity {
    //羽毛球的条形码
    private final String BARCODE_BADMINTON = "ITEM000001";
    //苹果的条形码
    private final String BARCODE_APPLE = "ITEM000003";
    //可口可乐的条形码
    private final String BARCODE_COCACALA = "ITEM000005";
    //商品数量View
    private EditText mCocaCalaCountView, mBadmintonCountView, mAppleCountView;
    //折扣选择View
    private CheckBox mCocaCalaDiscountView, mBadmintonDiscountView, mAppleDiscountView;
    //买二赠一View
    private CheckBox mCocaCalaFreeView, mBadmintonFreeView, mAppleFreeView;
    //账单View
    private TextView mBillView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    /*
    * 初始化默认的数据
    * */
    private void initData() {
        GoodsDao goodsDao = GoodsDao.getInstance(this);
        List<Goods> goodses = new ArrayList<>(3);

        Goods cocaCala = new Goods();
        cocaCala.setBarCode(BARCODE_COCACALA);
        cocaCala.setName("可口可乐");
        cocaCala.setUnit("瓶");
        cocaCala.setPrice(3.00f);
        cocaCala.setGoodsType(new GoodsTypeNormal());
        goodses.add(cocaCala);

        Goods badminton = new Goods();
        badminton.setBarCode(BARCODE_BADMINTON);
        badminton.setName("羽毛球");
        badminton.setUnit("个");
        badminton.setPrice(1.00f);
        badminton.setGoodsType(new GoodsTypeNormal());
        goodses.add(badminton);

        Goods apple = new Goods();
        apple.setBarCode(BARCODE_APPLE);
        apple.setName("苹果");
        apple.setUnit("斤");
        apple.setPrice(5.50f);
        apple.setGoodsType(new GoodsTypeNormal());
        goodses.add(apple);


        try {
            goodsDao.insertGoodses(goodses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 初始化Views
    * */
    private void initViews() {
        getActionBar().setDisplayShowHomeEnabled(false);

        View cocaCalaLayout = findViewById(R.id.goods_cocacala);
        ((TextView) cocaCalaLayout.findViewById(R.id.goods_item_name)).setText(R.string.goods_cocacala);
        ((TextView) cocaCalaLayout.findViewById(R.id.goods_item_unit)).setText(R.string.goods_cocacala_unit);
        mCocaCalaCountView = (EditText) cocaCalaLayout.findViewById(R.id.goods_item_count);
        mCocaCalaDiscountView = (CheckBox) cocaCalaLayout.findViewById(R.id.goods_item_discount);
        mCocaCalaFreeView = (CheckBox) cocaCalaLayout.findViewById(R.id.goods_item_free);

        View badmintonLayout = findViewById(R.id.goods_badminton);
        ((TextView) badmintonLayout.findViewById(R.id.goods_item_name)).setText(R.string.goods_badminton);
        ((TextView) badmintonLayout.findViewById(R.id.goods_item_unit)).setText(R.string.goods_badminton_unit);
        mBadmintonCountView = (EditText) badmintonLayout.findViewById(R.id.goods_item_count);
        mBadmintonDiscountView = (CheckBox) badmintonLayout.findViewById(R.id.goods_item_discount);
        mBadmintonFreeView = (CheckBox) badmintonLayout.findViewById(R.id.goods_item_free);

        View appleLayout = findViewById(R.id.goods_apple);
        ((TextView) appleLayout.findViewById(R.id.goods_item_name)).setText(R.string.goods_apple);
        ((TextView) appleLayout.findViewById(R.id.goods_item_unit)).setText(R.string.goods_apple_unit);
        mAppleCountView = (EditText) appleLayout.findViewById(R.id.goods_item_count);
        mAppleDiscountView = (CheckBox) appleLayout.findViewById(R.id.goods_item_discount);
        mAppleFreeView = (CheckBox) appleLayout.findViewById(R.id.goods_item_free);

        mBillView = (TextView) findViewById(R.id.goods_bill);
    }

    /*
    * 处理点击事件
    * */
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.calculate_result: {
                String cocaCalaCount = mCocaCalaCountView.getText().toString();
                String badmintonCount = mBadmintonCountView.getText().toString();
                String appleCount = mAppleCountView.getText().toString();
                if (isCountEmpty(cocaCalaCount) || isCountEmpty(badmintonCount) || isCountEmpty(appleCount)) {
                    Toast.makeText(this, R.string.goods_count_warning, Toast.LENGTH_SHORT).show();
                    mBillView.setText("");
                    return;
                }

                Map<String, CheckBox[]> types = new HashMap<>();
                types.put(BARCODE_COCACALA, new CheckBox[]{mCocaCalaDiscountView, mCocaCalaFreeView});
                types.put(BARCODE_BADMINTON, new CheckBox[]{mBadmintonDiscountView, mBadmintonFreeView});
                types.put(BARCODE_APPLE, new CheckBox[]{mAppleDiscountView, mAppleFreeView});
                setGoodsType(types);

                Map<String, Integer> jsonData = new HashMap<>();
                jsonData.put(BARCODE_COCACALA, Integer.parseInt(cocaCalaCount));
                jsonData.put(BARCODE_BADMINTON, Integer.parseInt(badmintonCount));
                jsonData.put(BARCODE_APPLE, Integer.parseInt(appleCount));
                String json = generateJSON(jsonData);

                Checkout checkout = new Checkout(this);
                try {
                    checkout.input(json);
                    String results = checkout.output();
                    mBillView.setText(results);
                    Log.d("TAG", "results=" + results);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            default:
                break;

        }

    }

    /*
    * 设置商品的优惠条件
    * */
    private void setGoodsType(Map<String, CheckBox[]> types) {
        GoodsDao goodsDao = GoodsDao.getInstance(this);
        Iterator i = types.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, CheckBox[]> entry = (Map.Entry<String, CheckBox[]>) i.next();
            GoodsType goodsType = null;
            CheckBox[] value = entry.getValue();
            if (value[0].isChecked() && value[1].isChecked()) {
                goodsType = new GoodsTypeDouble();
            } else if (value[0].isChecked()) {
                goodsType = new GoodsTypeDiscount();
            } else if (value[1].isChecked()) {
                goodsType = new GoodsTypeFree();
            } else {
                goodsType = new GoodsTypeNormal();
            }
            goodsDao.updateGoodsTypeByBarCode(entry.getKey(), goodsType);

            i.remove();
        }

    }

    /*
    * 根据条形码和商品数量生成JSON数据
    * */
    private String generateJSON(Map<String, Integer> jsonData) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator iterator = jsonData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
            sb.append("\"");
            sb.append(entry.getKey());
            sb.append(Constant.JSON_SPLIT_STRING);
            sb.append(entry.getValue());
            sb.append("\"");
            sb.append(",");
            iterator.remove();
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");

        return sb.toString();
    }

    /*
    * 检查商品数量是否为空和为0
    * */
    private boolean isCountEmpty(String count) {
        if (count == null || count.length() == 0 || Integer.valueOf(count) == 0) {
            return true;
        }
        return false;
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
