package com.vizurd.tryout.refactoring.practice;

import java.time.LocalDate;

public class Appointment {
    private LocalDate _date;

    Appointment(LocalDate date) {
        _date = date;
    }

    public LocalDate getDate() {
        return _date;
    }
}
