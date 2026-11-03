import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import { AuthProvider, useAuth } from './auth';
import { Layout } from './components/Layout';
import { ToastProvider } from './components/Toast';
import { CatalogPage } from './pages/CatalogPage';
import { Dashboard } from './pages/Dashboard';
import { DocPage } from './pages/DocPage';
import { Informes } from './pages/Informes';
import { Login } from './pages/Login';
import { Perfil } from './pages/Perfil';
import { Usuarios } from './pages/Usuarios';

function Routed() {
  const { user } = useAuth();
  if (!user) return <Login />;
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route index element={<Dashboard />} />
        <Route path="doc/:key" element={<DocPage />} />
        <Route path="catalogo/:name" element={<CatalogPage />} />
        <Route path="informes" element={<Informes />} />
        <Route path="perfil" element={<Perfil />} />
        {user.rol === 'ADMIN' && <Route path="usuarios" element={<Usuarios />} />}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Route>
    </Routes>
  );
}

export function App() {
  return (
    <ToastProvider>
      <AuthProvider>
        <BrowserRouter>
          <Routed />
        </BrowserRouter>
      </AuthProvider>
    </ToastProvider>
  );
}
