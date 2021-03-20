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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDailyAggregator<T extends OutputData> implements Aggregator {
    protected abstract boolean isValidTrack(Track track);

    @Deprecated
    protected abstract T createOutputData (Track track);

    @Deprecated
    protected abstract List<T> getOutputData(List<Track> tracks);

    protected int countItemList (List<Track> tracks, String productId){
        int count = 0;
        for (Track track : tracks) {
            if(productId != null && productId.equals(track.getProductId())){
                count += 1;
            }
        }
        return count;
    }

    private String convertTimeStampToDateString (long timestamp) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(timestamp).getTime());
    }

    protected boolean isDate(String trackDate, LocalDate currectDate) {
        String trackCurrentDate = this.convertTimeStampToDateString(Long.parseLong(trackDate));
        return currectDate.toString().equals(trackCurrentDate);
    }

    protected List<Track> getTracks(InputStreamIterator iterator) throws IOException {
        List<Track> tracks = new ArrayList<>();
        do {
            Reader reader = new InputStreamReader(iterator.next());
            try {
                CsvToBean<Track> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Track.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<Track> tracksTmp = csvToBean.parse().stream().filter(
                        track -> this.isValidTrack(track)
                ).collect(Collectors.toList());
                tracks.addAll(tracksTmp);
            } finally{
                reader.close();
            }
        } while(iterator.hasNext());
        return tracks;
    }
}
