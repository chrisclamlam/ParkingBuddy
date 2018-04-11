package test;

import static org.junit.Assert.assertEquals;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.junit.Test;

import server.Util;

public class UtilTest {
	
	@Test
	public void testKeyIO() {
		String filename = "test.txt";
		RsaJsonWebKey key = Util.generateKey();
		Util.writeKeyToFile(key, filename);
		RsaJsonWebKey sameKey = Util.readKeyFromFile(filename);
		if(sameKey == null) {
			assertEquals(true, false);
		}
		assertEquals(key.getKeyType(), sameKey.getKeyType());
	}
	
	@Test
	public void testEncryption() {
		// Generate the key
		RsaJsonWebKey key = Util.generateKey();
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
