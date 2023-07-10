set windows-shell := ["cmd.exe", "/c"]
up:
  docker compose up --build

gen:
   cd graphql && npm run gen

build:
    gradlew bootJar -i --stacktrace --no-daemon

start:
    gradlew bootRun
