HEALTH_CHECK_URL=$1
MAX_RETRIES=10
SLEEP_TIME=5

retries=0

until curl --output /dev/null --silent --fail $HEALTH_CHECK_URL; do
  retries=$((retries + 1))
  if [[ $retries -eq $MAX_RETRIES ]]; then
      echo "Health check failed after $retries retries."
      exit 1
  fi
  echo "Retrying health check in $SLEEP_TIME seconds..."
  sleep $SLEEP_TIME
done
echo "Health check passed!"
