import java.util.Random;

import redis.clients.jedis.UnifiedJedis;

public class Authentication {
    private final int API_KEY_LENGTH = 12;
    public static void main(String[] args) {
        // blank ?
    }

    public boolean initializeUser(UnifiedJedis jedis, String username) {
        // Check if user exists 
        String userKey = "username:" + username;
        boolean userExists = jedis.exists(userKey);

        if (userExists) {
            return false;
        } 

        // create user with api key
        String apiKey = generateApiKey();
        jedis.set(userKey, apiKey);
        return true;
    }

    public String getApiKey(UnifiedJedis jedis, String username) {
        String apiKey = jedis.get("username:" + username);
        System.out.println("API KEY: " + apiKey);
        return apiKey;
    }

    public String generateApiKey() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < this.API_KEY_LENGTH; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}