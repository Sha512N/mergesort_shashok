package shashok.mergesort;

import java.io.*;
import java.util.*;

public class MergeSort<T> {

    private final Comparator<T> comparator;
    private final ICasting<T> casting;

    public MergeSort(Comparator<T> comparator, ICasting<T> casting) {
        this.comparator = comparator;
        this.casting = casting;
    }

    public int mergeFiles(Collection<File> files, File output) throws IOException {
        int bufsize = castLongToInt(calculateBufSize(files));
        PriorityQueue<FileElement> queue = createFileQueue(files, bufsize);
        BufferedWriter outWriter = new BufferedWriter(new FileWriter(output), bufsize);
        int rows = 0;
        try {
            T previousValue = null;
            while(!queue.isEmpty()) {
                FileElement fe = queue.poll();
                String s = fe.pop();
                T element = casting.cast(s);
                cleanFileElement(fe);
                if (fe.isEmpty()) {
                    fe.close();
                } else {
                    queue.add(fe);
                }
                if (element == null
                        ||
                        (previousValue != null && this.comparator.compare(previousValue, element) > 0)) {
                    continue;
                }
                previousValue = element;
                outWriter.write(s);
                outWriter.newLine();
                ++rows;
            }
        } finally {
            outWriter.close();
            for (FileElement fe: queue) fe.close();
        }
        return rows;
    }

    private PriorityQueue<FileElement> createFileQueue(Collection<File> files, int bufsize) {
        PriorityQueue<FileElement> queue = new PriorityQueue<>(files.size(), (o1, o2) -> {
            T elem1 = casting.cast(o1.peek());
            T elem2 = casting.cast(o2.peek());
            return comparator.compare(elem1, elem2);
        });

        for (File f: files) {
            FileElement felem = new FileElement(f, bufsize);
            cleanFileElement(felem);
            if (felem.isEmpty()) {
                continue;
            }
            queue.add(felem);
        }
        return queue;
    }

    private void cleanFileElement(FileElement fe) {
        while (!fe.isEmpty() && casting.cast(fe.peek()) == null) {
            fe.pop();
        }
    }

    private long calculateBufSize(Collection<File> files) {
        return Runtime.getRuntime().freeMemory() / (files.size() + 1) * 2 / 3;
    }

    private int castLongToInt(long l) {
        if (!(Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE)) {
            return Integer.MAX_VALUE;
        }
        return (int) l;
    }
}
