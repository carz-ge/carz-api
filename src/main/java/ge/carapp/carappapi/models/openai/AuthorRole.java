package ge.carapp.carappapi.models.openai;

public enum AuthorRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String role;

    AuthorRole(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
