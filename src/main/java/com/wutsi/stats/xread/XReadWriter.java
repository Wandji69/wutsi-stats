package com.wutsi.stats.xread;
import com.wutsi.stats.impl.AbstractWriter;

import java.io.IOException;
import java.io.Writer;

public class XReadWriter extends AbstractWriter<XRead> {
    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"productid\",\"count\"\n");
    }
}
