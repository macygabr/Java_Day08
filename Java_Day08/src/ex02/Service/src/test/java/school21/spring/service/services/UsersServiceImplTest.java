package school21.spring.service.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
public class UsersServiceImplTest {

    private UsersServiceImpl usersService;

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = (UsersServiceImpl)applicationContext.getBean("usersService");
    }
    
    @Test
    public void testFailedSingUp(){
        assertNull(usersService.signUp("email_test"));
    }

    @Test
    public void testSucceeded(){
        assertNotNull(usersService.signUp("email_2"));
    }
}