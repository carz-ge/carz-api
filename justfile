set windows-shell := ["cmd.exe", "/c"]
up:
  docker compose up --build

gen:
   cd graphql && npm run gen
