package com.wutsi.stats.xread;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import com.wutsi.stats.InputStreamIterator;
import com.wutsi.stats.impl.ClasspathInputStreamIterator;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class DailyXReadAggregatorTest {
    DailyXReadAggregator aggregator;

    @Test
    public void happyPath() throws Exception {
        test(
                "/tracks/xread/happy-path/2020-04-14-output.csv",
                "/tracks/xread/happy-path/2020-04-14-000.csv",
                "/tracks/xread/happy-path/2020-04-14-001.csv"
        );
    }


    private void test(String expectedPath, String...paths) throws Exception {
        InputStreamIterator iterator = createIterator(paths);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // WHEN
        aggregator = new DailyXReadAggregator(LocalDate.parse("2020-04-14"));
        aggregator.aggregate(iterator, out);

        // THEN
        InputStream expected = getClass().getResourceAsStream(expectedPath);
        assertCsvMatches(expected, new ByteArrayInputStream(out.toByteArray()));
    }

    private void assertCsvMatches(InputStream expected, InputStream value) throws Exception {
        Patch<String> patch = DiffUtils.diff(
                toLines(value),
                toLines(expected)
        );
        System.out.println("Delta: " + patch.getDeltas());
        assertTrue(patch.getDeltas().isEmpty());
    }

    private List<String> toLines(InputStream in) throws IOException {
        return Arrays.asList(IOUtils.toString(in).split("\\r?\\n"))
                .stream()
                .filter(it -> !it.isEmpty())
                .map(it -> it + "\n")
                .collect(Collectors.toList());
    }

    private InputStreamIterator createIterator(String...paths) {
        return new ClasspathInputStreamIterator(Arrays.asList(paths));
    }
}
