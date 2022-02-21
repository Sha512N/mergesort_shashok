package shashok.mergesort;

import java.io.*;

public class FileElement {

    private BufferedReader reader;
    private String cache;
    private boolean isEmpty;

    public FileElement(File file, int bufsize) {
        try {
            this.reader = new BufferedReader(new FileReader(file), bufsize);
            readNext();
        } catch (IOException e) {
            this.isEmpty = true;
            this.cache = null;
        }
    }

    public String peek() {
        return this.cache;
    }

    public String pop() {
        String value = this.peek();
        this.readNext();
        return value;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void close() throws IOException {
        reader.close();
    }

    private void readNext() {
        try {
            this.cache = reader.readLine();
            this.isEmpty = this.cache == null;
        } catch (IOException exception) {
            this.isEmpty = true;
            this.cache = null;
        }
    }
}
