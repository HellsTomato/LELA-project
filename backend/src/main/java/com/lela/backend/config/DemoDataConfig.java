package com.lela.backend.config;

import com.lela.backend.entity.*;
import com.lela.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Seeds minimal demo data on application startup.
 *
 * Why this is useful for MVP:
 * - You can show working endpoints immediately.
 * - No manual SQL setup is required before demo.
 */
@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               CourseRepository courseRepository,
                               UnitRepository unitRepository,
                               LessonRepository lessonRepository,
                               RewardRepository rewardRepository) {
        return args -> {
            if (courseRepository.count() > 0) {
                // Prevent duplicate demo records on app restart.
                return;
            }

            rewardRepository.saveAll(List.of(
                    new Reward("Coffee Voucher", "Get a free coffee after active study", 30),
                    new Reward("Book Discount", "10% discount on language books", 60),
                    new Reward("Conversation Club Invite", "Access to offline speaking event", 100)
            ));

            userRepository.saveAll(List.of(
                    new User("Alice", "alice@lela.local", 0),
                    new User("Bob", "bob@lela.local", 20)
            ));

            Course englishA1 = courseRepository.save(new Course(
                    "English A1 Starter",
                    "Foundations of basic vocabulary, greetings, and daily phrases"
            ));

            Unit basicsUnit = unitRepository.save(new Unit("Unit 1: Basics", 1, englishA1));
            Unit dailyLifeUnit = unitRepository.save(new Unit("Unit 2: Daily Life", 2, englishA1));

            lessonRepository.saveAll(List.of(
                    new Lesson(
                            "Lesson 1: Greetings",
                            "Learn hello, goodbye, and polite introductions in simple dialogues.",
                            15,
                            basicsUnit
                    ),
                    new Lesson(
                            "Lesson 2: Numbers",
                            "Practice numbers 1-20 and use them in small conversation tasks.",
                            15,
                            basicsUnit
                    ),
                    new Lesson(
                            "Lesson 3: At the Cafe",
                            "Useful words and phrases for ordering drinks and snacks.",
                            20,
                            dailyLifeUnit
                    )
            ));
        };
    }
}
