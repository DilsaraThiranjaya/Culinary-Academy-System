-- Insert into students table
INSERT INTO students (studentId, firstName, lastName, dateOfBirth, gender, admissionDate, NIC, address, contactNo, email)
VALUES
  ('STD001', 'Amila', 'Perera', '1998-04-15', 'Male', '2022-01-01', '985412378V', '123 Main St, Colombo', '0771234567', 'amila.perera@email.com'),
  ('STD002', 'Kumari', 'Dissanayake', '1996-09-20', 'Female', '2022-03-15', '967890123V', '456 Park Rd, Kandy', '0778901234', 'kumari.dissanayake@email.com'),
  ('STD003', 'Sudath', 'Gunawardena', '1994-02-12', 'Male', '2021-09-01', '984567890V', '789 Sea View, Galle', '0772345678', 'sudath.gunawardena@email.com'),
  ('STD004', 'Nimal', 'Jayasinghe', '1997-07-05', 'Male', '2022-06-01', '978901234V', '321 Hill Top, Matara', '0779012345', 'nimal.jayasinghe@email.com'),
  ('STD005', 'Vishaka', 'Chandrasiri', '1995-11-30', 'Female', '2021-12-15', '965432101V', '654 Flower Rd, Negombo', '0773456789', 'vishaka.chandrasiri@email.com');

-- Insert into programs table
INSERT INTO programs (programId, name, description, duration, fee)
VALUES
  ('CA1001', 'Professional Cooking', 'Comprehensive program in culinary arts', '1 year', 120000.00),
  ('CA1003', 'Baking & Pastry Arts', 'Specialized program in baking and confectionery', '6 months', 60000.00),
  ('CA1004', 'International Cuisine', 'Explore diverse cooking techniques and flavors', '1 year', 100000.00),
  ('CA1005', 'Culinary Management', 'Blend of culinary skills and business management', '1 year', 150000.00),
  ('CA1006', 'Food Safety and Hygiene', 'Ensure food safety and handling practices', '3 months', 40000.00);

-- Insert into programDetails table
INSERT INTO programDetails (studentId, programId, status)
VALUES
  ('STD001', 'CA1001', 'Ongoing'),
  ('STD002', 'CA1003', 'Completed'),
  ('STD003', 'CA1004', 'Ongoing'),
  ('STD004', 'CA1005', 'Completed'),
  ('STD005', 'CA1006', 'Ongoing');

-- Insert into payments table
INSERT INTO payments (paymentId, date, method, type, upfrontPayment, totalPayment, studentId)
VALUES
  ('PAY001', '2022-01-15', 'Bank Transfer', 'Tuition', 50000.00, 120000.00, 'STD001'),
  ('PAY002', '2022-03-30', 'Cash', 'Tuition', 30000.00, 60000.00, 'STD002'),
  ('PAY003', '2021-09-20', 'Credit Card', 'Tuition', 40000.00, 100000.00, 'STD003'),
  ('PAY004', '2022-06-10', 'Bank Transfer', 'Tuition', 75000.00, 150000.00, 'STD004'),
  ('PAY005', '2021-12-05', 'Cash', 'Tuition', 20000.00, 40000.00, 'STD005');

-- Insert into paymentDetails table
INSERT INTO paymentDetails (paymentId, programId)
VALUES
  ('PAY001', 'CA1001'),
  ('PAY002', 'CA1003'),
  ('PAY003', 'CA1004'),
  ('PAY004', 'CA1005'),
  ('PAY005', 'CA1006');

-- Insert into users table
INSERT INTO users (userId, userName, position, password, email)
VALUES
  ('USER001', 'JohnDoe', 'Admin', 'password123', 'john.doe@culinaryacademy.lk'),
  ('USER002', 'SaraJones', 'Instructor', 'abc456', 'sara.jones@culinaryacademy.lk'),
  ('USER003', 'MikeRyan', 'Student Counselor', 'xyz789', 'mike.ryan@culinaryacademy.lk'),
  ('USER004', 'EmilyChang', 'Finance Manager', 'pass123', 'emily.chang@culinaryacademy.lk'),
  ('USER005', 'SanthoshKumar', 'Admissions Officer', 'admin456', 'santhosh.kumar@culinaryacademy.lk');