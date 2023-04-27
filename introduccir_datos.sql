/*ESTABLECIMIENTOS*/
INSERT INTO `hosteleria`.`establecimientos` (`id_establecimiento`, `nombre`, `direccion`, `cif`, `prefijo_telefono`, `telefono`) VALUES (null, 'Celme Galego', 'Valadares, Carr. do Portal, 284, 36315 Vigo, Pontevedra', 'B25727827', '34', '986461046');
/*SALAS*/
INSERT INTO `hosteleria`.`salas` (`id_sala`, `nombre`, `establecimiento`) VALUES ('1', 'Principal', '1'),('2', 'Comedor', '1');
