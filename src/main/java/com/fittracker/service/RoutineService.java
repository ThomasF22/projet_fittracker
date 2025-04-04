package com.fittracker.service;

import java.util.List;

import com.fittracker.model.Routine;
import com.fittracker.model.RoutineDAO;


public class RoutineService {
    private RoutineDAO routineDAO = new RoutineDAO();


    public List<Routine> getAllRoutines(){
        return routineDAO.getAllRoutines();
    }

    public List<Routine> getUserRoutines(int userId){
        return routineDAO.getUserRoutines(userId);
    }

    public boolean addRoutine(String name, int userId){
        return routineDAO.addRoutine(name, userId);
    }

    public boolean removeRoutine(int userId){
        return routineDAO.removeRoutine(userId);
    }
}
