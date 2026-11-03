CREATE OR REPLACE FUNCTION abm_car(
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO cargo VALUES (cod,upper(des),1);
 /*mod*/	WHEN 2 THEN UPDATE cargo SET descrip=upper(des) WHERE id_car=cod; 
 /*baja*/	WHEN 3 THEN update cargo set id_est=0 WHERE id_car=cod;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

  CREATE OR REPLACE FUNCTION abm_cond(--select * from conductor
    ban integer,
    cod integer,
    nom character varying,
    ape character varying,
    fcha date,
    nreg integer,
    dir character varying,
    tel character varying,
    nac integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO conductor VALUES (cod,upper(nom),upper(ape),fcha,nreg,upper(dir),tel,nac,1);
 /*mod*/	WHEN 2 THEN UPDATE conductor SET nombres=upper(nom),apellidos=upper(ape),fecha_nac=fcha,direcc=upper(dir),telef=tel,id_nac=nac WHERE id_cond=cod; 
 /*baja*/	WHEN 3 THEN --update conductor set id_est=0 WHERE id_cond=cod;
				delete from conductor where id_cond=cod;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION abm_personal(--select * from personal
    ban integer,
    cod integer,
    nom character varying,
    ape character varying,
    fcha date,
    usr character varying,
    pass character varying,
    dir character varying,
    tel character varying,
    car integer,
    suc integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO personal VALUES (cod,upper(nom),upper(ape),fcha,upper(usr),pass,upper(dir),tel,car,1,suc);
 /*mod*/	WHEN 2 THEN UPDATE personal SET nombres=upper(nom),apellidos=upper(ape),fecha_nac=fcha,usuario=upper(usr),contrasena=pass,direcc=upper(dir),telf=tel,id_car=car,id_suc=suc WHERE id_per=cod; 
 /*baja*/	WHEN 3 THEN update personal set id_est=0 WHERE id_per=cod;
				--delete from conductor where id_cond=cod;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

  CREATE OR REPLACE FUNCTION abm_suc(--select * from sucursal
    ban integer,
    cod integer,
    tel character varying,
    dir character varying,
    mail character varying,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO sucursal VALUES (cod,tel,upper(dir),mail,upper(des),1);
 /*mod*/	WHEN 2 THEN UPDATE sucursal SET telef=tel,direc=upper(dir),correo=mail,descrip=upper(des) WHERE id_suc=cod; 
 /*baja*/	WHEN 3 THEN update sucursal set id_est=0 WHERE id_suc=cod;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION abm_cli(--select * from cliente
    ban integer,
    cod integer,
    nom character varying,
    ape character varying,
    f_nac date,
    direc character varying,
    telef character varying,
    idcli integer,
    idnac integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO cliente VALUES (cod,upper(nom),upper(ape),f_nac,upper(direc),telef,idcli,idnac,1);--select * from cliente
 /*mod*/	WHEN 2 THEN UPDATE cliente SET nombres=upper(nom),apellidos=upper(ape),fecha_nac=f_nac,direcc=upper(direc),telf=telef,id_cli=idcli,id_nac=idnac WHERE id_docc=cod; 
 /*baja*/	WHEN 3 THEN UPDATE cliente SET id_est=0 WHERE id_docc=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_desp(--select * from despachante
    ban integer,
    cod integer,
    nom character varying,
    ape character varying,
    tel character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO despachante VALUES (cod,upper(nom),upper(ape),tel);--select * from despachante
 /*mod*/	WHEN 2 THEN UPDATE despachante SET nombres=upper(nom),apellidos=upper(ape),telef=tel WHERE id_desp=cod; 
 /*baja*/	WHEN 3 THEN DELETE FROM despachante WHERE id_desp=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_dmerc(--select * from d_merc
    ban integer,
    merc integer,
    cod integer,
    item integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO d_merc VALUES (merc,cod,item,upper(des));
 /*mod*/	/*WHEN 2 THEN UPDATE d_merc SET id_desp=desp,id_est=est WHERE id_ped=cod; */
 /*baja*/	/*WHEN 3 THEN UPDATE ped_flete SET id_est=0 WHERE id_ped=cod; */
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_est(
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO estado VALUES (cod,upper(des));--select * from estado
 /*mod*/	WHEN 2 THEN UPDATE estado SET descrip=upper(des) WHERE id_est=cod; 
 /*baja*/	WHEN 3 THEN DELETE FROM estado WHERE id_est=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;
    
CREATE OR REPLACE FUNCTION abm_merc(
    ban integer,
    cod integer,
    des character varying,
    idtm integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO mercaderia VALUES (cod,upper(des),idtm,1);--select * from mercaderia
 /*mod*/	WHEN 2 THEN UPDATE mercaderia SET descrip=upper(des),id_tm=idtm WHERE id_merc=cod; 
 /*baja*/	WHEN 3 THEN UPDATE mercaderia set id_est=0 WHERE id_merc=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_nac(
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO nacionalidad VALUES (cod,upper(des),1);--select * from nacionalidad
 /*mod*/	WHEN 2 THEN UPDATE nacionalidad SET descrip=upper(des) WHERE id_nac=cod; 
 /*baja*/	WHEN 3 THEN update nacionalidad set id_est=0 WHERE id_nac=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_ped(--select * from ped_flete--EN SERIO
    ban integer,
    cod integer,
    fecha date,
    des character varying,
    desp integer,
    rped integer,
    tic integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO ped_flete VALUES (cod,fecha,upper(des),desp,rped,1,tic);
 /*mod*/	--WHEN 2 THEN UPDATE ped_flete SET id_desp=desp,id_est=est WHERE id_ped=cod; 
 /*baja*/	WHEN 3 THEN UPDATE ped_flete SET id_est=0 WHERE id_ped=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_rped(--select * from r_pedido--
    ban integer,
    cod integer,
    dte date,
    iddcc integer,
    des character varying,
    idest integer,
    id_per integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO r_pedido VALUES (cod,dte,upper(des),iddcc,1,id_per);--select * from r_pedido
 /*mod*/	--WHEN 2 THEN UPDATE nacionalidad SET descrip=des WHERE id_nac=cod; 
 /*baja*/	WHEN 3 THEN update r_pedido set id_est=0 WHERE id_rped=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;
  --SELECT abm_rped ('1','1',Sat Apr 16 00:00:00 PYT 2016,1,'ewewewewew',1,777);

CREATE OR REPLACE FUNCTION abm_tcli(--select * from tipo_cli
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO tipo_cli VALUES (cod,upper(des));
 /*mod*/	WHEN 2 THEN UPDATE tipo_cli SET descrip=upper(des) WHERE id_cli=cod; 
 /*baja*/	WHEN 3 THEN DELETE FROM tipo_cli WHERE id_cli=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_tdoc(
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO tipo_doc VALUES (cod,upper(des));--select * from tipo_doc
 /*mod*/	WHEN 2 THEN UPDATE tipo_doc SET descrip=upper(des) WHERE id_tc=cod; 
 /*baja*/	WHEN 3 THEN DELETE FROM tipo_doc WHERE id_tc=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;
  
CREATE OR REPLACE FUNCTION abm_tmerc(
    ban integer,
    cod integer,
    des character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO tipo_merc VALUES (cod,upper(des));--select * from tipo_merc
 /*mod*/	WHEN 2 THEN UPDATE tipo_merc SET descrip=upper(des) WHERE id_tm=cod; 
 /*baja*/	WHEN 3 THEN DELETE FROM tipo_merc WHERE id_tm=cod;
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION abm_coti(--select * from coti_flete--EN SERIO--select abm_coti('1','1',1,'21''34','7497000','2','3')
    ban integer,
    cod integer,
    cli integer,
    peso integer,
    vol character varying,
    costo character varying,
    pais_o integer,
    pais_d integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO coti_flete VALUES (cod,cli,peso,vol,costo,pais_o,pais_d);
 /*mod*/	--WHEN 2 THEN UPDATE ped_flete SET id_desp=desp,id_est=est WHERE id_ped=cod; 
 /*baja*/	WHEN 3 THEN UPDATE coti_flete SET id_est=0 WHERE id_cot=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

  CREATE OR REPLACE FUNCTION abm_crt(--select * from emi_crt--EN SERIO--select d.item,d.valor,m.descrip,d.canti,d.vol,d.peso from d_m_emi d, moneda m where m.id_mon=d.id_mon
    ban integer, 
    cod integer,
    anho_ integer,
    fecha date,
    per integer,
    cli_exp integer,
    cli_imp integer,
    adu integer,
    pais_o integer,
    pais_d integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO emi_crt VALUES (cod,anho_,fecha,1,per,cli_exp,cli_imp,adu,pais_o,pais_d);
 /*mod*/	--WHEN 2 THEN UPDATE ped_flete SET id_desp=desp,id_est=est WHERE id_ped=cod; 
 /*baja*/	WHEN 3 THEN UPDATE emi_crt SET id_est=0 WHERE id_crt=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION abm_dcrt(--select * from d_m_emi--EN SERIO--
    ban integer, 
    cod integer,
    anho_ integer,mer integer,
    item integer,
    valor_ integer,
    mon integer,
    cant integer,
    vol integer,
    peso integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO d_m_emi VALUES (cod,anho_,mer,item,valor_,mon,cant,vol,peso);
 /*mod*/	--WHEN 2 THEN UPDATE ped_flete SET id_desp=desp,id_est=est WHERE id_ped=cod; 
 /*baja*/	--WHEN 3 THEN UPDATE emi_crt SET id_est=0 WHERE id_crt=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;
  --select * from reclamos
  CREATE OR REPLACE FUNCTION abm_recla(--select * from reclamos--EN SERIO--
    ban integer, 
    cod integer,
    fecha_ date,
    des character varying,
    rped integer,
    ped integer,
    cli integer,
    fecha_c date,
    soluc character varying,
    avisado character varying)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO reclamos VALUES (cod,fecha_,des,rped,ped,cli,1,fecha_c,soluc,avisado);
 /*mod*/	WHEN 2 THEN UPDATE reclamos SET fecha_c=current_date,solu=soluc,avisa=avisado,id_est=0 WHERE id_recla=cod; 
 /*baja*/	WHEN 3 THEN UPDATE reclamos SET id_est=0 WHERE id_recla=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;
  --select * from reclamos
  --select * from estado
  CREATE OR REPLACE FUNCTION abm_asig(--select * from personal--EN SERIO--
    ban integer, 
    cod integer,
    fecha_ date,
    des character varying,
    ped integer,
    est integer,
    cond integer,
    semi integer,
    per integer,
    trac integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO asignacion VALUES (cod,fecha_,des,ped,est,cond,semi,per,trac);
 /*mod*/	--WHEN 2 THEN UPDATE asignacion SET fecha_c=current_date,solu=soluc,avisa=avisado,id_est=0 WHERE id_recla=cod; 
 /*baja*/	WHEN 3 THEN UPDATE asignacion SET id_est=0 WHERE id_asig=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION abm_sali(--select * from r_salida--EN SERIO--
    ban integer, 
    cod integer,
    fecha_ date,
    kil_sal character varying,
    comb_sal character varying,
    asig integer,
    est integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO r_salida VALUES (cod,fecha_,kil_sal,comb_sal,asig,est);
 /*mod*/	--WHEN 2 THEN UPDATE asignacion SET fecha_c=current_date,solu=soluc,avisa=avisado,id_est=0 WHERE id_recla=cod; 
 /*baja*/	WHEN 3 THEN UPDATE r_salida SET id_est=0 WHERE id_asig=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;

      CREATE OR REPLACE FUNCTION abm_llega(--select * from r_llegada--EN SERIO--
    ban integer, 
    cod integer,
    fecha_ date,
    kil_sal character varying,
    comb_sal character varying,
    salid integer,
    est integer)
  RETURNS void AS
$BODY$
begin
CASE ban
 /*alta*/       WHEN 1 THEN INSERT INTO r_llegada VALUES (cod,fecha_,kil_sal,comb_sal,salid,est);
 /*mod*/	--WHEN 2 THEN UPDATE asignacion SET fecha_c=current_date,solu=soluc,avisa=avisado,id_est=0 WHERE id_recla=cod; 
 /*baja*/	WHEN 3 THEN UPDATE r_llegada SET id_est=0 WHERE id_asig=cod; 
end case;
     end;
    $BODY$
  LANGUAGE plpgsql;