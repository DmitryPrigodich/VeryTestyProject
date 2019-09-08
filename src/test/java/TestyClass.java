import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class TestyClass {
    private static Logger logger = LogManager.getLogger(TestyClass.class);



    @Test
    public void testSimpleTest(){
        logger.info("Hello there, sweety!");
    }

    @Test
    public void modifyXmlTest(){

        try{
            //Setup
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(XmlCreator.XML_PATH_FROM);

            //Update
            Node lname = doc.getElementsByTagName("lastname").item(0);
            lname.setTextContent("IT WORKS");

            //Create
            Element age = doc.createElement("age");
            age.appendChild(doc.createTextNode("28"));
            doc.getElementsByTagName("lastname").item(0).appendChild(age);

            //Finishing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XmlCreator.XML_PATH_TO));
            transformer.transform(source, result);

        } catch(Exception ignored) {}
    }

    @Test
    public void checkPipelineTest(){
        try{
            XmlCreator xmlka = new XmlCreator();
            xmlka.generateNew();

        } catch (Exception ignored){}

    }
}