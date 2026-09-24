package com.bulk.kyro.dal.initializers;

import com.bulk.kyro.dal.repositories.ExerciseRepository;
import com.bulk.kyro.dal.repositories.MuscleGroupRepository;
import com.bulk.kyro.dal.repositories.RoleRepository;
import com.bulk.kyro.dal.repositories.UserRepository;
import com.bulk.kyro.dal.repositories.WorkoutSessionRepository;
import com.bulk.kyro.dal.repositories.WorkoutSetRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import com.bulk.kyro.dl.entities.RoleEntity;
import com.bulk.kyro.dl.entities.UserEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import com.bulk.kyro.dl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Seed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutSetRepository workoutSetRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        //- User Entities
        RoleEntity userRole = new RoleEntity(UserRole.USER.name());
        RoleEntity adminRole = new RoleEntity(UserRole.ADMIN.name());
        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        String password = passwordEncoder.encode("Test1234");
        UserEntity user = new UserEntity("Guigos", password, userRole);
        UserEntity admin = new UserEntity("Guigui", password, adminRole);
        userRepository.saveAll(List.of(admin,user));

        //- Muscle Group Entities
        MuscleGroupEntity chest = muscleGroupRepository.save(new MuscleGroupEntity("Chest"));
        MuscleGroupEntity back = muscleGroupRepository.save(new MuscleGroupEntity("Back"));
        MuscleGroupEntity biceps = muscleGroupRepository.save(new MuscleGroupEntity("Biceps"));
        MuscleGroupEntity triceps = muscleGroupRepository.save(new MuscleGroupEntity("Triceps"));
        MuscleGroupEntity shoulder = muscleGroupRepository.save(new MuscleGroupEntity("Shoulder"));
        MuscleGroupEntity ischios = muscleGroupRepository.save(new MuscleGroupEntity("Ischio"));
        MuscleGroupEntity quadriceps = muscleGroupRepository.save(new MuscleGroupEntity("Quadriceps"));
        MuscleGroupEntity glutes = muscleGroupRepository.save(new MuscleGroupEntity("Glutes"));
        MuscleGroupEntity calves = muscleGroupRepository.save(new MuscleGroupEntity("Calves"));

        //- Excercise Entities
        ExerciseEntity benchPress = exerciseRepository.save(
                new ExerciseEntity("Bench Press", Set.of(chest, triceps, shoulder)));

        ExerciseEntity inclinePress = exerciseRepository.save(
                new ExerciseEntity("Incline Dumbbell Press", Set.of(chest, triceps, shoulder)));

        ExerciseEntity pullUps = exerciseRepository.save(
                new ExerciseEntity("Pull Up", Set.of(back, biceps)));

        ExerciseEntity rowing = exerciseRepository.save(
                new ExerciseEntity("Rowing Bar", Set.of(back, biceps)));

        ExerciseEntity bicepsCurl = exerciseRepository.save(
                new ExerciseEntity("Biceps Curl", Set.of(biceps)));

        ExerciseEntity tricepsPushdown = exerciseRepository.save(
                new ExerciseEntity("Triceps Extension", Set.of(triceps)));

        ExerciseEntity squat = exerciseRepository.save(
                new ExerciseEntity("Squat", Set.of(quadriceps, glutes, ischios)));

        ExerciseEntity legPress = exerciseRepository.save(
                new ExerciseEntity("Legs Press", Set.of(quadriceps, glutes)));

        ExerciseEntity legCurl = exerciseRepository.save(
                new ExerciseEntity("Leg Curl", Set.of(ischios)));

        ExerciseEntity calfRaise = exerciseRepository.save(
                new ExerciseEntity("Calf Raise", Set.of(calves)));

        ExerciseEntity deadlift = exerciseRepository.save(
                new ExerciseEntity("Deadlift", Set.of(glutes, ischios, quadriceps, back))); //- To be deleted

        //- Workout Session Entities
        WorkoutSessionEntity push = workoutSessionRepository.save(
                new WorkoutSessionEntity("Push Day", LocalDate.now().minusDays(6), "Bonne séance pectoraux.", user));

        WorkoutSessionEntity pull = workoutSessionRepository.save(
                new WorkoutSessionEntity("Pull Day", LocalDate.now().minusDays(4), "Bonnes sensations sur le dos.", user));

        WorkoutSessionEntity legs = workoutSessionRepository.save(
                new WorkoutSessionEntity("Leg Day", LocalDate.now().minusDays(2), "Séance jambes assez intense.", user));

        //- Workout Set Entities
        //- Push
        workoutSetRepository.saveAll(List.of(

                new WorkoutSetEntity(80.0, 10, 1, push, benchPress),
                new WorkoutSetEntity(80.0, 8, 2, push, benchPress),
                new WorkoutSetEntity(75.0, 8, 3, push, benchPress),

                new WorkoutSetEntity(24.0, 12, 1, push, inclinePress),
                new WorkoutSetEntity(24.0, 10, 2, push, inclinePress),

                new WorkoutSetEntity(20.0, 12, 1, push, tricepsPushdown),
                new WorkoutSetEntity(20.0, 10, 2, push, tricepsPushdown)
        ));

        //- Pull
        workoutSetRepository.saveAll(List.of(

                new WorkoutSetEntity(20.0, 10, 1, pull, pullUps),
                new WorkoutSetEntity(30.0, 8, 2, pull, pullUps),

                new WorkoutSetEntity(60.0, 10, 1, pull, rowing),
                new WorkoutSetEntity(60.0, 8, 2, pull, rowing),

                new WorkoutSetEntity(14.0, 12, 1, pull, bicepsCurl),
                new WorkoutSetEntity(14.0, 10, 2, pull, bicepsCurl)
        ));

        //- Legs
        workoutSetRepository.saveAll(List.of(

                new WorkoutSetEntity(100.0, 10, 1, legs, squat),
                new WorkoutSetEntity(120.0, 8, 2, legs, squat),
                new WorkoutSetEntity(140.0, 5, 3, legs, squat),

                new WorkoutSetEntity(160.0, 12, 1, legs, legPress),
                new WorkoutSetEntity(160.0, 10, 2, legs, legPress),

                new WorkoutSetEntity(40.0, 12, 1, legs, legCurl),
                new WorkoutSetEntity(40.0, 10, 2, legs, legCurl),

                new WorkoutSetEntity(60.0, 15, 1, legs, calfRaise),
                new WorkoutSetEntity(60.0, 12, 2, legs, calfRaise)
        ));
    }
}
