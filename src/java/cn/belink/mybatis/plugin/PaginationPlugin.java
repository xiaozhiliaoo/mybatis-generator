package cn.belink.mybatis.plugin;

import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;


public class PaginationPlugin extends PluginAdapter {

    public PaginationPlugin() {
        super();
    }

    private static final String baseDir = "cn.feezu.wzc.vehicle.service.entity";

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("AbstractExample"));
        /**
         *
         */
///        topLevelClass.addImportedType(new FullyQualifiedJavaType(baseDir + ".entity.AbstractExample"));
//        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.data.domain.Pageable"));
        return super.modelExampleClassGenerated(topLevelClass,
                introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        ///XmlElement isParameterPresenteElemen = (XmlElement) element
        //.getElements().get(element.getElements().size() - 1);
        //$NON-NLS-1$
        XmlElement isNotNullElement = new XmlElement("if");
        //$NON-NLS-1$ //$NON-NLS-2$
        isNotNullElement.addAttribute(new Attribute("test", "limitStart != null and limitStart>=0"));
        /// $NON-NLS-1$ //$NON-NLS-2$
        //isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        isNotNullElement.addElement(new TextElement("limit #{limitStart} , #{limitEnd}"));
        //isParameterPresenteElemen.addElement(isNotNullElement);  
        element.addElement(isNotNullElement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
                introspectedTable);
    }

    /**
     * 扩展mapper
     */
    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> type = interfaze.getImportedTypes();
        String[] sp = new String[2];
        for (FullyQualifiedJavaType f : type) {
            if (f.getFullyQualifiedName().startsWith(baseDir + ".entity")) {
                if (f.getShortName().endsWith("Example")) {
                    sp[1] = f.getShortName();
                } else {
                    sp[0] = f.getShortName();
                }
            }
        }

        ///interfaze.addSuperInterface(new FullyQualifiedJavaType("BaseMapper<"+sp[0]+","+sp[1]+">"));
        //interfaze.addImportedType(new FullyQualifiedJavaType("com.beaulog.mybatis.mapper.BaseMapper"));
        // add base mapper
        interfaze.addSuperInterface(new FullyQualifiedJavaType("SqlMapper"));
        interfaze.addImportedType(new FullyQualifiedJavaType("com.chainup.common.dao.SqlMapper"));
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}  