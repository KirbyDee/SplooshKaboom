package com.kirbydee.splooshkaboom.model.counter;

public class Rupees {

    private int count;

    public static Rupees of(int count) {
        return new Rupees(count);
    }

    public Rupees(int startCount) {
        this.count = startCount;
    }

    public void increase() {
        increase(1);
    }

    public void increase(int amount) {
        this.count += amount;
    }

    public void decrease() {
        decrease(1);
    }

    public void decrease(int amount) {
        this.count -= amount;
    }

    public int get() {
        return count;
    }

    public void set(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Rupees{" +
                "count=" + count +
                '}';
    }
}
