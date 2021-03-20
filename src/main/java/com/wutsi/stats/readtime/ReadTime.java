package com.wutsi.stats.readtime;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ReadTime {
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "time")
    private String date;
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "productid")
    private String productId;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "readtime")
    private long readTime;

    public ReadTime(String date, String productId, long readTime) {
        this.date = date;
        this.productId = productId;
        this.readTime = readTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getReadTime() {
        return readTime;
    }

    public void setReadTime(final long readTime) {
        this.readTime = readTime;
    }
}
