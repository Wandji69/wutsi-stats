package com.wutsi.stats.impl;

import com.wutsi.stats.InputStreamIterator;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ClasspathInputStreamIterator implements InputStreamIterator {
    private Iterator<InputStream> iterator;

    public ClasspathInputStreamIterator(final List<String> paths) {
        this.iterator = paths
                .stream()
                .map(it -> ClasspathInputStreamIterator.class.getResourceAsStream(it))
                .filter(it -> it != null)
                .collect(Collectors.toList())
                .iterator();
    }

    @Override
    public InputStream next() {
        try {
            return iterator.next();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }
}
