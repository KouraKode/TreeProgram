import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;

public class Tree {
    public static int level = 0;
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String path = "/home/kourakodeur/Desktop/DIC1/semestre2/Design_Patters/";
    
        String xmlString = pathToXml(path);
        Component document = xmlToDoc(xmlString);
        System.out.println("******************DEBUT CHAINE******************");
        System.out.println(xmlString);
        System.out.println("******************FIN CHAINE******************");

        document.afficher();
	}
    /**
     * 
     * @param path example "/home/kourakodeur/Documents/"
     * @return String contenant le document XML
     */
	public static String pathToXml(String path) {
        File file = new File(path);
        String xmlString = "";
        xmlString = "<directory name='"+file.getName()+"'>\n";
        for(File theFile : file.listFiles()){
            for (int i = 0; i <= Tree.level; i++) {
                xmlString = xmlString + "    ";
            }
            if(theFile.isDirectory()){
                Tree.level++;
                xmlString = xmlString + pathToXml(theFile.getPath());
                Tree.level--;
            }else{
                xmlString = xmlString + "<file name='"+theFile.getName()+"' />\n";
            }
        }
        for (int i = 0; i < Tree.level; i++) {
            xmlString = xmlString + "    ";
        }
        xmlString = xmlString + "</directory>\n";
        return xmlString;
	}

    
    /**
     * 
     * @param xmlString String containing xml
     * @return Repertoire racine
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
	public static Component xmlToDoc(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        //Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        //Create a Docuemnt from a stream
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(xmlString);
        ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        // System.out.println(xmlStringBuilder.toString().getBytes("UTF-8"));

        Document doc = builder.parse(input);

        doc.getDocumentElement().normalize();

        //Extract the root element

        Element root = doc.getDocumentElement();

		return addChildren(root); 
	}

    /**
     * 
     * @param element
     * @return the root of Components, it's a recursive fonction.
     */
    public static Component addChildren(Element element){


        Component dossier = new Dossier(element.getAttribute("name"));

        NodeList children = element.getChildNodes();
        for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            if(child.getNodeType() == Node.ELEMENT_NODE){
                Element iElement = (Element)child;
                if(child.getNodeName().equals("directory")){
                    dossier.ajouter(addChildren(iElement)); //if the current element is a directory, we add his children
                }
                else{
                    dossier.ajouter(new Fichier(iElement.getAttribute("name")));
                }

            }
        }
        return dossier;
    }
}
