package com.chronocritters.lib.model.domain;

import com.chronocritters.lib.util.ExperienceUtil;
import io.leangen.graphql.annotations.GraphQLQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerStats {
    @Default 
    private int wins = 0;

    @Default
    private int losses = 0;

    @Default
    private int level = 1;

    @Default
    private long experience = 0;

    @GraphQLQuery(name = "expToNextLevel")
    public Long getExpToNextLevel() {
        return ExperienceUtil.getRequiredExpForPlayerLevel(this.level + 1);
    }
}
