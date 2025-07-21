#!/bin/bash

# Voting System Build Script

echo "Building Voting System..."

# Create output directory
mkdir -p out

# Compile all Java source files
echo "Compiling source files..."
find src/main/java -name "*.java" -exec javac -d out -cp src/main/java {} +

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    
    # Check if user wants to run tests
    if [ "$1" = "test" ]; then
        echo "Compiling and running tests..."
        javac -d out -cp out:src/test/java src/test/java/com/voting/VotingSystemTest.java
        java -cp out com.voting.VotingSystemTest
    elif [ "$1" = "run" ]; then
        echo "Starting Voting System Application..."
        java -cp out com.voting.VotingSystemApplication
    else
        echo ""
        echo "Build complete! Use one of the following commands to run:"
        echo "  ./build.sh run   - Start the voting system application"
        echo "  ./build.sh test  - Run the test suite"
        echo "  java -cp out com.voting.VotingSystemApplication  - Run directly"
    fi
else
    echo "✗ Compilation failed!"
    exit 1
fi