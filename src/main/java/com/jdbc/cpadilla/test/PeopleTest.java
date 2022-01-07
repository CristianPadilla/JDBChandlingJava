package com.jdbc.cpadilla.test;

import com.jdbc.cpadilla.data.*;
import com.jdbc.cpadilla.domain.PersonDTO;

import java.sql.*;
import java.util.List;

public class PeopleTest {
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonMySQL();

//        insert
//        PersonDTO person = new PersonDTO("carlotas", "vargotas", "cvargas@mail", "33224455");
//        personMySQL.insert(person);
//
//        update
//        PersonDTO person = new PersonDTO(3,"carlotas", "vargotas", "cvargas@mail", "33224455");
//        personMySQL.update(person);
//
//        delete
//        PersonDTO person = new PersonDTO(3);
//        personMySQL.delete(person);


//        transaction test
        Connection conn = null;
        try {
            conn = MyConnection.getConnection();
            if (conn.getAutoCommit()) conn.setAutoCommit(false);

//            var person2 = new PersonDTO(1, "cris", "padilla", "cris@mail", "3344");
//            var personDAO2 = new PersonMySQL(conn);
//            personDAO2.update(person2);
//            personDAO2.insert(new PersonDTO("juan", "envigado", "pe@mail.com", "334455"));
//            conn.commit();

            PersonDAO personDAOtransaction = new PersonMySQL();

            List<PersonDTO> people = personDAOtransaction.select();
            for (PersonDTO p : people) {
                System.out.println(p);
            }

        } catch (SQLException e) {
            System.out.println("entramos en roll back");
            e.printStackTrace(System.out);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

//       read
//        List<PersonDTO> people = null;
//        try {
//            people = personDAO.select();
//        } catch (SQLException e) {
//            e.printStackTrace(System.out);
//        }
//        for (PersonDTO p : people) {
//            System.out.println(p);
//        }
    }
}
