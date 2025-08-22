-- liquibase formatted sql

-- changeset Luiz:20250106-02-02
INSERT INTO products (id, name, quantity, price)
VALUES
    (gen_random_uuid(),'Camiseta Básica',10, 19.99),
    (gen_random_uuid(),'Calça Jeans',15, 29.99),
    (gen_random_uuid(),'Tênis Esportivo',5, 49.90),
    (gen_random_uuid(),'Boné Preto',20, 9.99),
    (gen_random_uuid(),'Jaqueta de Couro',8, 99.50),
    (gen_random_uuid(),'Meias Algodão',12, 5.75),
    (gen_random_uuid(),'Cinto Masculino',30, 14.90),
    (gen_random_uuid(),'Blusa Moletom',25, 39.99),
    (gen_random_uuid(),'Camisa Polo',18, 12.50),
    (gen_random_uuid(),'Shorts Jeans',22, 17.45),
    (gen_random_uuid(),'Relógio Analógico',13, 55.00),
    (gen_random_uuid(),'Carteira Couro',7, 33.33),
    (gen_random_uuid(),'Óculos de Sol',16, 22.22),
    (gen_random_uuid(),'Chinelo Havaianas',11, 18.75),
    (gen_random_uuid(),'Sapato Social',6, 60.00),
    (gen_random_uuid(),'Gorro Inverno',9, 7.45),
    (gen_random_uuid(),'Perfume Importado',14, 88.88),
    (gen_random_uuid(),'Mochila Escolar',10, 45.00),
    (gen_random_uuid(),'Camisa Social',17, 21.30),
    (gen_random_uuid(),'Camiseta Estampada',19, 19.99);


-- -- changeset Luiz:20250106-02-03
-- INSERT INTO suppliers (name, cnpj, address, phone, email, active, version, created_at)
-- VALUES
--     ('SUPERMERCADO OLIVEIRA', '01234567000189', 'Rua das Flores, 123, Sao Paulo - SP', '11987654321', 'contato@oliveira.com.br', true, 1, now()),
--     ('DISTRIBUIDORA MARTINS', '11234567000145', 'Av. Central, 987, Rio de Janeiro - RJ', '21985473210', 'vendas@martinsdistribuidora.com', true, 1, now()),
--     ('FARMACIA SAUDE', '21234567000178', 'Rua da Saude, 345, Salvador - BA', '71912345678', 'farmacia@saude.com.br', true, 1, now()),
--     ('CONSTRUTORA ROCHA', '31234567000156', 'Av. dos Trabalhadores, 120, Belo Horizonte - MG', '31923456789', 'contato@rocha.com.br', true, 1, now()),
--     ('MERCADO PONTO CERTO', '41234567000167', 'Rua das Palmeiras, 450, Recife - PE', '81987654321', 'contato@pontocerto.com', false, 1, now()),
--     ('PANIFICADORA UNIAO', '51234567000123', 'Av. Brasil, 987, Fortaleza - CE', '85912345678', 'vendas@uniaopanificadora.com', true, 1, now()),
--     ('ATACADAO DO NORTE', '61234567000154', 'Rua Amazonas, 200, Manaus - AM', '92998765432', 'contato@atacadaodonorte.com', true, 1, now()),
--     ('LIVRARIA CENTRAL', '71234567000189', 'Rua do Comercio, 300, Curitiba - PR', '41987654321', 'vendas@livrariacentral.com', false, 1, now()),
--     ('PAPELARIA ESTRELA', '81234567000145', 'Av. dos Estudantes, 234, Porto Alegre - RS', '51912345678', 'contato@estrela.com', true, 1, now()),
--     ('ELETRONICOS SILVA', '91234567000178', 'Rua das Industrias, 456, Florianopolis - SC', '48987654321', 'suporte@eletronicossilva.com', true, 1, now()),
--     ('SUPERMERCADO LUZ', '11234567000112', 'Av. Principal, 789, Natal - RN', '84912345678', 'contato@supermercadoluz.com', false, 1, now()),
--     ('DISTRIBUIDORA ABC', '21234567000134', 'Rua do Progresso, 123, Teresina - PI', '86987654321', 'vendas@abcdistribuidora.com', true, 1, now()),
--     ('FARMACIA POPULAR', '31234567000167', 'Av. Joao Pessoa, 567, Belem - PA', '91912345678', 'farmacia@popular.com.br', true, 1, now()),
--     ('CONSTRUTORA ALMEIDA', '41234567000189', 'Rua das Acacias, 789, Goiania - GO', '62987654321', 'contato@almeidaconstrutora.com', false, 1, now()),
--     ('PANIFICADORA DOCE SABOR', '51234567000145', 'Av. Santa Luzia, 345, Campo Grande - MS', '67912345678', 'vendas@docesabor.com', true, 1, now()),
--     ('ATACADISTA SUL', '61234567000178', 'Rua das Hortensias, 890, Blumenau - SC', '47987654321', 'contato@sulatacadista.com', true, 1, now()),
--     ('LIVRARIA DO ESTUDANTE', '71234567000156', 'Av. Independencia, 1234, Vitoria - ES', '27912345678', 'vendas@livrariadoestudante.com', true, 1, now()),
--     ('PAPELARIA ESCOLAR', '81234567000123', 'Rua do Aprendizado, 567, Aracaju - SE', '79987654321', 'contato@escolar.com', true, 1, now()),
--     ('ELETRONICOS RIBEIRO', '91234567000134', 'Av. Rio Branco, 890, Joao Pessoa - PB', '83912345678', 'suporte@ribeiroeletronicos.com', true, 1, now()),
--     ('SUPERMERCADO ESPERANCA', '01234567000178', 'Rua da Paz, 456, Maceio - AL', '82987654321', 'contato@superesperanca.com', false, 1, now()),
--     ('DISTRIBUIDORA VITORIA', '11234567000156', 'Av. das Nacoes, 345, Sao Luis - MA', '98912345678', 'vendas@distribuidoravitoria.com', true, 1, now()),
--     ('FARMACIA BEM ESTAR', '21234567000123', 'Rua do Sossego, 123, Boa Vista - RR', '95987654321', 'farmacia@bemestar.com', true, 1, now()),
--     ('CONSTRUTORA BETA', '31234567000145', 'Av. das Construcoes, 567, Palmas - TO', '63912345678', 'contato@betaconstrutora.com', true, 1, now()),
--     ('PANIFICADORA DO NORTE', '41234567000178', 'Rua do Trigo, 890, Macapa - AP', '96987654321', 'vendas@panificadora.com', false, 1, now()),
--     ('ATACADISTA ALPHA', '51234567000156', 'Av. Brasil, 234, Rio Branco - AC', '68912345678', 'contato@alphaatacadista.com', true, 1, now());


