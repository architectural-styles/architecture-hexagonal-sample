package com.hexagonal.sample.adapters.out.id;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.hexagonal.sample.ports.out.IdGenerator;

/**
 * Package-private.
 */
final class UuidV7IdGenerator implements IdGenerator {

    private static final
    TimeBasedEpochGenerator GENERATOR =
            Generators.timeBasedEpochGenerator();


    @Override
    public String nextId() {
        return GENERATOR.generate().toString();
    }
}
