package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import database.User;

public class Util {
	
	private static RsaJsonWebKey key = null;
	
	public static String generateToken(User u, String filename) {
		// Generate the key
		System.out.println("Reading key from file");
		RsaJsonWebKey key = Util.generateKey();
		System.out.println("Read key from file");
		key.setKeyId("k1");
		
		// Set data claims
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("ParkingBuddy");
		claims.setAudience("user");
		claims.setClaim("username", u.getUsername());
		claims.setClaim("fname", u.getFname());
		claims.setClaim("lname", u.getLname());
		claims.setClaim("email", u.getEmail());
		claims.setClaim("passhash", u.getPasshash());
		claims.setExpirationTimeMinutesInTheFuture(30);
		claims.setSubject("auth");
		
		// Set the signature
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		
		// Encrypt and return
		jws.setKey(key.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		try {
			System.out.println("Serializing JWT into string");
			return jws.getCompactSerialization();
		} catch (JoseException je) {
			System.out.println(je.getMessage());
			return null;
		}
	}
	
	public static User readToken(String jwt) {
		// Decrypt data
		try {
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setRequireExpirationTime() // the JWT must have an expiration time
	            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
	            .setRequireSubject() // the JWT must have a subject claim
	            .setExpectedIssuer("ParkingBuddy") // whom the JWT needs to have been issued by
	            .setExpectedAudience("user") // to whom the JWT is intended for
	            .setVerificationKey(key.getKey()) // verify the signature with the public key
	            .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
	                    new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
	                            AlgorithmIdentifiers.RSA_USING_SHA256))
	            .build(); // create the JwtConsumer instance
			// Get data to instantiate user from claim
			int id;
			String username, email, fname, lname;
			byte[] passhash;
			// Get data from claim
			JwtClaims res = jwtConsumer.processToClaims(jwt);
			id = (int)res.getClaimValue("id");
			username = (String)res.getClaimValue("username");
			email = (String)res.getClaimValue("email");
			fname = (String)res.getClaimValue("fname");
			lname = (String)res.getClaimValue("lname");
			passhash = ((String)res.getClaimValue("passhash")).getBytes();
			// Initialize user and return it for operations
			return new User(id, username, email, fname, lname, passhash);
		} catch (InvalidJwtException ije) {
			System.out.println(ije.getMessage());
			return null;
		}
	}
	
	public static void writeKeyToFile(RsaJsonWebKey key, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(key);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static RsaJsonWebKey readKeyFromFile(String filename) {
		RsaJsonWebKey key = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			key = (RsaJsonWebKey) ois.readObject();
			ois.close();
			fis.close();
			return key;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
		return null;
	}
	
	public static RsaJsonWebKey generateKey() {
		try {
			return RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException je) {
			System.out.println(je.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) {
		writeKeyToFile(generateKey(), "key.txt");
	}
}