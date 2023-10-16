package com.dbms.project.preparedStatementSetters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class InsertIdsPreparedStatementSetter implements BatchPreparedStatementSetter{
    private List<Integer> Ids;
    
    public InsertIdsPreparedStatementSetter(List<Integer> Ids) {
      super();
      this.Ids = Ids;
    }
    @Override
    public void setValues(PreparedStatement ps, int i) {
      
      try {
        int id = Ids.get(i);
        ps.setInt(1,id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    @Override
    public int getBatchSize() {
      return Ids.size();
    }
  }