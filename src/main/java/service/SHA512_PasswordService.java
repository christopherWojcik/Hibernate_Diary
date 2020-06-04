package service;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA512_PasswordService implements PasswordService{

    @SneakyThrows
    @Override
    public byte[] hashPassword(String password_plaintext) {
        return (MessageDigest.getInstance("SHA-512").digest(password_plaintext.getBytes(StandardCharsets.UTF_8)));
    }

}
