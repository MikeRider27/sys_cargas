import { useCallback, useEffect, useState } from 'react';
import { api, type Row } from '../api';
import { DataTable } from '../components/DataTable';
import type { FieldSpec } from '../components/Fields';
import { NewButton, PageHead } from '../components/PageHead';
import { useToast } from '../components/Toast';
import { FormModal } from './DocPage';

const NUEVO: FieldSpec[] = [
  { name: 'username', label: 'Usuario', type: 'text', max: 50, required: true },
  { name: 'password', label: 'Contraseña (mín. 8, letras y números)', type: 'password', max: 72, required: true },
  { name: 'idRol', label: 'Rol', type: 'select', options: 'roles', required: true },
  { name: 'idPer', label: 'Personal vinculado', type: 'select', options: 'personal' },
];
const EDITAR: FieldSpec[] = [
  { name: 'idRol', label: 'Rol', type: 'select', options: 'roles', required: true },
  { name: 'idPer', label: 'Personal vinculado', type: 'select', options: 'personal' },
];
const PASSWORD: FieldSpec[] = [{ name: 'password', label: 'Nueva contraseña', type: 'password', max: 72, required: true }];

type Mode = { kind: 'new' } | { kind: 'edit' | 'pass'; row: Row };

export function Usuarios() {
  const toast = useToast();
  const [rows, setRows] = useState<Row[]>([]);
  const [loading, setLoading] = useState(true);
  const [mode, setMode] = useState<Mode | null>(null);
  const load = useCallback(() => {
    setLoading(true);
    api.get('/api/usuarios').then(setRows).catch(toast.error).finally(() => setLoading(false));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  useEffect(load, [load]);

  async function toggle(r: Row) {
    try {
      await api.put(`/api/usuarios/${r.id_usu}`, { idRol: r.id_rol, idPer: r.id_per, idEst: r.id_est === 1 ? 0 : 1 });
      toast.ok('Usuario actualizado');
      load();
    } catch (e) {
      toast.error(e);
    }
  }

  const done = (msg: string) => () => {
    setMode(null);
    toast.ok(msg);
    load();
  };

  return (
    <section>
      <PageHead icon="users" crumb="Mantenimiento · Seguridad" title="Usuarios">
        <NewButton onClick={() => setMode({ kind: 'new' })} />
      </PageHead>
      <DataTable
        columns={[
          { key: 'username', label: 'Usuario' },
          { key: 'rol', label: 'Rol' },
          { key: 'personal', label: 'Personal' },
          { key: 'ultimo_acceso', label: 'Último acceso', type: 'datetime' },
          { key: 'bloqueado', label: 'Bloqueado', type: 'bool' },
          { key: 'estado', label: 'Estado', type: 'estado' },
        ]}
        rows={rows}
        loading={loading}
        actions={(r) => (
          <div className="row-actions">
            <button className="btn btn-sm" onClick={() => setMode({ kind: 'edit', row: r })}>
              Editar
            </button>
            <button className="btn btn-sm" onClick={() => setMode({ kind: 'pass', row: r })}>
              Contraseña
            </button>
            <button className={`btn btn-sm ${r.id_est === 1 ? 'btn-danger' : ''}`} onClick={() => toggle(r)}>
              {r.id_est === 1 ? 'Desactivar' : 'Activar'}
            </button>
          </div>
        )}
      />
      {mode?.kind === 'new' && (
        <FormModal title="Nuevo usuario" fields={NUEVO} submit={(v) => api.post('/api/usuarios', v)} onClose={() => setMode(null)} onDone={done('Usuario creado')} />
      )}
      {mode?.kind === 'edit' && (
        <FormModal
          title={`Editar ${mode.row.username}`}
          fields={EDITAR}
          initial={{ idRol: mode.row.id_rol, idPer: mode.row.id_per }}
          submit={(v) => api.put(`/api/usuarios/${mode.row.id_usu}`, { ...v, idEst: mode.row.id_est })}
          onClose={() => setMode(null)}
          onDone={done('Usuario actualizado')}
        />
      )}
      {mode?.kind === 'pass' && (
        <FormModal
          title={`Contraseña de ${mode.row.username}`}
          fields={PASSWORD}
          submit={(v) => api.post(`/api/usuarios/${mode.row.id_usu}/password`, v)}
          onClose={() => setMode(null)}
          onDone={done('Contraseña actualizada')}
        />
      )}
    </section>
  );
}
