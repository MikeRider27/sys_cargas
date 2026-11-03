import { useEffect, useState } from 'react';
import { Link } from 'react-router';
import { api, type Row } from '../api';
import { useAuth } from '../auth';
import { Icon, type IconName } from '../components/Icon';
import { estadoClass, fmt } from '../format';

/** Etapas del circuito operativo, en el orden en que avanza una carga. */
const FLUJO: { key: string; label: string; hint: string; icon: IconName; to: string }[] = [
  { key: 'pedidos_pendientes', label: 'Pedidos pendientes', hint: 'Sin pedido de flete', icon: 'clipboard', to: '/doc/pedidos' },
  { key: 'fletes_abiertos', label: 'Fletes abiertos', hint: 'Por asignar o en curso', icon: 'package', to: '/doc/pedidos-flete' },
  { key: 'asignaciones_sin_salida', label: 'Por despachar', hint: 'Equipo asignado sin salida', icon: 'truck', to: '/doc/salidas' },
  { key: 'unidades_en_ruta', label: 'En ruta', hint: 'Esperando llegada', icon: 'route', to: '/doc/llegadas' },
  { key: 'viaticos_sin_rendir', label: 'Por rendir', hint: 'Viáticos pendientes', icon: 'receipt', to: '/doc/rendiciones' },
];

const KPIS: { key: string; label: string; icon: IconName; to: string; tone: string }[] = [
  { key: 'pedidos_en_proceso', label: 'Pedidos en proceso', icon: 'activity', to: '/doc/pedidos', tone: 'blue' },
  { key: 'crt_anho', label: 'CRT emitidos este año', icon: 'file', to: '/doc/crt', tone: 'navy' },
  { key: 'reclamos_pendientes', label: 'Reclamos pendientes', icon: 'alert', to: '/doc/reclamos', tone: 'red' },
  { key: 'cajas_abiertas', label: 'Cajas abiertas', icon: 'wallet', to: '/doc/cajas', tone: 'green' },
];

const ACCIONES: { label: string; icon: IconName; to: string }[] = [
  { label: 'Nuevo pedido', icon: 'clipboard', to: '/doc/pedidos' },
  { label: 'Cotizar flete', icon: 'calculator', to: '/doc/cotizaciones' },
  { label: 'Asignar equipo', icon: 'truck', to: '/doc/asignaciones' },
  { label: 'Registrar llegada', icon: 'flag', to: '/doc/llegadas' },
];

function saludo() {
  const h = new Date().getHours();
  return h < 12 ? 'Buenos días' : h < 19 ? 'Buenas tardes' : 'Buenas noches';
}

export function Dashboard() {
  const { user } = useAuth();
  const [data, setData] = useState<Row | null>(null);
  const [enRuta, setEnRuta] = useState<Row[] | null>(null);
  const [pedidos, setPedidos] = useState<Row[] | null>(null);

  useEffect(() => {
    api.get<Row>('/api/dashboard').then(setData).catch(() => setData({}));
    api.get('/api/asignaciones').then((r) => setEnRuta(r.filter((a) => a.estado === 'EN PROCESO').slice(0, 6))).catch(() => setEnRuta([]));
    api.get('/api/pedidos').then((r) => setPedidos(r.slice(0, 6))).catch(() => setPedidos([]));
  }, []);

  const n = (k: string) => (data ? Number(data[k] ?? 0) : null);

  return (
    <section className="dashboard">
      <div className="hero">
        <div className="hero-text">
          <p className="hero-kicker">Centro de operaciones</p>
          <h1>
            {saludo()}, {user?.nombre?.split(' ')[0]?.toLowerCase().replace(/^./, (c) => c.toUpperCase())}
          </h1>
          <p className="hero-sub">Resumen del estado de cargas, flota y rendiciones.</p>
        </div>
        <div className="hero-actions">
          {ACCIONES.map((a) => (
            <Link key={a.label} to={a.to} className="hero-action">
              <Icon name={a.icon} size={20} />
              <span>{a.label}</span>
            </Link>
          ))}
        </div>
        <div className="hero-road" aria-hidden="true" />
      </div>

      <h2 className="section-title">Flujo operativo</h2>
      <ol className="flow">
        {FLUJO.map((s, i) => (
          <li key={s.key}>
            <Link to={s.to} className={`flow-step ${n(s.key) ? 'has-items' : ''}`}>
              <span className="flow-icon">
                <Icon name={s.icon} size={22} />
              </span>
              <span className="flow-value">{n(s.key) ?? '…'}</span>
              <span className="flow-label">{s.label}</span>
              <span className="flow-hint">{s.hint}</span>
            </Link>
            {i < FLUJO.length - 1 && (
              <span className="flow-arrow" aria-hidden="true">
                <Icon name="chevron" size={20} />
              </span>
            )}
          </li>
        ))}
      </ol>

      <div className="kpis">
        {KPIS.map((k) => (
          <Link key={k.key} to={k.to} className={`kpi kpi-${k.tone}`}>
            <span className="kpi-icon">
              <Icon name={k.icon} size={20} />
            </span>
            <span>
              <span className="kpi-value">{n(k.key) ?? '…'}</span>
              <span className="kpi-label">{k.label}</span>
            </span>
          </Link>
        ))}
      </div>

      <div className="panels">
        <div className="card panel">
          <div className="panel-head">
            <h2>
              <Icon name="route" /> Unidades en ruta
            </h2>
            <Link to="/doc/llegadas" className="panel-link">
              Registrar llegada <Icon name="arrowRight" size={14} />
            </Link>
          </div>
          {enRuta === null ? (
            <p className="muted pad-x">Cargando…</p>
          ) : enRuta.length === 0 ? (
            <div className="empty">
              <Icon name="truck" size={30} />
              <span>No hay unidades en ruta en este momento</span>
            </div>
          ) : (
            <ul className="unit-list">
              {enRuta.map((a) => (
                <li key={String(a.id_asig)}>
                  <span className="plate">{String(a.tracto)}</span>
                  <span className="unit-main">
                    <strong>{String(a.conductor)}</strong>
                    <span className="muted">
                      Asignación Nº {String(a.id_asig)} · salió {fmt(a.fecha_sal, 'date')} · km {fmt(a.kilom_sal, 'num')}
                    </span>
                  </span>
                  <span className="plate plate-semi">{String(a.semirremolque)}</span>
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="card panel">
          <div className="panel-head">
            <h2>
              <Icon name="clipboard" /> Últimos pedidos
            </h2>
            <Link to="/doc/pedidos" className="panel-link">
              Ver todos <Icon name="arrowRight" size={14} />
            </Link>
          </div>
          {pedidos === null ? (
            <p className="muted pad-x">Cargando…</p>
          ) : pedidos.length === 0 ? (
            <div className="empty">
              <Icon name="clipboard" size={30} />
              <span>Todavía no hay pedidos registrados</span>
            </div>
          ) : (
            <ul className="order-list">
              {pedidos.map((p) => (
                <li key={String(p.id_rped)}>
                  <span className="order-n">Nº {String(p.id_rped)}</span>
                  <span className="unit-main">
                    <strong>{String(p.cliente)}</strong>
                    <span className="muted">
                      {fmt(p.fecha, 'date')} · {String(p.descrip)}
                    </span>
                  </span>
                  <span className={estadoClass(p.estado)}>{String(p.estado)}</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </section>
  );
}
