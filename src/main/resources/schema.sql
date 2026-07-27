-- LearnHub Database Schema Definition
-- Defines all core tables conforming to er-diagram.mmd

-- 1. USERS Table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'LEARNER',
    avatar_url VARCHAR(255),
    headline VARCHAR(255),
    location VARCHAR(255),
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. CATEGORIES Table
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    resource_count INT DEFAULT 0
);

-- 3. CONTENTS Table
CREATE TABLE IF NOT EXISTS contents (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    preview_text TEXT,
    content_body TEXT,
    file_url VARCHAR(255),
    price DECIMAL(10, 2) DEFAULT 0.00,
    type VARCHAR(100),
    level VARCHAR(100),
    tags VARCHAR(255),
    featured BOOLEAN DEFAULT FALSE,
    is_trending BOOLEAN DEFAULT FALSE,
    rating DECIMAL(3, 2) DEFAULT 0.00,
    reviews_count INT DEFAULT 0,
    learners_count INT DEFAULT 0,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    creator_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. PURCHASES Table
CREATE TABLE IF NOT EXISTS purchases (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    content_id BIGINT REFERENCES contents(id) ON DELETE CASCADE,
    amount_paid DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    transaction_id VARCHAR(255) UNIQUE,
    purchased_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. QUESTIONS Table
CREATE TABLE IF NOT EXISTS questions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    content_id BIGINT REFERENCES contents(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. DISCUSSION_REPLIES Table
CREATE TABLE IF NOT EXISTS discussion_replies (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT REFERENCES questions(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    reply_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. DOUBT_SESSIONS Table
CREATE TABLE IF NOT EXISTS doubt_sessions (
    id BIGSERIAL PRIMARY KEY,
    learner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    creator_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    topic VARCHAR(255) NOT NULL,
    scheduled_at TIMESTAMP NOT NULL,
    duration_minutes INT DEFAULT 30,
    session_price DECIMAL(10, 2) DEFAULT 0.00,
    booking_status VARCHAR(50) DEFAULT 'PENDING',
    payment_status VARCHAR(50) DEFAULT 'UNPAID',
    transaction_id VARCHAR(255) UNIQUE,
    jitsi_room_name VARCHAR(255) UNIQUE
);

-- 8. REVIEWS Table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    student_name VARCHAR(255),
    avatar_url VARCHAR(255),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    review_text TEXT,
    review_date VARCHAR(100),
    content_id BIGINT REFERENCES contents(id) ON DELETE CASCADE,
    session_id BIGINT REFERENCES doubt_sessions(id) ON DELETE CASCADE
);

-- 9. REFRESH_TOKENS Table (Auth Module)
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

