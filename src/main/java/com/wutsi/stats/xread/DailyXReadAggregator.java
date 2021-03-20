package com.wutsi.stats.xread;

import com.opencsv.exceptions.CsvException;
import com.wutsi.stats.InputStreamIterator;
import com.wutsi.stats.impl.AbstractDailySessionAggregator;
import com.wutsi.stats.impl.Session;
import com.wutsi.stats.impl.Track;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DailyXReadAggregator extends AbstractDailySessionAggregator {
    public DailyXReadAggregator(LocalDate date) {
        super(date);
    }

    public void aggregate(InputStreamIterator iterator, OutputStream output) throws IOException, CsvException {
        List<Session> sessions = this.getSessions(iterator);
        List<XRead> xreads = toXReads(sessions);
        new XReadWriter().write(xreads, output);
    }

    @Override
    protected boolean isValidSession(Session session) {
        return true;
    }

    private List<XRead> toXReads(List<Session> sessions) {
        return sessions.stream()
                .collect(groupingBy(Session::getProductId))
                .values()
                .stream()
                .map(it -> toXRead(it))
                .filter(it -> it != null)
                .collect(Collectors.toList());
    }

    private XRead toXRead(List<Session> sessions) {
        List<Track> xreads = sessions.stream()
                .flatMap(it -> it.getTracks().stream())
                .filter(it -> "xread".equals(it.getEvent()))
                .collect(Collectors.toList());

        return xreads.isEmpty() ? null : new XRead(this.date.toString(), sessions.get(0).getProductId(), xreads.size());
    }
}
