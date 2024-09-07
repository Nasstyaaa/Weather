CREATE TABLE Users (
   id SERIAL PRIMARY KEY,
   login VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL
);
CREATE INDEX idx_users ON Users(password);



CREATE TABLE Locations (
       id SERIAL PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       user_id INTEGER NOT NULL,
       latitude DECIMAL(10, 8) NOT NULL,
       longitude DECIMAL(11, 8) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE Locations ADD CONSTRAINT fk_location_user
    FOREIGN KEY (user_id) REFERENCES Users(id);
CREATE INDEX idx_locations_user_id ON Locations(user_id);
CREATE INDEX idx_locations_latitude ON Locations(latitude);
CREATE INDEX idx_locations_name ON Locations(name);
CREATE INDEX idx_locations_longitude ON Locations(longitude);



CREATE TABLE Sessions (
      session_id UUID PRIMARY KEY,
      user_id INTEGER NOT NULL,
      expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
ALTER TABLE Sessions ADD CONSTRAINT fk_session_user
    FOREIGN KEY (user_id) REFERENCES Users(id);
CREATE INDEX idx_sessions_user_id ON Sessions(user_id);
CREATE INDEX idx_sessions_expires_at ON Sessions(expires_at);