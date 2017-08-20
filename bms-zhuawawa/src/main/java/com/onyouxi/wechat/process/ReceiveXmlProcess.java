package com.onyouxi.wechat.process;

import com.onyouxi.wechat.entity.ReceiveXmlEntity;
import com.onyouxi.wechat.pojo.UnifiedorderResponse;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * 解析接收到的微信xml，返回消息对象
 *
 */
@Slf4j
public class ReceiveXmlProcess {

	/**
	 * 解析微信xml消息
	 * @param request
	 * @return
	 */
//	public ReceiveXmlEntity getMsgEntity(String strXml){
	public ReceiveXmlEntity getMsgEntity(HttpServletRequest request){

		ReceiveXmlEntity msg = null;
		try {
            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            log.info(inputStream.toString());

            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
//			if (strXml.length() <= 0 || strXml == null)
//				return null;
			 
			// 将字符串转化为XML文档对象
//			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			// 遍历所有结点
			msg = new ReceiveXmlEntity();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("com.onyouxi.wechat.entity.ReceiveXmlEntity");
			msg = (ReceiveXmlEntity)c.newInstance();//创建这个实体的对象
			
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				//获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            log.error(e.getMessage());
		}
		return msg;
	}

	public UnifiedorderResponse getUnifiedorderResponse(HttpServletRequest request){

		UnifiedorderResponse msg = null;
		try {
			// 从request中取得输入流
			InputStream inputStream = request.getInputStream();
			log.info(inputStream.toString());

			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
//			if (strXml.length() <= 0 || strXml == null)
//				return null;

			// 将字符串转化为XML文档对象
//			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();

			// 遍历所有结点
			msg = new UnifiedorderResponse();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("com.onyouxi.wechat.entity.UnifiedorderResponse");
			msg = (UnifiedorderResponse)c.newInstance();//创建这个实体的对象

			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				//获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return msg;
	}
}
