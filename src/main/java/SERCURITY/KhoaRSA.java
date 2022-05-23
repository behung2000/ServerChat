package SERCURITY;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KhoaRSA {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private static final String PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKRAItG+orJl9tRitdGaD69TcWii/vlHQN+8K+hRJHps48Rlc6RkySy1H60DtdQs1unUSnjoPKPT+ZgjP12jXdqaSCMa7nCqPfIPIJEI3xqA98jx57CjGhEroVmBIlAXpPmIDH8NatsK2c6jQwk84DlC42gwgGz+biDLjX3er26nAgMBAAECgYAqPWw28IW6x0og+Hm1u58JAbaKqFBWHyLp5uSLzEJLUbGJkmWZA2pRGwtYXTnnpNhoLfYw/diKcTshCv+Cvz6lS8zNDy7n7DznlGiU8BxhDAbV+TgZ17aVs7xlHMganrmoQ52XkuyC5+fsQq4Df+BCjnwqLcULPoTeGsEkF+lv4QJBAKjaeVlg6e/uC0DAN1yJLarXmpAolsjMthikH29JnyJgRH2biUFxHNIhkTZnygF1ILpYVupSLdN7mNG3Pd1nq78CQQD5BYMs8QeTgZLCWBEeYxeDGlk2+Oz0wWFbj+GuP0If4qFWDj3/rbVIr6uwsAP+4Kd7xfYd4VAYYmfm5GS/PpcZAkAPfLJio0PxsLixgK/TOfXJVKsli8OAuV3+VpdXgr4ozgGaL7jgGrrU/yUtg0mA5rhQvyeBqHXxjsWOfqsa8pZLAkBl+/P0++F2b6KDJT4di8edUbUn5lplAq20qgBrNE0IcfBRJhQOGj4cPpW6DtwUDj2JW1GoexYqZmLXFMKN8DxBAkEAoDzlrKcqxU+7QvLZUa+aszOFTVZYwx2ksvs2JaLaLEOuTYTE9NePcFsz0SuQwbwuKTPNpG4JndIKD75X4j+8fg==";
    private static final String PUBLIC_KEY_STRING =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkQCLRvqKyZfbUYrXRmg+vU3Foov75R0DfvCvoUSR6bOPEZXOkZMkstR+tA7XULNbp1Ep46Dyj0/mYIz9do13amkgjGu5wqj3yDyCRCN8agPfI8eewoxoRK6FZgSJQF6T5iAx/DWrbCtnOo0MJPOA5QuNoMIBs/m4gy4193q9upwIDAQAB";



    //Lúc đầu tạo key
    public void init(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
        } catch (Exception ignored) {
        }
    }


    public void initFromStrings(){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);
        }catch (Exception ignored){}
    }


    public void printKeys(){
        System.err.println("Public key\n"+ encode(publicKey.getEncoded()));
        System.err.println("Private key\n"+ encode(privateKey.getEncoded()));
    }

    public String encrypt(String message) {
        try {
            byte[] messageToBytes = message.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(messageToBytes);
            return encode(encryptedBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e){
            return "false";
        }
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String decrypt(String encryptedMessage){
        try {
            byte[] encryptedBytes = decode(encryptedMessage);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
            return new String(decryptedMessage, "UTF8");
        }
        catch (NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e){
            return "false";
        }
    }


    /*
    public static void main(String[] args) {
        KhoaRSA rsa = new KhoaRSA();
        rsa.init();
        rsa.printKeys();
        //String encryptedMessage = rsa.encrypt("5");
        //String decryptedMessage = rsa.decrypt("br2icR3q6vDCK5Sm3HkEor+0UGEbAz7CIgZunHe1oP2PVB/5PrFN9th/QbckEZpVD3CGZ2+OPRQcarqQpZ5YcD2BoF+IJULO2vRLMUDVaOXhY4HswCohkYCGPW+9XbX6shqnorL4Ydta1od/B/9FDQ4wExt+R6xGGivNL6HM7tg=");

        //System.out.println("Encrypted:\n"+encryptedMessage);
        //System.out.println("Decrypted:\n"+decryptedMessage);
    }
     */


}
