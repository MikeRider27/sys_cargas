const nf0 = new Intl.NumberFormat('es-PY', { maximumFractionDigits: 0 });
const nf2 = new Intl.NumberFormat('es-PY', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

export type ColType = 'text' | 'date' | 'datetime' | 'money' | 'num' | 'int' | 'bool' | 'estado';

export function fmt(value: unknown, type: ColType = 'text'): string {
  if (value === null || value === undefined || value === '') return '';
  switch (type) {
    case 'date': {
      const [y, m, d] = String(value).slice(0, 10).split('-');
      return `${d}/${m}/${y}`;
    }
    case 'datetime': {
      const s = String(value);
      return `${fmt(s.slice(0, 10), 'date')} ${s.slice(11, 16)}`;
    }
    case 'money':
      return nf0.format(Number(value));
    case 'num':
      return nf2.format(Number(value));
    case 'bool':
      return value ? 'Sí' : 'No';
    default:
      return String(value);
  }
}

export function today(): string {
  const d = new Date();
  d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
  return d.toISOString().slice(0, 10);
}

export function nowLocal(): string {
  const d = new Date();
  d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
  return d.toISOString().slice(0, 16);
}

export function humanize(key: string): string {
  const s = key.replace(/^id_/, '').replace(/_/g, ' ');
  return s.charAt(0).toUpperCase() + s.slice(1);
}

export function estadoClass(estado: unknown): string {
  switch (String(estado ?? '').toUpperCase()) {
    case 'ANULADO':
      return 'badge badge-red';
    case 'PENDIENTE':
    case 'ABIERTA':
      return 'badge badge-amber';
    case 'EN PROCESO':
      return 'badge badge-blue';
    case 'FINALIZADO':
    case 'CERRADA':
      return 'badge badge-gray';
    default:
      return 'badge badge-green';
  }
}
