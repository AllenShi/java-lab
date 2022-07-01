package net.sjl.fragrans.util;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Codec {
	
	public final static String BASE64_PREFIX = "base64";
	public final static char L_CB = '{';
	public final static char R_CB = '}';
	public final static String CB_BASE64_PREFIX = L_CB + BASE64_PREFIX + R_CB;
	public final static String ESCAPE_BASE64_PREFIX = "\\" + CB_BASE64_PREFIX + "\\";

	/**
	 * Encode token. If the token starts with "{base64}", just return it as it is; otherwise, we would encode it.
	 * If token itself wants to start with "{base64}" in plain text, escaping it with "\\{base64}\\".
	 * @param token
	 * @return
	 */
	public static String encode(String token) {
		if (token.startsWith(CB_BASE64_PREFIX)) {
			return token;
		} else {
			// Handle case whose token might start with "{base64}" in plain text, 
			// escape string "\\{base64}\\" must be used 
			String curToken = token;
			if(token.startsWith(ESCAPE_BASE64_PREFIX)) {
				curToken = CB_BASE64_PREFIX + token.substring(ESCAPE_BASE64_PREFIX.length());
			}
			
			byte[] encodedBytes = Base64.getEncoder().encode(curToken.getBytes());

			return CB_BASE64_PREFIX + new String(encodedBytes, Charset.forName("UTF-8"));
		}

	}

	public static String decode(String token) {
		if (token.startsWith(CB_BASE64_PREFIX)) {
			String curToken = token.substring(CB_BASE64_PREFIX.length());
			byte[] decodedBytes = Base64.getDecoder().decode(curToken.getBytes());
			return new String(decodedBytes, Charset.forName("UTF-8"));
		} else {
			return token;
		}
	}

}
