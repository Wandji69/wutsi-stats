package com.wutsi.stats.impl;

import com.wutsi.stats.InputStreamIterator;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ClasspathInputStreamIteratorTest {

    @Test
    public void next() throws Exception {
        InputStreamIterator it = new ClasspathInputStreamIterator(
                Arrays.asList(
                        "/tracks/viewers/happy-path/2020-04-14-000.csv",
                        "/tracks/viewers/happy-path/2020-04-14-001.csv"
                )
        );
        assertNotNull(it.next());
        assertNotNull(it.next());
        assertNull(it.next());
    }

    @Test
    public void nextWithInvalidPath() throws Exception {
        InputStreamIterator it = new ClasspathInputStreamIterator(
                Arrays.asList(
                        "/tracks/viewers/happy-path/2020-04-14-000.csv",
                        "/tracks/xxxxx",
                        "/tracks/viewers/happy-path/2020-04-14-001.csv",
                        "/tracks/yyyy"
                )
        );
        assertNotNull(it.next());
        assertNotNull(it.next());
        assertNull(it.next());
    }
}
