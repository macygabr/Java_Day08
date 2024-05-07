package school21.spring.service.repositories;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import school21.spring.service.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
    
    @Autowired
    @Qualifier("SpringDataSource")
    private final DataSource dataSource;
    
    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UsersRepositoryJdbcTemplateImpl() {
        this.dataSource = null;
    }

    @Override
    public <T> T findById(long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<T> users = jdbcTemplate.query("SELECT * FROM Users WHERE id = ?;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));}, id);
        if (users.size() == 0) return null;
        else return (T) users.get(0);
    }

    @Override
    public <T> List<T> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("SELECT * FROM Users;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));});
    }

    @Override
    public <T> void save(T entity){
        User obj = (User) entity;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO Users VALUES(?, ?);", obj.getId(), obj.getEmail());
    }
    
    @Override
    public <T> void update(T entity){
        User obj = (User) entity;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE Users SET email = ? WHERE id = ?;", obj.getEmail(), obj.getId());
    }

    @Override
    public void delete(Long id){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("DELETE FROM Users WHERE id = ?", Long.valueOf(id));
    }
    
    @Override
    public <T> Optional<T> findByEmail(String email){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<T> users = jdbcTemplate.query("SELECT * FROM Users WHERE email = ?;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));}, email);
        if (users.size() == 0) return Optional.empty();
        else return Optional.of((T) users.get(0));
    }
}