import { useState, type FormEvent } from 'react';
import { api } from '../api';
import { useAuth } from '../auth';
import { PageHead } from '../components/PageHead';
import { useToast } from '../components/Toast';

export function Perfil() {
  const { user } = useAuth();
  const toast = useToast();
  const [actual, setActual] = useState('');
  const [nueva, setNueva] = useState('');
  const [repetir, setRepetir] = useState('');

  async function onSubmit(e: FormEvent) {
    e.preventDefault();
    if (nueva !== repetir) {
      toast.error('Las contraseñas nuevas no coinciden');
      return;
    }
    try {
      await api.post('/api/auth/password', { actual, nueva });
      toast.ok('Contraseña actualizada');
      setActual('');
      setNueva('');
      setRepetir('');
    } catch (err) {
      toast.error(err);
    }
  }

  return (
    <section>
      <PageHead icon="user" crumb="Usuario" title="Mi cuenta" />
      <form className="card pad narrow" onSubmit={onSubmit}>
        <p>
          <strong>{user?.nombre}</strong> · {user?.username} · {user?.rol}
        </p>
        <h2>Cambiar contraseña</h2>
        <div className="field">
          <label htmlFor="pa">Contraseña actual</label>
          <input id="pa" type="password" value={actual} onChange={(e) => setActual(e.target.value)} autoComplete="current-password" required />
        </div>
        <div className="field">
          <label htmlFor="pn">Nueva contraseña</label>
          <input id="pn" type="password" value={nueva} onChange={(e) => setNueva(e.target.value)} autoComplete="new-password" minLength={8} required />
        </div>
        <div className="field">
          <label htmlFor="pr">Repetir nueva contraseña</label>
          <input id="pr" type="password" value={repetir} onChange={(e) => setRepetir(e.target.value)} autoComplete="new-password" minLength={8} required />
        </div>
        <button className="btn btn-primary">Guardar</button>
      </form>
    </section>
  );
}
