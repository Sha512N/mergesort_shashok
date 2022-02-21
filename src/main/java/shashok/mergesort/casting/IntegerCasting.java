package shashok.mergesort.casting;

import shashok.mergesort.ICasting;

public class IntegerCasting implements ICasting<Integer> {
    @Override
    public Integer cast(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
