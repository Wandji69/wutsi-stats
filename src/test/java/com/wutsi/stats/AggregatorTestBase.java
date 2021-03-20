package com.wutsi.stats;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import com.wutsi.stats.impl.ClasspathInputStreamIterator;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public abstract class AggregatorTestBase {
    protected abstract Aggregator getAggregator();

    protected void test(String expectedPath, String...paths) throws Exception {
        InputStreamIterator iterator = createIterator(paths);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // WHEN
        getAggregator().aggregate(iterator, out);

        // THEN
        InputStream expected = getClass().getResourceAsStream(expectedPath);
        assertCsvMatches(expected, new ByteArrayInputStream(out.toByteArray()));
    }

    protected void assertCsvMatches(InputStream expected, InputStream value) throws Exception {
        Patch<String> patch = DiffUtils.diff(
                toLines(value),
                toLines(expected)
        );
        System.out.println("Delta: " + patch.getDeltas());
        assertTrue(patch.getDeltas().isEmpty());
    }

    protected List<String> toLines(InputStream in) throws IOException {
        return Arrays.asList(IOUtils.toString(in).split("\\r?\\n"))
                .stream()
                .filter(it -> !it.isEmpty())
                .map(it -> it + "\n")
                .collect(Collectors.toList());
    }

    protected InputStreamIterator createIterator(String...paths) {
        return new ClasspathInputStreamIterator(Arrays.asList(paths));
    }
}
