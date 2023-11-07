package fr.univrouen.portfavor.config.database;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.entity.Role;
import fr.univrouen.portfavor.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * The database initializer, initialize some default data into the database.
 */
@Configuration
@Order(0) // Ensure that this DB initializer is executed before the development one
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initRoles();

        logger.info("The database has been initialized.");
    }

    private void initRoles() {
        if (this.roleRepository.findAll().isEmpty()) {
            RoleID.ALL.forEach(role -> roleRepository.save(new Role(role)));
        }
    }

}
