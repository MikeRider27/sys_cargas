import { useState, type FormEvent } from 'react';
import { useAuth } from '../auth';
import { BrandMark, Icon, type IconName } from '../components/Icon';

const FEATURES: { icon: IconName; title: string; text: string }[] = [
  { icon: 'file', title: 'Pedidos, cotizaciones y CRT', text: 'Del pedido del cliente al conocimiento de transporte internacional.' },
  { icon: 'truck', title: 'Flota y despacho', text: 'Asignación de tractos, semirremolques y choferes con control de salida y llegada.' },
  { icon: 'wallet', title: 'Viáticos y rendiciones', text: 'Caja, comprobantes y rendición de cada viaje.' },
];

/** Ilustración: camión recorriendo una ruta entre ciudades. */
function RouteIllustration() {
  return (
    <svg className="login-art" viewBox="0 0 480 260" role="img" aria-label="Camión de carga en ruta internacional">
      <defs>
        <linearGradient id="trailer" x1="0" x2="0" y1="0" y2="1">
          <stop offset="0" stopColor="#f4f7fb" />
          <stop offset="1" stopColor="#c9d6e6" />
        </linearGradient>
        <linearGradient id="cab" x1="0" x2="0" y1="0" y2="1">
          <stop offset="0" stopColor="#f7a54a" />
          <stop offset="1" stopColor="#e2741c" />
        </linearGradient>
      </defs>
      {/* ruta con paradas */}
      <path d="M30 70 C 120 10, 200 120, 280 60 S 430 40, 455 90" fill="none" stroke="#f28c28" strokeWidth="3" strokeDasharray="2 9" strokeLinecap="round" />
      {[
        [30, 70, 'ASU'],
        [280, 60, 'BUE'],
        [455, 90, 'SAO'],
      ].map(([x, y, t]) => (
        <g key={String(t)} transform={`translate(${x} ${y})`}>
          <circle r="11" fill="#0c2340" stroke="#f28c28" strokeWidth="3" />
          <circle r="3.5" fill="#f28c28" />
          <text y="-18" textAnchor="middle" fill="#cfdcec" fontSize="12" fontWeight="700" letterSpacing="1">
            {t}
          </text>
        </g>
      ))}
      {/* carretera */}
      <rect x="0" y="222" width="480" height="38" fill="#0a1c33" />
      <path d="M0 241h480" stroke="#f2c94c" strokeWidth="3" strokeDasharray="26 18" />
      {/* camión */}
      <g transform="translate(70 120)">
        <rect x="0" y="0" width="230" height="86" rx="4" fill="url(#trailer)" />
        <path d="M10 18h210M10 34h210M10 50h210M10 66h210" stroke="#aebfd4" strokeWidth="1.5" />
        <text x="115" y="50" textAnchor="middle" fill="#12457a" fontSize="20" fontWeight="800" letterSpacing="2">
          SYS CARGAS
        </text>
        <rect x="-6" y="86" width="250" height="8" rx="2" fill="#2b3f57" />
        <path d="M238 94V30q0-12 12-12h34q10 0 15 10l20 34q4 7 4 15v17z" fill="url(#cab)" />
        <path d="M262 28h22q6 0 9 6l13 22h-44z" fill="#bfe0ff" opacity="0.9" />
        <rect x="300" y="70" width="18" height="6" rx="2" fill="#fff3c4" />
        <rect x="244" y="94" width="80" height="8" rx="2" fill="#2b3f57" />
        {[30, 62, 190, 268, 300].map((cx) => (
          <g key={cx}>
            <circle cx={cx} cy="104" r="15" fill="#151b24" />
            <circle cx={cx} cy="104" r="6" fill="#8a97a8" />
          </g>
        ))}
      </g>
    </svg>
  );
}

export function Login() {
  const { login } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [busy, setBusy] = useState(false);

  async function onSubmit(e: FormEvent) {
    e.preventDefault();
    setBusy(true);
    setError(null);
    try {
      await login(username, password);
    } catch (err) {
      setError(err instanceof Error ? err.message : String(err));
      setPassword('');
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="login-page">
      <aside className="login-hero">
        <div className="login-brand">
          <BrandMark size={44} />
          <div>
            <div className="brand-name">Sys Cargas</div>
            <div className="brand-tag">Fletes &amp; Logística</div>
          </div>
        </div>
        <div className="login-hero-body">
          <h2>Transporte de cargas nacional e internacional</h2>
          <RouteIllustration />
          <ul className="login-features">
            {FEATURES.map((f) => (
              <li key={f.title}>
                <span className="feature-icon">
                  <Icon name={f.icon} size={20} />
                </span>
                <span>
                  <strong>{f.title}</strong>
                  <span>{f.text}</span>
                </span>
              </li>
            ))}
          </ul>
        </div>
      </aside>
      <main className="login-main">
        <form className="login-card" onSubmit={onSubmit}>
          <div className="login-card-brand">
            <BrandMark size={40} />
          </div>
          <h1>Bienvenido</h1>
          <p className="muted">Ingrese con su usuario para continuar</p>
          <div className="field">
            <label htmlFor="u">Usuario</label>
            <div className="input-icon">
              <Icon name="user" size={18} />
              <input id="u" value={username} onChange={(e) => setUsername(e.target.value)} autoComplete="username" required autoFocus />
            </div>
          </div>
          <div className="field">
            <label htmlFor="p">Contraseña</label>
            <div className="input-icon">
              <Icon name="shield" size={18} />
              <input id="p" type="password" value={password} onChange={(e) => setPassword(e.target.value)} autoComplete="current-password" required />
            </div>
          </div>
          {error && <div className="alert">{error}</div>}
          <button className="btn btn-accent btn-block btn-lg" disabled={busy}>
            {busy ? 'Ingresando…' : 'Ingresar'}
          </button>
          <p className="login-foot muted">© {new Date().getFullYear()} Sys Cargas · Fletes y Logística</p>
        </form>
      </main>
    </div>
  );
}
