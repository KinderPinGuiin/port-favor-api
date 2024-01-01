package fr.univrouen.portfavor.config.database;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.entity.Role;
import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.repository.RoleRepository;
import fr.univrouen.portfavor.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

/**
 * The database initializer, initialize some default data into the database.
 */
@Configuration
@Order(0) // Ensure that this DB initializer is executed before the development one
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initRoles();
        this.initAdmin();

        logger.info("The database has been initialized.");
    }

    private void initRoles() {
        if (this.roleRepository.findAll().isEmpty()) {
            RoleID.ALL.forEach(role -> roleRepository.save(new Role(role)));
        }
    }

    private void initAdmin() {
        // Default admin user
        this.userRepository.save(new User(
            0L,
            "admin@gmail.com",
            this.passwordEncoder.encode("admin"),
            "36b780db-cdfc-40b6-b8b2-2f5699b5be44",
            new HashSet<>(List.of(
                this.roleRepository.findById(RoleID.USER).get(),
                this.roleRepository.findById(RoleID.PRIVATE_USER).get(),
                this.roleRepository.findById(RoleID.ADMIN).get()
            ))
        ));
    }

}
