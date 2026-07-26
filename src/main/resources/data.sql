-- Default lookup categories for LearnHub Catalog module
-- Ensures all group members start with the same default categories
INSERT INTO categories (id, name, resource_count) VALUES
(1, 'Java', 0),
(2, 'DSA', 0),
(3, 'System Design', 0),
(4, 'Web Dev', 0),
(5, 'Cloud Computing', 0)
ON CONFLICT DO NOTHING;
