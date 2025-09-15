package com.chronocritters.lib.model.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import io.leangen.graphql.annotations.GraphQLId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "players")
public class Player {
    @GraphQLId
    private String id;
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "PlayerStats cannot be null")
    private PlayerStats stats;
    
    @NotNull(message = "Roster cannot be null")
    private List<Critter> roster;

    @Default
    private List<MatchHistoryEntry> matchHistory = new ArrayList<>();
}
