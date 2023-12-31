/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaXMLLearningAPI;


import static com.javaappweather.WeatherApp.fetchXml;
import static com.javaappweather.WeatherApp.formatXmlData;
import static com.javaappweather.WeatherApp.parseXml;
import org.w3c.dom.Document;

/**
 *
 * @author Rauld
 */
public class JavaAppWeather {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

//            String xmlData = xmlParser.fetchXml("https://www.aemet.es/xml/municipios/localidad_19054.xml");
//            Document xmlDocument = xmlParser.parseXml(xmlData);
//            String xmlString = xmlParser.convertDocumentToString(xmlDocument);
//            System.out.println("XML String: " + xmlString);


            String xmlData = fetchXml("https://www.aemet.es/xml/municipios/localidad_19054.xml");
            Document xmlDocument = parseXml(xmlData);
            String formattedData = formatXmlData(xmlDocument);
            System.out.println(formattedData);
            
            xmlParser.crearVistaSistema();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
