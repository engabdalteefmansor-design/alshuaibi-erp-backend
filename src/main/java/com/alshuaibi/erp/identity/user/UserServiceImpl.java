package com.alshuaibi.erp.identity.user;

import com.alshuaibi.erp.common.response.PagedResponse;
import com.alshuaibi.erp.identity.branch.Branch;
import com.alshuaibi.erp.identity.branch.BranchRepository;
import com.alshuaibi.erp.identity.role.Role;
import com.alshuaibi.erp.identity.role.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        String normalizedUsername = normalizeUsername(request.getUsername());
        String normalizedEmail = normalizeEmail(request.getEmail());

        if (normalizedUsername == null) {
            throw new IllegalArgumentException("Username is required");
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (normalizedEmail != null && userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = getRoleOrThrow(request.getRoleId());
        Branch defaultBranch = getBranchOrNull(request.getDefaultBranchId());

        User user = new User();
        user.setFullName(requireText(request.getFullName(), "Full name is required"));
        user.setUsername(normalizedUsername);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(normalizedEmail);
        user.setPhone(trimToNull(request.getPhone()));
        user.setRole(role);
        user.setDefaultBranch(defaultBranch);
        user.setActive(true);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserResponse> getUsers(UserSearchCriteria criteria, Pageable pageable) {
        Specification<User> specification = Specification.where(null);

        Boolean active = criteria.getActive() != null ? criteria.getActive() : Boolean.TRUE;
        specification = specification.and((root, query, cb) -> cb.equal(root.get("active"), active));

        if (hasText(criteria.getUsername())) {
            String username = criteria.getUsername().trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("username")), "%" + username + "%"));
        }

        if (hasText(criteria.getFullName())) {
            String fullName = criteria.getFullName().trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("fullName")), "%" + fullName + "%"));
        }

        Page<UserResponse> page = userRepository.findAll(specification, pageable)
                .map(userMapper::toResponse);

        return PagedResponse.<UserResponse>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String normalizedEmail = normalizeEmail(request.getEmail());
        if (normalizedEmail != null && userRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = getRoleOrThrow(request.getRoleId());
        Branch defaultBranch = getBranchOrNull(request.getDefaultBranchId());

        user.setFullName(requireText(request.getFullName(), "Full name is required"));
        user.setEmail(normalizedEmail);
        user.setPhone(trimToNull(request.getPhone()));
        user.setRole(role);
        user.setDefaultBranch(defaultBranch);
        user.setActive(request.getActive());

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(true);
        userRepository.save(user);
    }

    private Role getRoleOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    private Branch getBranchOrNull(Long branchId) {
        if (branchId == null) {
            return null;
        }

        return branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Default branch not found"));
    }

    private String normalizeUsername(String username) {
        String value = trimToNull(username);
        return value == null ? null : value.toLowerCase(Locale.ROOT);
    }

    private String normalizeEmail(String email) {
        String value = trimToNull(email);
        return value == null ? null : value.toLowerCase(Locale.ROOT);
    }

    private String requireText(String value, String message) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            throw new IllegalArgumentException(message);
        }
        return trimmed;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}