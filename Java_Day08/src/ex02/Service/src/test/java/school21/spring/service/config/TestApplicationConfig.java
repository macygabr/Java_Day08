
package school21.spring.service.config;

import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersServiceImpl;

import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.context.annotation.Bean;
import java.sql.Statement;
import java.sql.Connection;
import javax.sql.DataSource;



public class TestApplicationConfig {
    @Bean({"HikariDataSource", "SpringDataSource"})
    public DataSource getDataSource() {
        JDBCDataSource ds = new JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testdb");
        ds.setUser("SA");
        ds.setPassword("");

        try (Connection connection = ds.getConnection();
        Statement statement = connection.createStatement()) {

        statement.execute("CREATE TABLE IF NOT EXISTS Users (id INT, email VARCHAR(255))");
            for(int i = 0; i < 10; i++) 
                statement.execute("INSERT INTO Users VALUES (" + i + ", 'email_" + i + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

   @Bean("usersRepositoryJdbc")
    public UsersRepositoryJdbcImpl getUsersRepositoryJdbcImpl(){
        return new UsersRepositoryJdbcImpl();
    }

    @Bean("usersRepositoryJdbcTemplate")
    public UsersRepositoryJdbcTemplateImpl getUsersRepositoryJdbcTemplateImpl(){
        return new UsersRepositoryJdbcTemplateImpl();
    }

    @Bean(name = "usersService")
    public UsersServiceImpl getUsersServiceImpl(){
        return new UsersServiceImpl();
    }
}