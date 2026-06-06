package com.wellbeing.service;

import com.wellbeing.model.User;

/**
 * Service interface for User-related operations.
 */
public interface UserService {
    
    /**
     * Retrieves the currently authenticated user from SecurityContextHolder.
     *
     * @return the authenticated User entity from database
     * @throws RuntimeException if user is not authenticated or not found
     */
    User getCurrentUser();
}
