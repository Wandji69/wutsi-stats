package com.wutsi.stats.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.wutsi.stats.Aggregator;
import com.wutsi.stats.InputStreamIterator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDailySessionAggregator implements Aggregator {
    protected LocalDate date;

    public AbstractDailySessionAggregator(LocalDate date){
        this.date = date;
    }


    protected abstract boolean isValidSession(Session session);

    protected List<Session> getSessions(InputStreamIterator iterator) throws IOException{
        Map<String, Session> sessions = new LinkedHashMap<>();
        do {
            Reader reader = new InputStreamReader(iterator.next());
            try {
                loadSessions(reader, sessions);
            } finally{
                reader.close();
            }
        } while(iterator.hasNext());

        return sessions.values().stream()
                .filter(it -> isValidSession(it))
                .collect(Collectors.toList());
    }

    private void loadSessions(Reader reader, Map<String, Session> sessions) throws IOException {
        try {
            CsvToBean<Track> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Track.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            csvToBean.parse().stream()
                    .filter(track -> this.isValidTrack(track))
                    .forEach(it -> addTrack(it, sessions) );

        } finally{
            reader.close();
        }
    }

    protected boolean isValidTrack(Track track) {
        return  "page.read".equals(track.getPage()) &&
                !track.getBot() &&
                isDate(track.getTime(), this.date);
    }

    private void addTrack(Track track, Map<String, Session> sessions) {
        Session session = sessions.get(track.getHitId());
        if(session == null){
            session = new Session(track.getHitId(), track.getProductId());
            sessions.put(track.getHitId(), session);
        }
        session.add(track);
    }

    private String convertTimeStampToDateString (long timestamp) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(timestamp).getTime());
    }

    protected boolean isDate(String trackDate, LocalDate currectDate) {
        String trackCurrentDate = this.convertTimeStampToDateString(Long.parseLong(trackDate));
        return currectDate.toString().equals(trackCurrentDate);
    }
}
