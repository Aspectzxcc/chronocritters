package com.chronocritters.lib.model.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.chronocritters.lib.model.effects.DamageEffect;
import com.chronocritters.lib.model.effects.DamageOverTimeEffect;
import com.chronocritters.lib.model.effects.SkipTurnEffect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.leangen.graphql.annotations.GraphQLId;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "effects")
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "_type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = DamageEffect.class, name = "DamageEffect"),
  @JsonSubTypes.Type(value = DamageOverTimeEffect.class, name = "DamageOverTimeEffect"),
  @JsonSubTypes.Type(value = SkipTurnEffect.class, name = "SkipTurnEffect"),
})
@GraphQLInterface(name = "Effect", implementationAutoDiscovery = true)
public abstract class Effect {
    @GraphQLQuery
    @NotBlank(message = "Effect ID cannot be blank")
    @GraphQLId
    protected String id;
    
    @GraphQLQuery
    @NotBlank(message = "Effect description cannot be blank")
    protected String description;

    @GraphQLQuery
    protected String casterId;
}