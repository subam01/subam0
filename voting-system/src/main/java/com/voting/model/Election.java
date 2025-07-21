package com.voting.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an election with candidates and voting period
 */
public class Election {
    private String electionId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Candidate> candidates;
    private ElectionStatus status;
    private int totalVotes;
    
    public enum ElectionStatus {
        SCHEDULED, ACTIVE, COMPLETED, CANCELLED
    }
    
    // Default constructor
    public Election() {
        this.candidates = new ArrayList<>();
        this.status = ElectionStatus.SCHEDULED;
        this.totalVotes = 0;
    }
    
    // Parameterized constructor
    public Election(String electionId, String title, String description, 
                   LocalDateTime startDate, LocalDateTime endDate) {
        this();
        this.electionId = electionId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getters and Setters
    public String getElectionId() {
        return electionId;
    }
    
    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public List<Candidate> getCandidates() {
        return new ArrayList<>(candidates);
    }
    
    public void setCandidates(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }
    
    public ElectionStatus getStatus() {
        return status;
    }
    
    public void setStatus(ElectionStatus status) {
        this.status = status;
    }
    
    public int getTotalVotes() {
        return totalVotes;
    }
    
    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
    
    // Business methods
    public void addCandidate(Candidate candidate) {
        if (candidate != null && !candidates.contains(candidate)) {
            candidates.add(candidate);
        }
    }
    
    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }
    
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return status == ElectionStatus.ACTIVE && 
               (now.isAfter(startDate) || now.isEqual(startDate)) && 
               now.isBefore(endDate);
    }
    
    public boolean isCompleted() {
        return status == ElectionStatus.COMPLETED || 
               LocalDateTime.now().isAfter(endDate);
    }
    
    public void incrementTotalVotes() {
        this.totalVotes++;
    }
    
    public Candidate getWinner() {
        if (!isCompleted() || candidates.isEmpty()) {
            return null;
        }
        
        return candidates.stream()
                .max((c1, c2) -> Integer.compare(c1.getVoteCount(), c2.getVoteCount()))
                .orElse(null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Election election = (Election) o;
        return Objects.equals(electionId, election.electionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(electionId);
    }
    
    @Override
    public String toString() {
        return "Election{" +
                "electionId='" + electionId + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", totalVotes=" + totalVotes +
                ", candidatesCount=" + candidates.size() +
                '}';
    }
}