package com.voting.model;

import java.util.Objects;

/**
 * Represents a candidate in an election
 */
public class Candidate {
    private String candidateId;
    private String firstName;
    private String lastName;
    private String party;
    private String position;
    private String biography;
    private int voteCount;
    private boolean isActive;
    
    // Default constructor
    public Candidate() {
        this.voteCount = 0;
        this.isActive = true;
    }
    
    // Parameterized constructor
    public Candidate(String candidateId, String firstName, String lastName, String party, String position) {
        this();
        this.candidateId = candidateId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
        this.position = position;
    }
    
    // Getters and Setters
    public String getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getParty() {
        return party;
    }
    
    public void setParty(String party) {
        this.party = party;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getBiography() {
        return biography;
    }
    
    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public int getVoteCount() {
        return voteCount;
    }
    
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void incrementVoteCount() {
        this.voteCount++;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(candidateId, candidate.candidateId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(candidateId);
    }
    
    @Override
    public String toString() {
        return "Candidate{" +
                "candidateId='" + candidateId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", party='" + party + '\'' +
                ", position='" + position + '\'' +
                ", voteCount=" + voteCount +
                ", isActive=" + isActive +
                '}';
    }
}