import secrets

TOKEN_LENGTH = 24

generated_key = secrets.token_urlsafe(TOKEN_LENGTH)

print("Token", generated_key)
