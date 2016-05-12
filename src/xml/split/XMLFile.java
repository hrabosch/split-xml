/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.split;

import java.io.File;
import java.io.FileWriter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author hrabosch
 */
public class XMLFile {

    private Document doc;

    private DefaultTreeModel treeModel;

    private String rootValue;
    private String itemValue;

    public XMLFile(Document doc) {
        this.doc = doc;
        setXmlFileObject();
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
        this.doc.normalize();
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public String getRootValue() {
        return rootValue;
    }

    public void setRootValue(String rootValue) {
        this.rootValue = rootValue;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    private void setXmlFileObject() {
        setTreeModel(new DefaultTreeModel(generateStructure(doc.getDocumentElement(), new DefaultMutableTreeNode("XML Structure"))));
    }

    private DefaultMutableTreeNode generateStructure(Node doc, DefaultMutableTreeNode root) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(doc.getNodeName());

        NodeList nodeList = doc.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                root.add(generateStructure(currentNode, treeNode));
            }
        }
        return root;
    }

    public boolean validateIt() {
        return doc != null && itemValue != null && rootValue != null;
    }

    public int splitFile(String outPutPath, int itemsCount) throws XPathExpressionException, ParserConfigurationException, Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList nodes = (NodeList) xpath.evaluate("//" + rootValue + "//" + itemValue + "", doc, XPathConstants.NODESET);

        int fileNumber = 0;

        Document currentDoc = dbf.newDocumentBuilder().newDocument();
        Node rootNode = currentDoc.createElement(rootValue);
        File currentFile = new File(outPutPath + "/" + fileNumber + ".xml");
        for (int i = 1; i <= nodes.getLength(); i++) {
            Node imported = currentDoc.importNode(nodes.item(i - 1), true);
            rootNode.appendChild(imported);

            if (i % itemsCount == 0) {
                writeToFile(rootNode, currentFile);

                rootNode = currentDoc.createElement(rootValue);
                currentFile = new File(outPutPath + "/" + (++fileNumber) + ".xml");
            }
        }

        writeToFile(rootNode, currentFile);
        return fileNumber;
    }

    private void writeToFile(Node node, File file) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        FileWriter fileWriter = new FileWriter(file);
        StreamResult result = new StreamResult(fileWriter);
        transformer.transform(new DOMSource(node), result);
    }

}
