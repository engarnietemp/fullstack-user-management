package com.enzog.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.enzog.backend.entity.User;
import com.enzog.backend.entity.UserType;
import com.enzog.backend.repository.UserRepository;
import com.enzog.backend.repository.UserTypeRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;

    public DataInitializer(UserTypeRepository userTypeRepository, UserRepository userRepository) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        if (userTypeRepository.count() > 0) {
            System.out.println("✅ Database already initialized, skipping data insertion");
            return;
        }

        System.out.println("🔄 Initializing database with test data...");

        /* ========== CREATION OF USER TYPES ========== */
        UserType adminType = new UserType();
        adminType.setLabel("Administrator");
        adminType = userTypeRepository.save(adminType);

        UserType commercialType = new UserType();
        commercialType.setLabel("Commercial");
        commercialType = userTypeRepository.save(commercialType);

        UserType managerType = new UserType();
        managerType.setLabel("Manager");
        managerType = userTypeRepository.save(managerType);

        System.out.println("✅ Created 3 UserTypes");

        
        
        /* ========== CREATION OF USERS ========== */
        User user1 = new User();
        user1.setName("Dupont");
        user1.setFirstname("Jean");
        user1.setEmail("jean.dupont@example.com");
        user1.setUserType(adminType);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Martin");
        user2.setFirstname("Sophie");
        user2.setEmail("sophie.martin@example.com");
        user2.setUserType(commercialType);
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("Bernard");
        user3.setFirstname("Pierre");
        user3.setEmail("pierre.bernard@example.com");
        user3.setUserType(commercialType);
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("Lefebvre");
        user4.setFirstname("Marie");
        user4.setEmail("marie.lefebvre@example.com");
        user4.setUserType(managerType);
        userRepository.save(user4);

        User user5 = new User();
        user5.setName("Moreau");
        user5.setFirstname("Luc");
        user5.setEmail("luc.moreau@example.com");
        user5.setUserType(adminType);
        userRepository.save(user5);

        System.out.println("✅ Created 5 Users");
        System.out.println("🎉 Database initialization complete!");
    }
}
