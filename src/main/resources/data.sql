INSERT INTO image_data (image_url) VALUES ('assets/photo-profile-ri.png');
INSERT INTO image_data (image_url) VALUES ('assets/photo-profile-olaf.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/photo-profile-catrina.png');


INSERT INTO users (user_id, email, password, first_name, last_name, user_name, is_ri_designer, image_data_id)
VALUES (1, 'riannedumoulin@gmail.com', '$2a$10$0MNh5e7DP0vNV44QK10biu28kzxY3IEH47O6vX96rjChFstkg54S6', 'Rianne', 'van der Molen', 'RiDumoulin', true, 1),
       (2, 'olaf.holleman@outlook.com', '$2a$10$PWhlye6v88xBXenDW4W2U.qabxAWP1WbEte4aDXhQbFzJc3hV9fAC', 'Olaf', 'Holleman', 'Brolaf', true, 2),
       (3, 'catrina.hollander@gmail.com', '$2a$10$7uWUGqzHn2rs8N0LdVNN3O.hRE6uS5.jCtT6gKd1T2yCTIvyY3/YG', 'Catrina', 'Hollander', 'CHDesigner', true, 3);


INSERT INTO authorities (user_id, authority)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER');


INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-1.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-2.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-3.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-olaf-1.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-olaf-2.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-olaf-3.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-ri-1.png');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-ri-2.png');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-tables-ri-3.png');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-ri-1.JPG');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-ri-2.JPG');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-chair-ri-3.JPG');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-bed-1.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-bed-2.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-bed-3.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-closet-1.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-closet-2.jpeg');
INSERT INTO image_data (image_url) VALUES ('assets/products/photo-closet-3.jpeg');


INSERT INTO images (product_id, image_data_id) VALUES (1, 1);
INSERT INTO images (product_id, image_data_id) VALUES (1, 2);
INSERT INTO images (product_id, image_data_id) VALUES (1, 3);
INSERT INTO images (product_id, image_data_id) VALUES (2, 4);
INSERT INTO images (product_id, image_data_id) VALUES (2, 5);
INSERT INTO images (product_id, image_data_id) VALUES (2, 6);
INSERT INTO images (product_id, image_data_id) VALUES (3, 7);
INSERT INTO images (product_id, image_data_id) VALUES (3, 8);
INSERT INTO images (product_id, image_data_id) VALUES (3, 9);
INSERT INTO images (product_id, image_data_id) VALUES (4, 10);
INSERT INTO images (product_id, image_data_id) VALUES (4, 11);
INSERT INTO images (product_id, image_data_id) VALUES (4, 12);
INSERT INTO images (product_id, image_data_id) VALUES (5, 13);
INSERT INTO images (product_id, image_data_id) VALUES (5, 14);
INSERT INTO images (product_id, image_data_id) VALUES (5, 15);
INSERT INTO images (product_id, image_data_id) VALUES (6, 16);
INSERT INTO images (product_id, image_data_id) VALUES (6, 17);
INSERT INTO images (product_id, image_data_id) VALUES (6, 18);

INSERT INTO products (product_id, product_title, category, measurements, materials, description, price, user_id)
VALUES (1, 'Ovale eikenhouten tafel Knäke', 'Tafels', '220x100x75 cm', 'Eikenhout en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 250, 2),
       (2, 'Ribstof vintage stoel Seff', 'Stoelen & Fauteuils', '65x55x75 cm', 'Hout en stof', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 199.99, 2),
       (3, 'Tafeltjes Mia', 'Tafels', '50x40x30 cm', 'Hout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 179.99, 1),
       (4, 'Black & white sheep teddy chair', 'Stoelen & Fauteuils', '70x80x110 cm', 'Stof en staal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 289.99, 1),
       (5, 'Licht houten bed van pallets', 'Bedden', '204 x 160 x 80 cm', 'Pallets', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 89.99, 3),
       (6, 'Open kast met repurposed steigerhouten schuifdeur', 'Kasten', '80x43x122 cm', 'Hout, steigerhout', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.', 229.99);


SET @catrina_user_id = (SELECT user_id FROM users WHERE email = 'catrina.hollander@gmail.com');

SET @catrina_cart_id = (SELECT cart_id FROM shopping_carts WHERE user_id = @catrina_user_id);

INSERT INTO cart_products (cart_id, product_id) VALUES
                                                    (@catrina_cart_id, 3),
                                                    (@catrina_cart_id, 4);


INSERT INTO inquiries (inquiry_type, description, email, username)
VALUES ('Aanvraag RiDesign', 'Ik heb een tafel die er niet heel mooi meer uitziet door beschadigingen. Nu wilde ik eerst een nieuwe kopen, maar toen vond ik het toch wel een beetje zonde. Wellicht is het hout nog mooi. Zouden jullie een tafel willen RiDesignen?', 'catrina.hollander@gmail.com', 'CHDesigner'),
       ('Inleveren meubelstuk', 'Beste, ik heb nog een kast waarvan het hout nog goed is. De kast hoef ik zelf niet meer, ik breng de kast graag naar jullie.', 'olaf.holleman@outlook.com', 'Brolaf');



