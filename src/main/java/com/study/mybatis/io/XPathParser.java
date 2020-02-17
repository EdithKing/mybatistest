package com.study.mybatis.io;

import lombok.Data;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

/**
 * 读取xml文档生成document对象
 */
@Data
public class XPathParser {
    private Document document;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder builder;

    public XPathParser(InputStream is) {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = createDocument(is);
    }

    public Document createDocument(InputStream is) {
        try {
            document = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }


}
