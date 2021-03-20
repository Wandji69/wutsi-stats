package com.wutsi.stats.shares;
import com.wutsi.stats.impl.AbstractWriter;
import java.io.IOException;
import java.io.Writer;

public class ShareWriter extends AbstractWriter<Share> {

    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"productid\",\"count\"\n");
    }
}
