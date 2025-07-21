package com.voting;

import com.voting.ui.VotingSystemUI;

/**
 * Main application class for the Voting System Management
 */
public class VotingSystemApplication {
    
    public static void main(String[] args) {
        System.out.println("Starting Voting System Management Application...");
        
        try {
            VotingSystemUI ui = new VotingSystemUI();
            ui.start();
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}