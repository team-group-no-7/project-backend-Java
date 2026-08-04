-- Seed Data for LearnHub
-- Installs default users, categories, contents, purchases, reviews, mentorship sessions, and forum questions

-- 1. Insert Seed Users
INSERT INTO users (id, name, email, password, role, avatar_url, headline, location) VALUES
(101, 'Arjun Mehta', 'arjun.mehta@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'LEARNER', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150', 'Fullstack Architect', 'Mumbai, India'),
(102, 'Priya Sharma', 'priya.sharma@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'LEARNER', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150', 'MCA Graduate Student', 'Pune, India'),
(202, 'Rohan Verma', 'rohan.verma@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'CREATOR', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150', 'Staff Backend Engineer', 'Bengaluru, India'),
(203, 'Neha Gupta', 'neha.gupta@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'CREATOR', 'https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150', 'Principal System Architect', 'Hyderabad, India'),
(204, 'Vikramaditya Rao', 'vikram.rao@learnhub.com', '$2a$10$qMAgUqtms0oB8c2.WWZbD.gtn8rGrhVVmtbUvUK0T9FtJxmZPthgu', 'CREATOR', 'https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=150', 'Lead Frontend Engineer', 'Delhi, India'),
(303, 'System Administrator', 'admin@learnhub.com', '$2a$10$pi1.Dii5CpjFqFpE8.nYsOIxz2/9329jDlMqV8rmlJK7vLwFCc3.q', 'ADMIN', 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150', 'System Admin', 'Delhi, India')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Seed Categories
INSERT INTO categories (id, name, resource_count) VALUES
(1, 'Java', 2),
(2, 'DSA', 1),
(3, 'System Design', 1),
(4, 'Web Dev', 1),
(5, 'Cloud Computing', 0),
(6, 'DevOps & Docker', 1)
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Seed Contents
INSERT INTO contents (id, title, description, preview_text, price, type, level, tags, category_id, creator_id, rating, reviews_count) VALUES
(10, 'Complete Java Spring Boot Monolith & Microservices', 'Learn microservices, transactions, JPA, and security architecture.', 'Chapter 1: Configuration, Chapter 2: Security & JWT', 499.00, 'Notes & Code', 'Intermediate', 'Java,Spring Boot,Backend', 1, 202, 4.85, 2),
(11, 'Data Structures & Algorithms Interview Cheat Sheets', 'High-quality reference sheets for FAANG & top tech interviews.', 'Big O notation cheat sheet, Trees, Graphs & Dynamic Programming', 0.00, 'Cheat Sheet PDF', 'Beginner', 'DSA,Interview,C++', 2, 202, 4.50, 1),
(12, 'System Design Masterclass & Architectural Blueprint', 'Enterprise scale system design diagrams, load balancing & caching strategies.', 'Scalability fundamentals, Sharding, Redis Caching, CDN strategies', 799.00, 'Course Package', 'Advanced', 'System Design,Architecture,Distributed', 3, 203, 4.90, 3),
(13, 'React 19 & Next.js Production Architecture', 'Modern React patterns, SSR, Tailwind CSS, and state management.', 'React Server Components, Hooks, State Engines, Performance Optimization', 399.00, 'Course Package', 'Intermediate', 'React,Frontend,JavaScript', 4, 204, 4.75, 1),
(14, 'Docker & Kubernetes CI/CD Production Cheat Sheet', 'Step-by-step containerization guide for microservices.', 'Dockerfiles, K8s Deployment Manifests, Helm Charts, GitHub Actions', 0.00, 'Cheat Sheet PDF', 'Beginner', 'Docker,Kubernetes,DevOps', 6, 202, 4.95, 2)
ON CONFLICT (id) DO NOTHING;

-- 4. Insert Seed Purchases
INSERT INTO purchases (id, user_id, content_id, amount_paid, payment_status, payment_id, purchased_at) VALUES
(701, 101, 10, 499.00, 'SUCCESS', 'pay_sample_arjun_101_10', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(702, 101, 12, 799.00, 'SUCCESS', 'pay_sample_arjun_101_12', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(703, 102, 13, 399.00, 'SUCCESS', 'pay_sample_priya_102_13', CURRENT_TIMESTAMP - INTERVAL '5 hours')
ON CONFLICT (id) DO NOTHING;

-- 5. Insert Seed Reviews
INSERT INTO reviews (id, content_id, user_id, rating, student_name, review_date, review_text) VALUES
(801, 10, 101, 5, 'Arjun Mehta', '2026-08-03', 'Outstanding Spring Boot architecture guide! Very clear JPA and security examples.'),
(802, 12, 101, 5, 'Arjun Mehta', '2026-08-04', 'The System Design templates saved me weeks of preparation for senior role interviews.'),
(803, 13, 102, 5, 'Priya Sharma', '2026-08-05', 'Comprehensive React 19 breakdown. Exactly what I needed for my final year project.')
ON CONFLICT (id) DO NOTHING;

-- 6. Insert Seed Doubt Sessions (Mentorship)
INSERT INTO doubt_sessions (id, learner_id, creator_id, topic, session_price, duration_minutes, booking_status, payment_status, jitsi_room_name, scheduled_at) VALUES
(901, 101, 202, 'Spring Security OAuth2 & JWT Customization', 450.00, 60, 'APPROVED', 'PAID', 'learnhub-spring-security-901', CURRENT_TIMESTAMP + INTERVAL '1 day'),
(902, 101, 203, 'System Design High Availability Strategy', 500.00, 60, 'APPROVED', 'PAID', 'learnhub-sysdesign-902', CURRENT_TIMESTAMP + INTERVAL '2 days')
ON CONFLICT (id) DO NOTHING;

-- 7. Insert Seed QA Threads (Q&A Discussions)
INSERT INTO qa_threads (id, content_id, author_name, role, question, upvotes, is_resolved, created_at) VALUES
(1001, 10, 'Arjun Mehta', 'LEARNER', 'How do I handle transactional boundaries for multiple database connections in Spring Boot?', 4, true, CURRENT_TIMESTAMP - INTERVAL '1 day'),
(1002, 12, 'Priya Sharma', 'LEARNER', 'What is the recommended Redis cache TTL for high-frequency user profiles?', 2, false, CURRENT_TIMESTAMP - INTERVAL '12 hours')
ON CONFLICT (id) DO NOTHING;

-- 8. Insert Seed QA Replies
INSERT INTO qa_replies (id, thread_id, author_name, role, reply, upvotes, is_verified_answer, created_at) VALUES
(2001, 1001, 'Rohan Verma', 'CREATOR', 'You can use JtaTransactionManager or configure chained transaction managers depending on the datastores.', 5, true, CURRENT_TIMESTAMP - INTERVAL '18 hours'),
(2002, 1002, 'Neha Gupta', 'CREATOR', 'For profile metadata, a 15-minute TTL with cache eviction on profile updates works best.', 3, true, CURRENT_TIMESTAMP - INTERVAL '6 hours')
ON CONFLICT (id) DO NOTHING;

-- 9. Insert Seed Refresh Tokens (Auth Module)
INSERT INTO refresh_tokens (id, user_id, token, expiry_date, revoked) VALUES
(501, 101, 'sample-refresh-token-arjun-101', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(502, 202, 'sample-refresh-token-rohan-202', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(503, 303, 'sample-refresh-token-admin-303', CURRENT_TIMESTAMP + INTERVAL '7 days', false)
ON CONFLICT (id) DO NOTHING;


