package com.vizurd.tryout.refactoring.practice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyCalendar {
    private Set<Appointment> appointments;
    private Kent kent = new Kent();

    /**
     * Finds all the appointments for the specified date.
     *
     * @param date the date for which appointments are to be found
     * @return the list of appointments
     * @deprecated Use {@link #findAppointments(LocalDate, String)} instead
     */
    @Deprecated
    private List<Appointment> findAppointments(LocalDate date) {
        return findAppointments(date, "");
    }

    /**
     * Finds all the appointments for the specified date.
     *
     * @param date the date for which appointments are to be found
     * @param name the visitor name
     * @return the list of appointments
     */
    private List<Appointment> findAppointments(LocalDate date, String name) {
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
