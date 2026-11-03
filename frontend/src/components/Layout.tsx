import { useEffect, useState } from 'react';
import { NavLink, Outlet, useLocation } from 'react-router';
import { api } from '../api';
import { useAuth } from '../auth';
import { DOCS } from '../docs';
import { BrandMark, DOC_ICONS, Icon, MODULE_ICONS } from './Icon';

interface CatalogMeta {
  name: string;
  title: string;
  group: string;
}

const MODULOS = ['Pedidos', 'Logística', 'Rendiciones'];

const fechaLarga = new Intl.DateTimeFormat('es-PY', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });

function iniciales(nombre?: string) {
  return (nombre ?? '?')
    .split(/\s+/)
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0])
    .join('')
    .toUpperCase();
}

export function Layout() {
  const { user, logout } = useAuth();
  const [catalogs, setCatalogs] = useState<CatalogMeta[]>([]);
  const [menuOpen, setMenuOpen] = useState(false);
  const location = useLocation();
  useEffect(() => setMenuOpen(false), [location.pathname]);
  useEffect(() => {
    api.get<CatalogMeta[]>('/api/catalogos').then(setCatalogs).catch(() => setCatalogs([]));
  }, []);
  const catGroups = [...new Set(catalogs.map((c) => c.group))];
  const enMantenimiento = location.pathname.startsWith('/catalogo') || location.pathname === '/usuarios';

  return (
    <div className="app">
      <aside className={`sidebar ${menuOpen ? 'open' : ''}`}>
        <div className="brand">
          <BrandMark />
          <div>
            <div className="brand-name">Sys Cargas</div>
            <div className="brand-tag">Fletes &amp; Logística</div>
          </div>
        </div>
        <nav>
          <NavLink to="/" end className="nav-link">
            <Icon name="home" /> Centro de operaciones
          </NavLink>
          {MODULOS.map((m) => (
            <details key={m} open>
              <summary>
                <Icon name={MODULE_ICONS[m]} size={15} /> {m}
              </summary>
              {DOCS.filter((d) => d.group === m).map((d) => (
                <NavLink key={d.key} to={`/doc/${d.key}`} className="nav-link">
                  <Icon name={DOC_ICONS[d.key]} /> {d.title}
                </NavLink>
              ))}
            </details>
          ))}
          <details open={enMantenimiento}>
            <summary>
              <Icon name="sliders" size={15} /> Mantenimiento
            </summary>
            {catGroups.map((g) => (
              <div key={g} className="nav-sub">
                <div className="nav-sub-title">{g}</div>
                {catalogs
                  .filter((c) => c.group === g)
                  .map((c) => (
                    <NavLink key={c.name} to={`/catalogo/${c.name}`} className="nav-link nav-link-sm">
                      {c.title}
                    </NavLink>
                  ))}
              </div>
            ))}
            {user?.rol === 'ADMIN' && (
              <NavLink to="/usuarios" className="nav-link">
                <Icon name="users" /> Usuarios
              </NavLink>
            )}
          </details>
          <NavLink to="/informes" className="nav-link nav-reports">
            <Icon name="printer" /> Informes
          </NavLink>
        </nav>
      </aside>
      {menuOpen && <div className="sidebar-scrim" onClick={() => setMenuOpen(false)} />}
      <div className="main">
        <header className="topbar">
          <button className="btn-icon menu-btn" aria-label="Menú" onClick={() => setMenuOpen(!menuOpen)}>
            <Icon name="menu" size={22} />
          </button>
          <div className="topbar-date">
            <Icon name="calendar" size={16} />
            <span>{fechaLarga.format(new Date())}</span>
          </div>
          <div className="spacer" />
          <NavLink to="/perfil" className="user-chip" title="Mi cuenta">
            <span className="avatar">{iniciales(user?.nombre)}</span>
            <span className="user-info">
              <span className="user-name">{user?.nombre}</span>
              <span className="user-role">{user?.rol}</span>
            </span>
          </NavLink>
          <button className="btn-icon" onClick={logout} title="Cerrar sesión" aria-label="Cerrar sesión">
            <Icon name="logout" size={20} />
          </button>
        </header>
        <main className="content">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
