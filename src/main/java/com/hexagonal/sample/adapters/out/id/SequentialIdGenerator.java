package com.hexagonal.sample.adapters.out.id;

import com.hexagonal.sample.ports.out.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;


public class SequentialIdGenerator implements IdGenerator {

    private final AtomicLong seq = new AtomicLong(3);

    @Override
    public String nextId() {
        return String.valueOf(seq.incrementAndGet());
    }
}
