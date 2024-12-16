package org.bankAccountManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bankAccountManager.DTO.request.BranchRequestDTO;
import org.bankAccountManager.DTO.response.BranchResponseDTO;
import org.bankAccountManager.mapper.DTOResponseMapper;
import org.bankAccountManager.service.implementations.BranchServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankAccountManager.mapper.DTORequestMapper.toBranch;
import static org.bankAccountManager.mapper.DTOResponseMapper.toBranchResponseDTO;

@Tag(name = "Branch Management", description = "Endpoints for managing branches")
@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchServiceImplementation branchService;

    public BranchController(BranchServiceImplementation branchService) {
        this.branchService = branchService;
    }

    @Operation(summary = "Create a new branch", description = "Add a new branch to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO branch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toBranchResponseDTO(branchService.createBranch(toBranch(branch))));
    }

    @Operation(summary = "Retrieve a branch by ID", description = "Get the details of a branch using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    @GetMapping("/id")
    public ResponseEntity<BranchResponseDTO> getBranchById(@RequestBody BranchRequestDTO branch) {
        return ResponseEntity.ok(toBranchResponseDTO(branchService.getBranchById(branch.getId())));
    }

    @Operation(summary = "Retrieve a branch by name", description = "Get the details of a branch using its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    @GetMapping("/name")
    public ResponseEntity<BranchResponseDTO> getBranchByName(@RequestBody BranchRequestDTO branch) {
        return ResponseEntity.ok(toBranchResponseDTO(branchService.getBranchByName(branch.getName())));
    }

    @Operation(summary = "Retrieve all branches", description = "Get the list of all branches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branches retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches().stream().map(DTOResponseMapper::toBranchResponseDTO).toList());
    }

    @Operation(summary = "Update a branch", description = "Update an existing branch with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    @PutMapping
    public ResponseEntity<BranchResponseDTO> updateBranch(@RequestBody BranchRequestDTO branch) {
        return ResponseEntity.ok(toBranchResponseDTO(branchService.updateBranch(toBranch(branch))));
    }

    @Operation(summary = "Delete a branch", description = "Delete a branch by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteBranch(@RequestBody BranchRequestDTO branch) {
        branchService.deleteBranch(branchService.getBranchById(branch.getId()));
        return ResponseEntity.noContent().build();
    }
}
