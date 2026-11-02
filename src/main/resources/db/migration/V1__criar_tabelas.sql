CREATE TABLE TB_OWNER (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255)
);

CREATE TABLE TB_CLINIC (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    cnpj VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    opening_hours TIME,
    closing_hours TIME
);

CREATE TABLE TB_ACCOUNT (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    owner_id BIGINT,
    clinic_id BIGINT,

    CONSTRAINT uq_account_owner UNIQUE (owner_id),
    CONSTRAINT fk_account_owner
        FOREIGN KEY (owner_id) REFERENCES TB_OWNER(id),
    CONSTRAINT fk_account_clinic
        FOREIGN KEY (clinic_id) REFERENCES TB_CLINIC(id)
);

CREATE TABLE TB_PET (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    species VARCHAR(255),
    breed VARCHAR(255),
    birth_date DATE,
    weight FLOAT(53),
    sex TINYINT,
    owner_id BIGINT,

    CONSTRAINT fk_pet_owner
        FOREIGN KEY (owner_id) REFERENCES TB_OWNER(id)
);

CREATE TABLE TB_APPOINTMENT (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    speciality VARCHAR(255),
    symptoms VARCHAR(255),
    dignosis VARCHAR(255),
    observations VARCHAR(255),
    appointment_date DATETIME2,
    updated_weight FLOAT(53),
    clinic_id BIGINT,
    pet_id BIGINT,

    CONSTRAINT fk_appointment_clinic
        FOREIGN KEY (clinic_id) REFERENCES TB_CLINIC(id),
    CONSTRAINT fk_appointment_pet
        FOREIGN KEY (pet_id) REFERENCES TB_PET(id)
);
