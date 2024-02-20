INSERT INTO image_data (image_url) VALUES ('assets/photo-profile-ri.png');

INSERT INTO users (email, password, first_name, last_name, user_name, is_ri_designer)
VALUES ('riannedumoulin@gmail.com', 'RiisAdmin', 'Rianne', 'van der Molen', 'RiDumoulin', true);

UPDATE users SET image_data_id = (SELECT image_data_id FROM image_data WHERE image_url = 'assets/photo-profile-ri.png') WHERE email = 'riannedumoulin@gmail.com';

INSERT INTO authorities (user_id, authority)
VALUES ((SELECT user_id FROM users WHERE email = 'riannedumoulin@gmail.com'), 'ROLE_ADMIN');

