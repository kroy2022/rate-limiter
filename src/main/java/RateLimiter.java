import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import dto.RateLimitResponse;

public class RateLimiter {
    private JedisClientConfig config;
    private UnifiedJedis jedis;
    private Database db;

    public static void main(String[] args) {
        RateLimiter app = new RateLimiter();
        Properties prop = new Properties();
        app.db = new Database();
        try (FileInputStream fis = new FileInputStream(".env")) {
            prop.load(fis);
            
            String redisUrl = prop.getProperty("REDIS_URL");
            String redisPassword = prop.getProperty("REDIS_PASSWORD");

            System.out.println("REDIS URL: " + redisUrl + "\nREDIS PASSWORD: " + redisPassword);
            app.initializeRedis(redisUrl, redisPassword);
            app.testConnection();
        } catch (IOException e) {
            System.err.println(".env file not found!");
        }
    }

    private void initializeRedis(String redisUrl, String redisPassword) {
        this.config = DefaultJedisClientConfig.builder()
                .user("default")
                .password(redisPassword)
                .build();

        this.jedis = new UnifiedJedis(
            new HostAndPort(redisUrl, 17375),
            this.config
        );
    }

    private void testConnection() {
        String test1 = this.jedis.set("foo", "bar");
        System.out.println("TEST 1: " + test1);
        System.out.println("TEST 1 CACHE: " + this.jedis.get("foo"));   
    }
}
