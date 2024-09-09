import jakarta.servlet.http.Cookie;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.SessionDAO;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.InvalidPasswordException;
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
    private UserDTORequest userDTORequest = new UserDTORequest("Robert", "qwerty");

    @BeforeEach
    public void createSession() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:Weather");
        properties.setProperty("hibernate.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        Configuration configuration = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Session.class)
                .addAnnotatedClass(User.class);
        DBConfigurationUtil.initSessionFactory(configuration);
    }

    @Test
    public void register_userWithUniqueLogin_shouldRegisteredSuccessful() {
        authenticationService.register(userDTORequest);

        User registeredUser = userDAO.findByLogin("Robert").get();
        assertAll(
                () -> assertEquals(1, registeredUser.getId()),
                () -> assertEquals("Robert", registeredUser.getLogin()),
                () -> assertTrue(BCrypt.checkpw(userDTORequest.getPassword(), registeredUser.getPassword())));
    }


    @Test
    public void register_userWithNotUniqueLogin_shouldThrowUserAlreadyExistsException() {
        authenticationService.register(userDTORequest);

        assertThrowsExactly(UserAlreadyExistsException.class,
                () -> authenticationService.register(userDTORequest));
    }

    @Test
    public void login_unregisteredUser_shouldThrowUserNotFoundException() {
        assertThrowsExactly(UserNotFoundException.class,
                () -> authenticationService.login(userDTORequest));
    }

    @Test
    public void login_registeredUserWithInvalidPassword_shouldThrowInvalidPasswordException() {
        authenticationService.register(userDTORequest);
        userDTORequest.setPassword("1234");

        assertThrowsExactly(InvalidPasswordException.class,
                () -> authenticationService.login(userDTORequest));
    }

    @Test
    public void login_registeredUserWithCorrectPassword_shouldLoginSuccessfulWithCreatedSession() {
        authenticationService.register(userDTORequest);
        Cookie cookie = authenticationService.login(userDTORequest);
        UUID uuid = UUID.fromString(cookie.getValue());

        assertEquals(sessionDAO.findById(uuid).get().getId(), uuid);

    }
}
