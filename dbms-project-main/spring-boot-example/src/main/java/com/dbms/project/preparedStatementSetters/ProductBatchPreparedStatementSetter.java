package com.dbms.project.preparedStatementSetters;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
    int supplierOrderId;
    int productTypeId;
    int quantity;

    public ProductBatchPreparedStatementSetter(int supplierOrderId, int productTypeId, int quantity) {
        this.supplierOrderId = supplierOrderId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) {

        try {
            ps.setInt(1,supplierOrderId);
            ps.setInt(2,productTypeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getBatchSize() {
        return quantity;
    }
}