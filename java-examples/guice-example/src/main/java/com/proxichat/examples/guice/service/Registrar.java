package com.proxichat.examples.guice.service;

import com.proxichat.examples.guice.domain.Student;

public interface Registrar {
    public boolean checkStudentStatus( Integer studentId );
    public Student registerStudent( String name, Integer credits );
}
