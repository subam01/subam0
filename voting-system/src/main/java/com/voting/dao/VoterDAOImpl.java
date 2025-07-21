package com.voting.dao;

import com.voting.model.Voter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of VoterDAO
 */
public class VoterDAOImpl implements VoterDAO {
    
    private final Map<String, Voter> voters = new ConcurrentHashMap<>();
    
    @Override
    public Voter save(Voter voter) {
        if (voter == null || voter.getVoterId() == null) {
            throw new IllegalArgumentException("Voter and voter ID cannot be null");
        }
        voters.put(voter.getVoterId(), voter);
        return voter;
    }
    
    @Override
    public Optional<Voter> findById(String voterId) {
        if (voterId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(voters.get(voterId));
    }
    
    @Override
    public Optional<Voter> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return voters.values().stream()
                .filter(voter -> email.equals(voter.getEmail()))
                .findFirst();
    }
    
    @Override
    public List<Voter> findAll() {
        return new ArrayList<>(voters.values());
    }
    
    @Override
    public List<Voter> findByStatus(Voter.VoterStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return voters.values().stream()
                .filter(voter -> status.equals(voter.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean deleteById(String voterId) {
        if (voterId == null) {
            return false;
        }
        return voters.remove(voterId) != null;
    }
    
    @Override
    public boolean existsById(String voterId) {
        return voterId != null && voters.containsKey(voterId);
    }
    
    @Override
    public boolean updateStatus(String voterId, Voter.VoterStatus status) {
        if (voterId == null || status == null) {
            return false;
        }
        Voter voter = voters.get(voterId);
        if (voter != null) {
            voter.setStatus(status);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean markAsVoted(String voterId) {
        if (voterId == null) {
            return false;
        }
        Voter voter = voters.get(voterId);
        if (voter != null) {
            voter.setHasVoted(true);
            return true;
        }
        return false;
    }
    
    /**
     * Get the total number of voters
     * @return The count of voters
     */
    public int getVoterCount() {
        return voters.size();
    }
    
    /**
     * Clear all voters (useful for testing)
     */
    public void clear() {
        voters.clear();
    }
}