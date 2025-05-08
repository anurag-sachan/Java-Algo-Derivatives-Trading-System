#!/bin/bash

# Input file
input_file="01DEC22.txt"

# Function to process each value
process_value() {
    value=$1

    # Convert value to an integer base by dividing by 100 and multiplying back
    base_value=$(( (${value%.*} / 100) * 100 ))

    # Define P and C values
    p_value="${base_value}P"
    c_value="$((base_value + 100))C"

    # Check if P or C values are already in the output list
    if ! grep -q "$p_value" output.txt; then
        echo "$p_value" >> output.txt
    fi

    if ! grep -q "$c_value" output.txt; then
        echo "$c_value" >> output.txt
    fi
}

# Clear the output file
> output.txt

# Read the input file line by line
while IFS= read -r line; do
    process_value "$line"
done < "$input_file"

# Display the output
cat output.txt

