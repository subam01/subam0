# Voting System Management

A comprehensive Java-based voting system that allows for the management of voters, candidates, elections, and the voting process. The system provides a console-based user interface for easy interaction and includes features for election management, voter registration, and result tabulation.

## Features

### Core Functionality
- **Voter Management**: Register voters, view voter information, update voter status
- **Candidate Management**: Add candidates, view candidate information
- **Election Management**: Create elections, start/stop elections, manage election schedules
- **Voting Process**: Cast votes with validation and security checks
- **Results & Analytics**: View election results, determine winners, calculate voter turnout

### Key Features
- **Duplicate Prevention**: Prevents duplicate voter registration and double voting
- **Election Status Management**: Elections can be scheduled, active, completed, or cancelled
- **Voter Status Tracking**: Track voter status (active, inactive, suspended)
- **Real-time Results**: View election results and statistics
- **Data Validation**: Comprehensive input validation and error handling
- **Sample Data**: Optional sample data initialization for testing

## Project Structure

```
voting-system/
├── src/
│   ├── main/java/com/voting/
│   │   ├── model/           # Data models (Voter, Candidate, Election, Vote)
│   │   ├── dao/             # Data Access Objects and implementations
│   │   ├── service/         # Business logic layer
│   │   ├── ui/              # User interface layer
│   │   ├── util/            # Utility classes
│   │   └── VotingSystemApplication.java  # Main application entry point
│   └── test/java/com/voting/
│       └── VotingSystemTest.java         # Test class
└── README.md
```

## Getting Started

### Prerequisites
- Java 21 or higher
- Terminal/Command Prompt

### Installation & Setup

1. **Navigate to the project directory:**
   ```bash
   cd voting-system
   ```

2. **Compile the Java files:**
   ```bash
   javac -d out -cp src/main/java src/main/java/com/voting/*.java src/main/java/com/voting/*/*.java
   ```

3. **Run the application:**
   ```bash
   java -cp out com.voting.VotingSystemApplication
   ```

### Alternative: Direct compilation and execution
```bash
# Compile all Java files
find src/main/java -name "*.java" -exec javac -cp src/main/java {} +

# Run the main application
java -cp src/main/java com.voting.VotingSystemApplication
```

## Usage Guide

### Starting the Application

When you start the application, you'll be prompted to load sample data:
- Choose 'y' to load sample data (recommended for first-time users)
- Choose 'n' to start with an empty system

### Main Menu Options

1. **Voter Management**
   - Register new voters
   - View all registered voters
   - Search for specific voters
   - Update voter status (active/inactive/suspended)

2. **Election Management**
   - Create new elections with start/end dates
   - View all elections and their status
   - Start scheduled elections
   - End active elections
   - Add candidates to elections

3. **Candidate Management**
   - Add new candidates with party affiliation and position
   - View all candidates and their vote counts

4. **Cast Vote**
   - View active elections
   - Cast votes for registered voters
   - Automatic validation prevents duplicate voting

5. **View Results**
   - View election results sorted by vote count
   - See election winner
   - View voter turnout statistics

6. **System Statistics**
   - Overview of total voters, elections, candidates, and votes

### Sample Workflow

1. **Load sample data** when prompted (or create your own data)
2. **View elections** to see available elections
3. **View candidates** to see available candidates
4. **Add candidates to elections** using their IDs
5. **Start an election** to enable voting
6. **Cast votes** using voter IDs and candidate IDs
7. **View results** to see election outcomes

## Data Models

### Voter
- Voter ID (auto-generated)
- Personal information (name, email, phone)
- Registration date
- Voting status (has voted or not)
- Account status (active, inactive, suspended)

### Candidate
- Candidate ID (auto-generated)
- Personal information (name)
- Party affiliation
- Position running for
- Vote count
- Active status

### Election
- Election ID (auto-generated)
- Title and description
- Start and end dates
- List of participating candidates
- Election status (scheduled, active, completed, cancelled)
- Total vote count

### Vote
- Vote ID (auto-generated)
- Voter ID reference
- Candidate ID reference
- Election ID reference
- Timestamp
- Validity status

## Testing

Run the test class to verify system functionality:

```bash
# Compile test class
javac -cp src/main/java:src/test/java src/test/java/com/voting/VotingSystemTest.java

# Run tests
java -cp src/main/java:src/test/java com.voting.VotingSystemTest
```

The test class verifies:
- Voter registration and duplicate prevention
- Candidate management
- Election creation and management
- Voting process and validation
- Results calculation

## Architecture

### Design Patterns Used
- **Data Access Object (DAO)**: Separates data access logic from business logic
- **Service Layer**: Encapsulates business logic and rules
- **Model-View-Controller (MVC)**: Separates concerns between data, logic, and presentation

### Key Components
- **Models**: Plain Java objects representing data entities
- **DAOs**: Handle data persistence (currently in-memory storage)
- **Services**: Implement business logic and coordinate between layers
- **UI**: Console-based interface for user interaction

## Features in Detail

### Security & Validation
- Email uniqueness validation for voter registration
- Prevention of double voting by the same voter
- Election date validation (start date before end date)
- Voter status validation before allowing votes
- Election status validation for voting eligibility

### Data Management
- Thread-safe in-memory storage using ConcurrentHashMap
- Automatic ID generation for all entities
- Referential integrity between votes, voters, candidates, and elections

### User Experience
- Menu-driven interface with clear options
- Input validation with error messages
- Formatted output tables for easy reading
- Optional sample data for quick testing

## Future Enhancements

Potential improvements for the system:
- Database persistence (MySQL, PostgreSQL)
- Web-based user interface
- Authentication and authorization
- Audit logging
- Encrypted vote storage
- Multi-language support
- Report generation (PDF/Excel)
- Email notifications
- Batch voter import
- Advanced analytics and reporting

## Contributing

To contribute to this project:
1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is created for educational purposes and is available for use and modification.

## Contact

For questions or suggestions about this voting system, please create an issue in the project repository.