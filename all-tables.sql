-- user service
CREATE TABLE users (
    id              BIGSERIAL      PRIMARY KEY,
    keycloak_uuid   UUID           UNIQUE,
    username        VARCHAR(64)    UNIQUE NOT NULL,
    first_name      VARCHAR(64)    NOT NULL,
    last_name       VARCHAR(64)    NOT NULL,
    email           VARCHAR(255)   UNIQUE NOT NULL,
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
    sport_type      VARCHAR(32)     NOT NULL,
    captain_id      BIGINT,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP
);


CREATE TABLE team_members (
    team_id         BIGINT          NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    user_id         BIGINT          NOT NULL,
    role            VARCHAR(128),
    joined_at       TIMESTAMP       NOT NULL,
    PRIMARY KEY (team_id, user_id)
);


CREATE TABLE team_statistics (
    team_id             BIGINT       PRIMARY KEY REFERENCES teams(id) ON DELETE CASCADE,
    matches_played      INTEGER,
    wins                INTEGER,
    losses              INTEGER,
    draws               INTEGER
);


-- tournament service
CREATE TABLE tournaments (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(255)    NOT NULL,
    sport_type      VARCHAR(32)     NOT NULL,
    start_date      TIMESTAMP       NOT NULL,
    end_date        TIMESTAMP       NOT NULL,
    location        VARCHAR(255),
    prize_pool      DECIMAL,
    status          VARCHAR(16)     NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP
);


CREATE TABLE tournament_teams (
    tournament_id       BIGINT          REFERENCES tournaments(id) ON DELETE CASCADE,
    team_id             BIGINT,
    registration_date   TIMESTAMP       NOT NULL,
    is_approved         BOOLEAN,
    PRIMARY KEY (tournament_id, team_id)
);


-- match service
CREATE TABLE matches (
    id                    BIGSERIAL       PRIMARY KEY,
    sport_type            VARCHAR(32)     NOT NULL,
    start_date            TIMESTAMP       NOT NULL,
    end_date              TIMESTAMP       NOT NULL,
    location              VARCHAR(255),
    result                VARCHAR(16),
    score_first_team      INTEGER,
    score_second_team     INTEGER,
    status                VARCHAR(16)     NOT NULL,
    tournament_id         BIGINT          NOT NULL,
    first_team_id         BIGINT          NOT NULL,
    second_team_id        BIGINT          NOT NULL,
    UNIQUE (tournament_id, first_team_id, second_team_id)
);


-- notification service
CREATE TABLE notifications (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    event_type      VARCHAR(128)    NOT NULL,
    status          VARCHAR(64)     NOT NULL,
    created_at      TIMESTAMP       NOT NULL
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);


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
