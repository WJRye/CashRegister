package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsTypeFree extends GoodsType {
    private static final int TYPE = 2;
    private String content;

    public GoodsTypeFree() {
        super(TYPE);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
