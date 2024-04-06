check_single_spaces() {
    file="$1"
    while IFS= read -r line; do
        # Replace multiple spaces with a single space
        corrected_line=$(echo "$line" | tr -s '[:space:]' ' ')
        if [[ "$line" != "$corrected_line" ]]; then
            echo "File $file has multiple spaces between words on line: $line"
        fi
    done < "$file"
}

# Main script

# Loop through all files in the directory
for file in *; do
    if [ -f "$file" ]; then
        check_empty_line "$file"
        check_single_spaces "$file"
        # Add an empty line at the end of the file if it doesn't exist
        if [[ $(tail -c1 "$file" | wc -l) -eq 0 ]]; then
            echo >> "$file"
            echo "An empty line was added at the end of $file"
        fi
    fi
done