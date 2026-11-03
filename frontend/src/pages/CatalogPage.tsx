import { useCallback, useEffect, useMemo, useState } from 'react';
import { useParams } from 'react-router';
import { api, type Row } from '../api';
import { useAuth } from '../auth';
import { DataTable, type Column } from '../components/DataTable';
import type { FieldSpec, Values } from '../components/Fields';
import { NewButton, PageHead } from '../components/PageHead';
import { useToast } from '../components/Toast';
import { FormModal } from './DocPage';

interface Col {
  name: string;
  label: string;
  type: 'TEXT' | 'EMAIL' | 'INT' | 'DECIMAL' | 'DATE' | 'SELECT';
  required: boolean;
  max: number;
  ref: string | null;
  min: number | null;
  inList: boolean;
}

interface CatalogDef {
  name: string;
  title: string;
  group: string;
  pk: string;
  cols: Col[];
  hasEstado: boolean;
  readOnly: boolean;
}

let defsCache: Promise<CatalogDef[]> | null = null;

function toField(c: Col): FieldSpec {
  const base = { name: c.name, label: c.label, required: c.required, max: c.max || undefined, min: c.min ?? undefined };
  switch (c.type) {
    case 'SELECT':
      return { ...base, type: 'select', options: c.ref! };
    case 'INT':
      return { ...base, type: 'int' };
    case 'DECIMAL':
      return { ...base, type: 'number' };
    case 'DATE':
      return { ...base, type: 'date' };
    case 'EMAIL':
      return { ...base, type: 'email' };
    default:
      return { ...base, type: 'text' };
  }
}

export function CatalogPage() {
  const { name } = useParams();
  const { canWrite } = useAuth();
  const toast = useToast();
  const [def, setDef] = useState<CatalogDef | null>(null);
  const [rows, setRows] = useState<Row[]>([]);
  const [loading, setLoading] = useState(true);
  const [anulados, setAnulados] = useState(false);
  const [editing, setEditing] = useState<Row | 'new' | null>(null);

  useEffect(() => {
    defsCache ??= api.get<CatalogDef[]>('/api/catalogos');
    defsCache.then((all) => setDef(all.find((d) => d.name === name) ?? null)).catch(toast.error);
    setEditing(null);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [name]);

  const load = useCallback(() => {
    if (!name) return;
    setLoading(true);
    api.get(`/api/catalogos/${name}?anulados=${anulados}`).then(setRows).catch(toast.error).finally(() => setLoading(false));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [name, anulados]);
  useEffect(load, [load]);

  const columns = useMemo<Column[]>(() => {
    if (!def) return [];
    const cols: Column[] = [{ key: def.pk, label: 'Código', type: 'int' }];
    for (const c of def.cols.filter((x) => x.inList)) {
      cols.push({
        key: c.type === 'SELECT' ? `${c.name}_label` : c.name,
        label: c.label,
        type: c.type === 'DECIMAL' ? 'num' : c.type === 'DATE' ? 'date' : c.type === 'INT' ? 'int' : 'text',
      });
    }
    if (def.hasEstado) cols.push({ key: 'estado', label: 'Estado', type: 'estado' });
    return cols;
  }, [def]);

  if (!def) return <p className="muted">Cargando…</p>;
  const writable = canWrite && !def.readOnly;
  const fields = def.cols.map(toField);

  async function remove(r: Row) {
    const verb = def!.hasEstado ? 'anular' : 'eliminar';
    if (!confirm(`¿Desea ${verb} el registro ${r[def!.pk]}?`)) return;
    try {
      await api.del(`/api/catalogos/${def!.name}/${r[def!.pk]}`);
      toast.ok('Registro actualizado');
      load();
    } catch (e) {
      toast.error(e);
    }
  }

  async function activar(r: Row) {
    try {
      await api.post(`/api/catalogos/${def!.name}/${r[def!.pk]}/activar`);
      toast.ok('Registro activado');
      load();
    } catch (e) {
      toast.error(e);
    }
  }

  const initial: Values | undefined =
    editing && editing !== 'new' ? Object.fromEntries(def.cols.map((c) => [c.name, editing[c.name] ?? null])) : undefined;

  return (
    <section>
      <PageHead icon="layers" crumb={`Mantenimiento · ${def.group}`} title={def.title}>
        {writable && <NewButton onClick={() => setEditing('new')} />}
      </PageHead>
      <DataTable
        columns={columns}
        rows={rows}
        loading={loading}
        toolbar={
          def.hasEstado && (
            <label className="check">
              <input type="checkbox" checked={anulados} onChange={(e) => setAnulados(e.target.checked)} /> Mostrar anulados
            </label>
          )
        }
        actions={
          writable
            ? (r) => (
                <div className="row-actions">
                  <button className="btn btn-sm" onClick={() => setEditing(r)}>
                    Editar
                  </button>
                  {r.estado === 'ANULADO' ? (
                    <button className="btn btn-sm" onClick={() => activar(r)}>
                      Activar
                    </button>
                  ) : (
                    <button className="btn btn-sm btn-danger" onClick={() => remove(r)}>
                      {def.hasEstado ? 'Anular' : 'Eliminar'}
                    </button>
                  )}
                </div>
              )
            : undefined
        }
      />
      {editing && (
        <FormModal
          key={editing === 'new' ? 'new' : String(editing[def.pk])}
          title={editing === 'new' ? `${def.title} - nuevo` : `${def.title} - editar ${editing[def.pk]}`}
          fields={fields}
          initial={initial}
          submit={(v) =>
            editing === 'new' ? api.post(`/api/catalogos/${def.name}`, v) : api.put(`/api/catalogos/${def.name}/${editing[def.pk]}`, v)
          }
          onClose={() => setEditing(null)}
          onDone={() => {
            setEditing(null);
            toast.ok('Registro guardado');
            load();
          }}
        />
      )}
    </section>
  );
}
