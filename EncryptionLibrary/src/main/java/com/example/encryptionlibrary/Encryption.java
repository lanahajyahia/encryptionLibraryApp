package com.example.encryptionlibrary;


import android.content.Context;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * a class for encryption/decryption library, simple and core encryption/decryption
 */
public class Encryption {

    private static Encryption instance;
    /**
     * decrypt/encrypt method used by many... (learned in data security course)
     *  Advanced Encryption Standard (AES)
     */
    public static final String METHOD = "AES";


    /**
     * The first part of ISO-8859-1 (entity numbers from 0-127) is the original ASCII character-set.
     * It contains numbers, upper and lowercase English letters, and some special characters.
     * you could use UTF-8
     */
    public static final String CHAR_SET_NAME = "ISO-8859-1";


    private Cipher cipher, decipher;
    private SecretKeySpec secretKeySpec;
    private KeyGenerator keyGenerator;
    private SecretKey secretKey;

    /**
     * generates a random secure key for decryption
     * @param context the main activiy in app where use this library
     *  private constructor, to build the instance of encryption/decryption

     */
    private Encryption(Context context) throws NoSuchAlgorithmException {
        try {
            cipher = Cipher.getInstance(METHOD);
            decipher = Cipher.getInstance(METHOD);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey();

       secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), METHOD);
    }

    /**
     *
     * @param context
     * @return encryption instance
     */
    public static synchronized Encryption getInstance(Context context) throws NoSuchAlgorithmException {

        if (instance == null) {
            instance = new Encryption(context.getApplicationContext()); //getApplicationContext to avoid memory leaks
        }
        return instance;
    }

    /**
     * encryption method using AES
     * @param contentString - the string we want to encrypt
     * @return - encrypted string
     * @throws InvalidKeyException - if the specified key specification cannot be used to generate a secret key
     *@throws BadPaddingException - if the padding of the data does not match the padding scheme
     * @throws IllegalBlockSizeException - if the size of the resulting bytes is not a multiple of the cipher block size
     */
    public String AESEncryption(String contentString) {

        byte[] stringByte = contentString.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedByte = cipher.doFinal(stringByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        String returnString = "";
        try {
            returnString = new String(encryptedByte, CHAR_SET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnString;
    }


    /**
     * a method that calls AESEncryption and handles exceptions and return null when it occurs
     * @param contentString -data the String to be encrypted
     * @return method for encrtyption or null if data sent is null
     */
    public String AESEncryptionOrNull(String contentString) {
        try {
            return AESEncryption(contentString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This is a method that calls AESEncryption method in background, it is a good idea to use this
     * one instead the default method because encryption can take several time and with this method
     * the process occurs in a AsyncTask, other advantage is the Callback with separated methods,
     * one for success and other for the exception
     *
     * @param contentString     the String to be encrypted
     * @param callback the Callback to handle the results
     */
    public void AESEncryptionAsync(String contentString, final Callback callback) {
        if (callback == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String encrypt = AESEncryption(contentString);
                    if (encrypt == null) {
                        callback.onError(new Exception("Encrypt return null, it normally occurs when you send a null data"));
                    }
                    callback.onSuccess(encrypt);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }).start();

    }


    /**
     * decryption method using AES
     * @param contentString - the string we want to decrypt
     * @return - decrypted (the original) string
     * @throws InvalidKeyException -if the specified key specification cannot be used to generate a secret key
     *@throws BadPaddingException   -if the padding of the data does not match the padding scheme
     * @throws IllegalBlockSizeException  -if the size of the resulting bytes is not a multiple of the cipher block size
     */
    public String AESDecryption(String contentString) throws UnsupportedEncodingException {

        byte[] EncryptedByte = contentString.getBytes(CHAR_SET_NAME);
        String decryptedString = contentString;

        byte[] decryption;

        try {
            decipher.init(cipher.DECRYPT_MODE, secretKeySpec);
            decryption = decipher.doFinal(EncryptedByte);
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedString;

    }


    /**
     * a method that calls AESDecryption and handles exceptions and return null when it occurs
     * @param contentString -data the String to be decrypted
     * @return method for decryption or null if data sent is null
     */
    public String AESDecryptionOrNull(String contentString) {
        try {
            return AESDecryption(contentString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This is a method that calls AESDecryption method in background, it is a good idea to use this
     * one instead the default method because decryption can take several time and with this method
     * the process occurs in a AsyncTask, other advantage is the Callback with separated methods,
     * one for success and other for the exception
     *
     * @param contentString     the String to be encrypted
     * @param callback the Callback to handle the results
     */
    public void AESDecryptionAsync(String contentString, final Callback callback) {
        if (callback == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String decrypt = AESDecryption(contentString);
                    if (decrypt == null) {
                        callback.onError(new Exception("Decrypt return null, null data was decrypted"));
                    }
                    callback.onSuccess(decrypt);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }).start();

    }


    /**
     * When you encrypt or decrypt in callback mode you get noticed of result using this interface
     */
    public interface Callback {

        /**
         * Called when encrypt or decrypt job ends and the process was a success
         *
         * @param result the encrypted or decrypted String
         */
        void onSuccess(String result);

        /**
         * Called when encrypt or decrypt job ends and has occurred an error in the process
         *
         * @param exception the Exception related to the error
         */
        void onError(Exception exception);

    }

}

