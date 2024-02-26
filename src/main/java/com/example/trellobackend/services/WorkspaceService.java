package com.example.trellobackend.services;

import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.WorkspaceRequest;

import java.util.Optional;

public interface WorkspaceService {
    Iterable<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Workspace save(Workspace workspace);
    void delete(Long id);
    Workspace createWorkspace(WorkspaceRequest workspaceRequest, String frontendURL);
}
