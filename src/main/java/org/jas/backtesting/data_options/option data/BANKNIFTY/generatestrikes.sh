#!/bin/bash

# Function to process each value in the input file
process_value() {
    value=$1
    output_file=$2

    # Convert value to an integer base by dividing by 100 and multiplying back
    base_value=$(( (${value%.*} / 100) * 100 ))

    # Define P and C values
    p_value="${base_value}P"
    c_value="$((base_value + 100))C"

    # Check if P or C values are already in the output file
    if ! grep -q "$p_value" "$output_file"; then
        echo "$p_value" >> "$output_file"
    fi

    if ! grep -q "$c_value" "$output_file"; then
        echo "$c_value" >> "$output_file"
    fi
}

# Function to process each directory
process_directory() {
    dir=$1
    input_file="$dir$(basename "$dir").txt"
    output_file="$dir/output.txt"

    # Check if the input file exists
    if [ -f "$input_file" ]; then
        # Clear the output file
        > "$output_file"

        # Read the input file line by line
        while IFS= read -r line; do
            process_value "$line" "$output_file"
        done < "$input_file"
    else
        echo "Input file $input_file not found!"
    fi
}

# Traverse through all directories under the BANKNIFTY directory
parent_dir="$(pwd)"

for dir in "$parent_dir"/*/; do
    if [ -d "$dir" ]; then
        process_directory "$dir"
    fi
done

