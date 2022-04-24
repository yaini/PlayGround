package com.yaini.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class ObjectMapperBenchMark {
    private String sampleJson = "{\"test\":\"test\"}";
    private ObjectMapper createInstance;
    private ObjectMapper useSingleTon;

    @Setup
    public void setUp() {
        createInstance = new ObjectMapper();
        useSingleTon = new ObjectMapper();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void createInstance(Blackhole blackhole) throws JsonProcessingException {
        blackhole.consume(new ObjectMapper().readValue(sampleJson, Test.class));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void useSingleTon(Blackhole blackhole) throws JsonProcessingException {
        blackhole.consume(useSingleTon.readValue(sampleJson, Test.class));
    }

}