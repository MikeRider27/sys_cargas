import { useEffect, useMemo, useRef, useState } from 'react';
import { api, type Option } from '../api';

export type FieldType = 'text' | 'textarea' | 'number' | 'int' | 'date' | 'datetime' | 'email' | 'password' | 'select' | 'multiselect' | 'checkbox';

export interface FieldSpec {
  name: string;
  label: string;
  type: FieldType;
  required?: boolean;
  options?: string;
  max?: number;
  min?: number;
  step?: string;
  default?: () => unknown;
  full?: boolean;
}

export type Values = Record<string, unknown>;

export function useOptions(name?: string): Option[] {
  const [opts, setOpts] = useState<Option[]>([]);
  useEffect(() => {
    if (!name) return;
    let alive = true;
    api.options(name).then((o) => alive && setOpts(o)).catch(() => alive && setOpts([]));
    return () => {
      alive = false;
    };
  }, [name]);
  return opts;
}

/** Combo con filtro por texto (los catálogos como clientes pueden ser largos). */
export function SearchSelect({ options, value, onChange, required, id }: {
  options: string;
  value: unknown;
  onChange: (v: number | null) => void;
  required?: boolean;
  id?: string;
}) {
  const opts = useOptions(options);
  const [open, setOpen] = useState(false);
  const [q, setQ] = useState('');
  const ref = useRef<HTMLDivElement>(null);
  const selected = opts.find((o) => o.value === value);
  const filtered = useMemo(() => {
    const t = q.trim().toLowerCase();
    return (t ? opts.filter((o) => o.label.toLowerCase().includes(t)) : opts).slice(0, 200);
  }, [opts, q]);

  useEffect(() => {
    const close = (e: MouseEvent) => ref.current && !ref.current.contains(e.target as Node) && setOpen(false);
    document.addEventListener('mousedown', close);
    return () => document.removeEventListener('mousedown', close);
  }, []);

  return (
    <div className="sselect" ref={ref}>
      <input
        id={id}
        value={open ? q : selected?.label ?? ''}
        placeholder={open ? 'Escriba para filtrar…' : 'Seleccione…'}
        onFocus={() => {
          setOpen(true);
          setQ('');
        }}
        onChange={(e) => setQ(e.target.value)}
        required={required && value == null}
        autoComplete="off"
      />
      {open && (
        <ul className="sselect-list" role="listbox">
          {!required && (
            <li
              className="muted"
              onMouseDown={() => {
                onChange(null);
                setOpen(false);
              }}
            >
              (ninguno)
            </li>
          )}
          {filtered.map((o) => (
            <li
              key={o.value}
              className={o.value === value ? 'active' : undefined}
              onMouseDown={() => {
                onChange(o.value);
                setOpen(false);
              }}
            >
              {o.label}
            </li>
          ))}
          {filtered.length === 0 && <li className="muted">Sin coincidencias</li>}
        </ul>
      )}
    </div>
  );
}

function MultiSelect({ options, value, onChange }: { options: string; value: unknown; onChange: (v: number[]) => void }) {
  const opts = useOptions(options);
  const list = Array.isArray(value) ? (value as number[]) : [];
  const [pick, setPick] = useState<number | null>(null);
  return (
    <div className="multi">
      <div className="multi-add">
        <SearchSelect options={options} value={pick} onChange={setPick} />
        <button
          type="button"
          className="btn btn-sm"
          disabled={pick == null}
          onClick={() => {
            if (pick != null && !list.includes(pick)) onChange([...list, pick]);
            setPick(null);
          }}
        >
          Agregar
        </button>
      </div>
      <ul className="chips">
        {list.map((v) => (
          <li key={v} className="chip">
            {opts.find((o) => o.value === v)?.label ?? v}
            <button type="button" aria-label="Quitar" onClick={() => onChange(list.filter((x) => x !== v))}>
              ×
            </button>
          </li>
        ))}
        {list.length === 0 && <li className="muted">Ninguno</li>}
      </ul>
    </div>
  );
}

export function Field({ spec, value, onChange }: { spec: FieldSpec; value: unknown; onChange: (v: unknown) => void }) {
  const id = `f-${spec.name}`;
  const str = value == null ? '' : String(value);
  let input;
  switch (spec.type) {
    case 'select':
      input = <SearchSelect id={id} options={spec.options!} value={value} onChange={onChange} required={spec.required} />;
      break;
    case 'multiselect':
      input = <MultiSelect options={spec.options!} value={value} onChange={onChange} />;
      break;
    case 'textarea':
      input = (
        <textarea id={id} value={str} maxLength={spec.max} required={spec.required} rows={3} onChange={(e) => onChange(e.target.value)} />
      );
      break;
    case 'checkbox':
      input = <input id={id} type="checkbox" checked={Boolean(value)} onChange={(e) => onChange(e.target.checked)} />;
      break;
    case 'number':
    case 'int':
      input = (
        <input
          id={id}
          type="number"
          value={str}
          min={spec.min}
          step={spec.step ?? (spec.type === 'int' ? '1' : 'any')}
          required={spec.required}
          onChange={(e) => onChange(e.target.value === '' ? null : Number(e.target.value))}
        />
      );
      break;
    case 'date':
      input = <input id={id} type="date" value={str} required={spec.required} onChange={(e) => onChange(e.target.value || null)} />;
      break;
    case 'datetime':
      input = (
        <input id={id} type="datetime-local" value={str} required={spec.required} onChange={(e) => onChange(e.target.value || null)} />
      );
      break;
    default:
      input = (
        <input
          id={id}
          type={spec.type === 'email' || spec.type === 'password' ? spec.type : 'text'}
          autoComplete={spec.type === 'password' ? 'new-password' : undefined}
          value={str}
          maxLength={spec.max}
          required={spec.required}
          onChange={(e) => onChange(e.target.value)}
        />
      );
  }
  return (
    <div className={`field ${spec.full || spec.type === 'textarea' || spec.type === 'multiselect' ? 'field-full' : ''}`}>
      <label htmlFor={id}>
        {spec.label}
        {spec.required && <span className="req"> *</span>}
      </label>
      {input}
    </div>
  );
}

export interface LinesSpec {
  name: string;
  label: string;
  fields: FieldSpec[];
  min?: number;
}

export function LinesEditor({ spec, value, onChange }: { spec: LinesSpec; value: unknown; onChange: (v: Values[]) => void }) {
  const lines = Array.isArray(value) ? (value as Values[]) : [];
  const set = (i: number, name: string, v: unknown) => onChange(lines.map((l, j) => (j === i ? { ...l, [name]: v } : l)));
  return (
    <fieldset className="lines">
      <legend>{spec.label}</legend>
      {lines.map((line, i) => (
        <div className="line" key={i}>
          <span className="line-n">{i + 1}</span>
          {spec.fields.map((f) => (
            <Field key={f.name} spec={{ ...f, full: false }} value={line[f.name]} onChange={(v) => set(i, f.name, v)} />
          ))}
          <button type="button" className="btn-icon danger" aria-label="Quitar línea" onClick={() => onChange(lines.filter((_, j) => j !== i))}>
            ×
          </button>
        </div>
      ))}
      <button type="button" className="btn btn-sm" onClick={() => onChange([...lines, defaults(spec.fields)])}>
        + Agregar línea
      </button>
    </fieldset>
  );
}

export function defaults(fields: FieldSpec[]): Values {
  const v: Values = {};
  for (const f of fields) v[f.name] = f.default ? f.default() : f.type === 'multiselect' ? [] : f.type === 'checkbox' ? false : null;
  return v;
}
