package by.epam.task6001.service;

public interface EncryptorService {
    String decrypt(String encryptedPassword);
    String encrypt(String decryptedPassword);
}
