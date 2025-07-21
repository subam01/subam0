package com.voting.util;

import com.voting.model.*;
import com.voting.service.VotingService;
import java.time.LocalDateTime;

/**
 * Utility class to initialize the system with sample data
 */
public class SampleDataInitializer {
    
    private final VotingService votingService;
    
    public SampleDataInitializer(VotingService votingService) {
        this.votingService = votingService;
    }
    
    /**
     * Initialize the system with sample data
     */
    public void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        // Register sample voters
        initializeVoters();
        
        // Add sample candidates
        initializeCandidates();
        
        // Create sample elections
        initializeElections();
        
        System.out.println("Sample data initialized successfully!");
    }
    
    private void initializeVoters() {
        try {
            votingService.registerVoter("John", "Doe", "john.doe@email.com", "123-456-7890");
            votingService.registerVoter("Jane", "Smith", "jane.smith@email.com", "123-456-7891");
            votingService.registerVoter("Mike", "Johnson", "mike.johnson@email.com", "123-456-7892");
            votingService.registerVoter("Sarah", "Williams", "sarah.williams@email.com", "123-456-7893");
            votingService.registerVoter("David", "Brown", "david.brown@email.com", "123-456-7894");
            
            System.out.println("Sample voters registered.");
        } catch (Exception e) {
            System.out.println("Error registering sample voters: " + e.getMessage());
        }
    }
    
    private void initializeCandidates() {
        try {
            // Presidential candidates
            Candidate candidate1 = votingService.addCandidate("Alice", "Anderson", "Democratic Party", "President");
            Candidate candidate2 = votingService.addCandidate("Bob", "Baker", "Republican Party", "President");
            Candidate candidate3 = votingService.addCandidate("Carol", "Carter", "Independent", "President");
            
            // Mayor candidates
            Candidate candidate4 = votingService.addCandidate("Daniel", "Davis", "Progressive Party", "Mayor");
            Candidate candidate5 = votingService.addCandidate("Emma", "Evans", "Conservative Party", "Mayor");
            
            System.out.println("Sample candidates added.");
        } catch (Exception e) {
            System.out.println("Error adding sample candidates: " + e.getMessage());
        }
    }
    
    private void initializeElections() {
        try {
            // Create a presidential election
            LocalDateTime presidentialStart = LocalDateTime.now().plusMinutes(1);
            LocalDateTime presidentialEnd = LocalDateTime.now().plusDays(7);
            
            Election presidentialElection = votingService.createElection(
                "Presidential Election 2024",
                "General election for President of the United States",
                presidentialStart,
                presidentialEnd
            );
            
            // Create a mayoral election
            LocalDateTime mayoralStart = LocalDateTime.now().plusMinutes(2);
            LocalDateTime mayoralEnd = LocalDateTime.now().plusDays(5);
            
            Election mayoralElection = votingService.createElection(
                "Mayoral Election 2024",
                "City mayoral election",
                mayoralStart,
                mayoralEnd
            );
            
            // Add candidates to elections (this would need to be done manually through the UI
            // or we'd need to modify the service to return candidate IDs)
            
            System.out.println("Sample elections created.");
            System.out.println("Presidential Election ID: " + presidentialElection.getElectionId());
            System.out.println("Mayoral Election ID: " + mayoralElection.getElectionId());
            
        } catch (Exception e) {
            System.out.println("Error creating sample elections: " + e.getMessage());
        }
    }
    
    /**
     * Display instructions for using the sample data
     */
    public void displaySampleDataInfo() {
        System.out.println("\n=== SAMPLE DATA INFORMATION ===");
        System.out.println("The system has been initialized with:");
        System.out.println("- 5 sample voters");
        System.out.println("- 5 sample candidates");
        System.out.println("- 2 sample elections");
        System.out.println();
        System.out.println("To use the sample data:");
        System.out.println("1. Go to Election Management -> View All Elections to see election IDs");
        System.out.println("2. Go to Candidate Management -> View All Candidates to see candidate IDs");
        System.out.println("3. Use 'Add Candidate to Election' to associate candidates with elections");
        System.out.println("4. Start elections to enable voting");
        System.out.println("5. Go to Voter Management -> View All Voters to see voter IDs for voting");
        System.out.println("=====================================\n");
    }
}