package ch.epfl.cs107.collector;

@FunctionalInterface
public interface StringValidatorFunction {
    boolean apply(String x);
}
