package com.vizurd.tryout.refactoring.practice;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Kent {
    List<Appointment> getCourses() {
        return Arrays.asList(
                new Appointment(LocalDate.ofEpochDay(new Random().nextInt())),
                new Appointment(LocalDate.now()),
                new Appointment(LocalDate.ofEpochDay(new Random().nextInt())),
                new Appointment(LocalDate.ofEpochDay(new Random().nextInt())),
                new Appointment(LocalDate.now()));
    }
}
