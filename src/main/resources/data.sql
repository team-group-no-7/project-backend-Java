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
(2, 'DSA', 2),
(3, 'System Design', 1),
(4, 'Web Dev', 2),
(5, 'SQL & DB', 1),
(6, 'DevOps & Docker', 1)
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Seed Contents (All 18 Attributes Fully Populated)
INSERT INTO contents (id, title, description, preview_text, content_body, file_url, price, type, level, tags, featured, is_trending, rating, reviews_count, learners_count, category_id, creator_id, created_at) VALUES
(10, 'Complete Java Spring Boot Monolith & Microservices', 'Learn microservices, transactions, JPA, and security architecture.', 'Chapter 1: Configuration, Chapter 2: Security & JWT', '<h2>Complete Java Spring Boot Guide</h2><p>Spring Boot simplifies building production-ready applications with Java. It eliminates boilerplate XML configuration using auto-configuration and Spring Boot starters.</p><h3>Key Features</h3><ul><li>Embedded Tomcat / Jetty server</li><li>Spring Data JPA & Hibernate integration</li><li>Spring Security & JWT Authentication</li><li>Spring Boot Actuator health metrics</li></ul><p>Use Spring Security filters to enforce JWT authorization and RBAC role checks on protected REST endpoints.</p>', '/uploads/pdfs/sample-spring-boot.pdf', 499.00, 'ARTICLE', 'Intermediate', 'Java,Spring Boot,Backend', true, true, 4.85, 2, 1420, 1, 202, CURRENT_TIMESTAMP - INTERVAL '10 days'),
(11, 'Data Structures & Algorithms Interview Cheat Sheets', 'High-quality reference sheets for FAANG & top tech interviews.', 'Big O notation cheat sheet, Trees, Graphs & Dynamic Programming', '<h2>DSA Interview Cheat Sheet</h2><p>Comprehensive algorithmic complexity and data structure reference guide covering Arrays, Linked Lists, Binary Trees, Graphs, and Dynamic Programming.</p>', '/uploads/pdfs/sample-dsa-cheatsheet.pdf', 0.00, 'PDF', 'Beginner', 'DSA,Interview,C++', false, true, 4.50, 1, 980, 2, 202, CURRENT_TIMESTAMP - INTERVAL '8 days'),
(12, 'System Design Masterclass & Architectural Blueprint', 'Enterprise scale system design diagrams, load balancing & caching strategies.', 'Scalability fundamentals, Sharding, Redis Caching, CDN strategies', '<h2>System Design Masterclass</h2><p>Learn how to design high-throughput distributed systems capable of handling millions of concurrent requests.</p><h3>Core Topics</h3><ul><li>Load Balancing (Nginx, HAProxy)</li><li>Database Sharding & Read Replicas</li><li>Distributed Caching Strategies (Redis, Memcached)</li><li>Asynchronous Event-Driven Messaging (Kafka, RabbitMQ)</li></ul>', '/uploads/pdfs/sample-system-design.pdf', 799.00, 'ARTICLE', 'Advanced', 'System Design,Architecture,Distributed', true, false, 4.90, 3, 2150, 3, 203, CURRENT_TIMESTAMP - INTERVAL '6 days'),
(13, 'React 19 & Next.js Production Architecture', 'Modern React patterns, SSR, Tailwind CSS, and state management.', 'React Server Components, Hooks, State Engines, Performance Optimization', '<h2>React 19 & Next.js Production Architecture</h2><p>Master modern web application development using React 19 Server Components, Next.js App Router, and Tailwind CSS.</p><h3>Key Concepts</h3><ul><li>React Server Components (RSC)</li><li>Client-Side State Management with Zustand / Context</li><li>Optimistic UI Updates and Hydration</li></ul>', '/uploads/pdfs/sample-react-guide.pdf', 399.00, 'ARTICLE', 'Intermediate', 'React,Frontend,JavaScript', false, true, 4.75, 1, 1680, 4, 204, CURRENT_TIMESTAMP - INTERVAL '4 days'),
(14, 'Docker & Kubernetes CI/CD Production Cheat Sheet', 'Step-by-step containerization guide for microservices.', 'Dockerfiles, K8s Deployment Manifests, Helm Charts, GitHub Actions', '<h2>Docker & Kubernetes CI/CD Cheat Sheet</h2><p>Production containerization guide and Kubernetes deployment manifests for Java microservices.</p>', '/uploads/pdfs/sample-docker-k8s.pdf', 0.00, 'PDF', 'Beginner', 'Docker,Kubernetes,DevOps', true, false, 4.95, 2, 1120, 6, 202, CURRENT_TIMESTAMP - INTERVAL '2 days'),
(15, 'Mastering SQL & Database Indexing', 'Comprehensive guide to complex joins, subqueries, B-Tree indexes, and query optimization.', 'Module 1: Joins, Group By, Indexing, and EXPLAIN ANALYZE', '<h2>Mastering SQL & Database Indexing</h2><p>Learn how to write efficient SQL queries and optimize database performance using PostgreSQL indexing strategies.</p><h3>Topics Covered</h3><ul><li>B-Tree & Hash Indexes</li><li>Execution Plans & Query Tuning</li><li>Transaction Isolation Levels</li></ul>', '/uploads/pdfs/sample-sql-indexing.pdf', 349.00, 'ARTICLE', 'Intermediate', 'SQL,PostgreSQL,Database', false, true, 4.80, 2, 1120, 5, 203, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(16, 'Dynamic Programming Masterclass Notes', 'Step-by-step 1D/2D DP problems solved using Memoization & Tabulation.', 'Chapter 1: 0/1 Knapsack, Unbounded Knapsack, and Subsequences', '<h2>Dynamic Programming Masterclass Notes</h2><p>Complete reference manual for mastering Dynamic Programming interview problems with recursion tree visualizers and state transition equations.</p>', '/uploads/pdfs/sample-dynamic-programming.pdf', 249.00, 'PDF', 'Advanced', 'DSA,Dynamic Programming,C++', true, false, 4.85, 1, 1650, 2, 203, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(17, 'Fullstack Node.js & Express API Handbook', 'Build secure RESTful APIs with Express.js, JWT Authentication, and MongoDB.', 'Lesson 1: Express Router, Middleware, and JWT Security', '<h2>Fullstack Node.js & Express API Handbook</h2><p>Production guide for building scalable REST APIs using Node.js, Express, and JWT security middleware.</p><h3>Key Features</h3><ul><li>Custom Express Middlewares</li><li>JWT Token Validation</li><li>Rate Limiting & CORS Configuration</li></ul>', '/uploads/pdfs/sample-nodejs-express.pdf', 399.00, 'ARTICLE', 'Beginner', 'Node.js,Express,JavaScript', false, false, 4.65, 1, 780, 4, 204, CURRENT_TIMESTAMP - INTERVAL '7 days')
ON CONFLICT (id) DO NOTHING;

-- 4. Insert Seed Purchases
INSERT INTO purchases (id, user_id, content_id, amount_paid, payment_status, transaction_id, purchased_at) VALUES
(701, 101, 10, 499.00, 'SUCCESS', 'pay_sample_arjun_101_10', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(702, 101, 12, 799.00, 'SUCCESS', 'pay_sample_arjun_101_12', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(703, 102, 13, 399.00, 'SUCCESS', 'pay_sample_priya_102_13', CURRENT_TIMESTAMP - INTERVAL '5 hours')
ON CONFLICT (id) DO NOTHING;

-- 5. Insert Seed Reviews (Covering all contents)
INSERT INTO reviews (id, content_id, user_id, rating, student_name, avatar_url, review_date, review_text) VALUES
(801, 10, 101, 5, 'Arjun Mehta', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150', '2026-08-03', 'Outstanding Spring Boot architecture guide! Very clear JPA and security examples.'),
(802, 12, 101, 5, 'Arjun Mehta', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150', '2026-08-04', 'The System Design templates saved me weeks of preparation for senior role interviews.'),
(803, 13, 102, 5, 'Priya Sharma', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150', '2026-08-05', 'Comprehensive React 19 breakdown. Exactly what I needed for my final year project.'),
(804, 10, 102, 5, 'Priya Sharma', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150', '2026-07-20', 'The microservice patterns and JWT authorization details are exceptionally structured.'),
(805, 11, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'Exceptionally clear explanation in Data Structures & Algorithms Interview Cheat Sheets!'),
(806, 11, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'The concepts in Data Structures & Algorithms Interview Cheat Sheets helped me pass my technical assessment easily.'),
(807, 12, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'High throughput distributed caching and load balancing concepts are explained brilliantly.'),
(808, 13, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'React Server Components and state hooks are laid out cleanly with production blueprints.'),
(809, 14, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'Exceptionally clear explanation in Docker & Kubernetes CI/CD Production Cheat Sheet!'),
(810, 14, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'The concepts in Docker & Kubernetes CI/CD Production Cheat Sheet helped me deploy microservices smoothly.'),
(811, 15, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'Mastering SQL & Database Indexing cleared up all my doubts regarding B-Tree execution plans.'),
(812, 15, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'Subqueries, joins, and transaction isolation levels explained in great detail.'),
(813, 16, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'Dynamic Programming Memoization and Tabulation notes are top notch!'),
(814, 16, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'Recursion state transitions made DP interview prep so much easier.'),
(815, 17, 101, 5, 'Aarav Sharma', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '2026-07-15', 'Fullstack Node.js & Express API Handbook is practical and production ready.'),
(816, 17, 102, 5, 'Priya Patel', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '2026-07-20', 'Great middleware patterns and rate limiting examples.')
ON CONFLICT (id) DO NOTHING;

-- 6. Insert Seed Doubt Sessions (Mentorship)
INSERT INTO doubt_sessions (id, learner_id, creator_id, topic, session_price, duration_minutes, booking_status, payment_status, jitsi_room_name, scheduled_at) VALUES
(901, 101, 202, 'Spring Security OAuth2 & JWT Customization', 450.00, 60, 'APPROVED', 'PAID', 'learnhub-spring-security-901', CURRENT_TIMESTAMP + INTERVAL '1 day'),
(902, 101, 203, 'System Design High Availability Strategy', 500.00, 60, 'APPROVED', 'PAID', 'learnhub-sysdesign-902', CURRENT_TIMESTAMP + INTERVAL '2 days')
ON CONFLICT (id) DO NOTHING;

-- 7. Insert Seed QA Threads (Q&A Discussions for contents)
INSERT INTO qa_threads (id, content_id, author_name, role, question, upvotes, is_resolved, created_at) VALUES
(1001, 10, 'Arjun Mehta', 'LEARNER', 'How do I handle transactional boundaries for multiple database connections in Spring Boot?', 4, true, CURRENT_TIMESTAMP - INTERVAL '1 day'),
(1002, 12, 'Priya Sharma', 'LEARNER', 'What is the recommended Redis cache TTL for high-frequency user profiles?', 2, false, CURRENT_TIMESTAMP - INTERVAL '12 hours'),
(1003, 11, 'Priya Sharma', 'LEARNER', 'In section 2 of Data Structures & Algorithms Interview Cheat Sheets, what is the recommended pattern for tree traversal?', 5, true, CURRENT_TIMESTAMP - INTERVAL '2 days'),
(1004, 13, 'Priya Sharma', 'LEARNER', 'In section 2 of React 19 & Next.js Production Architecture, what is the recommended state management engine?', 3, true, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(1005, 14, 'Arjun Mehta', 'LEARNER', 'In section 2 of Docker & Kubernetes CI/CD Production Cheat Sheet, how do we configure liveness probes?', 4, true, CURRENT_TIMESTAMP - INTERVAL '4 days'),
(1006, 15, 'Priya Sharma', 'LEARNER', 'In section 2 of Mastering SQL & Database Indexing, when should we use partial indexes vs composite indexes?', 2, true, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(1007, 16, 'Arjun Mehta', 'LEARNER', 'In section 2 of Dynamic Programming Masterclass Notes, how do we optimize space complexity from O(N*M) to O(M)?', 6, true, CURRENT_TIMESTAMP - INTERVAL '6 days'),
(1008, 17, 'Priya Sharma', 'LEARNER', 'In section 2 of Fullstack Node.js & Express API Handbook, how do we handle refresh token rotation safely?', 3, true, CURRENT_TIMESTAMP - INTERVAL '7 days')
ON CONFLICT (id) DO NOTHING;

-- 8. Insert Seed QA Replies
INSERT INTO qa_replies (id, thread_id, author_name, role, reply, upvotes, is_verified_answer, created_at) VALUES
(2001, 1001, 'Rohan Verma', 'CREATOR', 'You can use JtaTransactionManager or configure chained transaction managers depending on the datastores.', 5, true, CURRENT_TIMESTAMP - INTERVAL '18 hours'),
(2002, 1002, 'Neha Gupta', 'CREATOR', 'For profile metadata, a 15-minute TTL with cache eviction on profile updates works best.', 3, true, CURRENT_TIMESTAMP - INTERVAL '6 hours'),
(2003, 1003, 'Rohan Verma', 'CREATOR', 'Great question! For Data Structures & Algorithms, ensure you follow the iterative stack/queue traversal structure.', 8, true, CURRENT_TIMESTAMP - INTERVAL '1 day'),
(2004, 1004, 'Vikramaditya Rao', 'CREATOR', 'Zustand or Context API combined with React Server Components provides a clean separation of concerns.', 7, true, CURRENT_TIMESTAMP - INTERVAL '2 days'),
(2005, 1005, 'Rohan Verma', 'CREATOR', 'Use HTTP GET probes on your /actuator/health endpoint in your Kubernetes deployment manifest.', 6, true, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(2006, 1006, 'Neha Gupta', 'CREATOR', 'Partial indexes are best when querying a small subset of rows (e.g. status = PENDING), saving memory space.', 5, true, CURRENT_TIMESTAMP - INTERVAL '4 days'),
(2007, 1007, 'Neha Gupta', 'CREATOR', 'Keep only the current and previous rows in memory instead of maintaining the full 2D grid matrix.', 9, true, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(2008, 1008, 'Vikramaditya Rao', 'CREATOR', 'Issue a new refresh token on every usage and revoke the previous token in PostgreSQL refresh_tokens table.', 4, true, CURRENT_TIMESTAMP - INTERVAL '6 hours')
ON CONFLICT (id) DO NOTHING;

-- 9. Insert Seed Refresh Tokens (Auth Module)
INSERT INTO refresh_tokens (id, user_id, token, expiry_date, revoked) VALUES
(501, 101, 'sample-refresh-token-arjun-101', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(502, 202, 'sample-refresh-token-rohan-202', CURRENT_TIMESTAMP + INTERVAL '7 days', false),
(503, 303, 'sample-refresh-token-admin-303', CURRENT_TIMESTAMP + INTERVAL '7 days', false)
ON CONFLICT (id) DO NOTHING;


