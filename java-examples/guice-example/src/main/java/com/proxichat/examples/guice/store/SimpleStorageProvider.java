package com.proxichat.examples.guice.store;

import com.proxichat.examples.guice.domain.Student;

import java.util.HashMap;
import java.util.Map;

public class SimpleStorageProvider implements StorageProvider {

    private final Map<Integer,Student> studentMap;

    public SimpleStorageProvider() {
        studentMap = new HashMap<>();
    }

    public boolean exists(Integer id) {
        return studentMap.containsKey( id );
    }

    public Student load(Integer id) {
        return studentMap.get(id);
    }

    public Student save(Student p) {
        if( p.getId() == null ) {
            p.setId( studentMap.size() + 1 );
        }
        studentMap.put( p.getId(), p );
        return p;
    }

}
