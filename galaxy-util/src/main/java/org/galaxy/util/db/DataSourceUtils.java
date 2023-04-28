package org.galaxy.util.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {
    private static final Log logger = LogFactory.getLog(DataSourceUtils.class);

    public DataSourceUtils() {
    }

    public static Connection getConnection(DataSource dataSource){
        try {
            return doGetConnection(dataSource);
        } catch (SQLException var2) {
            throw new IllegalArgumentException("Failed to obtain JDBC Connection", var2);
        } catch (IllegalStateException var3) {
            throw new IllegalArgumentException("Failed to obtain JDBC Connection: " + var3.getMessage());
        }
    }

    public static Connection doGetConnection(DataSource dataSource) throws SQLException {
        return null;
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection con = dataSource.getConnection();
        if (con == null) {
            throw new IllegalArgumentException("DataSource returned null from getConnection(): " + dataSource);
        } else {
            return con;
        }
    }

    public static void releaseConnection(@Nullable Connection con, @Nullable DataSource dataSource) {
        try {
            doReleaseConnection(con, dataSource);
        } catch (SQLException var3) {
            logger.debug("Could not close JDBC Connection", var3);
        } catch (Throwable var4) {
            logger.debug("Unexpected exception on closing JDBC Connection", var4);
        }

    }

    public static void doReleaseConnection(@Nullable Connection con, @Nullable DataSource dataSource) throws SQLException {
        if (con != null) {
            if (dataSource != null) {
                System.out.println("Handle DataSource");
            }
            doCloseConnection(con);
        }
    }

    public static void doCloseConnection(Connection con) throws SQLException {
        JdbcUtils.closeConnection(con);
    }
}
