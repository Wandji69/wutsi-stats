package com.wutsi.stats.viewers;

import com.opencsv.exceptions.CsvException;
import com.wutsi.stats.InputStreamIterator;
import com.wutsi.stats.impl.AbstractDailySessionAggregator;
import com.wutsi.stats.impl.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DailyViewersAggregator extends AbstractDailySessionAggregator {
    public DailyViewersAggregator (LocalDate date) {
        super(date);
    }

    public void aggregate(InputStreamIterator iterator, OutputStream output) throws IOException, CsvException {
        List<Session> sessions = this.getSessions(iterator);
        List<Viewer> viewers = toViewers(sessions);
        new ViewerWriter().write(viewers, output);
    }

    @Override
    protected boolean isValidSession(Session session) {
        return true;
    }

    private List<Viewer> toViewers(List<Session> sessions) {
        return sessions.stream()
                .collect(groupingBy(Session::getProductId))
                .values()
                .stream()
                .map(it -> toViewer(it))
                .filter(it -> it != null)
                .collect(Collectors.toList());
    }

    private Viewer toViewer(List<Session> sessions) {
        Collection<List<Session>> viewers = sessions.stream()
                .collect(groupingBy(Session::getHitId))
                .values();

        return viewers.isEmpty() ? null : new Viewer(this.date.toString(), sessions.get(0).getProductId(), viewers.size());
    }

}
