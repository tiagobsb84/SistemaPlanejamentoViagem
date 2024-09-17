CREATE TABLE trips(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    start_as TIMESTAMP NOT NULL,
    end_as TIMESTAMP NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    owner_email VARCHAR(255) NOT NULL
);