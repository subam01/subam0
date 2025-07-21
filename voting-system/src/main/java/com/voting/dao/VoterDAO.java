package com.voting.dao;

import com.voting.model.Voter;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Voter operations
 */
public interface VoterDAO {
    
    /**
     * Save a new voter or update existing voter
     * @param voter The voter to save
     * @return The saved voter
     */
    Voter save(Voter voter);
    
    /**
     * Find voter by ID
     * @param voterId The voter ID
     * @return Optional containing the voter if found
     */
    Optional<Voter> findById(String voterId);
    
    /**
     * Find voter by email
     * @param email The voter email
     * @return Optional containing the voter if found
     */
    Optional<Voter> findByEmail(String email);
    
    /**
     * Get all voters
     * @return List of all voters
     */
    List<Voter> findAll();
    
    /**
     * Get voters by status
     * @param status The voter status
     * @return List of voters with the specified status
     */
    List<Voter> findByStatus(Voter.VoterStatus status);
    
    /**
     * Delete voter by ID
     * @param voterId The voter ID
     * @return true if deleted successfully
     */
    boolean deleteById(String voterId);
    
    /**
     * Check if voter exists
     * @param voterId The voter ID
     * @return true if voter exists
     */
    boolean existsById(String voterId);
    
    /**
     * Update voter status
     * @param voterId The voter ID
     * @param status The new status
     * @return true if updated successfully
     */
    boolean updateStatus(String voterId, Voter.VoterStatus status);
    
    /**
     * Mark voter as having voted
     * @param voterId The voter ID
     * @return true if updated successfully
     */
    boolean markAsVoted(String voterId);
}