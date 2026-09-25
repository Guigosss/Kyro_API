-- Insert roles
INSERT INTO role_ (name) VALUES ('USER');
INSERT INTO role_ (name) VALUES ('ADMIN');

-- Insert users
INSERT INTO user_ (username, password, role_id, created_at, updated_at)
VALUES ('Guigos', '$2a$10$T.hS506DjX00K6WKB.FzhO8r1Gs1p0Rt5LcrVH5SSkdK.waPLjFl6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_ (username, password, role_id, created_at, updated_at)
VALUES ('Guigui', '$2a$10$T.hS506DjX00K6WKB.FzhO8r1Gs1p0Rt5LcrVH5SSkdK.waPLjFl6', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert muscle groups
INSERT INTO muscle_group (name) VALUES ('Chest');
INSERT INTO muscle_group (name) VALUES ('Back');
INSERT INTO muscle_group (name) VALUES ('Biceps');
INSERT INTO muscle_group (name) VALUES ('Triceps');
INSERT INTO muscle_group (name) VALUES ('Shoulder');
INSERT INTO muscle_group (name) VALUES ('Ischio');
INSERT INTO muscle_group (name) VALUES ('Quadriceps');
INSERT INTO muscle_group (name) VALUES ('Glutes');
INSERT INTO muscle_group (name) VALUES ('Calves');

-- Insert exercises
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Bench Press', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Incline Dumbbell Press', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Pull Up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Rowing Bar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Biceps Curl', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Triceps Extension', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Squat', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Legs Press', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Leg Curl', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Calf Raise', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);
INSERT INTO exercise (name, created_at, updated_at, is_enabled) VALUES ('Deadlift', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Insert exercise muscle groups
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (1, 1, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (1, 4, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (1, 5, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (2, 1, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (2, 4, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (2, 5, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (3, 2, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (3, 3, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (4, 2, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (4, 3, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (5, 3, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (6, 4, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (7, 7, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (7, 8, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (7, 6, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (8, 7, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (8, 8, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (9, 6, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (10, 9, true);

INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (11, 8, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (11, 6, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (11, 7, true);
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id, is_enabled) VALUES (11, 2, true);

-- Insert workout sessions
INSERT INTO workout_session (name, date, notes, user_id)
VALUES ('Push Day', CURRENT_DATE - 6, 'Bonne séance pectoraux.', 1);

INSERT INTO workout_session (name, date, notes, user_id)
VALUES ('Pull Day', CURRENT_DATE - 4, 'Bonnes sensations sur le dos.', 1);

INSERT INTO workout_session (name, date, notes, user_id)
VALUES ('Leg Day', CURRENT_DATE - 2, 'Séance jambes assez intense.', 1);

-- Insert workout sets
-- Push
INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (80.0, 10, 1, 1, 1);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (80.0, 8, 2, 1, 1);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (75.0, 8, 3, 1, 1);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (24.0, 12, 1, 1, 2);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (24.0, 10, 2, 1, 2);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (20.0, 12, 1, 1, 6);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (20.0, 10, 2, 1, 6);

-- Pull
INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (20.0, 10, 1, 2, 3);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (30.0, 8, 2, 2, 3);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (60.0, 10, 1, 2, 4);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (60.0, 8, 2, 2, 4);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (14.0, 12, 1, 2, 5);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (14.0, 10, 2, 2, 5);

-- Legs
INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (100.0, 10, 1, 3, 7);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (120.0, 8, 2, 3, 7);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (140.0, 5, 3, 3, 7);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (160.0, 12, 1, 3, 8);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (160.0, 10, 2, 3, 8);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (40.0, 12, 1, 3, 9);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (40.0, 10, 2, 3, 9);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (60.0, 15, 1, 3, 10);

INSERT INTO workout_set (weight, reps, order_index, session_id, exercise_id)
VALUES (60.0, 12, 2, 3, 10);
