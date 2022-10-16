package com.yaini.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Fork(1)
@Measurement(iterations = 10)
public class StreamBenchMark {
  public static final int N = 10000;

  static List<Integer> sourceList = new ArrayList<>();

  @Setup
  public void setUp() {
    for (int i = 0; i < N; i++) {
      sourceList.add(i);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void useForEach(Blackhole blackhole) {
    List<Double> result = new ArrayList<>(sourceList.size() / 2 + 1);
    for (Integer i : sourceList) {
      if (i % 2 == 0) {
        result.add(Math.sqrt(i));
      }
    }
    blackhole.consume(result);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void useStream(Blackhole blackhole) {
    List<Double> result =
        sourceList.stream()
            .filter(i -> i % 2 == 0)
            .map(Math::sqrt)
            .collect(Collectors.toCollection(() -> new ArrayList<>(sourceList.size() / 2 + 1)));
    blackhole.consume(result);
  }
}
