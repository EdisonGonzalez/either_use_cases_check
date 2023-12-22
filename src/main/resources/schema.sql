CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    arrangement_id VARCHAR(50) NOT NULL,
    customer_id VARCHAR(18) NOT NULL,
    opening_date DATE NOT NULL DEFAULT CURRENT_DATE,
    active BOOLEAN NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE
);