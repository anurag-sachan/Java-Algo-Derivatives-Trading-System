#!/bin/bash

authorization_token="enctoken /DrLhMnXUT3yw5iu26BIqfp04PsDUGFY1UC/Wqy6YAp9ZbX3glp2mm+3ol1gpAiz1gQS+CwVmbAgmg6TndxVigN/SU6pREdiXN4CLDvv4eDqAkk/3sQ/nA=="
base_url="https://kite.zerodha.com/oms/instruments/historical/256265/15minute"

# Single input date
date_str="01JUN23"

# Convert input date to required format
year="20${date_str:5:2}"  # Extract year from input date

case "${date_str:2:3}" in
    JAN) month="01" ;;
    FEB) month="02" ;;
    MAR) month="03" ;;
    APR) month="04" ;;
    MAY) month="05" ;;
    JUN) month="06" ;;
    JUL) month="07" ;;
    AUG) month="08" ;;
    SEP) month="09" ;;
    OCT) month="10" ;;
    NOV) month="11" ;;
    DEC) month="12" ;;
    *) echo "Invalid month"; exit 1 ;;
esac

# Convert day to an integer, subtract 1, and format back to string with leading zero if needed
day=$(printf "%02d" $((10#${date_str:0:2} - 1)))

from_date="${year}-05-31+14:30:00"
to_date="${year}-${month}-${date_str:0:2}+15:30:00"

echo "$from_date" "$to_date"

# Construct the URL
url="${base_url}?from=${from_date}&to=${to_date}"

# Send GET request using curl
response=$(curl -s -H "Authorization: ${authorization_token}" "$url")

# Check if the response contains valid data
if echo "$response" | jq -e '.data.candles | length > 0' > /dev/null; then
    # Extract the desired columns from the response using jq
    candles=$(echo "$response" | jq -r '.data.candles[] | .[4]')

    # Create the directory if it doesn't exist
    mkdir -p "$date_str"

    # Save the extracted data to the respective file
    echo "$candles" >> "$date_str/$date_str.txt"
else
    echo "No valid data for date: $date_str"
fi

