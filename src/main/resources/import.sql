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


