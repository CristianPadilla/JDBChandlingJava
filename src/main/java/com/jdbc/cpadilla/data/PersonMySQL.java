package com.jdbc.cpadilla.data;

import com.jdbc.cpadilla.domain.PersonDTO;

import java.sql.*;
import java.util.*;

import static com.jdbc.cpadilla.data.MyConnection.*;

public class PersonMySQL implements PersonDAO{

    private Connection transactionalConnection;
    private static final String SQL_SELECT = "SELECT person_id,name, last_name, email, phone_number FROM person";
    private static final String SQL_INSERT = "INSERT INTO person (name, last_name, email, phone_number) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE person SET name=?, last_name=?, email=?, phone_number=? WHERE person_id=?";
    private static final String SQL_DELETE = "DELETE FROM person WHERE person_id=?";

    public PersonMySQL() {
    }

    public PersonMySQL(Connection transactionalConnection) {
        this.transactionalConnection = transactionalConnection;
    }


    public List<PersonDTO> select() throws SQLException {
        List<PersonDTO> people = new ArrayList<>();
        try {
            //if method was provided by transactional conn, don't open a new one
            var conn = this.transactionalConnection != null ? this.transactionalConnection : getConnection();
            var stmt = conn.prepareStatement(SQL_SELECT);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("person_id");
                var name = rs.getString("name");
                var lastName = rs.getString("last_name");
                var email = rs.getString("email");
                var phoneNumber = rs.getString("phone_number");
                PersonDTO personDTO = new PersonDTO(id, name, lastName, email, phoneNumber);
                people.add(personDTO);
            }
            close(rs);
            close(stmt);
            //close local conn if a transactional conn wasn't provided
            if (this.transactionalConnection == null) close(conn);
        } catch (SQLException e) {
            throw e;
        }
        return people;
    }

    public int insert(PersonDTO personDTO) throws SQLException {
        var affectedRows = 0;
        try {
            var conn = this.transactionalConnection != null ? this.transactionalConnection : getConnection();
            var stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, personDTO.getName());
            stmt.setString(2, personDTO.getLastName());
            stmt.setString(3, personDTO.getEmail());
            stmt.setString(4, personDTO.getPhoneNumber());
            affectedRows = stmt.executeUpdate();
            close(stmt);
            if (this.transactionalConnection == null) close(conn);
        } catch (SQLException e) {
            throw e;
        }
        return affectedRows;
    }

    public int update(PersonDTO personDTO) throws SQLException {
        var affectedRows = 0;
        try {
            var conn = this.transactionalConnection != null ? this.transactionalConnection : getConnection();
            var stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, personDTO.getName());
            stmt.setString(2, personDTO.getLastName());
            stmt.setString(3, personDTO.getEmail());
            stmt.setString(4, personDTO.getPhoneNumber());
            stmt.setInt(5, personDTO.getId());
            affectedRows = stmt.executeUpdate();
            close(stmt);
            if (this.transactionalConnection == null) close(conn);
        } catch (SQLException e) {
            throw e;
        }

        return affectedRows;
    }

    public int delete(PersonDTO personDTO) throws SQLException {
        var affectedRows = 0;
        try {
            var conn = this.transactionalConnection != null ? this.transactionalConnection : getConnection();
            var stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, personDTO.getId());
            affectedRows = stmt.executeUpdate();
            close(stmt);
            if (this.transactionalConnection == null) close(conn);
        } catch (SQLException e) {
            throw e;
        }
        return affectedRows;
    }

}
