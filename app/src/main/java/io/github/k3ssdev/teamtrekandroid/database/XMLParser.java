package io.github.k3ssdev.teamtrekandroid.database;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class XMLParser {
    // Método para convertir una cadena XML en un objeto Document
    public static Document convertStringToXMLDocument(String xmlString) {
        try {
            // Crea una fábrica de documentos XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crea un constructor de documentos
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Crea una fuente de entrada a partir de la cadena XML
            InputSource inputSource = new InputSource(new StringReader(xmlString));

            // Parsea la cadena XML y la convierte en un objeto Document
            return builder.parse(inputSource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
