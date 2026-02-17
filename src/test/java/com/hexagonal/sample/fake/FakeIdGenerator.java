package com.hexagonal.sample.fake;

import com.hexagonal.sample.ports.out.IdGenerator;

public class FakeIdGenerator implements IdGenerator {
    @Override
    public String nextId() {
        return "42"; // фиксированный ID для предсказуемого теста
    }
}
