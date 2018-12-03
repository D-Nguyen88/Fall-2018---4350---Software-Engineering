import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class XMLGenerator implements Serializable {

    public XMLGenerator() {

    }

    public String resultSetToXML(ResultSet resultSet, String fileName) {
        String filePath = "xml-files\\"+fileName;
        File xmlFile = new File(filePath);

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            ArrayList<String> columnNames = new ArrayList<>();
            for(int i=0; i<rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i));
                System.out.println(rsmd.getColumnName(i));
            }
            String userIdCol=columnNames.get(columnNames.indexOf("userId"));
            columnNames.remove("userId");


            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("userdata");
            doc.appendChild(rootElement);

            while(resultSet.next()) {
                // profile element
                Element profile = doc.createElement("profile");
                rootElement.appendChild(profile);

                // setting userId attribute to profile element
                Attr userId = doc.createAttribute(userIdCol);
                userId.setValue(resultSet.getString(userIdCol));
                profile.setAttributeNode(userId);

                for(String columnName : columnNames) {
                    // datafield element
                    Element dataField = doc.createElement(columnName);
                    dataField.appendChild(doc.createTextNode(resultSet.getString(columnName)));
                    profile.appendChild(dataField);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }

}