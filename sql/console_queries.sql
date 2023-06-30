-- SELECT ALL TABLES IN SCHEMA

select *
from information_schema.tables
where table_schema = 'public';

-- DROP AND CREATE TABLES

drop table if exists account,atm,bank;
create table if not exists bank
(
    id   serial primary key,
    name varchar(256) not null
);
create table if not exists account
(
    id              serial primary key,
    account_number  int not null,
    account_balance int not null default 0,
    bank_id         int references bank (id)
);
create table if not exists atm
(
    id                     serial primary key,
    session_account_number int not null,
    bank_id                int references bank (id)
);

-- INSERT VALUE TO TABLES

-- Add bank procedure where atm info also inserted
create or replace procedure add_bank(
    name bank.name%type
) as
$$
declare
    added_bank_id                  bank.id%type;
    session_account_number_default atm.session_account_number%type := -1;
begin
    insert into bank(name) values (name) returning id into added_bank_id;
    insert into atm (session_account_number, bank_id) values (session_account_number_default, added_bank_id);
    commit;
end;
$$ language plpgsql;
call add_bank('Jusan bank');
insert into account(account_number, account_balance, bank_id)
values (1, 0, 1),
       (2, 0, 1),
       (3, 0, 1),
       (4, 0, 1),
       (5, 0, 1);

-- SELECT TABLES

-- select bank
select *
from bank;

-- select bank's accounts
select bank.*, account.*
from bank,
     account
where bank.id = account.bank_id;

-- select account
select *
from account;

-- select atm
select *
from atm;

-- select last account's account number
select account_number
from account
ORDER BY id DESC
LIMIT 1;

update atm set session_account_number = -1 where bank_id=3;