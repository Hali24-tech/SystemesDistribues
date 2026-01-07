package Projet;

import java.util.Arrays;

public class VectorClock {
    private int[] clock;

    public VectorClock(int[] arr) {
        this.clock = Arrays.copyOf(arr, arr.length);
    }

    public synchronized void tick(int nodeId) {
        clock[nodeId]++;
    }

    public synchronized void update(VectorClock other) {
        for (int i = 0; i < clock.length; i++) {
            clock[i] = Math.max(clock[i], other.clock[i]);
        }
    }

    public int get(int i) {
        return clock[i];
    }

    public int size() {
        return clock.length;
    }

    public int[] copy() {
        return Arrays.copyOf(clock, clock.length);
    }

    @Override
    public String toString() {
        return Arrays.toString(clock);
    }
}
