package com.voting.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a vote cast by a voter for a candidate in an election
 */
public class Vote {
    private String voteId;
    private String voterId;
    private String candidateId;
    private String electionId;
    private LocalDateTime timestamp;
    private boolean isValid;
    
    // Default constructor
    public Vote() {
        this.timestamp = LocalDateTime.now();
        this.isValid = true;
    }
    
    // Parameterized constructor
    public Vote(String voteId, String voterId, String candidateId, String electionId) {
        this();
        this.voteId = voteId;
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.electionId = electionId;
    }
    
    // Getters and Setters
    public String getVoteId() {
        return voteId;
    }
    
    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }
    
    public String getVoterId() {
        return voterId;
    }
    
    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }
    
    public String getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
    
    public String getElectionId() {
        return electionId;
    }
    
    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public void setValid(boolean valid) {
        isValid = valid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(voteId, vote.voteId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(voteId);
    }
    
    @Override
    public String toString() {
        return "Vote{" +
                "voteId='" + voteId + '\'' +
                ", voterId='" + voterId + '\'' +
                ", candidateId='" + candidateId + '\'' +
                ", electionId='" + electionId + '\'' +
                ", timestamp=" + timestamp +
                ", isValid=" + isValid +
                '}';
    }
}