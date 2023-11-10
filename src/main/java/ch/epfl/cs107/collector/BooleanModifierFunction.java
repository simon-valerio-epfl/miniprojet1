package ch.epfl.cs107.collector;

@FunctionalInterface
public interface BooleanModifierFunction {
    boolean apply(String x);
}
