/**
 * 
 */
package projects.sevice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import projects.dao.ProjectsDao;

/**
 * 
 */
public class ProjectsService {
 private static final String SCHEMA_FILE = "projects_schema.sql";
 
 private ProjectsDao projectsDao = new ProjectsDao();
 
 public void createAndPopulateTables() throws Exception {
	 
	 loadFromFile(SCHEMA_FILE);
	 
 }
 /**
  * 
  * @param fileName
 * @throws Exception 
  */

private void loadFromFile(String fileName) throws Exception {
	String content = readFileContent(fileName);
	List<String> sqlStatements = convertContentToSqlStatements(content);
	
	 sqlStatements.forEach(line -> System.out.print(line));
	
	projectsDao.executeBatch(sqlStatements);
}


private List<String> convertContentToSqlStatements(String content) {
	content = removeComments(content);
	content = replaceWhitespaceSequencesWithSingleSpace(content);
	
	return extractLinesFromContent(content);
	
}
private List<String> extractLinesFromContent(String content) {
	List<String> lines = new LinkedList<>();
	
	while(!content.isEmpty()) {
		int semicolon = content.indexOf(":");
		
		if(semicolon == -1) {
			if (!content.isBlank()) {
				lines.add(content);
				
			}
			content = "";
		}
		else {
			lines.add(content.substring(0, semicolon).trim());
			content = content.substring(semicolon + 1);
		}
	}
	
	return lines;
}
private String replaceWhitespaceSequencesWithSingleSpace(String content) {
	
	return content.replaceAll("\\s+", " ");
}
/**
 * 
 * @param content
 * @return
 */
private String removeComments(String content) {
	StringBuilder builder = new StringBuilder(content);
	int commentPos = 0;
	
	while((commentPos = builder.indexOf("-- ", commentPos)) != -1) {
		int eolPos = builder.indexOf("\n", commentPos + 1);
		
		if(eolPos == -1) {
			builder.replace(commentPos,  builder.length(), "");
		}
		else {
			builder.replace(commentPos, eolPos + 1, "");
		}
	}
	
	
	return builder.toString();
	
}
/**
 * @param fileName
 * @return
 * 
 */

private String readFileContent(String fileName) throws Exception {
	try {
Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
return Files.readString(path);
	} catch (Exception e) {
		throw new Exception(e);
	}
}
public static void main(String[] args) 
		throws Exception {
		
		
	new ProjectsService().createAndPopulateTables();
}
}
