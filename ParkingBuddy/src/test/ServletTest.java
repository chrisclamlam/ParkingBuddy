package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.junit.Test;

import database.AppDatabase;
import server.Util;

public class ServletTest {

	// @Test
	public void testRegisterUser() {
		
		String host = "http://localhost:8080/ParkingBuddy/SignUp";
		AppDatabase adb = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		try {
			// Delete the test user
			adb.delete("username");
			assertEquals(adb.exists("username"), false);
			// Connect to the host
			URL url = new URL(host);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			// Create the body
			String body = "username=username&fname=fname&lname=lname&email=email&password=password";
			conn.setDoOutput(true);
			// Write the Data
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			// Test the response code
			assertEquals(200, conn.getResponseCode());
			adb.delete("username");
			assertEquals(adb.exists("username"), false);
			return;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		// The code should never reach this line, this means an exception was thrown
		assertEquals(true, false);
	}
	
	@Test
	public void testEncryption() {
		/*// Filenames
		String pubFile = "public.pem";
		String priFile = "private.pem";
		// Variables for the keys
		RSAPrivateKey priKey = null;
		RSAPublicKey pubKey = null;
		// Read the keys with the Util functions
		try {
			priKey = Util.getPrivateKey(priFile);
			pubKey = Util.getPublicKey(pubFile);
		} catch (IOException | GeneralSecurityException e) {
			System.out.println(e.getMessage());
		}*/
		RsaJsonWebKey key = null;
		try {
			key = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException je) {
			System.out.println("HERE: " + je.getMessage());
			return;
		}
		key.setKeyId("k1");
		// Set data claims
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("ParkingBuddy");
		claims.setAudience("user");
		claims.setClaim("email", "test@test.com");
		claims.setExpirationTimeMinutesInTheFuture(30);
		claims.setSubject("auth");
		// Set the signature
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		// Encrypt
		jws.setKey(key.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		String jwt = "";
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException je) {
			System.out.println("THERE: " + je.getMessage());
		}
		// Decrypt data
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
		try {
			JwtClaims res = jwtConsumer.processToClaims(jwt);
			assertEquals(res.getAudience().get(0), "user");
			assertEquals(res.getIssuer(), "ParkingBuddy");
			return;
		} catch (InvalidJwtException ije) {
			System.out.println(ije.getMessage());
		} catch (MalformedClaimException mce) {
			System.out.println(mce.getMessage());
		}
		// Check if equal after decryption & encryption
		assertEquals(true, false);	
	}
}
