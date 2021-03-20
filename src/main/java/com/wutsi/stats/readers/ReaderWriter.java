package com.wutsi.stats.readers;
import com.wutsi.stats.impl.AbstractWriter;

import java.io.IOException;
import java.io.Writer;

public class ReaderWriter extends AbstractWriter<Reader> {

    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"productid\",\"count\"\n");
    }
}
