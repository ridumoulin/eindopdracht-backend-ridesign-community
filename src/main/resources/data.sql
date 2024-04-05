INSERT INTO users (email, password, first_name, last_name, user_name, is_ri_designer)
VALUES ('riannedumoulin@gmail.com', '$2a$12$hu9MMAWl2ve/UBbxY0ZYde6soxwCx9ZRGvWukK/XVgyk9z17X0miy', 'Rianne', 'van der Molen', 'RiDumoulin', true),
       ('olaf.holleman@outlook.com', '$2a$10$PWhlye6v88xBXenDW4W2U.qabxAWP1WbEte4aDXhQbFzJc3hV9fAC', 'Olaf', 'Holleman', 'Brolaf', true),
       ('catrina.hollander@gmail.com', '$2a$10$7uWUGqzHn2rs8N0LdVNN3O.hRE6uS5.jCtT6gKd1T2yCTIvyY3/YG', 'Catrina', 'Hollander', 'CHDesigner', true);

INSERT INTO authorities (email, authority)
VALUES ('riannedumoulin@gmail.com', 'ROLE_ADMIN'),
       ('olaf.holleman@outlook.com', 'ROLE_USER'),
       ('catrina.hollander@gmail.com', 'ROLE_USER');

INSERT INTO products (product_id, product_title, category, measurements, materials, description, price)
VALUES
    (1, 'Ovale eikenhouten tafel Knäke', 'Tafels', '220x100x75 cm', 'Eikenhout en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 250),
    (2, 'Ribstof vintage stoel Seff', 'Stoelen & Fauteuils', '65x55x75 cm', 'Hout en stof', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 199.99),
    (3, 'Tafeltjes Mia', 'Tafels', '50x40x30 cm', 'Hout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 179.99),
    (4, 'Black & white sheep teddy chair', 'Stoelen & Fauteuils', '70x80x110 cm', 'Stof en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 289.99),
    (5, 'Licht houten bed van pallets', 'Bedden', '204 x 160 x 80 cm', 'Pallets', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 89.99),
    (6, 'Open kast met repurposed steigerhouten schuifdeur', 'Kasten', '80x43x122 cm', 'Hout, steigerhout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 229.99);

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
--
-- INSERT INTO image_data (name, type, image_data)
-- VALUES ('photo-profile-ri.png', 'image/png', <byte_array_for_image_1>),
--        ('photo-profile-olaf.jpeg', 'image/jpeg', <byte_array_for_image_2>),
--        ('photo-profile-catrina.png', 'image/png', <byte_array_for_image_3>);


-- INSERT INTO image_data (name, type, image_data)
-- VALUES ('photo-tables-1.jpeg', 'image/jpeg', <byte_array_for_image_4>),
--        ('photo-tables-2.jpeg', 'image/jpeg', <byte_array_for_image_5>),
--        ('photo-tables-3.jpeg', 'image/jpeg', <byte_array_for_image_6>),
--        ('photo-chair-olaf-1.jpeg', 'image/jpeg', <byte_array_for_image_7>),
--        ('photo-chair-olaf-2.jpeg', 'image/jpeg', <byte_array_for_image_8>),
--        ('photo-chair-olaf-3.jpeg', 'image/jpeg', <byte_array_for_image_9>),
--        ('photo-tables-ri-1.png', 'image/png', <byte_array_for_image_10>),
--        ('photo-tables-ri-2.png', 'image/png', <byte_array_for_image_11>),
--        ('photo-tables-ri-3.png', 'image/png', <byte_array_for_image_12>),
--        ('photo-chair-ri-1.JPG', 'image/jpeg', <byte_array_for_image_13>),
--        ('photo-chair-ri-2.JPG', 'image/jpeg', <byte_array_for_image_14>),
--        ('photo-chair-ri-3.JPG', 'image/jpeg', <byte_array_for_image_15>),
--        ('photo-bed-1.jpeg', 'image/jpeg', <byte_array_for_image_16>),
--        ('photo-bed-2.jpeg', 'image/jpeg', <byte_array_for_image_17>),
--        ('photo-bed-3.jpeg', 'image/jpeg', <byte_array_for_image_18>),
--        ('photo-closet-1.jpeg', 'image/jpeg', <byte_array_for_image_19>),
--        ('photo-closet-2.jpeg', 'image/jpeg', <byte_array_for_image_20>),
--        ('photo-closet-3.jpeg', 'image/jpeg', <byte_array_for_image_21>);


INSERT INTO shopping_carts (email) VALUES ('catrina.hollander@gmail.com');

INSERT INTO cart_products (cart_id, product_id) VALUES
                                                    ((SELECT cart_id FROM shopping_carts WHERE email = 'catrina.hollander@gmail.com'), 3),
                                                    ((SELECT cart_id FROM shopping_carts WHERE email = 'catrina.hollander@gmail.com'), 4);


INSERT INTO inquiries (inquiry_type, description, email, username)
VALUES ('Aanvraag RiDesign', 'Ik heb een tafel die er niet heel mooi meer uitziet door beschadigingen. Nu wilde ik eerst een nieuwe kopen, maar toen vond ik het toch wel een beetje zonde. Wellicht is het hout nog mooi. Zouden jullie een tafel willen RiDesignen?', 'catrina.hollander@gmail.com', 'CHDesigner'),
       ('Inleveren meubelstuk', 'Beste, ik heb nog een kast waarvan het hout nog goed is. De kast hoef ik zelf niet meer, ik breng de kast graag naar jullie.', 'olaf.holleman@outlook.com', 'Brolaf');



