package com.voting;

import com.voting.model.*;
import com.voting.service.VotingService;
import java.time.LocalDateTime;

/**
 * Simple test class for the Voting System
 */
public class VotingSystemTest {
    
    public static void main(String[] args) {
        System.out.println("Running Voting System Tests...");
        
        VotingService votingService = new VotingService();
        
        try {
            // Test voter registration
            testVoterRegistration(votingService);
            
            // Test candidate management
            testCandidateManagement(votingService);
            
            // Test election management
            testElectionManagement(votingService);
            
            // Test voting process
            testVotingProcess(votingService);
            
            System.out.println("\nAll tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testVoterRegistration(VotingService votingService) {
        System.out.println("\n=== Testing Voter Registration ===");
        
        Voter voter1 = votingService.registerVoter("Test", "User1", "test1@example.com", "123-456-7890");
        Voter voter2 = votingService.registerVoter("Test", "User2", "test2@example.com", "123-456-7891");
        
        System.out.println("Registered voter 1: " + voter1.getVoterId());
        System.out.println("Registered voter 2: " + voter2.getVoterId());
        
        // Test duplicate email
        try {
            votingService.registerVoter("Test", "User3", "test1@example.com", "123-456-7892");
            System.out.println("ERROR: Should not allow duplicate email");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly prevented duplicate email registration");
        }
        
        System.out.println("Total voters: " + votingService.getAllVoters().size());
    }
    
    private static void testCandidateManagement(VotingService votingService) {
        System.out.println("\n=== Testing Candidate Management ===");
        
        Candidate candidate1 = votingService.addCandidate("John", "Smith", "Party A", "President");
        Candidate candidate2 = votingService.addCandidate("Jane", "Doe", "Party B", "President");
        
        System.out.println("Added candidate 1: " + candidate1.getCandidateId());
        System.out.println("Added candidate 2: " + candidate2.getCandidateId());
        
        System.out.println("Total candidates: " + votingService.getAllCandidates().size());
    }
    
    private static void testElectionManagement(VotingService votingService) {
        System.out.println("\n=== Testing Election Management ===");
        
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        
        Election election = votingService.createElection(
            "Test Election", 
            "A test election", 
            startDate, 
            endDate
        );
        
        System.out.println("Created election: " + election.getElectionId());
        
        // Add candidates to election
        String candidateId1 = votingService.getAllCandidates().get(0).getCandidateId();
        String candidateId2 = votingService.getAllCandidates().get(1).getCandidateId();
        
        boolean added1 = votingService.addCandidateToElection(election.getElectionId(), candidateId1);
        boolean added2 = votingService.addCandidateToElection(election.getElectionId(), candidateId2);
        
        System.out.println("Added candidate 1 to election: " + added1);
        System.out.println("Added candidate 2 to election: " + added2);
        
        // Start election
        boolean started = votingService.startElection(election.getElectionId());
        System.out.println("Started election: " + started);
        
        System.out.println("Total elections: " + votingService.getAllElections().size());
    }
    
    private static void testVotingProcess(VotingService votingService) {
        System.out.println("\n=== Testing Voting Process ===");
        
        // Get test data
        String voterId = votingService.getAllVoters().get(0).getVoterId();
        String electionId = votingService.getAllElections().get(0).getElectionId();
        String candidateId = votingService.getAllCandidates().get(0).getCandidateId();
        
        System.out.println("Attempting to cast vote...");
        System.out.println("Voter: " + voterId);
        System.out.println("Election: " + electionId);
        System.out.println("Candidate: " + candidateId);
        
        try {
            Vote vote = votingService.castVote(voterId, candidateId, electionId);
            System.out.println("✓ Vote cast successfully: " + vote.getVoteId());
            
            // Try to vote again (should fail)
            try {
                votingService.castVote(voterId, candidateId, electionId);
                System.out.println("ERROR: Should not allow double voting");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Correctly prevented double voting");
            }
            
        } catch (Exception e) {
            System.out.println("Voting failed: " + e.getMessage());
        }
        
        // Test results
        System.out.println("\n=== Testing Results ===");
        var results = votingService.getElectionResults(electionId);
        System.out.println("Election results: " + results);
        
        var winner = votingService.getElectionWinner(electionId);
        if (winner.isPresent()) {
            System.out.println("Current leader: " + winner.get().getFullName());
        }
        
        double turnout = votingService.getVoterTurnout(electionId);
        System.out.println("Voter turnout: " + String.format("%.2f%%", turnout));
    }
}