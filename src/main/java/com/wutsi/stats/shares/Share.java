package com.wutsi.stats.shares;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.wutsi.stats.impl.OutputData;

public class Share implements OutputData {
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "productid")
    private String productId;
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "time")
    private String date;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "count")
    private int count;

    public Share(String date, String productId){
        this.date = date;
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
