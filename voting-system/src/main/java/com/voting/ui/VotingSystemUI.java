package com.voting.ui;

import com.voting.model.*;
import com.voting.service.VotingService;
import com.voting.util.SampleDataInitializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Console-based user interface for the Voting System
 */
public class VotingSystemUI {
    
    private final VotingService votingService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    private final SampleDataInitializer sampleDataInitializer;
    
    public VotingSystemUI() {
        this.votingService = new VotingService();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.sampleDataInitializer = new SampleDataInitializer(votingService);
    }
    
    public void start() {
        System.out.println("=================================");
        System.out.println("   VOTING SYSTEM MANAGEMENT");
        System.out.println("=================================");
        
        // Ask if user wants to load sample data
        System.out.print("Would you like to load sample data? (y/n): ");
        String loadSample = scanner.nextLine().trim().toLowerCase();
        if (loadSample.equals("y") || loadSample.equals("yes")) {
            sampleDataInitializer.initializeSampleData();
            sampleDataInitializer.displaySampleDataInfo();
        }
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1 -> handleVoterManagement();
                    case 2 -> handleElectionManagement();
                    case 3 -> handleCandidateManagement();
                    case 4 -> handleVoting();
                    case 5 -> handleResults();
                    case 6 -> displaySystemStatistics();
                    case 0 -> {
                        System.out.println("Thank you for using the Voting System!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Voter Management");
        System.out.println("2. Election Management");
        System.out.println("3. Candidate Management");
        System.out.println("4. Cast Vote");
        System.out.println("5. View Results");
        System.out.println("6. System Statistics");
        System.out.println("0. Exit");
        System.out.println("==================");
    }
    
    private void handleVoterManagement() {
        System.out.println("\n=== VOTER MANAGEMENT ===");
        System.out.println("1. Register New Voter");
        System.out.println("2. View All Voters");
        System.out.println("3. Search Voter");
        System.out.println("4. Update Voter Status");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1 -> registerVoter();
            case 2 -> displayAllVoters();
            case 3 -> searchVoter();
            case 4 -> updateVoterStatus();
            case 0 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void registerVoter() {
        System.out.println("\n=== REGISTER NEW VOTER ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();
        
        try {
            Voter voter = votingService.registerVoter(firstName, lastName, email, phoneNumber);
            System.out.println("Voter registered successfully!");
            System.out.println("Voter ID: " + voter.getVoterId());
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    
    private void displayAllVoters() {
        System.out.println("\n=== ALL VOTERS ===");
        List<Voter> voters = votingService.getAllVoters();
        
        if (voters.isEmpty()) {
            System.out.println("No voters registered.");
            return;
        }
        
        System.out.printf("%-15s %-20s %-30s %-10s %-10s%n", 
                         "Voter ID", "Name", "Email", "Voted", "Status");
        System.out.println("-".repeat(85));
        
        for (Voter voter : voters) {
            System.out.printf("%-15s %-20s %-30s %-10s %-10s%n",
                             voter.getVoterId(),
                             voter.getFullName(),
                             voter.getEmail(),
                             voter.hasVoted() ? "Yes" : "No",
                             voter.getStatus());
        }
    }
    
    private void searchVoter() {
        System.out.print("Enter Voter ID: ");
        String voterId = scanner.nextLine().trim();
        
        Optional<Voter> voterOpt = votingService.getVoter(voterId);
        if (voterOpt.isPresent()) {
            Voter voter = voterOpt.get();
            System.out.println("\n=== VOTER DETAILS ===");
            System.out.println("ID: " + voter.getVoterId());
            System.out.println("Name: " + voter.getFullName());
            System.out.println("Email: " + voter.getEmail());
            System.out.println("Phone: " + voter.getPhoneNumber());
            System.out.println("Status: " + voter.getStatus());
            System.out.println("Has Voted: " + (voter.hasVoted() ? "Yes" : "No"));
            System.out.println("Registration Date: " + voter.getRegistrationDate());
        } else {
            System.out.println("Voter not found.");
        }
    }
    
    private void updateVoterStatus() {
        System.out.print("Enter Voter ID: ");
        String voterId = scanner.nextLine().trim();
        
        System.out.println("Select new status:");
        System.out.println("1. ACTIVE");
        System.out.println("2. INACTIVE");
        System.out.println("3. SUSPENDED");
        
        int statusChoice = getIntInput("Enter choice: ");
        Voter.VoterStatus status = switch (statusChoice) {
            case 1 -> Voter.VoterStatus.ACTIVE;
            case 2 -> Voter.VoterStatus.INACTIVE;
            case 3 -> Voter.VoterStatus.SUSPENDED;
            default -> null;
        };
        
        if (status != null) {
            boolean updated = votingService.updateVoterStatus(voterId, status);
            if (updated) {
                System.out.println("Voter status updated successfully.");
            } else {
                System.out.println("Failed to update voter status.");
            }
        } else {
            System.out.println("Invalid status choice.");
        }
    }
    
    private void handleElectionManagement() {
        System.out.println("\n=== ELECTION MANAGEMENT ===");
        System.out.println("1. Create New Election");
        System.out.println("2. View All Elections");
        System.out.println("3. Start Election");
        System.out.println("4. End Election");
        System.out.println("5. Add Candidate to Election");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1 -> createElection();
            case 2 -> displayAllElections();
            case 3 -> startElection();
            case 4 -> endElection();
            case 5 -> addCandidateToElection();
            case 0 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void createElection() {
        System.out.println("\n=== CREATE NEW ELECTION ===");
        System.out.print("Election Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        
        LocalDateTime startDate = getDateTimeInput("Start Date (yyyy-MM-dd HH:mm): ");
        LocalDateTime endDate = getDateTimeInput("End Date (yyyy-MM-dd HH:mm): ");
        
        try {
            Election election = votingService.createElection(title, description, startDate, endDate);
            System.out.println("Election created successfully!");
            System.out.println("Election ID: " + election.getElectionId());
        } catch (Exception e) {
            System.out.println("Election creation failed: " + e.getMessage());
        }
    }
    
    private void displayAllElections() {
        System.out.println("\n=== ALL ELECTIONS ===");
        List<Election> elections = votingService.getAllElections();
        
        if (elections.isEmpty()) {
            System.out.println("No elections found.");
            return;
        }
        
        System.out.printf("%-15s %-30s %-15s %-10s %-10s%n", 
                         "Election ID", "Title", "Status", "Candidates", "Votes");
        System.out.println("-".repeat(80));
        
        for (Election election : elections) {
            System.out.printf("%-15s %-30s %-15s %-10d %-10d%n",
                             election.getElectionId(),
                             election.getTitle(),
                             election.getStatus(),
                             election.getCandidates().size(),
                             election.getTotalVotes());
        }
    }
    
    private void startElection() {
        System.out.print("Enter Election ID to start: ");
        String electionId = scanner.nextLine().trim();
        
        boolean started = votingService.startElection(electionId);
        if (started) {
            System.out.println("Election started successfully!");
        } else {
            System.out.println("Failed to start election. Check if election exists and is scheduled.");
        }
    }
    
    private void endElection() {
        System.out.print("Enter Election ID to end: ");
        String electionId = scanner.nextLine().trim();
        
        boolean ended = votingService.endElection(electionId);
        if (ended) {
            System.out.println("Election ended successfully!");
        } else {
            System.out.println("Failed to end election. Check if election exists and is active.");
        }
    }
    
    private void handleCandidateManagement() {
        System.out.println("\n=== CANDIDATE MANAGEMENT ===");
        System.out.println("1. Add New Candidate");
        System.out.println("2. View All Candidates");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1 -> addCandidate();
            case 2 -> displayAllCandidates();
            case 0 -> { /* Return to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void addCandidate() {
        System.out.println("\n=== ADD NEW CANDIDATE ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Party: ");
        String party = scanner.nextLine().trim();
        
        System.out.print("Position: ");
        String position = scanner.nextLine().trim();
        
        Candidate candidate = votingService.addCandidate(firstName, lastName, party, position);
        System.out.println("Candidate added successfully!");
        System.out.println("Candidate ID: " + candidate.getCandidateId());
    }
    
    private void displayAllCandidates() {
        System.out.println("\n=== ALL CANDIDATES ===");
        List<Candidate> candidates = votingService.getAllCandidates();
        
        if (candidates.isEmpty()) {
            System.out.println("No candidates found.");
            return;
        }
        
        System.out.printf("%-15s %-20s %-15s %-15s %-10s%n", 
                         "Candidate ID", "Name", "Party", "Position", "Votes");
        System.out.println("-".repeat(75));
        
        for (Candidate candidate : candidates) {
            System.out.printf("%-15s %-20s %-15s %-15s %-10d%n",
                             candidate.getCandidateId(),
                             candidate.getFullName(),
                             candidate.getParty(),
                             candidate.getPosition(),
                             candidate.getVoteCount());
        }
    }
    
    private void addCandidateToElection() {
        System.out.print("Enter Election ID: ");
        String electionId = scanner.nextLine().trim();
        
        System.out.print("Enter Candidate ID: ");
        String candidateId = scanner.nextLine().trim();
        
        boolean added = votingService.addCandidateToElection(electionId, candidateId);
        if (added) {
            System.out.println("Candidate added to election successfully!");
        } else {
            System.out.println("Failed to add candidate to election.");
        }
    }
    
    private void handleVoting() {
        System.out.println("\n=== CAST VOTE ===");
        
        // Display active elections
        List<Election> activeElections = votingService.getActiveElections();
        if (activeElections.isEmpty()) {
            System.out.println("No active elections available for voting.");
            return;
        }
        
        System.out.println("Active Elections:");
        for (Election election : activeElections) {
            System.out.println("ID: " + election.getElectionId() + " - " + election.getTitle());
        }
        
        System.out.print("Enter Election ID: ");
        String electionId = scanner.nextLine().trim();
        
        // Display candidates for the election
        Optional<Election> electionOpt = votingService.getElection(electionId);
        if (electionOpt.isEmpty()) {
            System.out.println("Election not found.");
            return;
        }
        
        Election election = electionOpt.get();
        List<Candidate> candidates = election.getCandidates();
        if (candidates.isEmpty()) {
            System.out.println("No candidates in this election.");
            return;
        }
        
        System.out.println("\nCandidates:");
        for (Candidate candidate : candidates) {
            System.out.println("ID: " + candidate.getCandidateId() + 
                             " - " + candidate.getFullName() + 
                             " (" + candidate.getParty() + ")");
        }
        
        System.out.print("Enter Voter ID: ");
        String voterId = scanner.nextLine().trim();
        
        System.out.print("Enter Candidate ID: ");
        String candidateId = scanner.nextLine().trim();
        
        try {
            Vote vote = votingService.castVote(voterId, candidateId, electionId);
            System.out.println("Vote cast successfully!");
            System.out.println("Vote ID: " + vote.getVoteId());
        } catch (Exception e) {
            System.out.println("Voting failed: " + e.getMessage());
        }
    }
    
    private void handleResults() {
        System.out.println("\n=== ELECTION RESULTS ===");
        System.out.print("Enter Election ID: ");
        String electionId = scanner.nextLine().trim();
        
        Map<String, Integer> results = votingService.getElectionResults(electionId);
        if (results.isEmpty()) {
            System.out.println("No results found for this election.");
            return;
        }
        
        System.out.println("\nResults:");
        System.out.printf("%-30s %-10s%n", "Candidate", "Votes");
        System.out.println("-".repeat(40));
        
        results.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("%-30s %-10d%n", 
                        entry.getKey(), entry.getValue()));
        
        Optional<Candidate> winner = votingService.getElectionWinner(electionId);
        if (winner.isPresent()) {
            System.out.println("\nWinner: " + winner.get().getFullName());
        }
        
        int totalVotes = votingService.getTotalVotesInElection(electionId);
        double turnout = votingService.getVoterTurnout(electionId);
        System.out.println("Total Votes: " + totalVotes);
        System.out.println("Voter Turnout: " + String.format("%.2f%%", turnout));
    }
    
    private void displaySystemStatistics() {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        Map<String, Object> stats = votingService.getSystemStatistics();
        
        System.out.println("Total Voters: " + stats.get("totalVoters"));
        System.out.println("Total Elections: " + stats.get("totalElections"));
        System.out.println("Total Candidates: " + stats.get("totalCandidates"));
        System.out.println("Total Votes Cast: " + stats.get("totalVotes"));
        System.out.println("Active Elections: " + stats.get("activeElections"));
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private LocalDateTime getDateTimeInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalDateTime.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in format: yyyy-MM-dd HH:mm");
            }
        }
    }
}