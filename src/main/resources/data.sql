INSERT INTO users (email, password, first_name, last_name, user_name, is_ri_designer)
VALUES ('riannedumoulin@gmail.com', '$2a$12$hu9MMAWl2ve/UBbxY0ZYde6soxwCx9ZRGvWukK/XVgyk9z17X0miy', 'Rianne', 'van der Molen', 'RiDumoulin', true),
       ('olaf.holleman@outlook.com', '$2a$10$PWhlye6v88xBXenDW4W2U.qabxAWP1WbEte4aDXhQbFzJc3hV9fAC', 'Olaf', 'Holleman', 'Brolaf', true),
       ('catrina.hollander@gmail.com', '$2a$10$7uWUGqzHn2rs8N0LdVNN3O.hRE6uS5.jCtT6gKd1T2yCTIvyY3/YG', 'Catrina', 'Hollander', 'CHDesigner', true);

INSERT INTO authorities (email, authority)
VALUES ('riannedumoulin@gmail.com', 'ROLE_ADMIN'),
       ('olaf.holleman@outlook.com', 'ROLE_USER'),
       ('catrina.hollander@gmail.com', 'ROLE_USER');

INSERT INTO products (product_id, product_title, category, measurements, materials, description, price, email)
VALUES
    (1, 'Ovale eikenhouten tafel Knäke', 'Tafels', '220x100x75 cm', 'Eikenhout en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 250, 'olaf.holleman@outlook.com'),
    (2, 'Ribstof vintage stoel Seff', 'Stoelen & Fauteuils', '65x55x75 cm', 'Hout en stof', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 199.99, 'olaf.holleman@outlook.com'),
    (3, 'Tafeltjes Mia', 'Tafels', '50x40x30 cm', 'Hout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 179.99, 'riannedumoulin@gmail.com'),
    (4, 'Black & white sheep teddy chair', 'Stoelen & Fauteuils', '70x80x110 cm', 'Stof en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 289.99, 'riannedumoulin@gmail.com'),
    (5, 'Licht houten bed van pallets', 'Bedden', '204 x 160 x 80 cm', 'Pallets', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 89.99, 'catrina.hollander@gmail.com'),
    (6, 'Open kast met repurposed steigerhouten schuifdeur', 'Kasten', '80x43x122 cm', 'Hout, steigerhout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 229.99, 'catrina.hollander@gmail.com');

UPDATE products
SET email = 'olaf.holleman@outlook.com'
WHERE product_id IN (1, 2);

UPDATE products
SET email = 'riannedumoulin@gmail.com'
WHERE product_id IN (3, 4);


UPDATE products
SET email = 'catrina.hollander@gmail.com'
WHERE product_id IN (5, 6);

INSERT INTO delivery_options (product_id, delivery_options) VALUES
                                                                (1, 'Bezorgen'),
                                                                (1, 'Ophalen'),
                                                                (2, 'Bezorgen'),
                                                                (3, 'Ophalen'),
                                                                (4, 'Bezorgen'),
                                                                (4, 'Ophalen'),
                                                                (5, 'Bezorgen'),
                                                                (5, 'Ophalen'),
                                                                (6, 'Ophalen');

INSERT INTO shopping_carts (email) VALUES ('catrina.hollander@gmail.com');

INSERT INTO cart_products (cart_id, product_id) VALUES
                                                    ((SELECT cart_id FROM shopping_carts WHERE email = 'catrina.hollander@gmail.com'), 3),
                                                    ((SELECT cart_id FROM shopping_carts WHERE email = 'catrina.hollander@gmail.com'), 4);


INSERT INTO inquiries (inquiry_type, description, email, username)
VALUES ('Aanvraag RiDesign', 'Ik heb een tafel die er niet heel mooi meer uitziet door beschadigingen. Nu wilde ik eerst een nieuwe kopen, maar toen vond ik het toch wel een beetje zonde. Wellicht is het hout nog mooi. Zouden jullie een tafel willen RiDesignen?', 'catrina.hollander@gmail.com', 'CHDesigner'),
       ('Inleveren meubelstuk', 'Beste, ik heb nog een kast waarvan het hout nog goed is. De kast hoef ik zelf niet meer, ik breng de kast graag naar jullie.', 'olaf.holleman@outlook.com', 'Brolaf');



