import { useEffect, useState } from 'react';
import { api } from '../api';
import { PageHead } from '../components/PageHead';
import { useToast } from '../components/Toast';
import { today } from '../format';

interface ReportDef {
  name: string;
  title: string;
  tipo: 'DOCUMENTO' | 'RANGO';
  group: string;
}

function firstOfMonth() {
  return today().slice(0, 8) + '01';
}

export function Informes() {
  const toast = useToast();
  const [defs, setDefs] = useState<ReportDef[]>([]);
  const [ids, setIds] = useState<Record<string, string>>({});
  const [desde, setDesde] = useState(firstOfMonth());
  const [hasta, setHasta] = useState(today());
  useEffect(() => {
    api.get<ReportDef[]>('/api/reportes').then(setDefs).catch(toast.error);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const abrir = (d: ReportDef) => {
    const q = d.tipo === 'RANGO' ? `desde=${desde}&hasta=${hasta}` : `id=${encodeURIComponent(ids[d.name] ?? '')}`;
    api.openPdf(`/api/reportes/${d.name}?${q}`).catch(toast.error);
  };

  return (
    <section>
      <PageHead icon="printer" crumb="Reportes PDF" title="Informes" />
      <div className="card pad">
        <h2>Listados por período</h2>
        <div className="inline-form">
          <label>
            Desde <input type="date" value={desde} onChange={(e) => setDesde(e.target.value)} />
          </label>
          <label>
            Hasta <input type="date" value={hasta} onChange={(e) => setHasta(e.target.value)} />
          </label>
        </div>
        <div className="report-list">
          {defs.filter((d) => d.tipo === 'RANGO').map((d) => (
            <button key={d.name} className="btn" onClick={() => abrir(d)}>
              {d.title}
            </button>
          ))}
        </div>
      </div>
      <div className="card pad">
        <h2>Documentos</h2>
        <p className="muted">También pueden imprimirse desde cada pantalla con el botón "Imprimir".</p>
        <div className="report-grid">
          {defs.filter((d) => d.tipo === 'DOCUMENTO').map((d) => (
            <div key={d.name} className="report-item">
              <span>{d.title}</span>
              <input
                type="number"
                min={1}
                placeholder="Nº"
                value={ids[d.name] ?? ''}
                onChange={(e) => setIds({ ...ids, [d.name]: e.target.value })}
                aria-label={`Número de ${d.title}`}
              />
              <button className="btn btn-sm" disabled={!ids[d.name]} onClick={() => abrir(d)}>
                PDF
              </button>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
