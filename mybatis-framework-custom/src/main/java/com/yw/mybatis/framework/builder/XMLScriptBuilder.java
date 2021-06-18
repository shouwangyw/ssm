package com.yw.mybatis.framework.builder;

import com.yw.mybatis.framework.sqlnode.*;
import com.yw.mybatis.framework.sqlsource.DynamicSqlSource;
import com.yw.mybatis.framework.sqlsource.RawSqlSource;
import com.yw.mybatis.framework.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class XMLScriptBuilder {
    private boolean isDynamic = false;

    public SqlSource parse(Element element) {
        // 解析所有SQL节点，最终封装到MixedSqlNode中
        SqlNode mixedSqlNode = parseDynamicTags(element);

        SqlSource sqlSource;
        //如果带有${}或者动态SQL标签
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(mixedSqlNode);
        } else {
            sqlSource = new RawSqlSource(mixedSqlNode);
        }
        return sqlSource;
    }

    private SqlNode parseDynamicTags(Element element) {
        List<SqlNode> sqlNodes = new ArrayList<>();

        // 获取select标签的子元素 ：文本类型或者Element类型
        int nodeCount = element.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = element.node(i);
            if (node instanceof Text) {
                String text = node.getText();
                if (text == null || "".equals(text.trim())) {
                    continue;
                }
                // 先将sql文本封装到TextSqlNode中
                TextSqlNode textSqlNode = new TextSqlNode(text.trim());
                if (textSqlNode.isDynamic()) {
                    sqlNodes.add(textSqlNode);
                    isDynamic = true;
                } else {
                    sqlNodes.add(new StaticTextSqlNode(text.trim()));
                }
            } else if (node instanceof Element) {
                isDynamic = true;
                Element ele = (Element) node;
                String name = ele.getName();
                if ("if".equals(name)) {
                    String test = ele.attributeValue("test");
                    // 递归去解析子元素
                    SqlNode sqlNode = parseDynamicTags(ele);
                    sqlNodes.add(new IfSqlNode(test, sqlNode));
                } else {
                    // TODO
                }
            } else {
                // TODO
            }
        }

        return new MixedSqlNode(sqlNodes);
    }
}