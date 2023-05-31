INSERT INTO carapp_schema.category (id, active, created_at, image, internal_name, name, name_ka, priority, updated_at)
VALUES ('7bf712f7-0f23-463a-ac8f-09b6873e35a0', true, '2023-05-29 17:35:28.501222', null, 'CAR_CLEANING',
        'car cleaning', 'ქიმ. წმენდა', 0, '2023-05-29 17:35:28.501222');
INSERT INTO carapp_schema.category (id, active, created_at, image, internal_name, name, name_ka, priority, updated_at)
VALUES ('c8ba8ae4-7fb0-4f23-862c-1d30ac89793f', true, '2023-05-29 17:35:53.222943', null, 'CAR_WASH', 'Car wash',
        'რეცხვა', 1, '2023-05-29 17:35:53.222943');

INSERT INTO carapp_schema.provider (id, email, logo, name, phone, website)
VALUES ('817d68c5-d394-4a44-aa0e-49027d440020', 'a@a.com', 'test_logo', 'Gia', 'test_phone', 'test_website');
INSERT INTO carapp_schema.provider (id, email, logo, name, phone, website)
VALUES ('7cbc597d-262f-4c6a-b2bb-68b94164d659', 'a@a.com', 'test_logo', 'KOTIKO', 'test_phone', 'test_website');

INSERT INTO carapp_schema.product (id, capacity, city, description, description_ka, district, images, lat, lng,
                                   main_image, name, name_ka, street, tags, category_id, provider_id)
VALUES ('12e8636e-b4a0-4f84-9db2-1c54b6954967', 1, 'tbilisi', 'Gldani car wash - description',
        'გლდანი სამრეცხაო - აღწერა', 'gldani',
        '["https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png"]', 41.791751,
        44.812271, 'https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png',
        'Gldani car wash', 'გლდანის სამრეცხაო', 'test Street', '["test tag"]', 'c8ba8ae4-7fb0-4f23-862c-1d30ac89793f',
        '7cbc597d-262f-4c6a-b2bb-68b94164d659');
INSERT INTO carapp_schema.product (id, capacity, city, description, description_ka, district, images, lat, lng,
                                   main_image, name, name_ka, street, tags, category_id, provider_id)
VALUES ('96219f5e-902f-41de-9bd7-5a63cd937c9f', 1, 'tbilisi', 'Digomi car wash - description',
        'დიღმის სამრეცხაო - აღწერა', 'gldani',
        '["https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png"]', 41.790985,
        44.759586, 'https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png',
        'Digomi car wash', 'დიღმის სამრეცხაო', 'test Street', '["test tag"]', 'c8ba8ae4-7fb0-4f23-862c-1d30ac89793f',
        '7cbc597d-262f-4c6a-b2bb-68b94164d659');
INSERT INTO carapp_schema.product (id, capacity, city, description, description_ka, district, images, lat, lng,
                                   main_image, name, name_ka, street, tags, category_id, provider_id)
VALUES ('97ccf808-7091-4d87-949c-34df6e81f1c2', 1, 'tbilisi', 'GIA''s car wash - description',
        'გიას სამრეცხაო - აღწერა', 'gldani',
        '["https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png"]', 41.690985,
        44.792271, 'https://dvrwno76ljysq.cloudfront.net/carwash/41e06cca-571a-4ff6-811a-ffcfe222dca0.png',
        'GIA''s car wash', 'გიას სამრეცხაო', 'test Street', '["test tag"]', 'c8ba8ae4-7fb0-4f23-862c-1d30ac89793f',
        '817d68c5-d394-4a44-aa0e-49027d440020');


INSERT INTO carapp_schema.product_details (id, available_services, average_duration_minutes, currency, description,
                                           description_ka, name, name_ka, not_available_services, prices_for_car_types,
                                           product_id)
VALUES ('3f9eaf86-9360-46c9-b38b-4350c9b00846', '[{"en":"wash from up","ka":"რეხვა ზემოდან"}]', 15, 'GEL', 'FULL Wash',
        'სრული რეცხვა', 'FULL Wash', 'სრული რეცხვა', '[{"en":"wash from down","ka":"რეხვა ქვემოდან"}]',
        '[{"order":0,"carType":"ALL","price":10000}]', '97ccf808-7091-4d87-949c-34df6e81f1c2');
INSERT INTO carapp_schema.product_details (id, available_services, average_duration_minutes, currency, description,
                                           description_ka, name, name_ka, not_available_services, prices_for_car_types,
                                           product_id)
VALUES ('e514cd6d-c001-4154-85fb-4cbaced0b2e9', '[{"en":"wash from up","ka":"რეხვა ზემოდან 2"}]', 15, 'GEL',
        'FULL Wash', 'სრული რეცხვა 2', 'FULL Wash', 'სრული რეცხვა 2',
        '[{"en":"wash from down","ka":"რეხვა ქვემოდან 2"}]', '[{"order":0,"carType":"SEDAN","price":15000}]',
        '96219f5e-902f-41de-9bd7-5a63cd937c9f');
INSERT INTO carapp_schema.product_details (id, available_services, average_duration_minutes, currency, description,
                                           description_ka, name, name_ka, not_available_services, prices_for_car_types,
                                           product_id)
VALUES ('908e6336-9e8b-4499-ac6f-1c41c899d476', '[{"en":"wash from up","ka":"რეხვა ზემოდან 3"}]', 15, 'GEL',
        'FULL Wash 3', 'სრული რეცხვა 3', 'FULL Wash 3', 'სრული რეცხვა 3',
        '[{"en":"wash from down","ka":"რეხვა ქვემოდან 3"}]', '[{"order":0,"carType":"SEDAN","price":15000}]',
        '96219f5e-902f-41de-9bd7-5a63cd937c9f');
INSERT INTO carapp_schema.product_details (id, available_services, average_duration_minutes, currency, description,
                                           description_ka, name, name_ka, not_available_services, prices_for_car_types,
                                           product_id)
VALUES ('e57809e8-eec1-47fe-9c36-af73e0b9e5b0', '[{"en":"wash from up","ka":"რეხვა ზემოდან 3"}]', 15, 'GEL',
        'FULL Wash 4', 'სრული რეცხვა 4', 'FULL Wash 4', 'სრული რეცხვა 4',
        '[{"en":"wash from down","ka":"რეხვა ქვემოდან 3"}]', '[{"order":0,"carType":"ALL","price":15000}]',
        '96219f5e-902f-41de-9bd7-5a63cd937c9f');
INSERT INTO carapp_schema.product_details (id, available_services, average_duration_minutes, currency, description,
                                           description_ka, name, name_ka, not_available_services, prices_for_car_types,
                                           product_id)
VALUES ('8a861d52-1a35-49f5-b3f0-0d3be758982b', '[{"en":"wash from up","ka":"რეხვა ზემოდან"}]', 15, 'GEL', 'FULL Wash',
        'სრული რეცხვა', 'FULL Wash', 'სრული რეცხვა', '[{"en":"wash from down","ka":"რეხვა ქვემოდან"}]',
        '[{"order":0,"carType":"ALL","price":15000}]', '12e8636e-b4a0-4f84-9db2-1c54b6954967');



INSERT INTO carapp_schema.car (id, car_type, created_at, make, model, plate_number, tech_pass_number, updated_at, vin,
                               year, owner_id)
VALUES ('e7c5d6cc-f9d6-464c-8340-745546f79ce6', 'HATCHBACK', '2023-05-29 16:34:08.255822', 'test_make', 'test_model',
        'test_plateNumber', 'test_techPassportNumber', '2023-05-29 16:34:08.255822', 'test_vin', 2012,
        '87710255-d8c4-407c-acbd-13d3c66305a5');

INSERT INTO carapp_schema.car (id, car_type, created_at, make, model, plate_number, tech_pass_number, updated_at, vin,
                               year, owner_id)
VALUES ('7d5cea24-17b5-4d50-bf8d-bbd342e7158f', 'SUV', '2023-05-30 00:56:33.247410', 'test_make', 'test_model',
        'AA-123-AA', 'test_techPassportNumber', '2023-05-30 00:56:33.247410', 'test_vin', 2012,
        '87710255-d8c4-407c-acbd-13d3c66305a5');
