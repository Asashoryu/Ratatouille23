
    create table conto (
       id integer not null,
        is_chiuso bit,
        time integer,
        total float(23),
        numero_tavolo integer,
        primary key (id)
    ) engine=InnoDB;

    create table ingrediente (
       nome varchar(255) not null,
        description varchar(255),
        misura varchar(255),
        costo float(23),
        quantita float(23),
        tolleranza float(23),
        primary key (nome)
    ) engine=InnoDB;

    create table make_dish (
       corrispondenza integer not null,
        quantity float(23),
        nome_piatto varchar(255),
        nome_ingrediente varchar(255),
        primary key (corrispondenza)
    ) engine=InnoDB;

    create table ordered_dish (
       corrispondenza integer not null,
        quantita integer,
        conto_id integer,
        nome_piatto varchar(255),
        primary key (corrispondenza)
    ) engine=InnoDB;

    create table piatto (
       nome varchar(255) not null,
        allergie varchar(255),
        categoria varchar(255),
        description varchar(255),
        prezzo float(23),
        primary key (nome)
    ) engine=InnoDB;

    create table tavolo (
       id integer not null,
        taken bit,
        primary key (id)
    ) engine=InnoDB;

    create table user (
       username varchar(255) not null,
        cognome varchar(255),
        is_reimpostata bit,
        nome varchar(255),
        password varchar(255),
        role varchar(255),
        primary key (username)
    ) engine=InnoDB;

    alter table conto 
       add constraint FK8mfuca1l29uunnivfga7tj4k1 
       foreign key (numero_tavolo) 
       references tavolo (id) 
       on delete cascade;

    alter table make_dish 
       add constraint FKm1h08gomw566cxeim674i6ny8 
       foreign key (nome_piatto) 
       references piatto (nome) 
       on delete cascade;

    alter table make_dish 
       add constraint FKe0mul44hbq3fua48i9gle4tuf 
       foreign key (nome_ingrediente) 
       references ingrediente (nome) 
       on delete cascade;

    alter table ordered_dish 
       add constraint FK3l6d64a4yffycxwabh68n30ig 
       foreign key (conto_id) 
       references conto (id) 
       on delete cascade;

    alter table ordered_dish 
       add constraint FKbh94hbtbdijpbqs81gjiy07w4 
       foreign key (nome_piatto) 
       references piatto (nome) 
       on delete cascade;
