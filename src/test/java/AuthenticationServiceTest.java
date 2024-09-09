import org.apache.maven.model.InputLocationTracker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.listener.DataListener;
import org.nastya.model.User;
import org.nastya.service.AuthenticationService;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {
    private static SessionFactory sessionFactory;
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final UserDAO userDAO = new UserDAO();

        @BeforeAll
        public static void createSession() throws IOException {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:Weather");
            properties.setProperty("hibernate.driver_class","org.h2.Driver");
            properties.setProperty("hibernate.hbm2ddl.auto","create-drop");

            Configuration configuration = new Configuration()
                    .addAnnotatedClass(Session.class)
                    .setProperties(properties)
                    .addAnnotatedClass(User.class);
            DataListener.configure(configuration);

            sessionFactory = configuration.buildSessionFactory();
        }

    @Test
    public void register_userWithUniqueLogin_shouldRegisteredSuccessful(){
        UserDTORequest userDTORequest = new UserDTORequest("Robert", "qwerty");

        authenticationService.register(userDTORequest);

        User registeredUser = userDAO.findByLogin("Robert").get();
        assertAll(
                () -> assertEquals(1, registeredUser.getId()),
                () -> assertEquals("Robert", registeredUser.getLogin()),
                () -> assertTrue(BCrypt.checkpw(userDTORequest.getPassword(), registeredUser.getPassword())));
    }

}
