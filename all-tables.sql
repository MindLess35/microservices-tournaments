-- team service
CREATE TABLE users (
    id              BIGSERIAL      PRIMARY KEY,
    username        VARCHAR(64)    UNIQUE NOT NULL,
    email           VARCHAR(255)   UNIQUE NOT NULL,
    password        VARCHAR(255)   NOT NULL,
    about           VARCHAR(512),
    gender          CHAR(1),
    role            VARCHAR(32)    NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    updated_at      TIMESTAMP
);


-- team service
CREATE TABLE teams (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(64)     NOT NULL,
    about           VARCHAR(512),
    captain_id      BIGINT,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP
);


CREATE TABLE team_members (
    id              BIGSERIAL       PRIMARY KEY,
    team_id         BIGINT          NOT NULL REFERENCES teams(id),
    user_id         BIGINT          NOT NULL,
    role            VARCHAR(128),
    joined_at       TIMESTAMP       NOT NULL
);

CREATE INDEX idx_team_members_team_id_user_id ON team_members (team_id, user_id);


CREATE TABLE team_statistics (
    team_id             BIGINT       PRIMARY KEY REFERENCES teams(id),
    matches_played      INTEGER,
    wins                INTEGER,
    losses              INTEGER,
    draws               INTEGER
);

CREATE INDEX idx_team_statistics_team_id ON team_statistics(team_id);


-- tournament service
CREATE TABLE tournaments (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(128)    NOT NULL,
    sport_type      VARCHAR(128)    NOT NULL,
    start_date      TIMESTAMP       NOT NULL,
    end_date        TIMESTAMP       NOT NULL,
    location        VARCHAR(255),
    prize_pool      DECIMAL,
    status          VARCHAR(64)     NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP
);


CREATE TABLE tournament_teams (
    id                  BIGSERIAL       PRIMARY KEY,
    registration_date   TIMESTAMP       NOT NULL,
    is_approved         BOOLEAN         NOT NULL,
    tournament_id       BIGINT          REFERENCES tournaments(id),
    team_id             BIGINT
);

CREATE UNIQUE INDEX idx_tournament_teams_tournament_id_team_id ON tournament_teams(tournament_id, team_id);


-- match service
CREATE TABLE matches (
    id                    BIGSERIAL       PRIMARY KEY,
    start_date            TIMESTAMP       NOT NULL,
    end_date              TIMESTAMP       NOT NULL,
    location              VARCHAR(255),
    result                VARCHAR(64),
    score_first_team      INTEGER,
    score_second_team     INTEGER,
    status                VARCHAR(64)     NOT NULL,
    tournament_id         BIGINT          NOT NULL,
    first_team_id         BIGINT          NOT NULL,
    second_team_id        BIGINT          NOT NULL
);

CREATE INDEX idx_matches_tournament_id ON matches(tournament_id);
CREATE INDEX idx_matches_team1_id ON matches(first_team_id);
CREATE INDEX idx_matches_team2_id ON matches(second_team_id);


-- email notification service
CREATE TABLE email_notifications (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    event_type      VARCHAR(128)    NOT NULL,
    status          VARCHAR(64)     NOT NULL,
    created_at      TIMESTAMP       NOT NULL
);

CREATE INDEX idx_email_notifications_user_id ON email_notifications(user_id);


-- feed service
-- feeds
-- mongo schema
-- {
--     "_id": "ObjectId",
--     "user_id": "Long",
--     "events": [
--         {
--             "event_type": "String",
--             "description": "String",
--             "timestamp": "ISODate",
--             "is_read": "Boolean",
--             "priority": "String",
--             "source_id": "Long",
--             "source_type": "String",
--             "tags": ["String"],
--             "is_pinned": "Boolean"
--         }
--     ],
--     "last_updated": "ISODate"
-- }