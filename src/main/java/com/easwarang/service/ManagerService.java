package com.easwarang.service;

import com.easwarang.dto.request.ManagerRequestDTO;
import com.easwarang.dto.response.ManagerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ManagerService {
    Optional<List<ManagerResponseDTO>> getAllManagers();
    Optional<ManagerResponseDTO> getManagerById(Long managerId);
    void saveManager(ManagerRequestDTO managerRequestDTO);
    void updateManager(Long managerId, ManagerRequestDTO managerRequestDTO);
    void deleteManager(Long managerId);
}
