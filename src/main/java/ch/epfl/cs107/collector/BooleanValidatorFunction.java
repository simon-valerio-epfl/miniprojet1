package ch.epfl.cs107.collector;

@FunctionalInterface
public interface BooleanValidatorFunction {
    boolean apply(String x);
}
