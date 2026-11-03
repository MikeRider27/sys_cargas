export type Row = Record<string, unknown>;

export interface Option {
  value: number;
  label: string;
}

const TOKEN_KEY = 'syscargas.token';
const USER_KEY = 'syscargas.user';

export interface SessionUser {
  id: number;
  username: string;
  rol: 'ADMIN' | 'OPERADOR' | 'CONSULTA';
  nombre: string;
}

export const session = {
  token: (): string | null => sessionStorage.getItem(TOKEN_KEY),
  user: (): SessionUser | null => {
    const raw = sessionStorage.getItem(USER_KEY);
    return raw ? (JSON.parse(raw) as SessionUser) : null;
  },
  save(token: string, user: SessionUser) {
    sessionStorage.setItem(TOKEN_KEY, token);
    sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  },
  clear() {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(USER_KEY);
  },
};

export class ApiError extends Error {
  constructor(public status: number, message: string) {
    super(message);
  }
}

let onUnauthorized: () => void = () => {};
export function setUnauthorizedHandler(fn: () => void) {
  onUnauthorized = fn;
}

async function request(method: string, url: string, body?: unknown): Promise<Response> {
  const headers: Record<string, string> = {};
  const token = session.token();
  if (token) headers.Authorization = `Bearer ${token}`;
  if (body !== undefined) headers['Content-Type'] = 'application/json';
  const res = await fetch(url, { method, headers, body: body === undefined ? undefined : JSON.stringify(body) });
  if (res.status === 401 && token) {
    session.clear();
    onUnauthorized();
  }
  if (!res.ok) {
    let msg = `Error ${res.status}`;
    try {
      const pd = await res.json();
      if (pd?.detail) msg = pd.detail;
    } catch {
      /* respuesta sin cuerpo JSON */
    }
    throw new ApiError(res.status, msg);
  }
  return res;
}

async function json<T>(method: string, url: string, body?: unknown): Promise<T> {
  const res = await request(method, url, body);
  if (res.status === 204) return undefined as T;
  return (await res.json()) as T;
}

const optionCache = new Map<string, Promise<Option[]>>();

export const api = {
  get: <T = Row[]>(url: string) => json<T>('GET', url),
  post: <T = Row>(url: string, body?: unknown) => {
    optionCache.clear();
    return json<T>('POST', url, body ?? {});
  },
  put: <T = Row>(url: string, body: unknown) => {
    optionCache.clear();
    return json<T>('PUT', url, body);
  },
  del: (url: string) => {
    optionCache.clear();
    return json<void>('DELETE', url);
  },
  options(name: string): Promise<Option[]> {
    let p = optionCache.get(name);
    if (!p) {
      p = json<Option[]>('GET', `/api/opciones/${name}`);
      optionCache.set(name, p);
      p.catch(() => optionCache.delete(name));
    }
    return p;
  },
  async openPdf(url: string) {
    // se abre la ventana antes del await para que el navegador no la bloquee
    const win = window.open('', '_blank');
    try {
      const res = await request('GET', url);
      const blob = await res.blob();
      const href = URL.createObjectURL(blob);
      if (win) win.location.href = href;
      else window.location.href = href;
      setTimeout(() => URL.revokeObjectURL(href), 60_000);
    } catch (e) {
      win?.close();
      throw e;
    }
  },
};
