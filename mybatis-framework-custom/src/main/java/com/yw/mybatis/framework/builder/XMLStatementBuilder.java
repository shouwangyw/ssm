package com.yw.mybatis.framework.builder;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;
import com.yw.mybatis.framework.sqlsource.SqlSource;
import com.yw.mybatis.framework.util.ReflectUtils;
import org.dom4j.Element;

/**
 * @author yangwei
 */
public class XMLStatementBuilder {
    private Configuration configuration;

    public XMLStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(String namespace, Element element) {
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        String statementId = namespace + "." + element.attributeValue("id");

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = ReflectUtils.resolveType(resultType);

        String statementType = element.attributeValue("statementType");
        statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;

        // SqlSource和SqlNode的封装过程
        SqlSource sqlSource = createSqlSource(element);

        // 建议使用构建者模式去优化
        MappedStatement mappedStatement = new MappedStatement(statementId, statementType, resultType, resultTypeClass, sqlSource);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element element) {
        XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
        return scriptBuilder.parse(element);
    }

}