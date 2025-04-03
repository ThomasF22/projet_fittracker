package com.service;

import java.util.List;

import com.fittracker.model.Routine;
import com.fittracker.model.RoutineDAO;


public class RoutineService {
    private RoutineDAO routineDAO = new RoutineDAO();


    public List<Routine> getAllRoutines(){
        return routineDAO.getAllRoutines();
    }
}
