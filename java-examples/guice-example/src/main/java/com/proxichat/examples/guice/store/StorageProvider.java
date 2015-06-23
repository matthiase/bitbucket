package com.proxichat.examples.guice.store;

import com.proxichat.examples.guice.domain.Student;

public interface StorageProvider {
    public boolean exists(Integer id);
    public Student load(Integer id);
    public Student save(Student p);
}