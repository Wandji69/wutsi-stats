package com.wutsi.stats.signups;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.wutsi.stats.impl.OutputData;

public class Signup implements OutputData {
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "time")
    private String date;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "userid")
    private String userId;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "count")
    private int count;

    public Signup(String date, String userId, int count ) {
        this.date = date;
        this.userId = userId;
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
