package app;

public abstract class Oscillator {

    private Range range;
    private double value;
    private double step;
    private boolean radial;

    Oscillator(Range range, Start startPosition, boolean reverse, boolean radial, double speed) {
        this.range = range;

        setSpeed(speed);

        if (reverse) {
            step = -step;
        }

        this.radial = radial;

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
        double initialValue = value;

        if (radial) {
            if (value < range.min) {
                value = range.max;
            } else if (value > range.max) {
                value = range.min;
            }
        }
        else if (value < range.min || value > range.max) {
            step = -step;
        }

        value = value + step;
        return step(initialValue);
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

    public void setSpeed(double speed) {
        this.step = (range.max - range.min) * speed;
    }
}
