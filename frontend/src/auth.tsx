import { createContext, useContext, useEffect, useState, type ReactNode } from 'react';
import { api, session, setUnauthorizedHandler, type SessionUser } from './api';

interface AuthCtx {
  user: SessionUser | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  canWrite: boolean;
}

const Ctx = createContext<AuthCtx>(null!);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<SessionUser | null>(session.user());
  useEffect(() => setUnauthorizedHandler(() => setUser(null)), []);
  const value: AuthCtx = {
    user,
    canWrite: user?.rol === 'ADMIN' || user?.rol === 'OPERADOR',
    async login(username, password) {
      const res = await api.post<{ token: string; usuario: SessionUser }>('/api/auth/login', { username, password });
      session.save(res.token, res.usuario);
      setUser(res.usuario);
    },
    logout() {
      session.clear();
      setUser(null);
    },
  };
  return <Ctx.Provider value={value}>{children}</Ctx.Provider>;
}

export const useAuth = () => useContext(Ctx);
