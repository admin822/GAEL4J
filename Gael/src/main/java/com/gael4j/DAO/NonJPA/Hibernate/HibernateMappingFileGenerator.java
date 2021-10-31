package com.gael4j.DAO.NonJPA.Hibernate;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.gael4j.Entity.DBConfig;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HibernateMappingFileGenerator {
	public static void generateMappers(List<DBConfig> dbConfigList, String filePath) {
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			DocumentBuilderFactory documentFactory= DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder=documentFactory.newDocumentBuilder();
			Document document=documentBuilder.newDocument();
			Element root = document.createElement("hibernate-mapping");
            document.appendChild(root);
			for(DBConfig dbConfig:dbConfigList) {
				List<String> allFields=dbConfig.getfieldList(); 
				List<String> allColumns=dbConfig.getColumns();
				/* creating class label in mapping file*/
				Element classLabel=document.createElement("class");
				root.appendChild(classLabel);
				Attr typeName=document.createAttribute("name");
				typeName.setValue(dbConfig.getClassName());
				Attr tableName=document.createAttribute("table");
				tableName.setValue(dbConfig.getTableName());
				classLabel.setAttributeNode(typeName);
				classLabel.setAttributeNode(tableName);
				/* creating class label in mapping file*/
				
				/* creating id label in mapping file*/
				Element idLabel=document.createElement("id");
				classLabel.appendChild(idLabel);
				Attr idFieldName=document.createAttribute("name");
				idFieldName.setValue(allFields.get(0));    // !!!! BAD CODE, NEED TO CHANGE AFTER CHANGE DBCONFIG CLASS
				Attr idColumnName=document.createAttribute("column");
				idColumnName.setValue(allColumns.get(0));     // !!!! BAD CODE, NEED TO CHANGE AFTER CHANGE DBCONFIG CLASS
				idLabel.setAttributeNode(idFieldName);
				idLabel.setAttributeNode(idColumnName);
				/* creating id label in mapping file*/
				
				/* creating property label in mapping file*/
				for(int i=1;i<allColumns.size();i++) {
					Element propertyLabel=document.createElement("property");
					classLabel.appendChild(propertyLabel);
					Attr propertyFieldName=document.createAttribute("name");
					propertyFieldName.setValue(allFields.get(i));    // !!!! BAD CODE, NEED TO CHANGE AFTER CHANGE DBCONFIG CLASS
					Attr propertyColumnName=document.createAttribute("column");
					propertyColumnName.setValue(allColumns.get(i));     // !!!! BAD CODE, NEED TO CHANGE AFTER CHANGE DBCONFIG CLASS
					propertyLabel.setAttributeNode(propertyFieldName);
					propertyLabel.setAttributeNode(propertyColumnName);
				}
				/* creating property label in mapping file*/
			}
			
			/* transform dom object to xml file*/
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			DOMSource domSource=new DOMSource(document);
			StreamResult streamsResult=new StreamResult(new File(filePath));
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
			transformer.transform(domSource, streamsResult);
			System.out.println("mapper configs successfully created!");
			/* transform dom object to xml file*/
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
