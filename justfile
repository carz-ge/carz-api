set windows-shell := ["cmd.exe", "/c"]

init:
  cd graphql && npm i

up:
  docker compose up --build

gen:
   cd graphql && npm run gen

build:
    gradlew bootJar -i --stacktrace --no-daemon

start:
    gradlew bootRun
