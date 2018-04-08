package server;

import java.io.FileWriter;
import java.io.IOException;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

public class Util {
	
	public static void genKeys() {
		
		RsaJsonWebKey keyPair = null;
		try {
			keyPair = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException je) {
			System.out.println(je.getMessage());
		}
		byte[] priKey = keyPair.getPrivateKey().getEncoded();
		byte[] pubKey = keyPair.getPublicKey().getEncoded();
		
		// Write the key to a file
		try {
			FileWriter fw = new FileWriter("priKey.txt");
			fw.write(priKey.toString());
			fw.close();
			fw = new FileWriter("pubKey.txt");
			fw.write(pubKey.toString());
			fw.close();
		} catch (IOException ioe) {
			System.out.print(ioe.getMessage());
		}
	}
	
	public static void main(String[] args) {
		genKeys();
	}

}
