package com.voting.service;

import com.voting.dao.VoterDAO;
import com.voting.dao.VoterDAOImpl;
import com.voting.model.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Main service class for managing voting operations
 */
public class VotingService {
    
    private final VoterDAO voterDAO;
    private final Map<String, Election> elections;
    private final Map<String, Candidate> candidates;
    private final Map<String, Vote> votes;
    
    public VotingService() {
        this.voterDAO = new VoterDAOImpl();
        this.elections = new ConcurrentHashMap<>();
        this.candidates = new ConcurrentHashMap<>();
        this.votes = new ConcurrentHashMap<>();
    }
    
    // Voter Management
    public Voter registerVoter(String firstName, String lastName, String email, String phoneNumber) {
        // Check if voter already exists by email
        Optional<Voter> existingVoter = voterDAO.findByEmail(email);
        if (existingVoter.isPresent()) {
            throw new IllegalArgumentException("Voter with email " + email + " already exists");
        }
        
        String voterId = generateVoterId();
        Voter voter = new Voter(voterId, firstName, lastName, email, phoneNumber);
        return voterDAO.save(voter);
    }
    
    public Optional<Voter> getVoter(String voterId) {
        return voterDAO.findById(voterId);
    }
    
    public List<Voter> getAllVoters() {
        return voterDAO.findAll();
    }
    
    public boolean updateVoterStatus(String voterId, Voter.VoterStatus status) {
        return voterDAO.updateStatus(voterId, status);
    }
    
    // Election Management
    public Election createElection(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        String electionId = generateElectionId();
        Election election = new Election(electionId, title, description, startDate, endDate);
        elections.put(electionId, election);
        return election;
    }
    
    public Optional<Election> getElection(String electionId) {
        return Optional.ofNullable(elections.get(electionId));
    }
    
    public List<Election> getAllElections() {
        return new ArrayList<>(elections.values());
    }
    
    public List<Election> getActiveElections() {
        return elections.values().stream()
                .filter(Election::isActive)
                .collect(Collectors.toList());
    }
    
    public boolean startElection(String electionId) {
        Election election = elections.get(electionId);
        if (election != null && election.getStatus() == Election.ElectionStatus.SCHEDULED) {
            election.setStatus(Election.ElectionStatus.ACTIVE);
            return true;
        }
        return false;
    }
    
    public boolean endElection(String electionId) {
        Election election = elections.get(electionId);
        if (election != null && election.getStatus() == Election.ElectionStatus.ACTIVE) {
            election.setStatus(Election.ElectionStatus.COMPLETED);
            return true;
        }
        return false;
    }
    
    // Candidate Management
    public Candidate addCandidate(String firstName, String lastName, String party, String position) {
        String candidateId = generateCandidateId();
        Candidate candidate = new Candidate(candidateId, firstName, lastName, party, position);
        candidates.put(candidateId, candidate);
        return candidate;
    }
    
    public boolean addCandidateToElection(String electionId, String candidateId) {
        Election election = elections.get(electionId);
        Candidate candidate = candidates.get(candidateId);
        
        if (election != null && candidate != null && 
            election.getStatus() == Election.ElectionStatus.SCHEDULED) {
            election.addCandidate(candidate);
            return true;
        }
        return false;
    }
    
    public Optional<Candidate> getCandidate(String candidateId) {
        return Optional.ofNullable(candidates.get(candidateId));
    }
    
    public List<Candidate> getAllCandidates() {
        return new ArrayList<>(candidates.values());
    }
    
    // Voting Operations
    public Vote castVote(String voterId, String candidateId, String electionId) {
        // Validate voter
        Optional<Voter> voterOpt = voterDAO.findById(voterId);
        if (voterOpt.isEmpty()) {
            throw new IllegalArgumentException("Voter not found");
        }
        
        Voter voter = voterOpt.get();
        if (voter.getStatus() != Voter.VoterStatus.ACTIVE) {
            throw new IllegalArgumentException("Voter is not active");
        }
        
        if (voter.hasVoted()) {
            throw new IllegalArgumentException("Voter has already voted");
        }
        
        // Validate election
        Election election = elections.get(electionId);
        if (election == null) {
            throw new IllegalArgumentException("Election not found");
        }
        
        if (!election.isActive()) {
            throw new IllegalArgumentException("Election is not active");
        }
        
        // Validate candidate
        Candidate candidate = candidates.get(candidateId);
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate not found");
        }
        
        if (!election.getCandidates().contains(candidate)) {
            throw new IllegalArgumentException("Candidate is not part of this election");
        }
        
        // Cast vote
        String voteId = generateVoteId();
        Vote vote = new Vote(voteId, voterId, candidateId, electionId);
        votes.put(voteId, vote);
        
        // Update counts
        candidate.incrementVoteCount();
        election.incrementTotalVotes();
        voterDAO.markAsVoted(voterId);
        
        return vote;
    }
    
    // Results and Statistics
    public Map<String, Integer> getElectionResults(String electionId) {
        Election election = elections.get(electionId);
        if (election == null) {
            return new HashMap<>();
        }
        
        return election.getCandidates().stream()
                .collect(Collectors.toMap(
                    Candidate::getFullName,
                    Candidate::getVoteCount
                ));
    }
    
    public Optional<Candidate> getElectionWinner(String electionId) {
        Election election = elections.get(electionId);
        return election != null ? Optional.ofNullable(election.getWinner()) : Optional.empty();
    }
    
    public int getTotalVotesInElection(String electionId) {
        Election election = elections.get(electionId);
        return election != null ? election.getTotalVotes() : 0;
    }
    
    public double getVoterTurnout(String electionId) {
        int totalVoters = voterDAO.findAll().size();
        int totalVotes = getTotalVotesInElection(electionId);
        return totalVoters > 0 ? (double) totalVotes / totalVoters * 100 : 0.0;
    }
    
    // Utility methods
    private String generateVoterId() {
        return "V" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    private String generateElectionId() {
        return "E" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    private String generateCandidateId() {
        return "C" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    private String generateVoteId() {
        return "VOTE" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    // System Statistics
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVoters", voterDAO.findAll().size());
        stats.put("totalElections", elections.size());
        stats.put("totalCandidates", candidates.size());
        stats.put("totalVotes", votes.size());
        stats.put("activeElections", getActiveElections().size());
        return stats;
    }
}