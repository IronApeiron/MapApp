package com.mapapp.mpi.core.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.Key;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Ganesh Ravendranathan
 */
public class BitLocker {

    public static void main(String[]args) throws Exception {

        Key k = new SecretKeySpec(new Base64().decode("CEBPQLMWfhEetwv0xq1a7S/p53qZ3o+9PEhP0DMcHB8="), "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, k);
        byte[] decodedValue = new Base64().decode(Base64.decodeBase64("VbCjNtvw8SlkOxlRnnhr+1eDZXl2/45Z3NK2buBYfJYPaHQGtQm7zOubiDFQVvwFt8txeSLXxjz3XLiMn1b+8/Lrmsh6nTznxr6s03gFhNpwOKsYJ4LzeqObTnkqNvHWhJDzkzSEMhpAJipzRphx8UZq2dpL7LABqW2KD/Pl+VamFM/rk9/zyvMlYXBpziA6"));
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);

        System.out.println(decryptedValue);
    }

    public BitLocker(File file) throws Exception{

        File f=new File("init.mpt");
        int ch;

        StringBuffer strContent = new StringBuffer("");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(f);
            while ((ch = fin.read()) != -1)
                strContent.append((char) ch);
            fin.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Original string: " +strContent.toString()+"\n");
        // Get the KeyGenerator

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256); // 192 and 256 bits may not be available


        // Generate the secret key specs.
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();

        String key = new Base64().encodeAsString(raw);
        System.out.println("Key: " + key);

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");


        // Instantiate the cipher

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(strContent.toString().getBytes());

        Base64 base = new Base64();
        String enc = base.encodeAsString(cipher.doFinal(strContent.toString().getBytes()));

        System.out.println("encrypted string: " + enc);

        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original =cipher.doFinal(encrypted);



        String originalString = new String(original);
        System.out.println("Original string: " +originalString );

        // New
        FileOutputStream fos = new FileOutputStream("Hello2.txt");

    }
}
