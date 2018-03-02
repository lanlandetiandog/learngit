package com.jkgroup.kingkaid.utils.cls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import com.jkgroup.kingkaid.bo.formdata.FieldType;

/**
 * 
 * @author pan
 * @CreateDate 2015-03-26
 */
public class EntityClassWriter extends ClassLoader{
	
	public static final String CLASS_PATH = "com/jkgroup/kingkaid/bo/formdata/";//类限定名
	
	//public static final String ENTITY_DIR = new File(EntityClassWriter.class.getResource("/").getPath()) + "/" + CLASS_PATH;//类生成的路经
	//public static final String ENTITY_DIR = EntityClassWriter.class.getClassLoader().getResource("/").toURI().getPath()+CLASS_PATH;//类生成的路经
	
	/**
	 * 生成报文对应的Class对象
	 * @param className
	 */
	public static void build(String className,List<String> fields,List<FieldType> types){
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		createClass(cw, className);
		addField(cw ,className,fields,types);
		cw.visitEnd();
		storeClass(cw, className);
	}
	
	/**
	 * 定义class的基类、父类、包名
	 * @param cw
	 * @param fileDir
	 * @param className
	 */
	public static void createClass(ClassWriter cw , String className){
		cw.visit(Opcodes.V1_6 , Opcodes.ACC_PUBLIC,CLASS_PATH + className,null,CLASS_PATH+"FormData",null);
		MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, CLASS_PATH+"FormData", "<init>", "()V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}
	
	/**
	 * 为class添加属性 并且生成get、set方法
	 * @param cw
	 * @param className
	 */
	public static void addField(ClassWriter cw , String className,List<String> fields,List<FieldType> types){
		MethodVisitor mv = null;
		for(int i = 0 ;i < fields.size() ; i++){
			String field = fields.get(i);
			FieldType type = types.get(i);
			//get、set首字母大写 
			String firstUpperField = StringUtils.upperCase(field.substring(0,1))+field.substring(1,field.length());
			if(type.equals(FieldType.String)){			//字符串
				cw.visitField(Opcodes.ACC_PRIVATE, field,"Ljava/lang/String;", null, null).visitEnd();				
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "get"+firstUpperField, "()Ljava/lang/String;", null, null);
			}else if(type.equals(FieldType.Range)){		//区间
				cw.visitField(Opcodes.ACC_PRIVATE, field,"L"+CLASS_PATH+"Range;", null, null).visitEnd();
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "get"+firstUpperField, "()L"+CLASS_PATH+"Range;", null, null);
			} else if(type.equals(FieldType.Date)) {	//日期
				cw.visitField(Opcodes.ACC_PRIVATE, field,"Ljava/util/Date;", null, null).visitEnd();
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "get"+firstUpperField, "()Ljava/util/Date;", null, null);
			} else if(type.equals(FieldType.Array)) {	//数组
				cw.visitField(Opcodes.ACC_PRIVATE, field,"L"+CLASS_PATH+"Array;", null, null).visitEnd();
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "get"+firstUpperField, "()L"+CLASS_PATH+"Array;", null, null);
			}
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			if(type.equals(FieldType.String)){
				mv.visitFieldInsn(Opcodes.GETFIELD, CLASS_PATH+className, field, "Ljava/lang/String;");
			}else if(type.equals(FieldType.Range)){
				mv.visitFieldInsn(Opcodes.GETFIELD, CLASS_PATH+className, field, "L"+CLASS_PATH+"Range;");
			}else if(type.equals(FieldType.Date)) {
				mv.visitFieldInsn(Opcodes.GETFIELD, CLASS_PATH+className, field, "Ljava/util/Date;");
			}else if(type.equals(FieldType.Array)) {
				mv.visitFieldInsn(Opcodes.GETFIELD, CLASS_PATH+className, field, "L"+CLASS_PATH+"Array;");
			}
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
			//set方法
			if(type.equals(FieldType.String)){
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "set"+firstUpperField, "(Ljava/lang/String;)V", null, null);
			}else if(type.equals(FieldType.Range)){
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "set"+firstUpperField, "(L"+CLASS_PATH+"Range;)V", null, null);
			}else if(type.equals(FieldType.Date)) {
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "set"+firstUpperField, "(Ljava/util/Date;)V", null, null);
			}else if(type.equals(FieldType.Array)) {
				mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "set"+firstUpperField, "(L"+CLASS_PATH+"Array;)V", null, null);
			}
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			
			if(type.equals(FieldType.String)){
				mv.visitFieldInsn(Opcodes.PUTFIELD, CLASS_PATH+className, field, "Ljava/lang/String;");
			}else if(type.equals(FieldType.Range)){
				mv.visitFieldInsn(Opcodes.PUTFIELD, CLASS_PATH+className, field, "L"+CLASS_PATH+"Range;");
			}else if(type.equals(FieldType.Date)) {
				mv.visitFieldInsn(Opcodes.PUTFIELD, CLASS_PATH+className, field, "Ljava/util/Date;");
			}else if(type.equals(FieldType.Array)) {
				mv.visitFieldInsn(Opcodes.PUTFIELD, CLASS_PATH+className, field, "L"+CLASS_PATH+"Array;");
			}
			mv.visitMaxs(2, 2);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitEnd();
		}
	}
	
	/**
	 * 输出class到文件
	 * @param cw
	 * @param fileDir
	 * @param className
	 */
	public static void storeClass(ClassWriter cw , String className){
		byte[] code = cw.toByteArray();
	    FileOutputStream fos;
		try {
			String filepath = EntityClassWriter.class.getClassLoader().getResource("/").toURI().getPath() + CLASS_PATH;
			String fullname = filepath + className + ".class";
			File file = new File(fullname);
			fos = FileUtils.openOutputStream(file);
			fos.write(code);  
		    fos.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
