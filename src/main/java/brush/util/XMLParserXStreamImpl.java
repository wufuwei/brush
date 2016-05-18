package brush.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import brush.entity.DateStrategy;
import brush.entity.SendStrategy;
import brush.entity.Strategy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

 

public class XMLParserXStreamImpl  {
	private static Logger log = Logger.getLogger(XMLParserXStreamImpl.class);
	private final static String encode="UTF-8";
	public final static String XML_HEAD="<?xml version=\"1.0\" encoding=\""+encode+"\" ?>\n";
	public static Object formXML(String xmlString,Object object) {
		log.debug("execute Object formXML(String xmlString="+xmlString+",Object object="+object+") ");
		XStream xstream = new XStream(new Xpp3Driver(new XmlFriendlyNameCoder("_-", "_")));
		xstream.ignoreUnknownElements();
		// 非常重要，使用声明特性。
		xstream.processAnnotations(object.getClass());
		return xstream.fromXML(xmlString.trim());
	}


	public static String toXMLStringAppendXmlHead(Object object) throws IOException {
		log.debug("execute Object toXMLString(Object object="+object+") ");
		//XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		XStream xstream = initXStream(true);//new XStream(new Xpp3Driver(new XmlFriendlyNameCoder("_-", "_")));
		// 非常重要，使用声明特性。
		xstream.processAnnotations(object.getClass());
		ByteArrayOutputStream outputStream=null;
		Writer writer=null;
		try {
			outputStream = new ByteArrayOutputStream();

			writer = new OutputStreamWriter(outputStream,encode);
			writer.write(XML_HEAD);
			xstream.toXML(object, writer);
			String xml = outputStream.toString(encode);;
			return xml;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(outputStream!=null){
				outputStream.close();
			}
			if(writer!=null){
				writer.close();
			}
		}
	}
	
	
	public static String toXMLString(Object object) throws IOException {
		log.debug("execute Object toXMLString(Object object="+object+") ");
		//XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		XStream xstream = initXStream(true);//new XStream(new Xpp3Driver(new XmlFriendlyNameCoder("_-", "_")));
		// 非常重要，使用声明特性。
		xstream.processAnnotations(object.getClass());
		ByteArrayOutputStream outputStream=null;
		Writer writer=null;
		try {
			outputStream = new ByteArrayOutputStream();

			writer = new OutputStreamWriter(outputStream,encode);
			//writer.write("<?xml version=\"1.0\" encoding=\""+encode+"\" ?>\n");
			xstream.toXML(object, writer);
			String xml = outputStream.toString(encode);;
			return xml;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(outputStream!=null){
				outputStream.close();
			}
			if(writer!=null){
				writer.close();
			}
		}
	}
	
	
	public static Object formXML(byte[] body,Object object) {
		log.debug("execute Object toXMLString(byte[] body="+body+",Object object="+object+") ");
		if(null == body || body.length <= 0)
		{
			return null;
		}
		XStream xstream = new XStream(new Xpp3Driver(new XmlFriendlyNameCoder("_-", "_")));
		// 非常重要，使用声明特性。	
		xstream.processAnnotations(object.getClass());
		InputStream in = new ByteArrayInputStream(body);
		return (Object)xstream.fromXML(in);
	}
	
	protected static String PREFIX_CDATA = "<![CDATA[";
	protected static String SUFFIX_CDATA = "]]>";

	   
	/**
	 * 重写Xpp3Driver的方法,实现将对象转化为XML时
	 * 针对某些带有"<![CDATA["和"]]>"的字符串不进行转义处理
	 * @param isAddCDATA
	 * @return
	 */
	public static XStream initXStream(boolean isAddCDATA) {
		XStream xstream = null;
		if (isAddCDATA) {
			xstream = new XStream(new Xpp3Driver(new XmlFriendlyNameCoder("_-", "_")) {
				public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {
						protected void writeText(QuickWriter writer, String text) {
							if (text.startsWith(PREFIX_CDATA)
									&& text.endsWith(SUFFIX_CDATA)) {
								writer.write(text);
							} else {
								super.writeText(writer, text);
							}
						}
					};
				};
			});
		} else {
			xstream = new XStream();
		}
		return xstream;
	}
	
	
	

	public static void main(String[] args) throws Exception{
		
		BasicConfigurator.configure();
		String enCode="UTF-8";
		XMLParserXStreamImpl xmlParser = new XMLParserXStreamImpl();

		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>         "+
				"<strategy>                                          "+
				"  <dateStrategy>                                    "+
				"    <startTime>2016-05-17 11:18:12</startTime>      "+
				"    <endTime>2016-05-17 11:18:12</endTime>          "+
				"    <total>3000</total>                             "+
				"  </dateStrategy>                                   "+
				"  <sendStrategys>                                   "+
				"    <sendStrategy>                                  "+
				"      <fromTime>00:00:00</fromTime>                 "+
				"      <toTime>08:00:00</toTime>                     "+
				"      <percent>0.1</percent>                        "+
				"    </sendStrategy>                                 "+
				"    <sendStrategy>                                  "+
				"      <fromTime>08:00:00</fromTime>                 "+
				"      <toTime>18:00:00</toTime>                     "+
				"      <percent>0.8</percent>                        "+
				"    </sendStrategy>                                 "+
				"     <sendStrategy>                                 "+
				"      <fromTime>18:00:00</fromTime>                 "+
				"      <toTime>24:00:00</toTime>                     "+
				"      <percent>0.1</percent>                        "+
				"    </sendStrategy>                                 "+
				"  </sendStrategys>                                  "+
				"</strategy>                                         ";

		Strategy strategy=(Strategy)XMLParserXStreamImpl.formXML(xml.trim(), new Strategy());
		
		System.out.println(xmlParser.toXMLStringAppendXmlHead(strategy));
	}
}

