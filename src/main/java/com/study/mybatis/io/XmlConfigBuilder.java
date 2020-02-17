package com.study.mybatis.io;

import com.study.mybatis.configurate.Configurate;
import com.study.mybatis.configurate.Environment;
import com.study.mybatis.connection.DataSource;
import com.study.mybatis.connection.UnPoolDataSource;
import com.study.mybatis.statement.MapperHandler;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;

/**
 * config配置文件的处理
 */
@Data
public class XmlConfigBuilder {
    private Configurate configurate;
    private XPathParser xPathParser;

    public XmlConfigBuilder(InputStream is) {
        configurate = new Configurate();
        xPathParser = new XPathParser(is);
    }

    /**
     * 读取config文件的配置内容
     * @return
     */
    public Configurate parse() {
        Document document = xPathParser.getDocument();
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                switch (nodeName) {
                    case "environments":
                        setEnvironment(node);
                        break;
                    case "mappers":
                        setMapper(node);
                        break;
                }
            }
        }
        return configurate;
    }

    /**
     * 处理mappers标签的内容
     * @param node
     */
    private void setMapper(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element temp = (Element) child;
                String resource = temp.getAttribute("resource");
                if (resource != null && !configurate.checkResource(resource)) {
                    addMapper(resource);
                }
            }
        }
    }

    /**
     * 读取mapper文件，并且生成mapperhandler对象，并根据id以及对象进行映射形成map放入configurate对象
     * @param resouce
     */
    private void addMapper(String resouce) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(resouce));
            Document document = xPathParser.createDocument(is);
            String namespace = document.getDocumentElement().getAttribute("namespace");
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            Class clazz = null;
            try {
                clazz = Class.forName(namespace);
            } catch (ClassNotFoundException e) {
                return;
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node temp = nodeList.item(i);
                if (temp.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) temp;
                    String sqlType = element.getNodeName();
                    String id = element.getAttribute("id");
                    String resultType = element.getAttribute("resultType");
                    String sql = element.getTextContent();
                    MapperHandler mapperHandler = new MapperHandler();
                    mapperHandler.setId(namespace + "." + id);
                    mapperHandler.setResultType(resultType);
                    mapperHandler.setSqlType(sqlType);
                    mapperHandler.setSql(sql);
                    mapperHandler.setInterfaceName(clazz);
                    this.configurate.addMapperHandler(mapperHandler);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * environments节点的处理，主要是数据源信息的处理
     * @param node
     */
    private void setEnvironment(Node node) {
        Element element = (Element) node;
        String defaultName = element.getAttribute("default");
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node environmentName = nodeList.item(i);
            if (environmentName.getNodeType() == Node.ELEMENT_NODE) {
                if (((Element) environmentName).getAttribute("id").equalsIgnoreCase(defaultName)) {
                    NodeList nodeList1 = environmentName.getChildNodes();
                    Environment environment = new Environment();
                    for (int j = 0; j < nodeList1.getLength(); j++) {
                        Node node1 = nodeList1.item(j);
                        if (node1.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = node1.getNodeName();
                            switch (nodeName) {
                                case "transactionManager":

                                    break;
                                case "dataSource":
                                    Element element1 = (Element) node1;
                                    if ((element1.getAttribute("type").equalsIgnoreCase("UNPOOLED"))) {
                                        DataSource dataSource = new UnPoolDataSource();
                                        NodeList nodeList2 = element1.getElementsByTagName("property");
                                        for (int k = 0; k < nodeList2.getLength(); k++) {
                                            String name = ((Element) nodeList2.item(k)).getAttribute("name");
                                            String value = ((Element) nodeList2.item(k)).getAttribute("value");
                                            if (name.equalsIgnoreCase("driver")) {
                                                ((UnPoolDataSource) dataSource).setDriver(value);
                                            } else if (name.equalsIgnoreCase("url")) {
                                                ((UnPoolDataSource) dataSource).setUrl(value);
                                            } else if (name.equalsIgnoreCase("username")) {
                                                ((UnPoolDataSource) dataSource).setUsername(value);
                                            } else if (name.equalsIgnoreCase("password")) {
                                                ((UnPoolDataSource) dataSource).setPassword(value);
                                            }
                                        }
                                        environment.setDataSource(dataSource);
                                    }
                                    break;
                            }
                            this.configurate.setEnvironment(environment);
                        }
                    }
                }
            }
        }
    }

}
