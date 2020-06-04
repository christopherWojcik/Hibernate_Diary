package service;

public interface PasswordService {

    byte[] hashPassword(String password_plaintext);

}
