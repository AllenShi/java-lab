package net.sjl.fragrans.bench;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.annotations.OutputTimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(StreamVsVanilla.N)
public class StreamVsVanilla {
    public static final int N = 10000;

    static List<Integer> sourceList = new ArrayList<>();
    static {
        for (int i = 0; i < N; i++) {
            sourceList.add(i);
        }
    }

    @Benchmark
    public List<Double> vanilla() {
        List<Double> result = new ArrayList<>(sourceList.size() / 2 + 1);
        for (Integer i : sourceList) {
            if (i % 2 == 0){
                result.add(Math.sqrt(i));
            }
        }
        return result;
    }

    @Benchmark
    public List<Double> stream() {
        return sourceList.stream()
                .filter(i -> i % 2 == 0)
                .map(Math::sqrt)
                .collect(Collectors.toCollection(
                    () -> new ArrayList<>(sourceList.size() / 2 + 1)));
    }
}
