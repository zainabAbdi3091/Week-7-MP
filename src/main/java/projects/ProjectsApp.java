/**
 * 
 */
package projects;

import java.sql.Connection;
import projects.dao.DbConnection;

/**
 * @author Promineo
 */


@SuppressWarnings("unused")
public class ProjectsApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 DbConnection.getConnection();
	}

}
