package com.example.tungphan.wizelinecleanshortenchallenge.distractrabit;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Crypto {

    private static final String TAG = Crypto.class.getSimpleName();
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static String DELIMITER = "]";
    private static int KEY_LENGTH = 256;
    private static SecureRandom random = new SecureRandom();
    private static KeyStore keystore;

    private Crypto() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static SecretKey generateAesSecretKey(String alias) {
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);
        KeyGenParameterSpec keySpec = builder
                .setKeySize(KEY_LENGTH)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setRandomizedEncryptionRequired(false)
                .setUserAuthenticationRequired(false)
                .setUserAuthenticationValidityDurationSeconds(5 * 60)
                .build();
        KeyGenerator kg = null;
        try {
            keystore = KeyStore.getInstance(ANDROID_KEY_STORE);
            try {
                keystore.load(null);
                if (!keystore.containsAlias(alias)) {
                    kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
                    kg.init(keySpec);
                    return kg.generateKey();
                }
            } catch (CertificateException | IOException | NoSuchAlgorithmException | NoSuchProviderException
                    | InvalidAlgorithmParameterException ex) {
                ex.printStackTrace();
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey getAesSecretKey(String alisas) {
        KeyStore.SecretKeyEntry entry = null;
        try {
            keystore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keystore.load(null);
            entry = (KeyStore.SecretKeyEntry) keystore.getEntry(alisas, null);
        } catch (UnrecoverableEntryException | KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return entry.getSecretKey();
    }

    public static byte[] generateIv(int length) {
        byte[] b = new byte[length];
        random.nextBytes(b);

        return b;
    }

    public static String encryptAesCbc(String plaintext, SecretKey key) {
        try {
            Cipher cipher;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");//"AES/CBC/NoPadding");
            } else {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            }
            byte[] iv = generateIv(cipher.getBlockSize());
            Log.d(TAG, "IV: " + toHex(iv));
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            Log.d(TAG, "Cipher IV: "
                    + (cipher.getIV() == null ? null : toHex(cipher.getIV())));
            byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));

            return String.format("%s%s%s", toBase64(iv), DELIMITER,
                    toBase64(cipherText));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (byte b : bytes) {
            buff.append(String.format("%02X", b));
        }

        return buff.toString();
    }

    public static String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static byte[] fromBase64(String base64) {
        return Base64.decode(base64, Base64.DEFAULT);
    }

    public static String decryptAesCbc(String ciphertext, SecretKey key) {
        try {
            String[] fields = ciphertext.split(DELIMITER);
            if (fields.length != 2) {
                throw new IllegalArgumentException(
                        "Invalid encypted text format");
            }
            byte[] cipherBytes = fromBase64(fields[1]);
            Cipher cipher;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");//"AES/CBC/NoPadding");
            } else {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            }
            byte[] iv = generateIv(cipher.getBlockSize());
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
            byte[] plaintext = cipher.doFinal(cipherBytes);
            String plainrStr = new String(plaintext, "UTF-8");
            return plainrStr;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
