/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaXMLLearningAPI;

/**
 *
 * @author Rauld
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;
import org.jfree.chart.plot.PlotOrientation;

public class xmlParser {

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

    public static String formatXmlData(Document xmlDocument) {
        StringBuilder formattedData = new StringBuilder();
        NodeList nodeList = xmlDocument.getElementsByTagName("tuEtiquetaDeseada");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String datoEspecifico = element.getElementsByTagName("subEtiqueta").item(0).getTextContent();
                formattedData.append("Dato: ").append(datoEspecifico).append("\n");
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

    public static String catchTemperatures(Document xmlDocument) {
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

    public static void crearVistaSistema() {
        




      
              DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Añadir datos
        dataset.addValue(13, "Máxima", "2023-12-27");
        dataset.addValue(-3, "Mínima", "2023-12-27");
        dataset.addValue(11, "Máxima", "2023-12-28");
        dataset.addValue(-3, "Mínima", "2023-12-28");
        dataset.addValue(13, "Máxima", "2023-12-29");
        dataset.addValue(-3, "Mínima", "2023-12-29");
        dataset.addValue(30, "Máxima", "2023-12-30");
        dataset.addValue(10, "Mínima", "2023-12-30");
        dataset.addValue(13, "Máxima", "2023-12-1");
        dataset.addValue(5, "Mínima", "2023-12-1");
        dataset.addValue(-1, "Máxima", "2023-12-2");
        dataset.addValue(-3, "Mínima", "2023-12-2");
        // ... Añade el resto de los datos aquí

        // Crear el gráfico de líneas
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Temperaturas Máxima y Mínima",
                "Fecha",
                "Temperatura",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Mostrar el gráfico en un JFrame
        ChartPanel chartPanel = new ChartPanel(lineChart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.setTitle("Gráfico de Temperaturas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
      
    }
}
