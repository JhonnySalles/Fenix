package model.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DecodeHash {
	
	// Fun��o criada para estar criptografando a senha informada.
	private static String Decode(String password ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String senha = password;
        
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigestSenhaAdmin[] = algorithm.digest(senha.getBytes("UTF-8"));
        
        StringBuilder hexStringSenha = new StringBuilder();
        for (byte b : messageDigestSenhaAdmin) {
                 hexStringSenha.append(String.format("%02X", 0xFF & b));
        }

        return hexStringSenha.toString();                      
	}
	
	public static Boolean CompPassword(String user, String psw) {
		return true;
	}
	
	public static String DecodePassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Decode(password);
	}
}
