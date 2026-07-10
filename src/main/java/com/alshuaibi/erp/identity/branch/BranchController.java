package com.alshuaibi.erp.identity.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches() {
        List<BranchResponse> branches = branchRepository.findAll()
                .stream()
                .map(branch -> BranchResponse.builder()
                        .id(branch.getId())
                        .code(branch.getCode())
                        .nameAr(branch.getNameAr())
                        .nameEn(branch.getNameEn())
                        .phone(branch.getPhone())
                        .address(branch.getAddress())
                        .active(branch.getActive())
                        .build())
                .toList();

        return ResponseEntity.ok(branches);
    }
}