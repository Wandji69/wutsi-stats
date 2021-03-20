package com.wutsi.stats.viewers;
import com.wutsi.stats.impl.AbstractWriter;
import java.io.IOException;
import java.io.Writer;

public class ViewerWriter extends AbstractWriter<Viewer> {
    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"productid\",\"count\"\n");
    }
}
