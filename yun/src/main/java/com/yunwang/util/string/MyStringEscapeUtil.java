/**
 * 
 */
package com.yunwang.util.string;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;

/**
 * @author 方宜斌
 * @date 2014-4-2下午1:10:22
 * <p>特殊字符转义</p>
 * @version 1.0
 */

public class MyStringEscapeUtil extends StringEscapeUtils {
	
	public static final CharSequenceTranslator ESCAPE_JSON = 
	          new LookupTranslator(
	            new String[][] { 
	              {"\"", "\\\""},
	              {"\\", "\\\\"},
	              {"/", "\\/"},
	              {"\b", "\\b"},
	              {"\f", "\\f"},
	              {"\n", "\\n"},
	              {"\r", "\\r"},
	              {"\t", "\\t"},
	          }).with(
	            new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE())
	          ).with(
	            UnicodeEscaper.outsideOf(32, 0x7f) 
	        );
	
	public static final String escapeJson(String input) {
        return ESCAPE_JSON.translate(input);
    }
}
