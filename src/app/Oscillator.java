package app;

public abstract class Oscillator {

    private Range range;
    private double value;
    private double step;

    Oscillator(Range range, Start startPosition, boolean reverse, double speed) {
        this.range = range;
        this.step = (range.max - range.min) * speed;

        if (reverse) {
            step = -step;
        }

        switch (startPosition) {
            case START:
                value = range.min;
                break;
            case CENTER:
                value = range.min + ((range.max - range.min) / 2);
                break;
            case END:
                value = range.max;
                break;
        }
    }

    double doStep() {
        if (value < range.min || value > range.max) {
            step = -step;
        }
        value = value + step;
        return step(value);
    }

    protected abstract double step(double value);

    static class Range {
        double min, max;

        Range(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }

    enum Start {
        START, CENTER, END
    }
}
