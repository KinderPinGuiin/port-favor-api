package fr.univrouen.portfavor.config.database;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.entity.Image;
import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.repository.ImageRepository;
import fr.univrouen.portfavor.repository.RoleRepository;
import fr.univrouen.portfavor.repository.UserRepository;
import fr.univrouen.portfavor.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * The development database initializer, it initializes some useful data for testing.
 */
@Configuration
@Profile(value = { "dev" })
@Order(1)
public class DevelopmentDatabaseInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResourceService resourceService;

    @Value("${portfavor.dev-images.folder}")
    private String devResourcesFolder;

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentDatabaseInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initUsers();

        logger.info("The development data has been initialized.");
    }

    /**
     * Initialize the dev users.
     */
    private void initUsers() {
        // Admin user
        this.userRepository.save(new User(
            0L,
            "admin@gmail.com",
            this.passwordEncoder.encode("admin"),
            "36b780db-cdfc-40b6-b8b2-2f5699b5be44",
            new HashSet<>(List.of(
                this.roleRepository.findById(RoleID.USER).get(),
                this.roleRepository.findById(RoleID.ADMIN).get()
            ))
        ));

        // Classic user
        this.userRepository.save(new User(
            0L,
            "user@gmail.com",
            this.passwordEncoder.encode("user"),
            "36b780db-cdfc-40b6-b8b2-2f5699b5be45",
            new HashSet<>(List.of(this.roleRepository.findById(RoleID.USER).get()))
        ));
    }

}
