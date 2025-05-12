CREATE TABLE sentences (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           sentence TEXT NOT NULL,
                           from_source VARCHAR(50) NOT NULL,
                           from_who VARCHAR(50) NOT NULL,
                           add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);