package school21.spring.service.repositories;


import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import school21.spring.service.models.User;

import org.springframework.jdbc.core.JdbcTemplate;


public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
    
    private final JdbcTemplate jdbcTemplate;
    
    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public <T> T findById(long id) {
        List<T> users = jdbcTemplate.query("select * from Users where id = ?;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));}, id);
        if (users.size() == 0) return null;
        else return (T) users.get(0);
    }

    @Override
    public <T> List<T> findAll() {
        return jdbcTemplate.query("select * from Users;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));});
    }

    @Override
    public <T> void save(T entity){
        User obj = (User) entity;
        jdbcTemplate.update("insert into Users values(?, ?);", obj.getId(), obj.getEmail());
    }
    
    @Override
    public <T> void update(T entity){
        User obj = (User) entity;
        jdbcTemplate.update("update Users set email = ? where id = ?;", obj.getEmail(), obj.getId());
    }

    @Override
    public void delete(Long id){
        this.jdbcTemplate.update("delete from Users where id = ?", Long.valueOf(id));
    }
    
    @Override
    public <T> Optional<T> findByEmail(String email){
        List<T> users = jdbcTemplate.query("select * from Users where email = ?;", (rs, rowNum) -> {return (T) new User(rs.getLong("id"), rs.getString("email"));}, email);
        if (users.size() == 0) return Optional.empty();
        else return Optional.of((T) users.get(0));
    }
}