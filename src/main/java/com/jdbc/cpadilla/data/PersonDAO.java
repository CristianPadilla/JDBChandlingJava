package com.jdbc.cpadilla.data;

import com.jdbc.cpadilla.domain.PersonDTO;

import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {

    List<PersonDTO> select() throws SQLException;

    int insert(PersonDTO personDTO) throws SQLException;

    int update(PersonDTO personDTO) throws SQLException;

    int delete(PersonDTO personDTO) throws SQLException;
}
