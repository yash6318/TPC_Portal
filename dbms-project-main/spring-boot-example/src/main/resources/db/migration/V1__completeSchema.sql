CREATE TABLE customer (
    id INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(25) NOT NULL,
    middleName VARCHAR(50),
    lastName VARCHAR(25),
    contactNumber VARCHAR(20) NOT NULL,
    dateOfBirth Date NOT NULL,
    emailId VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULl,
    state VARCHAR(50) NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    country VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE supplier (
    id INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(25) NOT NULL,
    middleName VARCHAR(50),
    lastName VARCHAR(25),
    contactNumber VARCHAR(20) NOT NULL,
    city VARCHAR(50) NOT NULl,
    state VARCHAR(50) NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    country VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE employee (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL DEFAULT '$2a$10$c9p5GncgKrRBVOQ/CfN8m.kQZeimFYyQHa7L8Jjs8Y/F4/qKbku12',
    firstName VARCHAR(25) NOT NULL,
    middleName VARCHAR(50),
    lastName VARCHAR(25),
    contactNumber VARCHAR(20) NOT NULL,
    designation ENUM('Employee', 'Manager', 'Admin') NOT NULL DEFAULT ('Employee'),
    salary INT NOT NULL,
    dateOfBirth Date NOT NULL,
    emailId VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULl,
    state VARCHAR(50) NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    country VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transaction (
    id INT NOT NULL AUTO_INCREMENT,
    bankBranch VARCHAR(25) NOT NULL,
    accountNumber VARCHAR(25),
    amount INT NOT NULL,
    mode VARCHAR(25) NOT NULL,
    dateOfTransaction DATE NOT NULL,
    bankName VARCHAR(25) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE supplierOrder (
    id INT NOT NULL AUTO_INCREMENT,
    dateOfOrder DATE,
    status VARCHAR(20),
    supplierId INT,
    PRIMARY KEY (id),
    FOREIGN KEY (supplierId) REFERENCES supplier(id) ON DELETE SET NULL
);

CREATE TABLE leavesAndSalaries (
    id INT NOT NULL AUTO_INCREMENT,
    leavesTaken INT NOT NULL,
    leavesAllowed INT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    overtime INT NOT NULL,
    bonus INT NOT NULL,
    penalty INT NOT NULL,
    employeeId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(employeeId) REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE workExperience (
    id INT NOT NULL AUTO_INCREMENT,
    employeeId int NOT NULL,
    designation VARCHAR(20) NOT NULL,
    joiningDate DATE NOT NULL,
    leavingDate DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(employeeId) REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE customerOrder (
    id INT NOT NULL AUTO_INCREMENT,
    deliveryAgentAssigned BOOLEAN DEFAULT FALSE,
    verificationStatus BOOLEAN NOT NULL,
    deliveryDate DATE,
    orderedDate DATE NOT NULL,
    customerId INT NOT NULL,
    employeeId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (customerId) REFERENCES customer(id) ON DELETE CASCADE,
    FOREIGN KEY (employeeId) REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE productType (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    warrantyPeriod INT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE bill (
	id INT NOT NULL AUTO_INCREMENT,
    gstNumber VARCHAR(12) NOT NULL,
    amount INT NOT NULL,
    discount INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE feedback (
    id INT NOT NULL AUTO_INCREMENT,
    reviews TEXT,
    complaints TEXT,
    suggestions TEXT,
    rating INT NOT NULL,
    customerOrderId INT,
    PRIMARY KEY (id),
    FOREIGN KEY (customerOrderId) REFERENCES customerOrder(id)
);

CREATE TABLE contactForm (
    id INT NOT NULL AUTO_INCREMENT,
    reply TEXT,
    query TEXT,
    customerId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customerId) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE payment (
    id INT NOT NULL AUTO_INCREMENT,
    customerOrderId INT NOT NULL,
    transactionId INT,
    billId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customerOrderId) REFERENCES customerOrder(id) ON DELETE CASCADE,
    FOREIGN KEY (transactionId) REFERENCES transaction(id) ON DELETE SET NULL,
    FOREIGN KEY (billId) REFERENCES bill(id) ON DELETE CASCADE
);

CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    supplierOrderId INT,
    productTypeId INT NOT NULL,
    customerOrderId INT,
    PRIMARY KEY (id),
    FOREIGN KEY (supplierOrderId) REFERENCES supplierOrder(id) ON DELETE SET NULL,
    FOREIGN KEY (productTypeId) REFERENCES productType(id) ON DELETE CASCADE,
    FOREIGN KEY (customerOrderId) REFERENCES customerOrder(id) ON DELETE SET NULL
);

CREATE TABLE warranty (
    id INT NOT NULL AUTO_INCREMENT,
    coverage TEXT,
    productId INT NOT NULL,
    customerId INT,
    endDate DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (customerId) REFERENCES customer(id) ON DELETE SET NULL
);

CREATE TABLE orderedProductType (
    id INT NOT NULL AUTO_INCREMENT,
    supplierOrderId INT NOT NULL,
    quantity INT NOT NULL,
    productTypeId INT NOT NULL,
    numberDelivered INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (productTypeId) REFERENCES productType(id),
    FOREIGN KEY (supplierOrderId) REFERENCES supplierOrder(id) ON DELETE CASCADE
);

INSERT INTO employee (firstName, middleName, lastName, contactNumber, designation, salary, dateOfBirth, emailId, city, state, postalCode, country, street, username)
VALUES ('admin', NULL, NULL, '0000000000', 'Admin', 0, '2000-01-01', 'admin@dbms.com', 'city', 'state', 'postalCode', 'country', 'street', 'admin');

create table productSpecification (
	id int not null auto_increment,
    specificationName Varchar(20),
    specificationText Varchar(100),
    productTypeId int,
    primary key (id),
    foreign key (productTypeId) references productType(id)
);
-- CREATE TABLE IF NOT EXISTS USER(
--     username varchar(255),
--     password varchar(255)
--     designation ENUM('Student', 'TPR', 'Company') NOT NULL DEFAULT ('Student')
-- );