set windows-shell := ["cmd.exe", "/c"]

init:
  chmod +x ./scripts/init.sh
  ./scripts/init.sh
  cd graphql && npm i

up:
  docker compose up --build

gen:
   cd graphql && npm run gen

run:
   docker build -f Dockerfile.native -t carz-api .
#   docker run  -rm carz-api

build:
    gradlew bootJar -i --stacktrace --no-daemon

start:
    gradlew bootRun


log:
   railway logs
