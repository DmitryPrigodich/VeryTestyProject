import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlCreator {
    private static Logger logger = LogManager.getLogger(XmlCreator.class);

    public static final String XML_PATH_FROM = "src/test/resources/filesToChange/simple.xml";
    public static final String XML_PATH_TO = "src/test/resources/filesToChange/simple2.xml";

    private DocumentBuilderFactory theDocFactory;
    private DocumentBuilder theDocumentBuilder;
    private Document theDocument;


    public XmlCreator() throws ParserConfigurationException, SAXException, IOException {
        //Setup
        this.theDocFactory = DocumentBuilderFactory.newInstance();
        this.theDocumentBuilder = this.theDocFactory.newDocumentBuilder();
        this.theDocument = this.theDocumentBuilder.parse(XML_PATH_FROM);
    }

    public void updateAttribute(String aTagName, String aTagValue){
        //Update
        Node theNode = this.theDocument.getElementsByTagName(aTagName).item(0);
        theNode.setTextContent(aTagValue);
    }

    public void addAttribute(String aTagName, String aTagValue){
        //Create
        Element theElement = this.theDocument.createElement(aTagName);
        theElement.appendChild(this.theDocument.createTextNode(aTagValue));
        this.theDocument.getElementsByTagName("company").item(0).appendChild(theElement);
    }

    public boolean saveTo(String aPathTo) {
        try{
            //Finishing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.theDocument);
            StreamResult result = new StreamResult(new File(aPathTo));
            transformer.transform(source, result);
        }catch (TransformerException tr){
            return false;
        }
        return true;
    }

    public boolean save(){
        return saveTo(XML_PATH_TO);
    }

    public void generateNew(){
        logger.info("Updating...");
        updateAttribute("nickname", "Tester");

        logger.info("Adding...");
        addAttribute("leche", "lait");
        addAttribute("gato", "chat");
        addAttribute("chico", "garcon");

        logger.info("Saving...");
        save();
    }
}
