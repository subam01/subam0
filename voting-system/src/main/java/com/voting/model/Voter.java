package com.voting.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a voter in the voting system
 */
public class Voter {
    private String voterId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private boolean hasVoted;
    private VoterStatus status;
    
    public enum VoterStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
    
    // Default constructor
    public Voter() {
        this.registrationDate = LocalDateTime.now();
        this.hasVoted = false;
        this.status = VoterStatus.ACTIVE;
    }
    
    // Parameterized constructor
    public Voter(String voterId, String firstName, String lastName, String email, String phoneNumber) {
        this();
        this.voterId = voterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getVoterId() {
        return voterId;
    }
    
    public void setVoterId(String voterId) {
        this.voterId = voterId;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public boolean hasVoted() {
        return hasVoted;
    }
    
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
    
    public VoterStatus getStatus() {
        return status;
    }
    
    public void setStatus(VoterStatus status) {
        this.status = status;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(voterId, voter.voterId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(voterId);
    }
    
    @Override
    public String toString() {
        return "Voter{" +
                "voterId='" + voterId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", hasVoted=" + hasVoted +
                ", status=" + status +
                '}';
    }
}