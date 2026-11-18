package org.example;

import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;
import exceptions.NegativeTimeSpanException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan(int h, int m) {
        if (h < 0 || m < 0 || m > 59) throw new IllegalInputTimeSpanException("Помилка часу");
        this.hours = h;
        this.minutes = m;
    }

    public void add(int h, int m) {
        if (h < 0 || m < 0 || m > 59) throw new IllegalInputTimeSpanException("Помилка часу");
        this.hours += h;
        this.minutes += m;
        if (this.minutes > 59) { this.hours += this.minutes / 60; this.minutes %= 60; }
    }


    public void subtract(int h, int m) {
        if (h < 0 || m < 0 || m > 59) throw new IllegalInputTimeSpanException("Помилка часу");
        this.hours -= h;
        this.minutes -= m;
        if (this.minutes < 0) { this.minutes += 60; this.hours -= 1; }
        if (this.hours < 0) throw new NegativeTimeSpanException("Від'ємний час");
    }


    public void scale(int factor) {
        if (factor <= 0) throw new IllegalScaleFactorException("Помилка множника");
        this.hours *= factor;
        this.minutes *= factor;
        if (this.minutes > 59) { this.hours += this.minutes / 60; this.minutes %= 60; }
    }

    @Override
    public String toString() {
        return hours + " г " + minutes + " хв";
    }
}