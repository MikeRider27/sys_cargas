import { useCallback, useEffect, useState, type FormEvent } from 'react';
import { useParams } from 'react-router';
import { api, type Row } from '../api';
import { useAuth } from '../auth';
import { DataTable } from '../components/DataTable';
import { DetailView } from '../components/DetailView';
import { Field, LinesEditor, defaults, type FieldSpec, type Values } from '../components/Fields';
import { DOC_ICONS } from '../components/Icon';
import { Modal } from '../components/Modal';
import { NewButton, PageHead } from '../components/PageHead';
import { useToast } from '../components/Toast';
import { DOCS, type DocConfig, type RowAction } from '../docs';

export function DocPage() {
  const { key } = useParams();
  const cfg = DOCS.find((d) => d.key === key);
  if (!cfg) return <p>Pantalla no encontrada</p>;
  return <DocScreen key={cfg.key} cfg={cfg} />;
}

function DocScreen({ cfg }: { cfg: DocConfig }) {
  const { canWrite } = useAuth();
  const toast = useToast();
  const [rows, setRows] = useState<Row[]>([]);
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);
  const [detail, setDetail] = useState<Row | null>(null);
  const [action, setAction] = useState<{ action: RowAction; row: Row } | null>(null);

  const load = useCallback(() => {
    setLoading(true);
    api.get(cfg.endpoint).then(setRows).catch(toast.error).finally(() => setLoading(false));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [cfg.endpoint]);
  useEffect(load, [load]);

  const id = (r: Row) => r[cfg.idField] as number;

  async function anular(r: Row) {
    if (!confirm(`¿Anular el registro Nº ${id(r)}? Esta acción no se puede deshacer.`)) return;
    try {
      await api.del(`${cfg.endpoint}/${id(r)}`);
      toast.ok('Registro anulado');
      load();
    } catch (e) {
      toast.error(e);
    }
  }

  return (
    <section>
      <PageHead icon={DOC_ICONS[cfg.key]} crumb={`Módulo ${cfg.group}`} title={cfg.title}>
        {canWrite && <NewButton onClick={() => setCreating(true)} />}
      </PageHead>
      <DataTable
        columns={cfg.columns}
        rows={rows}
        loading={loading}
        actions={(r) => (
          <div className="row-actions">
            {cfg.detail && (
              <button className="btn btn-sm" onClick={() => api.get<Row>(`${cfg.endpoint}/${id(r)}`).then(setDetail).catch(toast.error)}>
                Ver
              </button>
            )}
            {cfg.report && r.estado !== 'ANULADO' && (
              <button className="btn btn-sm" onClick={() => api.openPdf(`/api/reportes/${cfg.report}?id=${id(r)}`).catch(toast.error)}>
                Imprimir
              </button>
            )}
            {canWrite &&
              cfg.actions?.filter((a) => a.when(r)).map((a) => (
                <button key={a.label} className="btn btn-sm" onClick={() => setAction({ action: a, row: r })}>
                  {a.label}
                </button>
              ))}
            {canWrite && cfg.anularWhen?.(r) && (
              <button className="btn btn-sm btn-danger" onClick={() => anular(r)}>
                Anular
              </button>
            )}
          </div>
        )}
      />
      {creating && (
        <FormModal
          title={`${cfg.title} - nuevo`}
          fields={cfg.form.fields}
          lines={cfg.form.lines}
          calc={cfg.form.calc}
          submit={(v) => api.post(cfg.endpoint, v)}
          onClose={() => setCreating(false)}
          onDone={(saved) => {
            setCreating(false);
            toast.ok('Registro guardado');
            load();
            if (cfg.report && saved && saved[cfg.idField] != null && confirm('¿Desea imprimir el documento?')) {
              api.openPdf(`/api/reportes/${cfg.report}?id=${saved[cfg.idField]}`).catch(toast.error);
            }
          }}
        />
      )}
      {action && (
        <FormModal
          title={`${action.action.label} - Nº ${id(action.row)}`}
          fields={action.action.fields}
          submit={(v) => api.post(action.action.path(action.row), v)}
          onClose={() => setAction(null)}
          onDone={() => {
            setAction(null);
            toast.ok('Operación realizada');
            load();
          }}
        />
      )}
      {detail && (
        <Modal title={`${cfg.title} - Nº ${detail[cfg.idField]}`} onClose={() => setDetail(null)} wide>
          <DetailView data={detail} />
        </Modal>
      )}
    </section>
  );
}

interface FormProps {
  title: string;
  fields: FieldSpec[];
  lines?: DocConfig['form']['lines'];
  calc?: DocConfig['form']['calc'];
  initial?: Values;
  submit: (v: Values) => Promise<Row>;
  onClose: () => void;
  onDone: (saved: Row) => void;
}

export function FormModal({ title, fields, lines, calc, initial, submit, onClose, onDone }: FormProps) {
  const toast = useToast();
  const [values, setValues] = useState<Values>(() => ({
    ...defaults(fields),
    ...(lines ? { [lines.name]: lines.min ? [defaults(lines.fields)] : [] } : {}),
    ...initial,
  }));
  const [saving, setSaving] = useState(false);
  const [calcText, setCalcText] = useState<string | null>(null);
  const set = (name: string, v: unknown) => setValues((s) => ({ ...s, [name]: v }));

  async function onSubmit(e: FormEvent) {
    e.preventDefault();
    if (lines?.min && ((values[lines.name] as Values[]) ?? []).length < lines.min) {
      toast.error(`Debe cargar al menos ${lines.min} línea en ${lines.label}`);
      return;
    }
    setSaving(true);
    try {
      onDone(await submit(values));
    } catch (err) {
      toast.error(err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <Modal
      title={title}
      onClose={onClose}
      wide={Boolean(lines) || fields.length > 6}
      footer={
        <>
          {calc && (
            <button
              type="button"
              className="btn"
              onClick={() => api.post<Row>(calc.endpoint, values).then((r) => setCalcText(calc.render(r))).catch(toast.error)}
            >
              {calc.label}
            </button>
          )}
          <div className="spacer" />
          <button type="button" className="btn" onClick={onClose}>
            Cancelar
          </button>
          <button type="submit" form="doc-form" className="btn btn-primary" disabled={saving}>
            {saving ? 'Guardando…' : 'Guardar'}
          </button>
        </>
      }
    >
      <form id="doc-form" className="form-grid" onSubmit={onSubmit}>
        {fields.map((f) => (
          <Field key={f.name} spec={f} value={values[f.name]} onChange={(v) => set(f.name, v)} />
        ))}
        {lines && (
          <div className="field-full">
            <LinesEditor spec={lines} value={values[lines.name]} onChange={(v) => set(lines.name, v)} />
          </div>
        )}
        {calcText && <div className="field-full calc-result">{calcText}</div>}
      </form>
    </Modal>
  );
}
