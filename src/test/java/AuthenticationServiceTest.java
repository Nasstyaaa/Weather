import jakarta.servlet.http.Cookie;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.SessionDAO;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.IncorrectLoginInformationException;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.Session;
import org.nastya.model.User;
import org.nastya.service.AuthenticationService;
import org.nastya.util.DBConfigurationUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final UserDAO userDAO = new UserDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    @BeforeAll
    public static void createSession() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:Weather");
        properties.setProperty("hibernate.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        Configuration configuration = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Session.class)
                .addAnnotatedClass(User.class);
        DBConfigurationUtil.initSessionFactory(configuration);
    }

    @Test
    public void register_userWithUniqueLogin_shouldRegisteredSuccessful() {
        authenticationService.register(new UserDTORequest("Robert", "qwerty"));

        User registeredUser = userDAO.findByLogin("Robert").get();
        assertAll(
                () -> assertEquals(1, registeredUser.getId()),
                () -> assertEquals("Robert", registeredUser.getLogin()),
                () -> assertTrue(BCrypt.checkpw("qwerty", registeredUser.getPassword())));
    }


    @Test
    public void register_userWithNotUniqueLogin_shouldThrowUserAlreadyExistsException() {
        UserDTORequest userDTORequest = new UserDTORequest("Bob", "1234");
        authenticationService.register(userDTORequest);

        assertThrows(UserAlreadyExistsException.class,
                () -> authenticationService.register(userDTORequest));
    }

    @Test
    public void login_unregisteredUser_shouldThrowIncorrectLoginInformationException() {
        assertThrows(IncorrectLoginInformationException.class,
                () -> authenticationService.login(new UserDTORequest("Rober", "qwerty")));
    }


    @Test
    public void login_registeredUserWithCorrectPassword_shouldLoginSuccessfulWithCreatedSession() {
        UserDTORequest userDTORequest = new UserDTORequest("Mani", "qwertus");
        authenticationService.register(userDTORequest);
        Session session = authenticationService.login(userDTORequest);
        UUID uuid = UUID.fromString(session.getId().toString());

        assertEquals(sessionDAO.findById(uuid).get().getId(), uuid);

    }
}
