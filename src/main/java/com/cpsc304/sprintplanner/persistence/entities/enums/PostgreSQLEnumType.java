package com.cpsc304.sprintplanner.persistence.entities.enums;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

// Taken from https://stackoverflow.com/questions/27804069/hibernate-mapping-between-postgresql-enum-and-java-enum/27807765
// in order to use ENUM types with postgres and JPA
public class PostgreSQLEnumType extends org.hibernate.type.EnumType {
    @Override
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull( index, Types.OTHER );
        }
        else {
            st.setObject(
                    index,
                    value.toString(),
                    Types.OTHER
            );
        }
    }
}