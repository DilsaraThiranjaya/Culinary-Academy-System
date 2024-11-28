-- Insert Users
INSERT INTO users (userId, userName, position, password, email)
VALUES ('Dilsara076', 'Dilsara', 'Admin', '07614', 'dilsarathiranjaya3@gmail.com');

INSERT INTO users (userId, userName, position, password, email)
VALUES ('Dilsara123', 'Thiranjaya', 'Coordinator', '07614', 'dilsarathiranjaya4@gmail.com');

-- Insert Students
INSERT INTO students (studentId, firstName, lastName, dateOfBirth, gender, admissionDate, NIC, address, contactNo, email)
VALUES ('ST001', 'Dilsara', 'Thiranjaya', '1995-05-15', 'Male', '2020-01-01', '901234567V', 'Main St, City', '0712345678', 'dilsarathiranjaya3@gmail.com');

INSERT INTO students (studentId, firstName, lastName, dateOfBirth, gender, admissionDate, NIC, address, contactNo, email)
VALUES ('ST002', 'Supun', 'Smith', '1998-08-20', 'Female', '2021-09-01', '987654321V', 'Elm St, Town', '0723456789', 'dilsarathiranjaya4@gmail.com');

INSERT INTO students (studentId, firstName, lastName, dateOfBirth, gender, admissionDate, NIC, address, contactNo, email)
VALUES ('ST003', 'Michael', 'Johnson', '1997-02-10', 'Male', '2022-05-20', '890123456V', 'Oak St, Village', '0734567890', 'michael.johnson@example.com');

INSERT INTO students (studentId, firstName, lastName, dateOfBirth, gender, admissionDate, NIC, address, contactNo, email)
VALUES ('ST004', 'Emily', 'Davis', '2000-12-25', 'Female', '2023-07-15', '890876543V', 'Pine St, Metro', '0745678901', 'emily.davis@example.com');

-- Insert Programs
INSERT INTO programs (programId, name, description, duration, fee)
VALUES ('C001', 'Professional Cooking', 'Learn global cooking techniques.', '1 Years', '120000.00');

INSERT INTO programs (programId, name, description, duration, fee)
VALUES ('C002', 'Baking & Pastry Arts', 'Master baking and pastry skills.', '6 Months', '60000.00');

INSERT INTO programs (programId, name, description, duration, fee)
VALUES ('C003', 'International Cuisine', 'Explore international cooking styles.', '1 Years', '100000.00');

INSERT INTO programs (programId, name, description, duration, fee)
VALUES ('C004', 'Culinary Management', 'Learn restaurant and kitchen management.', '1 Years', '150000.00');

INSERT INTO programs (programId, name, description, duration, fee)
VALUES ('C005', 'Food Safety and Hygiene', 'Understand food safety standards.', '3 Months', '40000.00');
