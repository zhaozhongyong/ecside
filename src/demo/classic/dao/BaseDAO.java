package demo.classic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.common.log.LogHandler;
import org.ecside.easydataaccess.ConnectionUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class BaseDAO {
	private DataSource dataSource;

	protected static int MAX_BATCH = 10000;

	private Log logger = LogFactory.getLog(BaseDAO.class);

	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	protected final void closeConnection(Connection conn) {
		ConnectionUtils.closeAllStatement(conn);
		DataSourceUtils.releaseConnection(conn, getDataSource());
	}

	public void close(ResultSet rest, Statement pstmt, Connection conn) {
		try {
			close(rest);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(pstmt, conn);
	}

	public void close(Statement pstmt, Connection conn) {
		try {
			close(pstmt);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(conn);
	}

	public void close(ResultSet rest) throws SQLException {
		if (rest != null)
			rest.close();
	}

	public void close(Statement pstmt) throws SQLException {
		if (pstmt != null)
			pstmt.close();
	}

	public void close(Connection conn) {
		if (conn != null)
			closeConnection(conn);
	}

	protected final Connection getConnection() {
		Connection conn;
		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			ConnectionUtils.initStatementMap(conn);
		} catch (Exception e) {
//			LogHandler.errorLog(logger, e);
			return getConnectionDirect();
		}
		return conn;
	}

	public Connection getConnectionDirect(){
		try {
				String DRIVER_NAME="org.h2.Driver";
				String USER_NAME="sa";
				String PASSWORD="";
				String CONNECTION_URL="jdbc:h2:tcp://localhost/testdb";
				Class.forName(DRIVER_NAME);
				return DriverManager.getConnection(CONNECTION_URL,USER_NAME, PASSWORD);
				
			} catch (Exception e) {
//				LogHandler.errorLog(logger, e);
				return null;
			}

	}

	// ////////////////////////////////////////////////

	// ////////////////////////////////////////////////

	public static String[] getColumnName(ResultSet resultSet)
			throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		String[] columnName = new String[cols];
		for (int i = 0; i < columnName.length; i++) {
			columnName[i] = metaData.getColumnName(i + 1).toUpperCase();
		}
		return columnName;
	}

	public static int[] getColumnType(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		int[] columnType = new int[cols];
		for (int i = 0; i < columnType.length; i++) {
			columnType[i] = metaData.getColumnType(i + 1);
		}
		return columnType;
	}

	public static void buildRecord(ResultSet resultSet, String[] columnName,
			Map map) throws SQLException {
		for (int i = 0; i < columnName.length; i++) {
			map.put(columnName[i], resultSet.getString(columnName[i]));
		}
	}

}
