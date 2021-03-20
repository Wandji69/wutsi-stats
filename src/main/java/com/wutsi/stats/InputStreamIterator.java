package com.wutsi.stats;

import java.io.InputStream;

public interface InputStreamIterator {
    InputStream next();
    boolean hasNext();
}
