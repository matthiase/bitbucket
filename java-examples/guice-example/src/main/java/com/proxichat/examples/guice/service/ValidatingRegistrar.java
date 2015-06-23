package com.proxichat.examples.guice.service;

import com.proxichat.examples.guice.domain.Student;
import com.proxichat.examples.guice.store.StorageProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ValidatingRegistrar implements Registrar {

    private final StorageProvider studentStore;

    @Inject
    public ValidatingRegistrar(StorageProvider studentStore) {
        this.studentStore = studentStore;
    }

    public boolean checkStudentStatus(Integer studentId) {
        boolean status = false;

        Student student = studentStore.load( studentId );

        if( student != null && student.getCredits() != null ) {
            status = student.getCredits() >= 10.0;
        }
        return status;

    }

    public Student registerStudent(String name, Integer credits) {
        Student s = new Student();
        s.setName( name );
        s.setCredits( credits );
        return studentStore.save( s );
    }
}
