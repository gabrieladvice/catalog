/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Gabika
 */
public class Cryptography {
    
    Signature sign;
    KeyPairGenerator keyPairGen;
    KeyPair pair;
    Cipher cipher;

    private String encrypted;
    public Cryptography() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

                
    }
    
    public String EncryptPass(String pass) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] sa = md.digest(pass.getBytes());		
                this.encrypted = new String(sa);
                return this.encrypted;
    }
    
}
    
      /*  this.sign = Signature.getInstance("SHA256withRSA");
        this.keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        this.pair= keyPairGen.generateKeyPair();
        this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        byte[] input = "Welcome to Tutorialspoint".getBytes();	  
        cipher.update(input);
	  
        byte[] cipherText = cipher.doFinal();	 
        System.out.println(new String(cipherText, "UTF8"));
        
              //Initializing the same cipher for decryption
      cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
      
      //Decrypting the text
      byte[] decipheredText = cipher.doFinal(cipherText);
      System.out.println(new String(decipheredText));
      
        System.out.println(this.pair.getPublic());
        System.out.println(this.pair.getPrivate());*/





