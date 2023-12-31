/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javaappweather;

/**
 *
 * @author Rauld
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WeatherApp {

    public static Document parseXml(String xmlData) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlData)));
    }

    public static String fetchXml(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader rd = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (rd != null) {
                rd.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result.toString();
    }

//    public static String formatXmlData(Document xmlDocument) {
//        StringBuilder formattedData = new StringBuilder();
//        NodeList nodeList = xmlDocument.getElementsByTagName("tuEtiquetaDeseada");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element) node;
//                String datoEspecifico = element.getElementsByTagName("subEtiqueta").item(0).getTextContent();
//                formattedData.append("Dato: ").append(datoEspecifico).append("\n");
//            }
//        }
//        return formattedData.toString();
//    }
    public static String formatXmlData(Document xmlDocument) {
        StringBuilder formattedData = new StringBuilder();
        NodeList nodeList = xmlDocument.getElementsByTagName("dia");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String fecha = element.getAttribute("fecha");
                formattedData.append("Fecha: ").append(fecha).append("\n");

                Element temperatura = (Element) element.getElementsByTagName("temperatura").item(0);
                String maxima = temperatura.getElementsByTagName("maxima").item(0).getTextContent();
                String minima = temperatura.getElementsByTagName("minima").item(0).getTextContent();
                formattedData.append("Temperatura Máxima: ").append(maxima).append("\n");
                formattedData.append("Temperatura Mínima: ").append(minima).append("\n\n");
            }
        }
        return formattedData.toString();
    }

    public static String convertDocumentToString(Document doc) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {

            String xmlData = fetchXml("https://www.aemet.es/xml/municipios/localidad_19054.xml");
            Document xmlDocument = parseXml(xmlData);

            String formattedData = formatXmlData(xmlDocument);
            System.out.println(formattedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
