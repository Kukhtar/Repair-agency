package ua.kukhtar.model.dao;

import javax.sql.DataSource;

/**
 * Template for classes that generate DataSource object
 */
public interface DataSourceBuilder {
    DataSource getDataSource();
}
