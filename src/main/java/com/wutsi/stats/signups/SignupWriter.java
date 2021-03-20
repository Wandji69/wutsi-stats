package com.wutsi.stats.signups;
import com.wutsi.stats.impl.AbstractWriter;

import java.io.IOException;
import java.io.Writer;

public class SignupWriter extends AbstractWriter<Signup> {
    @Override
    protected void writeHeader(Writer writer) throws IOException {
        writer.append("\"time\",\"userid\",\"count\"\n");
    }
}
