package com.fittracker;

import com.fittracker.model.Exercise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class ExerciseTest {

    @Test
    void testExerciseCreation() {
        Exercise exercise = new Exercise(1, "Push-ups");

        assertNotNull(exercise);
        assertEquals(1, exercise.getId());
        assertEquals("Push-ups", exercise.getName());
    }

    @Test
    void testToString() {
        Exercise exercise = new Exercise(2, "Squats");

        assertEquals("Squats", exercise.toString());
    }
}
