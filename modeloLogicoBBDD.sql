drop database if exists hosteleria;
Create database if not exists hosteleria;
use hosteleria;

drop table if exists establecimientos;
create table if not exists establecimientos (
	id_establecimiento integer unsigned auto_increment not null,
    nombre varchar(50) not null,
    direccion varchar(150) not null,
    cif char (9) not null,
    prefijo_telefono integer unsigned not null,
    telefono int unsigned not null,
    
    primary key (id_establecimiento)
)engine innodb;

drop table if exists salas;
create table if not exists salas (
	id_sala integer unsigned auto_increment not null,
    nombre varchar(50) not null,
    establecimiento integer unsigned  not null,
    
	foreign key (establecimiento) references establecimientos(id_establecimiento)
				on update cascade
                on delete cascade,
                
    primary key (id_sala),
    index fk_establecimiento (establecimiento)
   
)engine innodb;


drop table if exists imagenes;
create table if not exists imagenes (
	id_imagen integer unsigned auto_increment not null,
	url varchar(150)  not null,
   
   primary key (id_imagen)
   
)engine innodb;


drop table if exists mesas;
create table if not exists mesas (
	id_mesa integer unsigned auto_increment not null,
    posicionX integer unsigned  not null,
    posicionY integer unsigned  not null,
    tamanoX integer unsigned  not null,
    tamanoY integer unsigned  not null,
	imagen int unsigned null,
    sala  integer unsigned  not null,
    
    foreign key (sala) references salas(id_sala)
				on update cascade
                on delete cascade,
                
	foreign key (imagen) references imagenes(id_imagen)
				on update cascade
                on delete cascade,             
                
	primary key (id_mesa),
    index fk_sala(sala),
    index fk_imagen(imagen)
    
)engine innodb;

drop table if exists camareros;
create table if not exists camareros(
	id_camarero integer unsigned auto_increment not null,
    empleado integer unsigned not null,
    alias varchar(20) null, /*Si el nombre se repite tenemos alias para diferenciar a los camareros*/
	primary key (id_camarero)
)engine innodb;

drop table if exists empleados;
create table if not exists empleados(
	id_empleado integer  unsigned auto_increment not null,
    nombre varchar(20) not null,
    apellidos varchar(50) not null,
    dni char(9) not null,
    cargo enum('administrador','camarero','cocina','jefe_camarero','jefe_cocina') null,
	primary key(id_empleado)
)engine innodb;

drop table if exists empledos_telefonos;
create table if not exists empleados_telefonos(
	empleado integer unsigned not null,
    prefijo_telefono integer unsigned not null,
    telefono int unsigned not null,
    
    foreign key fk_empleado (empleado) references empleados(id_empleado)
							on update cascade
							on delete cascade,
                            
	primary key (empleado,prefijo_telefono,telefono),
	index (empleado) 
)engine innodb;

drop table if exists mesas;
create table if not exists mesas(
	id_mesa integer unsigned auto_increment not null,
    posicionX integer unsigned  not null,
    posicionY integer unsigned  not null,
    tamanoX integer unsigned  not null,
    tamanoY integer unsigned  not null,
	imagen int unsigned null,
    sala  integer unsigned  not null,
    
    foreign key (sala) references salas(id_sala)
				on update cascade
                on delete cascade,
      foreign key (imagen) references imagenes(id_imagen)
				on update cascade
                on delete set null,           
                
                
	primary key (id_mesa),
    index fk_sala(sala),
    index fk_imagen(imagen)
    
)engine innodb;

drop table if exists cuentas;
create table if not exists cuentas(
	id_cuenta integer unsigned auto_increment not null,
	fecha_hora datetime not null default current_timestamp,
	camarero integer unsigned  null,
	mesa integer unsigned  not null,

	foreign key (mesa) references mesas(id_mesa)
					on update cascade
					on delete cascade,
					
	foreign key (camarero) references camareros(id_camarero)
					on update cascade
					on delete set null,
					
	primary key (id_cuenta),
	index fk_mesa(mesa),
    index fk_camarero(camarero)

)engine innodb;

drop table if exists productos;
create table if not exists productos(
	id_producto integer unsigned auto_increment not null,
    nombre varchar(30) not null,
    precio double not null,
    disponible boolean not null,
    imagen int unsigned null,

	foreign key (imagen) references imagenes(id_imagen)
				on update cascade
                on delete set null,           

	primary key (id_producto),
    index fk_imagen(imagen)

)engine innodb;

drop table if exists grupos_productos;
create table if not exists productos(
	
	id_grupo integer unsigned auto_increment not null,
    nombre varchar(30) not null,
    producto int unsigned null,
    imagen int unsigned null,

	foreign key (imagen) references imagenes(id_imagen)
				on update cascade
                on delete set null,           

	primary key (id_grupo,producto),
    index fk_imagen(imagen)

)engine innodb;


drop table if exists pedidos;
create table if not exists pedidos(
	
	id_pedido integer unsigned auto_increment not null,
	producto integer unsigned  not null,
	cuenta integer unsigned not null,

	foreign key (producto) references productos (id_producto)
							on update cascade
                            on delete restrict,
                            
	foreign key (cuenta) references cuentas (id_cuenta)
							on update cascade
                            on delete restrict,
                            
	primary key(id_pedido),
    index (producto),
    index (cuenta)
                            
)engine innodb;

