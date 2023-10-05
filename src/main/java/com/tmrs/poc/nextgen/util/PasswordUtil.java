package com.tmrs.poc.nextgen.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tmrs.poc.nextgen.service.NextGenUserService;


@Component
public class PasswordUtil {
	    
	private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
	private static final Logger logger = LogManager.getLogger(NextGenUserService.class);
    
    
	/**
	 * 
	 * @param input
	 * @param key
	 * @param iv
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String encrypt(String input, SecretKey key, IvParameterSpec iv) 
	throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException 
	{    
	    Cipher cipher = Cipher.getInstance(encryptAlgorithm);
	    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	    byte[] cipherText = cipher.doFinal(input.getBytes());
	    return Base64.getEncoder()
	        .encodeToString(cipherText);
	}
	
	/**
	 * 
	 * @param cipherText
	 * @param key
	 * @param iv
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String decrypt(String cipherText, SecretKey key, IvParameterSpec iv) 
	throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException 
	{
	    Cipher cipher = Cipher.getInstance(encryptAlgorithm);
	    cipher.init(Cipher.DECRYPT_MODE, key, iv);
	    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
	    return new String(plainText);
	}
	
	
	/**
	 * 
	 * @param providedPassword
	 * @param securedPassword
	 * @param key
	 * @param iv
	 * @return
	 */
	public boolean verifyUserPassword(String providedPassword, String securedPassword, SecretKey key, IvParameterSpec iv) {
		String decryptedPassword;
		try {
			decryptedPassword = this.decrypt(securedPassword, key, iv);
			return decryptedPassword.equals(providedPassword);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
	public IvParameterSpec generateIv(String ivString) {
	    return new IvParameterSpec(ivString.getBytes());
	}
	
	public SecretKey getKeyFromPassword(String password, String salt)
	throws NoSuchAlgorithmException, InvalidKeySpecException 
	{
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
	    SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	    return originalKey;
	}
	
	public static void main(String [] args) {
		PasswordUtil passwordUtil = new PasswordUtil();
		
		if(args.length == 4) {
			String password = args[0];
			String keyPassword = args[1];
			String salt = args[2];
			String ivSpec = args[3];
			
			try {
				IvParameterSpec Ivspec = new IvParameterSpec(ivSpec.getBytes());
				SecretKey key = passwordUtil.getKeyFromPassword(keyPassword, salt);
				String decPassword = passwordUtil.encrypt(password, key, Ivspec);
				System.out.println("Password [ "+password+" ] encrypted [ "+decPassword+" ] key [ "+keyPassword+" ] salt [ "+salt+" ] vi [ "+ivSpec+" ]");
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}
		}
		else if(args.length == 0) {
			
			String salt = "salt_of_the_earth";
			String keyPassword = "I@mTh3W0rld";
			String ivSpec = "0192837465564738";
			try {
				IvParameterSpec Ivspec = new IvParameterSpec(ivSpec.getBytes());
				SecretKey key = passwordUtil.getKeyFromPassword(keyPassword, salt);
				String password = passwordUtil.encrypt("TMRS@dm1n", key, Ivspec);
				System.out.println("TMRS@dm1n : [ "+password+" ] decrypted [ "+passwordUtil.verifyUserPassword("TMRS@dm1n", password, key, Ivspec)+" ] key [ "+keyPassword+" ] salt [ "+salt+" ] vi [ "+ivSpec+" ]");
				
				password = passwordUtil.encrypt("I@mTh3Myth!", key, Ivspec);
				System.out.println("I@mTh3Myth! : [ "+password+" ] decrypted [ "+passwordUtil.verifyUserPassword("I@mTh3Myth!", password, key, Ivspec)+" ] key [ "+keyPassword+" ] salt [ "+salt+" ]vi [ "+ivSpec+" ]");
				
				password = passwordUtil.encrypt("T3st1User!", key, Ivspec);
				System.out.println("T3st1User! : [ "+password+" ] decrypted [ "+passwordUtil.verifyUserPassword("T3st1User!", password, key, Ivspec)+" ] key [ "+keyPassword+" ] salt [ "+salt+" ]vi [ "+ivSpec+" ]");
				
				Ivspec = new IvParameterSpec("8164093857836501".getBytes());
				key = passwordUtil.getKeyFromPassword("next_gen_database", salt);
				password = passwordUtil.encrypt("tmrs_next_gen", key, Ivspec);
				System.out.println("tmrs_next_gen : [ "+password+" ] decrypted [ "+passwordUtil.verifyUserPassword("tmrs_next_gen", password, key, Ivspec)+" ] key [ next_gen_database ] salt [ salt_of_the_earth ] vi [ 8164093857836501 ]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("");
		}
	}

}
