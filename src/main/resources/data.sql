-- Seed Data for LearnHub
-- Installs default users, categories, contents, and forum questions

-- 1. Insert Seed Users
-- Passwords set to plain text for local mock testing or dev auth
INSERT INTO users (id, name, email, password, role, avatar_url, headline, location) VALUES
(101, 'Arjun Mehta', 'arjun.mehta@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'LEARNER', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150', 'Fullstack Architect', 'Mumbai, India'),
(202, 'Rohan Verma', 'rohan.verma@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'CREATOR', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150', 'Staff Backend Engineer', 'Bengaluru, India'),
(303, 'System Administrator', 'admin@learnhub.com', '$2a$10$pi1.Dii5CpjFqFpE8.nYsOIxz2/9329jDlMqV8rmlJK7vLwFCc3.q', 'ADMIN', 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150', 'System Admin', 'Delhi, India')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Seed Categories
INSERT INTO categories (id, name, resource_count) VALUES
(1, 'Java', 2),
(2, 'DSA', 1),
(3, 'System Design', 1),
(4, 'Web Dev', 0),
(5, 'Cloud Computing', 0)
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Seed Contents
INSERT INTO contents (id, title, description, preview_text, price, type, level, tags, category_id, creator_id, rating, reviews_count) VALUES
(10, 'Complete Java Spring Boot Monolith', 'Learn microservices, transactions, and JPA.', 'Chapter 1: Configuration, Chapter 2: Security', 499.00, 'Notes & Code', 'Intermediate', 'Java,Spring Boot,Backend', 1, 202, 4.85, 2),
(11, 'Data Structures & Algorithms Cheat Sheets', 'High-quality reference sheets for interviews.', 'Big O notation cheat sheet, Trees & Graph reference', 0.00, 'Cheat Sheet PDF', 'Beginner', 'DSA,Interview,C++', 2, 202, 4.50, 0)
ON CONFLICT (id) DO NOTHING;

-- 4. Insert Seed Questions
INSERT INTO questions (id, user_id, content_id, question_text) VALUES
(1001, 101, 10, 'How do I handle transactional boundaries for multiple database connections?')
ON CONFLICT (id) DO NOTHING;

-- 5. Insert Seed Discussion Replies
INSERT INTO discussion_replies (id, question_id, user_id, reply_text) VALUES
(2001, 1001, 202, 'You can use JtaTransactionManager or configure chained transaction managers depending on the datastores.')
ON CONFLICT (id) DO NOTHING;

-- 6. Insert Seed Refresh Tokens (Auth Module)
INSERT INTO refresh_tokens (id, user_id, token, expiry_date, revoked) VALUES
(501, 101, 'sample-refresh-token-arjun-101', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(502, 202, 'sample-refresh-token-rohan-202', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(503, 303, 'sample-refresh-token-admin-303', CURRENT_TIMESTAMP + INTERVAL '7 days', false)
ON CONFLICT (id) DO NOTHING;

