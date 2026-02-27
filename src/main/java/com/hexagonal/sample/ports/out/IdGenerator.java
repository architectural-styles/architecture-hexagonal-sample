package com.hexagonal.sample.ports.out;

/**
 * Port for generating unique user identifiers.
 * Default implementation produces UUID v7 (time-based, sortable).
 */
@FunctionalInterface
public interface IdGenerator {
    String nextId();
}
