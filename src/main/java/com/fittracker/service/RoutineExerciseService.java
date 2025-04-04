package com.fittracker.service;

import java.util.List;

import com.fittracker.model.RoutineExercise;
import com.fittracker.model.RoutineExerciseDAO;


public class RoutineExerciseService {
    private RoutineExerciseDAO routineExerciseDAO = new RoutineExerciseDAO();


    public List<RoutineExercise> getRoutineExercisesById(int routineId){
        return routineExerciseDAO.getRoutineExercisesById(routineId);
    }
    public List<RoutineExercise> getRoutineExercisesForUser(int routineId, int userId){
        return routineExerciseDAO.getRoutineExercisesForUser(routineId, userId);
    }

    public List<RoutineExercise> getExercisesInRoutine(int routineId) {
        return routineExerciseDAO.getRoutineExercisesById(routineId);
    }

    public boolean addExerciseToRoutine(int routineId, int exerciseId){
        return routineExerciseDAO.addExerciseToRoutine(routineId, exerciseId);
    }
    
    public boolean removeExerciseFromRoutine(int routineId, int exerciseId){
        return routineExerciseDAO.removeExerciseFromRoutine(routineId, exerciseId);
    }

    public boolean updateExerciseDetails(int routineId, int exerciseId, double weight, int duration, int reps){
        return routineExerciseDAO.updateExerciseDetails(routineId, exerciseId, weight, duration, reps);
    }
}
