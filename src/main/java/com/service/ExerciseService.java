package com.service;

import java.util.List;

import com.fittracker.model.Exercise;
import com.fittracker.model.ExerciseDAO;


// Pourquoi exerciseService ? eh bien mon bon Roshan c'est pour respecter le SRP
public class ExerciseService {
    private ExerciseDAO exerciseDAO = new ExerciseDAO();

    public List<Exercise> getAllExercises(){
        return exerciseDAO.getAllExercises();
    };
}
