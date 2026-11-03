
CREATE TABLE public.fletero (
                id_flet INTEGER NOT NULL,
                nombres VARCHAR NOT NULL,
                apellidos VARCHAR NOT NULL,
                camion VARCHAR NOT NULL,
                telef VARCHAR NOT NULL,
                direc INTEGER NOT NULL,
                nro_reg VARCHAR NOT NULL,
                CONSTRAINT fletero_pk PRIMARY KEY (id_flet)
);


CREATE TABLE public.rol (
                id_rol INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT rol_pk PRIMARY KEY (id_rol)
);


CREATE TABLE public.usuario (
                id_usu INTEGER NOT NULL,
                id_rol INTEGER NOT NULL,
                contrasena VARCHAR NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usu, id_rol)
);


CREATE TABLE public.caja (
                id_caj INTEGER NOT NULL,
                descrip INTEGER NOT NULL,
                CONSTRAINT caja_pk PRIMARY KEY (id_caj)
);


CREATE TABLE public.tipo_carg (
                id_tic INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT tipo_carg_pk PRIMARY KEY (id_tic)
);


CREATE TABLE public.itinerario (
                id_iti INTEGER NOT NULL,
                descrip INTEGER NOT NULL,
                CONSTRAINT itinerario_pk PRIMARY KEY (id_iti)
);


CREATE TABLE public.tip_event (
                id_tip INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT tip_event_pk PRIMARY KEY (id_tip)
);


CREATE TABLE public.moneda (
                id_mon INTEGER NOT NULL,
                abrev VARCHAR NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT moneda_pk PRIMARY KEY (id_mon)
);


CREATE TABLE public.rendiciones (
                id_rend INTEGER NOT NULL,
                total INTEGER NOT NULL,
                diferencia INTEGER NOT NULL,
                CONSTRAINT rendiciones_pk PRIMARY KEY (id_rend)
);


CREATE TABLE public.aduana (
                id_adu INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT aduana_pk PRIMARY KEY (id_adu)
);


CREATE TABLE public.estado (
                id_est INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT estado_pk PRIMARY KEY (id_est)
);


CREATE TABLE public.sucursal (
                id_suc INTEGER NOT NULL,
                telef VARCHAR NOT NULL,
                direc VARCHAR NOT NULL,
                correo VARCHAR NOT NULL,
                descrip VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT sucursal_pk PRIMARY KEY (id_suc)
);


CREATE TABLE public.pais (
                id_pais INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                precio INTEGER NOT NULL,
                CONSTRAINT pais_pk PRIMARY KEY (id_pais)
);


CREATE TABLE public.estad_prov (
                id_prov INTEGER NOT NULL,
                id_pais INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT estad_prov_pk PRIMARY KEY (id_prov, id_pais)
);


CREATE TABLE public.ciudad (
                id_ciud INTEGER NOT NULL,
                id_prov INTEGER NOT NULL,
                id_pais INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT ciudad_pk PRIMARY KEY (id_ciud, id_prov, id_pais)
);


CREATE TABLE public.ruta (
                id_ruta INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                extension VARCHAR NOT NULL,
                id_ciud INTEGER NOT NULL,
                id_prov INTEGER NOT NULL,
                id_pais INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT ruta_pk PRIMARY KEY (id_ruta)
);


CREATE TABLE public.peaje (
                id_peaj INTEGER NOT NULL,
                descrip BYTEA NOT NULL,
                id_ciud INTEGER NOT NULL,
                id_prov INTEGER NOT NULL,
                id_pais INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT peaje_pk PRIMARY KEY (id_peaj)
);


CREATE TABLE public.accesorios (
                id_acc INTEGER NOT NULL,
                descrip INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT accesorios_pk PRIMARY KEY (id_acc)
);


CREATE TABLE public.nacionalidad (
                id_nac INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT nacionalidad_pk PRIMARY KEY (id_nac)
);


CREATE TABLE public.marca_s (
                id_ms INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT marca_s_pk PRIMARY KEY (id_ms)
);


CREATE TABLE public.tipo_semi (
                id_tsem INTEGER NOT NULL,
                id_ms INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT tipo_semi_pk PRIMARY KEY (id_tsem, id_ms)
);


CREATE TABLE public.marca_t (
                id_mt INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT idmart_pk PRIMARY KEY (id_mt)
);


CREATE TABLE public.modelo_t (
                id_modt INTEGER NOT NULL,
                id_mt INTEGER NOT NULL,
                anho INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT modelo_t_pk PRIMARY KEY (id_modt, id_mt)
);


CREATE TABLE public.tracto (
                id_tc INTEGER NOT NULL,
                chasis_nr VARCHAR NOT NULL,
                chapa_nr VARCHAR NOT NULL,
                tara VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                id_modt INTEGER NOT NULL,
                id_mt INTEGER NOT NULL,
                CONSTRAINT id_tc PRIMARY KEY (id_tc)
);


CREATE TABLE public.semirremolque (
                id_semi INTEGER NOT NULL,
                chasis_nro VARCHAR NOT NULL,
                cap_carg INTEGER NOT NULL,
                largo INTEGER NOT NULL,
                ancho INTEGER NOT NULL,
                alto INTEGER NOT NULL,
                chapa_nro VARCHAR NOT NULL,
                id_tsem INTEGER NOT NULL,
                id_ms INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT idsemi_pk PRIMARY KEY (id_semi)
);


CREATE TABLE public.tipo_doc (
                id_tc INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT idtc_pk PRIMARY KEY (id_tc)
);


CREATE TABLE public.despachante (
                id_desp INTEGER NOT NULL,
                nombres VARCHAR NOT NULL,
                apellidos VARCHAR NOT NULL,
                telef VARCHAR NOT NULL,
                CONSTRAINT iddes_pk PRIMARY KEY (id_desp)
);


CREATE TABLE public.cargo (
                id_car INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT ide_car_pk PRIMARY KEY (id_car)
);


CREATE TABLE public.personal (
                id_per INTEGER NOT NULL,
                nombres VARCHAR NOT NULL,
                apellidos VARCHAR NOT NULL,
                fecha_nac DATE NOT NULL,
                direcc VARCHAR NOT NULL,
                telf VARCHAR NOT NULL,
                id_car INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                id_suc INTEGER NOT NULL,
                id_usu INTEGER NOT NULL,
                id_rol INTEGER NOT NULL,
                CONSTRAINT idper_pk PRIMARY KEY (id_per)
);


CREATE TABLE public.aper_cierr_caja (
                id_caj INTEGER NOT NULL,
                id_per INTEGER NOT NULL,
                id_ap_cie INTEGER NOT NULL,
                id_suc INTEGER NOT NULL,
                fecha_ap DATE NOT NULL,
                hora_ap TIME NOT NULL,
                monto_inic INTEGER NOT NULL,
                fecha_cierr DATE NOT NULL,
                hora_cierr TIME NOT NULL,
                monto_cierr INTEGER NOT NULL,
                CONSTRAINT aper_cierr_caja_pk PRIMARY KEY (id_caj, id_per, id_ap_cie, id_suc)
);


CREATE TABLE public.viatico (
                id_viat INTEGER NOT NULL,
                monto VARCHAR NOT NULL,
                id_per INTEGER NOT NULL,
                CONSTRAINT viatico_pk PRIMARY KEY (id_viat)
);


CREATE TABLE public.d_viat_mon (
                id_viat INTEGER NOT NULL,
                id_mon INTEGER NOT NULL,
                item INTEGER NOT NULL,
                CONSTRAINT d_viat_mon_pk PRIMARY KEY (id_viat, id_mon, item)
);


CREATE TABLE public.comprobante (
                id_comp INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                monto INTEGER NOT NULL,
                total INTEGER NOT NULL,
                id_viat INTEGER NOT NULL,
                CONSTRAINT comprobante_pk PRIMARY KEY (id_comp)
);


CREATE TABLE public.d_ren_comp (
                id_comp INTEGER NOT NULL,
                id_rend INTEGER NOT NULL,
                item INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT d_ren_comp_pk PRIMARY KEY (id_comp, id_rend, item)
);


CREATE TABLE public.r_viaje (
                id_viaj INTEGER NOT NULL,
                fecha_sal DATE NOT NULL,
                id_est INTEGER NOT NULL,
                id_per INTEGER NOT NULL,
                id_iti INTEGER NOT NULL,
                id_adu INTEGER NOT NULL,
                id_ruta INTEGER NOT NULL,
                CONSTRAINT r_viaje_pk PRIMARY KEY (id_viaj)
);


CREATE TABLE public.asig_flet (
                id_aflet INTEGER NOT NULL,
                id_flet INTEGER NOT NULL,
                id_viaj INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                fecha DATE NOT NULL,
                CONSTRAINT asig_flet_pk PRIMARY KEY (id_aflet)
);


CREATE TABLE public.d_viaj_ciud (
                id_ciud INTEGER NOT NULL,
                id_prov INTEGER NOT NULL,
                id_pais INTEGER NOT NULL,
                id_viaj INTEGER NOT NULL,
                item INTEGER NOT NULL,
                id_viat INTEGER NOT NULL,
                CONSTRAINT d_viaj_ciud_pk PRIMARY KEY (id_ciud, id_prov, id_pais, id_viaj, item)
);


CREATE TABLE public.tipo_merc (
                id_tm INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT id_tmer_pk PRIMARY KEY (id_tm)
);


CREATE TABLE public.mercaderia (
                id_merc INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                id_tm INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT id_merc_pk PRIMARY KEY (id_merc)
);


CREATE TABLE public.tipo_cli (
                id_cli INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT id_tcli_pk PRIMARY KEY (id_cli)
);


CREATE TABLE public.cliente (
                id_docc INTEGER NOT NULL,
                nombres VARCHAR NOT NULL,
                apellidos VARCHAR NOT NULL,
                fecha_nac DATE NOT NULL,
                direcc VARCHAR NOT NULL,
                telf VARCHAR NOT NULL,
                id_cli INTEGER NOT NULL,
                id_nac INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT iddocc_pk PRIMARY KEY (id_docc)
);


CREATE TABLE public.emi_crt (
                id_crt INTEGER NOT NULL,
                anho INTEGER NOT NULL,
                fecha_emi DATE NOT NULL,
                id_mon INTEGER NOT NULL,
                id_per INTEGER NOT NULL,
                id_expo INTEGER NOT NULL,
                id_impo INTEGER NOT NULL,
                id_adu INTEGER NOT NULL,
                id_pais_o INTEGER NOT NULL,
                id_pais_d INTEGER NOT NULL,
                CONSTRAINT emi_crt_pk PRIMARY KEY (id_crt, anho)
);


CREATE TABLE public.d_m_emi (
                id_crt INTEGER NOT NULL,
                anho INTEGER NOT NULL,
                id_merc INTEGER NOT NULL,
                item INTEGER NOT NULL,
                valor INTEGER NOT NULL,
                id_mon INTEGER NOT NULL,
                canti INTEGER NOT NULL,
                vol INTEGER NOT NULL,
                peso INTEGER NOT NULL,
                CONSTRAINT d_m_emi_pk PRIMARY KEY (id_crt, anho, id_merc, item)
);


CREATE TABLE public.eventos (
                id_crt INTEGER NOT NULL,
                anho INTEGER NOT NULL,
                export VARCHAR NOT NULL,
                import VARCHAR NOT NULL,
                coment VARCHAR NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT eventos_pk PRIMARY KEY (id_crt, anho)
);


CREATE TABLE public.d_event (
                id_tip INTEGER NOT NULL,
                id_crt INTEGER NOT NULL,
                anho INTEGER NOT NULL,
                item INTEGER NOT NULL,
                CONSTRAINT d_event_pk PRIMARY KEY (id_tip, id_crt, anho)
);


CREATE TABLE public.coti_flete (
                id_cot INTEGER NOT NULL,
                id_docc INTEGER NOT NULL,
                peso INTEGER NOT NULL,
                vol_me VARCHAR NOT NULL,
                costo_f VARCHAR NOT NULL,
                id_pais_o INTEGER NOT NULL,
                id_pais_d INTEGER NOT NULL,
                CONSTRAINT coti_flete_pk PRIMARY KEY (id_cot)
);


CREATE TABLE public.d_co_mer (
                id_cot INTEGER NOT NULL,
                id_merc INTEGER NOT NULL,
                item INTEGER NOT NULL,
                CONSTRAINT d_co_mer_pk PRIMARY KEY (id_cot, id_merc, item)
);


CREATE TABLE public.r_pedido (
                id_rped INTEGER NOT NULL,
                fecha DATE NOT NULL,
                descrip VARCHAR NOT NULL,
                id_docc INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                id_per INTEGER NOT NULL,
                CONSTRAINT id_rped_pk PRIMARY KEY (id_rped)
);


CREATE TABLE public.ped_flete (
                id_ped INTEGER NOT NULL,
                fecha DATE NOT NULL,
                descrip VARCHAR NOT NULL,
                id_desp INTEGER NOT NULL,
                id_rped INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                id_tic INTEGER NOT NULL,
                CONSTRAINT idped_pk PRIMARY KEY (id_ped)
);


CREATE TABLE public.asignacion (
                id_asig INTEGER NOT NULL,
                fecha_carg DATE NOT NULL,
                descrip VARCHAR NOT NULL,
                id_ped INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                id_cond INTEGER NOT NULL,
                id_semi INTEGER NOT NULL,
                iid_per INTEGER NOT NULL,
                id_tc INTEGER NOT NULL,
                CONSTRAINT idasig_pk PRIMARY KEY (id_asig)
);


CREATE TABLE public.r_salida (
                id_salid INTEGER NOT NULL,
                fecha_sal DATE NOT NULL,
                kilom_sal VARCHAR NOT NULL,
                comb_sal VARCHAR NOT NULL,
                id_asig INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT r_salida_pk PRIMARY KEY (id_salid)
);


CREATE TABLE public.r_llegada (
                id_lleg INTEGER NOT NULL,
                fecha_lleg DATE NOT NULL,
                kilom_lleg VARCHAR NOT NULL,
                comb_lleg VARCHAR NOT NULL,
                id_salid INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                CONSTRAINT r_llegada_pk PRIMARY KEY (id_lleg)
);


CREATE TABLE public.d_acc (
                id_asig INTEGER NOT NULL,
                id_acc INTEGER NOT NULL,
                item INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT d_acc_pk PRIMARY KEY (id_asig, id_acc, item)
);


CREATE TABLE public.d_asi_via (
                id_asig INTEGER NOT NULL,
                id_viaj INTEGER NOT NULL,
                item INTEGER NOT NULL,
                CONSTRAINT d_asi_via_pk PRIMARY KEY (id_asig, id_viaj, item)
);


CREATE TABLE public.d_merc (
                id_merc INTEGER NOT NULL,
                id_ped INTEGER NOT NULL,
                item INTEGER NOT NULL,
                cantidad VARCHAR NOT NULL,
                CONSTRAINT dm_pk PRIMARY KEY (id_merc, id_ped, item)
);


CREATE TABLE public.reclamos (
                id_recla INTEGER NOT NULL,
                fecha DATE NOT NULL,
                descrip VARCHAR NOT NULL,
                id_rped INTEGER NOT NULL,
                id_ped INTEGER NOT NULL,
                id_docc INTEGER NOT NULL,
                id_est INTEGER NOT NULL,
                fecha_c DATE NOT NULL,
                Solu VARCHAR NOT NULL,
                avisa VARCHAR NOT NULL,
                CONSTRAINT id_re_pk PRIMARY KEY (id_recla)
);


CREATE TABLE public.d_cli_tdoc (
                id_tc INTEGER NOT NULL,
                id_docc INTEGER NOT NULL,
                item INTEGER NOT NULL,
                descrip VARCHAR NOT NULL,
                CONSTRAINT dclitdoc_pk PRIMARY KEY (id_tc, id_docc, item)
);


ALTER TABLE public.asig_flet ADD CONSTRAINT fletero_asig_flet_fk
FOREIGN KEY (id_flet)
REFERENCES public.fletero (id_flet)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.usuario ADD CONSTRAINT rol_usuario_fk
FOREIGN KEY (id_rol)
REFERENCES public.rol (id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.personal ADD CONSTRAINT usuario_personal_fk
FOREIGN KEY (id_usu, id_rol)
REFERENCES public.usuario (id_usu, id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.aper_cierr_caja ADD CONSTRAINT caja_aper_cierr_caja_fk
FOREIGN KEY (id_caj)
REFERENCES public.caja (id_caj)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ped_flete ADD CONSTRAINT tipo_carg_ped_flete_fk
FOREIGN KEY (id_tic)
REFERENCES public.tipo_carg (id_tic)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_viaje ADD CONSTRAINT itinerario_r_viaje_fk
FOREIGN KEY (id_iti)
REFERENCES public.itinerario (id_iti)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_event ADD CONSTRAINT tip_event_d_event_fk
FOREIGN KEY (id_tip)
REFERENCES public.tip_event (id_tip)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT moneda_emi_crt_fk
FOREIGN KEY (id_mon)
REFERENCES public.moneda (id_mon)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_viat_mon ADD CONSTRAINT moneda_d_viat_mon_fk
FOREIGN KEY (id_mon)
REFERENCES public.moneda (id_mon)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_m_emi ADD CONSTRAINT moneda_d_m_emi_fk
FOREIGN KEY (id_mon)
REFERENCES public.moneda (id_mon)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_ren_comp ADD CONSTRAINT rendiciones_d_ren_comp_fk
FOREIGN KEY (id_rend)
REFERENCES public.rendiciones (id_rend)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_viaje ADD CONSTRAINT aduana_r_viaje_fk
FOREIGN KEY (id_adu)
REFERENCES public.aduana (id_adu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT aduana_emi_crt_fk
FOREIGN KEY (id_adu)
REFERENCES public.aduana (id_adu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ped_flete ADD CONSTRAINT estado_ped_flete_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_pedido ADD CONSTRAINT estado_r_pedido_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT estado_asignacion_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_viaje ADD CONSTRAINT estado_r_viaje_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente ADD CONSTRAINT estado_cliente_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.nacionalidad ADD CONSTRAINT estado_nacionalidad_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_salida ADD CONSTRAINT estado_r_salida_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.mercaderia ADD CONSTRAINT estado_mercaderia_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reclamos ADD CONSTRAINT estado_reclamos_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cargo ADD CONSTRAINT estado_cargo_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.personal ADD CONSTRAINT estado_personal_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.semirremolque ADD CONSTRAINT estado_semirremolque_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.accesorios ADD CONSTRAINT estado_accesorios_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_llegada ADD CONSTRAINT estado_r_llegada_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.peaje ADD CONSTRAINT estado_peaje_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ruta ADD CONSTRAINT estado_ruta_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ciudad ADD CONSTRAINT estado_ciudad_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pais ADD CONSTRAINT estado_id_pais_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.tracto ADD CONSTRAINT estado_tracto_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.sucursal ADD CONSTRAINT estado_sucursal_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eventos ADD CONSTRAINT estado_eventos_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asig_flet ADD CONSTRAINT estado_asig_flet_fk
FOREIGN KEY (id_est)
REFERENCES public.estado (id_est)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.personal ADD CONSTRAINT sucursal_personal_fk
FOREIGN KEY (id_suc)
REFERENCES public.sucursal (id_suc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.aper_cierr_caja ADD CONSTRAINT sucursal_aper_cierr_caja_fk
FOREIGN KEY (id_suc)
REFERENCES public.sucursal (id_suc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.estad_prov ADD CONSTRAINT id_pais_estad_prov_fk
FOREIGN KEY (id_pais)
REFERENCES public.pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.coti_flete ADD CONSTRAINT pais_coti_flete_fk
FOREIGN KEY (id_pais_o)
REFERENCES public.pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.coti_flete ADD CONSTRAINT pais_coti_flete_fk2
FOREIGN KEY (id_pais_d)
REFERENCES public.pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT pais_emi_crt_fk
FOREIGN KEY (id_pais_o)
REFERENCES public.pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT pais_emi_crt_fk2
FOREIGN KEY (id_pais_d)
REFERENCES public.pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ciudad ADD CONSTRAINT estad_prov_ciudad_fk
FOREIGN KEY (id_prov, id_pais)
REFERENCES public.estad_prov (id_prov, id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ruta ADD CONSTRAINT ciudad_ruta_fk
FOREIGN KEY (id_ciud, id_prov, id_pais)
REFERENCES public.ciudad (id_ciud, id_prov, id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.peaje ADD CONSTRAINT ciudad_peaje_fk
FOREIGN KEY (id_ciud, id_prov, id_pais)
REFERENCES public.ciudad (id_ciud, id_prov, id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_viaj_ciud ADD CONSTRAINT ciudad_d_viaj_ciud_fk
FOREIGN KEY (id_ciud, id_prov, id_pais)
REFERENCES public.ciudad (id_ciud, id_prov, id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_viaje ADD CONSTRAINT ruta_r_viaje_fk
FOREIGN KEY (id_ruta)
REFERENCES public.ruta (id_ruta)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_acc ADD CONSTRAINT accesorios_d_acc_fk
FOREIGN KEY (id_acc)
REFERENCES public.accesorios (id_acc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente ADD CONSTRAINT nacionalidad_cliente_fk
FOREIGN KEY (id_nac)
REFERENCES public.nacionalidad (id_nac)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.tipo_semi ADD CONSTRAINT marca_s_tipo_semi_fk
FOREIGN KEY (id_ms)
REFERENCES public.marca_s (id_ms)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.semirremolque ADD CONSTRAINT tipo_semi_semirremolque_fk
FOREIGN KEY (id_tsem, id_ms)
REFERENCES public.tipo_semi (id_tsem, id_ms)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.modelo_t ADD CONSTRAINT marca_t_modelo_t_fk
FOREIGN KEY (id_mt)
REFERENCES public.marca_t (id_mt)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.tracto ADD CONSTRAINT modelo_t_tracto_fk
FOREIGN KEY (id_modt, id_mt)
REFERENCES public.modelo_t (id_modt, id_mt)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT tracto_asignacion_fk
FOREIGN KEY (id_tc)
REFERENCES public.tracto (id_tc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT semirremolque_asignacion_fk
FOREIGN KEY (id_semi)
REFERENCES public.semirremolque (id_semi)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_cli_tdoc ADD CONSTRAINT tipo_doc_d_cli_tdoc_fk
FOREIGN KEY (id_tc)
REFERENCES public.tipo_doc (id_tc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ped_flete ADD CONSTRAINT despachante_ped_flete_fk
FOREIGN KEY (id_desp)
REFERENCES public.despachante (id_desp)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.personal ADD CONSTRAINT cargo_personal_fk
FOREIGN KEY (id_car)
REFERENCES public.cargo (id_car)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT personal_asignacion_fk
FOREIGN KEY (id_cond)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_viaje ADD CONSTRAINT personal_r_viaje_fk
FOREIGN KEY (id_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.viatico ADD CONSTRAINT personal_viatico_fk
FOREIGN KEY (id_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT personal_emi_crt_fk
FOREIGN KEY (id_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.aper_cierr_caja ADD CONSTRAINT personal_aper_cierr_caja_fk
FOREIGN KEY (id_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT personal_asignacion_fk2
FOREIGN KEY (iid_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_pedido ADD CONSTRAINT personal_r_pedido_fk
FOREIGN KEY (id_per)
REFERENCES public.personal (id_per)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_viaj_ciud ADD CONSTRAINT viatico_d_viaj_ciud_fk
FOREIGN KEY (id_viat)
REFERENCES public.viatico (id_viat)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.comprobante ADD CONSTRAINT viatico_comprobante_fk
FOREIGN KEY (id_viat)
REFERENCES public.viatico (id_viat)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_viat_mon ADD CONSTRAINT viatico_d_viat_mon_fk
FOREIGN KEY (id_viat)
REFERENCES public.viatico (id_viat)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_ren_comp ADD CONSTRAINT comprobante_d_ren_comp_fk
FOREIGN KEY (id_comp)
REFERENCES public.comprobante (id_comp)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_asi_via ADD CONSTRAINT r_viaje_d_asi_via_fk
FOREIGN KEY (id_viaj)
REFERENCES public.r_viaje (id_viaj)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_viaj_ciud ADD CONSTRAINT r_viaje_d_viaj_ciud_fk
FOREIGN KEY (id_viaj)
REFERENCES public.r_viaje (id_viaj)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asig_flet ADD CONSTRAINT r_viaje_asig_flet_fk
FOREIGN KEY (id_viaj)
REFERENCES public.r_viaje (id_viaj)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.mercaderia ADD CONSTRAINT tipo_merc_mercaderia_fk
FOREIGN KEY (id_tm)
REFERENCES public.tipo_merc (id_tm)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_merc ADD CONSTRAINT mercaderia_d_merc_fk
FOREIGN KEY (id_merc)
REFERENCES public.mercaderia (id_merc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_co_mer ADD CONSTRAINT mercaderia_d_co_mer_fk
FOREIGN KEY (id_merc)
REFERENCES public.mercaderia (id_merc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_m_emi ADD CONSTRAINT mercaderia_d_m_emi_fk
FOREIGN KEY (id_merc)
REFERENCES public.mercaderia (id_merc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente ADD CONSTRAINT tipo_cli_cliente_fk
FOREIGN KEY (id_cli)
REFERENCES public.tipo_cli (id_cli)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_cli_tdoc ADD CONSTRAINT cliente_d_cli_tdoc_fk
FOREIGN KEY (id_docc)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reclamos ADD CONSTRAINT cliente_reclamos_fk
FOREIGN KEY (id_docc)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_pedido ADD CONSTRAINT cliente_r_pedido_fk
FOREIGN KEY (id_docc)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.coti_flete ADD CONSTRAINT cliente_coti_flete_fk
FOREIGN KEY (id_docc)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT cliente_emi_crt_fk2
FOREIGN KEY (id_expo)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emi_crt ADD CONSTRAINT cliente_emi_crt_fk
FOREIGN KEY (id_impo)
REFERENCES public.cliente (id_docc)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eventos ADD CONSTRAINT emi_crt_eventos_fk
FOREIGN KEY (id_crt, anho)
REFERENCES public.emi_crt (id_crt, anho)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_m_emi ADD CONSTRAINT emi_crt_d_m_emi_fk
FOREIGN KEY (id_crt, anho)
REFERENCES public.emi_crt (id_crt, anho)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_event ADD CONSTRAINT eventos_d_event_fk
FOREIGN KEY (id_crt, anho)
REFERENCES public.eventos (id_crt, anho)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_co_mer ADD CONSTRAINT coti_flete_d_co_mer_fk
FOREIGN KEY (id_cot)
REFERENCES public.coti_flete (id_cot)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reclamos ADD CONSTRAINT r_pedido_reclamos_fk
FOREIGN KEY (id_rped)
REFERENCES public.r_pedido (id_rped)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ped_flete ADD CONSTRAINT r_pedido_ped_flete_fk
FOREIGN KEY (id_rped)
REFERENCES public.r_pedido (id_rped)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_merc ADD CONSTRAINT ped_flete_d_merc_fk
FOREIGN KEY (id_ped)
REFERENCES public.ped_flete (id_ped)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reclamos ADD CONSTRAINT ped_flete_reclamos_fk
FOREIGN KEY (id_ped)
REFERENCES public.ped_flete (id_ped)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asignacion ADD CONSTRAINT ped_flete_asignacion_fk
FOREIGN KEY (id_ped)
REFERENCES public.ped_flete (id_ped)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_asi_via ADD CONSTRAINT asignacion_d_asi_via_fk
FOREIGN KEY (id_asig)
REFERENCES public.asignacion (id_asig)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.d_acc ADD CONSTRAINT asignacion_d_acc_fk
FOREIGN KEY (id_asig)
REFERENCES public.asignacion (id_asig)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_salida ADD CONSTRAINT asignacion_r_salida_fk
FOREIGN KEY (id_asig)
REFERENCES public.asignacion (id_asig)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.r_llegada ADD CONSTRAINT r_salida_r_llegada_fk
FOREIGN KEY (id_salid)
REFERENCES public.r_salida (id_salid)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
