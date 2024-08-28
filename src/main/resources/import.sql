    /* Produtos */
insert into produto (descricao, valor)values ('Teclado', 250.00);
insert into produto (descricao, valor)values ('Mouse', 150.00);
insert into produto (descricao, valor)values ('Monitor', 1100.00);
insert into produto (descricao, valor)values ('Gabinete', 200.00);
insert into produto (descricao, valor)values ('Ps5', 5000.00);


insert into pessoa (nome, telefone)values ('Ermeson', '63981201914');
insert into pessoa_fisica (id, cpf)values (1, '608.794.583-05');

insert into pessoa (nome, telefone)values ('João Victor', '63991164826');
insert into pessoa_fisica (id, cpf)values (2, '558.879.300-55');

insert into pessoa (nome, telefone)values ('TI Balbinot', '32325632');
insert into pessoa_juridica (id, cnpj)values (3, '73.612.851/0001-69');

-- /* Vendas */
insert into venda (data_hora, pessoa_id) values ('2024-03-10', 1);
insert into venda (data_hora, pessoa_id) values ('2024-03-11', 3);
insert into venda (data_hora, pessoa_id) values ('2024-03-12', 2);
insert into venda (data_hora, pessoa_id) values ('2024-03-12', 3);
--
--
--
-- -- /* Itens de Venda */
-- -- /* venda id:1 */
insert into item_venda (quantidade, produto_id, venda_id) values (2, 1, 1);
insert into item_venda (quantidade, produto_id, venda_id) values (3, 2, 1);
--
--
-- -- /* venda id:2 */
insert into item_venda (quantidade, produto_id, venda_id) values (1, 1, 2);
insert into item_venda (quantidade, produto_id, venda_id) values (1, 2, 2);
insert into item_venda (quantidade, produto_id, venda_id) values (2, 3, 2);
insert into item_venda (quantidade, produto_id, venda_id) values (1, 4, 2);
--
-- -- /* venda id:3 */
insert into item_venda (quantidade, produto_id, venda_id) values (3, 5, 3);

insert into item_venda (quantidade, produto_id, venda_id) values (2, 2, 4);

insert into role (nome) values ('ROLE_ADMIN');
insert into role (nome) values ('ROLE_USER');

insert into usuario (pessoa_id, username, password) values (1, 'Ermeson', '$2a$10$q8Nph97Tlvuli8mW/rkD/eutMqnEHB0eStyf2px3GnQWKI3JBCUWK');
insert into usuario (pessoa_id, username, password) values (3, 'TI Balbinot', '$2a$10$Lqdi9O/MCO8Td4MDQkR.Ce9VUXuOiCLk9LUNsp7aJqSXnTQh004t6');

insert into usuario_roles (roles_id, usuarios_id) values (1, 1);
insert into usuario_roles (roles_id, usuarios_id) values (2, 2);
