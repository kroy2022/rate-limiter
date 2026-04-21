package dto;

public class RateLimitResponse {
    private boolean allowed;
    private int remaining;
    private String message;

    public RateLimitResponse(boolean allowed, int remaining, String message) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.message = message;
    }
}