-- 4. INSERT SAMPLE EVENTS
INSERT INTO events
(name, event_date, location, total_budget)
VALUES
    ('Birthday Party', '2026-11-15', 'Jaipur', 50000.00),
    ('College Fest', '2026-12-10', 'College Campus', 100000.00),
    ('Wedding', '2027-02-20', 'Udaipur', 500000.00);

-- 5. INSERT SAMPLE EXPENSES
INSERT INTO expenses
(event_id, category, amount, description, expense_date)
VALUES
    (1, 'Decoration', 5000.00,
     'Stage and balloon decoration', '2026-11-01'),

    (1, 'Food', 12000.00,
     'Food and catering', '2026-11-02'),

    (1, 'Photography', 8000.00,
     'Photographer booking', '2026-11-03'),

    (2, 'Sound', 15000.00,
     'Sound system rental', '2026-12-01'),

    (2, 'Decoration', 10000.00,
     'Stage decoration', '2026-12-02'),

    (3, 'Venue', 80000.00,
     'Venue advance payment', '2027-02-01');