package com.wutsi.stats.readtime;
import com.wutsi.stats.impl.AbstractWriter;

import java.io.IOException;
import java.io.Writer;

public class ReadTimeWriter extends AbstractWriter<ReadTime> {

    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"productid\",\"readtime\"\n");
    }
}
