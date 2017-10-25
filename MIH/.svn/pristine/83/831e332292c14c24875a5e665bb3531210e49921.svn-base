package com.sophos.semih.common;

import java.io.File;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sophos.semih.bean.BaseBean;
import com.sophos.semih.model.MenuItem;
import com.sophos.semih.util.Security;

@ManagedBean
@SessionScoped
public class MenuBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private Security security;
	private static final Log log = LogFactory.getLog(MenuBean.class);
	
	public MenuBean(){
		security = new Security();
	}
	
	/**
	 * Obtiene los elementos del menu.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MenuItem> getMenu() {
		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		menu=(ArrayList<MenuItem>)security.getAttributeFromSession("userMenu");
		if(security.getAttributeFromSession("userMenu")==null){
			menu = new ArrayList<MenuItem>();
			try {
	
				File file = new File(this.getClass().getResource("/menu.xml").toURI()); 
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(file);
	
				if (doc.hasChildNodes()) {
					
					//Nodo menu.
					Node menuNode =doc.getFirstChild(); 
					
					//Itera sobre los items del menu.
					if (menuNode.hasChildNodes()) {
						menu = leerItems(menuNode.getChildNodes(), menu);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			security.setAttributeInSession("userMenu", menu);
		}
		return menu;
	}

	public ArrayList<MenuItem> leerItems(NodeList nodeList, ArrayList<MenuItem> menu) {
		try {

			//Items
			for (int count = 0; count < nodeList.getLength(); count++) {
				Node tempNode = nodeList.item(count);
				MenuItem menuItem = new MenuItem();

				// Verifica que sea un element node (nodo valido).
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

					// Atributos de nodo
					if (tempNode.hasAttributes()) {
						NamedNodeMap nodeMap = tempNode.getAttributes();
						boolean isHeader=true;
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							if (node.getNodeName().equals("titulo")) {
								menuItem.setTitulo(node.getNodeValue());
							}
							if (node.getNodeName().equals("url")) {
								isHeader=false;
								menuItem.setUrl(node.getNodeValue());
							}
							if (node.getNodeName().equals("metodo")) {
								menuItem.setMetodo(node.getNodeValue());
							}
						}
						if(!isHeader && security.checkMenuPerms(menuItem) ){
							menu.add(menuItem);
						}
					}
					
					// Hijos del nodo
					if (tempNode.hasChildNodes()) {
						menuItem.setHijos(leerItems(tempNode.getChildNodes(), new ArrayList<MenuItem>()));
						if(!menuItem.getHijos().isEmpty()){
							menu.add(menuItem);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al leer los items del menu", e);
		}
		return menu;
	}

}
