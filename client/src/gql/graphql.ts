export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  Instant: { input: any; output: any; }
  Long: { input: any; output: any; }
};

export type Ability = Node & {
  __typename?: 'Ability';
  description: Scalars['String']['output'];
  effects: Array<Maybe<Effect>>;
  id: Scalars['ID']['output'];
  name: Scalars['String']['output'];
};

export type BaseStats = {
  __typename?: 'BaseStats';
  attack: Scalars['Int']['output'];
  defense: Scalars['Int']['output'];
  expToNextLevel?: Maybe<Scalars['Long']['output']>;
  experience: Scalars['Long']['output'];
  health: Scalars['Int']['output'];
  level: Scalars['Int']['output'];
};

export type Critter = {
  __typename?: 'Critter';
  abilities: Array<Maybe<Ability>>;
  baseStats: BaseStats;
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
  name: Scalars['String']['output'];
  type: CritterType;
};

export enum CritterType {
  Electric = 'ELECTRIC',
  Fire = 'FIRE',
  Grass = 'GRASS',
  Kinetic = 'KINETIC',
  Metal = 'METAL',
  Toxic = 'TOXIC',
  Unknown = 'UNKNOWN',
  Water = 'WATER'
}

export type DamageEffect = Effect & Node & {
  __typename?: 'DamageEffect';
  casterId?: Maybe<Scalars['String']['output']>;
  damage: Scalars['Int']['output'];
  description: Scalars['String']['output'];
  id: Scalars['ID']['output'];
};

export type DamageOverTimeEffect = Effect & Node & {
  __typename?: 'DamageOverTimeEffect';
  casterId?: Maybe<Scalars['String']['output']>;
  damagePerTurn: Scalars['Int']['output'];
  description: Scalars['String']['output'];
  duration: Scalars['Int']['output'];
  id: Scalars['ID']['output'];
};

export type Effect = {
  casterId?: Maybe<Scalars['String']['output']>;
  description: Scalars['String']['output'];
  id: Scalars['ID']['output'];
};

export type LoginResponse = {
  __typename?: 'LoginResponse';
  token?: Maybe<Scalars['String']['output']>;
  user?: Maybe<User>;
};

export type MatchHistoryEntry = {
  __typename?: 'MatchHistoryEntry';
  battleId?: Maybe<Scalars['String']['output']>;
  damageDealt: Scalars['Int']['output'];
  damageReceived: Scalars['Int']['output'];
  duration: Scalars['Long']['output'];
  loserId?: Maybe<Scalars['String']['output']>;
  opponentCrittersNames?: Maybe<Array<Maybe<Scalars['String']['output']>>>;
  opponentUsername?: Maybe<Scalars['String']['output']>;
  timestamp?: Maybe<Scalars['Instant']['output']>;
  turnActionHistory?: Maybe<Array<Maybe<TurnActionEntry>>>;
  turnCount: Scalars['Int']['output'];
  usedCrittersNames?: Maybe<Array<Maybe<Scalars['String']['output']>>>;
  winnerId?: Maybe<Scalars['String']['output']>;
};

/** Mutation root */
export type Mutation = {
  __typename?: 'Mutation';
  login?: Maybe<LoginResponse>;
  register?: Maybe<LoginResponse>;
};


/** Mutation root */
export type MutationLoginArgs = {
  password?: InputMaybe<Scalars['String']['input']>;
  username?: InputMaybe<Scalars['String']['input']>;
};


/** Mutation root */
export type MutationRegisterArgs = {
  password?: InputMaybe<Scalars['String']['input']>;
  username?: InputMaybe<Scalars['String']['input']>;
};

/** An object with an ID */
export type Node = {
  /** The ID of an object */
  id: Scalars['ID']['output'];
};

export type Player = {
  __typename?: 'Player';
  id?: Maybe<Scalars['ID']['output']>;
  matchHistory?: Maybe<Array<Maybe<MatchHistoryEntry>>>;
  roster: Array<Maybe<Critter>>;
  stats: PlayerStats;
  userId: Scalars['String']['output'];
  username?: Maybe<Scalars['String']['output']>;
};

export type PlayerStats = {
  __typename?: 'PlayerStats';
  expToNextLevel?: Maybe<Scalars['Long']['output']>;
  experience: Scalars['Long']['output'];
  level: Scalars['Int']['output'];
  losses: Scalars['Int']['output'];
  wins: Scalars['Int']['output'];
};

/** Query root */
export type Query = {
  __typename?: 'Query';
  getMatchHistoryEntry?: Maybe<MatchHistoryEntry>;
  getPlayer?: Maybe<Player>;
};


/** Query root */
export type QueryGetMatchHistoryEntryArgs = {
  battleId?: InputMaybe<Scalars['String']['input']>;
  playerId?: InputMaybe<Scalars['String']['input']>;
};


/** Query root */
export type QueryGetPlayerArgs = {
  id?: InputMaybe<Scalars['String']['input']>;
};

export type SkipTurnEffect = Effect & Node & {
  __typename?: 'SkipTurnEffect';
  casterId?: Maybe<Scalars['String']['output']>;
  description: Scalars['String']['output'];
  duration: Scalars['Int']['output'];
  id: Scalars['ID']['output'];
};

export type TurnActionEntry = {
  __typename?: 'TurnActionEntry';
  playerHasTurn: Scalars['Boolean']['output'];
  playerId?: Maybe<Scalars['String']['output']>;
  turn: Scalars['Int']['output'];
  turnActionLog?: Maybe<Scalars['String']['output']>;
};

export type User = {
  __typename?: 'User';
  id?: Maybe<Scalars['String']['output']>;
  username?: Maybe<Scalars['String']['output']>;
};

export type LoginMutationVariables = Exact<{
  username: Scalars['String']['input'];
  password: Scalars['String']['input'];
}>;


export type LoginMutation = { __typename?: 'Mutation', login?: { __typename?: 'LoginResponse', token?: string | null, user?: { __typename?: 'User', id?: string | null, username?: string | null } | null } | null };

export type RegisterMutationVariables = Exact<{
  username: Scalars['String']['input'];
  password: Scalars['String']['input'];
}>;


export type RegisterMutation = { __typename?: 'Mutation', register?: { __typename?: 'LoginResponse', token?: string | null, user?: { __typename?: 'User', id?: string | null, username?: string | null } | null } | null };

export type GetPlayerStatsQueryVariables = Exact<{
  id: Scalars['String']['input'];
}>;


export type GetPlayerStatsQuery = { __typename?: 'Query', getPlayer?: { __typename?: 'Player', stats: { __typename?: 'PlayerStats', wins: number, losses: number } } | null };

export type GetPlayerOverviewQueryVariables = Exact<{
  id: Scalars['String']['input'];
}>;


export type GetPlayerOverviewQuery = { __typename?: 'Query', getPlayer?: { __typename?: 'Player', id?: string | null, username?: string | null, stats: { __typename?: 'PlayerStats', wins: number, losses: number, level: number, experience: any, expToNextLevel?: any | null }, roster: Array<{ __typename?: 'Critter', name: string, description: string, type: CritterType, baseStats: { __typename?: 'BaseStats', level: number } } | null> } | null };

export type GetMyCrittersQueryVariables = Exact<{
  id: Scalars['String']['input'];
}>;


export type GetMyCrittersQuery = { __typename?: 'Query', getPlayer?: { __typename?: 'Player', roster: Array<{ __typename?: 'Critter', id: string, name: string, description: string, type: CritterType, baseStats: { __typename?: 'BaseStats', level: number, experience: any, expToNextLevel?: any | null, health: number, attack: number, defense: number }, abilities: Array<{ __typename?: 'Ability', id: string, name: string, description: string, effects: Array<
          | { __typename?: 'DamageEffect', id: string, description: string, damage: number }
          | { __typename?: 'DamageOverTimeEffect', id: string, description: string, damagePerTurn: number, duration: number }
          | { __typename?: 'SkipTurnEffect', id: string, description: string, duration: number }
         | null> } | null> } | null> } | null };

export type GetPlayerResultsQueryVariables = Exact<{
  id: Scalars['String']['input'];
}>;


export type GetPlayerResultsQuery = { __typename?: 'Query', getPlayer?: { __typename?: 'Player', username?: string | null, stats: { __typename?: 'PlayerStats', level: number, experience: any, expToNextLevel?: any | null }, roster: Array<{ __typename?: 'Critter', id: string, name: string, baseStats: { __typename?: 'BaseStats', level: number, experience: any, expToNextLevel?: any | null } } | null> } | null };

export type GetBattleHistoryQueryVariables = Exact<{
  id: Scalars['String']['input'];
}>;


export type GetBattleHistoryQuery = { __typename?: 'Query', getPlayer?: { __typename?: 'Player', matchHistory?: Array<{ __typename?: 'MatchHistoryEntry', battleId?: string | null, winnerId?: string | null, loserId?: string | null, opponentUsername?: string | null, timestamp?: any | null, usedCrittersNames?: Array<string | null> | null, opponentCrittersNames?: Array<string | null> | null, turnCount: number, duration: any, damageDealt: number, damageReceived: number, turnActionHistory?: Array<{ __typename?: 'TurnActionEntry', playerId?: string | null, playerHasTurn: boolean, turn: number, turnActionLog?: string | null } | null> | null } | null> | null } | null };

export type GetBattleHistoryEntryQueryVariables = Exact<{
  playerId: Scalars['String']['input'];
  battleId: Scalars['String']['input'];
}>;


export type GetBattleHistoryEntryQuery = { __typename?: 'Query', getMatchHistoryEntry?: { __typename?: 'MatchHistoryEntry', battleId?: string | null, winnerId?: string | null, loserId?: string | null, opponentUsername?: string | null, timestamp?: any | null, usedCrittersNames?: Array<string | null> | null, opponentCrittersNames?: Array<string | null> | null, turnCount: number, duration: any, damageDealt: number, damageReceived: number, turnActionHistory?: Array<{ __typename?: 'TurnActionEntry', playerId?: string | null, playerHasTurn: boolean, turn: number, turnActionLog?: string | null } | null> | null } | null };
