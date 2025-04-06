/**
 * 
 */
package projects.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import projects.exception.DbException;
import provided.util.DaoBase;

/**
 * 
 */
public class ProjectsDao extends DaoBase {
   public void executeBatch(List<String> sqlBatch) {
	   try(Connection conn = DbConnection.getConnection()) {
		   startTransaction(conn);
		   
		   try(Statement stmt = conn.createStatement()) {
			  for(String sql : sqlBatch) { 
				 stmt.addBatch(sql); 
			  }
		   stmt.executeBatch();
		   commitTransaction(conn);
		   }
		   
		   catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		   
	   }
   } catch (SQLException e) {
	throw new DbException(e);
}
}
}