package shashok.mergesort.casting;

import shashok.mergesort.ICasting;

public class StringCasting implements ICasting<String> {

    @Override
    public String cast(String s) {
        return s;
    }
}
