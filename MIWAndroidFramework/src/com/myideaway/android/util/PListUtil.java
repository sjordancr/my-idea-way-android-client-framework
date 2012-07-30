package com.myideaway.android.util;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class PListUtil {
	/**
	 * 将plist文件转换成object对象
	 * 
	 * @param path
	 * @param htmlLst
	 *            有html标签的key
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> htmlListData = new ArrayList<String>();

	public static Object toObject(String path, ArrayList<String> htmlLst)
			throws Exception {
		htmlListData = htmlLst;
		File file = new File(path);

		InputSource source = new InputSource(new FileInputStream(file));

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		PListContentHandler handler = new PListContentHandler();
		xr.setContentHandler(handler);

		xr.parse(source);

		return handler.getRoot();
	}

	public static String toXML(Object obj) {
		StringBuffer xml = new StringBuffer();
		StringBuffer space = new StringBuffer();
		toXMLCycle(obj, xml, space);

		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		result
				.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\r\n");
		result.append("<plist version=\"1.0\">\r\n");
		result.append(xml);
		return result.toString();
	}

	private static void toXMLCycle(Object element, StringBuffer xml,
			StringBuffer space) {
		if (element instanceof Map) {
			Map map = (Map) element;

			xml.append(space);
			xml.append("<dict>\r\n");
			space.append("  ");
			for (Object key : map.keySet()) {
				xml.append(space);
				xml.append("<key>");
				xml.append(key.toString());
				xml.append("</key>\r\n");

				Object value = map.get(key);
				toXMLCycle(value, xml, space);
			}

			space.delete(0, 2);
			xml.append(space);
			xml.append("</dict>\r\n");

		} else if (element instanceof List) {
			xml.append(space);
			xml.append("<array>\r\n");
			space.append("  ");

			List list = (List) element;
			for (Object item : list) {
				toXMLCycle(item, xml, space);
			}

			space.delete(0, 2);
			xml.append(space);
			xml.append("</array>\r\n");

		} else {
			xml.append(space);
			xml.append("<string>");
			xml.append(element.toString());
			xml.append("</string>\r\n");
		}
	}

	static class PListContentHandler extends DefaultHandler {
		private static final String TAG_PLIST = "plist";
		private static final String TAG_KEY = "key";
		private static final String TAG_STRING = "string";
		private static final String TAG_DICT = "dict";
		private static final String TAG_ARRAY = "array";
		private boolean isEnd = false;
		private String currentTag;
		private String key;
		private String value;
		private Stack stack = new Stack();
		private Object root;
		private boolean isRead;
		private String mapValue = "";

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String data = new String(ch, start, length);
			if (!isRead) {
				return;
			}
			if (">".equals(data)) {
				data = "|";
			}
			if ("<".equals(data)) {
				data = "^";
			}
			if (currentTag.equals(TAG_KEY)) {
				key = data;
			} else if (currentTag.equals(TAG_STRING)) {
				if (null != htmlListData && htmlListData.contains(key)) {
					if (!isEnd) {
						mapValue += data;
					} else {
						mapValue = data;
					}
				} else {
					value = data;
				}
			}
			if (null != mapValue) {
				mapValue = mapValue.replace("|", ">");
				mapValue = mapValue.replace("^", "<");
			}
			isEnd = false;
			super.characters(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			currentTag = localName;
			if (("").equals(currentTag)) {
				currentTag = qName;
			}

			if (currentTag.equals(TAG_DICT)) {
				root = stack.pop();
				isEnd = true;

			} else if (currentTag.equals(TAG_ARRAY)) {
				root = stack.pop();
				isEnd = true;

			} else if (currentTag.equals(TAG_STRING)) {
				isEnd = true;
				Object parent = stack.peek();

				if (parent instanceof ArrayList) {
					((ArrayList) parent).add(value);
				} else if (parent instanceof HashMap) {
					if (null != htmlListData && htmlListData.contains(key)) {
						((HashMap) parent).put(key, mapValue);
					} else {
						((HashMap) parent).put(key, value);
					}

				}
			} else if (currentTag.equals(TAG_KEY)) {
				isEnd = true;

			} else if (currentTag.equals(TAG_PLIST)) {
				isEnd = true;
			}

			isRead = false;

			super.endElement(uri, localName, qName);
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			currentTag = localName;
			if (("").equals(currentTag)) {
				currentTag = qName;
			}

			if (currentTag.equals(TAG_DICT)) {
				isEnd = true;
				Object item = new HashMap();
				if (("").equals(currentTag)) {
					currentTag = qName;
				}

				if (!stack.empty()) {
					Object parent = stack.peek();

					if (parent instanceof ArrayList) {
						((ArrayList) parent).add(item);
					} else if (parent instanceof HashMap) {
						((HashMap) parent).put(key, item);
					}
				}

				stack.push(item);
			} else if (currentTag.equals(TAG_ARRAY)) {
				isEnd = true;
				Object item = new ArrayList();

				if (!stack.empty()) {
					Object parent = stack.peek();
					if (parent instanceof ArrayList) {
						((ArrayList) parent).add(item);
					} else if (parent instanceof HashMap) {
						((HashMap) parent).put(key, item);
					}
				}

				stack.push(item);
			} else if (currentTag.equals(TAG_KEY)) {
				isEnd = true;
			} else if (currentTag.equals(TAG_STRING)) {
				isEnd = true;
			} else if (currentTag.equals(TAG_PLIST)) {
				isEnd = true;
			}
			isRead = true;

			super.startElement(uri, localName, qName, attributes);
		}

		public Object getRoot() {
			return root;

		}
	}

}
