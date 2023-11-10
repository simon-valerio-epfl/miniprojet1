package ch.epfl.cs107.collector;

@FunctionalInterface
public interface StringModifierFunction {
    String apply(String x);
}
