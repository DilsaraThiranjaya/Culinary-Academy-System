-- Create database
CREATE DATABASE culinaryAcademy;
USE culinaryAcademy;

-- Create students table
CREATE TABLE students (
                          studentId VARCHAR(10) PRIMARY KEY,
                          firstName VARCHAR(30),
                          lastName VARCHAR(30),
                          dateOfBirth DATE,
                          gender ENUM('Male', 'Female'),
                          admissionDate DATE,
                          NIC VARCHAR(15),
                          address VARCHAR(50),
                          contactNo VARCHAR(10),
                          email VARCHAR(50)
);

-- Create programs table
CREATE TABLE programs (
                          programId VARCHAR(10) PRIMARY KEY,
                          name VARCHAR(30),
                          description VARCHAR(100),
                          duration VARCHAR(10),
                          fee DECIMAL(10,2)
);

-- Create programDetails table
CREATE TABLE programDetails (
                               studentId VARCHAR(10),
                               FOREIGN KEY (studentId) REFERENCES student (studentId) ON UPDATE CASCADE ON DELETE CASCADE,
                               programId VARCHAR(10),
                               FOREIGN KEY (programId) REFERENCES programs (programId) ON UPDATE CASCADE ON DELETE CASCADE,
                               status ENUM('Ongoing', 'Completed')
);

-- Create payments table
CREATE TABLE payments (
                          paymentId VARCHAR(10) PRIMARY KEY,
                          date DATE,
                          method VARCHAR(30),
                          type VARCHAR(30),
                          upfrontPayment DECIMAL(10,2),
                          totalPayment DECIMAL(10,2),
                          studentId VARCHAR(10),
                          FOREIGN KEY (studentId) REFERENCES student (studentId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE paymentDetails(
                               paymentId VARCHAR(10),
                               FOREIGN KEY (paymentId) REFERENCES payment (paymentId) ON UPDATE CASCADE ON DELETE CASCADE,
                               programId VARCHAR(10),
                               FOREIGN KEY (programId) REFERENCES programs (programId) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create users table
CREATE TABLE users (
                       userId VARCHAR(50) PRIMARY KEY,
                       userName VARCHAR(30),
                       position VARCHAR(30),
                       password VARCHAR(50),
                       email VARCHAR(50),
);

