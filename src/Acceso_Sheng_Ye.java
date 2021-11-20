import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.metadata.IIOMetadataNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * PACKAGE_NAME
 * Nombre_project: Examen_AccesoDeDatos
 * Acceso_Sheng_Ye
 * Created by: sheng
 * Date : 18/11/2021
 * Description:
 **/
public class Acceso_Sheng_Ye {

    public static Document CargarXML(String nombredoc) throws SAXException, IOException, ParserConfigurationException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(nombredoc));
        return doc;
    }
    public void crearxml() throws IOException {

        FileWriter fileWriter = null;
        PrintWriter printWriter = null;

        fileWriter = new FileWriter("src/xml/Alumnes.xml");
        printWriter = new PrintWriter(fileWriter);
        printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        printWriter.println("<registre_alumnes>");
        printWriter.println("</registre_alumnes>");
        printWriter.close();
    }

    //Creamos los elementos para luego ser llamado en el metodo principal
    public Document CrearElemento(Document document, String codi_alumne, String nom_alumne, String curs,
                                  String any_naixement, String colegi) {
        //Creamos los elementos de los campos que queremos
        Element nodevideojuego = document.createElement("alumnes");
        nodevideojuego.setAttribute("codi_alumne", codi_alumne);


        Node nodetitulo = document.createElement("nom_alumne");
        Node nodetitulotext = document.createTextNode(nom_alumne);
        nodetitulo.appendChild(nodetitulotext);

        Node nodecreador = document.createElement("curs");
        Node nodecreadortext = document.createTextNode(curs);
        nodecreador.appendChild(nodecreadortext);

        Node nodesinopsis = document.createElement("any_naixement");
        Node nodesinoptext = document.createTextNode(any_naixement);
        nodesinopsis.appendChild(nodesinoptext);

        Node nodeplataforma = document.createElement("colegi");
        Node nodeplattext = document.createTextNode(colegi);
        nodeplataforma.appendChild(nodeplattext);

        //Y lo guardaremos
        nodevideojuego.appendChild(nodetitulo);
        nodevideojuego.appendChild(nodecreador);
        nodevideojuego.appendChild(nodesinopsis);
        nodevideojuego.appendChild(nodeplataforma);

        //Y hacemos que se coja del ultimo lista
        document.getLastChild().appendChild(nodevideojuego);

        return document;
    }

    public void crea(){

        try {
            File xml = new File("src/xml/Alumnes.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xml);

            Scanner scanner = new Scanner(System.in);
            System.out.println("codi alumne : ");
            String texto = scanner.nextLine();

            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Nombre alumno: ");
            String texto1 = scanner1.nextLine();

            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Curs: ");
            String texto2 = scanner2.nextLine();

            Scanner scanner3 = new Scanner(System.in);
            System.out.println("any: ");
            String texto3 = scanner3.nextLine();

            Scanner scanner4 = new Scanner(System.in);
            System.out.println("colegi: ");
            String texto4 = scanner4.nextLine();


            Document document1 =CrearElemento(document , texto , texto1 , texto2  ,texto3 , texto4);

            document.getDocumentElement().normalize();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Result output = new StreamResult(new File("src/xml/Alumnes.xml"));
            Source input = new DOMSource(document1);
            transformer.transform(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarDatos() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        String xPathExpression = "/registre_alumnes//*";
        // Carga del documento xml
        // Creamos una nueva instancia de DocuementBuilderFactory para construir el documento
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Guardamos en la variable "documento" el contenido del fichero xml parseado.
        Document documento = builder.parse(new File("src/xml/Alumnes.xml"));
        // Preparación de xpath. Creamos una nueva instancia
        XPath xpath = XPathFactory.newInstance().newXPath();

        // Consultas: recorremos los nodos del fichero con XPath
        NodeList nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);
        for (int i = 0; i < nodos.getLength(); i++) {
            // y mostramos un mensaje cada vez que encuentra un nodo, con el nombre del nodo (es decir, la etiqueta)
            // y el/los atributo/s que tiene dicha etiqueta. En este caso busca el atributo "nombre"

            if (nodos.item(i).getNodeName().equals("alumnes")){
                System.out.println(nodos.item(i).getNodeName() + " : " +
                        nodos.item(i).getAttributes().getNamedItem("codi_alumne"));
            }else if (nodos.item(i).getNodeName().equals("nom_alumne")){
                System.out.println(nodos.item(i).getNodeName() + " : " +
                        nodos.item(i).getTextContent());
            }else if (nodos.item(i).getNodeName().equals("curs")){
                System.out.println(nodos.item(i).getNodeName() + " : " +
                        nodos.item(i).getTextContent());
            }else if (nodos.item(i).getNodeName().equals("any_naixement")){
                System.out.println(nodos.item(i).getNodeName() + " : " +
                        nodos.item(i).getTextContent());
            }else if (nodos.item(i).getNodeName().equals("colegi")){
                System.out.println(nodos.item(i).getNodeName() + " : " +
                        nodos.item(i).getTextContent());

                System.out.println("**********************************************");
            }

        }
    }



    //Pasam per parametre el nom del joc que volem modificar y el nou nom que volem ficar per modificar el joc.
    public static Document ModificarNomJoc(Document doc, String codi, String valor_campo, String contenido) throws SAXException, IOException, ParserConfigurationException {
        //Recream els nodes amb node list i cercam els elements titol
        NodeList nodes = doc.getElementsByTagName("alumnes");
        //Pasam per tots els elements titol amb el for
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Element element = (Element) node;
            String titulo = element.getAttribute("codi_alumne");
            if (titulo.equals(codi)) { //Si el text que hi ha al titol es el nom que hem cercat entrara a l'if i ho canviara per el nou titol que li donem.
                int pos = 0;
                NodeList ndl = doc.getElementsByTagName(valor_campo);
                for(int is=0;is<ndl.getLength();is++)
                {
                    Node nodesa = ndl.item(is);
                    if(nodesa.getFirstChild().getNodeValue().equals(contenido))
                        pos = is;
                }
                 element.getElementsByTagName(valor_campo).item(pos).getFirstChild().setNodeValue(contenido);

                break; //Si entra a l'if no fa falta que cerqui mes per aixó aquest break
            }

        }
        return doc;

    }


    //Pasam per parametre el nom del joc que volem eliminar.
    public static Document EliminarJoc(Document doc, String codi) throws SAXException, IOException, ParserConfigurationException {
        NodeList nodes = doc.getElementsByTagName("alumnes");
        //Tornam a fer un bucle per cercar el joc per titol.
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Element element = (Element) node;
            //Feim una nova variable per trobar el nom del joc que esteim cercant com solament hi ha un joc posam .item(0) ja que solament podrá estar a la possicio 0.
            String titulo = element.getAttribute("codi_alumne");
            if (titulo.equals(codi)) { //Si el titol es igual al que hem pasat per parametre simplement l'eliminarà.
                element.getParentNode().removeChild(element);
            }
        }
        return doc;

    }

    public static void GuardarArchivo(Document doc, String documento) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transf = tf.newTransformer();
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(new File(documento));
        transf.transform(source, file);
    }

    public void consultasXpath(String xPathExpression) throws FileNotFoundException, XPathExpressionException {
        InputSource inputSource = new InputSource(new FileInputStream("src/xml/Alumnes.xml"));
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        XPathExpression expression = xPath.compile(xPathExpression);
        NodeList list = (NodeList) expression.evaluate(inputSource, XPathConstants.NODESET);
        for (int i = 0; i < list.getLength(); i++) {
            System.out.println(list.item(i).getTextContent());
        }
    }


    public void menu() throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, TransformerException {
        boolean bandera = true;
        while (bandera) {
            System.out.println("********************************************************");
            System.out.println("Elige opcion: ");
            System.out.println("1.-Crear el fichero XML");
            System.out.println("2.-Introducir datos en el XML");
            System.out.println("3.-Mostrar el contenido del fichero XML");
            System.out.println("4.-Modificar datos");
            System.out.println("5.-Consultas");
            System.out.println("6.-Eliminar un registro");
            System.out.println("salir");
            System.out.println("*********************************************************");

            Scanner scanner = new Scanner(System.in);

            String opcion = scanner.nextLine();

            switch (opcion){
                case"1":
                    crearxml();
                    break;
                case"2":
                    crea();
                    break;
                case"3":
                    mostrarDatos();
                    break;
                case"4":
                    Document doc = CargarXML("src/xml/Alumnes.xml");
                    System.out.println("Elige el ID que quieres: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String textosa = scanner2.nextLine();
                    System.out.println("Elige el valor que eliminar: (nom_alumne, curs , any_naixement , colegi)");
                    Scanner scanner3 = new Scanner(System.in);
                    String textosas = scanner3.nextLine();
                    System.out.println("Que quieres poner?");
                    Scanner scanner4 = new Scanner(System.in);
                    String textosasa = scanner4.nextLine();

                    ModificarNomJoc(doc , textosa,textosas,textosasa);
                    GuardarArchivo(doc,"src/xml/Alumnes.xml");
                    break;
                case"5":
                    menu2();
                    break;
                case"6":
                    Document docs = CargarXML("src/xml/Alumnes.xml");
                    System.out.println("Cual ID quisieras eliminar: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String texto = scanner1.nextLine();
                    EliminarJoc(docs , texto);
                    GuardarArchivo(docs,"src/xml/Alumnes.xml");
                    break;
                case"salir":
                    Document docas = CargarXML("src/xml/Alumnes.xml");
                    GuardarArchivo(docas,"src/xml/Alumnes.xml");
                    bandera = false;
                    break;
            }
        }
    }


    public void menu2() throws FileNotFoundException, XPathExpressionException {
        boolean bandera = true;
        while (bandera) {
            System.out.println("********************************************************");
            System.out.println("Elige opcion: ");
            System.out.println("1.-Consultar todos los nombre de los alumnos");
            System.out.println("2.-Consultar los alumnos que vayan al colegio cide");
            System.out.println("3.-Consultar el nombre  del alumno con codigo 3");
            System.out.println("4.-Consultar los alumnos nacidos antes de 1990");
            System.out.println("salir");
            System.out.println("*********************************************************");


            Scanner scanner = new Scanner(System.in);

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    consultasXpath("//nom_alumne");
                    break;
                case "2":
                    consultasXpath("//alumnes[colegi=\"cide\"]//nom_alumne");
                    break;
                case "3":
                    consultasXpath("//alumnes[@codi_alumne='3']//nom_alumne");
                    break;
                case "4":
                    consultasXpath("//alumnes[any_naixement<=1990]//nom_alumne");
                    break;
                case "salir":
                    bandera = false;
                    break;
            }
        }
    }


    public static void main(String[] args) throws IOException, ParserConfigurationException, XPathExpressionException, SAXException, TransformerException {
        Acceso_Sheng_Ye acceso_sheng_ye = new Acceso_Sheng_Ye();
        acceso_sheng_ye.menu();
    }





}
