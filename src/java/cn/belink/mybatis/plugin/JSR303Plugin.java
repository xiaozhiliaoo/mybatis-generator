package cn.belink.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class JSR303Plugin extends PluginAdapter {


    public JSR303Plugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    protected void makeSerializable(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {

    	List<IntrospectedColumn> columns = introspectedTable.getBaseColumns();
    	//添加长度的验证
    	for(IntrospectedColumn column:columns){
    		if(column.isStringColumn()){
    			for(Field f:topLevelClass.getFields()){
    				if(f.getName().equals(column.getJavaProperty())){
    					f.addAnnotation("@Length(max="+column.getLength()+")");
    					topLevelClass.addImportedType("org.hibernate.validator.constraints.Length");
    				}
    			}
    		}
    		//添加非空验证
    		if(!column.isNullable()){
    			for(Field f:topLevelClass.getFields()){
    				if(f.getName().equals(column.getJavaProperty())){
    					f.addAnnotation("@NotEmpty");
    					topLevelClass.addImportedType("org.hibernate.validator.constraints.NotEmpty");
    				}
    			}
    		}
    	}
    }

}
