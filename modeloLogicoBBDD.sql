drop database if exists hosteleria;
Create database if not exists hosteleria;

drop table if exists establecimientos;
create table if not exists establecimientos (
	id_establecimiento integer unsigned auto_increment not null,
    nombre varchar(50) not null,
    direccion varchar(50) not null,
    cif char (9) not null
)engine innodb;

drop table if exists salas;
create table if not exists salas (
	id_sala integer unsigned auto_increment not null,
    nombre varchar(50) not null,
    establecimiento integer unsigned  not null,
	foreign key (local) references establecimiento(id_establecimiento)
				on update cascade
                on delete cascade,
    primary key (id_sala),
    index fk_establecimiento (establecimiento)
   
)engine innodb;

drop table if exists mesas;
create table if not exists mesas (
	id_mesa integer unsigned auto_increment not null,
    posicionX integer unsigned  not null,
    posicionY integer unsigned  not null,
    tamanoX integer unsigned  not null,
    tamanoY integer unsigned  not null,
	sala  integer unsigned  not null,
    foreign key (sala) references salas(id_sala)
				on update cascade
                on delete cascade,
	primary key (id_mesa),
    index sala(sala)
)engine innodb;

drop table if exists decoraciones;
create table if not exists decoraciones (
	id_decoraciones integer unsigned auto_increment not null,
    posicionX integer unsigned  not null,
    posicionY integer unsigned  not null,
    tamanoX integer unsigned  not null,
    tamanoY integer unsigned  not null,
	sala  integer unsigned  not null,
    foreign key (sala) references salas(id_sala)
				on update cascade
                on delete cascade,
	primary key (id_mesa),
    index sala(sala)
)engine innodb;


