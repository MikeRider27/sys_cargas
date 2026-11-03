import type { Row } from '../api';
import { estadoClass, fmt, humanize, type ColType } from '../format';

function guessType(key: string, v: unknown): ColType {
  if (typeof v === 'boolean') return 'bool';
  if (typeof v === 'string' && /^\d{4}-\d{2}-\d{2}T/.test(v)) return 'datetime';
  if (typeof v === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(v)) return 'date';
  if (typeof v === 'number' && /costo|monto|valor|total|tarifa|saldo|diferencia/.test(key)) return 'money';
  if (typeof v === 'number' && (!Number.isInteger(v) || /kilom|km_|comb|peso|volumen|cantidad|cap_carg|tara/.test(key))) return 'num';
  return 'text';
}

/** Vista genérica de un documento: campos de cabecera y tablas para los detalles. */
export function DetailView({ data }: { data: Row }) {
  const scalars = Object.entries(data).filter(([k, v]) => !Array.isArray(v) && !(k.startsWith('id_') && k !== Object.keys(data)[0]));
  const arrays = Object.entries(data).filter(([, v]) => Array.isArray(v)) as [string, Row[]][];
  return (
    <div>
      <dl className="kv">
        {scalars.map(([k, v]) => (
          <div key={k}>
            <dt>{humanize(k)}</dt>
            <dd>{k === 'estado' ? <span className={estadoClass(v)}>{fmt(v)}</span> : fmt(v, guessType(k, v)) || '—'}</dd>
          </div>
        ))}
      </dl>
      {arrays.map(([k, rows]) => {
        const cols = rows.length ? Object.keys(rows[0]).filter((c) => !c.startsWith('id_') || c === 'id_asig' || c === 'id_aflet' || c === 'id_ped' || c === 'id_comp') : [];
        return (
          <section key={k} className="detail-section">
            <h3>{humanize(k)}</h3>
            {rows.length === 0 ? (
              <p className="muted">Sin registros</p>
            ) : (
              <div className="table-wrap">
                <table>
                  <thead>
                    <tr>
                      {cols.map((c) => (
                        <th key={c}>{humanize(c)}</th>
                      ))}
                    </tr>
                  </thead>
                  <tbody>
                    {rows.map((r, i) => (
                      <tr key={i}>
                        {cols.map((c) => (
                          <td key={c}>{fmt(r[c], guessType(c, r[c]))}</td>
                        ))}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </section>
        );
      })}
    </div>
  );
}
