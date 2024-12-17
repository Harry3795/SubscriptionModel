-- CREATE PLANS TABLE
CREATE TABLE dbo.plans (
    id BIGINT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name VARCHAR(50) NOT NULL,
    validity_in_months INT NOT NULL,
    number_of_bids INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- CREATE PROJECTS TABLE
CREATE TABLE dbo.projects (
    project_id BIGINT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NULL
);

-- CREATE CUSTOMER REGISTER MODEL
CREATE TABLE dbo.register_model (
    register_id INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    email VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    remainingBids INT DEFAULT 0,
    remaining_bids INT NULL
);



--SUBSCRIPTION TABLE 
CREATE TABLE dbo.subscriptions (
    id BIGINT   PRIMARY KEY IDENTITY(1,1) NOT NULL,
    plan_id BIGINT NOT NULL,
    register_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    remaining_bids INT NOT NULL,
    
    FOREIGN KEY (plan_id) REFERENCES dbo.plans(id),
    FOREIGN KEY (register_id) REFERENCES dbo.register_model(register_id)
);

--insert data into plans table 
INSERT INTO dbo.plans (name, validity_in_months, number_of_bids, price) VALUES 
('Basic', 1, 7, 2999),
('Standard', 6, 50, 5999),
('Premium', 12, 120, 10999);

insert into projects values('nrt','nrt side')


http://10.93.196.158:9999/docmgmt/generate-dynamic-report
