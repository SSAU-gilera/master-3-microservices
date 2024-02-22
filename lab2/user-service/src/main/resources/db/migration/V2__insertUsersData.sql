INSERT INTO "user-schema".users(
    user_id, name, email, login, password, enabled, company_id)
VALUES
    (101, 'John Doe', 'johndoe@example.com', 'johndoe', 'password123', true, 1001),
    (102, 'Jane Smith', 'janesmith@example.com', 'janesmith', 'securepass456', true, 1002),
    (103, 'Michael Johnson', 'michaeljohnson@example.com', 'michaelj', 'mysecretpwd', true, 1003),
    (104, 'Sarah Wilson', 'sarahwilson@example.com', 'sarahw', 'strongpassword789', true, 1004),
    (105, 'David Brown', 'davidbrown@example.com', 'davidb', 'p@ssw0rd!', true, 1005),
    (106, 'Emily Lee', 'emilylee@example.com', 'emilylee', 'qwerty123', true, 1001),
    (107, 'Daniel Davis', 'danieldavis@example.com', 'danield', 'password321', true, 1002),
    (108, 'Olivia Taylor', 'oliviataylor@example.com', 'oliviat', 'letmein2023', true, 1003),
    (109, 'James Martinez', 'jamesmartinez@example.com', 'jamesm', 'changeme5678', true, 1004),
    (110, 'Sophia Anderson', 'sophiaanderson@example.com', 'sophiaa', 'passw0rd2023', true, 1005);