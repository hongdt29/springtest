package summer.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Encryption {
	public static String EncryptPassword(String input) {
		String result = "";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			result = DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		}
		return result;
		}

}
