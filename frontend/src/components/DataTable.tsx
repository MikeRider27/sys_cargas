import { useMemo, useState, type ReactNode } from 'react';
import type { Row } from '../api';
import { estadoClass, fmt, type ColType } from '../format';

export interface Column {
  key: string;
  label: string;
  type?: ColType;
}

interface Props {
  columns: Column[];
  rows: Row[];
  loading?: boolean;
  actions?: (row: Row) => ReactNode;
  toolbar?: ReactNode;
  empty?: string;
}

const PAGE = 25;

export function DataTable({ columns, rows, loading, actions, toolbar, empty }: Props) {
  const [q, setQ] = useState('');
  const [page, setPage] = useState(0);
  const filtered = useMemo(() => {
    const term = q.trim().toLowerCase();
    if (!term) return rows;
    return rows.filter((r) => columns.some((c) => fmt(r[c.key], c.type).toLowerCase().includes(term)));
  }, [rows, columns, q]);
  const pages = Math.max(1, Math.ceil(filtered.length / PAGE));
  const current = Math.min(page, pages - 1);
  const visible = filtered.slice(current * PAGE, current * PAGE + PAGE);

  return (
    <div className="card">
      <div className="table-toolbar">
        <input
          className="search"
          type="search"
          placeholder="Buscar…"
          value={q}
          onChange={(e) => {
            setQ(e.target.value);
            setPage(0);
          }}
          aria-label="Buscar en la tabla"
        />
        <div className="toolbar-right">{toolbar}</div>
      </div>
      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              {columns.map((c) => (
                <th key={c.key} className={isNum(c.type) ? 'num' : undefined}>
                  {c.label}
                </th>
              ))}
              {actions && <th className="actions-col">Acciones</th>}
            </tr>
          </thead>
          <tbody>
            {loading && (
              <tr>
                <td colSpan={columns.length + 1} className="muted center">
                  Cargando…
                </td>
              </tr>
            )}
            {!loading && visible.length === 0 && (
              <tr>
                <td colSpan={columns.length + 1} className="muted center">
                  {empty ?? 'Sin registros'}
                </td>
              </tr>
            )}
            {!loading &&
              visible.map((r, i) => (
                <tr key={i}>
                  {columns.map((c) => (
                    <td key={c.key} className={isNum(c.type) ? 'num' : undefined}>
                      {c.type === 'estado' ? <span className={estadoClass(r[c.key])}>{fmt(r[c.key])}</span> : fmt(r[c.key], c.type)}
                    </td>
                  ))}
                  {actions && <td className="actions-col">{actions(r)}</td>}
                </tr>
              ))}
          </tbody>
        </table>
      </div>
      {pages > 1 && (
        <div className="pager">
          <button className="btn btn-sm" disabled={current === 0} onClick={() => setPage(current - 1)}>
            ‹ Anterior
          </button>
          <span className="muted">
            Página {current + 1} de {pages} · {filtered.length} registros
          </span>
          <button className="btn btn-sm" disabled={current >= pages - 1} onClick={() => setPage(current + 1)}>
            Siguiente ›
          </button>
        </div>
      )}
    </div>
  );
}

function isNum(t?: ColType) {
  return t === 'money' || t === 'num' || t === 'int';
}
