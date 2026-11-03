import type { Row } from './api';
import type { Column } from './components/DataTable';
import type { FieldSpec, LinesSpec } from './components/Fields';
import { fmt, nowLocal, today } from './format';

export interface RowAction {
  label: string;
  when: (r: Row) => boolean;
  path: (r: Row) => string;
  fields: FieldSpec[];
}

export interface DocConfig {
  key: string;
  title: string;
  group: 'Pedidos' | 'Logística' | 'Rendiciones';
  endpoint: string;
  idField: string;
  columns: Column[];
  form: { fields: FieldSpec[]; lines?: LinesSpec; calc?: { endpoint: string; label: string; render: (r: Row) => string } };
  detail?: boolean;
  report?: string;
  anularWhen?: (r: Row) => boolean;
  actions?: RowAction[];
}

const est = (...e: string[]) => (r: Row) => e.includes(String(r.estado));
const fecha = (name: string, label = 'Fecha'): FieldSpec => ({ name, label, type: 'date', required: true, default: today });
const sel = (name: string, label: string, options: string, required = true): FieldSpec => ({ name, label, type: 'select', options, required });
const num = (name: string, label: string, min = 0): FieldSpec => ({ name, label, type: 'number', required: true, min });
const txt = (name: string, label: string, max: number, required = true): FieldSpec => ({ name, label, type: 'textarea', max, required });

export const DOCS: DocConfig[] = [
  // ------------------------------------------------------------------ Pedidos
  {
    key: 'pedidos', title: 'Registrar pedido', group: 'Pedidos', endpoint: '/api/pedidos', idField: 'id_rped',
    columns: [
      { key: 'id_rped', label: 'Nº', type: 'int' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'cliente', label: 'Cliente' }, { key: 'descrip', label: 'Descripción' },
      { key: 'estado', label: 'Estado', type: 'estado' }, { key: 'personal', label: 'Registrado por' },
    ],
    form: { fields: [fecha('fecha'), sel('idCliente', 'Cliente', 'cliente'), txt('descrip', 'Descripción', 300)] },
    detail: true, report: 'regped', anularWhen: est('PENDIENTE', 'EN PROCESO'),
  },
  {
    key: 'cotizaciones', title: 'Cotizar flete', group: 'Pedidos', endpoint: '/api/cotizaciones', idField: 'id_cot',
    columns: [
      { key: 'id_cot', label: 'Nº', type: 'int' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'cliente', label: 'Cliente' }, { key: 'pais_origen', label: 'Origen' }, { key: 'pais_destino', label: 'Destino' },
      { key: 'peso', label: 'Peso', type: 'num' }, { key: 'volumen', label: 'Volumen', type: 'num' },
      { key: 'costo', label: 'Costo (Gs.)', type: 'money' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        sel('idCliente', 'Cliente', 'cliente'), sel('idPaisO', 'País origen', 'pais'), sel('idPaisD', 'País destino', 'pais'),
        num('peso', 'Peso', 0.01), num('volumen', 'Volumen (m³)', 0.01),
        { name: 'mercaderias', label: 'Mercaderías', type: 'multiselect', options: 'mercaderia' },
      ],
      calc: {
        endpoint: '/api/cotizaciones/calcular', label: 'Calcular costo',
        render: (r) => `(${fmt(r.tarifa_origen, 'money')} + ${fmt(r.tarifa_destino, 'money')}) × volumen × peso = Gs. ${fmt(r.costo, 'money')}`,
      },
    },
    detail: true, report: 'coti', anularWhen: est('ACTIVO'),
  },
  {
    key: 'pedidos-flete', title: 'Pedido de flete', group: 'Pedidos', endpoint: '/api/pedidos-flete', idField: 'id_ped',
    columns: [
      { key: 'id_ped', label: 'Nº', type: 'int' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'id_rped', label: 'Pedido', type: 'int' }, { key: 'cliente', label: 'Cliente' },
      { key: 'despachante', label: 'Despachante' }, { key: 'tipo_carga', label: 'Tipo de carga' },
      { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        fecha('fecha'), sel('idRped', 'Pedido del cliente', 'pedidos_abiertos'), sel('idDesp', 'Despachante', 'despachante'),
        sel('idTic', 'Tipo de carga', 'tipo_carg'), txt('descrip', 'Descripción', 300),
      ],
      lines: { name: 'items', label: 'Mercaderías', min: 1, fields: [sel('idMerc', 'Mercadería', 'mercaderia'), num('cantidad', 'Cantidad', 0.01)] },
    },
    detail: true, report: 'ped_flete', anularWhen: est('PENDIENTE', 'EN PROCESO'),
  },
  {
    key: 'crt', title: 'Emitir CRT', group: 'Pedidos', endpoint: '/api/crt', idField: 'id_crt',
    columns: [
      { key: 'nro', label: 'Nro', type: 'int' }, { key: 'anho', label: 'Año', type: 'int' },
      { key: 'fecha_emi', label: 'Emisión', type: 'date' }, { key: 'exportador', label: 'Exportador' },
      { key: 'importador', label: 'Importador' }, { key: 'pais_origen', label: 'Origen' }, { key: 'pais_destino', label: 'Destino' },
      { key: 'valor_total', label: 'Valor', type: 'num' }, { key: 'moneda', label: 'Mon.' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        fecha('fechaEmi', 'Fecha emisión'), sel('idPed', 'Pedido de flete', 'pedidos_flete_abiertos', false),
        sel('idExpo', 'Exportador', 'cliente'), sel('idImpo', 'Importador', 'cliente'),
        sel('idPaisO', 'País origen', 'pais'), sel('idPaisD', 'País destino', 'pais'),
        sel('idAdu', 'Aduana', 'aduana'), sel('idMon', 'Moneda', 'moneda'),
      ],
      lines: {
        name: 'items', label: 'Mercaderías', min: 1,
        fields: [
          sel('idMerc', 'Mercadería', 'mercaderia'), num('cantidad', 'Cantidad', 0.01), num('peso', 'Peso (kg)', 0.01),
          num('volumen', 'Volumen (m³)', 0.01), num('valor', 'Valor', 0), sel('idMon', 'Moneda', 'moneda'),
        ],
      },
    },
    detail: true, report: 'emicrt', anularWhen: est('ACTIVO'),
  },
  {
    key: 'reclamos', title: 'Reclamos', group: 'Pedidos', endpoint: '/api/reclamos', idField: 'id_recla',
    columns: [
      { key: 'id_recla', label: 'Nº', type: 'int' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'id_rped', label: 'Pedido', type: 'int' }, { key: 'cliente', label: 'Cliente' },
      { key: 'descrip', label: 'Reclamo' }, { key: 'estado', label: 'Estado', type: 'estado' },
      { key: 'fecha_c', label: 'Cierre', type: 'date' }, { key: 'avisado', label: 'Avisado', type: 'bool' },
    ],
    form: {
      fields: [
        fecha('fecha'), sel('idRped', 'Pedido', 'pedidos_todos'), sel('idPed', 'Pedido de flete', 'pedidos_flete_todos', false),
        txt('descrip', 'Reclamo', 500),
      ],
    },
    detail: true, report: 'recla', anularWhen: est('PENDIENTE'),
    actions: [{
      label: 'Resolver', when: est('PENDIENTE'), path: (r) => `/api/reclamos/${r.id_recla}/resolver`,
      fields: [txt('solucion', 'Solución', 500), { name: 'avisado', label: 'Cliente avisado', type: 'checkbox', default: () => false }],
    }],
  },
  // ------------------------------------------------------------------ Logística
  {
    key: 'asignaciones', title: 'Asignar equipos', group: 'Logística', endpoint: '/api/asignaciones', idField: 'id_asig',
    columns: [
      { key: 'id_asig', label: 'Nº', type: 'int' }, { key: 'fecha_carg', label: 'Carga', type: 'date' },
      { key: 'id_ped', label: 'Ped. flete', type: 'int' }, { key: 'conductor', label: 'Conductor' },
      { key: 'tracto', label: 'Tracto' }, { key: 'semirremolque', label: 'Semirremolque' },
      { key: 'id_viaj', label: 'Viaje', type: 'int' }, { key: 'km_recorridos', label: 'Km recorridos', type: 'num' },
      { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        fecha('fechaCarg', 'Fecha de carga'), sel('idPed', 'Pedido de flete', 'pedidos_flete_abiertos'),
        sel('idCond', 'Conductor', 'personal'), sel('idTc', 'Tracto', 'tracto'), sel('idSemi', 'Semirremolque', 'semirremolque'),
        txt('descrip', 'Descripción', 300),
      ],
      lines: { name: 'accesorios', label: 'Accesorios', fields: [sel('idAcc', 'Accesorio', 'accesorios'), { name: 'obs', label: 'Observación', type: 'text', max: 150 }] },
    },
    detail: true, report: 'asignacion', anularWhen: est('PENDIENTE'),
  },
  {
    key: 'salidas', title: 'Registrar salida', group: 'Logística', endpoint: '/api/salidas', idField: 'id_salid',
    columns: [
      { key: 'id_salid', label: 'Nº', type: 'int' }, { key: 'fecha_sal', label: 'Fecha', type: 'date' },
      { key: 'id_asig', label: 'Asignación', type: 'int' }, { key: 'tracto', label: 'Tracto' }, { key: 'conductor', label: 'Conductor' },
      { key: 'kilom_sal', label: 'Km', type: 'num' }, { key: 'comb_sal', label: 'Combustible (l)', type: 'num' },
      { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: { fields: [fecha('fechaSal', 'Fecha de salida'), sel('idAsig', 'Asignación', 'asignaciones_sin_salida'), num('kilomSal', 'Kilometraje'), num('combSal', 'Combustible (l)')] },
    anularWhen: est('ACTIVO'),
  },
  {
    key: 'viajes', title: 'Registrar viaje / fleteros', group: 'Logística', endpoint: '/api/viajes', idField: 'id_viaj',
    columns: [
      { key: 'id_viaj', label: 'Nº', type: 'int' }, { key: 'fecha_sal', label: 'Salida', type: 'date' },
      { key: 'itinerario', label: 'Itinerario' }, { key: 'ruta', label: 'Ruta' }, { key: 'aduana', label: 'Aduana' },
      { key: 'asignaciones', label: 'Asignaciones', type: 'int' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        fecha('fechaSal', 'Fecha de salida'), sel('idIti', 'Itinerario', 'itinerario'), sel('idRuta', 'Ruta', 'ruta'),
        sel('idAdu', 'Aduana', 'aduana'),
        { name: 'ciudades', label: 'Ciudades del recorrido', type: 'multiselect', options: 'ciudad' },
        { name: 'asignaciones', label: 'Asignaciones de equipo', type: 'multiselect', options: 'asignaciones_sin_viaje' },
      ],
    },
    detail: true, anularWhen: est('PENDIENTE'),
    actions: [{
      label: 'Asignar fletero', when: est('PENDIENTE', 'EN PROCESO'), path: (r) => `/api/viajes/${r.id_viaj}/fleteros`,
      fields: [sel('idFlet', 'Fletero', 'fletero'), fecha('fecha')],
    }],
  },
  {
    key: 'llegadas', title: 'Registrar llegada', group: 'Logística', endpoint: '/api/llegadas', idField: 'id_lleg',
    columns: [
      { key: 'id_lleg', label: 'Nº', type: 'int' }, { key: 'fecha_lleg', label: 'Fecha', type: 'date' },
      { key: 'id_salid', label: 'Salida', type: 'int' }, { key: 'id_asig', label: 'Asignación', type: 'int' },
      { key: 'kilom_lleg', label: 'Km llegada', type: 'num' }, { key: 'km_recorridos', label: 'Km recorridos', type: 'num' },
      { key: 'comb_consumido', label: 'Consumo (l)', type: 'num' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: { fields: [fecha('fechaLleg', 'Fecha de llegada'), sel('idSalid', 'Salida', 'salidas_sin_llegada'), num('kilomLleg', 'Kilometraje'), num('combLleg', 'Combustible (l)')] },
    anularWhen: est('ACTIVO'),
  },
  {
    key: 'eventos', title: 'Eventos de carga', group: 'Logística', endpoint: '/api/eventos', idField: 'id_evento',
    columns: [
      { key: 'crt', label: 'CRT' }, { key: 'tipo', label: 'Evento' }, { key: 'fecha_hora', label: 'Fecha/hora', type: 'datetime' },
      { key: 'coment', label: 'Comentario' }, { key: 'personal', label: 'Registrado por' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        sel('idCrt', 'CRT', 'crt'), sel('idTip', 'Tipo de evento', 'tip_event'),
        { name: 'fechaHora', label: 'Fecha y hora', type: 'datetime', required: true, default: nowLocal },
        txt('coment', 'Comentario', 300, false),
      ],
    },
    anularWhen: est('ACTIVO'),
  },
  // ------------------------------------------------------------------ Rendiciones
  {
    key: 'cajas', title: 'Apertura y cierre de caja', group: 'Rendiciones', endpoint: '/api/cajas', idField: 'id_ap_cie',
    columns: [
      { key: 'id_ap_cie', label: 'Nº', type: 'int' }, { key: 'caja', label: 'Caja' }, { key: 'moneda', label: 'Mon.' },
      { key: 'personal', label: 'Responsable' }, { key: 'fecha_hora_ap', label: 'Apertura', type: 'datetime' },
      { key: 'monto_inic', label: 'Monto inicial', type: 'money' }, { key: 'viaticos', label: 'Viáticos', type: 'money' },
      { key: 'saldo_esperado', label: 'Saldo esperado', type: 'money' }, { key: 'fecha_hora_cierr', label: 'Cierre', type: 'datetime' },
      { key: 'monto_cierr', label: 'Monto cierre', type: 'money' }, { key: 'diferencia_cierre', label: 'Diferencia', type: 'money' },
      { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: { fields: [sel('idCaj', 'Caja', 'caja'), sel('idMon', 'Moneda', 'moneda'), num('montoInic', 'Monto inicial')] },
    actions: [{
      label: 'Cerrar caja', when: est('ABIERTA'), path: (r) => `/api/cajas/${r.id_ap_cie}/cerrar`,
      fields: [num('montoCierr', 'Monto contado al cierre')],
    }],
  },
  {
    key: 'viaticos', title: 'Asignar viáticos', group: 'Rendiciones', endpoint: '/api/viaticos', idField: 'id_viat',
    columns: [
      { key: 'id_viat', label: 'Nº', type: 'int' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'beneficiario', label: 'Beneficiario' }, { key: 'caja', label: 'Caja' }, { key: 'moneda', label: 'Mon.' },
      { key: 'monto', label: 'Monto', type: 'money' }, { key: 'comprobado', label: 'Comprobado', type: 'money' },
      { key: 'saldo', label: 'Saldo', type: 'money' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        fecha('fecha'), sel('idPer', 'Beneficiario', 'personal'), sel('idApCie', 'Caja abierta', 'cajas_abiertas'),
        sel('idViaj', 'Viaje', 'viajes_abiertos', false), num('monto', 'Monto', 0.01), txt('obs', 'Observación', 300, false),
      ],
    },
    detail: true, anularWhen: est('PENDIENTE'),
  },
  {
    key: 'comprobantes', title: 'Registrar comprobantes', group: 'Rendiciones', endpoint: '/api/comprobantes', idField: 'id_comp',
    columns: [
      { key: 'id_comp', label: 'Nº', type: 'int' }, { key: 'id_viat', label: 'Viático', type: 'int' },
      { key: 'beneficiario', label: 'Beneficiario' }, { key: 'fecha', label: 'Fecha', type: 'date' },
      { key: 'nro_comp', label: 'Comprobante' }, { key: 'descrip', label: 'Concepto' },
      { key: 'monto', label: 'Monto', type: 'money' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: {
      fields: [
        sel('idViat', 'Viático', 'viaticos_pendientes'), fecha('fecha'),
        { name: 'nroComp', label: 'Nro. comprobante', type: 'text', max: 30, required: true },
        { name: 'descrip', label: 'Concepto', type: 'text', max: 200, required: true }, num('monto', 'Monto', 0.01),
      ],
    },
    anularWhen: est('ACTIVO'),
  },
  {
    key: 'rendiciones', title: 'Emitir rendiciones', group: 'Rendiciones', endpoint: '/api/rendiciones', idField: 'id_rend',
    columns: [
      { key: 'id_rend', label: 'Nº', type: 'int' }, { key: 'id_viat', label: 'Viático', type: 'int' },
      { key: 'fecha', label: 'Fecha', type: 'date' }, { key: 'beneficiario', label: 'Beneficiario' }, { key: 'moneda', label: 'Mon.' },
      { key: 'total_asignado', label: 'Asignado', type: 'money' }, { key: 'total_rendido', label: 'Rendido', type: 'money' },
      { key: 'diferencia', label: 'Diferencia', type: 'money' }, { key: 'estado', label: 'Estado', type: 'estado' },
    ],
    form: { fields: [sel('idViat', 'Viático', 'viaticos_pendientes'), txt('obs', 'Observación', 300, false)] },
    report: 'rendicion', anularWhen: est('ACTIVO'),
  },
];
