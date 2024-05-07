package school21.spring.service.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import school21.spring.service.models.User;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository{
    
    @Autowired
    @Qualifier("HikariDataSource")
    private final DataSource dataSource;
    
    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UsersRepositoryJdbcImpl() {
        this.dataSource = null;
    }

    @Override
    public <T> T findById(long id) {
        try (Connection connection = dataSource.getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE id = ?;");
             preparedStatement.setLong(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (!resultSet.next()) return null;
             return (T) (new User(resultSet.getLong("id"),resultSet.getString("email")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findAll() {
        List res = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Users");
            for(;resultSet.next();) res.add(new User(resultSet.getLong(1), resultSet.getString(2)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public <T> void save(T obj){
        User entity = (User)obj;
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users VALUES(?, ?);");
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void update(T obj) {
        User entity = (User)obj;
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET email = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id){
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public <T> Optional<T> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ?;");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (!resultSet.next()) return Optional.empty();
            return Optional.of((T) new User(resultSet.getLong("id"),resultSet.getString("email")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}