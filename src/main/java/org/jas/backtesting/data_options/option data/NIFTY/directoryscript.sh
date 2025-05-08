#!/bin/bash

# Check if the file exists
if [ ! -f "niftyexpiry copy.txt" ]; then
    echo "File niftyexpiry copy.txt does not exist."
    exit 1
fi

# Loop through each value extracted by awk
awk '{ for (i=1; i<=NF; i++) print $i }' "niftyexpiry copy.txt" | while read -r value; do
    # Create directory for each value
    dir_name="$value"
    mkdir -p "$dir_name"
    echo "Created directory: $dir_name"
done

