package com.yaini.study;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class EnumMapBenchMark {
    private Map<State, String> enumMap;
    private Map<State, String> hashMap;

    @Setup
    public void setUp() {
        enumMap = new EnumMap<>(State.class);
        hashMap = new HashMap<>();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void enumMap(Blackhole blackhole) {
        blackhole.consume(enumMap.put(State.SOLID, State.SOLID.name()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void hashMap(Blackhole blackhole) {
        blackhole.consume(hashMap.put(State.SOLID, State.SOLID.name()));
    }

    enum State {
        SOLID, LIQUID, GAS;
    }
}