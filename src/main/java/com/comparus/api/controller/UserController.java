package com.comparus.api.controller;

import com.comparus.api.dto.UserDto;
import com.comparus.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
@RequestMapping("/users")
public class UserController {

    private final UsersService service;

    @GetMapping
    @Operation(
            summary = "Get aggregated users",
            description = "Returns users from all configured data sources")
    public Collection<UserDto> getAllUsers(
            @Parameter(description = "Filter by username")
            @RequestParam(required = false) String username,
            @Parameter(description = "Filter by first name")
            @RequestParam(required = false) String name,
            @Parameter(description = "Filter by surname")
            @RequestParam(required = false) String surname
    ) {
        return service.getUsers(username, name, surname);
    }
}
