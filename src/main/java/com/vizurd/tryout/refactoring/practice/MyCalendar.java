package com.vizurd.tryout.refactoring.practice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyCalendar {
    private Set<Appointment> appointments;
    private Kent kent = new Kent();

    private List<Appointment> findAppointments(LocalDate date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : kent.getCourses()) {
            if (date.compareTo(appointment.getDate()) == 0) {
                result.add(appointment);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        MyCalendar myCalendar = new MyCalendar();
        myCalendar.findAppointments(LocalDate.now());
    }
}
