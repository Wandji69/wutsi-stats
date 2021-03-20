package com.wutsi.stats.signups;

import com.wutsi.stats.Aggregator;
import com.wutsi.stats.AggregatorTestBase;
import org.junit.Test;

import java.time.LocalDate;

public class DailySignupsAggregatorTest extends AggregatorTestBase {
    @Override
    protected Aggregator getAggregator() {
        return new DailySignupsAggregator(LocalDate.parse("2020-04-14"));
    }

    @Test
    public void happyPath() throws Exception {
        test(
                "/tracks/signup/happy-path/2020-04-14-output.csv",
                "/tracks/signup/happy-path/2020-04-14-000.csv",
                "/tracks/signup/happy-path/2020-04-14-001.csv"
        );
    }

    @Test
    public void bot() throws Exception {
        test(
                "/tracks/signup/bot/2020-04-14-output.csv",
                "/tracks/signup/bot/2020-04-14-000.csv",
                "/tracks/signup/bot/2020-04-14-001.csv"
        );
    }

    @Test
    public void date() throws Exception {
        test(
                "/tracks/signup/date/2020-04-14-output.csv",
                "/tracks/signup/date/2020-04-14-000.csv",
                "/tracks/signup/date/2020-04-14-001.csv",
                "/tracks/signup/date/2020-04-15-000.csv"
        );
    }

    @Test
    public void userIdNotEmpty() throws Exception {
        test(
                "/tracks/signup/user-id-non-empty/2020-04-14-output.csv",
                "/tracks/signup/user-id-non-empty/2020-04-14-000.csv",
                "/tracks/signup/user-id-non-empty/2020-04-14-001.csv"
        );
    }

    @Test
    public void userIdNumeric() throws Exception {
        test(
                "/tracks/signup/user-id-numeric/2020-04-14-output.csv",
                "/tracks/signup/user-id-numeric/2020-04-14-000.csv",
                "/tracks/signup/user-id-numeric/2020-04-14-001.csv"
        );
    }

}
