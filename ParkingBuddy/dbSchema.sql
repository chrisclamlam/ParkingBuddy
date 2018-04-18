# Explain which schema is being used
USE test;

# DROPS just in case
DROP TABLE FriendsList;
DROP TABLE FavoritesList;
DROP TABLE Comments;
DROP TABLE Users;
DROP TABLE Prices;
DROP TABLE ParkingSpots;



# Must create tables
# User Table (ID, username, fname, lname, email, passhash)
# Check the length of passhash
CREATE TABLE Users (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    fname VARCHAR(30) NOT NULL,
    lname VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    passhash LONG NOT NULL,
    lat FLOAT(9,6),
    lng FLOAT(9,6),
    PRIMARY KEY(id)
);

# Parking Spot Table (ID, remoteid, label, long, lat, price, status, type)
CREATE TABLE ParkingSpots (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    remoteid VARCHAR(50) NOT NULL,
    label VARCHAR(30) NOT NULL,
    longitude FLOAT(9,6) NOT NULL,
    latitude FLOAT(9,6) NOT NULL,
    spotType MEDIUMINT NOT NULL,
    PRIMARY KEY(id)
);

# FriendsList(fristid, secondid)
CREATE TABLE FriendsList (
	firstid MEDIUMINT NOT NULL,
    secondid MEDIUMINT NOT NULL,
    FOREIGN KEY fk1(firstid) REFERENCES Users(id),
    FOREIGN KEY fk2(secondid) REFERENCES Users(id)
);

# Favorite Spots (userid, spotid)usersusers
CREATE TABLE FavoritesList (
	userid MEDIUMINT NOT NULL,
    spotid MEDIUMINT NOT NULL,
    FOREIGN KEY fk1(userid) REFERENCES Users(id),
    FOREIGN KEY fk2(spotid) REFERENCES ParkingSpots(id)
);

# Comments
CREATE TABLE Comments(
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    userid MEDIUMINT NOT NULL,
    spotid MEDIUMINT NOT NULL,
    rating SMALLINT NOT NULL,
    input  VARCHAR(300),
    PRIMARY KEY(id),
    FOREIGN KEY fk1(userid) REFERENCES Users(id),
    FOREIGN KEY fk2(spotid) REFERENCES ParkingSpots(id)
);

CREATE TABLE Prices(
	spotid MEDIUMINT NOT NULL,
    price  FLOAT(5,2) NOT NULL,
    FOREIGN KEY fk1(spotid) REFERENCES ParkingSpots(id)
);

# More tables
# Availability/Status

# Make some sample data
# Users
# ParkingSpots
# FriendsList
# FavoritesList

