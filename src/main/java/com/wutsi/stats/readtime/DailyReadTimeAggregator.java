package com.wutsi.stats.readtime;

import com.opencsv.exceptions.CsvException;
import com.wutsi.stats.InputStreamIterator;
import com.wutsi.stats.impl.AbstractDailySessionAggregator;
import com.wutsi.stats.impl.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DailyReadTimeAggregator extends AbstractDailySessionAggregator {
    public DailyReadTimeAggregator(LocalDate date) {
        super(date);
    }

    public void aggregate(InputStreamIterator iterator, OutputStream output) throws IOException, CsvException {
        List<Session> sessions = this.getSessions(iterator);
        List<ReadTime> readTimes = toReadTimes(sessions);
        new ReadTimeWriter().write(readTimes, output);
    }

    @Override
    protected boolean isValidSession(Session session) {
        return true;
    }

    private List<ReadTime> toReadTimes(List<Session> sessions) {
        return sessions.stream()
                .collect(groupingBy(Session::getProductId))
                .values()
                .stream()
                .map(it -> toReadTime(it))
                .filter(it -> it != null)
                .collect(Collectors.toList());
    }

    private ReadTime toReadTime(List<Session> sessions) {
        long duration = sessions.stream()
                .mapToLong(Session::getDurationInSecond)
                .sum();

        return duration == 0 ? null : new ReadTime(this.date.toString(), sessions.get(0).getProductId(), duration);
    }
}
