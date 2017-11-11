package ch.raiffeisen.ipricer.fxdesigner.domain;

public class GridGroesse {
    public static final Integer DEFAULT_COLS = 8;
    public static final Integer DEFAULT_ROWS = 10;

    public GridGroesse() {
        cols = DEFAULT_COLS;
        rows = DEFAULT_ROWS;
    }

    public int cols;
    public int rows;
}
