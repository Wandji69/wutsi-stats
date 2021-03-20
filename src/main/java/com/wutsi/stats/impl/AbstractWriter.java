package com.wutsi.stats.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public abstract class AbstractWriter <T>  {
    protected abstract void writeHeader (Writer writer) throws IOException;

    public void write(List<T> data, OutputStream out) throws IOException, CsvException {
        Writer writer = new OutputStreamWriter(out);

        try{
            StatefulBeanToCsv<T> csvWriter = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            this.writeHeader(writer);

            csvWriter.write(data);
        } finally {
            writer.close();
        }
    }
}
